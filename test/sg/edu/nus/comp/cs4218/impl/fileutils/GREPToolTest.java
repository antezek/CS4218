package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;
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
	
	private final String PASSAGE2 = "Right from haha the start\n"+
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

	private GREPTool grepToolTest; 
	
	@Before
	public void before(){
		grepToolTest = new GREPTool();
	}

    @After
	public void after(){
		grepToolTest = null;
	}
	
	@Test
	public void testGetCountOfMatchingLines() {
		
		int numOfMatchLines = grepToolTest.getCountOfMatchingLines("Fashion", PASSAGE);
		assertEquals(numOfMatchLines, 4);
		
		int numOfMatchLines2 = grepToolTest.getCountOfMatchingLines("general", PASSAGE);
		assertEquals(numOfMatchLines2, 2);
	}
	
	@Test
	public void testGetMatchingLines() {
		
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
	public void testGetMatchingLinesWithLeadingContext() {
		
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
	public void testGetMatchingLinesWithTrailingContext() {
		
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
	public void testGetMatchingLinesOnlyMatchingPart() {
		
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
	public void testGetNonMatchingLines() {
		
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
	public void testGetMatchingLinesWithOutputContext() {
		
		String matchLines = grepToolTest.getMatchingLinesWithOutputContext(1, "haha", PASSAGE2);
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
	public void testExecuteForFileContents() {
		
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin1 = "grep -A 1 Fashion PASSAGE.txt";
		String stdin2 = "grep -B 1 Fashion LOST.txt";
		
		String matchLines1 = grepToolTest.execute(wrkDir, stdin1);
		String matchLines2 = grepToolTest.execute(wrkDir, stdin2);
		
		String expected1 = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
				  		   "accessories, makeup, body piercing, or furniture.\n" +
		                   "Fashion refers to a distinctive and often habitual trend in the style\n" +
		                   "in which a person dresses or to prevailing styles in behaviour.\n" +
		                   "Fashion also refers to the newest creations of textile designers.\n" +
		                   "The more technical term costume has become so linked to its term\n" +
		                   "while Fashion means clothing more generally, including the study of it.\n" +
		                   "Although aspects of fashion can be feminine or masculine, some trends are androgynous.\n";
		
		String expected2 = "LOST.txt:\n"+
						   "No such files or directory\n";
		
		assertEquals(matchLines1, expected1);	
		assertEquals(matchLines2, expected2);	
	}
	
	@Test
	public void testForExecuteForMultipleFiles() {
		
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
						  "Although aspects of fashion haha can be feminine or masculine, some trends are androgynous.\n";
				
		String matchLine = grepToolTest.execute(wrkDir, stdin);
		assertEquals(matchLine,expected);
		
		String stdin2 = "grep -C 2 haha PASSAGE2.txt LOST.txt";
		String expected2 = "Right from haha the start\n"+
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
				  		   "No such files or directory\n";
		
		String matchLine2 = grepToolTest.execute(wrkDir, stdin2);
		assertEquals(matchLine2,expected2);

	}
	
	
	//Comment off this section when testing for STD Output
	/*
	@Test
	public void testExecuteForSTDInput() {
		
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
				
		String stdin = "grep -C 1 haha -"+
					   "Right from haha the start\n"+
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
		
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		assertEquals(matchLines, expected);
	}
	*/
	
	@Test
	public void testInvalidInputAndHelpCommand() {

		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();
				
		File wrkDir = new File(curDir);
		String stdin = "grep -C yoyo haha";
		String expected = "Invalid Grep Command: Use grep -help for command format\n";
		
		String matchLines = grepToolTest.execute(wrkDir, stdin);
		
		assertEquals(matchLines,expected);
		
		String stdin2 = "grep -help";
		String expected2 = "Command Format - grep [OPTIONS] PATTERN [FILE] PATTERN\n"+
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
		
		String matchLines2 = grepToolTest.execute(wrkDir, stdin2);
		
		assertEquals(matchLines2,expected2);		
		
	}
	
	
	
	

}
