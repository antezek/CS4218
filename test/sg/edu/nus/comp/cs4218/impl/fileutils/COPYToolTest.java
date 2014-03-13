package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class COPYToolTest {
	private COPYTool copyTool;
	private File workingDir;
	private File from, from2, from3, from4;
	private File to, to2;
	private File existingFile, invalidFile, invalidDir, newFile1, newFile2;

	@Before
	public void setUp() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		copyTool = new COPYTool();
		from = new File("./misc/tempcopyfile.txt");
		from.createNewFile();
		to = new File("./misc/TestFolder");
		to.mkdir();
		from2 = new File("./misc/tempcopyfile2.txt");
		from2.createNewFile();
		to2 = new File("./misc/TestFolder2");
		to2.mkdir();
		from3 = new File("./misc/tempcopyfile3.txt");
		from3.createNewFile();
		from4 = new File("./misc/tempcopyfile4.txt");
		from4.createNewFile();
		existingFile = new File("./misc/TestFolder/tempcopyfile4.txt");
		existingFile.createNewFile();
		invalidFile = new File("invalidFile");
		invalidDir = new File("invalidDir");
		newFile1 = new File("");
		newFile2 = new File("");
	}

	@After
	public void tearDown() throws Exception {
		copyTool = null;
		from.delete();
		from2.delete();
		from3.delete();
		from4.delete();
		existingFile.delete();
		newFile1.delete();
		newFile2.delete();
		invalidFile.delete();
		to.delete();
		to2.delete();
		invalidDir.delete();
	}

	/**
	 * Test expected behaviour of copying file
	 */
	@Test
	public void copyFileTest() {
		// Test expected behaviour
		assertTrue(copyTool.copy(from, to));
		assertEquals(copyTool.getStatusCode(), 0);
		assertTrue(from.exists());
		newFile1 = new File(to, from.getName());
		assertTrue(newFile1.exists());
	}
	
	/**
	 * Test expected behaviour of copying file
	 */
	@Test
	public void executeCopyValidFileTest() {
		String stdin = "copy " +from2.getAbsolutePath() + " " +to2.getAbsolutePath();
		String expected = "Copied file tempcopyfile2.txt to " +to2.getAbsolutePath();
		String actual = copyTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(copyTool.getStatusCode(), 0);
		assertTrue(from2.exists());
		newFile2 = new File(to2, from2.getName());
		assertTrue(newFile2.exists());
	}
	
	/**
	 * Test error handling: file already exists in dir
	 */
	@Test
	public void executeCopyValidFileExistsTest() {
		String stdin = "copy " +from4.getAbsolutePath() + " " +to.getAbsolutePath();
		String expected = "Error: failed to copy file";
		String actual = copyTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(copyTool.getStatusCode(), 1);
		assertTrue(from4.exists());
	}
	
	/**
	 * Test error handling: copy invalid file to valid dir
	 */
	@Test
	public void executeCopyInvalidFileTest() {
		String stdin = "copy " +invalidFile.getAbsolutePath() + " " +to2.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String actual = copyTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling: copy valid file to invalid dir
	 */
	@Test
	public void executeCopyInvalidDirTest() {
		String stdin = "copy " +from3.getAbsolutePath() + " " +invalidDir.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String actual = copyTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling: copy invalid file to invalid dir
	 */
	@Test
	public void executeCopyInvalidFileDirTest() {
		String stdin = "copy " +invalidFile.getAbsolutePath() + " " +invalidDir.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String actual = copyTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling: copy null file test
	 */
	@Test
	public void executeMoveNullFileTest() {
		String stdin = "copy file directory error";
		String expected ="Error: file name null";
		String actual = copyTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	

}