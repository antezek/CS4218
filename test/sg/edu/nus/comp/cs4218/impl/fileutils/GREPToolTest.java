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

import sg.edu.nus.comp.cs4218.impl.fileutils.GREPTool;

public class GREPToolTest {

	private final String PASSAGE = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
									"accessories, makeup, body piercing, or furniture.\n" +
									"Fashion refers to a distinctive and often habitual trend in the style\n" +
									"in which a person dresses or to prevailing styles in behaviour.\n" +
									"Fashion also refers to the newest creations of textile designers.\n" +
									"The more technical term costume has become so linked to its term\n" +
									"that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
									"while Fashion means clothing more generally, including the study of it.\n" +
									"Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
	
	private final String PASSAGESP = "Right from haha the start\n"+
									"You were a thief\n"+
									"You stole my heart\n"+
									"And I your haha willing victim\n"+
									"I let you see the parts of me\n"+
									"That weren't haha all that pretty\n"+
									"And with every touch you fixed them\n"+
									"Now you've been talking in your sleep, oh, oh\n"+
									"Things you never haha say to me, oh, oh\n"+
									"Tell me that you've had enough\n"+
									"Of our love, our love\n"+
									"Just give me a reason\n"+
									"Just a little bit's enough\n"+
									"Just a second we're not haha broken just bent\n"+
									"And we can learn to love again\n"+
									"It's in the stars\n"+
									"It's been written in the haha scars on our hearts\n"+
									"We're not broken just bent\n"+
									"And we can learn to love again\n";

	private final String PASSAGE2 = "Right from haha the start\n" +
									"You were a thief you stole my heart\n"+
									"And I your willing victim\n"+
									"I let you see the parts of me\n"+
									"That weren't all that pretty\n"+
									"And with every touch you fixed them\n"+
									"Now you've been talking in your sleep\n"+
									"Things you never haha say to me\n"+
									"Tell me that you've had enough\n"+
									"Of our love, our love\n"+
									"\n"+
									"Just give me a reason\n"+
									"Just a little bit's enough\n"+
									"Just a second we're not broken just bent\n"+
									"And we can haha learn to love again\n"+
									"It's in the stars\n"+
									"It's been written in the scars on our hearts\n"+
									"We're not broken just bent\n"+
									"And we can learn to love again\n"+
									"Im sorry I don't understand where all of this is coming from\n"+
									"I thought that we were fine\n"+
									"Oh we had everything haha\n"+
									"Your head is running wild again\n"+
									"My dear we still have everything\n"+
									"And its all in your mind\n"+
									"Yeah but this is happening\n"+
									"You've been having real bad dreams\n"+
									"Oh oh\n"+
									"You used to lie so close to me\n"+
									"Oh oh\n"+
									"There's nothing more than empty sheets\n"+
									"Between haha our love, our love, oh our love, our love\n";

	private final String PASSAGE3 ="Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
									"accessories, makeup, body piercing, or furniture.\n" +
									"Fashion refers to a distinctive and often habitual trend in the style\n" +
									"in which a person dresses or to prevailing styles in behaviour.\n" +
									"Fashion also refers to the newest creations of textile designers.\n" +
									"The more technical term costume has become so linked to its term\n" +
									"that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
									"while Fashion means clothing more generally, including the study of it.\n" +
									"Although aspects of fashion haha can be feminine or masculine, some trends are androgynous.\n";

	private GREPTool grepToolTest; 
	String actualOutput, expectedOutput, helpOutput;
	File workingDirectory;

	File fileA, fileB, fileC;
	String fileContentA, fileContentB, fileContentC;
	
