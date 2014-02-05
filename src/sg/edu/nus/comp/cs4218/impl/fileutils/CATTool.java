package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class CATTool extends ATool implements ICatTool {
	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_NOT_FOUND = "Error: file not found";

	public CATTool() {
		super(null);
	}

	@Override
	public String getStringForFile(File toRead) {
		String displayString = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(toRead));
			String nextLine;

			while ((nextLine = br.readLine()) != null) {
				displayString += nextLine +"\n";
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return displayString;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(Helper.REGEX_WHITE_SPACE);
		String result = "";
		
		if (!isFileNameNull(parts)) {
			for (int i = 1; i < parts.length; i++) {
				if (!parts[i].equalsIgnoreCase("-")) {
					File toRead = isValidFile(workingDir, parts[i]);
					if (toRead != null) {
						result += getStringForFile(toRead);
					}
					else {
						setStatusCode(1);
						return MESSAGE_FILE_NOT_FOUND;
					}
				}
				else {
					System.out.print("enter text:");
					Scanner sc = new Scanner(System.in);
					String input = sc.nextLine();
					result += input +"\n";
				}
			}
		}
		else {
			setStatusCode(1);
			return MESSAGE_FILE_NAME_NULL;
		}
		return result.trim();
	}
	
	private static File isValidFile(File workingDir, String fileName) {
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
	
	private static boolean isFileNameNull(String[] fileName) {
		if (fileName.length < 2) {
			return true;
		}
		else {
			return false;
		}
	}

}
