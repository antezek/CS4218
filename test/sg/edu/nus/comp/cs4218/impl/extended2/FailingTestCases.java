package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
import sg.edu.nus.comp.cs4218.impl.Shell;
import sg.edu.nus.comp.cs4218.impl.extended2.COMMTool;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTETool;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTTool;
import sg.edu.nus.comp.cs4218.impl.extended2.UNIQTool;
import sg.edu.nus.comp.cs4218.impl.extended2.WCTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.GREPTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.Helper;
import sg.edu.nus.comp.cs4218.impl.fileutils.LSTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.PIPETool;
import sg.edu.nus.comp.cs4218.impl.extended2.CUTTool;

public class FailingTestCases {    
    
    private ICatTool CATTool;
    private ILsTool lsTool;
    private IGrepTool grepTool;
    private UNIQTool uniqTool;
    private CUTTool cutTool;
    private COMMTool commTool;
	String actualOutput, expectedOutput;
	File workingDirectory;
	File inputFileUniq, inputFileCut;
    String helpOutputCut, helpOutputComm;
    
    File file1, file2, file3, file4, fileA, fileB, fileC, fileD, fileE, fileF;
	String file1Content, file2Content, file3Content, file4Content, fileContentA, fileContentB, fileContentC, fileContentD, fileComm1, fileComm2;
	String testTab, testNewLine, testDash;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Before
    public void before(){
        lsTool = new LSTool();
		workingDirectory = new File(System.getProperty("user.dir"));
		

		// Uniq
		uniqTool = new UNIQTool();
		String input1 = "hi\nhi\nhello\nhello\nhi\nabc";
		inputFileUniq = new File("Test_Output.txt");
		writeToFile(inputFileUniq, input1);
		
		
		// Cut
		cutTool = new CUTTool();
		helpOutputCut = "usage: cut [OPTIONS] [FILE]" + "\n"
				+ "FILE : Name of the file, when no file is present" + "\n"
				+ "OPTIONS : -c LIST : Use LIST as the list of characters to cut out. Items within "
				+ "the list may be separated by commas, "
				+ "and ranges of characters can be separated with dashes. "
				+ "For example, list 1-5,10,12,18-30 specifies characters "
				+ "1 through 5, 10,12 and 18 through 30" + "\n"
				+ "-d DELIM: Use DELIM as the field-separator character instead of"
				+ "the TAB character" + "\n" 
				+ "-help : Brief information about supported options";
		
		
		String input2 = "apple\nball\ncat\ndog";
		
		inputFileCut = new File("testCut.txt");
		
		writeToFile(inputFileCut, input2);
		
		
		helpOutputComm = " /*\n"
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
		file1 = new File("test1.txt");
		file2 = new File("test2.txt");
		file3 = new File("test3.txt");
		file4 = new File("test4.txt");
		fileA = new File("w.txt");
		fileB = new File("x.txt");
		fileC = new File("y.txt");
		fileD = new File("z.txt");
		fileE = new File("commTestCase3a.txt");
		fileF = new File("commTestCase3b.txt");
		
		file1Content = "ddd\nbbb\nccc";
		file2Content = "ddd\nbbb";
		file3Content = "aaa\nbbb\nccc";
		file4Content = "aaa\nbbb";
		fileContentA = "Apple\nMelon\nOrange";
		fileContentB = "Banana\nMelon\nOrange";
		fileContentC = "Batman\nSpiderman\nSuperman";
		fileContentD = "Cat\nBat";
		fileComm1 = "def\nddd\neee\nabc";
		fileComm2 = "eee\nfff\nggg\nabc";

		writeToFile(file1, file1Content);
		writeToFile(file2, file2Content);
		writeToFile(file3, file3Content);
		writeToFile(file4, file4Content);
		writeToFile(fileA, fileContentA);
		writeToFile(fileB, fileContentB);
		writeToFile(fileC, fileContentC);
		writeToFile(fileD, fileContentD);
		writeToFile(fileE, fileComm1);
		writeToFile(fileF, fileComm2);

		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		
    }
    
    @After
    public void after(){
        lsTool = null;
		uniqTool = null;
		cutTool = null;
    	actualOutput ="";
    	expectedOutput = "";
    	System.gc();
		if(inputFileUniq.exists())
			inputFileUniq.delete();
		if (inputFileCut.exists())
			inputFileCut.delete();
		file1.delete();
		file2.delete();
		file3.delete();
		file4.delete();
		fileA.delete();
		fileB.delete();
		fileC.delete();
		fileD.delete();
		fileE.delete();
		fileF.delete();
		
    }
    
