package com.arunpjohny.core.exception.conversion;


import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import com.arunpjohny.core.exception.AppException;
import com.arunpjohny.web.utils.RequestUtils;
import com.arunpjohny.web.utils.RequestUtils.MessageType;

public class CustomExceptionConverter implements
		Converter<AppException, Map<String, Object>> {

	@Override
	public Map<String, Object> convert(AppException ex) {
		if (!(ex instanceof Throwable)) {
			throw new IllegalArgumentException("Exception " + ex.getClass()
					+ " must be an instance of " + Throwable.class + ".");
		}
		Throwable e = (Throwable) ex;

		Map<String, Object> map = new HashMap<String, Object>();
		RequestUtils.addAjaxMessage(map, MessageType.ERROR, e.getMessage());
		return map;
	}

}
