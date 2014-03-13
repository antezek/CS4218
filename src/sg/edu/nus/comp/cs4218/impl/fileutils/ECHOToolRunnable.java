package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

public class ECHOToolRunnable implements Runnable {
	private File workingDir;
	private String stdin;

	public ECHOToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		ECHOTool echoTool = new ECHOTool();
		result = echoTool.execute(workingDir, stdin);
		System.out.println(echoTool.execute(workingDir, stdin));
		Result.result = result;
	}

}
