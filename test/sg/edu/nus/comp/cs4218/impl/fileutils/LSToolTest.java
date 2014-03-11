package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * LSToolTest class to test the functionality of LSTool
 *
 */
public class LSToolTest {
	private LSTool lsTool;
	private String validDir;
	private String emptyDir;
	private File a;
	private File b;
	private File valid;
	private File empty;

	@Before
	public void setUp() throws Exception {
		lsTool = new LSTool();
		validDir = "./misc/LSToolTestValid";
		emptyDir = "./misc/LSToolTestEmpty";
		valid = new File(validDir);
		empty = new File(emptyDir);
		a = new File(valid, "aaa.txt");
		b = new File(valid, "bbb.txt");
		
		valid.mkdir();
		empty.mkdir();
		a.createNewFile();
		b.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		lsTool = null;
		validDir = null;
		emptyDir = null;
		a.delete();
		b.delete();
		valid.delete();
		empty.delete();
	}

	/**
	 * Test expected behaviour of listing valid directory
	 */
	@Test
	public void getValidDirectoryListingTest() {
		File f = new File(validDir);
		String expected = "aaa.txt" +"\n"
							+ "bbb.txt";
		String result = lsTool.getStringForFiles(lsTool.getFiles(f));
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of listing valid directory
	 */
	@Test
	public void executeGetValidDirectoryListingTest() {
		File f = new File(validDir);
		String stdin = "ls";
		String expected = "aaa.txt" +"\n"
							+ "bbb.txt";
		String result = lsTool.execute(f, stdin);
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of listing empty directory
	 */
	@Test
	public void getEmptyDirectoryListingTest() {
		File f = new File(emptyDir);
		String stdin = "ls";
		String expected = "Error: No files in working directory";
		String result = lsTool.execute(f, stdin);
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}

}
