package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * HelperTest class to test the functionality of Helper
 *
 */
public class HelperTest {
	private String dirName, fileName, expected, workingDir;
	private File validFile, validDir, actual, workDir;
	private Helper helperTool;
	
	@Before
	public void setUp() throws Exception {
		helperTool = new Helper();
		workingDir = "misc";
		dirName = "HelperTest";
		validDir = new File("misc/" +dirName);
		validDir.mkdir();
		
		workDir = new File(workingDir);
		fileName = "valid.txt";
		validFile = new File("misc/" +fileName);
		validFile.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		dirName = null;
		fileName = null;
		expected = null;
		workingDir = null;
		actual = null;
		workDir = null;
		validFile.delete();
		validDir.delete();
		helperTool = null;
	}

	/**
	 * Test expected behaviour of checking for valid file directory
	 */
	@Test
	public void validFileDirectoryTest() {
		// Test valid file directory
		File f = new File(workingDir);
		assertTrue(Helper.isValidDirectory(f));
		
		// Test valid file directory 2
		workDir = new File(workingDir);
		expected = workDir.getAbsolutePath();
		actual = Helper.isValidDirectory(workDir, "misc");
		assertEquals(expected, actual.getAbsolutePath());
		
		// Test valid file directory 3
		workDir = new File(workingDir);
		expected = workDir.getAbsolutePath() + "/" +dirName;			//Bugs: OS compatible \\ changed to /
		actual = Helper.isValidDirectory(workDir, dirName);
		assertEquals(expected, actual.getAbsolutePath());
	}
	
	/**
	 * Test error handling of checking invalid file directory
	 */
	@Test
	public void invalidFileDirectoryTest() {
		// Test error handling: invalid file directory
		workingDir = "./misc/InvalidHelper";
		File f = new File(workingDir);
		assertFalse(Helper.isValidDirectory(f));
		
		// Test error handling: invalid file directory
		dirName = "InvalidTest";
		workDir = new File(workingDir);
		actual = Helper.isValidDirectory(workDir, dirName);
		assertNull(actual);
		
		// Test error handling: invalid directory name with numbers
		dirName = "54321";
		actual = Helper.isValidDirectory(workDir, dirName);
		assertNull(actual);
		
		// Test error handling: invalid directory name with spaces
		dirName = "not a directory";
		actual = Helper.isValidDirectory(workDir, dirName);
		assertNull(actual);
		
		// Test error handling: blank directory
		dirName = "";
		actual = Helper.isValidDirectory(workDir, dirName);
		assertNull(actual);
		
		dirName = " ";
		actual = Helper.isValidDirectory(workDir, dirName);
		assertNull(actual);
		
		dirName = null;
		actual = Helper.isValidDirectory(workDir, dirName);
		assertNull(actual);
		
	}
	
	/**
	 * Test expected behaviour of checking valid file
	 */
	@Test
	public void validFileTest() {
		// Test valid file with full file path
		workDir = new File(workingDir);
		expected = validFile.getAbsolutePath();
		actual = Helper.isValidFile(workDir, validFile.getAbsolutePath());
		assertEquals(expected, actual.getAbsolutePath());
		
		// Test valid file with only file name
		expected = workDir.getAbsolutePath() +"/" +fileName;		//Bugs: OS compatible \\ changed to /
		actual = Helper.isValidFile(workDir, fileName);
		assertEquals(expected, actual.getAbsolutePath());
	}
	
	/**
	 * Test error handling checking of checking invalid file
	 */
	@Test
	public void invalidFileTest() {
		// Test error handling: invalid file
		fileName = "invalid.txt";
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
		// Test error handling: invalid file
		fileName = "./misc/secondinvalid.txt";
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
		// Test error handling: invalid file name with numbers
		fileName = "12345";
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
		// Test error handling: invalid file name with spaces
		fileName = "not a file";
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
		// Test error handling: blank file
		fileName = "";
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
		fileName = " ";
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
		fileName = null;
		actual = Helper.isValidFile(workDir, fileName);
		assertNull(actual);
		
	}
	
	/**
	 * Test expected behaviour of getCommand method
	 */
	@Test
	public void getCommandTest() {
		String command = "Hello World";
		String expected = "Hello";
		String actual = Helper.getCommand(command);
		assertEquals(expected, actual);
		
		command = "This is a line of commands";
		expected = "This";
		actual = Helper.getCommand(command);
		assertEquals(expected, actual);
		
	}

}