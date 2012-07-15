package com.arunpjohny.core.exception.conversion;


import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import com.arunpjohny.web.utils.RequestUtils;
import com.arunpjohny.web.utils.RequestUtils.MessageType;

public class DefaultExceptionConverter implements
		Converter<Exception, Map<String, Object>> {

	public static Converter<Exception, Map<String, Object>> getInstance() {
		return new DefaultExceptionConverter();
	};

	@Override
	public Map<String, Object> convert(Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
		RequestUtils.addAjaxMessage(map, MessageType.ERROR,
				"Unknown server error occured!");
		return map;
	}

}
