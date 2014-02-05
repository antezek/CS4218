package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
;
;
;

public class DELETEToolTest {
	private DELETETool delTool;
	private File toDelete;
	
	@Before
	public void setUp() throws Exception {
		delTool = new DELETETool();
		toDelete = new File("./misc/tempdeletefile.txt");
		toDelete.createNewFile();
	}

	@After
	public void tearDown() throws Exception {
		delTool = null;
		toDelete = null;
	}

	@Test
	public void deleteFileTest() {
		// Test expected behaviour
		assertTrue(delTool.delete(toDelete));
		assertFalse(toDelete.exists());
	}

}
