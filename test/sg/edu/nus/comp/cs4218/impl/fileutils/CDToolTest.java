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
	private File workingDir;
	File tempcdfile;

	@Before
	public void setUp() throws Exception {
		cdTool = new CDTool();
		workingDir = new File(System.getProperty("user.dir"));
		tempcdfile = new File("./misc/tempcdfile.txt");
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
		File newDir = cdTool.changeDirectory(dir);
		assertEquals(f.getAbsolutePath(), newDir.getAbsolutePath());
		assertEquals(cdTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of changing to valid directory
	 */
	@Test
	public void executeChangeValidDirectoryTest() {
		String stdin = "cd misc";
		String expected = "Working dir changed to: " +workingDir +"\\misc";
		String newDir = cdTool.execute(workingDir, stdin);
		assertEquals(expected, newDir);
		assertEquals(cdTool.getStatusCode(), 0);
	}
	
	
	/**
	 * Test error handling of changing to invalid file directory
	 * File is not a directory
	 */
	@Test
	public void changeInvalidFileDirectoryTest() {
		String dir = tempcdfile.getAbsolutePath();
		File newDir = cdTool.changeDirectory(dir);
		assertNull(newDir);
		assertEquals(cdTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling of changing to invalid file directory
	 * File is not a directory
	 */
	@Test
	public void executeChangeInvalidDirectoryTest() {
		String stdin = "cd " +tempcdfile.getName();
		String expected = "Error: not a directory";
		String newDir = cdTool.execute(workingDir, stdin);
		assertEquals(expected, newDir);
		assertEquals(cdTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling of changing to invalid file directory
	 * Directory does not exist
	 */
	@Test
	public void changeNonExistentDirectoryTest() {
		String dir = "./invalid";
		File newDir = cdTool.changeDirectory(dir);
		assertNull(newDir);
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
		String newDir = cdTool.execute(workingDir, stdin);
		assertEquals(expected, newDir);
		assertEquals(cdTool.getStatusCode(), 1);
	}

}
