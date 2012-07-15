package com.arunpjohny.core.web.model.ajax;

import org.codehaus.jackson.annotate.JsonProperty;

public class Redirect {

	@JsonProperty("_app-redirect-path")
	private String path;

	@JsonProperty("_page-message")
	private String message;

	public Redirect() {
	}

	public Redirect(String path) {
		super();
		this.path = path;
	}

	public Redirect(String path, String message) {
		super();
		this.path = path;
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
