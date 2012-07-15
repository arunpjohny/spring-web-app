package com.arunpjohny.core.exception;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;

public class ExceptionMetadata {
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	@SuppressWarnings("rawtypes")
	private Converter converter;

	@SuppressWarnings("rawtypes")
	private Class exception;

	public ExceptionMetadata() {
	}

	@SuppressWarnings("rawtypes")
	public ExceptionMetadata(Class exception, HttpStatus status,
			Converter converter) {
		super();

		assert exception != null;
		assert status != null;
		assert converter != null;

		this.exception = exception;
		this.status = status;
		this.converter = converter;
	}

	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {
		return exception != null && exception.equals(clazz);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Converter getConverter() {
		return converter;
	}

	@SuppressWarnings("rawtypes")
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	@SuppressWarnings("rawtypes")
	public Class getException() {
		return exception;
	}

	@SuppressWarnings("rawtypes")
	public void setException(Class exception) {
		this.exception = exception;
	}
}
