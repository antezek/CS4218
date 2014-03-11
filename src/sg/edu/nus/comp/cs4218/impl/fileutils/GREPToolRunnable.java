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
		String result = "";
		GREPTool grepTool = new GREPTool();
		result = grepTool.execute(workingDir, stdin);
		//System.out.println(grepTool.execute(workingDir, stdin));
		Result.result = result;
	}

}
