package com.arunpjohny.service;


import java.util.Map;

import com.arunpjohny.core.service.Manager;

public interface MailManager extends Manager {

	void sendMail(String from, String to, String subject, String body);

	public abstract void sendMail(final String from, final String to,
			final String subject, final String message,
			final Map<String, byte[]> attachments);

}
