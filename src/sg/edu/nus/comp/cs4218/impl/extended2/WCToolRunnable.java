package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.fileutils.Result;

public class WCToolRunnable implements Runnable {

	private File workingDir;
	private String stdin;
	
	public WCToolRunnable(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.stdin = stdin;
	}

	@Override
	public void run() {
		String result = "";
		WCTool wcTool = new WCTool();
		//System.out.println(wcTool.execute(workingDir, stdin));
		result = wcTool.execute(workingDir, stdin);
		Result.result = result;
	}

}