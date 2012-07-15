package com.arunpjohny.core.exception;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.INTERNAL_SERVER_ERROR)
public class RequestProcessingException extends RuntimeException {
	private static final long serialVersionUID = 958825190752918093L;

	public RequestProcessingException() {
		super();
	}

	public RequestProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestProcessingException(String message) {
		super(message);
	}

	public RequestProcessingException(Throwable cause) {
		super(cause);
	}

}
