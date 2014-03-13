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
	private File a, b;
	private File validFile, emptyFile, validDir;

	@Before
	public void setUp() throws Exception {
		lsTool = new LSTool();
		validFile = new File("./misc/LSToolTestValid");
		emptyFile = new File("./misc/LSToolTestEmpty");
		validDir = new File("./misc/LSToolTestValid/ValidDir");
		a = new File(validFile, "aaa.txt");
		b = new File(validFile, "bbb.txt");
		
		validFile.mkdir();
		emptyFile.mkdir();
		validDir.mkdir();
		a.createNewFile();
		b.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		lsTool = null;
		a.delete();
		b.delete();
		validDir.delete();
		validFile.delete();
		emptyFile.delete();
	}

	/**
	 * Test expected behaviour of listing valid directory
	 */
	@Test
	public void getValidDirectoryListingTest() {
		File f = validFile;
		String expected = "aaa.txt\n" + "bbb.txt\n" + "ValidDir";
		String result = lsTool.getStringForFiles(lsTool.getFiles(f));
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of listing valid directory
	 */
	@Test
	public void executeGetValidDirectoryListingTest() {
		File f = validFile;
		String stdin = "ls";
		String expected = "aaa.txt\n" + "bbb.txt\n" + "ValidDir";
		String result = lsTool.execute(f, stdin);
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of listing empty directory
	 */
	@Test
	public void getEmptyDirectoryListingTest() {
		File f = emptyFile;
		String stdin = "ls";
		String expected = "Error: No files in working directory";
		String result = lsTool.execute(f, stdin);
		assertEquals(expected, result);
		assertEquals(lsTool.getStatusCode(), 0);
	}

}
