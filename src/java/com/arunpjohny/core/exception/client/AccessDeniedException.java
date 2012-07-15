package com.arunpjohny.core.exception.client;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;


@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends ClientException {
	private static final long serialVersionUID = -6175116407718378138L;

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}

}
