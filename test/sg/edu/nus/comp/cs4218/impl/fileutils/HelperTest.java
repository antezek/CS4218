package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelperTest {
	String dir;
	String folderName;
	String expected;
	File result;
	File f;
	File workDir;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		dir = null;
		folderName = null;
		expected = null;
		result = null;
		f = null;
		workDir = null;
	}

	@Test
	public void validFileDirectoryTest() {
		// Test valid file directory
		dir = "./misc/HelperTest";
		f = new File(dir);
		assertTrue(Helper.isValidDirectory(f));
		
		// Test valid file directory 2
		String workingDir = "./misc";
		workDir = new File(workingDir);
		String folderName = "HelperTest";
		f = new File(folderName);
		
		expected = workDir.getAbsolutePath() + "\\" +folderName;
		result = Helper.isValidDirectory(workDir, folderName);
		assertEquals(result.getAbsolutePath(), expected);
		
	}
	
	@Test
	public void invalidFileDirectoryTest() {
		// Test error handling: invalid file directory
		dir = "./misc/InvalidHelper";
		f = new File(dir);
		assertFalse(Helper.isValidDirectory(f));
		
		// Test error handling: invalid file directory
		folderName = "InvalidTest";
		workDir = new File(dir);
		result = Helper.isValidDirectory(workDir, folderName);
		assertNull(result);
	}

}
