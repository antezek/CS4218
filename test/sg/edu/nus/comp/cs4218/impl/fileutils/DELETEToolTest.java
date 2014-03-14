package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * DELETEToolTest class to test the functionality of DELETETool
 *
 */
public class DELETEToolTest {
	private DELETETool delTool;
	private File workingDir;
	private File toDelete, toDelete2, toDelete3, toDelete4, tempFile;
	
	@Before
	public void setUp() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		delTool = new DELETETool();
		toDelete = new File("./misc/tempdeletefile.txt");
		toDelete.createNewFile();
		toDelete2 = new File("tempdeletefile2.txt");
		toDelete2.createNewFile();
		toDelete3 = new File("./misc/tempdeletefile3.txt");
		toDelete3.createNewFile();
		toDelete4 = new File("tempdeletefile4.txt");
		toDelete4.createNewFile();
		tempFile = new File("tempFile");
	}

	@After
	public void tearDown() throws Exception {
		delTool = null;
		toDelete.delete();
		toDelete2.delete();
		toDelete3.delete();
		toDelete4.delete();
		tempFile.delete();
	}

	/**
	 * Test expected behaviour of deleting a file
	 */
	@Test
	public void deleteFileTest() {
		// full file path
		assertTrue(delTool.delete(toDelete));
		assertFalse(toDelete.exists());
		assertEquals(delTool.getStatusCode(), 0);
		
		// simple file name
		assertTrue(delTool.delete(toDelete2));
		assertFalse(toDelete2.exists());
		assertEquals(delTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of deleting a file with full file path
	 */
	@Test
	public void executeDeleteFullFilePathTest() {
		String stdin = "delete " +toDelete3.getAbsolutePath();
		String expected = "Deleted file " +toDelete3.getAbsolutePath();
		String actual = delTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertFalse(toDelete3.exists());
		assertEquals(delTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of deleting a file with file name
	 */
	@Test
	public void executeDeleteSimpleFileNameTest() {
		String stdin = "delete " +toDelete4.getName();
		String expected = "Deleted file " +toDelete4.getAbsolutePath();
		String actual = delTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertFalse(toDelete4.exists());
		assertEquals(delTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of deleting invalid file
	 */
	@Test
	public void executeDeleteInvalidFileNameTest() {
		String stdin = "delete notvalid.txt";
		String expected = "Error: file not found";
		String actual = delTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(delTool.getStatusCode(), 1);
	}
	
	/**
	 * Test expected behaviour of deleting blank file
	 */
	@Test
	public void executeDeleteBlankFileNameTest() {
		String stdin = "delete ";
		String expected = "Error: file name null";
		String actual = delTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(delTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling for IOException
	 * @throws IOException
	 */
	@Test
	public void uniqTestForIOExceptionFile() throws IOException {
		boolean expected = false;
		final RandomAccessFile raFile = new RandomAccessFile(tempFile, "rw");
		raFile.getChannel().lock();
		boolean actual = delTool.delete(tempFile);
		assertEquals(expected, actual);
		raFile.close();
	}
	
	/**
	 * Test error handling for IOException
	 * @throws IOException
	 */
	@Test
	public void uniqExecuteTestForIOExceptionFile() throws IOException {
		String expected = "Error: failed to delete file";
		String stdin = "delete " +tempFile.getName();
		final RandomAccessFile raFile = new RandomAccessFile(tempFile, "rw");
		raFile.getChannel().lock();
		String actual = delTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		raFile.close();
	}

}
