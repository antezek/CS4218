package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.fileutils.Result;

public class UNIQToolRunnable implements Runnable {

	private File workingDir;
	private String stdin;
	
	public UNIQToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		UNIQTool uniqTool = new UNIQTool();
		result = uniqTool.execute(workingDir, stdin);
		Result.result = result;
	}

}