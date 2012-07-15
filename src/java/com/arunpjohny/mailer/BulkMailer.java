package com.arunpjohny.mailer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;

public interface BulkMailer {
	void setLogger(Logger logger);

	void setSender(JavaMailSender mailSender);

	void setFields(List<String> fields);

	void setFrom(String from);

	void setFromName(String fromName);

	void setSubject(String subject);

	void html(String html);

	void plain(String plain);

	void setAttachments(Map<String, byte[]> attachments);

	void send(File source) throws IOException;
}
