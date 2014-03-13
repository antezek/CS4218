package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MOVEToolTest {
	private MOVETool moveTool;
	private File workingDir;
	private File from, from2, from3, from4;
	private File to, to2;
	private File existingFile, invalidFile, invalidDir, newFile1, newFile2;

	@Before
	public void setUp() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		moveTool = new MOVETool();
		from = new File("./misc/tempmovefile.txt");
		from.createNewFile();
		to = new File("./misc/TestFolder");
		to.mkdir();
		from2 = new File("./misc/tempmovefile2.txt");
		from2.createNewFile();
		to2 = new File("./misc/TestFolder2");
		to2.mkdir();
		from3 = new File("./misc/tempmovefile3.txt");
		from3.createNewFile();
		from4 = new File("./misc/tempmovefile4.txt");
		from4.createNewFile();
		existingFile = new File("./misc/TestFolder/tempmovefile4.txt");
		existingFile.createNewFile();
		invalidFile = new File("invalidFile");
		invalidDir = new File("invalidDir");
		newFile1 = new File("");
		newFile2 = new File("");
	}

	@After
	public void tearDown() throws Exception {
		moveTool = null;
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
	 * Test expected behaviour of moving file
	 */
	@Test
	public void moveFileTest() {
		// Test expected behaviour
		assertTrue(moveTool.move(from, to));
		assertEquals(moveTool.getStatusCode(), 0);
		assertFalse(from.exists());
		newFile1 = new File(to, from.getName());
		assertTrue(newFile1.exists());
	}

	/**
	 * Test expected behaviour of moving file
	 */
	@Test
	public void executeMoveValidFileTest() {
		String stdin = "move " + from2.getAbsolutePath() + " " + to2.getAbsolutePath();
		String expected = "Moved file tempmovefile2.txt to " + to2.getAbsolutePath();
		String result = moveTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(moveTool.getStatusCode(), 0);
		assertFalse(from2.exists());
		newFile2 = new File(to2, from2.getName());
		assertTrue(newFile2.exists());
	}

	/**
	 * Test error handling: file already exists in dir
	 */
	@Test
	public void executeCopyValidFileExistsTest() {
		String stdin = "copy " + from4.getAbsolutePath() + " " + to.getAbsolutePath();
		String expected = "Error: failed to move file";
		String result = moveTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(moveTool.getStatusCode(), 1);
		assertTrue(from4.exists());
	}

	/**
	 * Test error handling: move invalid file to valid dir
	 */
	@Test
	public void executeMoveInvalidFileTest() {
		String stdin = "move " + invalidFile.getAbsolutePath() + " " + to2.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String result = moveTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(moveTool.getStatusCode(), 1);
	}

	/**
	 * Test error handling: move valid file to invalid dir
	 */
	@Test
	public void executeMoveInvalidDirTest() {
		String stdin = "move " + from3.getAbsolutePath() + " " + invalidDir.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String result = moveTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(moveTool.getStatusCode(), 1);
	}

	/**
	 * Test error handling: move invalid file to invalid dir
	 */
	@Test
	public void executeMoveInvalidFileDirTest() {
		String stdin = "move " + invalidFile.getAbsolutePath() + " " + invalidDir.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String result = moveTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(moveTool.getStatusCode(), 1);
	}

	/**
	 * Test error handling: move null file test
	 */
	@Test
	public void executeMoveNullFileTest() {
		String stdin = "move file directory error";
		String expected = "Error: file name null";
		String result = moveTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(moveTool.getStatusCode(), 1);
	}

}
