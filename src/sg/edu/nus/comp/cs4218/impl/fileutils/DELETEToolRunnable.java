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
		String result = "";
		DELETETool deleteTool = new DELETETool();
		deleteTool.execute(workingDir, stdin);
		//System.out.println(deleteTool.execute(workingDir, stdin));
		Result.result = result;
	}

}
