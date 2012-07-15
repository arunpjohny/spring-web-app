package com.arunpjohny.core.exception.server;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.RequestProcessingException;
import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends RequestProcessingException {
	private static final long serialVersionUID = -4204670287552119243L;

	public ServerException() {
		super();
	}

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

}
