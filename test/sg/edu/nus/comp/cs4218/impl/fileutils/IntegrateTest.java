package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.Shell;

public class IntegrateTest {
	private static final File homeDir = new File(System.getProperty("user.dir"));
	private Shell sh;
	private File workingDir;
	private int os = 1; // 0 for Windows, 1 for Mac

	private File fileW, fileX, fileY, fileZ, inputFile1, inputFile2,
			inputFile3, fileS, fileT, tempFile, tempFile2, tempDir;
	private String fileContentW, fileContentX, fileContentY, fileContentZ,
			input1, input2, input3, passage, passage2;
	String pathSep;

	@Before
	public void before() throws IOException {
		sh = new Shell();
		workingDir = new File(System.getProperty("user.dir"));
		pathSep = java.nio.file.FileSystems.getDefault().getSeparator();

		fileW = new File("w.txt");
		fileX = new File("x.txt");
		fileY = new File("y.txt");
		fileZ = new File("z.txt");
		inputFile1 = new File("test1.txt");
		inputFile2 = new File("test2.txt");
		inputFile3 = new File("test3.txt");
		fileS = new File("PASSAGE.txt");
		fileT = new File("PASSAGE2.txt");

		fileContentW = "Apple\nMelon\nOrange";
		fileContentX = "Banana\nMelon\nOrange";
		fileContentY = "Batman\nSpiderman\nSuperman";
		fileContentZ = "Cat\nBat";
		input1 = "apple\nball\ncat\ndog";
		input2 = "hello\nworld\ncoding\nis\nfun";
		input3 = "";
		passage = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n"
				+ "accessories, makeup, body piercing, or furniture.\n"
				+ "Fashion refers to a distinctive and often habitual trend in the style\n"
				+ "in which a person dresses or to prevailing styles in behaviour.\n"
				+ "Fashion also refers to the newest creations of textile designers.\n"
				+ "The more technical term costume has become so linked to its term\n"
				+ "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n"
				+ "while Fashion means clothing more generally, including the study of it.\n"
				+ "Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";

		passage2 = "Right from haha the start\n"
				+ "You were a thief you stole my heart\n"
				+ "And I your willing victim\n"
				+ "I let you see the parts of me\n"
				+ "That weren't all that pretty\n"
				+ "And with every touch you fixed them\n"
				+ "Now you've been talking in your sleep\n"
				+ "Things you never haha say to me\n"
				+ "Tell me that you've had enough\n"
				+ "Of our love, our love\n"
				+ "\n"
				+ "Just give me a reason\n"
				+ "Just a little bit's enough\n"
				+ "Just a second we're not broken just bent\n"
				+ "And we can haha learn to love again\n"
				+ "It's in the stars\n"
				+ "It's been written in the scars on our hearts\n"
				+ "We're not broken just bent\n"
				+ "And we can learn to love again\n"
				+ "Im sorry I don't understand where all of this is coming from\n"
				+ "I thought that we were fine\n"
				+ "Oh we had everything haha\n"
				+ "Your head is running wild again\n"
				+ "My dear we still have everything\n"
				+ "And its all in your mind\n" + "Yeah but this is happening\n"
				+ "You've been having real bad dreams\n" + "Oh oh\n"
				+ "You used to lie so close to me\n" + "Oh oh\n"
				+ "There's nothing more than empty sheets\n"
				+ "Between haha our love, our love, oh our love, our love\n";

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
		writeToFile(fileS, passage);
		writeToFile(fileT, passage2);

		tempFile = new File("./misc/tempFile.txt");
		tempFile.createNewFile();

		tempFile2 = new File("./misc/tempFile2.txt");
		tempFile2.createNewFile();

		tempDir = new File("./misc/misc2");
		tempDir.mkdir();
	}

