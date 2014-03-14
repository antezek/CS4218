package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * CDToolTest class to test the functionality of CDTool
 *
 */
public class CDToolTest {
	private CDTool cdTool;
	private File workingDir, tempcdfile;

	@Before
	public void setUp() throws Exception {
		cdTool = new CDTool();
		workingDir = new File(System.getProperty("user.dir"));
		tempcdfile = new File("./misc/tempcdfile.txt");
		tempcdfile.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		cdTool = null;
		tempcdfile.delete();
	}

	/**
	 * Test expected behaviour of changing to valid directory
	 */
	@Test
	public void changeValidDirectoryTest() {
		String dir = "./misc";
		File f = new File(dir);
		File actual = cdTool.changeDirectory(dir);
		assertEquals(f.getAbsolutePath(), actual.getAbsolutePath());
		assertEquals(cdTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of changing to valid directory
	 */
	@Test
	public void executeChangeValidDirectoryTest() {
		String stdin = "cd misc";
		String expected = "Working dir changed to: " +workingDir +"\\misc";		//Bugs: OS compatible \\misc changed to /misc
		String actual = cdTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(cdTool.getStatusCode(), 0);
	}
	
	
	/**
	 * Test error handling of changing to invalid file directory
	 * File is not a directory
	 */
	@Test
	public void changeInvalidFileDirectoryTest() {
		String dir = tempcdfile.getAbsolutePath();
		File actual = cdTool.changeDirectory(dir);
		assertNull(actual);
		assertEquals(cdTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling of changing to invalid file directory
	 * File is not a directory
	 */
	@Test
	public void executeChangeInvalidDirectoryTest() {
		String stdin = "cd " +tempcdfile.getAbsolutePath();
		String expected = "Error: not a directory";
		String actual = cdTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(cdTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling of changing to invalid file directory
	 * Directory does not exist
	 */
	@Test
	public void changeNonExistentDirectoryTest() {
		String dir = "./invalid";
		File actual = cdTool.changeDirectory(dir);
		assertNull(actual);
		assertEquals(cdTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling of changing to invalid file directory
	 * Directory does not exist
	 */
	@Test
	public void executeChangeNonExistentDirectoryTest() {
		String stdin = "cd invalid";
		String expected = "Error: not a directory";
		String actual = cdTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(cdTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling of changing to null file directory
	 */
	@Test
	public void executeChangeNullDirectoryTest() {
		String stdin = "cd ";
		String expected = "Error: directory cannot be null";
		String actual = cdTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(cdTool.getStatusCode(), 1);
	}

}