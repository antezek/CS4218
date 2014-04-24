package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;

public class COMMToolTest {

	public COMMToolTest() {

	}

	private ICommTool commTool;
	String actualOutput, expectedOutput, helpOutput;
	String pathSep="";
	File workingDirectory;

	File fileA, fileB, fileC, fileD, fileF, fileG, fileH, fileI, fileJ, fileK,
			fileL, fileEM1, fileEM2;
	String fileContentA, fileContentB, fileContentC, fileContentD,
			fileContentE, fileContentF, fileContentG, fileContentH,
			fileContentI, fileContentJ, fileContentK, fileContentL;
	String testTab, testNewLine, testDash;

	@Before
	public void before() throws Exception {
		workingDirectory = new File(System.getProperty("user.dir"));
		helpOutput = " /*\n"
				+ "\n*"
				+ "\n* comm : Compares two sorted files line by line. With no options, produce three-column output."
				+ "\n* 		 Column one contains lines unique to FILE1, column two contains lines unique to FILE2,"
				+ "\n 		 and column three contains lines common to both files."
				+ "\n*"
				+ "\n*	Command Format - comm [OPTIONS] FILE1 FILE2"
				+ "\n*	FILE1 - Name of the file 1"
				+ "\n*	FILE2 - Name of the file 2"
				+ "\n*		-c : check that the input is correctly sorted"
				+ "\n*      -d : do not check that the input is correctly sorted"
				+ "\n*      -help : Brief information about supported options"
				+ "\n*/";

		fileA = new File("w.txt");
		fileB = new File("x.txt");
		fileC = new File("y.txt");
		fileD = new File("z.txt");
		fileF = new File("unsort.txt");
		fileG = new File("ant1.txt");
		fileH = new File("ant2.txt");
		fileI = new File("ant3.txt");
		fileJ = new File("ant4.txt");
		fileK = new File("ant5.txt");
		fileL = new File("ant6.txt");
		fileEM1 = new File("em1.txt");
		fileEM2 = new File("em2.txt");
		fileEM1.createNewFile();
		fileEM2.createNewFile();

		fileContentA = "Apple\nMelon\nOrange";
		fileContentB = "Banana\nMelon\nOrange";
		fileContentC = "Batman\nSpiderman\nSuperman";
		fileContentD = "Cat\nBat";
		fileContentE = "";
		fileContentF = "Apple\nBanana\nJewel\nRat\nOrange\nZebra\nPolice\nWater";
		fileContentG = "Lychee\nJackfruit\nKiwi";
		fileContentH = "Pineapple\nJackfruit\nKiwi";
		fileContentI = "Apple\nLychee\nJackfruit\nKiwi";
		fileContentJ = "Lychee\nJackfruit\nKiwi";
		fileContentK = "Pineapple\nStarfruit";
		fileContentL = "Lychee\nStarfruit";

		writeToFile(fileA, fileContentA);
		writeToFile(fileB, fileContentB);
		writeToFile(fileC, fileContentC);
		writeToFile(fileD, fileContentD);
		writeToFile(fileF, fileContentF);
		writeToFile(fileG, fileContentG);
		writeToFile(fileH, fileContentH);
		writeToFile(fileI, fileContentI);
		writeToFile(fileJ, fileContentJ);
		writeToFile(fileK, fileContentK);
		writeToFile(fileL, fileContentL);

		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		
		pathSep=java.nio.file.FileSystems.getDefault().getSeparator();
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
		commTool = null;
		System.gc();
		fileA.delete();
		fileB.delete();
		fileC.delete();
		fileD.delete();
		fileF.delete();
		fileG.delete();
		fileH.delete();
		fileI.delete();
		fileJ.delete();
		fileK.delete();
		fileL.delete();
		fileEM1.delete();
		fileEM2.delete();
	}

