import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


enum TokenType{
	NAME, EMAIL, ADDRESS, URL, URL_BODY, PHONE, EMAIL_BODY, 
	ALPHA, NUMERIC, ALPHA_NUMERIC, NEW_LINE, EOF, PHONE_HEADER
}

class Token{
	TokenType tokenType;
	String value;
	public Token(String value, TokenType tokenType){
		this.value = value;
		this.tokenType = tokenType;
	}
	
	@Override
	public String toString(){
		return "{ type : "+tokenType+" , value : "+value+" }";
	}
}



public class Lexer {
	private NLScanner scanner ;
//	private String word = "" ;
	private Token token;
	public Lexer(InputStream stream){
		this.scanner = new NLScanner(stream);
		this.scanner.useDelimiter(":| ");
	}
	
	public Token getCurrentToken(){
		return token;
	}
	public void lex(){
		String word = scanner.next();
		
		if(word.equals("\n")){
//			System.out.println(">>>>>>>>>>>>>>>>");
			token = new Token(word, TokenType.NEW_LINE);
		}
		else if(word.equalsIgnoreCase("name")){
			token =  new Token(word,TokenType.NAME);
		}
		else if(word.equals("address")){
			token = new Token(word,TokenType.ADDRESS);
		}
		else if(word.equals("email")){
			token =  new Token(word,TokenType.EMAIL);
		}
		else if(word.contains("@")){
			token = new Token(word,TokenType.EMAIL_BODY);
		}
		else if(word.equals("web")||word.contains("url")){
			token = new Token(word,TokenType.URL);
		}
		else if(word.contains(".com")){
			token = new Token(word,TokenType.URL_BODY);
		}
		else if(word.contains("cell")||word.contains("mobile")||word.contains("phone")){
			token = new Token(word,TokenType.PHONE_HEADER);
		}
		else if((isNumeric(word)|| containsNumbers(word)) && word.length()>7 ){
			token = new Token(word,TokenType.PHONE);

		}
		else if(isNumeric(word)){
			token = new Token(word,TokenType.NUMERIC);
		}
		else if(isNumeric(word)==false){
			token = new Token(word,TokenType.ALPHA);
		}
		else{
			token = new Token(word,TokenType.ALPHA_NUMERIC);
		}
		
		System.out.println("lexer "+getCurrentToken().tokenType+" :"+getCurrentToken().value);
				
	}
	
	private boolean containsNumbers(String word) {
		for(char c : word.toCharArray()){
			if(c>='0'&&c<='9') return true;
		}
		return false;
	}

	private static boolean isNumeric(String str)
	{
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
