package main;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ResumeParser {
	private Resume resume = new Resume();
	Lexer lexer;

	public ResumeParser(Lexer lex) {
		this.lexer = lex;
	}

	public Resume parse() {
		do {
			lexer.lex();
			checkCurrentTokenAndParse();

		} while (getCurrentTokenType() != TokenType.EOF);
		System.out.println(resume);
		return resume;
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
		return lexer.getCurrentToken().getTokenType();
	}

	private void parseSkills() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken() || getCurrentTokenType() == TokenType.SKILLS) {
			getResume().setSkillsString(getResume().getSkillsString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		checkCurrentTokenAndParse();
	}

	private void parseExperience() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken() || getCurrentTokenType() == TokenType.EXPERIENCE ) {
			getResume().setExperienceString(getResume().getExperienceString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		System.out.println("parse experience ends at "+getCurrentTokenType());
		Lexer experienceLexer = new ExperienceLexer(new ByteArrayInputStream(getResume().getExperienceString().getBytes()));
		ExperienceParser experienceParser = new ExperienceParser(experienceLexer);
		ArrayList<Experience> experiences =  experienceParser.parse();
		getResume().setExperience(experiences);
		
		checkCurrentTokenAndParse();
	}

	private void parseEducation() {
		lexer.lex();
		while (isAlphaNumericOrNewLineToken()|| getCurrentTokenType() == TokenType.EDUCATION) {
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
			ResumeParser parser = new ResumeParser(new ResumeLexer(new FileInputStream("testData/test")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Resume getResume() {
		return resume;
	}

}
