package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExperienceParser {
	Lexer lexer;
	ArrayList<Experience> experiences = new ArrayList<>();
	public ExperienceParser(Lexer experienceLexer){
		this.lexer = experienceLexer;
	}
	
	public ArrayList<Experience> parse(){
		String dateMonthStart="";
		String dateMonthEnd = "";
		String dateYearStart = "";
		String dateYearEnd = "";
		String description = "";
				
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
			else if(!dateMonthStart.isEmpty() && !dateYearStart.isEmpty() && !dateMonthEnd.isEmpty()
					&& !dateYearEnd.isEmpty() && !description.isEmpty() ){
				LocalDate startDate = LocalDate.of(Integer.parseInt(dateYearStart),monthStringToInt(dateMonthStart),1);
				LocalDate endDate = LocalDate.of(Integer.parseInt(dateYearEnd),monthStringToInt(dateMonthEnd),1);
				String companyName = getFirstLine(description);
				experiences.add(new Experience(companyName,description,new DateRange(startDate,endDate)));
				dateMonthStart="";
				dateMonthEnd = "";
				dateYearStart = "";
				dateYearEnd = "";
				description = "";
			}
		}
		while(getCurrentTokenType() != TokenType.EOF);
		return experiences;
	}

	private String getFirstLine(String text) {
		text = text.replaceAll("^\\s+", "");		
		System.out.println("gfl "+ text+">>>");
		int index = text.indexOf('\n');
		if(index!=-1){
			return text.substring(0, index);
		}
		return text;
		
	}

	private int monthStringToInt(String dateMonthStart) {
		System.out.println("got month str "+dateMonthStart);
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
			default: return 0;
		}
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}
}
