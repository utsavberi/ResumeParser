package main;
import java.io.InputStream;


enum TokenType{
	NAME, EMAIL_HEADER, ADDRESS, URL_HEADER, URL, PHONE, EMAIL, 
	ALPHA, NUMERIC, ALPHA_NUMERIC, NEW_LINE, EOF, PHONE_HEADER, 
	EDUCATION, EXPERIENCE, SKILLS
}





public class Lexer {
	private NLScanner scanner ;
	private Token token;
	public Lexer(InputStream stream){
		this.scanner = new NLScanner(stream);
		this.scanner.useDelimiter(":| ");
	}
	
	public Token getCurrentToken(){
		return token;
	}
	public void lex(){
		if(scanner.hasNext()==false){
			token = new Token("EOF",TokenType.EOF);
			return;
		}
		String word = scanner.next();
		
		if(word.equals("\n")){
			token = new Token(word, TokenType.NEW_LINE);
		}
		else if(word.equalsIgnoreCase("name")){
			token =  new Token(word,TokenType.NAME,true);
		}
		else if(word.equals("address")){
			token = new Token(word,TokenType.ADDRESS);
		}
		else if(word.equals("email")){
			token =  new Token(word,TokenType.EMAIL,true);
		}
		else if(word.contains("@")){
			token = new Token(word,TokenType.EMAIL);
		}
		else if(word.equals("web")||word.contains("url")){
			token = new Token(word,TokenType.URL,true);
		}
		else if(word.contains(".com")){
			token = new Token(word,TokenType.URL);
		}
		else if(word.contains("cell")||word.contains("mobile")||word.contains("phone")){
			token = new Token(word,TokenType.PHONE,true);
		}
		else if((isNumeric(word)|| containsNumbers(word)) && word.length()>7 ){
			token = new Token(word,TokenType.PHONE);

		}
		else if(word.equalsIgnoreCase("education")){
			token = new Token(word,TokenType.EDUCATION,true);
		}
		else if(word.equalsIgnoreCase("experience"))
		{
			token = new Token(word,TokenType.EXPERIENCE,true);
		}
		else if(word.equalsIgnoreCase("skill")||word.equalsIgnoreCase("skills")||word.equalsIgnoreCase("keywords")){
			token = new Token(word,TokenType.SKILLS,true);
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
