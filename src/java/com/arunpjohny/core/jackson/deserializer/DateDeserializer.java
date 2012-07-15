package com.arunpjohny.core.jackson.deserializer;

import static com.arunpjohny.core.DateUtils.ISO_8601;
import static com.arunpjohny.core.DateUtils.ISO_FORMAT;
import static com.arunpjohny.core.DateUtils.YMD_FORMAT;
import static com.arunpjohny.core.DateUtils.parseDate;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String text = jp.getText();
		Date date = null;
		if (StringUtils.isNotBlank(text)) {
			date = parseDate(text);
			if (date == null) {
				date = parseDate(text, YMD_FORMAT);
			}
			if (date == null) {
				date = parseDate(text, ISO_8601);
			}
			if (date == null) {
				date = parseDate(text, ISO_FORMAT);
			}
		}
		return date;
	}

}
