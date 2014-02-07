package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IPipingTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.Shell;

public class PIPETool extends ATool implements IPipingTool {
	private static final String CMD_PWD = "pwd";
	private static final String CMD_CD = "cd";
	private static final String CMD_LS = "ls";
	private static final String CMD_CAT = "cat";
	private static final String CMD_ECHO = "echo";
	private static final String CMD_GREP = "grep";
	
	private Shell s;
	private File workDir;
	private File tempFile;

	public PIPETool() {
		super(null);
		s = new Shell();
	}

	@Override
	public String pipe(ITool from, ITool to) {

		return null;
	}

	@Override
	public String pipe(String stdout, ITool to) {
		return to.execute(workDir, stdout);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		workDir = workingDir;
		boolean isFirst = true;
		boolean isValid = false;
		String[] pipeCommand = null;
		int pipeCmdCount = 0;
		String result = "";
		ITool tool;

		String strInputCmdPipe = stdin;
		pipeCommand = strInputCmdPipe.split("[|]+");
		pipeCmdCount = pipeCommand.length;
		
		// formatting commands
		for (int i = 0; i < pipeCmdCount; i++) {
			pipeCommand[i] = ltrim(pipeCommand[i]);
			StringBuilder sb = new StringBuilder(pipeCommand[i]);
			
			if (pipeCommand[i].charAt(pipeCommand[i].length() -1) == ' ') {
				sb = sb.deleteCharAt(pipeCommand[i].length() -1);
				pipeCommand[i] = sb.toString();
			}
		}
		
		// Check for valid commands
		for (int i = 0; i < pipeCmdCount; i++) {
			isValid = checkValidCommandTypeForPipe(Helper.getCommand(pipeCommand[i]), isFirst);
			if (!isValid) {
				break;
			}
			isFirst = false;
		}

		if (isValid) {
			// proceed with pipe processing
			// parsing first command
			tool = s.parse(pipeCommand[0]);
			result = tool.execute(workingDir, pipeCommand[0]);
			createPIPEFile(result);
			
			// parsing subsequent commands
			for (int i = 1; i < pipeCmdCount; i++) {
				tool = s.parse(pipeCommand[i]);
				result = pipe(pipeCommand[i] +" " + tempFile.getAbsolutePath(), tool);
				createPIPEFile(result);
			}
		}
		else {
			// invalid command; stop execution
			return "invalid input";
		}
		
		tempFile.delete();
		return result;
	}
	
	public static String ltrim(String s) {
		int i = 0;
		while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
			i++;
		}
		return s.substring(i);
	}

	/**
	 * Check for valid command types for pipe processing
	 * First command can only be GREP/LS/ECHO/CAT/PWD
	 * Subsequent commands can only be GREP
	 * @param command
	 * @param isFirst
	 * @return true if valid, false if otherwise
	 */
	public boolean checkValidCommandTypeForPipe(String command, boolean isFirst) {
		boolean valid = false;
		if (isFirst) {
			// Processing first command
			if (command.equalsIgnoreCase(CMD_GREP)
					|| command.equalsIgnoreCase(CMD_PWD)
					|| command.equalsIgnoreCase(CMD_CD)
					|| command.equalsIgnoreCase(CMD_LS)
					|| command.equalsIgnoreCase(CMD_CAT)
					|| command.equalsIgnoreCase(CMD_ECHO)) {
				valid = true;
			} else {
				valid = false;
			}
		} else {
			// Processing second command, only GREP is allowed
			if (command.equalsIgnoreCase(CMD_GREP)) {
				valid = true;
			} else {
				valid = false;
			}
		}
		return valid;

	}
	
	private void createPIPEFile(String result) {
		try {
			tempFile = new File("./misc/temppipefile.txt");
			
			if (tempFile.exists()) {
				tempFile.delete();
			}
			else {
				tempFile.createNewFile();
			}
			
			// Adding contents
			FileWriter fstream = new FileWriter(tempFile);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(result);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
