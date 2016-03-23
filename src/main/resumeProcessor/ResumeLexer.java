package main.resumeProcessor;
import java.io.InputStream;

public class ResumeLexer implements Lexer {
	private NLScanner scanner ;
	private Token token;
	public ResumeLexer(InputStream stream){
		this.scanner = new NLScanner(stream);
		this.scanner.useDelimiter(":| ");
	}
	
	
	@Override
	public Token getCurrentToken(){
		return token;
	}
	
	@Override
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
		else if(word.equals("email")){
			token =  new Token(word,TokenType.EMAIL,true);
		}
		else if( word.matches("^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
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
		else if((isNumeric(word.replaceAll("[\\(\\\\-]", ""))) && word.length()>7 ){
			token = new Token(word,TokenType.PHONE);
		}
		else if(word.equalsIgnoreCase("education")){
			token = new Token(word,TokenType.EDUCATION,true);
		}
		else if(word.equalsIgnoreCase("experience"))
		{
			token = new Token(word,TokenType.EXPERIENCE,true);
		}
		else if(word.equals("Projects"))
		{
			token = new Token(word,TokenType.PROJECTS,true);
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
		
		System.out.println("resume lexer "+getCurrentToken().getTokenType()+" :"+getCurrentToken().value);
				
	}
	
	private static boolean isNumeric(String str)
	{
		  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
