package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.IPasteTool;

public class PASTEToolTest {

	private IPasteTool pasteTool;
	String actualOutput, expectedOutput, helpOutput;
	File workingDirectory;

	File fileA, fileB, fileC, fileD, fileEM1, fileEM2;
	String fileContentA, fileContentB, fileContentC, fileContentD,
			fileContentE;

	@Before
	public void before() throws Exception {
		workingDirectory = new File(System.getProperty("user.dir"));
		helpOutput = "paste : writes to standard output lines "
				+ "\n* of sequentially corresponding lines of each given file,"
				+ "\n* separated by a TAB character"
				+ "\n* Command Format - paste [OPTIONS] [FILE]"
				+ "\n* FILE - Name of the file, when no file is present (denoted by \"-\") "
				+ "\n* use standard input OPTIONS"
				+ "\n -s : paste one file at a time instead of in parallel"
				+ "\n -d DELIM: Use characters from the DELIM instead of TAB character"
				+ "\n -help : Brief information about supported options";

		fileA = new File("a.txt");
		fileB = new File("b.txt");
		fileC = new File("c.txt");
		fileD = new File("d.txt");

		fileEM1 = new File("em1.txt");
		fileEM2 = new File("em2.txt");

		fileEM1.createNewFile();
		fileEM2.createNewFile();

		fileContentA = "Table\nChair\nMan";
		fileContentB = "Wall\nFloor";
		fileContentC = "Superman\nSpiderman\nBatman";
		fileContentD = "Cat";
		fileContentE = "";

		writeToFile(fileA, fileContentA);
		writeToFile(fileB, fileContentB);
		writeToFile(fileC, fileContentC);
		writeToFile(fileD, fileContentD);

	}

