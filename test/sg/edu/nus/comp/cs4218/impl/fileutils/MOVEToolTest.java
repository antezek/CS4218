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
	private File from;
	private File to;
	private File newFile;

	@Before
	public void setUp() throws Exception {
		moveTool = new MOVETool();
		from = new File("./misc/tempfile.txt");
		from.createNewFile();
		to = new File("./misc/MoveTest");
	}

	@After
	public void tearDown() throws Exception {
		moveTool = null;
		from.delete();
		to.delete();
		newFile.delete();
	}

	@Test
	public void moveFileTest() {
		// Test expected behaviour
		assertTrue(moveTool.move(from, to));
		assertEquals(moveTool.getStatusCode(), 0);
		assertFalse(from.exists());
		
		newFile = new File(to, from.getName());
		assertTrue(newFile.exists());
	}

}