	@Before
	public void before() throws Exception {
		
		workingDirectory = new File(System.getProperty("user.dir"));
		

		fileA = new File("PASSAGE.txt");
		fileB = new File("PASSAGE2.txt");
		fileC = new File("PASSAGE3.txt");
		
		fileContentA = PASSAGE;
		fileContentB = PASSAGE2;
		fileContentC = PASSAGE3;
		

		writeToFile(fileA, fileContentA);
		writeToFile(fileB, fileContentB);
		writeToFile(fileC, fileContentC);
		
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
	public void after(){
		
	}
    
	//Test Cases
	
	//SECTION ONE: Testing -c Option Method
	
	@Test
	//Normal Test Case 
	public void testGetCountOfMatchingLinesNormal() {
		
		grepToolTest = new GREPTool();
		int numOfMatchLines = grepToolTest.getCountOfMatchingLines("Fashion", PASSAGE);
		assertEquals(numOfMatchLines, 4);
		
		int numOfMatchLines2 = grepToolTest.getCountOfMatchingLines("general", PASSAGE);
		assertEquals(numOfMatchLines2, 2);
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetCountOfMatchingLinesEmpty() {
		
		grepToolTest = new GREPTool();
		int numOfMatchLines = grepToolTest.getCountOfMatchingLines("Fashion", "");
		assertEquals(numOfMatchLines,0);
	}
	
	//SECTION TWO: Testing No Option Method
		
	@Test
	//Normal Test Case
	public void testGetMatchingLinesNormal() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getOnlyMatchingLines("Fashion", PASSAGE);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						  "Fashion refers to a distinctive and often habitual trend in the style\n" +
						  "Fashion also refers to the newest creations of textile designers.\n" +
						  "while Fashion means clothing more generally, including the study of it.\n";
		
		assertEquals(matchLines, expected);
		
		String matchLines2 = grepToolTest.getOnlyMatchingLines("general",PASSAGE);
		String expected2 = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						   "while Fashion means clothing more generally, including the study of it.\n";
		
		assertEquals(matchLines2, expected2);
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetMatchingLinesEmpty() {
	
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getOnlyMatchingLines("Fashion", "");
		String expected = "";
		
		assertEquals(matchLines, expected);
	}
	
	//SECTION THREE: Testing -B Option Method
	
	@Test
	//Normal Test Case
	public void testGetMatchingLinesWithLeadingContextNormal() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesWithLeadingContext(1,"Fashion", PASSAGE);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						  "accessories, makeup, body piercing, or furniture.\n" +
						  "Fashion refers to a distinctive and often habitual trend in the style\n" +
						  "in which a person dresses or to prevailing styles in behaviour.\n" +
						  "Fashion also refers to the newest creations of textile designers.\n" +
						  "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						  "while Fashion means clothing more generally, including the study of it.\n";
		
		assertEquals(matchLines, expected);			
		
		String matchLines2 = grepToolTest.getMatchingLinesWithLeadingContext(2, "general", PASSAGE);
		String expected2 = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						   "The more technical term costume has become so linked to its term\n" +
						   "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						   "while Fashion means clothing more generally, including the study of it.\n";
		
		assertEquals(matchLines2,expected2);
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetMatchingLinesWithLeadingContextEmpty() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesWithLeadingContext(1,"Fashion", "");
		String expected ="";
		assertEquals(matchLines, expected);
		
	}
	
	//SECTION FOUR: Testing -A Option Method
	
	@Test
	//Normal Test Case
	public void testGetMatchingLinesWithTrailingContextNormal() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesWithTrailingContext(1,"Fashion", PASSAGE);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						  "accessories, makeup, body piercing, or furniture.\n" +
				          "Fashion refers to a distinctive and often habitual trend in the style\n" +
				          "in which a person dresses or to prevailing styles in behaviour.\n" +
				          "Fashion also refers to the newest creations of textile designers.\n" +
				          "The more technical term costume has become so linked to its term\n" +
				          "while Fashion means clothing more generally, including the study of it.\n" +
				          "Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
				
		assertEquals(matchLines, expected);		
		
		String matchLines2 = grepToolTest.getMatchingLinesWithTrailingContext(2, "general", PASSAGE);
		String expected2 =  "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
							"accessories, makeup, body piercing, or furniture.\n" +
							"Fashion refers to a distinctive and often habitual trend in the style\n" +
							"while Fashion means clothing more generally, including the study of it.\n" +
							"Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
		
		assertEquals(matchLines2, expected2);
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetMatchingLinesWithTrailingContextEmpty() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesWithTrailingContext(1,"Fashion", "");
		String expected = "";
		assertEquals(matchLines, expected);
		
	}
	
	//SECTION FIVE: Testing -o Option Method
	
	@Test
	//Normal Test Case
	public void testGetMatchingLinesOnlyMatchingPartNormal() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesOnlyMatchingPart("Fashion", PASSAGE);
		String expected = "Fashion\n" +
						  "Fashion\n" +
				          "Fashion\n" +
				          "Fashion\n";
				          
		assertEquals(matchLines, expected);	
		