	/**
	 * Test if overall control flow is correct
	 */
	@Test
	public void overallTest() {
		commTool = new COMMTool();
		actualOutput = commTool.execute(workingDirectory, "comm w.txt x.txt");
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash
				+ testNewLine + testDash + testTab + "Banana" + testTab
				+ testDash + testNewLine + testDash + testTab + testDash
				+ testTab + "Melon" + testNewLine + testDash + testTab
				+ testDash + testTab + "Orange";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/**
	 * Test file doesn't exist for arg 1
	 */
	@Test
	public void compareFilesInvalidFileArgs1Test() {
		String fileName1 = "C:"+pathSep+"Users"+pathSep+"Dale"+pathSep+"w.txt";
		String fileName2 = "./x.txt";
		String[] arguments = new String[] { "C:"+pathSep+"Users"+pathSep+"Dale"+pathSep+"w.txt", "./x.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm C:"+pathSep+"Users"+pathSep+"Dale"+pathSep+"w.txt ./x.txt");

		expectedOutput = "File 1 doesn't exist!";
		assertEquals(expectedOutput, actualOutput);
	}

	/**
	 * Test file doesn't exist for arg 2
	 */
	@Test
	public void compareFilesInvalidFileArgs2Test() {
		String fileName1 = "C:"+pathSep+"Users"+pathSep+"Dale"+pathSep+"w.txt";
		String fileName2 = "./x.txt";
		String[] arguments = new String[] { "./x.txt", "C:"+pathSep+"Users"+pathSep+"Dale"+pathSep+"w.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm ./x.txt C:"+pathSep+"Users"+pathSep+"Dale"+pathSep+"w.txt");

		expectedOutput = "File 2 doesn't exist!";
		assertEquals(expectedOutput, actualOutput);
	}

	/**
	 * Test help command will take priority over the rest of args
	 */
	@Test
	public void compareFilesCGiveHelpPriorityTest() {
		String[] arguments = new String[] { "-c", "-help", "file1", "file2" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm -c -help file1 file2");
		expectedOutput = helpOutput;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/**
	 * Test help command will take priority over -d args
	 */
	@Test
	public void compareFilesDGiveHelpPriorityTest() {
		String[] arguments = new String[] { "-d", "-help", "file1", "file2" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm -d -help file1 file2");
		expectedOutput = helpOutput;
		
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/**
	 * Test -d args
	 */
	@Test
	public void compareFilesDoNotCheckForSortStatus() {
		String[] arguments = new String[] { "-d", "w.txt", "x.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool
				.execute(workingDirectory, "comm -d w.txt x.txt");
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash
				+ testNewLine + testDash + testTab + "Banana" + testTab
				+ testDash + testNewLine + testDash + testTab + testDash
				+ testTab + "Melon" + testNewLine + testDash + testTab
				+ testDash + testTab + "Orange";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/*
	 * Test comm for 2 empty files
	 */
	@Test
	public void compareFilesNoOptionsBothFilesEmptyTest() {
		String[] arguments = new String[] { "em1.txt", "em2.txt" };
		String input1 = "em1.txt";
		String input2 = "em2.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm em1.txt em2.txt");
		expectedOutput = "";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/*
	 * Test for all lines in both files are unique. Result - Column 3 should be
	 * empty
	 */
	@Test
	public void compareFilesNoOptionsAllUniqueTest() {
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[] {"w.txt","y.txt"};
		String input1 = "w.txt";
		String input2 = "y.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm w.txt y.txt");

		expectedOutput = "Apple" + testTab + testDash + testTab + testDash
				+ testNewLine + testDash + testTab + "Batman" + testTab
				+ testDash + testNewLine + "Melon" + testTab + testDash
				+ testTab + testDash + testNewLine + "Orange" + testTab
				+ testDash + testTab + testDash + testNewLine + testDash
				+ testTab + "Spiderman" + testTab + testDash + testNewLine
				+ testDash + testTab + "Superman";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);

	}

	/*
	 * Test for some common lines in both files. Result - All three columns have
	 * items
	 */
	@Test
	public void compareFilesNoOptionsSomeUniqueTest() {
		String[] arguments = new String[] { "w.txt", "x.txt" };
		String input1 = "w.txt";
		String input2 = "x.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm w.txt x.txt");
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash
				+ testNewLine + testDash + testTab + "Banana" + testTab
				+ testDash + testNewLine + testDash + testTab + testDash
				+ testTab + "Melon" + testNewLine + testDash + testTab
				+ testDash + testTab + "Orange";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/*
	 * //Test for no unique lines at all. Result - Col1 and Col2 are empty
	 */
	@Test
	public void compareFilesNoOptionsNoneUniqueTest() {
		String[] arguments = new String[] { "w.txt", "w.txt" };
		String input1 = "w.txt";
		String input2 = "w.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm w.txt w.txt");
		expectedOutput = testDash + testTab + testDash + testTab + "Apple"
				+ testNewLine + testDash + testTab + testDash + testTab
				+ "Melon" + testNewLine + testDash + testTab + testDash
				+ testTab + "Orange";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/*
	 * Test for invalid command input
	 */
	@Test
	public void invalidCommand() {
		String[] arguments = new String[] { "-e", "w.txt", "x.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool
				.execute(workingDirectory, "comm -e w.txt x.txt");
		expectedOutput = "Error: Invalid command";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 1);
	}
	
	/*
	 * Test for insufficient file input
	 */
	@Test
	public void insufficientCommand() {
		String[] arguments = new String[] { "-e", "w.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm -e w.txt");
		expectedOutput = "File 1 doesn't exist!";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 1);
	}
	
	/*
	 * Test for invalid overloaded command
	 */
	@Test
	public void invalidOverloadCommand() {
		String[] arguments = new String[] { "-e", "w.txt", "x.txt", "y.txt",
				"z.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm -e w.txt x.txt y.txt");
		expectedOutput = "Error: Invalid command";
		
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 1);
	}
	
	/*
	 * Test sort. Positive case: Files are sorted
	 */
	@Test
	public void compareFilesCheckSortStatusTest1() {
		String[] arguments = new String[] { "-c", "w.txt", "x.txt" };
		String input1 = "w.txt";
		String input2 = "x.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm -c w.txt x.txt");

		expectedOutput = "Apple" + testTab + testDash + testTab + testDash
				+ testNewLine + testDash + testTab + "Banana" + testTab
				+ testDash + testNewLine + testDash + testTab + testDash
				+ testTab + "Melon" + testNewLine + testDash + testTab
				+ testDash + testTab + "Orange";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}
	
	/*
	 * Test negative case: File 2 not sorted
	 */
	@Test
	public void compareFilesCheckSortStatusTest2() {
		String[] arguments = new String[] { "-c", "w.txt", "unsort.txt" };
		String input1 = "w.txt";
		String input2 = "z.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm -c w.txt unsort.txt");
		expectedOutput = "File 2 not sorted!";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/*
	 * Test the placement of line according to the alphabetic order. In this case, words in column A less than column B but more than column C
	 */
	@Test
	public void compareFileALessBMoreC() {
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[] { "ant1.txt", "ant2.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,"comm ant1.txt ant2.txt");

		expectedOutput = testDash + testTab + testDash + testTab + "Jackfruit"
				+ testNewLine + testDash + testTab + testDash + testTab
				+ "Kiwi" + testTab + testDash + testTab + testDash
				+ testNewLine + "Lychee" + testTab + testDash + testTab
				+ testDash + testNewLine + testDash + testTab + "Pineapple";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}
	
	/*
	 * Test the placement of line according to the alphabetic order. In this case, words in column A more than column B and column C
	 */
	@Test
	public void compareFileAMoreBMoreC() {
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[] { "ant2.txt", "ant1.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm ant2.txt ant1.txt");
		expectedOutput = testDash + testTab + testDash + testTab + "Jackfruit"
				+ testNewLine + testDash + testTab + testDash + testTab
				+ "Kiwi" + testTab + testDash + testTab + testDash
				+ testNewLine + testTab + "Lychee" + testTab + testDash
				+ testNewLine + "Pineapple";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}
	
	/*
	 * Test the placement of line according to the alphabetic order. In this case, words in column A more than column B but less than column C
	 */
	@Test
	public void compareFileAMoreBLessC() {
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[] { "ant5.txt", "ant6.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm ant5.txt ant6.txt");
		expectedOutput = testTab + "Lychee" + testTab + testDash
				+ testNewLine + "Pineapple" + testTab + testDash + testTab
				+ testDash + testNewLine + testDash + testTab + testTab
				+ "Starfruit";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}
	
	/*
	 * Test the placement for 1 unique in column A and the rest common in column C
	 */
	@Test
	public void compareFileAAndCNoB() {
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[] { "ant3.txt", "ant4.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm ant3.txt ant4.txt");
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash
				+ testNewLine + testDash + testTab + testTab + "Lychee"
				+ testNewLine + testDash + testTab + testDash + testTab
				+ "Jackfruit" + testNewLine + testDash + testTab + testDash
				+ testTab + "Kiwi";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}

	/*
	 * Test the placement for 1 unique in column B and the rest in column C
	 */
	@Test
	public void compareFileBAndCNoA() {
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[] { "ant4.txt", "ant3.txt" };
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory,
				"comm ant4.txt ant3.txt");
		expectedOutput = testTab + "Apple" + testTab + testDash
				+ testNewLine + testDash + testTab + testDash + testTab
				+ "Lychee" + testNewLine + testDash + testTab + testDash
				+ testTab + "Jackfruit" + testNewLine + testDash + testTab
				+ testDash + testTab + "Kiwi";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);
	}
	
	/*
	 * Bugs Solved from Hackathon
	 * 
	 */
    // help should be given priority
    @Test
    public void executeHelpCommand(){
    	File workingDirectory = new File(System.getProperty("user.dir"));
    	
    	String[] arguments = new String[] {"x.txt", "w.txt", "-help"};
    	String helpOutputComm = " /*\n"
				+ "\n*"
				+ "\n* comm : Compares two sorted files line by line. With no options, produce three-column output."
				+ "\n* 		 Column one contains lines unique to FILE1, column two contains lines unique to FILE2,"
				+ "\n 		 and column three contains lines common to both files."
				+ "\n*"
				+ "\n*	Command Format - comm [OPTIONS] FILE1 FILE2"
				+ "\n*	FILE1 - Name of the file 1"
				+ "\n*	FILE2 - Name of the file 2"
				+ "\n*		-c : check that the input is correctly sorted"
				+ "\n*      -d : do not check that the input is correctly sorted"
				+ "\n*      -help : Brief information about supported options"
				+ "\n*/";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm x.txt w.txt -help ");
		assertEquals(helpOutputComm,actualOutput);
    }
    
    
}
