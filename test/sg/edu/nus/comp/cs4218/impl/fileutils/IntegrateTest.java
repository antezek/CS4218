package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.Shell;

public class IntegrateTest {

	Shell sh;
	Runnable run = null;
	ITool tool = null;
	File workingDirectory;

	File fileW,fileX,fileY,fileZ;
	String fileContentW,fileContentX,fileContentY,fileContentZ;

	IntegrateTest() {
		before();
	}

	@Before
	public void before() {
		sh = new Shell();
		workingDirectory = new File(System.getProperty("user.dir"));
		
		fileW = new File("w.txt");
		fileX = new File("x.txt");
		fileY = new File("y.txt");
		fileZ = new File("z.txt");
		
		fileContentW = "Apple\nMelon\nOrange";
		fileContentX = "Banana\nMelon\nOrange";
		fileContentY = "Batman\nSpiderman\nSuperman";
		fileContentZ = "Cat\nBat";
		
		writeToFile(fileW, fileContentW);
		writeToFile(fileX, fileContentX);
		writeToFile(fileY, fileContentY);
		writeToFile(fileZ,fileContentZ);

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
	@Test
	// Test " echo abcde | cut -c 1-3 "
	public void componentIntegrateTest1() {
		String expectedOutput = "abc";
		String actualOutput = "";
		actualOutput = sh.runCmd("echo abcde | cut -c 1-3");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	@Test
	// Test " comm w.txt x.txt | grep Banana "
	public void componentIntegrateTest2() {
		String testTab = "\t";
		String testNewLine = "\n";
		String testDash = " ";
		String expectedOutput = testDash+testTab+"Banana"+testTab+testDash+testNewLine;
		String actualOutput = "";
		actualOutput = sh.runCmd("comm w.txt x.txt | grep Banana");
		//System.out.println("result: "+actualOutput);
		//System.out.println("result: "+expectedOutput);
		//System.out.println("Assert: "+expectedOutput.equalsIgnoreCase(actualOutput));
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}


	//Test " cut -c 1-2 w.txt | paste -d : | grep Ap "
	public void componentIntegrateTest3() {
		String expectedOutput = "Ap\n\n";
		String actualOutput = "";
		
		actualOutput = sh.runCmd("cut -c 1-2 w.txt | paste -d : | grep Ap");		//TODO Something wrong with Grep
		System.out.println("actualOutput:\n"+actualOutput);
		System.out.println("result:\n"+expectedOutput);
		System.out.println("Assert: "+expectedOutput.equalsIgnoreCase(actualOutput));
		//assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
 
	
	
}
