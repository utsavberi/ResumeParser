package test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import main.Lexer;
import main.Resume;
import main.ResumeParser;

public class ResumeParserTest {
	ResumeParser parser;
	@Before
	public void setUp() throws Exception {
		try {
			parser = new ResumeParser(new Lexer(new FileInputStream("testData/test")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testName() {
		Resume resume = parser.getResume();
		assertEquals(resume.name,"Utsav Beri");
	}
	
	@Test
	public void testEmail() {
		Resume resume = parser.getResume();
		assertEquals(resume.email,"utsavber@buffalo.edu");
	}
	
	@Test
	public void testPhone(){
		Resume resume = parser.getResume();
		assertEquals(resume.phone,"716-495-8194");
	}
	
	

}
