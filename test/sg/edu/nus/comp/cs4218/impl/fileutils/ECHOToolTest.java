package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
;

/**
 * ECHOToolTest class to test the functionality of ECHOTool
 *
 */
public class ECHOToolTest {
	private ECHOTool echoTool;
	private File workingDir;
	
	@Before
	public void setUp() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		echoTool = new ECHOTool();
	}

	@After
	public void tearDown() throws Exception {
		echoTool = null;
	}

	/**
	 * Test expected behaviour for echoing a valid text
	 */
	@Test
	public void echoValidTextTest() {
		String toEcho = "Hello World";
		String actual = echoTool.echo(toEcho);
		assertEquals(toEcho, actual);
		assertEquals(echoTool.getStatusCode(), 0);
		
		toEcho = "12345";
		actual = echoTool.echo(toEcho);
		assertEquals(toEcho, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing a valid text
	 */
	@Test
	public void executeEchoValidTextTest() {
		String toEcho = "Hello World";
		String stdin = "echo " + toEcho;
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(toEcho, actual);
		assertEquals(echoTool.getStatusCode(), 0);
		
		toEcho = "12345";
		stdin = "echo " + toEcho;
		actual = echoTool.execute(workingDir, stdin);
		assertEquals(toEcho, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	
	/**
	 * Test expected behaviour of echoing valid system variable
	 */
	@Test
	public void echoValidSystemVariableTest() {
		String toEcho = "homepath";
		String expected = System.getenv(toEcho);
		String actual = echoTool.echo(toEcho);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour of echoing valid system variable
	 */
	@Test
	public void executeEchoValidSystemVariableTest() {
		String stdin = "echo homepath";
		String expected = System.getenv("homepath");
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of echoing an invalid system variable
	 */
	@Test
	public void echoInvalidSystemVariableTest() {
		String expected = "homebase";
		String actual = echoTool.echo(expected);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test error handling of echoing an invalid system variable
	 */
	@Test
	public void executeEchoInvalidSystemVariableTest() {
		String stdin = "echo homebase";
		String expected = "homebase";
		String result = echoTool.execute(workingDir, stdin);
		assertEquals(expected, result);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing an empty text
	 */
	@Test
	public void echoEmptyTextTest() {
		String expected = "";
		String actual = echoTool.echo(expected);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing an empty text
	 */
	@Test
	public void executeEchoEmptyTextTest() {
		String stdin = "echo";
		String expected = "Error: args null";
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 1);
	}
	
	/**
	 * Test expected behaviour for echoing a blank text
	 */
	@Test
	public void echoBlankTextTest() {
		String expected = " ";
		String actual = echoTool.echo(expected);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing a blank text
	 */
	@Test
	public void executeEchoBlankTextTest() {
		String stdin = "echo  ";
		String expected = "Error: args null";
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 1);
	}
	
	/**
	 * Test expected behaviour for echoing a large integer
	 */
	@Test
	public void echoLargeNumberTest() {
		String expected = String.valueOf(Integer.MAX_VALUE+1);
		String actual = echoTool.echo(expected);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing a large integer
	 */
	@Test
	public void executeEchoLargeNumberTest() {
		int maxNum = Integer.MAX_VALUE+1;
		String stdin = "echo " +maxNum;
		String expected = String.valueOf(maxNum);
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing zero
	 */
	@Test
	public void echoZeroNumberTest() {
		String expected = String.valueOf(00000000000);
		String actual = echoTool.echo(expected);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing zero
	 */
	@Test
	public void executeEchoZeroNumberTest() {
		String stdin = "echo " +0000000000;
		String expected = String.valueOf(0000000000);
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing negative number
	 */
	@Test
	public void echoNegativeNumberTest() {
		String expected = String.valueOf(-54321);
		String actual = echoTool.echo(expected);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}
	
	/**
	 * Test expected behaviour for echoing negative number
	 */
	@Test
	public void executeEchoNegativeNumberTest() {
		String stdin = "echo " + -12345;
		String expected = String.valueOf(-12345);
		String actual = echoTool.execute(workingDir, stdin);
		assertEquals(expected, actual);
		assertEquals(echoTool.getStatusCode(), 0);
	}

}
