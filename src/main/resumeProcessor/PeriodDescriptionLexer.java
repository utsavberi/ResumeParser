package main.resumeProcessor;

import java.io.InputStream;
import java.util.Scanner;

public class PeriodDescriptionLexer implements Lexer {
	private Scanner scanner;
	private Token prevToken = new Token("", TokenType.ALPHA_NUMERIC);
	private Token token = new Token("", TokenType.ALPHA_NUMERIC);;

	public PeriodDescriptionLexer(InputStream stream) {
		this.scanner = new Scanner(stream);
		this.scanner.useDelimiter("\\s+|-|–");
	}

	@Override
	public Token getCurrentToken() {
		return token;
	}

	@Override
	public void lex() {
		prevToken = token;
		if (scanner.hasNext() == false) {
			token = new Token("EOF", TokenType.EOF);
			return;
		}

		String word = scanner.next();

		if (word.toLowerCase()
				.matches(".?jan(uary)?[’'`]\\d{2,4}.?" + "|.?feb(ruary)?[’'`]\\d{2,4}.?" + "|.?mar(ch)?[’'`]\\d{2,4}.?"
						+ "|.?apr(il)?[’'`]\\d{2,4}.?" + "|.?may[’'`]\\d{2,4}.?" + "|.?jun(e)?[’'`]\\d{2,4}.?"
						+ "|.?jul(y)?[’'`]\\d{2,4}.?" + "|.?aug(ust)?[’'`]\\d{2,4}.?" + "|.?sep(tember)?[’'`]\\d{2,4}.?"
						+ "|.?oct(ober)?[’'`]\\d{2,4}.?" + "|.?nov(ember)?[’'`]\\d{2,4}.?"
						+ "|.?dec(ember)?[’'`]\\d{2,4}.?")) {
			token = new Token(word.replaceAll("[()-]", ""), TokenType.MONTH_YEAR);
		}
		// 80 - 99
		else if (prevToken.getTokenType() == TokenType.MONTH_STRING && word.matches("[89][0-9][)(-]?")) {
			word = word.replaceAll("[()-]", "");
			word = "19" + word;
			token = new Token(word, TokenType.YEAR);
		}
		// 00-19
		else if (prevToken.getTokenType() == TokenType.MONTH_STRING && word.matches("[01][0-9][)(-]?")) {
			word = word.replaceAll("[()-]", "");
			word = "20" + word;
			token = new Token(word, TokenType.YEAR);
		}
		// 02/08/13
		else if (word.matches("\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}")) {
			token = new Token(word, TokenType.DATE);
		}
		// jan or january
		else if (word.toLowerCase()
				.matches(".?jan(uary)?.?|.?feb(ruary)?.?" + "|.?mar(ch)?.?|.?apr(il)?.?"
						+ "|.?may.?|.?jun(e)?.?|.?jul(y)?.?|.?aug(ust)?.?|.?sep(tember)?.?"
						+ "|.?oct(ober)?.?|.?nov(ember)?.?|.?dec(ember)?.?")) {
			token = new Token(word.replaceAll("[^a-zA-Z]+", ""), TokenType.MONTH_STRING);
		} else if (word.matches("[^a-zA-Z0-9]?\\d{4}[^a-zA-Z0-9]?")
				&& Integer.parseInt(word.replaceAll("[^0-9]", "")) < 3000) {
			token = new Token(word.replaceAll("[^a-zA-Z0-9]", ""), TokenType.YEAR);
		} else if (word.toLowerCase().matches("[^a-zA-Z0-9]?till[^a-zA-Z0-9]?")
				|| word.toLowerCase().matches("[^a-zA-Z0-9]?present[^a-zA-Z0-9]?")
				|| word.toLowerCase().matches("[^a-zA-Z0-9]?since[^a-zA-Z0-9]?")) {
			token = new Token("present", TokenType.PRESENT);
		} else {
			token = new Token(word, TokenType.ALPHA_NUMERIC);
		}

		System.out.println("period lexer " + getCurrentToken().getTokenType() + " :" + getCurrentToken().value);

	}

	private static boolean isNumeric(String str) {
		return str.matches("\\d+"); // match a number with optional '-' and
									// decimal.
	}
}
