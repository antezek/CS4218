package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LSToolTest {
	private LSTool lsTool;
	private String workingDir;

	@Before
	public void setUp() throws Exception {
		lsTool = new LSTool();
	}

	@After
	public void tearDown() throws Exception {
		lsTool = null;
		workingDir = null;
	}

	@Test
	public void getValidDirectoryListingTest() {
		workingDir = "./misc/LSToolTest/lstoolvalid";
		File f = new File(workingDir);
		String expected = "aaa.txt" +"\n"
							+ "bbb.txt";
		String result = lsTool.getStringForFiles(lsTool.getFiles(f));
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}
	
	@Test
	public void getEmptyDirectoryListingTest() {
		workingDir = "./misc/LSToolTest/lstoolempty";
		File f = new File(workingDir);
		String expected = "Error: No files in working directory";
		String result = lsTool.getStringForFiles(lsTool.getFiles(f));
		assertEquals(result, expected);
		assertEquals(lsTool.getStatusCode(), 0);
	}

}
