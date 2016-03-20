package main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ResumeParser {
	private Resume resume = new Resume();
	Lexer lexer;

	public ResumeParser(Lexer lex) {
		this.lexer = lex;
	}

	public void parse() {
		lexer.lex();
		if (getCurrentTokenType() == TokenType.EOF) {
			System.out.println(getResume());
			return;
		}
		if (getResume().name == null) {
			parseName();
		} else if (getResume().email == null 
				&& (getCurrentTokenType() == TokenType.EMAIL)) {
			parseEmail();
		} else if (getResume().phone == null 
				&& (getCurrentTokenType() == TokenType.PHONE)) {
			parsePhone();
		} else if (getCurrentTokenType() == TokenType.URL) {
			parseUrl();
		} else if (getCurrentTokenType() == TokenType.EDUCATION) {
			parseEducation();
		} else if (getCurrentTokenType() == TokenType.EXPERIENCE) {
			parseExperience();
		} else if (getCurrentTokenType() == TokenType.SKILLS) {
			parseSkills();
		}
		parse();
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().tokenType;
	}

	private void parseSkills() {
		lexer.lex();
		while (isAlphaOrAlphaNumericOrNewLineToken()) {
			getResume().skillsString += lexer.getCurrentToken().value + " ";
			lexer.lex();
		}
	}

	private boolean isAlphaOrAlphaNumericOrNewLineToken() {
		return getCurrentTokenType() == TokenType.ALPHA
				|| getCurrentTokenType() == TokenType.ALPHA_NUMERIC
				|| getCurrentTokenType() == TokenType.NUMERIC
				|| getCurrentTokenType() == TokenType.NEW_LINE;
	}

	private void parseExperience() {
		lexer.lex();
		while (isAlphaOrAlphaNumericOrNewLineToken()) {
			getResume().experienceString += lexer.getCurrentToken().value + " ";
			lexer.lex();
		}
	}

	private void parseEducation() {
		lexer.lex();
		while (isAlphaOrAlphaNumericOrNewLineToken()) {
			getResume().educationString += lexer.getCurrentToken().value.replaceAll("\\r?\n", " ") + " ";
			lexer.lex();
		}
	}

	private void parseUrl() {
		skipHeader();
		getResume().links = lexer.getCurrentToken().value;
	}

	private void skipHeader() {
		if(lexer.getCurrentToken().isHeader()) lexer.lex();
	}

	private void parsePhone() {
		skipHeader();
		getResume().phone = lexer.getCurrentToken().value;
	}

	private void parseEmail() {
		skipHeader();
		getResume().email = lexer.getCurrentToken().value;
	}

	private void parseName() {
		skipHeader();
		getResume().name = lexer.getCurrentToken().value;
		lexer.lex();
		getResume().name += " "+lexer.getCurrentToken().value;
	}

	public static void main(String arg[]) {
		try {
			ResumeParser parser = new ResumeParser(new Lexer(new FileInputStream("testData/test2")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Resume getResume() {
		return resume;
	}

}
