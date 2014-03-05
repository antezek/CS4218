package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ICutTool;


public class CUTToolTest {

	private ICutTool cuttool; 
	String actualOutput,expectedOutput,helpOutput;
	File workingDirectory;
	File inputFile1, inputFile2, inputFile3,inputFile4;
	String absoluteFilePath;
	
	@Before
	public void before(){
		
		absoluteFilePath = System.getProperty("home.dir")+"test.txt";
		workingDirectory = new File(System.getProperty("user.dir"));
		
		helpOutput = "usage: cut [OPTIONS] [FILE]" + "\n"
				+ "FILE : Name of the file, when no file is present" + "\n"
				+ "OPTIONS : -c LIST : Use LIST as the list of characters to cut out. Items within "
				+ "the list may be separated by commas, "
				+ "and ranges of characters can be separated with dashes. "
				+ "For example, list 1-5,10,12,18-30 specifies characters "
				+ "1 through 5, 10,12 and 18 through 30" + "\n"
				+ "-d DELIM: Use DELIM as the field-separator character instead of"
				+ "the TAB character" + "\n" 
				+ "-help : Brief information about supported options";
		
		
		String input1 = "apple\nball\ncat\ndog";
		String input2 = "hello\nworld\ncoding\nis\nfun";
		String input3 = "";
		inputFile1 = new File("test1.txt");
		inputFile2 = new File("test2.txt");
		inputFile3 = new File("test3.txt");
		inputFile4 = new File(absoluteFilePath);
		writeToFile(inputFile1, input1);
		writeToFile(inputFile2, input2);
		writeToFile(inputFile3, input3);
		writeToFile(inputFile4, input1);
	}

    @After
	public void after(){
		cuttool = null;
		
		if(inputFile1.exists())
			inputFile1.delete();
		if(inputFile2.exists())
			inputFile2.delete();
		if(inputFile3.exists())
			inputFile3.delete();
		if(inputFile4.exists())
			inputFile4.delete();
	}
	
