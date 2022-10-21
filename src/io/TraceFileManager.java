package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import engine.Trace;

public class TraceFileManager {
	public static final Trace getTraceFromFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("traces"));
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            Trace.traceFile = chooser.getSelectedFile();
        }
        return null;
	}

	public static void writeSummarizedTraceToFile() {
		try {
			String fileName = Trace.traceFile.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			fileName+="_summarized.trace";
			BufferedWriter output = new BufferedWriter(new FileWriter(new File(fileName)));
			output.write(Trace.dumpTrace());
			output.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
