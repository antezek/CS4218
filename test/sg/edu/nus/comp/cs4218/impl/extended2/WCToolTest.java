package sg.edu.nus.comp.cs4218.impl.extended2;
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

import sg.edu.nus.comp.cs4218.extended2.IWcTool;



public class WCToolTest {

	//TODO Always test against the interface! 
	private IWcTool wctool; 
	Path currentRelativePath = Paths.get("");
	String curDir = currentRelativePath.toAbsolutePath().toString();

	File inputFile1,inputFile2;		
	File workingDirectory = new File(curDir);
	
	@Before
	public void before(){
		wctool = new WCTool();
		inputFile1 = new File("input1.txt");
		inputFile2 = new File("input2.txt");
		writeToFile(inputFile1, "abc\nb d\n---\n");
		writeToFile(inputFile2, "");
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
		inputFile1.delete();
		inputFile2.delete();
	}
	
	String actualOutput,expectedOutput;
	
	String helpOutput = "wc : Prints the number of bytes, words, and lines in given file\n"
    + " Command Format - wc [OPTIONS] [FILE]\n"
+ " FILE - Name of the file, when no file is present (denoted by '-') use standard input\n"
+" OPTIONS\n"
+"-m : Print only the character counts\n"
+"-w : Print only the word counts\n"
+"-l : Print only the newline counts\n"
+"-help : Brief information about supported options";
	@Test
    public void helpTest()
    {
		String stdin1 = "wc -help";
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = helpOutput ;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
	
	@Test
    public void helpPriorityTest()
    {String stdin1 = "wc -help input.txt";
    	//String[] arguments = new String[]{"-m" , "-help" , "input.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = helpOutput ;
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
    
	@Test
    public void invalidStdinInputTest()
    {	
		String stdin1 = "wc -m -w -l";
    	//String[] arguments = new String[]{"-m", "-w" , "-l" , "-"} ;
    	wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "Error: file name null";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }

	@Test
    public void invalidFilenameInputTest()
    {
		String stdin1 ="wc -m san.txt";
		//String[] arguments = new String[]{"-m", "-w", "-l", "idontexisthaha.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "Error: file not found";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	
	@Test
    public void noFilenameOrStdinInputTest()
    {
		String stdin1 ="wc -m ";
    	//String[] arguments = new String[]{"-m", "-w", "-l"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "Error: file name null";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
	
	@Test
    public void noFilenameOrStdinOrOptionsInputTest()
    {
		String stdin1 ="wc";
    	//String[] arguments = new String[]{} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "Error: file name null";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
   }
	
	@Test
    public void allOptionsInputTest()
    {
		String stdin1 ="wc -m -w -l input1.txt";
    	//String[] arguments = new String[]{"-m", "-w", "-l", "input1.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: chars= 5 lines= 3 words= 3";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	

	@Test
    public void fewOptionsInputTest()
    {
		String stdin1 ="wc -m -w input1.txt";
    	//String[] arguments = new String[]{"-w", "-m", "input1.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: chars= 5 words= 3";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	
	@Test
    public void capsOptionsInputTest()
    {
		String stdin1 ="wc -M -W -L input1.txt";
    	//String[] arguments = new String[]{"-W", "-M", "-L", "input1.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: chars= 5 lines= 3 words= 3";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
    }
	
		
	@Test
    public void emptyFileInputTest()
    {
		String stdin1 ="wc -M -W -L input2.txt";
    	//String[] arguments = new String[]{"-w", "-m", "-l", WorkingDirectory.workingDirectory + File.separator + "input2.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: chars= 0 lines= 0 words= 0";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
	
	
	@Test
    public void multipleFilenamesInputTest()
    {
		String stdin1 ="wc -w -m input1.txt input2.txt";
	    //String[] arguments = new String[]{"-w", "-m", "input1.txt", "input2.txt"} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput =  "File1: chars= 5 words= 3\n"
				         +"File2: chars= 0 words= 0";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
		
    }
	
	
	
	
	@Test
    public void noOptionsStdinInputTest()
    {
		String stdin1 ="wc input2.txt";
	    //String[] arguments = new String[]{} ;
		wctool = new WCTool();
		actualOutput = wctool.execute(workingDirectory, stdin1);
		expectedOutput = "File1: chars= 0 words= 0 lines= 0";
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}	
	

}
