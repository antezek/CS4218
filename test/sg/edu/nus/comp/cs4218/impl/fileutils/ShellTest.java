package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.Shell;
import sg.edu.nus.comp.cs4218.impl.extended2.COMMTool;
import sg.edu.nus.comp.cs4218.impl.extended2.COMMToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.CUTTool;
import sg.edu.nus.comp.cs4218.impl.extended2.CUTToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTETool;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTEToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTTool;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.UNIQTool;
import sg.edu.nus.comp.cs4218.impl.extended2.UNIQToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.WCTool;
import sg.edu.nus.comp.cs4218.impl.extended2.WCToolRunnable;

/**
 * ShellTest class to test the basic functionality of Shell class
 *
 */
public class ShellTest {
	private Shell shell;

	@Before
	public void setUp() throws Exception {
		shell = new Shell();
		Shell.setupHm();
	}

	@After
	public void tearDown() throws Exception {
		shell = null;
	}

	/**
	 * testToolInstance() Test the input parse is working correctly and retrieve
	 * the appropriate ITool
	 */
	@Test
	public void testBasicToolInstance() {
		ITool tool = null;
		String input[] = { "cat", "cd", "copy", "delete", "echo", "grep", "ls", "move", "pwd" , };
		Runnable run;

		for (int i = 0; i < input.length; i++) {
			tool = shell.parse(input[i]);
			run = shell.execute(tool);
			if (i == 0) {
				assertTrue(tool instanceof CATTool);
				assertTrue(run instanceof CATToolRunnable);
			}
			if (i == 1) {
				assertTrue(tool instanceof CDTool);
				assertTrue(run instanceof CDToolRunnable);
			}
			if (i == 2) {
				assertTrue(tool instanceof COPYTool);
				assertTrue(run instanceof COPYToolRunnable);
			}
			if (i == 3) {
				assertTrue(tool instanceof DELETETool);
				assertTrue(run instanceof DELETEToolRunnable);
			}
			if (i == 4) {
				assertTrue(tool instanceof ECHOTool);
				assertTrue(run instanceof ECHOToolRunnable);
			}
			if (i == 5) {
				assertTrue(tool instanceof GREPTool);
				assertTrue(run instanceof GREPToolRunnable);
			}
			if (i == 6) {
				assertTrue(tool instanceof LSTool);
				assertTrue(run instanceof LSToolRunnable);
			}
			if (i == 7) {
				assertTrue(tool instanceof MOVETool);
				assertTrue(run instanceof MOVEToolRunnable);
			}
			if (i == 8) {
				assertTrue(tool instanceof PWDTool);
				assertTrue(run instanceof PWDToolRunnable);
			}
		}
	}
	
	/**
	 * testToolInstance() Test the input parse is working correctly and retrieve
	 * the appropriate ITool
	 */
	@Test
	public void testExtendedToolInstance() {
		ITool tool = null;
		String input[] = { "comm", "cut", "paste", "sort", "uniq", "wc" };
		Runnable run;

		for (int i = 0; i < input.length; i++) {
			tool = shell.parse(input[i]);
			run = shell.execute(tool);
			if (i == 0) {
				assertTrue(tool instanceof COMMTool);
				assertTrue(run instanceof COMMToolRunnable);
			}
			if (i == 1) {
				assertTrue(tool instanceof CUTTool);
				assertTrue(run instanceof CUTToolRunnable);
			}
			if (i == 2) {
				assertTrue(tool instanceof PASTETool);
				assertTrue(run instanceof PASTEToolRunnable);
			}
			if (i == 3) {
				assertTrue(tool instanceof SORTTool);
				assertTrue(run instanceof SORTToolRunnable);
			}
			if (i == 4) {
				assertTrue(tool instanceof UNIQTool);
				assertTrue(run instanceof UNIQToolRunnable);
			}
			if (i == 5) {
				assertTrue(tool instanceof WCTool);
				assertTrue(run instanceof WCToolRunnable);
			}
		}
	}
}
