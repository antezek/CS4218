package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * CATToolTest class to test the functionality of CATTool
 *
 */
public class CATToolTest {
	private CATTool catTool;
	private File workingDir;
	private File toRead, emptyFile, tempFile;
	private String contents = "Hello World \n"
								+ "This is a CATToolTest";
	String pathSep="";

	
	@Before
	public void setUp() throws IOException {
		pathSep=java.nio.file.FileSystems.getDefault().getSeparator();
		workingDir = new File(System.getProperty("user.dir"));
		catTool = new CATTool();
		toRead = new File("."+pathSep+"misc"+pathSep+"tempreadfile.txt");
		toRead.createNewFile();
		emptyFile = new File("."+pathSep+"misc"+pathSep+"tempemptyfile.txt");
		emptyFile.createNewFile();
		tempFile = new File("tempCatFile");
		
		// Adding contents to read from file
		FileWriter fstream = new FileWriter(toRead, true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(contents);
		out.close();
		fstream.close();
		
	}

	@After
	public void tearDown() throws Exception{
		System.gc();
		catTool = null;
		toRead.delete();
		emptyFile.delete();
		tempFile.delete();
	}

	/**
	 * Test expected behaviour of reading string from file. 
	 */
	@Test
	public void getStringForNonEmptyFileTest() {
		String actual = catTool.getStringForFile(toRead);
		assertEquals(contents, actual);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of reading from valid file
	 */
	@Test
	public void executeCatForValidFileTest() {
		File workingDir = new File(System.getProperty("user.dir"));
		String stdin = "cat " + workingDir +pathSep+"misc"+pathSep+"tempreadfile.txt";
		String actual = catTool.execute(workingDir, stdin);
		assertEquals(contents, actual);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of reading from empty file.
	 */
	@Test
	public void getStringForEmptyFileTest() {
		String expected = "File is empty";
		String actual = catTool.getStringForFile(emptyFile);
		assertEquals(expected, actual);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of reading from valid file
	 */
	@Test
	public void executeCatForEmptyFileTest() {
		String stdin = "cat " + workingDir +pathSep+"misc"+pathSep+"tempemptyfile.txt";
		String expected = "File is empty";
		String actual = catTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of reading from invalid file.
	 */
	@Test
	public void executeCatForInvalidFileTest() {
		String expected = "Error: file not found";
		String stdin = "cat " + workingDir +pathSep+"misc"+pathSep+"invalidFile.txt";
		String result = catTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(catTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling null input.
	 */
	@Test
	public void executeCatForNullInputTest() {
		String expected = "Error: file name null";
		String stdin = "cat ";
		String result = catTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(catTool.getStatusCode(), 1);
	}
	
	/**
	 * Test error handling dash(-) input.
	 */
	@Test
	public void executeCatForDashInputTest() {
		String expected = "";
		String stdin = "cat -";
		String result = catTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(catTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling for IOException
	 * @throws IOException
	 */
	@Test
	public void executeCatForIOExceptionFile() throws IOException {
		String expected = "Unable to read file";
		final RandomAccessFile raFile = new RandomAccessFile(tempFile, "rw");
		raFile.getChannel().lock();
		String actual = catTool.getStringForFile(tempFile);
		assertEquals(expected, actual);
		raFile.close();
	}

}
