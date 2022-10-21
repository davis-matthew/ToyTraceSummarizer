package engine.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import engine.Trace;
import engine.Variable;
import engine.tokens.AccessToken;
import engine.tokens.ReadToken;
import engine.tokens.Token;
import engine.tokens.TokenType;
import engine.tokens.WriteToken;

public class SummaryPass {

	public static final void run() {
		//generateSections(); If you wanted more complicated sections or rules to specifically create / insert sections
		
		summarizeSections();
		
		//removeSections(); If you wanted to create temporary sections during analysis and then remove them
	}

	

	/*private static final void generateSections() {
		
		
	}*/

	private static final void summarizeSections() {
		for(int i = 0; i< Trace.trace.size(); i ++) {
			Token line = Trace.trace.get(i);
			if(line.getType() == TokenType.SECTION) {
				int j = i+1;
				ArrayList<Token> accesses = new ArrayList<Token>();
				for(; Trace.trace.get(j).getType() != TokenType.SECTION; j++) {
					Token inside = Trace.trace.get(j);
					if(inside.getType() == TokenType.WRITE || inside.getType() == TokenType.READ) {
						accesses.add(inside);
						Trace.trace.remove(j);
						j--;
					}
				}
				ArrayList<Token> summarized = summarize(accesses);
				for(int k = 0; k< summarized.size();k++) {
					Trace.trace.add(i+k+1,summarized.get(k));
				}
				i = j + summarized.size();
			}
			
		}
		
	}
	
	private static final void removeSections() {
		for(int i = 0; i< Trace.trace.size(); i++) {
			Token line = Trace.trace.get(i);
			if(line.getType() == TokenType.SECTION) {
				Trace.trace.remove(i);
				i--;
			}
		}
	}

	private static final ArrayList<Token> summarize(ArrayList<Token> tokens) {
		LinkedHashSet<String> accesses = new LinkedHashSet<String>(tokens.size()); 
		for(Token token : tokens) {
			AccessToken t = (AccessToken) token;
			
			String varName = t.getVar().getName();
			boolean isRead = t.isRead();
			
			// Check what RHS was first
			if(!isRead){
				WriteToken write = (WriteToken) t;
				for(Variable read : write.readComponents) {
					handleAccess(accesses,read.getName(),true);
				}
			}
			
			handleAccess(accesses,varName,isRead);
		}
			
		ArrayList<Token> summary = new ArrayList<Token>();
		Iterator varInfo = accesses.iterator();
		while(varInfo.hasNext()) {
			String element = (String)varInfo.next();
			if(element.contains("READ")) {
				summary.add(new ReadToken(-1, element,Trace.data.get(element.split(" ")[1])));
			}
			else {
				summary.add(new WriteToken(-1, element, Trace.data.get(element.split(" ")[1]),null));
			}
		}
		return summary;
	}
	private static void handleAccess(LinkedHashSet<String> accesses,String varName, boolean isRead) {
		String access = "";
		if(isRead) {
			access+="READ ";
		}
		else {
			access+="WRITE ";
		}
		access+= varName;
		if(accesses.contains(access)) {return;}
		accesses.add(access);
	}
}
