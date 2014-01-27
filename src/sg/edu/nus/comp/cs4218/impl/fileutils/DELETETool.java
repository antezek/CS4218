package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class DELETETool extends ATool implements IDeleteTool {
	private static final String MESSAGE_DELETE_SUCCESS = "Deleted file %1$s";
	private static final String MESSAGE_DELETE_ERROR ="Error: failed to delete file";
	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_NOT_FOUND = "Error: file not found";
	
	// Regex
	private static final String REGEX_WHITE_SPACE = "\\s+";

	public DELETETool() {
		super(null);
	}

	@Override
	public boolean delete(File toDelete) {
		if (toDelete.delete()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(REGEX_WHITE_SPACE);
		
		if (!isFileNameNull(parts)) {
			File f = isValidFile(workingDir, parts[1]);
			
			if (f != null) {
				// deleting file
				if (delete(f)) {
					return String.format(MESSAGE_DELETE_SUCCESS, f.getAbsolutePath());
				}
				else {
					setStatusCode(1);
					return MESSAGE_DELETE_ERROR;
				}
			}
			else {
				setStatusCode(1);
				return MESSAGE_FILE_NOT_FOUND;
			}
		}
		else {
			setStatusCode(1);
			return MESSAGE_FILE_NAME_NULL;
		}
		
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
		if (fileName.length != 2) {
			return true;
		}
		else {
			return false;
		}
	}

}
