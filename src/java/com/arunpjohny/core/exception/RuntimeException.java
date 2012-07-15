package com.arunpjohny.core.exception;

import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@com.arunpjohny.core.exception.annotation.ExceptionMetadata(converter = CustomExceptionConverter.class)
public class RuntimeException extends java.lang.RuntimeException implements
		AppException {
	private static final long serialVersionUID = 1L;

	public RuntimeException() {
		super();
	}

	public RuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeException(String message) {
		super(message);
	}

	public RuntimeException(Throwable cause) {
		super(cause);
	}

}
