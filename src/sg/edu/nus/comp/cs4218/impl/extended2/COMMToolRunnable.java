package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.fileutils.Result;

public class COMMToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public COMMToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		COMMTool commTool = new COMMTool();
		Result.result = commTool.execute(workingDir, stdin);
	}
}
