package com.arunpjohny.mailer;

import com.arunpjohny.mailer.impl.MailerImpl;
import com.arunpjohny.mailer.impl.SimpleMailerImpl;

public enum MailerType {
	SIMPLE(SimpleMailerImpl.class), INDIVIDUAL(MailerImpl.class);

	private Class<? extends BulkMailer> clazz;

	private MailerType(Class<? extends BulkMailer> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends BulkMailer> getClazz() {
		return clazz;
	}
}
