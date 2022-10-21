package engine;

import java.util.ArrayList;

import engine.tokens.AccessToken;
import engine.tokens.CreateToken;
import engine.tokens.MapToken;

public class Variable {
	String name;
	
	CreateToken createdAt;
	
	public ArrayList<AccessToken> accesses = new ArrayList<AccessToken>();
	
	public String getName() { return name; }
	
	public Variable(String name) {
		this.name = name;
	}
	
	public void setCreatedLoc(CreateToken loc) {
		createdAt = loc;
	}
}
