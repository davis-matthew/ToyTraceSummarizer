package engine.tokens;

import engine.Variable;

public class ReadToken extends AccessToken {

	public ReadToken(int lineNumber, String rawValue, Variable v) {
		super(lineNumber, rawValue, false, v);
		type = TokenType.READ;
	}

}
