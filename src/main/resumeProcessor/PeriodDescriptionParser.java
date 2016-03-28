package main.resumeProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.model.DateRange;
import main.model.PeriodDescription;

public class PeriodDescriptionParser {
	Lexer lexer;
	ArrayList<PeriodDescription> experiences = new ArrayList<>();

	public PeriodDescriptionParser(Lexer experienceLexer) {
		this.lexer = experienceLexer;
	}

	private String dateMonthStart = "";
	private String dateMonthEnd = "";
	private String dateYearStart = "";
	private String dateYearEnd = "";
	private String description = "";

	public ArrayList<PeriodDescription> parse() {
		do {
			lexer.lex();
			checkCurrentTokenAndParse();
		} while (getCurrentTokenType() != TokenType.EOF);

		if (dateYearStart.isEmpty() == false) {
			LocalDate endDate = CalendarUtils.getDateFromYearAndMonthString(dateYearStart, dateMonthStart);
			experiences.add(new PeriodDescription(description, new DateRange(null, endDate)));
			resetAllFields();
		}

		return experiences;
	}

	private void checkCurrentTokenAndParse() {
		if (dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.PRESENT) {
			Date date = new Date();
			dateYearEnd = CalendarUtils.extractYearFromDate(date);
			dateMonthEnd = CalendarUtils.extractMonthFromDate(date);
			return;
		}
		if (dateMonthStart.isEmpty() && getCurrentTokenType() == TokenType.MONTH_STRING) {
			dateMonthStart = lexer.getCurrentToken().value;
		} else if (dateMonthStart.isEmpty() && getCurrentTokenType() == TokenType.MONTH_YEAR) {
			String monthYear[] = lexer.getCurrentToken().value.split("[’'`]");
			dateMonthStart = monthYear[0];
			dateYearStart = CalendarUtils.getMonthFromMonthYear(monthYear[1]);
		} else if (dateYearStart.isEmpty() && getCurrentTokenType() == TokenType.YEAR) {
			dateYearStart = lexer.getCurrentToken().value;
		} else if (dateYearStart.isEmpty() && getCurrentTokenType() == TokenType.DATE) {
			Date date = new Date(lexer.getCurrentToken().value);
			dateYearStart = CalendarUtils.extractYearFromDate(date);
			dateMonthStart = CalendarUtils.extractMonthFromDate(date);
		} else if (dateMonthEnd.isEmpty() && getCurrentTokenType() == TokenType.MONTH_STRING) {
			dateMonthEnd = lexer.getCurrentToken().value;
		} else if (dateMonthEnd.isEmpty() && getCurrentTokenType() == TokenType.MONTH_YEAR) {
			String monthYear[] = lexer.getCurrentToken().value.split("[’'`]");
			dateMonthEnd = monthYear[0];
			dateYearEnd = CalendarUtils.getMonthFromMonthYear(monthYear[1]);
		} else if (dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.YEAR) {
			dateYearEnd = lexer.getCurrentToken().value;
		} else if (dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.DATE) {
			Date date = new Date(lexer.getCurrentToken().value);
			dateYearEnd = CalendarUtils.extractYearFromDate(date);
			dateMonthEnd = CalendarUtils.extractMonthFromDate(date);
		} else if (getCurrentTokenType() == TokenType.ALPHA_NUMERIC) {
			description += lexer.getCurrentToken().value + " ";
		}

		else if (gotYearStartYearEndAndDescription() == true) {
			LocalDate startDate = CalendarUtils.getDateFromYearAndMonthString(dateYearStart, dateMonthStart);
			LocalDate endDate = CalendarUtils.getDateFromYearAndMonthString(dateYearEnd, dateMonthEnd);

			if (endDateIsSmallerThanStartDate(startDate, endDate)) {
				splitToTwoPeriodsAndAdd(startDate, endDate);
			} else {
				experiences.add(new PeriodDescription(description, new DateRange(startDate, endDate)));
			}
			resetAllFields();
			checkCurrentTokenAndParse();
		}
	}

	private void splitToTwoPeriodsAndAdd(LocalDate startDate, LocalDate endDate) {
		experiences.add(new PeriodDescription(description, new DateRange(null, startDate)));
		experiences.add(new PeriodDescription(description, new DateRange(null, endDate)));
	}

	private boolean endDateIsSmallerThanStartDate(LocalDate startDate, LocalDate endDate) {
		return endDate.compareTo(startDate) < 0;
	}

	private void resetAllFields() {
		dateMonthStart = "";
		dateMonthEnd = "";
		dateYearStart = "";
		dateYearEnd = "";
		description = "";
	}

	private boolean gotYearStartYearEndAndDescription() {
		return !dateYearStart.isEmpty() && !dateYearEnd.isEmpty() && !description.isEmpty();
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}

	// test
	public static void main(String arg[]) throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/education/3")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		System.out.println("Size : " + periods.size());
	}
}
