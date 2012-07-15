package com.arunpjohny.web.utils;

public enum HttpHeader {
	LOCATION("Location"), X_REQUESTED_WITH("x-requested-with");

	private String name;

	private HttpHeader(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
