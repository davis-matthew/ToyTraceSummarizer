package engine.tokens;

import java.util.ArrayList;

import engine.Variable;

public class CreateToken extends Token {
	
	ArrayList<Variable> created;
	
	public CreateToken(int lineNumber, String raw, ArrayList<Variable> newVars) {
		super(lineNumber, raw);
		created = newVars;
	}
}
