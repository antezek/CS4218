package sg.edu.nus.comp.cs4218.impl.fileutils;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;

public class SORTToolTest {

	//TODO Always test against the interface! 
	private ISortTool wctool;
	
	Path currentRelativePath = Paths.get("");
	String curDir = currentRelativePath.toAbsolutePath().toString();

	File inputFile1,inputFile2,inputFile3;		
	File workingDirectory = new File(curDir);
	
	@Before
	public void before(){
		wctool = new SORTTool();
		String input1 = "apple\nball\ncat\ndog";
		String input2 = "hello\nworld\ncoding\nis\nfun";
		String input3 = "";
		inputFile1 = new File("test1.txt");
		inputFile2 = new File("test2.txt");
		inputFile3 = new File("test3.txt");
		writeToFile(inputFile1, input1);
		writeToFile(inputFile2, input2);
		writeToFile(inputFile3, input3);

		}

	private void writeToFile(File file, String input) {
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

	@After
	public void after(){
		wctool = null;
		if(inputFile1.exists())
			inputFile1.delete();
		if(inputFile2.exists())
			inputFile2.delete();
		if(inputFile3.exists())
			inputFile3.delete();
	}
	String actualOutput,expectedOutput;
	
	String helpOutput =" sort : sort lines of text file\n"
			 
			 +" Command Format - sort [OPTIONS] [FILE]\n"
			 +"	FILE - Name of the file\n"
			 +"	OPTIONS\n"
			 +"		-c : Check whether the given file is already sorted, if it is not all sorted, print a\n"
			 +"           diagnostic containing the first line that is out of order\n"
			 +"	    -help : Brief information about supported options";
	@Test
    public void helpTest()
    {
		String stdin1 = "sort -help";
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = helpOutput ;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
	
	@Test
    public void helpPriorityTest()
    {String stdin1 = "sort -help test3.txt";
    	//String[] arguments = new String[]{"-m" , "-help" , "input.txt"} ;
    wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = helpOutput ;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
	@Test
    public void multipleOptionsTest2()
    {String stdin1 = "sort -c -help test3.txt";
    	//String[] arguments = new String[]{"-m" , "-help" , "input.txt"} ;
    wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = helpOutput ;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
    @Test
    public void multipleOptionsTest1()
    {
    	
    	String stdin1 = "sort -c -c test1.txt";
    	//String[] arguments = new String[]{"-c", "-c" ,"test1.txt"} ;
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: Already sorted";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
	@Test
    public void invalidStdinInputTest()
    {	
		String stdin1 = "sort -c";
    	//String[] arguments = new String[]{"-m", "-w" , "-l" , "-"} ;
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "Error: file name null";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }

	@Test
    public void invalidFilenameInputTest()
    {
		String stdin1 ="sort -c san.txt";
		//String[] arguments = new String[]{"-m", "-w", "-l", "idontexisthaha.txt"} ;
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "Error: file not found";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	
		
	@Test
    public void noFilenameOrStdinOrOptionsInputTest()
    {
		String stdin1 ="sort";
    	//String[] arguments = new String[]{} ;
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "Error: file name null";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
   }
	
	

	@Test
    public void sortedFileInputTest()
    {
		String stdin1 ="sort test1.txt";
    	//String[] arguments = new String[]{"-w", "-m", "input1.txt"} ;
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: Already sorted";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	
	@Test
    public void capsOptionsInputTest()
    {
		String stdin1 ="sort -C test1.txt";
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: Already sorted";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	
		
	@Test
    public void emptyFileInputTest()
    {
		String stdin1 ="sort test3.txt";
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: Already sorted";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
	
	
	@Test
    public void multipleFilenamesInputTest()
    {
		String stdin1 ="sort -c test1.txt test2.txt";
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "File1: Already sorted\n"
				         +"File2: not sorted";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
	
	
	
	
	@Test
    public void StdinInputTest()
    {
		String stdin1 ="sort test2.txt";
	    //String[] arguments = new String[]{} ;
		wctool = new SORTTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1:\ncoding\nfun\nhello\nis\nworld";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}	
	

}
