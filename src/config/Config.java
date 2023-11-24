package config;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Config {
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static LocalDate parseDate(String date) throws ParseException {
		if (date.equals("NULL") || date.equals("null"))
			return null;
		return LocalDate.parse(date, DATE_FORMAT);
	}

	public static String parseDate(LocalDate date) {
		if (date == null)
			return "";
		return date.format(DATE_FORMAT);
	}
}
