package com.arunpjohny.service.impl;


import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.arunpjohny.core.service.impl.BaseManager;
import com.arunpjohny.service.MailManager;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MailManagerImpl extends BaseManager implements MailManager {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendMail(String from, String to, String subject, String body) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom(from);
		simpleMessage.setReplyTo(from);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(body);
		simpleMessage.setTo(to);
		mailSender.send(simpleMessage);
	}

	@Override
	public void sendMail(final String from, final String to,
			final String subject, final String message,
			final Map<String, byte[]> attachments) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(
						mimeMessage, true, "UTF-8");
				messageHelper.setSubject(subject);
				messageHelper.setTo(to);
				messageHelper.setFrom(from);
				messageHelper.setText(message);
				for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
					messageHelper.addAttachment(entry.getKey(),
							new ByteArrayResource(entry.getValue()));
				}

			}
		};
		mailSender.send(preparator);
	}
}
