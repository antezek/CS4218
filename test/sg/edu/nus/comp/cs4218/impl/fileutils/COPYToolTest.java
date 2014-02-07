package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * COPYToolTest class to test the functionality of COPYTool
 *
 */
public class COPYToolTest {
	private COPYTool copyTool;
	private File from;
	private File to;
	private File newFile;

	@Before
	public void setUp() throws Exception {
		copyTool = new COPYTool();
		from = new File("./misc/tempcopyfile.txt");
		from.createNewFile();
		to = new File("./misc/TestFolder");
		to.mkdir();
	}

	@After
	public void tearDown() throws Exception {
		copyTool = null;
		newFile.delete();
		from.delete();
		to.delete();
	}

	/**
	 * Test expected behaviour of copying file
	 */
	@Test
	public void moveFileTest() {
		// Test expected behaviour
		assertTrue(copyTool.copy(from, to));
		assertEquals(copyTool.getStatusCode(), 0);
		assertTrue(from.exists());
		
		newFile = new File(to, from.getName());
		assertTrue(newFile.exists());
	}

}
