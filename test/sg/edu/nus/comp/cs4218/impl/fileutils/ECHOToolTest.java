package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
;

/**
 * ECHOToolTest class to test the functionality of ECHOTool
 *
 */
public class ECHOToolTest {
	private ECHOTool eTool;
	
	@Before
	public void setUp() throws Exception {
		eTool = new ECHOTool();
	}

	@After
	public void tearDown() throws Exception {
		eTool = null;
	}

	/**
	 * Test expected behaviour for echoing a valid text
	 */
	@Test
	public void echoValidTextTest() {
		String toEcho = "Hello World";
		String result = eTool.echo(toEcho);
		assertEquals(toEcho, result);
	}
	
	/**
	 * Test expected behaviour of echoing valid system variable
	 */
	@Test
	public void echoValidSystemVariableTest() {
		String toEcho = "homepath";
		String expected = System.getenv(toEcho);
		String result = eTool.echo(toEcho);
		assertEquals(expected, result);
	}
	
	/**
	 * Test error handling of echoing an invalid system variable
	 */
	@Test
	public void echoInvalidSystemVariableTest() {
		String toEcho = "homebase";
		String result = eTool.echo(toEcho);
		assertEquals(toEcho, result);
	}

}
