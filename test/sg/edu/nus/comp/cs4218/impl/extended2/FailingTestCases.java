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

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
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
    
    
    
    @Test
    public void getStatusCodeForNullWorkingDir() throws IOException {
        //Test error-handling 7
        CATTool = new sg.edu.nus.comp.cs4218.impl.fileutils.CATTool();
        CATTool.execute(null, "nonexist nonexist");
        assertEquals(0, CATTool.getStatusCode());
    }
    
    @Test
    public void testCatWithMultipleFiles(){
        File currentDir = new File(System.getProperty("user.dir"));
        CATTool = new sg.edu.nus.comp.cs4218.impl.fileutils.CATTool();
        assertEquals("a\nb\nc\n\nd\ne\nf\ng\nzzzzz\nddddddd\nggggggg\nhhhhhh\nluklu", CATTool.execute(currentDir, new String("Test/file.txt ../file.txt")));
        assertEquals(CATTool.getStatusCode(), 0);
    }
    
    @Test
    public void testCatWithDoubleQuotes(){
        File currentDir = new File(System.getProperty("user.dir"));
        CATTool = new sg.edu.nus.comp.cs4218.impl.fileutils.CATTool();
        assertEquals("a\nb\nc\n\nd\ne\nf\ng\nzzzzz\nddddddd\nggggggg\nhhhhhh\nluklu", CATTool.execute(currentDir, new String("\"Test/test file.txt\" ../file.txt")));
        assertEquals(CATTool.getStatusCode(), 1);
    }
    
    @Test //CATTool returns error message stating that file is null when it is actually an empty string. (The expected value is irrelevant, just that the error message is wrong and misleading)
    public void executeZeroArgs(){
        File currentDir = new File(System.getProperty("user.dir"));
        CATTool = new sg.edu.nus.comp.cs4218.impl.fileutils.CATTool();
        assertEquals("Invalid cat command", CATTool.execute(currentDir, new String("")));
        assertEquals(1, CATTool.getStatusCode());
    }
    
    @Test
    public void getStringForDirectoryContentsTestExecute() throws IOException {
        //Test expected behavior
        //Create a temporary directory and get directory contents
        List<File> files = lsTool.getFiles(testFolder.getRoot());
        String fileString = null;
        for (File file : files) {
            if (fileString == null) {
                fileString = file.getName() + "\n";
            }
            else {
                fileString = fileString + file.getName() + "\n";
            }
        }
        fileString = fileString + files.size() + " item(s) in directory";
        assertEquals(fileString, lsTool.execute(testFolder.getRoot(), null));
        assertEquals(lsTool.getStatusCode(), 0);
    }

    
    @Test
    public void getMatchingLinesWithLeadingContextNullPatternAndNullInput() {
        assertEquals("Invalid grep command", grepTool.getMatchingLinesWithLeadingContext(0, null, null));
        assertEquals(1, grepTool.getStatusCode());
    }
    
    @Test //Boundary test case (will be stuck in infinite loop)
    public void getMatchingLinesWithTrailingContextNegativeNumber() {
        assertEquals("Invalid grep command", grepTool.getMatchingLinesWithTrailingContext(-1, "abc", "abc"));
        assertEquals(1, grepTool.getStatusCode());
    }
    
    @Test
    public void lsGrepMove() throws IOException {
        File toMove = testFolder.newFile("toMove.txt");
        File folder = testFolder.newFolder("Test");    
        File destination = getFile(folder, toMove.getName());
        
        PIPETool pipeTool = new PIPETool();
        pipeTool.execute(testFolder.getRoot(), "ls | grep toMove - | move - " + destination.getAbsolutePath());

        List<File> fromFolder = getFiles(testFolder.getRoot());    
        List<File> toFolder = getFiles(folder);
        
        assertFalse(fromFolder.contains(toMove));        
        assertTrue(toFolder.contains(destination));        
        assertEquals(0, pipeTool.getStatusCode());
    }
    
    // UniqTool
    // execute with stdin as null
 	@Test
 	public void executeStdinIsNull() {
 		actualOutput = uniqTool.execute(workingDirectory, null);
 		assertEquals(-1, uniqTool.getStatusCode());
 	}

 	// replace uniq with test
     @Test
     public void executeInvalidCommandUniq(){
 		uniqTool.execute(workingDirectory, "test Test_Output.txt");
 		assertEquals(-1,uniqTool.getStatusCode());
     }
     
     // field is negative value
     @Test
     public void getUniqueSkipNumNegativeFieldNumber(){
 		actualOutput = uniqTool.getUniqueSkipNum(-1, false ,"1235");
 		assertEquals(-1, uniqTool.getStatusCode());
     }

     
     // cut tool
  // priority should be given to help when there are multiple options
     @Test
 	public void executeHelpPriorityTest()
 	{
 		cutTool = new CUTTool();
 		actualOutput = cutTool.execute(workingDirectory, "cut -c 1-2 -help - abcde");
 		expectedOutput = helpOutputCut;
 		actualOutput = actualOutput.replace("\n", "");
 		expectedOutput = expectedOutput.replace("\n", "");
 		assertEquals(expectedOutput ,actualOutput);
 	}
     // type in rubbish with correct length
     @Test
     public void executeArgsIsInvalidCut(){
     	cutTool = new CUTTool();
 		actualOutput = cutTool.execute(workingDirectory, "cut    ");

 		assertEquals(-1, cutTool.getStatusCode());
     }
     
     // replace cut with test
     @Test
     public void executeInvalidCommandCut(){
     	File workingDirectory = new File(System.getProperty("user.dir"));

 		actualOutput = cutTool.execute(workingDirectory, "test -c 1-2 testCut.txt");
 		assertEquals(-1, cutTool.getStatusCode());
     }
     
     // c option with invalid range
     @Test
    	public void executeCOptionInvalidRangeTest()
    	{
    		cutTool = new CUTTool();
    		cutTool.execute(workingDirectory, "cut -c ABC testCut.txt");

    		assertEquals(-1, cutTool.getStatusCode());
    	}
     

     // c option with empty stdin
     @Test
    	public void executeCOptionEmptyStdinTest()
    	{
    		cutTool = new CUTTool();
    		cutTool.execute(workingDirectory, "cut -c 1 - ");
    		assertEquals(-1, cutTool.getStatusCode());
    	}
     // d option with empty stdin 
     @Test
    	public void executeDOptionEmptyStdinTest()
    	{
    		cutTool = new CUTTool();
    		cutTool.execute(workingDirectory, "cut -d : -f 1 - ");
    		assertEquals(-1, cutTool.getStatusCode());
    	}
     
     //Both std input and file input should be executed (std input first)
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
     
     // stdin is null
     @Test
 	public void executeArgsNullFailTest() throws IOException {
 		// test execute method 
     	cutTool = new CUTTool();
     	
 		assertEquals("Invalid command", cutTool.execute(workingDirectory, null));
     }
     
     
     // duplicated range
     @Test
 	public void cutSpecifiedCharactersUseDelimiterTest1() throws IOException 
 	{
     	cutTool = new CUTTool();	
 		actualOutput = cutTool.cutSpecifiedCharactersUseDelimiter("1-3,1-3", "\"" ,"12\"3\"4");
 		expectedOutput = "12\"3\"4";
 		actualOutput = actualOutput.replace("\n", "");
 		expectedOutput = expectedOutput.replace("\n", "");
 		assertEquals(expectedOutput, actualOutput);
 	}
     // 0 is valid, 1-3 is valid but 0-3 is invalid? why?
     @Test
 	public void cutSpecifiedCharactersUseDelimiterTest2() throws IOException 
 	{
     	cutTool = new CUTTool();	
 		actualOutput = cutTool.cutSpecifiedCharactersUseDelimiter("0-3", "\"" ,"12\"3\"4");
 		expectedOutput = "12\"3\"4";
 		actualOutput = actualOutput.replace("\n", "");
 		expectedOutput = expectedOutput.replace("\n", "");
 		assertEquals(expectedOutput, actualOutput);
 	}
     // invalid range
     @Test
 	public void cutSpecifiedCharactersUseDelimiterTest3() throws IOException 
 	{
     	cutTool = new CUTTool();	
 		actualOutput = cutTool.cutSpecifiedCharactersUseDelimiter("-1-==", "\"" ,"12\"3\"4");
 		expectedOutput = "Invalid Range Specify";
 		actualOutput = actualOutput.replace("\n", "");
 		expectedOutput = expectedOutput.replace("\n", "");
 		assertEquals(expectedOutput, actualOutput);
 	}

     // invalid range
     @Test
 	public void cutSpecifiedCharactersTest1() throws IOException 
 	{
     	cutTool = new CUTTool();	
 		actualOutput = cutTool.cutSpecfiedCharacters("-1-==", "1234");
 		expectedOutput = "Invalid Range Specify";
 		actualOutput = actualOutput.replace("\n", "");
 		expectedOutput = expectedOutput.replace("\n", "");
 		assertEquals(expectedOutput, actualOutput);
 	}
     
     
    // Comm Tool
 //  COMMTool(String args[]) is used to check whether the command is valid using args.length
     // but it is not specified in assumptions
     @Test
     public void executeArgsIsNull(){
     	File workingDirectory = new File(System.getProperty("user.dir"));
     	
 		commTool = new COMMTool(null);
 		actualOutput = commTool.execute(workingDirectory, "comm x.txt w.txt");

 		assertEquals(1, commTool.getStatusCode());
     }
     // type in rubbish with correct length
     @Test
     public void executeArgsIsInvalidComm(){
     	File workingDirectory = new File(System.getProperty("user.dir"));

     	
     	String[] arguments = new String[] {" ", " "};
 		commTool = new COMMTool(arguments);
 		actualOutput = commTool.execute(workingDirectory, "comm    ");

 		assertEquals(1, commTool.getStatusCode());
     }
     
     

     // stdin is null
     @Test
     public void executeWithNull(){
     	File workingDirectory = new File(System.getProperty("user.dir"));

 		commTool = new COMMTool();
 		actualOutput = commTool.execute(workingDirectory, null);
 		assertEquals(1, commTool.getStatusCode());
     }


     // replace comm with test
     @Test
     public void executeInvalidCommand1(){
     	File workingDirectory = new File(System.getProperty("user.dir"));

     	String[] arguments = new String[] { "w.txt", "x.txt"};
 		commTool = new COMMTool(arguments);
 		actualOutput = commTool.execute(workingDirectory, "test w.txt x.txt");
 		assertEquals(1, commTool.getStatusCode());
     }
     
     // Helper isValidFile put in directory as filename
     @Test
     public void executeInvalidFile(){
     	assertEquals(null, Helper.isValidFile(new File(System.getProperty("user.dir")), "\\/"));//System.getProperty("user.dir")));
     }

     // Helper isValidDirectory 
     @Test
     public void executeInvalidDirectory(){
     	assertEquals(null, Helper.isValidDirectory(new File(System.getProperty("user.dir")), "  "));
     	
     }

     
 	// ffff should be only appear once 
     @Test
 	public void executeNoOptionTest() {
     	// test execute method
     	// both files have same length
     	String [] args = {"commTestCase1a.txt", "commTestCase1b.txt"};
     	commTool = new COMMTool(args);
 		String workingDir = System.getProperty("user.dir");
 		String output = testDash + testTab  + testDash + testTab + "aaa" + testDash + testTab
 						+ testNewLine + testTab + testDash +"bbb"
 						+ testNewLine + "ccc" + testDash + testTab
 						+ testNewLine + "eee" + testDash + testTab
 						+ testNewLine + testTab + testDash + "ffff" + testDash + testTab
 						+ testNewLine + "gggggg";
 		File f = new File(workingDir);
 		assertEquals(output, commTool.execute(f, "comm commTestCase1a.txt commTestCase1b.txt"));
     }
    
     
     
     
     // will get stuck at infinite loop in CompareFiles
     
     @Test
    	public void executeDoNotCheckSortTest() {
     	// test execute method
     	// -d option, both files same length with ONLY 1 common line "abc"
     	// "eee" is not considered common line since it exists at different line numbers of the 2 files
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
     


     // print extra lines (empty lines)
     @Test
   	public void executeEmptyFileTest() throws IOException {
     	// test execute method
     	// -c option, sorted, file1 is empty
   		String [] args = {"-c","commEmptyTestCase.txt", "commTestCase1a.txt"};
   		commTool = new COMMTool(args);
   		String workingDir = System.getProperty("user.dir");
   		String output = testDash + testTab + "aaa" + testDash + testDash
 					+ testNewLine + testDash + testTab +"ccc" + testDash + testDash
 					+ testNewLine + testDash + testTab +"eee" + testDash + testDash
 					+ testNewLine + testDash + testTab +"gggggg";
   		
   		File f = new File(workingDir);
   		assertEquals(commTool.execute(f, "comm -c commEmptyTestCase.txt commTestCase1a.txt"), output);
     }
   
     
     // 1 newline file and 1 empty file
     @Test
   	public void executeNewLineFileTest1() throws IOException {
     	// test execute method
     	// -c option, file1 contains 1 new line, file2 is empty
   		String [] args = {"-c","commNewLineTestCase.txt", "commEmptyTestCase.txt"};
   		commTool = new COMMTool(args);
   		String workingDir = System.getProperty("user.dir");
   		String output = testNewLine;
   		
   		File f = new File(workingDir);
   		assertEquals(output, commTool.execute(f, "comm -c commNewLineTestCase.txt commEmptyTestCase.txt"));
     }
     

     // constructor validation for args works but execute method does not check for status code before proceeding
     @Test
   	public void executeInvalidOptionFailTest() throws IOException {
     	// test execute method
     	// with invalid option (args' length is 1)
   		String [] args = {"-"};
   		commTool = new COMMTool(args);
   		String workingDir = System.getProperty("user.dir");
   		
   		File f = new File(workingDir);
   		assertEquals("Error: Invalid command", commTool.execute(f, "comm -"));
     }
    
     // output is sorted wrongly
 	// only second and third column has value
     // second 2 are in third column, last is in second column
 	@Test
 	public void compareFileBAndCNoA() {
 		String workingDir = System.getProperty("user.dir");
    		
    		File workingDirectory = new File(workingDir);
 		String[] arguments = new String[] { "test2.txt", "test1.txt" };
 		commTool = new COMMTool(arguments);
 		
 		actualOutput = commTool.execute(workingDirectory,
 				"comm test2.txt test1.txt");
 		expectedOutput = testNewLine + testTab + testDash + testTab + testDash+ "bbb"
 					    + testNewLine + testTab + testDash + "ccc"
 						+ testNewLine + testTab + testDash + testTab + testDash + "ddd";

 		assertEquals(expectedOutput, actualOutput);
    	}
     
 	// output line 2 has extra space behind bbb
 	// only first and third column has value
     // first 2 are in third column, last is in first column
 	@Test
 	public void compareFileAAndCNoB() {
 		String workingDir = System.getProperty("user.dir");
    		
    		File workingDirectory = new File(workingDir);
 		String[] arguments = new String[] { "test3.txt", "test4.txt" };
 		commTool = new COMMTool(arguments);
 		
 		actualOutput = commTool.execute(workingDirectory,
 				"comm test3.txt test4.txt");
 		expectedOutput = testTab + testDash + testTab + testDash+ "aaa" 
 					    + testNewLine + testTab + testDash + testTab + testDash + "bbb"
 						+ testNewLine + "ccc";

 		assertEquals(expectedOutput, actualOutput);
    	}
 	
 	 // cannot handle whitespace in a line
     @Test
    	public void compareFilesDifferentInputTest(){
     	// test compareFiles method
     	commTool = new COMMTool();
     	String input1 = "abc aed";
    		String input2 = "acdef fe";
    		String output = "abc aed" + testDash + testTab + testDash + testTab
    						+ testNewLine + testDash + testTab + testDash + testTab + "acdef fe" + testDash + testTab;
    		assertEquals(output, commTool.compareFiles(input1, input2));
     }
     
     // help should be given priority
     @Test
     public void executeHelpCommand(){
     	File workingDirectory = new File(System.getProperty("user.dir"));
     	
     	String[] arguments = new String[] {"x.txt", "w.txt", "-help"};
     	
 		commTool = new COMMTool(arguments);
 		actualOutput = commTool.execute(workingDirectory, "comm x.txt w.txt -help ");
 		assertEquals(helpOutputComm,actualOutput);
     }
     
    

 	
 	@Test
 	public void stdinTest(){
 		WCTool wctool = new WCTool();
 		assertEquals(wctool.execute(null, null), "42", "42");
 	}

 	
 	
 	@Test
 	public void validationTest1() {
 		SORTTool sorttool = new SORTTool();
 		assertEquals(sorttool.execute(null, null), "No argument.");
 	}

 	
 	
 	@Test
 	public void NullTest() {
 	
 		PASTETool pasteTool = new PASTETool();
 		assertEquals(
 				pasteTool.execute(null, null),
 				"-s : paste one file at a time instead of in parallel\t"
 						+ " -d DELIM: Use characters from the DELIM instead of TAB character\t"
 						+ " -help : Brief information about supported options");
 	} 
     
     
    private List<File> getFiles(File directory) {
        return new ArrayList<File>(Arrays.asList(directory.listFiles()));
    }
    
    private static File getFile(File workingDir, String path) {
        File toReturn;

        if (path.contains(":")) {
            toReturn = new File(path);
        } else {
            toReturn = new File(workingDir, path);
        }

        return toReturn;
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

	public String readFromFile(File inputFile) {
		String output = "";
		FileReader fr = null;
		try {
			fr = new FileReader(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "File not found";
		}
		BufferedReader br = new BufferedReader(fr);
		try {
			String line = br.readLine();
			while (line != null) {
				if (line.equalsIgnoreCase("\n") || line.equalsIgnoreCase(""))
					output += "\n";
				else
					output += line + "\n";
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Unable to read file";
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

}
