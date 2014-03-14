package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.fileutils.IPwdTool;

public class PWDToolTest {
	// TODO Always test against the interface!
	private IPwdTool pwdtool;
	private File workingDir, nonExistDir, invalidDir;
	
	@Before
	public void before() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		pwdtool = new PWDTool();
		nonExistDir = new File("Invalid");
		invalidDir = new File("./misc/Invalid.txt");
		invalidDir.createNewFile();
	}

	@After
	public void after() {
		pwdtool = null;
		invalidDir.delete();
	}

	@Test
	public void getStringForDirectoryTest() throws IOException {
		// Test expected behavior
		// Create a tmp-file and get (existing) parent directory
		String existsDirString = File.createTempFile("exists", "tmp")
				.getParent();
		File existsDir = new File(existsDirString);
		String dirString = pwdtool.getStringForDirectory(existsDir);
		assertTrue(dirString.equals(existsDirString));
		assertEquals(pwdtool.getStatusCode(), 0);
	}

	@Test
	public void getStringForNonExistingDirectoryTest() throws IOException {
		// Test error-handling 1
		// Reference non-existing file
		File notExistsDir = new File("notexists");
		pwdtool.getStringForDirectory(notExistsDir);
		assertEquals(pwdtool.getStatusCode(), 1);
	}

	@Test
	public void getStringForNullDirectoryTest() throws IOException {
		// Test error-handling 2
		String expected = "Error: Cannot find working directory";
		String actual = pwdtool.getStringForDirectory(null);
		assertEquals(expected, actual);
		assertEquals(pwdtool.getStatusCode(), 1);
	}

	@Test
	public void executeGetStringForNullDirectoryTest() throws IOException {
		// Test error-handling 2
		String expected = workingDir.getAbsolutePath();
		String actual = pwdtool.execute(workingDir, null);
		assertEquals(expected, actual);
		assertEquals(pwdtool.getStatusCode(), 0);
	}

	@Test
	public void getStringForNonExistantDirectoryTest() throws IOException {
		// Test error-handling 3
		String expected = "Error: Cannot find working directory";
		String actual = pwdtool.getStringForDirectory(nonExistDir);
		assertEquals(expected, actual);
		assertEquals(pwdtool.getStatusCode(), 1);
	}

	@Test
	public void getStringForNonValidDirectoryTest() throws IOException {
		// Test error-handling 3
		String expected = "Error: Cannot find working directory";
		String actual = pwdtool.getStringForDirectory(invalidDir);
		assertEquals(expected, actual);
		assertEquals(pwdtool.getStatusCode(), 1);
	}

	@Test
	public void executeGetStringForDirectoryTest() throws IOException {
		// Test error-handling 3
		String expected = workingDir.getAbsolutePath();
		String actual = pwdtool.execute(workingDir, "pwd");
		assertEquals(expected, actual);
		assertEquals(pwdtool.getStatusCode(), 0);
	}

}
