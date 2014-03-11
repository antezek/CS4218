package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.fileutils.ICdTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

/**
 * CDTool to provide basic functionality of CD command
 *
 */
public class CDTool extends ATool implements ICdTool {
	private static final String MESSAGE_DIR_CHANGE_SUCCESS = "Working dir changed to: %1$s";
	private static final String MESSAGE_DIR_NULL = "Error: directory cannot be null";
	private static final String MESSAGE_DIR_NOT_VALID = "Error: not a directory";
	
	public CDTool() {
		super(null);
	}

	@Override
	public File changeDirectory(String newDirectory) {
		File f = new File(newDirectory);
		
		if (!f.exists() || !f.isDirectory()) {
			setStatusCode(1);
			return null;
		}
		else {
			System.setProperty("user.dir", f.getAbsolutePath());
			return f;
		}

	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(Helper.REGEX_WHITE_SPACE);
		
		if (!isDirNull(parts)) {
			File f = Helper.isValidDirectory(workingDir, parts[1]);
			
			if (f != null) {
				File newDir = changeDirectory(f.getAbsolutePath());
				
				if (newDir != null) {
					return String.format(MESSAGE_DIR_CHANGE_SUCCESS, newDir.getAbsolutePath());
				}
				else {
					setStatusCode(1);
					return MESSAGE_DIR_NOT_VALID;
				}
			}
			else {
				setStatusCode(1);
				return MESSAGE_DIR_NOT_VALID;
			}
			
		}
		else {
			setStatusCode(1);
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
