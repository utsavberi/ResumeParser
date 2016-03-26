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
	public PeriodDescriptionParser(Lexer experienceLexer){
		this.lexer = experienceLexer;
	}
	
	private String dateMonthStart="";
	private String dateMonthEnd = "";
	private String dateYearStart = "";
	private String dateYearEnd = "";
	private String description = "";
	
	public ArrayList<PeriodDescription> parse(){
		do{
			lexer.lex();
			checkCurrentTokenAndParse();
		}
		while(getCurrentTokenType() != TokenType.EOF);
		
		if(dateYearStart.isEmpty()==false ){
			LocalDate endDate = getDateFromFields(dateYearStart,dateMonthStart);
			LocalDate startDate = null;
			endDate = LocalDate.of(endDate.getYear(), 8, 31);
			
			experiences.add(new PeriodDescription(description,new DateRange(startDate,endDate)));
			resetAllFields();
		}
		
		return experiences;
	}

	private void checkCurrentTokenAndParse() {
		if(dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.PRESENT){
			Date date = new Date();
			dateYearEnd = Integer.toString(date.getYear()+1900);
			dateMonthEnd = monthIntToString(date.getMonth());
			return;
		}
		if(dateMonthStart.isEmpty() && getCurrentTokenType() == TokenType.MONTH_STRING){
			dateMonthStart = lexer.getCurrentToken().value;
		}
		else if(dateMonthStart.isEmpty() && getCurrentTokenType() == TokenType.MONTH_YEAR){
			dateMonthStart = lexer.getCurrentToken().value.split("[’'`]")[0];
			dateYearStart = getMonthFromMonthYear(lexer.getCurrentToken().value.split("[’'`]")[1]);
		}
		else if(dateYearStart.isEmpty() && getCurrentTokenType() == TokenType.YEAR){
			dateYearStart = lexer.getCurrentToken().value;
		}
		else if(dateMonthEnd.isEmpty() && getCurrentTokenType() == TokenType.MONTH_STRING){
			dateMonthEnd = lexer.getCurrentToken().value;
		}
		else if(dateMonthEnd.isEmpty() && getCurrentTokenType() == TokenType.MONTH_YEAR){
			dateMonthEnd = lexer.getCurrentToken().value.split("[’'`]")[0];
			dateYearEnd = getMonthFromMonthYear(lexer.getCurrentToken().value.split("[’'`]")[1]);
		}
		else if(dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.YEAR){
			dateYearEnd = lexer.getCurrentToken().value;
		}
		else if(getCurrentTokenType()==TokenType.ALPHA_NUMERIC){
			description+=lexer.getCurrentToken().value+" ";
		}
		
		else if( gotYearStartYearEndAndDescription() == true ){
			LocalDate startDate = getDateFromFields(dateYearStart,dateMonthStart);
			LocalDate endDate =  getDateFromFields(dateYearEnd,dateMonthEnd);
			
			if(endDateIsSmallerThanStartDate(startDate, endDate)){
				splitToTwoPeriodsAndAdd(startDate,endDate);
			}
			else{
				experiences.add(new PeriodDescription(description,new DateRange(startDate,endDate)));
			}
			resetAllFields();
			checkCurrentTokenAndParse();
		}
	}

	private String getMonthFromMonthYear(String string) {
		if(string.length()==4)return string;
		else if (string.length()==2){
			if(Integer.parseInt(string)>50){
				return "19"+string;
			}
			else{
				return "20"+string;
			}
			
		}
		else return "0000";
		
	}

	private void splitToTwoPeriodsAndAdd(LocalDate startDate,LocalDate endDate) {
		experiences.add(new PeriodDescription(description,new DateRange(null,startDate)));
		experiences.add(new PeriodDescription(description,new DateRange(null,endDate)));
	}

	private boolean endDateIsSmallerThanStartDate(LocalDate startDate, LocalDate endDate) {
		return endDate.compareTo(startDate) < 0;
	}

	private LocalDate getDateFromFields(String year,String month) {
		return LocalDate.of(Integer.parseInt(year),monthStringToInt(month),1);
	}

	private void resetAllFields() {
		dateMonthStart="";
		dateMonthEnd = "";
		dateYearStart = "";
		dateYearEnd = "";
		description = "";
	}

	private boolean gotYearStartYearEndAndDescription() {
		return !dateYearStart.isEmpty() && !dateYearEnd.isEmpty()	&& !description.isEmpty();
	}

	private String monthIntToString(int month) {
		String [] MONTHS = {"jan","feb","mar","apr","may","jun","jul","aug"
				,"sep","oct","nov","dec"};
		return MONTHS[month];
	}

	private int monthStringToInt(String dateMonthStart) {
		if(dateMonthStart.isEmpty()) return 1;
		switch(dateMonthStart.toLowerCase().substring(0, 3)){
			case "jan":return 1;
			case "feb": return 2;
			case "mar":return 3;
			case "apr":return 4;
			case "may":return 5;
			case "jun":return 6;
			case "jul":return 7;
			case "aug":return 8;
			case "sep":return 9;
			case "oct":return 10;
			case "nov":return 11;
			case "dec":return 12;
			default: return 1;
		}
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}
	
	//test
	public static void main(String arg[]) throws FileNotFoundException{
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(
						new FileInputStream("testData/educationDataUtsav")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		System.out.println("Size : "+periods.size());
	}
}
