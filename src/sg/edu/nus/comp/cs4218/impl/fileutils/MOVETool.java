package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class MOVETool extends ATool implements IMoveTool {
	private static final String MESSAGE_MOVE_SUCCESS ="Moved file %1$s to %2$s";
	private static final String MESSAGE_MOVE_ERROR ="Error: failed to move file";
	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_DIR_NOT_FOUND = "Error: file or dir not found";
	
	// Regex
	private static final String REGEX_WHITE_SPACE = "\\s+";

	public MOVETool() {
		super(null);
	}

	@Override
	public boolean move(File from, File to) {
		File f = new File(to, from.getName());
		try {
			if (!f.exists()) {
				// moving file
				if(f.createNewFile()) {
					// deleting file after move
					if (from.delete()) {
						return true;
					}
					else {
						return false;
					}
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
			File from = isValidFile(workingDir, parts[1]);
			File to = new File(parts[2]);
			if (from != null && isValidDirectory(to)) {
				// moving file
				if (move(from, to)) {
					return String.format(MESSAGE_MOVE_SUCCESS, from.getName(), to.getAbsolutePath());
				}
				else {
					setStatusCode(1);
					return MESSAGE_MOVE_ERROR;
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
	
	private boolean isValidDirectory(File f) {
		if (f.isDirectory()) {
			return true;
		}
		else {
			return false;
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
