package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;

import java.io.File;

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
	}
	
	@Test
	public void testGetMatchingLines() {
		
		String matchLines = grepToolTest.getOnlyMatchingLines("Fashion", PASSAGE);
		String expected = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						  "Fashion refers to a distinctive and often habitual trend in the style\n" +
						  "Fashion also refers to the newest creations of textile designers.\n" +
						  "while Fashion means clothing more generally, including the study of it.\n";
		
		assertEquals(matchLines, expected);				  
						  
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
						  
	}
	
	@Test
	public void testGetMatchingLinesOnlyMatchingPart() {
		
		String matchLines = grepToolTest.getMatchingLinesOnlyMatchingPart("Fashion", PASSAGE);
		String expected = "Fashion\n" +
						  "Fashion\n" +
				          "Fashion\n" +
				          "Fashion\n";
				          
		assertEquals(matchLines, expected);				  
						  
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
					      
	}
	
	@Test
	public void testExecuteForFileContents() {
		
		File wrkDir = new File("C:\\Users\\Avan\\Shell");
		String stdin1 = "grep -A 1 Fashion PASSAGE.txt";
		String stdin2 = "grep -B 1 Fashion C:\\Users\\Avan\\Documents\\PASSAGE.txt";
		
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
		
		String expected2 = "Fashion is a general term for a popular style or practice, especially in clothing, footwear,\n" +
						   "accessories, makeup, body piercing, or furniture.\n" +
						   "Fashion refers to a distinctive and often habitual trend in the style\n" +
						   "in which a person dresses or to prevailing styles in behaviour.\n" +
						   "Fashion also refers to the newest creations of textile designers.\n" +
						   "that the use of the former has been relegated to special senses like fancy dress or masquerade wear,\n" +
						   "while Fashion means clothing more generally, including the study of it.\n";
		
		assertEquals(matchLines1, expected1);	
		assertEquals(matchLines2, expected2);	
	}
	
	@Test
	public void testExecuteForSTDInput() {
		File wrkDir = new File("C:\\Users\\Avan\\Shell");
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
	
	
	
	

}
