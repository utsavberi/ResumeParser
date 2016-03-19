import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ResumeParser {
	int count = 0;
	Resume resume = new Resume();
	Lexer lexer ;
	
	public ResumeParser(Lexer lex){
		this.lexer = lex;
	}
	
	public void parse(){
		if(count++>20){
			System.out.println(resume);
			return;
			}
		lexer.lex();
		if(lexer.getCurrentToken().tokenType == TokenType.EOF){
			return;
		}

		 if(resume.name==null && lexer.getCurrentToken().tokenType == TokenType.NAME){
			lexer.lex(); //lex name token
			personalInfo();
		}
		 else if(resume.name == null){
			personalInfo();
		}
		 else if(resume.email==null && lexer.getCurrentToken().tokenType == TokenType.EMAIL){
			lexer.lex();
			parseEmail();
		}
		 else if(resume.email == null && lexer.getCurrentToken().tokenType == TokenType.EMAIL_BODY){
			parseEmail();
		}
		 else if(resume.phone == null && lexer.getCurrentToken().tokenType == TokenType.PHONE_HEADER){
			 lexer.lex();
			 parsePhone();
		 }
		 else if(resume.phone == null && lexer.getCurrentToken().tokenType== TokenType.PHONE){
			 parsePhone();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.URL){
			 lexer.lex();
			 parseUrl();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.URL_BODY){
			 parseUrl();
		 }
		parse();
		
	}

	private void parseUrl() {
		
		resume.links = lexer.getCurrentToken().value;		
		
	}

	private void parsePhone() {
		resume.phone = lexer.getCurrentToken().value;		
		
	}

	private void parseEmail() {
		resume.email = lexer.getCurrentToken().value;		
	}

	private void personalInfo() {
		resume.name = lexer.getCurrentToken().value;
		lexer.lex();
		resume.name +=lexer.getCurrentToken().value;
//		System.out.println("Name"+resume.name);
//		System.out.println(lexer.getCurrentToken().tokenType);
	}


	public static void main(String arg[]){
		try {
			ResumeParser parser = new ResumeParser(new Lexer(new FileInputStream("data/test")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
