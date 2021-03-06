package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

/**
 * COPYTool to provide basic functionality of copying a file
 *
 */
public class COPYTool extends ATool implements ICopyTool {
	private static final String MESSAGE_COPY_SUCCESS ="Copied file %1$s to %2$s";
	private static final String MESSAGE_COPY_ERROR ="Error: failed to copy file";
	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_DIR_NOT_FOUND = "Error: file or dir not found";
	
	// Regex
	private static final String REGEX_WHITE_SPACE = "\\s+";

	public COPYTool() {
		super(null);
	}

	@Override
	public boolean copy(File from, File to) {
		File f = new File(to, from.getName());
		try {
			if (!f.exists()) {
				// copying file
				if(f.createNewFile()) {
					return true;
				}
				else {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(REGEX_WHITE_SPACE);
		
		if (!isFileNameNull(parts)) {
			File from = Helper.isValidFile(workingDir, parts[1]);
			File to = Helper.isValidDirectory(workingDir, parts[2]);
			
			if ((from != null) && (to != null)) {
				// copying file
				if (copy(from, to)) {
					return String.format(MESSAGE_COPY_SUCCESS, from.getName(), to.getAbsolutePath());
				}
				else {
					setStatusCode(1);
					return MESSAGE_COPY_ERROR;
				}
			}
			else {
				setStatusCode(1);
				return MESSAGE_FILE_DIR_NOT_FOUND;
			}
		}
		else {
			setStatusCode(1);
			return MESSAGE_FILE_NAME_NULL;
		}
	}

	private static boolean isFileNameNull(String[] fileName) {
		if (fileName.length != 3) {
			return true;
		}
		else {
			return false;
		}
	}

}
