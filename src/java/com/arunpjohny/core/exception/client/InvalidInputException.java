package com.arunpjohny.core.exception.client;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;


@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends ClientException {
	private static final long serialVersionUID = -8402179475459192649L;

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}

}
