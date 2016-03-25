package main.resumeProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
			if(dateMonthStart.isEmpty() && getCurrentTokenType() == TokenType.MONTH_STRING){
				dateMonthStart = lexer.getCurrentToken().value;
			}
			else if(dateYearStart.isEmpty() && getCurrentTokenType() == TokenType.YEAR){
				dateYearStart = lexer.getCurrentToken().value;
			}
			else if(dateMonthEnd.isEmpty() && getCurrentTokenType() == TokenType.MONTH_STRING){
				dateMonthEnd = lexer.getCurrentToken().value;
			}
			else if(dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.YEAR){
				dateYearEnd = lexer.getCurrentToken().value;
			}
			else if(getCurrentTokenType()==TokenType.ALPHA_NUMERIC){
				description+=lexer.getCurrentToken().value+" ";
			}
			else if(dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.PRESENT){
				
				Date date = new Date();
				dateYearEnd = Integer.toString(date.getYear()+1900);
				dateMonthEnd = monthIntToString(date.getMonth());
				continue;
			}
			else if( gotYearStartYearEndAndDescription() == true ){
				LocalDate startDate = getDateFromFields(dateYearStart,dateMonthStart);
				LocalDate endDate =  getDateFromFields(dateYearEnd,dateMonthEnd);
				
				//end date is smaller than start date split to two records
				if(endDate.compareTo(startDate) < 0){
					endDate = startDate;
					startDate = null;
					endDate = LocalDate.of(endDate.getYear(), 8, 31);
					
					experiences.add(new PeriodDescription(getFirstLine(description),description,new DateRange(startDate,endDate)));
					shiftEndDateFieldsToStartDateFields();
					resetEndDateFieldsAndDescription();
				}
				else{
					experiences.add(new PeriodDescription(getFirstLine(description),description,new DateRange(startDate,endDate)));
					resetAllFields();
				}
			}
		}
		while(getCurrentTokenType() != TokenType.EOF);
		
		if(dateYearStart.isEmpty()==false){
			LocalDate endDate = getDateFromFields(dateYearStart,dateMonthStart);
			LocalDate startDate = null;
			endDate = LocalDate.of(endDate.getYear(), 8, 31);
			
			experiences.add(new PeriodDescription(getFirstLine(description),description,new DateRange(startDate,endDate)));
			resetAllFields();
		}
		
		return experiences;
	}

	private LocalDate getDateFromFields(String year,String month) {
		return LocalDate.of(Integer.parseInt(year),monthStringToInt(month),1);
	}

	private void shiftEndDateFieldsToStartDateFields() {
		dateMonthStart=dateMonthEnd;
		dateYearStart = dateYearEnd;
	}

	private void resetEndDateFieldsAndDescription() {
		dateMonthEnd = "";
		dateYearEnd = "";
		description = "";
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

	private String getFirstLine(String text) {
		text = text.replaceAll("^\\s+", "");		
		int index = text.indexOf('\n');
		if(index!=-1){
			return text.substring(0, index);
		}
		return text;
		
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
						new FileInputStream("testData/experienceData")));
		System.out.println(parser.parse());
	}
}
