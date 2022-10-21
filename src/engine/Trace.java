package engine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import engine.tokens.*;

public class Trace {
	public static ArrayList<Token> trace = new ArrayList<Token>();
	public static HashMap<String, Variable> data = new HashMap<String, Variable>();
	public static File traceFile;
	
	//boolean inKernel = false;
	
	
	
	public static void parseTrace() {
		Trace.trace.clear();
		data.clear();
		try {
			int ln = 0;
			Scanner input = new Scanner(new FileInputStream(traceFile));
			while(input.hasNextLine()) {
				parseLine(input.nextLine(), ln);
			}
			input.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(Trace.dumpTrace());
	}
	
	public static void parseLine(String line, int ln) {
		if(line.length() == 0 || line.charAt(0) == '#') { return; }
		
		line = line.trim();
		switch(line.split(" ")[0].toUpperCase()) {
			case("CREATE"):{
				parseCreate(line, ln);
				break;
			}
			case("READ"):{
				parseRead(line, ln);
				break;
			}
			case("WRITE"):{
				parseWrite(line, ln);
				break;
			}
			case("DONE"):
			case("CALL"):
			case("RETURN"):
			case("SECTION"):{
				parseSection(line,ln);
				break;
			}
			case("MAP"):{
				parseMap(line,ln);
			}
		}
	}

	private static void parseCreate(String line, int ln) {
		String varName = line.substring(line.indexOf(' ')+1);
		
		ArrayList<String> names = new ArrayList<String>();
		
		if(varName.contains("[")) { // array creation
			int num = Integer.parseInt(varName.substring(varName.indexOf('[')+1,varName.indexOf(']')));
			
			for(int i = 0; i< num; i++) {
				names.add(varName.substring(0,varName.indexOf('['))+"["+i+"]");
			}
		}
		else {
			names.add(varName);
		}
		
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for(String name : names) {
			if(data.containsKey(name)) {
				System.out.println("Error in trace file, recreating variable");
				System.exit(-1);
			}
			variables.add(new Variable(name));
			data.put(name, variables.get(variables.size()-1));
		}
		CreateToken create = new CreateToken(ln, line, variables);
		for(Variable v : variables) {
			v.setCreatedLoc(create);
		}
		Trace.trace.add(create);
	}
//	public void parseKernel(String line, int ln) {
//		String operation = line.split(" ")[1].toUpperCase();
//		if(operation == "ENTER") {
//			inKernel = true;
//		}
//		else {
//			inKernel = false;
//		}
//	}
	
	private static void parseRead(String line, int ln) {
		Variable var = data.get(line.split(" ")[1]);
		ReadToken read = new ReadToken(ln,line,var);
		trace.add(read);
		var.accesses.add(read);
	}
	private static void parseWrite(String line, int ln) {
		ArrayList<Variable> reads = new ArrayList<Variable>();
		Variable var = data.get(line.split(" ")[1]);
		String value = "";
		for(int i=2;i<line.split(" ").length;i++) {
			value += line.split(" ")[i] + " ";
		}
		value = value.replaceAll("\\(", "");
		value = value.replaceAll("\\)","");
		for(int i = 0; i < value.split(" ").length; i++) {
			String s = value.split(" ")[i];
			if(s.equalsIgnoreCase("READ")) {
				reads.add(data.get(value.split(" ")[i+1]));
			}
		}
		WriteToken write = new WriteToken(ln, line, var, reads);
		trace.add(write);
	}
	private static void parseSection(String line, int ln) {
		trace.add(new SectionToken(line));
	}
	private static void parseMap(String line, int ln) {
		trace.add(new MapToken(line));
	}
	
	public static String dumpTrace() {
		String str = "";
		for(Token traceline : trace) {
			str+=traceline+"\n";
		}
		return str;
	}
}
