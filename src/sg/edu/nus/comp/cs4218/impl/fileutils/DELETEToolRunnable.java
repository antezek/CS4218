package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class DELETEToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public DELETEToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		DELETETool deleteTool = new DELETETool();
		System.out.println(deleteTool.execute(workingDir, stdin));
	}

}
