package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.fileutils.Result;

public class CUTToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public CUTToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		CUTTool cutTool = new CUTTool();
		//System.out.println(cutTool.execute(workingDir, stdin));
		result = cutTool.execute(workingDir, stdin);
		Result.result = result;
	}
}