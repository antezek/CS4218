package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IPipingTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class PIPETool extends ATool implements IPipingTool{

	public PIPETool() {
		super(null);
	}

	@Override
	public String pipe(ITool from, ITool to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pipe(String stdout, ITool to) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String execute(File workingDir, String stdin) {
		// TODO Auto-generated method stub
		return null;
	}

}
