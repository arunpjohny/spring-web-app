package com.arunpjohny.web.controller.main.form;

import org.hibernate.validator.constraints.NotBlank;

public class ContactUsMailForm {

	private String name;

	private String mobile;

	@NotBlank
	private String from;

	@NotBlank
	private String subject;

	@NotBlank
	private String body;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
