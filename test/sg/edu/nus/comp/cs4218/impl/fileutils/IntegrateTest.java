package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.Shell;

public class IntegrateTest {

	Shell sh;
	Runnable run = null;
	ITool tool = null;

	public void before() {
		sh = new Shell();

	}

	// Test "echo abcde | cut -c 1-2"
	
	public void componentIntegrateTest() {
		sh.runCmd("echo abcde | cut -c 1-2");
	}

}
