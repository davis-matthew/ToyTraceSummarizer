package engine.tokens;

import java.util.ArrayList;

import engine.Variable;

public class WriteToken extends AccessToken {

	public ArrayList<Variable> readComponents = new ArrayList<Variable>();
	
	public WriteToken(int lineNumber, String rawValue, Variable v, ArrayList<Variable> reads) {
		super(lineNumber, rawValue, true, v);
		type = TokenType.WRITE;
		readComponents = reads;
	}


}
