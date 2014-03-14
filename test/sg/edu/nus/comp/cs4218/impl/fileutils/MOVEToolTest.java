package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MOVEToolTest {
	private MOVETool moveTool;
	private File workingDir;
	private File from, from2, from3, from4;
	private File to, to2;
	private File existingFile, invalidFile, invalidDir, newFile1, newFile2, tempFile;

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
		
		tempFile = new File("tempFile.txt");
		tempFile.createNewFile();
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
	
	@Test
	public void executeTest() {
		File f = new File("tempFile.txt");
		
		System.out.println(f.exists());
		
		String stdin = "move tempFile.txt misc";
		String expected = "Error: file name null";
		String actual = moveTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(moveTool.getStatusCode(), 1);
	}

}
