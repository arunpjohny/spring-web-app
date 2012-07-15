package com.arunpjohny.core.exception.client;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.METHOD_NOT_ALLOWED)
public class OperationNotAllowedException extends ClientException {
	private static final long serialVersionUID = -8430538530150228781L;

	public OperationNotAllowedException() {
		super();
	}

	public OperationNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationNotAllowedException(String message) {
		super(message);
	}

	public OperationNotAllowedException(Throwable cause) {
		super(cause);
	}

}
