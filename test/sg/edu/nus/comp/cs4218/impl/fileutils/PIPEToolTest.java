package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * PIPEToolTest class to test the functionality of PIPETool
 *
 */
public class PIPEToolTest {
	private PIPETool pt;

	@Before
	public void setUp() throws Exception {
		pt = new PIPETool();
	}

	@After
	public void tearDown() throws Exception {
		pt = null;
	}

	/*
	 * checkValidCommandTypeForPipeTest() Check command is valid for PIPE. 1st
	 * args would be just "pwd","ls","cat","echo","grep" 2nd args would be
	 * "grep" only
	 */
	@Test
	public void checkValidCommandTypeForFirstInputTest() {
		String[] cmdList1 = { "pwd", "ls", "cat", "echo", "grep" };
		for (int i = 0; i < cmdList1.length; i++) {
			assertTrue(pt.checkValidCommandTypeForPipe(cmdList1[i], true));
		}
	}

	/**
	 * Test error handling for first input
	 */
	@Test
	public void checkInvalidCommandTypeForFirstInputTest() {
		String[] cmdList2 = { "copy", "move", "delete" };
		for (int i = 0; i < cmdList2.length; i++) {
			assertFalse(pt.checkValidCommandTypeForPipe(cmdList2[i], true));
		}
	}

	/**
	 * Test expected behaviour of valid subsequent inputs
	 */
	@Test
	public void checkValidCommandTypeForSubsequentInputTest() {
		assertTrue(pt.checkValidCommandTypeForPipe("grep", false));
	}

	/**
	 * Test error handling for invalid subsequent inputs
	 */
	@Test
	public void checkInvalidCommandTypeForSubsequentInputTest() {
		String[] cmdList3 = { "cd", "ls", "move", "delete", "pwd", "ls", "cat",
				"echo" };

		for (int i = 0; i < cmdList3.length; i++) {
			assertFalse(pt.checkValidCommandTypeForPipe(cmdList3[i], false));
		}
	}

}