		String matchLines2 = grepToolTest.getMatchingLinesOnlyMatchingPart("general", PASSAGE);
		String expected2 = "general\n"+
						   "general\n";
		
		assertEquals(matchLines2, expected2);
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetMatchingLinesOnlyMatchingPartEmpty() {
	
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesOnlyMatchingPart("Fashion", "");
		String expected = "";
		
		assertEquals(matchLines, expected);
		
	}
	
	//SECTION SIX: Testing -v Option Method
	
	@Test
	//Normal Test Case
	public void testGetNonMatchingLinesNormal() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getNonMatchingLines("Fashion", PASSAGE);
		String expected = "accessories, makeup, body piercing, or furniture.\n" +
				          "in which a person dresses or to prevailing styles in behaviour.\n" +
				          "The more technical term costume has become so linked to its term\n" +
				          "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
				          "Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
				          
		assertEquals(matchLines, expected);		
		
		String matchLines2 = grepToolTest.getNonMatchingLines("general", PASSAGE);
		String expected2 = "accessories, makeup, body piercing, or furniture.\n" +
						   "Fashion refers to a distinctive and often habitual trend in the style\n" +
						   "in which a person dresses or to prevailing styles in behaviour.\n" +
						   "Fashion also refers to the newest creations of textile designers.\n" +
						   "The more technical term costume has become so linked to its term\n" +
						   "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						   "Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
		
		assertEquals(matchLines2, expected2);
						  
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetNonMatchingLinesEmpty() {
		
		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getNonMatchingLines("Fashion", "");
		String expected = "";
		assertEquals(matchLines, expected);	
	}

	//SECTION SEVEN: Testing -C Option Method
	
	@Test
	//Normal Test Case
	public void testGetMatchingLinesWithOutputContextNormal() {
		
		grepToolTest = new GREPTool();
		
		String matchLines = grepToolTest.getMatchingLinesWithOutputContext(1, "haha", PASSAGESP);
		String expected = "Right from haha the start\n"+
						  "You were a thief\n"+
						  "You stole my heart\n"+
						  "And I your haha willing victim\n"+
						  "I let you see the parts of me\n"+
						  "That weren't haha all that pretty\n"+
						  "And with every touch you fixed them\n"+
						  "Now you've been talking in your sleep, oh, oh\n"+
						  "Things you never haha say to me, oh, oh\n"+
						  "Tell me that you've had enough\n"+
						  "Just a little bit's enough\n"+
						  "Just a second we're not haha broken just bent\n"+
						  "And we can learn to love again\n"+
						  "It's in the stars\n"+
						  "It's been written in the haha scars on our hearts\n"+
						  "We're not broken just bent\n";
		
		assertEquals(matchLines, expected);	
		
		String matchLines2 = grepToolTest.getMatchingLinesWithOutputContext(2, "general", PASSAGE);
		String expected2 = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						   "accessories, makeup, body piercing, or furniture.\n" +
						   "Fashion refers to a distinctive and often habitual trend in the style\n" +
						   "The more technical term costume has become so linked to its term\n" +
						   "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						   "while Fashion means clothing more generally, including the study of it.\n" +
						   "Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
		
		assertEquals(matchLines2, expected2);
	}
	
	@Test
	//Corner Test Case with Empty Passage
	public void testGetMatchingLinesWithOutputContextEmpty() {

		grepToolTest = new GREPTool();
		String matchLines = grepToolTest.getMatchingLinesWithOutputContext(1, "haha", "");
		String expected ="";
		
		assertEquals(matchLines, expected);	
	}

	//SECTION EIGHT: Testing Execute Method For File Input
	
	@Test
	//Testing Execute -A option with Single File Input
	public void testExecuteForSingleFileContentsAOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		File wrkDir = new File(curDir);
		
