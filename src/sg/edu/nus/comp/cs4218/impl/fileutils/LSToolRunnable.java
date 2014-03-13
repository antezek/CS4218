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
		String result = "";
		LSTool lsTool = new LSTool();
		result = lsTool.execute(workingDir, stdin);
		System.out.println(lsTool.execute(workingDir, stdin));
		Result.result = result;
	}

}
