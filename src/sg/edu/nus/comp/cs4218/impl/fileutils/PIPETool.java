package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

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

	public PIPETool() {
		super(null);
		s = new Shell();
	}

	@Override
	public String pipe(ITool from, ITool to) {
		ITool startTool, endTool;

		startTool = determineTool(from);
		endTool = determineTool(to);
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
			
			// parsing subsequent commands
			for (int i = 1; i < pipeCmdCount; i++) {
				tool = s.parse(pipeCommand[i]);
				result = pipe(pipeCommand[i] + result, tool);
			}
		}
		else {
			// invalid command; stop execution
			return "invalid input";
		}

		return result;
	}

	/**
	 * Check for valid command types for pipe processing
	 * First command can only be GREP/LS/ECHO/CAT/PWD
	 * Subsequent commands can only be GREP
	 * @param command
	 * @param isFirst
	 * @return true if valid, false if otherwise
	 */
	private boolean checkValidCommandTypeForPipe(String command, boolean isFirst) {
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
	
	/**
	 * Determine the tool for PIPE command processing
	 * @param tool
	 * @return
	 */
	public ITool determineTool(ITool tool) {
		if (tool instanceof PWDTool) {
			return new PWDTool();
		} else if (tool instanceof CDTool) {
			return new CDTool();
		} else if (tool instanceof LSTool) {
			return new LSTool();
		} else if (tool instanceof CATTool) {
			return new CATTool();
		} else if (tool instanceof ECHOTool) {
			return new ECHOTool();
		}
		return null;
	}

}
