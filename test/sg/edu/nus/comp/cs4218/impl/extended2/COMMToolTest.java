package sg.edu.nus.comp.cs4218.impl.extended2;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;
import sg.edu.nus.comp.cs4218.extended2.IPasteTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.COMMTool;

public class COMMToolTest {
	
	public COMMToolTest(){
		
	}
	private ICommTool commTool;
	String actualOutput,expectedOutput,helpOutput;
	File workingDirectory;

	File fileA,fileB,fileC,fileD,fileEM1,fileEM2;
	String fileContentA,fileContentB,fileContentC,fileContentD,fileContentE;
	String testTab, testNewLine, testDash;
	
	@Before
	public void before() throws Exception {
		workingDirectory = new File(System.getProperty("user.dir"));
		helpOutput = " /*\n" +
				"\n*" +
				"\n* comm : Compares two sorted files line by line. With no options, produce three-column output." +
				"\n* 		 Column one contains lines unique to FILE1, column two contains lines unique to FILE2," +
				"\n 		 and column three contains lines common to both files."+
				"\n*" +	
				"\n*	Command Format - comm [OPTIONS] FILE1 FILE2" +
				"\n*	FILE1 - Name of the file 1" +
				"\n*	FILE2 - Name of the file 2" +
				"\n*		-c : check that the input is correctly sorted" +
				"\n*      -d : do not check that the input is correctly sorted" +
				"\n*      -help : Brief information about supported options" +
				"\n*/";

		fileA = new File("a.txt");
		fileB = new File("b.txt");
		fileC = new File("c.txt");
		fileD = new File("d.txt");
		fileEM1 = new File("em1.txt");
		fileEM2 = new File("em2.txt");
		fileEM1.createNewFile();
		fileEM2.createNewFile();
		
		fileContentA = "Apple\nMelon\nOrange";
		fileContentB = "Banana\nMelon\nOrange";
		fileContentC = "Batman\nSpiderman\nSuperman";
		fileContentD = "Cat\nBat";
		fileContentE = "";
		
		writeToFile(fileA, fileContentA);
		writeToFile(fileB, fileContentB);
		writeToFile(fileC, fileContentC);
		writeToFile(fileD,fileContentD);
		
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";

	}

