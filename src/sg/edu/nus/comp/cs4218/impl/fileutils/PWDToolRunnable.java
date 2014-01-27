package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class PWDToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public PWDToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		PWDTool pwdTool = new PWDTool();
		System.out.println(pwdTool.execute(workingDir, stdin));
	}

}
