package main.resumeProcessor;

import java.io.InputStream;

public class PeriodDescriptionLexer implements Lexer {
	private NLScanner scanner ;
	private Token token;
	public PeriodDescriptionLexer(InputStream stream){
		this.scanner = new NLScanner(stream);
		this.scanner.useDelimiter(" |-");
	}
	
	@Override
	public Token getCurrentToken() {
		return token;
	}

	@Override
	public void lex() {
		if(scanner.hasNext() == false){
			token = new Token("EOF",TokenType.EOF);
			return;
		}
		
		String word = scanner.next();
		
		if(word.equals("\n")){
			token = new Token(word, TokenType.NEW_LINE);
		}
		else if(word.toLowerCase().matches(".*jan(uary)?.*|feb(ruary)?"
				+ "|mar(ch)?|apr(il)?"
				+ "|may|jun(e)?|jul(y)?|aug(ust)?|sep(tember)?"
				+ "|.*oct(ober)?.*|nov(ember)?|dec(ember)?")){
			token = new Token(word.replaceAll("[^a-zA-Z]+", ""), TokenType.MONTH_STRING);
		}
		else if(word.matches("[^a-zA-Z0-9]?\\d{4}[^a-zA-Z0-9]?")){
			token = new Token(word.replaceAll("[^a-zA-Z0-9]", ""),TokenType.YEAR);
		}
		else if(word.toLowerCase().matches(".*present.*")){
			token = new Token("present", TokenType.PRESENT);
		}
		else if(isNumeric(word) && word.length()==4 ){
			token = new Token(word,TokenType.YEAR);
		}
		
		else{
			token = new Token(word,TokenType.ALPHA_NUMERIC);
		}
		
		System.out.println("period lexer "+getCurrentToken().getTokenType()+" :"+getCurrentToken().value);

	}
	private static boolean isNumeric(String str)
	{
		  return str.matches("\\d+");  //match a number with optional '-' and decimal.
	}
}
