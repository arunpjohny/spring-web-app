package com.arunpjohny.mailer.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class SimpleMailerImpl extends AbstractMailer {

	public void send(File source) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"Sending mails using source: [{}], fields: [{}]"
							+ ", subject: [{}], html: [{}], plain: [{}] and attachments: [{}].",
					new Object[] { source, fields, subject, html, plain,
							attachments });
		} else {
			logger.info("Sending mails for source: {}", source);
		}

		if (StringUtils.isBlank(html) && StringUtils.isBlank(plain)) {
			throw new IllegalArgumentException(
					"Both HTML and plain contents cannot be blank.");
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(source));
			String line;
			List<String> emails = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				logger.debug("Reading line {}", line);
				try {
					logger.info("Processing line {}", line);

					String[] split = StringUtils.split(line, ",");
					for (String string : split) {
						if (StringUtils.isNotBlank(string)) {
							emails.add(StringUtils.trimToEmpty(string));

							if (emails.size() == 50) {
								mail(emails.toArray(new String[] {}), html,
										plain);
								emails.clear();
							}
						}
					}
				} catch (Exception ex) {
					if (logger.isWarnEnabled()) {
						logger.warn("Error while sending mail to source: ", ex);
					} else {
						logger.error("Error while sending mail to source: {}",
								ex.toString());
					}
				}
			}
			if (emails.size() != 0) {
				mail(emails.toArray(new String[] {}), html, plain);
				emails.clear();
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		FileUtils.deleteQuietly(source);
	}

	protected void mail(final String[] emails, final String html,
			final String plain) {
		logger.debug("Sending emails to {}", StringUtils.join(emails, ", "));
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(
						mimeMessage, true, "UTF-8");
				messageHelper.setSubject(subject);
				for (String string : emails) {
					messageHelper.addBcc(string);
				}
				InternetAddress fromAddress = StringUtils.isBlank(fromName) ? new InternetAddress(
						from) : new InternetAddress(from, fromName);
				messageHelper.setFrom(fromAddress);
				messageHelper.setTo(from);
				if (StringUtils.isNotBlank(plain)
						&& StringUtils.isNotBlank(html)) {
					messageHelper.setText(plain, html);
				} else if (StringUtils.isNotBlank(plain)) {
					messageHelper.setText(plain);
				} else if (StringUtils.isNotBlank(html)) {
					messageHelper.setText(html, true);
				}

				if (attachments != null) {
					for (Map.Entry<String, byte[]> entry : attachments
							.entrySet()) {
						messageHelper.addAttachment(entry.getKey(),
								new ByteArrayResource(entry.getValue()));
					}
				}
			}
		};

		this.mailSender.send(preparator);
	}
}