	// @After
	public void after() throws Exception {
		System.gc();
		fileW.delete();
		fileX.delete();
		fileY.delete();
		fileZ.delete();
		inputFile1.delete();
		inputFile2.delete();
		inputFile3.delete();
		fileS.delete();
		fileT.delete();

		tempFile.delete();
		tempFile2.delete();
		tempDir.delete();
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

	/*
	 * Test " echo | cut "
	 */
	@Test
	public void componentIntegrateTest1() {
		String expectedOutput = "abc";
		String actualOutput = "";
		actualOutput = sh.runCmd("echo abcde | cut -c 1-3");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " comm | grep "
	 */
	@Test
	public void componentIntegrateTest2() {
		String testTab = "\t";
		String testDash = " ";
		String expectedOutput = testDash + testTab + "Banana" + testTab
				+ testDash;
		String actualOutput = "";
		actualOutput = sh.runCmd("comm w.txt x.txt | grep Banana");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " cut | paste | grep "
	 */
	@Test
	public void componentIntegrateTest3() {
		String expectedOutput = "Ap";
		String actualOutput = "";
		actualOutput = sh.runCmd("cut -c 1-2 w.txt | paste -d : | grep Ap");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " cut | wc "
	 */
	@Test
	public void componentIntegrateTest4() {
		String expectedOutput = "File1: chars= 8 words= 4 lines= 4";
		String actualOutput = "";
		actualOutput = sh.runCmd("cut -c 1-2 test1.txt | wc");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " grep | sort "
	 */
	@Test
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
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " cat | grep | sort "
	 */
	@Test
	public void componentIntegrateTest6() {
		String expectedOutput = "File1:\n"
				+ "And we can haha learn to love again\nBetween haha our love, our love, oh our love, our love\n"
				+ "Oh we had everything haha\nRight from haha the start\nThings you never haha say to me";
		String actualOutput = "";

		actualOutput = sh.runCmd("cat PASSAGE2.txt | grep haha | sort");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test grep | wc
	 */
	@Test
	public void componentIntegrateTest7() {
		String expectedOutput = "File1: chars= 421 words= 83 lines= 7";
		String actualOutput = "";

		actualOutput = sh.runCmd("grep -B 1 Fashion PASSAGE.txt | wc");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " ls | sort "
	 */
	@Test
	public void componentIntegrateTest8() {
		String expectedOutput;
		if (os == 1) { // For Mac
			expectedOutput = "File1:\n.DS_Store\n.\\misc\\tempemptyfile.txt\n.\\misc\\tempreadfile.txt\n.classpath\n.git\n.project\n.settings\nPASSAGE.txt\nPASSAGE2.txt\nREADME.txt\nbin\nmisc\nsrc\ntest\ntest1.txt\ntest2.txt\ntest3.txt\ntest4.txt\nw.txt\nx.txt\ny.txt\nz.txt";
		} else { // For Window
			expectedOutput = "File1:\n.\\misc\\tempemptyfile.txt\n.\\misc\\tempreadfile.txt\n.classpath\n.git\n.project\n.settings\nPASSAGE.txt\nPASSAGE2.txt\nREADME.txt\nbin\nmisc\nsrc\ntest\ntest1.txt\ntest2.txt\ntest3.txt\ntest4.txt\nw.txt\nx.txt\ny.txt\nz.txt";
		}

		String actualOutput = "";
		actualOutput = sh.runCmd("ls | sort");
		System.out.println("actualOutput: " + actualOutput);
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * Test " echo | wc "
	 */
	@Test
	public void componentIntegrateTest9() {
		String expectedOutput = "File1: chars= 27 words= 6 lines= 1";
		String actualOutput = "";

		actualOutput = sh
				.runCmd("echo hello world, Programming is fun!! :D | wc");
		assertTrue(expectedOutput.equalsIgnoreCase(actualOutput));
	}

	/*
	 * Test " echo | cut | paste | grep | wc  "
	 */
	@Test
	public void componentIntegrateTest10() {
		String expectedOutput = "File1: chars= 21 words= 3 lines= 1";
		String actualOutput = "";

		actualOutput = sh
				.runCmd("echo hello world, Programming is fun!! | cut -c 1-24 | paste -d : | grep Program | wc");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * negative Test case " cd | sort "
	 */
	@Test
	public void componentIntegrateTest11() {
		String expectedOutput = "invalid input";
		String actualOutput = "";

		actualOutput = sh.runCmd("cd | sort");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * negative Test case " delete | sort "
	 */
	@Test
	public void componentIntegrateTest12() {
		String expectedOutput = "invalid input";
		String actualOutput = "";

		actualOutput = sh.runCmd("delete | sort");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * negative Test case " ls | delete "
	 */
	@Test
	public void componentIntegrateTest13() {
		String expectedOutput = "invalid input";
		String actualOutput = "";

		actualOutput = sh.runCmd("ls | delete");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * negative Test case " delete | move | cd "
	 */
	@Test
	public void componentIntegrateTest14() {
		String expectedOutput = "invalid input";
		String actualOutput = "";

		actualOutput = sh.runCmd("delete | move | cd");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * negative Test case " CD | delete "
	 */
	@Test
	public void componentIntegrateTest15() {
		String expectedOutput = "invalid input";
		String actualOutput = "";

		actualOutput = sh.runCmd("ls | delete");
		assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * negative Test case " | "
	 */
	@Test
	public void componentIntegrateTest16() {
		String expectedOutput = "invalid input";
		String actualOutput = "";

		actualOutput = sh.runCmd(" | ");
		assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void shellStateTest1() throws IOException {
		String expected = "Working dir changed to: ";
		String actual = "";

		actual = sh.runCmd("cd misc");
		String newDir = sh.runCmd("pwd");
		expected += newDir;
		assertEquals(expected, actual);

		workingDir = new File(System.getProperty("user.dir"));
		expected = "Moved file tempFile.txt to " + workingDir.getAbsolutePath()
				+ pathSep + "misc2";
		actual = sh.runCmd("move tempFile.txt misc2");
		assertEquals(expected, actual);

		expected = "Working dir changed to: ";
		actual = sh.runCmd("cd misc2");
		newDir = sh.runCmd("pwd");
		expected += newDir;
		assertEquals(expected, actual);

		expected = "Deleted file " + workingDir.getAbsolutePath() + pathSep
				+ "misc2" + pathSep + "tempFile.txt";
		actual = sh.runCmd("delete tempFile.txt");
		assertEquals(expected, actual);

		if (os == 1) {
			// For Mac
			expected = ".DS_Store";
		} else {
			// For Windows
			expected = "No files in working directory";
		}
		actual = sh.runCmd("ls");
		assertEquals(expected, actual);
	}

	@Test
	public void shellStateTest2() throws IOException {
		String expected = "Working dir changed to: ";
		String actual = "";
		String cmd = "cd " + homeDir + pathSep + "misc";

		actual = sh.runCmd(cmd);
		String newDir = sh.runCmd("pwd");

		expected += newDir;
		assertEquals(expected, actual);

		workingDir = new File(System.getProperty("user.dir"));
		expected = "Copied file tempFile2.txt to "
				+ workingDir.getAbsolutePath() + pathSep + "misc2";
		actual = sh.runCmd("copy tempFile2.txt misc2");
		assertEquals(expected, actual);

		expected = "Deleted file " + workingDir.getAbsolutePath() + pathSep
				+ "tempFile2.txt";
		actual = sh.runCmd("delete tempFile2.txt");
		assertEquals(expected, actual);

		expected = "Working dir changed to: ";
		actual = sh.runCmd("cd misc2");
		newDir = sh.runCmd("pwd");
		expected += newDir;
		assertEquals(expected, actual);

		workingDir = new File(System.getProperty("user.dir"));
		expected = "Deleted file " + workingDir.getAbsolutePath() + pathSep
				+ "tempFile2.txt";
		actual = sh.runCmd("delete tempFile2.txt");
		assertEquals(expected, actual);

		if (os == 1) {
			// For Mac
			expected = ".DS_Store";
		} else {
			// For Windows
			expected = "No files in working directory";
		}
		actual = sh.runCmd("ls");
		assertEquals(expected, actual);

		// change directory to homeDir
		expected = "Working dir changed to: ";
		cmd = "cd " + homeDir;
		actual = sh.runCmd(cmd);
		newDir = sh.runCmd("pwd");
		expected += newDir;
		assertEquals(expected, actual);
	}

}
