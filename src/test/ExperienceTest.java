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

public class ExperienceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/1")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(3, periods.size());
		assertEquals(new DateRange(LocalDate.of(2008, 1, 30), LocalDate.of(2008, 6, 30)),
				periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2008, 6, 30), LocalDate.of(2011, 3, 30)),
				periods.get(1).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2011, 4, 30), now()), periods.get(2).getDateRange());
	}

	@Test
	public void test2() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/2")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(2, periods.size());
		assertEquals(new DateRange(LocalDate.of(2013, 2, 28), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2014, 2, 28), now()), periods.get(1).getDateRange());
	}

	private LocalDate now() {
		return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 30);
	}

	@Test
	public void test3() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/3")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(4, periods.size());
		assertEquals(new DateRange(LocalDate.of(2011, 8, 30), LocalDate.of(2013, 9, 30)),
				periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2010, 3, 30), LocalDate.of(2011, 6, 30)),
				periods.get(1).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2008, 2, 28), LocalDate.of(2009, 12, 30)),
				periods.get(2).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2006, 2, 28), LocalDate.of(2008, 12, 30)),
				periods.get(3).getDateRange());
	}

	@Test
	public void test4() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/4")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(6, periods.size());
		assertEquals(new DateRange(LocalDate.of(2011, 11, 30), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2010, 12, 30), LocalDate.of(2011, 10, 30)),
				periods.get(1).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2008, 6, 30), LocalDate.of(2010, 12, 30)),
				periods.get(2).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2007, 5, 30), LocalDate.of(2008, 6, 30)),
				periods.get(3).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2005, 5, 30), LocalDate.of(2007, 4, 30)),
				periods.get(4).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2002, 8, 30), LocalDate.of(2005, 3, 30)),
				periods.get(5).getDateRange());
	}

	@Test
	public void test5() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/5")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(8, periods.size());
	}

	@Test
	public void test6() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/6")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(3, periods.size());
		assertEquals(new DateRange(LocalDate.of(2011, 1, 30), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2008, 1, 30), LocalDate.of(2010, 10, 30)),
				periods.get(1).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2005, 2, 28), LocalDate.of(2008, 1, 30)),
				periods.get(2).getDateRange());
	}

	@Test
	public void test7() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/7")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(4, periods.size());
		assertEquals(new DateRange(LocalDate.of(2008, 7, 30), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2003, 11, 30), LocalDate.of(2008, 4, 30)),
				periods.get(1).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2002, 4, 30), LocalDate.of(2003, 7, 30)),
				periods.get(2).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2000, 6, 30), LocalDate.of(2001, 12, 30)),
				periods.get(3).getDateRange());
	}

	@Test
	public void test8() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/8")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(2, periods.size());
		assertEquals(new DateRange(LocalDate.of(2013, 1, 30), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2007, 4, 30), LocalDate.of(2013, 3, 30)),
				periods.get(1).getDateRange());
	}

	@Test
	public void test9() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/9")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println(periods);
		assertEquals(3, periods.size());
		assertEquals(new DateRange(LocalDate.of(2012, 8, 30), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2011, 10, 30), LocalDate.of(2012, 8, 30)),
				periods.get(1).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2011, 6, 30), LocalDate.of(2011, 9, 30)),
				periods.get(2).getDateRange());
	}

	@Test
	public void test10() throws FileNotFoundException {
		PeriodDescriptionParser parser = new PeriodDescriptionParser(
				new PeriodDescriptionLexer(new FileInputStream("testData/experience/10")));
		List<PeriodDescription> periods = parser.parse();
		System.out.println("19  " + periods);
		assertEquals(2, periods.size());
		assertEquals(new DateRange(LocalDate.of(2010, 11, 30), now()), periods.get(0).getDateRange());
		assertEquals(new DateRange(LocalDate.of(2008, 4, 30), LocalDate.of(2010, 7, 30)),
				periods.get(1).getDateRange());
	}

}
