package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.fileutils.IEchoTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

/**
 * ECHOTool to provide basic functionality of ECHO command
 *
 */
public class ECHOTool extends ATool implements IEchoTool {
	private static final String MESSAGE_ARGS_NULL = "Error: args null";
	private static final String CMD_ECHO = "echo";

	public ECHOTool() {
		super(null);
	}

	@Override
	public String echo(String toEcho) {
		String echoString = toEcho.replace(CMD_ECHO, "").trim();
		
		// getting variable if exists
		String value = System.getenv(echoString);
		if (value != null) {
			return value;
		} 
		else {
			return echoString;
		}
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String parts[] = stdin.split(Helper.REGEX_WHITE_SPACE);
		if (!isFileNameNull(parts)) {
			return echo(stdin);
		}
		else {
			setStatusCode(1);
			return MESSAGE_ARGS_NULL;
		}
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
