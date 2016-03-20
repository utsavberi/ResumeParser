package main;

public class Token{
	TokenType tokenType;
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
		return "{ type : "+tokenType+" , value : "+value+" }";
	}

	boolean isHeader() {
		return isHeader;
	}
}