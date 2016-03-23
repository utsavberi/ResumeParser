package main.resumeProcessor;

public class Token{
	private TokenType tokenType;
	String value;
	private boolean isHeader = false;
	public Token(String value, TokenType tokenType){
		this.value = value;
		this.tokenType = tokenType;
	}
	
	public Token(String value, TokenType tokenType,boolean isHeader){
		this(value, tokenType);
		this.isHeader = isHeader;
	}

	@Override
	public String toString(){
		return "{ type : "+getTokenType()+" , value : "+value+" }";
	}

	boolean isHeader() {
		return isHeader;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	
}