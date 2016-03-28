package main.resumeProcessor;

import java.time.LocalDate;
import java.util.Date;

public class CalendarUtils {
	public static LocalDate getDateFromYearAndMonthString(String year, String month) {
		int yearInt = Integer.parseInt(year);
		int monthInt = monthStringToInt(month);
		return LocalDate.of(yearInt, monthInt, monthInt != 2 ? 30 : 28);
	}

	public static String extractMonthFromDate(Date date) {
		return monthIntToString(date.getMonth());
	}

	public static String extractYearFromDate(Date date) {
		return Integer.toString(date.getYear() + 1900);
	}

	public static String getMonthFromMonthYear(String string) {
		if (string.length() == 4)
			return string;
		else if (string.length() == 2) {
			if (Integer.parseInt(string) > 50) {
				return "19" + string;
			} else {
				return "20" + string;
			}

		} else
			return "0000";
	}

	public static String monthIntToString(int month) {
		String[] MONTHS = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
		return MONTHS[month];
	}

	public static int monthStringToInt(String dateMonthStart) {
		if (dateMonthStart.isEmpty())
			return 6;
		switch (dateMonthStart.toLowerCase().substring(0, 3)) {
		case "jan":
			return 1;
		case "feb":
			return 2;
		case "mar":
			return 3;
		case "apr":
			return 4;
		case "may":
			return 5;
		case "jun":
			return 6;
		case "jul":
			return 7;
		case "aug":
			return 8;
		case "sep":
			return 9;
		case "oct":
			return 10;
		case "nov":
			return 11;
		case "dec":
			return 12;
		default:
			return 1;
		}
	}
}
