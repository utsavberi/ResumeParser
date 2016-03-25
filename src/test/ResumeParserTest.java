package test;

import static org.junit.Assert.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import main.model.Resume;
import main.resumeProcessor.ResumeLexer;
import main.resumeProcessor.ResumeParser;

public class ResumeParserTest {
	ResumeParser parser;
	@Before
	public void setUp() throws Exception {
		try {
			parser = new ResumeParser(new ResumeLexer(new FileInputStream("testData/test")));
			parser.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testName() {
		Resume resume = parser.getResume();
		assertEquals("Utsav Beri",resume.getName());
	}
	
	@Test
	public void testEmail() {
		Resume resume = parser.getResume();
		assertEquals("utsavber@buffalo.edu",resume.getEmail());
	}
	
	@Test
	public void testPhone(){
		Resume resume = parser.getResume();
		assertEquals("7164958194",resume.getPhone());
	}
	
	@Test
	public void testEducationNotEmpty(){
		Resume resume = parser.getResume();
		assertEquals( 3,resume.getEducation().size());
	}
	
	@Test
	public void testSkillsNotEmpty(){
		Resume resume = parser.getResume();
		assertEquals(26,resume.getSkills().size());
	}
	
	@Test
	public void testExperienceNotEmpty(){
		Resume resume = parser.getResume();
		assertEquals(2,resume.getExperience().size());
	}
	
	

}