		String stdin = "grep -A 1 Fashion PASSAGE.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
				  		   "accessories, makeup, body piercing, or furniture.\n" +
		                   "Fashion refers to a distinctive and often habitual trend in the style\n" +
		                   "in which a person dresses or to prevailing styles in behaviour.\n" +
		                   "Fashion also refers to the newest creations of textile designers.\n" +
		                   "The more technical term costume has become so linked to its term\n" +
		                   "while Fashion means clothing more generally, including the study of it.\n" +
		                   "Although aspects of fashion can be feminine or masculine, some trends are androgynous.";
				
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Testing Execute -B option with Single File Input
	public void testExecuteForSingleFileContentsBOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -B 1 Fashion PASSAGE.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected ="Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						 "accessories, makeup, body piercing, or furniture.\n" +
						 "Fashion refers to a distinctive and often habitual trend in the style\n" +
						 "in which a person dresses or to prevailing styles in behaviour.\n" +
						 "Fashion also refers to the newest creations of textile designers.\n" +
						 "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						 "while Fashion means clothing more generally, including the study of it.";
								
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Testing Execute -C option with Single File Input
	public void testExecuteForSingleFileContentCOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C 2 haha PASSAGE2.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "Right from haha the start\n"+
				  		  "You were a thief you stole my heart\n"+
				  		  "And I your willing victim\n"+
				  		  "And with every touch you fixed them\n"+
				  		  "Now you've been talking in your sleep\n"+
				  		  "Things you never haha say to me\n"+
				  		  "Tell me that you've had enough\n"+
				  		  "Of our love, our love\n"+
				  		  "Just a little bit's enough\n"+
				  		  "Just a second we're not broken just bent\n"+
				  		  "And we can haha learn to love again\n"+
				  		  "It's in the stars\n"+
				  		  "It's been written in the scars on our hearts\n"+
				  		  "Im sorry I don't understand where all of this is coming from\n"+
				  		  "I thought that we were fine\n"+
				  		  "Oh we had everything haha\n"+
				  		  "Your head is running wild again\n"+
				  		  "My dear we still have everything\n"+
				  		  "Oh oh\n"+
				  		  "There's nothing more than empty sheets\n"+
				  		  "Between haha our love, our love, oh our love, our love";
		
