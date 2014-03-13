package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class PASTEToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public PASTEToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		PASTETool pasteTool = new PASTETool();
		System.out.println(pasteTool.execute(workingDir, stdin));
		result = pasteTool.execute(workingDir, stdin);
		Result.result = result;
	}
}
