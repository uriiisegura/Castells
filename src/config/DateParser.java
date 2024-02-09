package config;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateParser {
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
}
