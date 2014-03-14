package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.fileutils.Result;

public class PASTEToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public PASTEToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		PASTETool pasteTool = new PASTETool();
		Result.result = pasteTool.execute(workingDir, stdin);
	}
}
