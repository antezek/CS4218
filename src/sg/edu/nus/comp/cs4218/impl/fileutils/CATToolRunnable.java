package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class CATToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public CATToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		CATTool catTool = new CATTool();
		result = catTool.execute(workingDir, stdin);
		//System.out.println(catTool.execute(workingDir, stdin));
		Result.result = result;
	}
}
