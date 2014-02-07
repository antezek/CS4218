package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

/**
 * MOVETool to provide basic functionality of MOVE command
 *
 */
public class MOVETool extends ATool implements IMoveTool {
	private static final String MESSAGE_MOVE_SUCCESS ="Moved file %1$s to %2$s";
	private static final String MESSAGE_MOVE_ERROR ="Error: failed to move file";
	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_DIR_NOT_FOUND = "Error: file or dir not found";

	public MOVETool() {
		super(null);
	}

	@Override
	public boolean move(File from, File to) {
		File f = new File(to, from.getName());
		try {
			if (!f.exists()) {
				// moving file
				if (f.createNewFile()) {
					// deleting file after move
					if (from.delete()) {
						return true;
					}
					else {
						setStatusCode(1);
						return false;
					}
				}
				else {
					setStatusCode(1);
					return false;
				}
			}
			else {
				setStatusCode(1);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(Helper.REGEX_WHITE_SPACE);
		
		if (!isFileNameNull(parts)) {
			File from = Helper.isValidFile(workingDir, parts[1]);
			File to = new File(parts[2]);
			if (from != null && Helper.isValidDirectory(to)) {
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

	private static boolean isFileNameNull(String[] fileName) {
		if (fileName.length != 3) {
			return true;
		}
		else {
			return false;
		}
	}

}