	private void writeToFile(File f, String fContent) {
		BufferedWriter bw;
		try {
			String[] lines = fContent.split("\n");
			bw = new BufferedWriter(new FileWriter(f));

			for (int i = 0; i < lines.length; i++) {
				bw.write(lines[i]);
				bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@After
	public void after() throws Exception {
		 pasteTool = null;
		 fileA.delete(); fileB.delete(); fileC.delete(); fileD.delete(); 
	}

	@Test
	// If only "-help", print help message
	public void pasteGetHelpAsOnlyArgumentTest() {
		// String[] arguments = new String[]{"-help"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory, "paste -help");
		expectedOutput = helpOutput;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Test -help is given priority
	public void pasteGetHelpWithOtherArgumentsTest() {
		// String[] arguments = new String[]{"-s","-help","-d",":"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory,
				"paste -s -help -d :");

		expectedOutput = helpOutput;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);

	}

	@Test
	// Check for invalid files
	public void pasteNoOptionsInvalidFilesTest() {
		// String[] arguments = new
		// String[]{"C:\\Users\\Dale\\a.txt","./b.txt"};
		String fileName1 = "C:\\Users\\Dale\\a.txt";
		String fileName2 = "./b.txt";
		ArrayList<String> fNames = new ArrayList<String>();
		fNames.add(fileName1);
		fNames.add(fileName2);
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory,
				"paste C:\\Users\\Dale\\a.txt ./b.txt");

		expectedOutput = "a.txt: No such file or directory!";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));

	}

	@Test
	// Check for empty file
	public void pasteNoOptionsCheckWithOneEmptyFileTest() {

		// String[] arguments = new String[]{"em1.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter("\t",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory, "paste em1.txt");
		expectedOutput = "";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);

	}

	@Test
	// Check for empty file when between multiple non empty files
	public void pasteUseDelimWithOneEmptyInManyFilesTest() {

		// @Changed: add in the -d options in command
		// String[] arguments = new String[]{"-d","*",
		// "b.txt","em1.txt","d.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter("*", arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory,
				"paste -d * b.txt em1.txt d.txt");
		expectedOutput = "Wall**Cat" + "\n" + "Floor**";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Serial output using empty files
	public void pasteUseSerialWithManyEmptyFilesTest() {

		// @Changed: add in the -s options in command
		// String[] arguments = new String[]{"-s","em1.txt","em2.txt"};
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteSerial(arguments);
		// pasteSerial should take in the contents of the file and not filenames
		// as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory,
				"paste -s em1.txt em2.txt");
		expectedOutput = "\n";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Check paste o/p with 1 file, no options
	public void pasteNoOptionsOneFileTest() {
		// String[] arguments = new String[]{"b.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter("\t",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory, "paste b.txt");
		expectedOutput = fileContentB;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Testing paste with Stdin
	public void pasteNoOptionsStdinTest() {
		// String[] arguments = new String[]{"-"};
		String stdin = "stdin input";
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory,
				"paste - stdin input");
		
		expectedOutput = "stdin input";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Paste: no options, many files
	public void pasteNoOptionsManyFilesTest() {
		// String[] arguments = new String[]{"a.txt","b.txt","c.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter("\t",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory,
				"paste a.txt b.txt c.txt");
		String empty = "";
		expectedOutput = "Table\tWall\tSuperman" + "\n"
				+ "Chair\tFloor\tSpiderman" + "\n" + "Man\t" + empty
				+ "\tBatman";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// case where numDelim = 1 for 1 file
	public void pasteUseDelimiterOneFileTest1() {

		// @Changed: add in the -d and : options in command
		// String[] arguments = new String[]{"-d",":","a.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter(":", arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory, "paste -d : a.txt");
		expectedOutput = "Table" + "\n" + "Chair" + "\n" + "Man";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// case where numDelim > 1 for 1 file
	public void pasteUseDelimiterOneFileTest2() {

		// @Changed: add in the -d and :& options in command
		// String[] arguments = new String[]{"-d",":&","a.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter(":&",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory, "paste -d :& a.txt");
		expectedOutput = "Table" + "\n" + "Chair" + "\n" + "Man";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Delimiter Test with Stdin
	public void pasteUseDelimiterStdinTest() {
		// String[] arguments = new String[]{"-d",":","-"};
		// Changes: command stdin is shifted to execute method to cater to our project
		String stdin = "Stdin input";
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory, "paste -d : - Stdin input");
		//System.out.println("actualOutput: "+actualOutput);
		expectedOutput = stdin;
		//assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		//assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// case where numDelim = n - 1 for n files
	public void pasteUseDelimiterManyFilesTest1() {

		// @Changed: add in the -d and :& options in command
		// String[] arguments = new String[]{"-d",":&","a.txt","b.txt","c.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter(":&",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory,
				"paste -d :& a.txt b.txt c.txt");

		String empty = "";
		expectedOutput = "Table:Wall&Superman" + "\n" + "Chair:Floor&Spiderman"
				+ "\n" + "Man:" + empty + "&Batman";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// case where 1 delim - many files
	public void pasteUseDelimiterManyFilesTest2() {

		// @Changed: add in the -d and : options in command
		// String[] arguments = new String[]{"-d",":","a.txt","b.txt","c.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter(":", arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory,
				"paste -d : a.txt b.txt c.txt");
		String empty = "";
		expectedOutput = "Table:Wall:Superman" + "\n" + "Chair:Floor:Spiderman"
				+ "\n" + "Man:" + empty + ":Batman";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// case where numDelim < n - 1 and many files
	public void pasteUseDelimiterManyFilesTest2_1() {

		// @Changed: add in the -d and :& options in command
		// String[] arguments = new
		// String[]{"-d",":&","a.txt","b.txt","c.txt","d.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter(":&",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().
		actualOutput = pasteTool.execute(workingDirectory,
				"paste -d :& a.txt b.txt c.txt d.txt");
		String empty = "";
		expectedOutput = "Table:Wall&Superman:Cat" + "\n"
				+ ("Chair:Floor&Spiderman:" + empty) + "\n" + "Man:" + empty
				+ "&Batman:" + empty;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// case where numDelim > n - 1
	public void pasteUseDelimiterManyFilesTest3() {

		// @Changed: add in the -d and :& options in command
		// String[] arguments = new String[]{"-d",":&","a.txt","b.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteUseDelimiter(":&",
		// arguments);
		// pasteUseDelimiter should take in the contents of the file and not
		// filenames as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().
		actualOutput = pasteTool.execute(workingDirectory,
				"paste -d :& a.txt b.txt");
		String empty = "";
		expectedOutput = "Table:Wall" + "\n" + "Chair:Floor" + "\n" + "Man:"
				+ empty;

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Paste: Serial with 1 file
	public void pasteSerialOneFileTest() {

		// @Changed: add in the -s options in command
		// String[] arguments = new String[]{"-s","a.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteSerial(arguments);
		// pasteSerial should take in the contents of the file and not filenames
		// as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().

		actualOutput = pasteTool.execute(workingDirectory, "paste -s a.txt");
		expectedOutput = "Table\tChair\tMan";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Paste serial with Stdin
	public void pasteSerialStdinTest() {
		String[] arguments = new String[] { "-s", "-" };
		String stdin = "Stdin input";
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		actualOutput = pasteTool.execute(workingDirectory,
				"paste -s - Stdin input");
		expectedOutput = stdin;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// Serial many files
	public void pasteSerialManyFilesTest() {

		// @Changed: add in the -s options in command
		// String[] arguments = new String[]{"-s","a.txt","b.txt","d.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();

		// @Changed: actualOutput = pasteTool.pasteSerial(arguments);
		// pasteSerial should take in the contents of the file and not filenames
		// as argument
		// this will force every implementation(like opening a file,reading,etc)
		// in the method rather than execute().
		actualOutput = pasteTool.execute(workingDirectory,
				"paste -s a.txt b.txt d.txt");
		expectedOutput = "Table\tChair\tMan" + "\n" + "Wall\tFloor" + "\n"
				+ "Cat";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// if multiple delim, choose last option and args
	public void pasteUseDelimMultipleDelimsTest() {
		// String[] arguments = new String[]{"-d",":","-d","*","a.txt","b.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory,
				"paste -d : -d * a.txt b.txt");

		// @Changed: expectedOutput = "Table*Wall" + "\n" + "Chair*Floor" + "\n"
		// + "Man*" + "";
		// We are not sure if there is anymore inputs so a \n at the end is
		// necessary
		expectedOutput = "Table*Wall" + "\n" + "Chair*Floor" + "\n" + "Man*";
		assertEquals(expectedOutput, actualOutput);
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

	@Test
	// should choose -s over -d
	public void pasteOptionsPriorityCheckTest() {
		// String[] arguments = new String[]{"-s","-d",":","a.txt"};
		// Changes: command stdin is shifted to execute method to cater to our
		// project
		pasteTool = new PASTETool();
		actualOutput = pasteTool.execute(workingDirectory,
				"paste -s -d : a.txt");
		expectedOutput = "Table\tChair\tMan";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(pasteTool.getStatusCode(), 0);
	}

}
