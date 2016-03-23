package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import model.DateRange;
import model.PeriodDescription;


public class PeriodDescriptionParser {
	Lexer lexer;
	ArrayList<PeriodDescription> experiences = new ArrayList<>();
	public PeriodDescriptionParser(Lexer experienceLexer){
		this.lexer = experienceLexer;
	}
	
	public ArrayList<PeriodDescription> parse(){
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
			else if(dateYearEnd.isEmpty() && getCurrentTokenType() == TokenType.PRESENT){
				String [] MONTHS = {"jan","feb","mar","apr","may","jun","jul","aug"
						,"sep","oct","nov","dec"};
				Date date = new Date();
				dateYearEnd = Integer.toString(date.getYear()+1900);
				dateMonthEnd = MONTHS[date.getMonth()];
			}
			else if( !dateYearStart.isEmpty() 
					&& !dateYearEnd.isEmpty() 
					&& !description.isEmpty() ){
				LocalDate startDate = LocalDate.of(Integer.parseInt(dateYearStart),monthStringToInt(dateMonthStart),1);
				
				LocalDate endDate = dateYearEnd.isEmpty()?startDate: LocalDate.of(Integer.parseInt(dateYearEnd),monthStringToInt(dateMonthEnd),1);
				if(endDate.compareTo(startDate) < 0){
					endDate = startDate;
					startDate = null;
					endDate = LocalDate.of(endDate.getYear(), 8, 31);
					String companyName = getFirstLine(description);
					experiences.add(new PeriodDescription(companyName,description,new DateRange(startDate,endDate)));
					dateMonthStart=dateMonthEnd;
					dateMonthEnd = "";
					dateYearStart = dateYearEnd;
					dateYearEnd = "";
					description = "";
				}
				else{
					String companyName = getFirstLine(description);
					experiences.add(new PeriodDescription(companyName,description,new DateRange(startDate,endDate)));
					dateMonthStart="";
					dateMonthEnd = "";
					dateYearStart = "";
					dateYearEnd = "";
					description = "";
				}
			}
		}
		while(getCurrentTokenType() != TokenType.EOF);
		if(dateYearStart.isEmpty()==false){
			LocalDate endDate = LocalDate.of(Integer.parseInt(dateYearStart),monthStringToInt(dateMonthStart),1);
			
			LocalDate startDate = null;//dateYearEnd.isEmpty()?startDate: LocalDate.of(Integer.parseInt(dateYearEnd),monthStringToInt(dateMonthEnd),1);
			endDate = LocalDate.of(endDate.getYear(), 8, 31);

			String companyName = getFirstLine(description);
			experiences.add(new PeriodDescription(companyName,description,new DateRange(startDate,endDate)));
			dateMonthStart="";
			dateMonthEnd = "";
			dateYearStart = "";
			dateYearEnd = "";
			description = "";
		}
		return experiences;
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
			default: return 0;
		}
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}
	
	public static void main(String arg[]) throws FileNotFoundException{
		//ResumeParser parser = new ResumeParser(new ResumeLexer(new FileInputStream("testData/test2")));
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(
						new FileInputStream("testData/experienceData")));
		System.out.println(parser.parse());
	}
}
