package com.arunpjohny.core.exception.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExceptionMetadata {
	HttpStatus status() default HttpStatus.INTERNAL_SERVER_ERROR;

	@SuppressWarnings("rawtypes")
	Class<? extends Converter> converter();
}
