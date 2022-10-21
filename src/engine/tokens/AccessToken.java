package engine.tokens;

import engine.Variable;

public class AccessToken extends Token {
	boolean write;
	boolean inKernel;
	Variable var;
	
	public AccessToken(int lineNumber, String rawValue, boolean write, Variable v) {
		super(lineNumber, rawValue);
		this.write = write;
		var = v;
	}
	
	public Variable getVar() { return var; }
	public boolean isRead() { return !write; }
}
