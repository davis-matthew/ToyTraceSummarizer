package main;

import engine.Trace;
import engine.analysis.SummaryPass;
import io.TraceFileManager;

public class TraceSummarizer {
	
	public static void main(String[]args) {
		TraceFileManager.getTraceFromFile();		
		if(Trace.traceFile != null) {
			Trace.parseTrace();
			SummaryPass.run();
			TraceFileManager.writeSummarizedTraceToFile();
		}
	}

}
