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
		do {
			lexer.lex();
			checkCurrentTokenAndParse();

		} while (getCurrentTokenType() != TokenType.EOF);
		System.out.println(resume);
	}

	private void checkCurrentTokenAndParse() {
		if (getResume().getName() == null) {
			parseName();
		} else if (getCurrentTokenType() == TokenType.EMAIL) {
			parseEmail();
		} else if (getResume().getPhone() == null && (getCurrentTokenType() == TokenType.PHONE)) {
			parsePhone();
		} else if(getCurrentTokenType() == TokenType.ADDRESS){
			parseAddress();
		}else if (getCurrentTokenType() == TokenType.URL) {
			parseUrl();
		} else if (getCurrentTokenType() == TokenType.EDUCATION) {
			parseEducation();
		} else if (getCurrentTokenType() == TokenType.EXPERIENCE) {
			parseExperience();
		} else if (getCurrentTokenType() == TokenType.SKILLS) {
			parseSkills();
		}
	}

	private void parseAddress() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken()) {
			getResume().setAddress(getResume().getAddress() + lexer.getCurrentToken().value+" ");
			lexer.lex();
		}
		checkCurrentTokenAndParse();
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().tokenType;
	}

	private void parseSkills() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken()) {
			getResume().setSkillsString(getResume().getSkillsString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		checkCurrentTokenAndParse();
	}

	private void parseExperience() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken()) {
			getResume().setExperienceString(getResume().getExperienceString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		checkCurrentTokenAndParse();
	}

	private void parseEducation() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken()) {
			getResume().setEducationString(getResume().getEducationString() + lexer.getCurrentToken().value);
			lexer.lex();
		}
		checkCurrentTokenAndParse();
	}

	private void parseUrl() {
		skipHeader();
		getResume().setLinks(lexer.getCurrentToken().value);
	}

	private void parsePhone() {
		skipHeader();
		getResume().setPhone(lexer.getCurrentToken().value);
	}

	private void parseEmail() {
		skipHeader();
		getResume().setEmail(lexer.getCurrentToken().value);
	}

	private void parseName() {
		skipHeader();
		getResume().setName(lexer.getCurrentToken().value);
		lexer.lex();
		getResume().setName(getResume().getName() + " " + lexer.getCurrentToken().value);
	}

	private void skipHeader() {
		if (lexer.getCurrentToken().isHeader())
			lexer.lex();
	}

	private boolean isAlphaNumericOrNewLineToken() {
		return getCurrentTokenType() == TokenType.ALPHA || getCurrentTokenType() == TokenType.ALPHA_NUMERIC
				|| getCurrentTokenType() == TokenType.NUMERIC || getCurrentTokenType() == TokenType.NEW_LINE;
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
