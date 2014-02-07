package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
	File tempcdfile;

	@Before
	public void setUp() throws Exception {
		cdTool = new CDTool();
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
		assertTrue(cdTool.getStatusCode() != 0);
	}
	
	/**
	 * Test error handling of changing to invalid file directory
	 * Directory does not exist
	 */
	@Test
	public void changeNonexistentDirectoryTest() {
		String dir = "./invalid";
		File newDir = cdTool.changeDirectory(dir);
		assertNull(newDir);
		assertTrue(cdTool.getStatusCode() != 0);
	}

}