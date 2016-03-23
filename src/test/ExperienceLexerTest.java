package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;


import main.PeriodDescriptionLexer;
import main.TokenType;

public class ExperienceLexerTest {

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testNewLine() {
//		ExperienceLexer lexer = new ExperienceLexer(new ByteArrayInputStream("\n".getBytes()));
//		lexer.lex();
//		assertEquals(TokenType.NEW_LINE, lexer.getCurrentToken().getTokenType());
//	}
	
	@Test
	public void testMonth() {
		String input = "Jan feb mar apr may jun jul aug sep oct nov dec "
				+ "january february march april may jun july august september"
				+ " october november december";
		PeriodDescriptionLexer lexer = new PeriodDescriptionLexer(new ByteArrayInputStream(input.getBytes()));
		lexer.lex();
		while(lexer.getCurrentToken().getTokenType()!=TokenType.EOF){
			assertEquals(TokenType.MONTH_STRING, lexer.getCurrentToken().getTokenType());
			lexer.lex();
		}

	}
	
	@Test
	public void testYear() {
		String input = " 2015 2016 1992 1988 1800 2089 9999";
		PeriodDescriptionLexer lexer = new PeriodDescriptionLexer(new ByteArrayInputStream(input.getBytes()));
		lexer.lex();
		while(lexer.getCurrentToken().getTokenType()!=TokenType.EOF){
			assertEquals(TokenType.YEAR, lexer.getCurrentToken().getTokenType());
			lexer.lex();
		}

	}
	
	

}
