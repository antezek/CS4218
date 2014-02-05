package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class Helper {
	public static final String REGEX_WHITE_SPACE = "\\s+";
	
	public static File isValidFile(File workingDir, String fileName) {
		File f = new File(fileName);
		
		if (f.exists()) {
			return f;
		}
		else {
			f = new File(workingDir.getAbsolutePath() +"\\" +fileName);
			
			if (f.exists()) {
				return f;
			}
		}
		return null;
	}
	
	public static File isValidDirectory(File workingDir, String folderName) {
		File f = new File(folderName);
		
		if (f.isDirectory()) {
			return f;
		}
		else {
			f = new File(workingDir.getAbsolutePath() +"\\" +folderName);
			
			if (f.isDirectory()) {
				return f;
			}
		}
		return null;
	}
	
	public static boolean isValidDirectory(File f) {
		if (f.isDirectory()) {
			return true;
		}
		else {
			return false;
		}
	}
}
