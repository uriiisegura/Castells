package config;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {
	private final static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static LocalDate parseLocalDate(Date date) {
		if (date == null)
			return null;
		return date.toLocalDate();
	}

	public static ZonedDateTime parseZonedDateTime(Timestamp timestamp) {
		if (timestamp == null)
			return null;
		// TODO:
		return timestamp.toLocalDateTime().atZone(ZoneOffset.UTC);
	}

	public static String parseLocalDate(java.util.Date date) {
		if (date == null)
			return "";
		return dateFormatter.format(date);
	}

	public static String parseLocalDate(LocalDate date) {
		if (date == null)
			return "";
		return localDateFormatter.format(date);
	}
}