	private void writeToFile(File f, String fContent){
		BufferedWriter bw;
		try {
			String[] lines= fContent.split("\n");
			bw = new BufferedWriter(new FileWriter(f));

			for(int i=0; i<lines.length; i++){
				bw.write(lines[i]); bw.newLine();
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//@After
	public void after() throws Exception {
		commTool = null;

		fileA.delete();
		fileB.delete();
		fileC.delete();
		fileD.delete();
		fileEM1.delete();
		fileEM2.delete();
	}

	@Test
	//Test if overall control flow is correct
	public void overallTest() {
		//String[] arguments = new String[]{"a.txt","b.txt"};

		//commTool = new COMMTool(arguments);
		commTool = new COMMTool();
		actualOutput = commTool.execute(workingDirectory, "comm a.txt b.txt");
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash + testNewLine +
				testDash + testTab + "Banana" + testTab +testDash + testNewLine +
				testDash + testTab + testDash + testTab + "Melon" + testNewLine +
				testDash + testTab + testDash + testTab + "Orange";
		
	/*	
		System.out.println("actualOutput:\n"+actualOutput);
		System.out.println("expectedOutput:\n"+expectedOutput);
		System.out.println("bool: "+expectedOutput.equalsIgnoreCase(actualOutput));
		System.out.println("statusCode: "+commTool.getStatusCode());
	*/
		
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}
	
	@Test
	//File doesn't exist
	public void compareFilesInvalidFileArgsTest(){
		String fileName1 = "C:\\Users\\Dale\\a.txt";
		String fileName2 = "./b.txt";
		String[] arguments = new String[]{"C:\\Users\\Dale\\a.txt","./b.txt"};
		commTool = new COMMTool(arguments);		
		actualOutput = commTool.execute(workingDirectory, "comm C:\\Users\\Dale\\a.txt ./b.txt");

		expectedOutput = "File 1 doesn't exist!";
		assertEquals(expectedOutput, actualOutput);	

	}
	
	@Test
	//comm -c -help file1 file2
	public void compareFilesGiveHelpPriorityTest(){
		String[] arguments = new String[]{"-c","-help","file1","file2"};
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm -c -help file1 file2");
		expectedOutput = helpOutput;

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}
	
	
	//Both file arguments are empty files
	//comm em1.txt em2.txt
	public void compareFilesNoOptionsBothFilesEmptyTest(){
		String[] arguments = new String[]{"em1.txt","em2.txt"};
		String input1 = "em1.txt";
		String input2 = "em2.txt";
		commTool = new COMMTool(arguments);
		//Changes: use execute instead of compareFiles
		actualOutput = commTool.execute(workingDirectory, "comm em1.txt em2.txt");	
		//actualOutput = commTool.compareFiles(input1, input2);
		expectedOutput = "";
		
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}
	
	
	/*		@Changed
	  		expectedOutput = "Apple" + testTab + testDash + testTab + testDash + testNewLine +
			testDash + testTab + "Batman" + testTab +testDash + testNewLine +
			"Melon" + testTab + testDash + testTab + testDash + testNewLine +
			"Orange" + testTab + testDash + testTab + testDash + testNewLine +
			testDash + testTab + "Spiderman" + testTab +testDash + testNewLine +
			testDash + testTab + "Superman" + testTab +testDash;
			
			"testTab + testDash" after "Superman" is removed to be more precise. 
	 */
	@Test
	//All lines in both files are unique. Column 3 should be empty
	public void compareFilesNoOptionsAllUniqueTest(){
		testTab = "\t";
		testNewLine = "\n";
		testDash = " ";
		String[] arguments = new String[]{"a.txt","c.txt"};
		String input1 = "a.txt";
		String input2 = "c.txt";
		commTool = new COMMTool(arguments);
		//Changes: use execute instead of compareFiles
		actualOutput = commTool.execute(workingDirectory, "comm a.txt c.txt");	
		//actualOutput = commTool.compareFiles(input1, input2);
		
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash + testNewLine +
				testDash + testTab + "Batman" + testTab +testDash + testNewLine +
				"Melon" + testTab + testDash + testTab + testDash + testNewLine +
				"Orange" + testTab + testDash + testTab + testDash + testNewLine +
				testDash + testTab + "Spiderman" + testTab +testDash + testNewLine +
				testDash + testTab + "Superman";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);		
		
	}
	
	@Test
	//Some common lines. All three columns have items
	public void compareFilesNoOptionsSomeUniqueTest(){
		String[] arguments = new String[]{"a.txt","b.txt"};
		String input1 = "a.txt";
		String input2 = "b.txt";
		commTool = new COMMTool(arguments);
		//Changes: use execute instead of compareFiles
		actualOutput = commTool.execute(workingDirectory, "comm a.txt b.txt");	
		//actualOutput = commTool.compareFiles(input1, input2);
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash + testNewLine +
				testDash + testTab + "Banana" + testTab +testDash + testNewLine +
				testDash + testTab + testDash + testTab + "Melon" + testNewLine +
				testDash + testTab + testDash + testTab + "Orange";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}
	
	@Test
	//No unique lines at all. Col1 and Col2 are empty
	public void compareFilesNoOptionsNoneUniqueTest(){
		String[] arguments = new String[]{"a.txt","a.txt"};
		String input1 = "a.txt";
		String input2 = "a.txt";
		commTool = new COMMTool(arguments);
		actualOutput = commTool.execute(workingDirectory, "comm a.txt a.txt");	//Changes: use execute instead of compareFiles
		//actualOutput = commTool.compareFiles(input1, input2);
		expectedOutput = 
				 testDash + testTab + testDash + testTab + "Apple" + testNewLine +
				 testDash + testTab + testDash + testTab + "Melon" + testNewLine +
				 testDash + testTab + testDash + testTab + "Orange";
		
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}

	//======================================================================================
	@Test
		//Test for invalid command input
		public void invalidCommand(){
			String[] arguments = new String[]{"-e","a.txt","b.txt"};
			commTool = new COMMTool(arguments);
			actualOutput = commTool.execute(workingDirectory, "comm -e a.txt b.txt");
			expectedOutput = "Error: Invalid command";
			/*
			System.out.println("actualOutput:\n"+actualOutput);
			System.out.println("expectedOutput:\n"+expectedOutput);
			System.out.println("bool: "+expectedOutput.equalsIgnoreCase(actualOutput));
			System.out.println("statusCode: "+commTool.getStatusCode());
			*/
			assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
			assertEquals(commTool.getStatusCode(), 1);	
		}
		
	@Test
	//Positive case: Files are sorted
	public void compareFilesCheckSortStatusTest1(){
		String[] arguments = new String[]{"-c","a.txt","b.txt"};
		String input1 = "a.txt";
		String input2 = "b.txt";
		commTool = new COMMTool(arguments);
		//Changes: use execute instead of compareFiles
		actualOutput = commTool.execute(workingDirectory, "comm -c a.txt b.txt");	
		//actualOutput = commTool.compareFilesCheckSortStatus(input1, input2);
		
		expectedOutput = "Apple" + testTab + testDash + testTab + testDash + testNewLine +
				testDash + testTab + "Banana" + testTab +testDash + testNewLine +
				testDash + testTab + testDash + testTab + "Melon" + testNewLine +
				testDash + testTab + testDash + testTab + "Orange";

		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}
	

	@Test		
	//Negative case: File 2 not sorted
	public void compareFilesCheckSortStatusTest2(){
		String[] arguments = new String[]{"-c","a.txt","unsort.txt"};
		String input1 = "a.txt";
		String input2 = "d.txt";
		commTool = new COMMTool(arguments);
		//Changes: use execute instead of compareFiles
		actualOutput = commTool.execute(workingDirectory, "comm -c a.txt unsort.txt"); 
		expectedOutput = "File 2 not sorted!";	
		//actualOutput = commTool.compareFilesCheckSortStatus(input1, input2);

		//Changes: modify the result from "File 2 not sorted!\n" to "File 2 not sorted!" to be more concise
		//expectedOutput = "File 2 not sorted!\n";				
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(commTool.getStatusCode(), 0);	
	}
}
