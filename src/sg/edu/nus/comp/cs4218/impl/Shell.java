package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.IShell;
import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.fileutils.CATTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.CATToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.CDTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.CDToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.COPYTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.COPYToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.DELETETool;
import sg.edu.nus.comp.cs4218.impl.fileutils.DELETEToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.ECHOTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.ECHOToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.GREPTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.GREPToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.Helper;
import sg.edu.nus.comp.cs4218.impl.fileutils.LSTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.LSToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.MOVETool;
import sg.edu.nus.comp.cs4218.impl.fileutils.MOVEToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.PIPETool;
import sg.edu.nus.comp.cs4218.impl.fileutils.PIPEToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.PWDTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.PWDToolRunnable;

/**
 * The Shell is used to interpret and execute user's commands. Following
 * sequence explains how a basic shell can be implemented in Java
 */
public class Shell implements IShell {
	// List of user input commands
	private static final String CMD_PWD = "pwd";
	private static final String CMD_CD = "cd";
	private static final String CMD_LS = "ls";
	private static final String CMD_COPY = "copy";
	private static final String CMD_MOVE = "move";
	private static final String CMD_DELETE = "delete";
	private static final String CMD_CAT = "cat";
	private static final String CMD_ECHO = "echo";
	private static final String CMD_GREP = "grep";
	private static final String CMD_PIPE = "pipe";

	// List of commands
	private enum COMMAND {
		PWD, CD, LS, COPY, MOVE, DELETE, CAT, ECHO, GREP, PIPE, INVALID
	}

	// Scanner
	static Scanner scanner = new Scanner(System.in);

	// Others
	private static String stdin;
	private static Thread mainThread = null;
	private static Thread listenerThread = null;
	private static HashMap<String, Thread> hm = null;
	private static int timeFrame = 500; // default value 500ms
	private static boolean breakCmd = false;
	private static boolean isStdInput = true;

	// Getter & Setter Methods
	public static HashMap<String, Thread> getHm() {
		return hm;
	}

	public static void setUHm(String tName, Thread thread) {
		Shell.hm = new HashMap<String, Thread>();
		Shell.hm.put(tName, thread);
	}

	public static void setupHm() {
		Shell.hm = new HashMap<String, Thread>();
	}

	public static void setTimeFrame(int timeFrame) {
		Shell.timeFrame = timeFrame;
	}

	public static boolean isBreakCmd() {
		return breakCmd;
	}
	
	public static boolean isStdInput() {
		return isStdInput;
	}

	public static void setStdInput(boolean isStdInput) {
		Shell.isStdInput = isStdInput;
	}

	@Override
	public ITool parse(String input) {
		if (input.indexOf("|") != -1) {
			return new PIPETool();
		} else {
			String userCommand = Helper.getCommand(input);
			COMMAND command = determineCommandType(userCommand);

			switch (command) {
			case PWD:
				return new PWDTool();
			case CD:
				return new CDTool();
			case LS:
				return new LSTool();
			case COPY:
				return new COPYTool();
			case MOVE:
				return new MOVETool();
			case DELETE:
				return new DELETETool();
			case CAT:
				return new CATTool();
			case ECHO:
				return new ECHOTool();
			case GREP:
				return new GREPTool();
			default:
				System.err.println("Cannot parse " + input);
				return null;
			}
		}
	}

	@Override
	public Runnable execute(ITool tool) {
		File workingDir = new File(System.getProperty("user.dir"));

		if (tool instanceof PWDTool) {
			return new PWDToolRunnable(workingDir, stdin);
		} else if (tool instanceof CDTool) {
			return new CDToolRunnable(workingDir, stdin);
		} else if (tool instanceof LSTool) {
			return new LSToolRunnable(workingDir, stdin);
		} else if (tool instanceof COPYTool) {
			return new COPYToolRunnable(workingDir, stdin);
		} else if (tool instanceof MOVETool) {
			return new MOVEToolRunnable(workingDir, stdin);
		} else if (tool instanceof DELETETool) {
			return new DELETEToolRunnable(workingDir, stdin);
		} else if (tool instanceof CATTool) {
			return new CATToolRunnable(workingDir, stdin);
		} else if (tool instanceof ECHOTool) {
			return new ECHOToolRunnable(workingDir, stdin);
		} else if (tool instanceof GREPTool) {
			return new GREPToolRunnable(workingDir, stdin);
		} else if (tool instanceof PIPETool) {
			return new PIPEToolRunnable(workingDir, stdin);
		}
		return null;
	}

