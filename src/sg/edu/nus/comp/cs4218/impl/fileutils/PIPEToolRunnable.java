package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class PIPEToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public PIPEToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		PIPETool pipeTool = new PIPETool();
		result = pipeTool.execute(workingDir, stdin);
		Result.result = result;
		// System.out.println(pipeTool.execute(workingDir, stdin));
	}

}
