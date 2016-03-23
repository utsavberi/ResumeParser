package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.PeriodDescriptionLexer;
import main.PeriodDescriptionParser;
import model.DateRange;
import model.PeriodDescription;

public class ExperienceParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void experienceCountTest() {
		try {
			PeriodDescriptionLexer lexer = new PeriodDescriptionLexer(new FileInputStream("testData/experienceData"));
			PeriodDescriptionParser parser = new PeriodDescriptionParser(lexer);
			System.out.println(parser.parse());
			assertEquals(2,parser.parse().size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void experienceDateRangesTest() {
		try {
			PeriodDescriptionLexer lexer = new PeriodDescriptionLexer(new FileInputStream("testData/experienceData"));
			PeriodDescriptionParser parser = new PeriodDescriptionParser(lexer);
			ArrayList<PeriodDescription> experiences = parser.parse();
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
