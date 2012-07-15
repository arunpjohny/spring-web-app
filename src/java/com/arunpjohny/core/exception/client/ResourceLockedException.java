package com.arunpjohny.core.exception.client;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.LOCKED)
public class ResourceLockedException extends ClientException {
	private static final long serialVersionUID = -4710216811634707298L;

	public ResourceLockedException() {
		super();
	}

	public ResourceLockedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceLockedException(String message) {
		super(message);
	}

	public ResourceLockedException(Throwable cause) {
		super(cause);
	}

}
