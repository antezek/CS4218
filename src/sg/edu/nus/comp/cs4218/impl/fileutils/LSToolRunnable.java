package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class LSToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public LSToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		LSTool lsTool = new LSTool();
		System.out.println(lsTool.execute(workingDir, stdin));
	}

}
