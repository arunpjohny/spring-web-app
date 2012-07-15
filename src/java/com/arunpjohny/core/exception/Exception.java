package com.arunpjohny.core.exception;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@ExceptionMetadata(converter = CustomExceptionConverter.class)
public class Exception extends java.lang.Exception implements AppException {
	private static final long serialVersionUID = 1L;

	public Exception() {
		super();
	}

	public Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public Exception(String message) {
		super(message);
	}

	public Exception(Throwable cause) {
		super(cause);
	}

}
