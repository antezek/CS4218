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
		MOVETool moveTool = new MOVETool();
		Result.result = moveTool.execute(workingDir, stdin);;
	}
}
