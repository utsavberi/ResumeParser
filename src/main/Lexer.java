package main;

public interface Lexer {

	Token getCurrentToken();

	void lex();

}