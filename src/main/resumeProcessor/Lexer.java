package main.resumeProcessor;

public interface Lexer {

	Token getCurrentToken();

	void lex();

}