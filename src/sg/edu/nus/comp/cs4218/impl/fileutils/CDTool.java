package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.fileutils.ICdTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class CDTool extends ATool implements ICdTool {
	private static final String MESSAGE_DIR_CHANGE_SUCCESS = "Working dir changed to: %1$s";
	private static final String MESSAGE_DIR_CHANGE_FAIL = "Failed to change working dir";
	private static final String MESSAGE_DIR_NULL = "Error: directory cannot be null";
	private static final String MESSAGE_DIR_DOES_NOT_EXIST = "Error: directory does not exist";
	private static final String MESSAGE_DIR_NOT_VALID = "Error: not a directory";
	
	// Regex
	private static final String REGEX_WHITE_SPACE = "\\s+";
	
	public CDTool() {
		super(null);
	}

	@Override
	public File changeDirectory(String newDirectory) {
		File f = new File(newDirectory);

		if (!f.exists()) {
			setStatusCode(1);
			System.err.println(MESSAGE_DIR_DOES_NOT_EXIST);
			return null;
		}
		else if (!f.isDirectory()) {
			setStatusCode(1);
			System.err.println(MESSAGE_DIR_NOT_VALID);
			return null;
		}
		else {
			//TODO glen: check if this is safe to use
			System.setProperty("user.dir", f.getAbsolutePath());
			return f;
		}

	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(REGEX_WHITE_SPACE);
		
		if (!isDirNull(parts)) {
			File newDir = changeDirectory(parts[1]);
			
			if (newDir != null) {
				return String.format(MESSAGE_DIR_CHANGE_SUCCESS, newDir.getAbsolutePath());
			}
			else {
				return MESSAGE_DIR_CHANGE_FAIL;
			}
		}
		else {
			return MESSAGE_DIR_NULL;
		}
	}
	
	private static boolean isDirNull(String[] dir) {
		if (dir.length != 2) {
			return true;
		}
		else {
			return false;
		}
	}

}
