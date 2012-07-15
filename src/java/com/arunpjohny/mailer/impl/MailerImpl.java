package com.arunpjohny.mailer.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MailerImpl extends AbstractMailer {
	protected int emailIndex;

	public void setFields(List<String> fields) {
		super.setFields(fields);
		if (fields != null) {
			this.emailIndex = fields.indexOf("email");
		}
		Assert.isTrue(this.emailIndex >= 0,
				"Please specify the email field location in source.");
	}

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
			while ((line = reader.readLine()) != null) {
				logger.debug("Reading line {}", line);
				try {
					final Record record = getRecord(line);
					logger.info("Sending email to {}", record.getEmail());

					final String email = record.getEmail();
					final String plainBody = StringUtils.isBlank(plain) ? null
							: processMessage(plain, record);
					final String htmlBody = StringUtils.isBlank(html) ? null
							: processMessage(html, record);

					mail(email, htmlBody, plainBody);
				} catch (Exception ex) {
					if (logger.isWarnEnabled()) {
						logger.warn("Error while sending mail to source: "
								+ line, ex);
					} else {
						logger.error("Error while sending mail to source: {}"
								+ line + ", " + ex.toString());
					}
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		FileUtils.deleteQuietly(source);
	}

	protected String processMessage(String message, Record record)
			throws IOException, TemplateException {
		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		Template template = new Template("message", new StringReader(message),
				configuration);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template,
				record.getModel());
	}

	protected Record getRecord(String line) {
		Record record = new Record();
		if (org.springframework.util.CollectionUtils.isEmpty(fields)) {
			record.setEmail(line);
		} else {

			String[] split = StringUtils.split(line, ",");
			if (split.length <= this.emailIndex
					&& StringUtils.isNotBlank(split[this.emailIndex])) {
				throw new IllegalArgumentException(
						"Unable to find email field in {" + line + "}.");
			}
			record.setEmail(StringUtils.trimToEmpty(split[this.emailIndex]));

			for (int i = 0; i < fields.size(); i++) {
				if (i >= split.length) {
					break;
				}
				record.put(StringUtils.trimToEmpty(fields.get(i)),
						StringUtils.trimToEmpty(split[i]));
			}
		}

		return record;
	}

	private class Record {
		private String email;

		private Map<String, String> model = new HashMap<String, String>();

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Map<String, String> getModel() {
			return new HashMap<String, String>(model);
		}

		public void put(String key, String value) {
			model.put(key, value);
		}
	}

}