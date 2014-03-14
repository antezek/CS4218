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
		String result = "";
		COMMTool commTool = new COMMTool();
		result = commTool.execute(workingDir, stdin);
		//System.out.println(commTool.execute(workingDir, stdin));
		Result.result = result;
	}
}
