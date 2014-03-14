package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class COPYToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public COPYToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		COPYTool copyTool = new COPYTool();
		Result.result = copyTool.execute(workingDir, stdin);
	}

}
