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

		lexer.lex();
		
		if(lexer.getCurrentToken().tokenType == TokenType.EOF){
			System.out.println(resume);
			return;
		}

		 if(resume.name==null && lexer.getCurrentToken().tokenType == TokenType.NAME){
			lexer.lex(); //lex name token
			parseName();
		}
		 else if(resume.name == null){
			parseName();
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
		 else if(lexer.getCurrentToken().tokenType == TokenType.EDUCATION_HEADER){
			 lexer.lex();
			 parseEducation();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.EXPERIENCE_HEADER){
			 lexer.lex();
			 parseExperience();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.SKILLS_HEADER){
			 lexer.lex();
			 parseSkills();
		 }
		parse();
		
	}

	private void parseSkills() {
		while(lexer.getCurrentToken().tokenType==TokenType.ALPHA ||
				lexer.getCurrentToken().tokenType==TokenType.ALPHA_NUMERIC ||
				lexer.getCurrentToken().tokenType==TokenType.NUMERIC || 
				lexer.getCurrentToken().tokenType == TokenType.NEW_LINE){
			resume.skillsString+=lexer.getCurrentToken().value+" ";
			lexer.lex();
		}
		if(lexer.getCurrentToken().tokenType == TokenType.EDUCATION_HEADER){
			 lexer.lex();
			 parseEducation();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.EXPERIENCE_HEADER){
			 lexer.lex();
			 parseExperience();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.SKILLS_HEADER){
			 lexer.lex();
			 parseSkills();
		 }
	}

	private void parseExperience() {
		while(lexer.getCurrentToken().tokenType==TokenType.ALPHA ||
				lexer.getCurrentToken().tokenType==TokenType.ALPHA_NUMERIC ||
				lexer.getCurrentToken().tokenType==TokenType.NUMERIC||
				lexer.getCurrentToken().tokenType == TokenType.NEW_LINE){
			resume.experienceString+=lexer.getCurrentToken().value+" ";
			lexer.lex();
		}
		if(lexer.getCurrentToken().tokenType == TokenType.EDUCATION_HEADER){
			 lexer.lex();
			 parseEducation();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.EXPERIENCE_HEADER){
			 lexer.lex();
			 parseExperience();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.SKILLS_HEADER){
			 lexer.lex();
			 parseSkills();
		 }
		
	}

	private void parseEducation() {
		System.out.println("in edu  >>>>>>>>>>>>>>>>>>>>>");
		while(lexer.getCurrentToken().tokenType==TokenType.ALPHA ||
				lexer.getCurrentToken().tokenType==TokenType.ALPHA_NUMERIC ||
				lexer.getCurrentToken().tokenType==TokenType.NUMERIC||
				lexer.getCurrentToken().tokenType == TokenType.NEW_LINE){
			resume.educationString+=lexer.getCurrentToken().value.replaceAll("\\r?\n", " ")+" ";
			lexer.lex();
		}
		if(lexer.getCurrentToken().tokenType == TokenType.EDUCATION_HEADER){
			 lexer.lex();
			 parseEducation();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.EXPERIENCE_HEADER){
			 lexer.lex();
			 parseExperience();
		 }
		 else if(lexer.getCurrentToken().tokenType == TokenType.SKILLS_HEADER){
			 lexer.lex();
			 parseSkills();
		 }
		
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

	private void parseName() {
		resume.name = lexer.getCurrentToken().value;
		lexer.lex();
		resume.name +=lexer.getCurrentToken().value;
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
