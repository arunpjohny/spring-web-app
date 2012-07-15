package com.arunpjohny.core.jackson.serializer;

import static com.arunpjohny.core.DateUtils.ISO_8601;
import static com.arunpjohny.core.DateUtils.formatDate;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateISO8601Serializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		if (value != null) {
			jgen.writeString(formatDate(value, ISO_8601));
		}
	}

}
