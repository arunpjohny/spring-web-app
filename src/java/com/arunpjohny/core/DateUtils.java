package com.arunpjohny.core;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	public static final String YMD_TIME_ZONE = "yyyy-MM-dd HH:mm:ss.0 Z";

	public static final String ISO_FORMAT = "yyyy-MM-dd HH:mm:ss.0";

	public static final String DMY_FORMAT = "dd MMM yyyy";

	public static final String DATETIME_FORMAT = "dd MMM yyyy HH:mm:ss";

	public static final String DMY_HMS_FORMAT = "dd MMM yyyy hh:mm:ss a";

	public static final String TIME_FORMAT_12_HOURS = "h:mm:ss a";

	public static final String TIME_FORMAT_24_HOURS = "HH:mm";

	public static final String YMD_FORMAT = "yyyy-MM-dd";

	public static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	public static Date parseDate(String date) {
		return parseDate(date, DMY_FORMAT);
	}

	public static Date parseDateTime(String date) {
		return parseDate(date, DMY_HMS_FORMAT);
	}

	public static Date parseDate(String date, String format) {
		Date result = null;
		SimpleDateFormat df = new SimpleDateFormat(format);

		try {
			ParsePosition pp = new ParsePosition(0);
			result = StringUtils.isEmpty(date) ? null : df.parse(date, pp);
			if (date.length() != pp.getIndex()) {
				result = null;
			}
		} catch (Exception e) {
			// do nothing
		}
		return result;
	}

	public static String formatDate(Date d) {
		return formatDate(d, DMY_FORMAT);
	}

	public static String formatDate(Date d, String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		return d == null ? "" : f.format(d);
	}

	public static void blankTimePart(GregorianCalendar cal) {
		cal.set(GregorianCalendar.HOUR, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		cal.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
	}

	public static Date blankTimePart(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(GregorianCalendar.HOUR, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		cal.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
		return cal.getTime();
	}

}
