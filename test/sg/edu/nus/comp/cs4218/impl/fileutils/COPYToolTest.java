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
	private File from;
	private File to;
	private File from2;
	private File to2;
	private File from3;
	private File invalidFile;
	private File invalidDir;
	private File newFile1;
	private File newFile2;

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
		assertTrue(copyTool.copy(from, to));
		assertEquals(copyTool.getStatusCode(), 0);
		assertTrue(from.exists());
		newFile1 = new File(to, from.getName());
		assertTrue(newFile1.exists());
	}
	
	/**
	 * Test expected behaviour of moving file
	 */
	@Test
	public void executeMoveValidFileTest() {
		String stdin = "copy " +from2.getAbsolutePath() + " " +to2.getAbsolutePath();
		String expected = "Copied file tempcopyfile2.txt to " +to2.getAbsolutePath();
		String result = copyTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(copyTool.getStatusCode(), 0);
		assertTrue(from2.exists());
		newFile2 = new File(to2, from2.getName());
		assertTrue(newFile2.exists());
	}
	
	/**
	 * Test error handling: move invalid file to valid dir
	 */
	@Test
	public void executeMoveInvalidFileTest() {
		String stdin = "copy " +invalidFile.getAbsolutePath() + " " +to2.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String result = copyTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling: move valid file to invalid dir
	 */
	@Test
	public void executeMoveInvalidDirTest() {
		String stdin = "copy " +from3.getAbsolutePath() + " " +invalidDir.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String result = copyTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling: move invalid file to invalid dir
	 */
	@Test
	public void executeMoveInvalidFileDirTest() {
		String stdin = "copy " +invalidFile.getAbsolutePath() + " " +invalidDir.getAbsolutePath();
		String expected = "Error: file or dir not found";
		String result = copyTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(copyTool.getStatusCode(), 1);
	}
	

}