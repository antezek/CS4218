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
import sg.edu.nus.comp.cs4218.impl.fileutils.LSTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.LSToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.MOVETool;
import sg.edu.nus.comp.cs4218.impl.fileutils.MOVEToolRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.PWDTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.PWDToolRunnable;

/**
 * The Shell is used to interpret and execute user's commands. Following
 * sequence explains how a basic shell can be implemented in Java
 */
public class Shell implements IShell {
	// Tests
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

	// Regex
	private static final String REGEX_WHITE_SPACE = "\\s+";

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

	@Override
	public ITool parse(String commandline) {
		String userCommand = getCommand(commandline);
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
			// TODO glen: uncomment when pipe implemented
			// case PIPE:
			// return new PIPETool();
		default:
			System.err.println("Cannot parse " + commandline);
			return null;
		}
	}

	@Override
	public Runnable execute(ITool tool) {
		// TODO Implement
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
		}
		// TODO glen: uncomment when grep/pipe implemented
		// else if (tool instanceof GREPTool) {
		// return new GREPToolRunnable(workingDir, stdin);
		// }
		// else if (tool instanceof PIPETool) {
		// return new PIPEToolRunnable(workingDir, stdin);
		// }
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
	
	public static void startRun() {
		while (true) {
			stdin = readUserInput();
			
			Shell s = new Shell();
			ITool tool = s.parse(stdin);
			Runnable run = s.execute(tool);
			
			startMainThread(run);
		}
	}
	
	public static void startMainThread(Runnable run) {
		mainThread = new Thread(run);
		hm.put("t1", mainThread);
		mainThread.start();
		
		if (!Thread.currentThread().isInterrupted() && mainThread.isAlive()) {
			startInterruptListener();
		}
	}
	
	public static void startInterruptListener() {
		Runnable listener = new Runnable() {
			@Override
			public void run() {
				Scanner s = new Scanner(System.in);
				while (mainThread.isAlive()) {
					System.out.print("Type \"ctrl-z\" to break: ");
					String input = s.nextLine();
					
					if (input.equalsIgnoreCase("ctrl-z")) {
						mainThread = (Thread) hm.get("t1");
						mainThread.stop();
						break;
					}
					else {
						System.out.println("invalid command");
					}
				}
				s.close();
			}
		};
		
		try {
			listenerThread = new Thread(listener);
			listenerThread.sleep(500);
			listenerThread.start();
			
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

//	public static void startRun() {
//		Runnable r = null;
//		Runnable r2 = null;
//		hm = new HashMap();
//		try {
//			do {
//				stdin = readUserInput(0);
//				if (!stdin.equals("")) {
//					Shell s = new Shell();
//					ITool tool = s.parse(stdin);
//					r = s.execute(tool);
//					thread = new Thread(r); // Spawn a thread to execute the
//											// command
//					hm.put("t1", thread);
//					thread.start();
//					r2 = new Runnable() {
//						Scanner eofScan;
//
//						public void run() {
//							if (thread.isAlive()) { // Check if thread is still
//													// processing. if yes, give
//													// option to break.
//								readUserInput(1);
//							}
//						}
//					};
//					thread2 = new Thread(r2); // Spawn a thread to listen for
//												// break command.
//					thread2.sleep(500); // Delay for result to show first before
//										// cmd prompt
//					thread2.start();
//					while (true) {
//						Thread.currentThread().sleep(500);
//						if (thread.isAlive() == false) {
//							thread2.stop();
//							startRun();
//							break;
//						}
//					}
//				} else {
//					break; // stdin is EMPTY!
//				}
//			} while (stdin.equalsIgnoreCase("exit"));
//		} catch (InterruptedException e) {
//			System.out.println("Thread interrupted"); // Interrupted
//		}
//	}

//	public static String readUserInput(int type) {
//		String s = "";
//		if (type == 0) {
//			System.out.print("command:");
//		} else {
//			System.out.print("Type \"ctrl-z\" to break: ");
//		}
//		try {
//			// glen: i uncomment this!!
//			//scanner = new Scanner(System.in);
//			s = scanner.nextLine();
//			if (s.equalsIgnoreCase("ctrl-z")) {
//				thread = (Thread) hm.get("t1");
//				thread.stop();
//			} else if (type == 1 && !(s.equalsIgnoreCase("ctrl-z"))) {
//				System.out.println("Invalid command");
//				readUserInput(1);
//			}
//		} catch (NullPointerException e) {
//			System.out.println("System Exit");
//			System.exit(0);
//		}
//		return s;
//	}
	
	public static String readUserInput() {
		System.out.print("command:");
		return scanner.nextLine();
	}

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

	private static String getCommand(String userInputString) {
		return userInputString.trim().split(REGEX_WHITE_SPACE)[0];
	}
}
