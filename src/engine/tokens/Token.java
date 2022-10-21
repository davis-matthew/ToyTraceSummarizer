package engine.tokens;

public class Token {
	String tokenStr;
	int lineNumber;
	
	TokenType type;
	
	public Token() {
		//For temporary analysis tokens
	}
	public Token(String rawValue) {
		tokenStr = rawValue;
	}
	public Token(int lineNumber, String rawValue) {
		this.lineNumber = lineNumber;
		tokenStr = rawValue;
	}
	
	public TokenType getType() { return type; }
	
	public String toString() {
		return tokenStr;
	}
	
	public int getLineNum() {
		return lineNumber;
	}
}
