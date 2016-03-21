package main;

import java.io.InputStream;

public class ExperienceLexer implements Lexer {
	private NLScanner scanner ;
	private Token token;
	public ExperienceLexer(InputStream stream){
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
		else if(word.toLowerCase().matches("jan(uary)?|feb(ruary)?"
				+ "|mar(ch)?|apr(il)?"
				+ "|may|jun(e)?|jul(y)?|aug(ust)?|sep(tember)?"
				+ "|oct(ober)?|nov(ember)?|dec(ember)?")){
			token = new Token(word, TokenType.MONTH_STRING);
		}
		else if(isNumeric(word) && word.length()==4){
			token = new Token(word,TokenType.YEAR);
		}
		
		else{
			token = new Token(word,TokenType.ALPHA_NUMERIC);
		}
		
		System.out.println("lexer "+getCurrentToken().getTokenType()+" :"+getCurrentToken().value);

	}
	private static boolean isNumeric(String str)
	{
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
