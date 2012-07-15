package com.arunpjohny.core.exception.client;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.RequestProcessingException;
import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;


@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.BAD_REQUEST)
public class ClientException extends RequestProcessingException {
	private static final long serialVersionUID = -3490566405110396993L;

	public ClientException() {
		super();
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

}
