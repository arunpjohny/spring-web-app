package com.arunpjohny.core.spring.web.servlet.view;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.arunpjohny.core.jackson.JacksonUtils;

public class JSONView implements View {
	private Object object;

	public JSONView(Object value) {
		this.object = value;
	}

	public void render(@SuppressWarnings("rawtypes") Map map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (object != null) {
			JacksonUtils.getInstance().writeValue(object, response.getWriter());
		}
	}

	public String getContentType() {
		return "text/json";
	}

}