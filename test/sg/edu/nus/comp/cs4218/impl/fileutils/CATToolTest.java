package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * CATToolTest class to test the functionality of CATTool
 *
 */
public class CATToolTest {
	private CATTool catTool;
	private File toRead;
	private File emptyFile;
	private File workingDir;
	private String contents = "Hello World \n"
								+ "This is a CATToolTest";
	
	@Before
	public void setUp() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		
		catTool = new CATTool();
		toRead = new File("./misc/tempreadfile.txt");
		toRead.createNewFile();
		
		emptyFile = new File("./misc/tempemptyfile.txt");
		emptyFile.createNewFile();
		
		// Adding contents to read from file
		FileWriter fstream = new FileWriter(toRead, true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(contents);
		out.close();
	}

	@After
	public void tearDown() throws Exception {
		catTool = null;
		toRead.delete();
		emptyFile.delete();
	}

	/**
	 * Test expected behaviour of reading string from file. 
	 */
	@Test
	public void getStringForNonEmptyFileTest() {
		String result = catTool.getStringForFile(toRead);
		assertEquals(contents, result);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of reading from valid file
	 */
	@Test
	public void executeCatForValidFileTest() {
		File workingDir = new File(System.getProperty("user.dir"));
		String stdin = "cat " + workingDir + "/misc/tempreadfile.txt";
		String result = catTool.execute(workingDir, stdin);
		assertEquals(contents, result);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of reading from empty file.
	 */
	@Test
	public void getStringForEmptyFileTest() {
		String expected = "File is empty";
		String result = catTool.getStringForFile(emptyFile);
		assertEquals(expected, result);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of reading from valid file
	 */
	@Test
	public void executeCatForEmptyFileTest() {
		String expected = "File is empty";
		String stdin = "cat " + workingDir + "/misc/tempemptyfile.txt";
		String result = catTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of reading from invalid file.
	 */
	@Test
	public void executeCatForInvalidFileTest() {
		String expected = "Error: file not found";
		File workingDir = new File(System.getProperty("user.dir"));
		String stdin = "cat " + workingDir + "/misc/invalidFile.txt";
		String result = catTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(catTool.getStatusCode(), 1);
	}

}
