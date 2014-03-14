package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class GREPToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public GREPToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		GREPTool grepTool = new GREPTool();
		Result.result = grepTool.execute(workingDir, stdin);
	}

}
