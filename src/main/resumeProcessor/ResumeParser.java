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
		ArrayList<PeriodDescription> projects = extractPeriodList(TokenType.PROJECTS);
		getResume().setProjects(projects);
		checkCurrentTokenAndParse();		
	}

	private ArrayList<PeriodDescription> extractPeriodList(TokenType tokenType) {
		String periods = "";
		while (isAlphaNumericToken() || getCurrentTokenType() ==  tokenType) {
			periods +=lexer.getCurrentToken().value + " ";
			lexer.lex();
		}
		return parsePeriods(periods);
	}

	private ArrayList<PeriodDescription> parsePeriods(String periods) {
		Lexer lexer = new PeriodDescriptionLexer(new ByteArrayInputStream(periods.getBytes()));
		PeriodDescriptionParser parser = new PeriodDescriptionParser(lexer);
		ArrayList<PeriodDescription> periodList =  parser.parse();
		return periodList;
	}

	private TokenType getCurrentTokenType() {
		return lexer.getCurrentToken().getTokenType();
	}

	private void parseSkills() {
		lexer.lex();
		String skillsStr = "";
		while (isAlphaNumericToken() || getCurrentTokenType() == TokenType.SKILLS) {
			skillsStr+=lexer.getCurrentToken().value + " ";
			lexer.lex();
		}
		List<String> skillsList = parseSkills(skillsStr);
		getResume().setSkills(skillsList);
		checkCurrentTokenAndParse();
	}

	private List<String> parseSkills(String skillsStr) {
		String [] skills = (skillsStr.split("[\\n,]"));
		List<String> skillsList = new ArrayList<String>();
		for(String skill: skills){
			skill = skill.trim();
			if(skill.isEmpty()==false){
				skillsList.add(skill);
			}
		}
		return skillsList;
	}

	private void parseExperience() {
		ArrayList<PeriodDescription> experiences =  extractPeriodList(TokenType.EXPERIENCE);
		getResume().setExperience(experiences);
		
		checkCurrentTokenAndParse();
	}

	private void parseEducation() {
		//give priority to "education" over academic
		if(!lexer.getCurrentToken().value.equalsIgnoreCase("education")
				&& !getResume().getEducation().isEmpty()){
			return;
		};
		lexer.lex();
		ArrayList<PeriodDescription> education =  extractPeriodList(TokenType.EDUCATION);
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
			ResumeParser parser = new ResumeParser(new ResumeLexer(new FileInputStream("testData/resume6")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Resume getResume() {
		return resume;
	}

}
