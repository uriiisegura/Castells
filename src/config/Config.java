package config;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Config {
	public final static DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public final static DateTimeFormatter LOCAL_DATETIME_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

	public static LocalDate parseDate(String date) throws ParseException {
		if (date.equals("NULL") || date.equals("null"))
			return null;
		return LocalDate.parse(date, LOCAL_DATE_FORMAT);
	}

	public static String parseDate(LocalDate date) {
		if (date == null)
			return "";
		return date.format(LOCAL_DATE_FORMAT);
	}

	public static ZonedDateTime parseDatetime(String dateTime) {
		if (dateTime.equals("NULL") || dateTime.equals("null"))
			return null;
		return ZonedDateTime.parse(dateTime, LOCAL_DATETIME_FORMAT);
	}

	public static String parseDateTime(ZonedDateTime dateTime) {
		if (dateTime == null)
			return "";
		return dateTime.format(LOCAL_DATETIME_FORMAT);
	}
}