		assertEquals(matchLines, expected);	
		
	}

	@Test
	//Test Execute -c Option with Single File Input
	public void testExecuteForSingleFileContentcOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -c haha PASSAGE2.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines, "5");	
	}
	
	@Test
	//Test Execute -o Option with Single File Input
	public void testExecuteForSingleFileContentoOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -o haha PASSAGE2.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "haha\n"+"haha\n"+"haha\n"+"haha\n"+"haha";
		
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Test Execute -v Option with Single File Input
	public void testExecuteForSingleFileContentvOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		File wrkDir = new File(curDir);
		
		String stdin = "grep -v Fashion PASSAGE.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "accessories, makeup, body piercing, or furniture.\n" +
				    	  "in which a person dresses or to prevailing styles in behaviour.\n" +
				    	  "The more technical term costume has become so linked to its term\n" +
				    	  "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
			              "Although aspects of fashion can be feminine or masculine, some trends are androgynous.";
		
		assertEquals(matchLines, expected);	
	}

	@Test
	//Test Execute No Option with Single File Input
	public void testExecuteForSingleFileContentNoOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		File wrkDir = new File(curDir);
		
		String stdin = "grep Fashion PASSAGE.txt";
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
				          "Fashion refers to a distinctive and often habitual trend in the style\n" +
				          "Fashion also refers to the newest creations of textile designers.\n" +
				          "while Fashion means clothing more generally, including the study of it.";
		
		assertEquals(matchLines, expected);	
	}
	
	//SECTION NINE: Testing Execute Method For Multiple Input
	
	@Test
	//Testing Execute with multiple VALID files 
	public void testExecuteForMulFileContentWithAllValidFiles() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C 2 haha PASSAGE2.txt PASSAGE3.txt";
		String expected = "Right from haha the start\n"+
						  "You were a thief you stole my heart\n"+
						  "And I your willing victim\n"+
						  "And with every touch you fixed them\n"+
						  "Now you've been talking in your sleep\n"+
						  "Things you never haha say to me\n"+
						  "Tell me that you've had enough\n"+
						  "Of our love, our love\n"+
						  "Just a little bit's enough\n"+
						  "Just a second we're not broken just bent\n"+
						  "And we can haha learn to love again\n"+
						  "It's in the stars\n"+
						  "It's been written in the scars on our hearts\n"+
						  "Im sorry I don't understand where all of this is coming from\n"+
						  "I thought that we were fine\n"+
						  "Oh we had everything haha\n"+
						  "Your head is running wild again\n"+
						  "My dear we still have everything\n"+
						  "Oh oh\n"+
						  "There's nothing more than empty sheets\n"+
						  "Between haha our love, our love, oh our love, our love\n"+
						  "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n"+
						  "while Fashion means clothing more generally, including the study of it.\n"+
						  "Although aspects of fashion haha can be feminine or masculine, some trends are androgynous.";
				
		String matchLine = grepToolTest.execute(wrkDir, stdin);
		assertEquals(matchLine,expected);
	}
	
	@Test
	//Testing Execute with multiple file with one of more invalid files
	public void testExecuteForMulFileContentWithSomeValidFiles() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C 2 haha PASSAGE2.txt LOST.txt";
		String matchLine = grepToolTest.execute(wrkDir, stdin);
		String expected = "Right from haha the start\n"+
				  		   "You were a thief you stole my heart\n"+
				  		   "And I your willing victim\n"+
				  		   "And with every touch you fixed them\n"+
				  		   "Now you've been talking in your sleep\n"+
				  		   "Things you never haha say to me\n"+
				  		   "Tell me that you've had enough\n"+
				  		   "Of our love, our love\n"+
				  		   "Just a little bit's enough\n"+
				  		   "Just a second we're not broken just bent\n"+
				  		   "And we can haha learn to love again\n"+
				  		   "It's in the stars\n"+
				  		   "It's been written in the scars on our hearts\n"+
				  		   "Im sorry I don't understand where all of this is coming from\n"+
				  		   "I thought that we were fine\n"+
				  		   "Oh we had everything haha\n"+
				  		   "Your head is running wild again\n"+
				  		   "My dear we still have everything\n"+
				  		   "Oh oh\n"+
				  		   "There's nothing more than empty sheets\n"+
				  		   "Between haha our love, our love, oh our love, our love\n"+
				  		   "LOST.txt:\n"+
				  		   "No such files or directory";
		
	
		assertEquals(matchLine,expected);
		
	}

	//SECTION TEN: Testing Execute Method For Standard Input
	
	@Test
	//Testing Execute Method with STD Input -A Option
	public void textExecuteForSTDAOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		File wrkDir = new File(curDir);
		
		String stdin = "grep -A 1 Fashion "+"-"+PASSAGE;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
				  		   "accessories, makeup, body piercing, or furniture.\n" +
		                   "Fashion refers to a distinctive and often habitual trend in the style\n" +
		                   "in which a person dresses or to prevailing styles in behaviour.\n" +
		                   "Fashion also refers to the newest creations of textile designers.\n" +
		                   "The more technical term costume has become so linked to its term\n" +
		                   "while Fashion means clothing more generally, including the study of it.\n" +
		                   "Although aspects of fashion can be feminine or masculine, some trends are androgynous.";
				
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Testing Execute Method with STD Input -B Option
	public void testExecuteForSTDBOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -B 1 Fashion "+"-"+ PASSAGE;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected ="Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						 "accessories, makeup, body piercing, or furniture.\n" +
						 "Fashion refers to a distinctive and often habitual trend in the style\n" +
						 "in which a person dresses or to prevailing styles in behaviour.\n" +
						 "Fashion also refers to the newest creations of textile designers.\n" +
						 "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						 "while Fashion means clothing more generally, including the study of it.";
								
		assertEquals(matchLines, expected);
	}
	
	@Test
	//Testing Execute Method with STD Input -C Option
	public void testExecuteForSTDCOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C 2 haha "+"-"+PASSAGE2;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "Right from haha the start\n"+
				  		  "You were a thief you stole my heart\n"+
				  		  "And I your willing victim\n"+
				  		  "And with every touch you fixed them\n"+
				  		  "Now you've been talking in your sleep\n"+
				  		  "Things you never haha say to me\n"+
				  		  "Tell me that you've had enough\n"+
				  		  "Of our love, our love\n"+
				  		  "Just a little bit's enough\n"+
				  		  "Just a second we're not broken just bent\n"+
				  		  "And we can haha learn to love again\n"+
				  		  "It's in the stars\n"+
				  		  "It's been written in the scars on our hearts\n"+
				  		  "Im sorry I don't understand where all of this is coming from\n"+
				  		  "I thought that we were fine\n"+
				  		  "Oh we had everything haha\n"+
				  		  "Your head is running wild again\n"+
				  		  "My dear we still have everything\n"+
				  		  "Oh oh\n"+
				  		  "There's nothing more than empty sheets\n"+
				  		  "Between haha our love, our love, oh our love, our love";
		
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Testing Execute Method with STD Input -c Option
	public void testExecuteForSTDcOption() {
	
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -c haha "+"-"+ PASSAGE2;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines, "5");	
		
	}
	
	@Test
	//Testing Execute Method with STD Input -o Option
	public void testExecuteForSTDoOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -o haha "+"-"+ PASSAGE2;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "haha\n"+"haha\n"+"haha\n"+"haha\n"+"haha";
		
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Testing Execute Method with STD Input -v Option
	public void testExecuteForSTDvOption() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		File wrkDir = new File(curDir);
		
		String stdin = "grep -v Fashion "+"-"+PASSAGE;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "accessories, makeup, body piercing, or furniture.\n" +
				    	  "in which a person dresses or to prevailing styles in behaviour.\n" +
				    	  "The more technical term costume has become so linked to its term\n" +
				    	  "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
			              "Although aspects of fashion can be feminine or masculine, some trends are androgynous.";
		
		assertEquals(matchLines, expected);	
	}
	
	@Test
	//Testing Execute Method with STD Input No Option
	public void testExecuteForSTDNoOption() {
	
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		File wrkDir = new File(curDir);
		
		String stdin = "grep Fashion "+"-"+ PASSAGE;
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
				          "Fashion refers to a distinctive and often habitual trend in the style\n" +
				          "Fashion also refers to the newest creations of textile designers.\n" +
				          "while Fashion means clothing more generally, including the study of it.";
		
		assertEquals(matchLines, expected);	
	}
	
	//SECTION ELEVEN: Miscellaneous Testing
	
	@Test
	//Testing Invalid Input for invalid Number for -A,-B,-C Option
	public void testInvalidInputWrongNumIndicated() {

		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C yoyo haha";
		String expected = "Invalid Grep Command: Use grep -help for command format\n";
		
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines,expected);
	}
	
	@Test
	//Test for empty grep command
	public void testForEmptyGrepCommand() {
		
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		
		String command = "grep";
		String actual = grepToolTest.execute(wrkDir, command);
		String expected = "Invalid Grep Command: Use grep -help for command format\n";
		
		assertEquals(expected,actual);
		assertEquals(grepToolTest.getStatusCode(), 1);
		
	}
	
	@Test
	//Testing Invalid Input for not Enough input for -o, -v, -c type of option
	public void testInvalidInputNotEnoughInputOVC() {

		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -c";
		String expected = "Invalid Grep Command: Use grep -help for command format\n";
		
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines,expected);
	}
	
	@Test
	//Testing Invalid Input for not Enough input for -A, -B, -C type of option
	public void testInvalidInputNotEnoughInputABC() {

		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C 1";
		String expected = "Invalid Grep Command: Use grep -help for command format\n";
		
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines,expected);
	}
		
	@Test
	//Testing help command
	public void testHelp() {
	
		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -help";
		String expected = "Command Format - grep [OPTIONS] PATTERN [FILE] PATTERN\n"+
						   "This specifies a regular expression pattern that describes a set of strings\n"+
						   "FILE - Name of the file, when no file is present (denoted by -) use standard input\n"+
						   "OPTIONS\n"+
						   "-A NUM : Print NUM lines of trailing context after matching lines\n"+
						   "-B NUM : Print NUM lines of leading context before matching lines " +
						   "-C NUM : Print NUM lines of output context\n" +
						   "-c : Suppress normal output. Instead print a count of matching lines for each input file\n" +
						   "-o : Show only the part of a matching line that matches PATTERN\n" +
						   "-v : Select non-matching (instead of matching) lines\n" +
						   "-help : Brief information about supported options\n"+
						   "Examples:\n"+ 
						   "grep -A 3 haha hahaha.txt\n"+
						   "grep -o hoho Pokemon.txt\n";
		
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines,expected);	
	}
	
	@Test
	//Testing get file funtion with file with path
	public void testGetFileInputWithFileNameWithPath(){
		

		grepToolTest = new GREPTool();
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
		String filePath = curDir +"\\"+"PASSAGE.txt";
		
		File wrkDir = new File(curDir);
		try {
			String fileContents = grepToolTest.getFileContents(wrkDir, filePath);
			String expected = PASSAGE;
			
			expected = expected.substring(0,expected.length()-1);
			
			assertEquals(fileContents,expected);
			
		} catch (Exception e) {
			return;
		}
	}
	
}
