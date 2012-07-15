package com.arunpjohny.core.exception.conversion;

import static com.arunpjohny.web.utils.RequestUtils.AJAX_FIELD_ERRORS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.arunpjohny.web.utils.RequestUtils;
import com.arunpjohny.web.utils.RequestUtils.MessageType;

public class BindExceptionConverter implements
		Converter<BindException, Map<String, Object>> {

	public static Converter<BindException, Map<String, Object>> getInstance() {
		return new BindExceptionConverter();
	};

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> convert(BindException ex) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> fieldErrorMap = new HashMap<String, Object>();
		for (FieldError fieldError : ex.getFieldErrors()) {
			if (fieldErrorMap.containsKey(fieldError.getField())) {
				Collection<Object> collection;
				if (Collection.class.isAssignableFrom(fieldErrorMap.get(
						fieldError.getField()).getClass())) {
					collection = (Collection<Object>) fieldErrorMap
							.get(fieldError.getField());
				} else {
					collection = new ArrayList<Object>();
					collection.add(fieldErrorMap.get(fieldError.getField()));
					fieldErrorMap.put(fieldError.getField(), collection);
				}
				collection.add(getMessage(fieldError));
			} else {
				fieldErrorMap
						.put(fieldError.getField(), getMessage(fieldError));
			}
		}
		map.put(AJAX_FIELD_ERRORS, fieldErrorMap);

		List<String> objectErrors = new ArrayList<String>();
		for (ObjectError fieldError : ex.getGlobalErrors()) {
			objectErrors.add(getMessage(fieldError));
		}
		RequestUtils.addAjaxMessage(map, MessageType.ERROR, objectErrors);
		return map;
	}

	private String getMessage(ObjectError error) {
		String message = error.getDefaultMessage();
		String[] codes = error.getCodes();
		for (String code : codes) {
			String msg = RequestUtils.getApplicationMessage(code,
					error.getArguments());
			if (!StringUtils.equalsIgnoreCase(msg, code)) {
				message = msg;
				break;
			}
		}
		return message;
	}
}