    @Before
    public void setUp() throws Exception {
        grepTool = new GREPTool();
    }    

    @After
    public void tearDown() throws Exception {
        grepTool = null;
    }
    
    public void writeToFile(File file, String input) {
		try {
			if (!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			char[] temp = input.toCharArray();
			int i = 0;
			while (i < temp.length) {
				while (temp[i] != '\n') {
					bw.write(temp[i]);
					i++;
					if (i >= temp.length)
						break;
				}
				bw.newLine();
				i++;
			}
			bw.close();
		} catch (IOException e) {
		}
	}
    
    /*
     * Bug_#15 CutTool Bug 2
     * Description: Type in rubbish with correct length
     * ======Fix=======
     * Class Name: CUTTool.java
     * Line No: 162 of 599
     * 
     */
     @Test
     public void executeArgsIsInvalidCut(){											
     	cutTool = new CUTTool();
 		actualOutput = cutTool.execute(workingDirectory, "cut    ");

 		assertEquals(-1, cutTool.getStatusCode());
     }
     
     /*
      * Bug_#17 CutTool Bug 4
      * Description: c option with invalid range.Testing for status Code for StartRange > EndRange
      * ======Fix=======
      * Class Name: CUTTool.java
      * Line No: 
      * 
      */
     @Test
    	public void executeCOptionInvalidRangeTest()										
    	{
    		cutTool = new CUTTool();
    		cutTool.execute(workingDirectory, "cut -c ABC testCut.txt");

    		assertEquals(-1, cutTool.getStatusCode());
    	}
     
     /*
      * Bug_#20 CutTool Bug 7
      * Description: Both std input and file input should be executed (std input first)
      * ======Fix=======
      * Class Name: CUTTool.java
      * Line No:
      * 
      */
     @Test   
 	public void fileInputAndStdInputTest()														
 	{
     	// swap position of stdin and file
     	cutTool = new CUTTool();
 		actualOutput = cutTool.execute(workingDirectory, "cut -c 1-2 - abcde testCut.txt");
 		expectedOutput = "ab\nap\nba\nca\ndo\n";
 		actualOutput = actualOutput.replace("\n", "");
 		expectedOutput = expectedOutput.replace("\n", "");
 		assertEquals(expectedOutput, actualOutput);
 	}
     
     /* 
      * Bug_#3 CommTool Bug 3
      * Description: stdin is null
      * ======Fix=======
      * Class Name: COMMTool.java
      * Line No: 539 of 628 
      * 
      */
     @Test
     public void executeWithNull(){															
     	File workingDirectory = new File(System.getProperty("user.dir"));

 		commTool = new COMMTool();
 		actualOutput = commTool.execute(workingDirectory, null);
 		assertEquals(1, commTool.getStatusCode());
     }

     /*
      * Bug_#6 CommTool Bug 6
      * Description: will get stuck at infinite loop in CompareFiles. Test expected result has been tested with terminal "comm" command.
      * ======Fix=======
      * Class Name: 
      * Line No: 75 of 628
      * 
      */
     @Test
    	public void executeDoNotCheckSortTest() {																
        	String [] args = {"-d", "commTestCase3a.txt", "commTestCase3b.txt"};
        	commTool = new COMMTool(args);
    		String workingDir = System.getProperty("user.dir");
    		String output = "def"+testTab+testDash+testTab+testDash+testNewLine+
						"ddd"+testTab+testDash+testTab+testDash+testNewLine+testDash+testTab+testTab+
						"eee"+testNewLine+testDash+testTab+testDash+testTab+"abc"+testTab+testDash+testTab+testDash+testNewLine+testTab+
						"fff"+testTab+testDash+testNewLine+testDash+testTab+"ggg";
    		File f = new File(workingDir);
    		assertEquals(output, commTool.execute(f, "comm -d commTestCase3a.txt commTestCase3b.txt"));

     }
     
     /*
      * Bug_#39 GREPTool Bug 2
      * Description: Boundary test case (will be stuck in infinite loop)
      * ======Fix=======
      * Class Name: GREPTool.java
      * LOC: 253 of 523
      * 
      */
     @Test 
     public void getMatchingLinesWithTrailingContextNegativeNumber() {
         assertEquals("Invalid Grep Command: Use grep -help for command format\n", grepTool.getMatchingLinesWithTrailingContext(-1, "abc", "abc"));
         assertEquals(1, grepTool.getStatusCode());
     }
}
