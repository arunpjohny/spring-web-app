package com.arunpjohny.core.exception.server;


import org.springframework.http.HttpStatus;

import com.arunpjohny.core.exception.annotation.ExceptionMetadata;
import com.arunpjohny.core.exception.conversion.CustomExceptionConverter;

@ExceptionMetadata(converter = CustomExceptionConverter.class, status = HttpStatus.NOT_IMPLEMENTED)
public class OperationNotImplementedException extends ServerException {
	private static final long serialVersionUID = 1732892747400928985L;

	public OperationNotImplementedException() {
		super();
	}

	public OperationNotImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationNotImplementedException(String message) {
		super(message);
	}

	public OperationNotImplementedException(Throwable cause) {
		super(cause);
	}

}