    public void writeToFile(File file, String input){
		try{
			if(!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			char[] temp = input.toCharArray(); int i = 0;
			while(i<temp.length){
				while(temp[i]!='\n'){
					bw.write(temp[i]);
					i++;
					if(i>=temp.length)
						break;
				}
				bw.newLine(); i++;
			}
			bw.close();
		} catch (IOException e){
		}
	}

    @Test
    public void cOptionTest()
    {	
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2","-", "abcde"} ;
		//cuttool = new CUTTool(arguments);
    	cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 - abcde");
		expectedOutput = "ab";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    @Test
    //Both std input and file input are executed (std input first)
	public void fileInputAndStdInputTest()
	{
		//Changes: command stdin is shifted to execute method to cater to our project
		//String[] arguments = new String[]{"-c", "1-2","test1.txt","-"} ;
		//cuttool = new CUTTool(arguments);
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 test1.txt - abcde");
		expectedOutput = "ab\nap\nba\nca\ndo\n";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}
	
	@Test
	public void absoluteFilePathTest()
	{
		//Changes: command stdin is shifted to execute method to cater to our project
		//String[] arguments = new String[]{"-c", "1-2", absoluteFilePath } ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 "+absoluteFilePath);
		expectedOutput = "ap\nba\nca\ndo\n";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}
	
    @Test
    public void cOptionInputInvalidRangeTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "2-1","-"} ;
		//cuttool = new CUTTool(arguments);
    	cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 2-1 - abcde");
		expectedOutput = "";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    @Test
    public void dOptionFollowedByFOptionTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-d", ":","-f","1-2","-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -d : -f 1-2 - one:two:three:four:five:six:seven");
		expectedOutput = "one:two";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    @Test
    public void fOptionFollowedByDOptionTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-f", "1-2","-d",":","-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -f 1-2 -d : - one:two:three:four:five:six:seven");
		expectedOutput = "one:two";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
   
    @Test
    public void dOptionWithFileTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-d", ":","-f","0","test1.txt"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -d : -f 0 test1.txt");
		expectedOutput = "apple\nball\ncat\ndog\n";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertEquals(actualOutput,expectedOutput);
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    @Test
    public void cOptionWithFileTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2","test1.txt"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 test1.txt");
		expectedOutput = "ap\nba\nca\ndo\n";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    //In this test method, the file specified is empty
    @Test
    public void cOptionWithEmptyFileTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2","test3.txt"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 test3.txt");
		expectedOutput = "";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    //In case of multiple files, cut option is executed for all files
    @Test
    public void cOptionWithMultipleFilesTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2","test1.txt","test2.txt"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 test1.txt test2.txt");
		expectedOutput = "ap\nba\nca\ndo\n\n"+ "he\nwo\nco\nis\nfu\n";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    
    @Test
    public void cOptionWithFileMissingTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2","file.txt"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 file.txt");
		expectedOutput = "File not found";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), -1);
    }
    
    ///*Cut tool executes all options */

    @Test
    public void cOptionRepeatedTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2", "-c", "3-4", "-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 -c 3-4 - abcde");
		expectedOutput = "abcd";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    // in case of multiple d options, the latest delimiter is considered
    @Test
    public void dOptionRepeatedTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-d", " ", "-d", ":", "-f","1-3","-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -d   -d : -f 1-3 - one:two:three:four:five:six:seven");
		expectedOutput = "one:two:three";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
    //when c and d options are present, c option is taken as priority
    @Test
    public void cDOptionTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2", "-d", ":", "-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 -d : - one:two:three:four:five:six:seven");
		expectedOutput = "on";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
   //when c and f options are present, c option is taken as priority
    @Test
    public void cFOptionTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2", "-f", "2", "-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 -f 2 - one:two:three:four:five:six:seven");
		expectedOutput = "on";
		actualOutput= actualOutput.replace("\n", "");
		expectedOutput=expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
    
   //when c , d and f options are present, all options are executed
    @Test
    public void cDFOptionTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-c", "1-2", "-f", "2-4", "-d", ":","-"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -c 1-2 -f 2-4 -d : - one:two:three:four:five:six:seven");
		expectedOutput = "ontwo:three:four";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
  
    @Test
    public void helpTest()
    {
    	//Changes: command stdin is shifted to execute method to cater to our project
    	//String[] arguments = new String[]{"-help"} ;
		cuttool = new CUTTool();
		actualOutput = cuttool.execute(workingDirectory, "cut -help");
		expectedOutput = helpOutput;
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertEquals(expectedOutput, actualOutput);
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
  
	@Test
	public void cutSpecfiedCharactersTest1() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecfiedCharacters("1-2", "abc");
		expectedOutput = "ab";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}
	
	@Test
	public void cutSpecfiedCharactersTest2() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecfiedCharacters("1-4,5,6,10", "the quick brown fox jumps over the lazy dog");
		expectedOutput = "the qu ";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }
	
	@Test 
	//LIST with negative values as -2-3 as  1 till 2
	public void cutSpecfiedCharactersTest3() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecfiedCharacters("-2-3", "abc");
		expectedOutput = "ab";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertEquals(actualOutput,expectedOutput);
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}

	@Test
	public void cutSpecfiedCharactersForEmptyStringTest() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecfiedCharacters("1-2", "");
		expectedOutput = "";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
    }

	//5- as LIST is interpreted as 5 till end of string
	@Test
	public void cutSpecifiedCharactersUseDelimiterTest1() throws IOException 
	{
		cuttool = new CUTTool();	
		actualOutput = cuttool.cutSpecifiedCharactersUseDelimiter("5-", ":" ,"one:two:three:four:five:six:seven");
		expectedOutput = "five:six:seven";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}
	
	//-3 as LIST is interpreted as 1-3
	@Test
	public void cutSpecifiedCharactersUseDelimiterTest2() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecifiedCharactersUseDelimiter("-3", ":" ,"one:two:three:four:five:six:seven");
		expectedOutput = "one:two:three";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}	

	//test 3
	@Test
	public void cutSpecifiedCharactersUseDelimiterTest3() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecifiedCharactersUseDelimiter("1-10", ":" ,"one:two:three:four:five:six:seven");
		expectedOutput = "one:two:three:four:five:six:seven";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}	

	//test 4
	@Test
	public void cutSpecifiedCharactersUseDelimiterTest4() throws IOException 
	{	
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecifiedCharactersUseDelimiter("3", " " ,"foo:bar:baz:qux:quux");
		expectedOutput = "foo:bar:baz:qux:quux";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
	}
	
	@Test
	public void cutSpecifiedCharactersUseDelimiterTest5() throws IOException 
	{
		cuttool = new CUTTool();
		actualOutput = cuttool.cutSpecifiedCharactersUseDelimiter("3", " " ,"the quick brown fox jumps over the lazy dog");
		expectedOutput = "brown";
		actualOutput = actualOutput.replace("\n", "");
		expectedOutput = expectedOutput.replace("\n", "");
		assertTrue(expectedOutput.equals(actualOutput));
		assertEquals(cuttool.getStatusCode(), 0);
		
    }
	

}
