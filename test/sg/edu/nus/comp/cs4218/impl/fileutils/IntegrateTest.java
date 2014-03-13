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

	File fileW, fileX, fileY, fileZ, inputFile1, inputFile2, inputFile3;
	String fileContentW, fileContentX, fileContentY, fileContentZ, input1,
			input2, input3;
/*
	IntegrateTest() {
		before();
	}
*/
	@Before
	public void before() {
		sh = new Shell();
		workingDirectory = new File(System.getProperty("user.dir"));

		fileW = new File("w.txt");
		fileX = new File("x.txt");
		fileY = new File("y.txt");
		fileZ = new File("z.txt");
		inputFile1 = new File("test1.txt");
		inputFile2 = new File("test2.txt");
		inputFile3 = new File("test3.txt");

		fileContentW = "Apple\nMelon\nOrange";
		fileContentX = "Banana\nMelon\nOrange";
		fileContentY = "Batman\nSpiderman\nSuperman";
		fileContentZ = "Cat\nBat";
		input1 = "apple\nball\ncat\ndog";
		input2 = "hello\nworld\ncoding\nis\nfun";
		input3 = "";

		writeToFile(fileW, fileContentW);
		writeToFile(fileX, fileContentX);
		writeToFile(fileY, fileContentY);
		writeToFile(fileZ, fileContentZ);
		writeToFile(inputFile1, input1);
		writeToFile(inputFile2, input2);
		writeToFile(inputFile3, input3);
		writeToFile(inputFile1, input1);
		writeToFile(inputFile2, input2);
		writeToFile(inputFile3, input3);

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
		String expectedOutput = testNewLine + testDash + testTab + "Banana"
				+ testTab + testDash + testNewLine + testNewLine;
		String actualOutput = "";
		actualOutput = sh.runCmd("comm w.txt x.txt | grep Banana");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	@Test
	// Test " cut -c 1-2 w.txt | paste -d : | grep Ap "
	public void componentIntegrateTest3() {
		String expectedOutput = "Ap\n\n";
		String actualOutput = "";
		actualOutput = sh.runCmd("cut -c 1-2 w.txt | paste -d : | grep Ap");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	@Test
	// Test " cut -c 1-2 test1.txt | wc "
	public void componentIntegrateTest4() {
		String expectedOutput = "File1: chars= 8 words= 4 lines= 4";
		String actualOutput = "";

		actualOutput = sh.runCmd("cut -c 1-2 test1.txt | wc");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	@Test
	// Test " grep -C 2 haha PASSAGE2.txt LOST.txt | sort "
	public void componentIntegrateTest5() {
		String expectedOutput = "File1:\nAnd I your willing victim\n"
				+ "And we can haha learn to love again\n"
				+ "And with every touch you fixed them\n"
				+ "Between haha our love, our love, oh our love, our love\n"
				+ "I thought that we were fine\nIm sorry I don't understand where all of this is coming from\n"
				+ "It's been written in the scars on our hearts\nIt's in the stars\n"
				+ "Just a little bit's enough\nJust a second we're not broken just bent\n"
				+ "LOST.txt:\nMy dear we still have everything\nNo such files or directory\n"
				+ "Now you've been talking in your sleep\nOf our love, our love\nOh oh\n"
				+ "Oh we had everything haha\nRight from haha the start\nTell me that you've had enough\n"
				+ "There's nothing more than empty sheets\nThings you never haha say to me\n"
				+ "You were a thief you stole my heart\nYour head is running wild again";

		String actualOutput = "";

		actualOutput = sh.runCmd("grep -C 2 haha PASSAGE2.txt LOST.txt | sort");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	@Test
	// Test " cat PASSAGE2.txt | grep haha | sort "
	public void componentIntegrateTest6() {
		String expectedOutput = "File1:\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "And we can haha learn to love again\nBetween haha our love, our love, oh our love, our love\n"
				+ "Oh we had everything haha\nRight from haha the start\nThings you never haha say to me";
		String actualOutput = "";

		actualOutput = sh.runCmd("cat PASSAGE2.txt | grep haha | sort");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	@Test
	// Test grep -B 1 Fashion PASSAGE.txt | wc
	public void componentIntegrateTest7() {
		String expectedOutput = "File1: chars= 421 words= 83 lines= 7";
		String actualOutput = "";

		actualOutput = sh.runCmd("grep -B 1 Fashion PASSAGE.txt | wc");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
	
	@Test
	// Test " ls | sort "
	public void componentIntegrateTest8() {
		String expectedOutput = "File1:\n.DS_Store\n.classpath\n.git\n.project\n.settings\nPASSAGE.txt\n"
				+ "PASSAGE2.txt\nPASSAGE3.txt\nREADME.txt\na.txt\nb.txt\nbin\nc.txt\nd.txt\nem1.txt\nem2.txt\ninput1.txt\n"
				+ "input2.txt\nmisc\nnulltest.txt\nsrc\ntemppipefile.txt\ntest\ntest1.txt\ntest2.txt\ntest3.txt\nunsort.txt\nw.txt\n"
				+ "x.txt\ny.txt\nz.txt";
		String actualOutput = "";

		actualOutput = sh.runCmd("ls | sort");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
	
	@Test
	// Test " echo hello world, Programming is fun!! :D | wc "
	public void componentIntegrateTest9() {
		String expectedOutput = "File1: chars= 27 words= 6 lines= 1";
		String actualOutput = "";

		actualOutput = sh.runCmd("echo hello world, Programming is fun!! :D | wc");
		 System.out.println("actualOutput:\n"+actualOutput);
		 System.out.println("result:\n"+expectedOutput);
		 System.out.println("Assert: "+expectedOutput.equalsIgnoreCase(actualOutput));
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}
}
