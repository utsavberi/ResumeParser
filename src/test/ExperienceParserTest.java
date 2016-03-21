package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.DateRange;
import main.Experience;
import main.ExperienceLexer;
import main.ExperienceParser;

public class ExperienceParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void experienceCountTest() {
		try {
			ExperienceLexer lexer = new ExperienceLexer(new FileInputStream("testData/experienceData"));
			ExperienceParser parser = new ExperienceParser(lexer);
			System.out.println(parser.parse());
			assertEquals(parser.parse().size(),2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void experienceDateRangesTest() {
		try {
			ExperienceLexer lexer = new ExperienceLexer(new FileInputStream("testData/experienceData"));
			ExperienceParser parser = new ExperienceParser(lexer);
			ArrayList<Experience> experiences = parser.parse();
			DateRange expected = new DateRange( LocalDate.of(2015,6,1), LocalDate.of(2015,7,1));
			assertTrue(expected.equals(experiences.get(0).getDateRange()));
			//Jan 2013 - Aug 2014
			expected = new DateRange( LocalDate.of(2013,1,1), LocalDate.of(2014,8,1));
			assertTrue(expected.equals(experiences.get(1).getDateRange()));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	

}
