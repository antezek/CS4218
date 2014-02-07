package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
import sg.edu.nus.comp.cs4218.impl.ATool;


/**
 * LSTool to provide basic functionality of LS command
 *
 */
public class LSTool extends ATool implements ILsTool {
	private static final String MESSAGE_NO_FILES_IN_DIR = "Error: No files in working directory";

	public LSTool() {
		super(null);
	}

	@Override
	public List<File> getFiles(File directory) {
		File[] files = directory.listFiles();
		List<File> result = new ArrayList<File>();
		
		for (File file: files) {
			if (file.isFile() || file.isDirectory()) {
				result.add(file);
			}
		}
		return result;
	}

	@Override
	public String getStringForFiles(List<File> files) {
		if (!files.isEmpty()) {
			String result = "";
			
			for (File file : files) {
		        result += file.getName() +"\n";
			}
			return result.trim();
		}
		else {
			return MESSAGE_NO_FILES_IN_DIR;
		}
	}

	@Override
	public String execute(File workingDir, String stdin) {
		List<File> file = getFiles(workingDir);
		return getStringForFiles(file);
	}

}
