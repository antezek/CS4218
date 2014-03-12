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
	private String dirName;
	private String fileName;
	private String expected;
	private String workingDir;
	private File validFile;
	private File result;
	private File validDir;;
	private File workDir;
	
	@Before
	public void setUp() throws Exception {
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
		result = null;
		workDir = null;
		validFile.delete();
		validDir.delete();
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
		expected = workDir.getAbsolutePath() + "/" +dirName;			//Bugs: OS compatible \\ changed to /
		result = Helper.isValidDirectory(workDir, dirName);
		assertEquals(expected, result.getAbsolutePath());
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
		result = Helper.isValidDirectory(workDir, dirName);
		assertNull(result);
		
		// Test error handling: invalid directory name with numbers
		dirName = "54321";
		result = Helper.isValidDirectory(workDir, dirName);
		assertNull(result);
		
		// Test error handling: invalid directory name with spaces
		dirName = "not a directory";
		result = Helper.isValidDirectory(workDir, dirName);
		assertNull(result);
		
		// Test error handling: blank directory
		dirName = "";
		result = Helper.isValidDirectory(workDir, dirName);
		assertNull(result);
	}
	
	/**
	 * Test expected behaviour of checking valid file
	 */
	@Test
	public void validFileTest() {
		// Test valid file with full file path
		workDir = new File(workingDir);
		expected = validFile.getAbsolutePath();
		result = Helper.isValidFile(workDir, validFile.getAbsolutePath());
		assertEquals(expected, result.getAbsolutePath());
		
		// Test valid file with only file name
		expected = workDir.getAbsolutePath() +"/" +fileName;		//Bugs: OS compatible \\ changed to /
		result = Helper.isValidFile(workDir, fileName);
		assertEquals(expected, result.getAbsolutePath());
	}
	
	/**
	 * Test error handling checking of checking invalid file
	 */
	@Test
	public void invalidFileTest() {
		// Test error handling: invalid file
		fileName = "invalid.txt";
		result = Helper.isValidFile(workDir, fileName);
		assertNull(result);
		
		// Test error handling: invalid file
		fileName = "./misc/secondinvalid.txt";
		result = Helper.isValidFile(workDir, fileName);
		assertNull(result);
		
		// Test error handling: invalid file name with numbers
		fileName = "12345";
		result = Helper.isValidFile(workDir, fileName);
		assertNull(result);
		
		// Test error handling: invalid file name with spaces
		fileName = "not a file";
		result = Helper.isValidFile(workDir, fileName);
		assertNull(result);
		
		// Test error handling: blank file
		fileName = "";
		result = Helper.isValidFile(workDir, fileName);
		assertNull(result);
		
	}

}
