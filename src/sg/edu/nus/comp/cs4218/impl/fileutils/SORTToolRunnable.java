package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class SORTToolRunnable implements Runnable {

	private File workingDir;
	private String stdin;
	
	public SORTToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		SORTTool sortTool = new SORTTool();
		//System.out.println(sortTool.execute(workingDir, stdin));
		result = sortTool.execute(workingDir, stdin);
		Result.result = result;
	}
}