	@Override
	public void stop(Runnable toolExecution) {
		// TODO Implement
	}

	/**
	 * Do Forever 1. Wait for a user input 2. Parse the user input. Separate the
	 * command and its arguments 3. Create a new thread to execute the command
	 * 4. Execute the command and its arguments on the newly created thread.
	 * Exit with the status code of the executed command 5. In the shell, wait
	 * for the thread to complete execution 6. Report the exit status of the
	 * command to the user
	 */
	public static void main(String[] args) {
		hm = new HashMap<String, Thread>();
		startRun();
	}

	/**
	 * Starts the main program
	 */
	public static void startRun() {
		Runnable run = null;
		ITool tool = null;
		while (true) {
			stdin = readUserInput();

			Shell s = new Shell();
			tool = s.parse(stdin);
			run = s.execute(tool);
			startMainThread(run);
		}
	}

	/**
	 * Start main thread to execute instruction
	 * 
	 * @param run
	 */
	public static void startMainThread(Runnable run) {
		mainThread = new Thread(run);
		hm.put("t1", mainThread);
		mainThread.start();
		if (!Thread.currentThread().isInterrupted() && mainThread.isAlive()) {
			startInterruptListener();
		}
	}

	/**
	 * Starts interrupt listener to listen for ctrl-z interrupt
	 */
	public static void startInterruptListener() {
		Runnable listener = new Runnable() {
			@Override
			public void run() {
				Scanner s = new Scanner(System.in);
				while (mainThread.isAlive() && isStdInput) {
					breakCmd = true;
					System.out.print("Type \"ctrl-z\" to break: ");
					String input = s.nextLine();

					if (input.equalsIgnoreCase("ctrl-z")) {
						mainThread = (Thread) hm.get("t1");
						mainThread.stop();
						break;
					} else {
						System.out.println("invalid command");
					}
				}
				s.close();
			}
		};

		try {
			
			listenerThread = new Thread(listener);
			listenerThread.sleep(timeFrame);
			if(isStdInput){
				listenerThread.start();
			}
			while (true) {
				if (!mainThread.isAlive()) {
					listenerThread.stop();
					startRun();
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read user input
	 * 
	 * @return
	 */
	public static String readUserInput() {
		System.out.print("command:");
		return scanner.nextLine();
	}

	/**
	 * Determines the type of command user entered
	 * 
	 * @param command
	 * @return
	 */
	private static COMMAND determineCommandType(String command) {

		if (command.equalsIgnoreCase(CMD_PWD)) {
			return COMMAND.PWD;
		} else if (command.equalsIgnoreCase(CMD_CD)) {
			return COMMAND.CD;
		} else if (command.equalsIgnoreCase(CMD_LS)) {
			return COMMAND.LS;
		} else if (command.equalsIgnoreCase(CMD_COPY)) {
			return COMMAND.COPY;
		} else if (command.equalsIgnoreCase(CMD_MOVE)) {
			return COMMAND.MOVE;
		} else if (command.equalsIgnoreCase(CMD_DELETE)) {
			return COMMAND.DELETE;
		} else if (command.equalsIgnoreCase(CMD_CAT)) {
			return COMMAND.CAT;
		} else if (command.equalsIgnoreCase(CMD_ECHO)) {
			return COMMAND.ECHO;
		} else if (command.equalsIgnoreCase(CMD_GREP)) {
			return COMMAND.GREP;
		} else if (command.equalsIgnoreCase(CMD_PIPE)) {
			return COMMAND.PIPE;
		} else {
			return COMMAND.INVALID;
		}
	}
}
