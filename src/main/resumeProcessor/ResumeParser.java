package main.resumeProcessor;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import main.model.PeriodDescription;
import main.model.Resume;

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
		if (getCurrentTokenType() == TokenType.EMAIL) {
			parseEmail();
		} else if ((getCurrentTokenType() == TokenType.PHONE)) {
			parsePhone();
		}  else if (getCurrentTokenType() == TokenType.URL) {
			parseUrl();
		} else if (getCurrentTokenType() == TokenType.EDUCATION) {
			parseEducation();
		} else if (getCurrentTokenType() == TokenType.EXPERIENCE) {
			parseExperience();
		} else if (getCurrentTokenType() == TokenType.PROJECTS) {
			parseProjects();
		} else if (getCurrentTokenType() == TokenType.SKILLS) {
			parseSkills();
		}
		else if (getResume().getName() == null && getCurrentTokenType() == TokenType.ALPHA ) {
			parseName();
		} 
	}

	private void parseProjects() {
		lexer.lex();
		while (isAlphaNumericToken() || getCurrentTokenType() == TokenType.PROJECTS ) {
			getResume().setProjectString(getResume().getProjectString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		
		Lexer lexer = new PeriodDescriptionLexer(new ByteArrayInputStream(getResume().getProjectString().getBytes()));
		PeriodDescriptionParser parser = new PeriodDescriptionParser(lexer);
		ArrayList<PeriodDescription> projects =  parser.parse();
		getResume().setProjects(projects);
		
		checkCurrentTokenAndParse();		
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}

	private void parseSkills() {
		lexer.lex();
		while (isAlphaNumericToken() || getCurrentTokenType() == TokenType.SKILLS) {
			getResume().setSkillsString(getResume().getSkillsString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		String [] skills = (getResume().getSkillsString().split("[\\n,]"));
		List<String> skillsList = new ArrayList<String>();
		for(String skill: skills){
			skill = skill.trim();
			if(skill.isEmpty()==false){
				skillsList.add(skill);
			}
		}
		getResume().setSkills(skillsList);
		checkCurrentTokenAndParse();
	}

	private void parseExperience() {
		lexer.lex();
		while (isAlphaNumericToken() || getCurrentTokenType() == TokenType.EXPERIENCE ) {
			getResume().setExperienceString(getResume().getExperienceString() + lexer.getCurrentToken().value + " ");
			lexer.lex();
		}
		
		System.out.println("parse experience ends at "+getCurrentTokenType());
		Lexer experienceLexer = new PeriodDescriptionLexer(new ByteArrayInputStream(getResume().getExperienceString().getBytes()));
		PeriodDescriptionParser experienceParser = new PeriodDescriptionParser(experienceLexer);
		ArrayList<PeriodDescription> experiences =  experienceParser.parse();
		getResume().setExperience(experiences);
		
		checkCurrentTokenAndParse();
	}

	private void parseEducation() {
		lexer.lex();
		while (isAlphaNumericToken()|| getCurrentTokenType() == TokenType.EDUCATION) {
			getResume().setEducationString(getResume().getEducationString() + lexer.getCurrentToken().value+" ");
			lexer.lex();
		}
		
		System.out.println("parse education ends at "+getCurrentTokenType());
		Lexer educationLexer = new PeriodDescriptionLexer(new ByteArrayInputStream(getResume().getEducationString().getBytes()));
		PeriodDescriptionParser periodDescriptionParser = new PeriodDescriptionParser(educationLexer);
		ArrayList<PeriodDescription> education =  periodDescriptionParser.parse();
		getResume().setEducation(education);
		
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

	private boolean isAlphaNumericToken() {
		return getCurrentTokenType() == TokenType.ALPHA || getCurrentTokenType() == TokenType.ALPHA_NUMERIC
				|| getCurrentTokenType() == TokenType.NUMERIC || getCurrentTokenType() == TokenType.NEW_LINE ;
	}

	public static void main(String arg[]) {
		try {
			ResumeParser parser = new ResumeParser(new ResumeLexer(new FileInputStream("testData/resume3")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Resume getResume() {
		return resume;
	}

}
