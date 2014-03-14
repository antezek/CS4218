package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.fileutils.Result;

public class SORTToolRunnable implements Runnable {

	private File workingDir;
	private String stdin;
	
	public SORTToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		SORTTool sortTool = new SORTTool();
		Result.result = sortTool.execute(workingDir, stdin);
	}
}
