package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class MOVEToolRunnable implements Runnable{
	private File workingDir;
	private String stdin;

	public MOVEToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		MOVETool moveTool = new MOVETool();
		//System.out.println(moveTool.execute(workingDir, stdin));
		result = moveTool.execute(workingDir, stdin);
		Result.result = result;
	}
}
