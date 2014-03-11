package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

/**
 * Helper class to provide some helper methods to aid the various processing tools.
 * Since many of the tools use similar methods, these shared methods will be placed
 * in this Helper class instead of the individual classes.
 *
 */
public class Helper {
	public static final String REGEX_WHITE_SPACE = "\\s+";
	
	/**
	 * Checks if file is valid
	 * @param workingDir
	 * @param fileName
	 * @return File if file exists, null otherwise
	 */
	public static File isValidFile(File workingDir, String fileName) {
		if (fileName.equalsIgnoreCase(REGEX_WHITE_SPACE) || fileName == null || fileName.equalsIgnoreCase("")) {
			return null;
		}
		
		File f = new File(fileName);
		
		if (f.exists()) {
			return f;
		}
		else {
			f = new File(workingDir.getAbsolutePath() +"/" +fileName);
			
			if (f.exists()) {
				return f;
			}
		}
		return null;
	}
	
	/**
	 * Check if directory is valid
	 * @param workingDir
	 * @param folderName
	 * @return File if directory exists, null otherwise
	 */
	public static File isValidDirectory(File workingDir, String folderName) {
		if (folderName.equalsIgnoreCase(REGEX_WHITE_SPACE) || folderName == null || folderName.equalsIgnoreCase("")) {
			return null;
		}
		
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
	
	/**
	 * Check if directory is valid
	 * @param f
	 * @return true if file exists, false otherwise
	 */
	public static boolean isValidDirectory(File f) {
		if (f.isDirectory()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Gets the command the user entered
	 * @param userInputString
	 * @return
	 */
	public static String getCommand(String userInputString) {
		return userInputString.trim().split(REGEX_WHITE_SPACE)[0];
	}
}
