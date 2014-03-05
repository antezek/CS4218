package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

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
		System.out.println(commTool.execute(workingDir, stdin));
	}
}
