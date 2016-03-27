package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.model.DateRange;
import main.model.PeriodDescription;
import main.resumeProcessor.PeriodDescriptionLexer;
import main.resumeProcessor.PeriodDescriptionParser;

public class EducationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(new PeriodDescriptionLexer(new FileInputStream("testData/education/1")));
		List<PeriodDescription> periods = parser.parse();
		assertEquals(1,periods.size());
		assertEquals(new DateRange(LocalDate.of(2006, 6, 30),LocalDate.of(2008, 6, 30)),periods.get(0).getDateRange());
		
	}
	
	@Test
	public void test2() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(
						new FileInputStream("testData/education/2")
						));
		List<PeriodDescription> periods = parser.parse();
		assertEquals(3,periods.size());
		assertEquals(new DateRange(null,LocalDate.of(2012, 6, 30)),periods.get(0).getDateRange());
		assertEquals(new DateRange(null,LocalDate.of(2010, 6, 30)),periods.get(1).getDateRange());
		assertEquals(new DateRange(null,LocalDate.of(2004, 6, 30)),periods.get(2).getDateRange());
		
	}
	
	@Test
	public void test3() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(
						new FileInputStream("testData/education/3")
						));
		List<PeriodDescription> periods = parser.parse();
		assertEquals(1,periods.size());
		assertEquals(new DateRange(null,LocalDate.of(1988, 11, 30)),periods.get(0).getDateRange());
	}

	@Test
	public void test4() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(
						new FileInputStream("testData/education/4")
						));
		List<PeriodDescription> periods = parser.parse();
		assertEquals(1,periods.size());
		assertEquals(new DateRange(null,LocalDate.of(2003, 6, 30)),periods.get(0).getDateRange());
	}

	@Test
	public void test5() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(
						new FileInputStream("testData/education/5")
						));
		List<PeriodDescription> periods = parser.parse();
		assertEquals(4,periods.size());
		assertEquals(new DateRange(null,LocalDate.of(2008, 6, 30)),periods.get(0).getDateRange());
		assertEquals(new DateRange(null,LocalDate.of(2006, 6, 30)),periods.get(1).getDateRange());
		assertEquals(new DateRange(null,LocalDate.of(2002, 6, 30)),periods.get(2).getDateRange());
		assertEquals(new DateRange(null,LocalDate.of(2000, 6, 30)),periods.get(3).getDateRange());
		System.out.println(periods);
	}
	
}
