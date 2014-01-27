package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class CDToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public CDToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		CDTool cdTool = new CDTool();
		System.out.println(cdTool.execute(workingDir, stdin));
	}

}
