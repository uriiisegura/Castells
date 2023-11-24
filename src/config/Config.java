package config;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Config {
	public final static DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
}
