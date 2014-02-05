package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;

public class GREPTool implements IGrepTool {
	
	private final String SPACE = " ";
	private final String EOL = "\n";
	private final String EMPTY = "";
	private final String DASH = "-";
	
	private final int STDINPUT = 1;
	private final int FILEINPUT = 2;
	
	private String[] command;
	private int commandSize;
	private String stdContent;
	private String[] fileContent;
	private int numOfMatch;
	private String result;
	private String errorMsg;
	private int inputType;
	private int contentStartIndex;
	private int fileNameStartIndex;

	
	@Override
	public String execute(File workingDir, String stdin) {
		
		int i, j=0;
		Scanner myScan = new Scanner(System.in);
		command = stdin.split(SPACE);
				
		//check for invalid command
		//Skip for now
		
		commandSize = command.length;
		inputType = checkForInputType(command);
		stdContent = EMPTY;
		result = EMPTY;
		
		
		try {
			if(inputType==FILEINPUT)
			{
				inputType = FILEINPUT;
				fileNameStartIndex = getFileNameStartIndex(command);
				fileContent = new String[commandSize - fileNameStartIndex];
				for(i = fileNameStartIndex; i<commandSize; i++) {
					fileContent[j] = getFileContents(workingDir, command[i]);
					j++;
				}
			}
			else
			{
				inputType = STDINPUT;
				stdContent = getStdInputContents();
			}
		} catch (IOException e) {
			
			errorMsg = "Invalid File Name or Path";
			return errorMsg;
		}
		
		if(inputType == FILEINPUT )
		{
			for(i = 0; i<fileContent.length; i++) {
				result = result + processContents(fileContent[i]);
			}
			
			return result;
		}
		else
		{
			//testing for junit!! 
			//remove this line and comment the while loop when doing thread testing.
			result = processContents(stdContent);
			return result;
			/*
			while(true)
			{
				
				System.out.print(output);
				content = myScan.next();
				result = processContents(stdContent);
			}
			*/
		}
		
			
	}

	private int getFileNameStartIndex(String[] command) {
		
		int fileIndex = -1; 
	
		if(isOption(command[1]))
		{
			if(command[1].equals("-A")||command[1].equals("-B")||command[1].equals("-C"))
				fileIndex = 4;
			else
				fileIndex = 3;
		}
		else
			fileIndex = 2;
			
		return fileIndex;
	}

	private boolean isOption(String command) {
		return command.equals("-A")| command.equals("-B") | command.equals("-C") |
				command.equals("-v")| command.equals("-o") | command.equals("-c");
	}
	@Override
	public int getStatusCode() {
		return 0;
	}

	@Override
	public int getCountOfMatchingLines(String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i;
		int matchCount = 0;
		
		for(i = 0; i < size; i++) 
		{
			if(inputLine[i].contains(pattern))
			{
				matchCount ++;
			}
		}
			
		return matchCount;
	}

	@Override
	public String getOnlyMatchingLines(String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i;
		String matchLine = EMPTY;
		
		for(i = 0; i < size; i++) 
		{
			if(inputLine[i].contains(pattern))
			{
				matchLine = matchLine + inputLine[i]+EOL;
			}
		}
				
		return matchLine;
	}

	@Override
	public String getMatchingLinesWithTrailingContext(int option_A,
			String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i,j;
		String matchLine = EMPTY;
		
		for(i = 0; i < size; i++) 
		{
			if(inputLine[i].contains(pattern))
			{
				for(j = i; j <= i + option_A; j++)
				{
					if(j<size)
						matchLine = matchLine + inputLine[j] + EOL;
				}
				
				i = j - 1;
			}
		}

		return matchLine;
		
	}

	@Override
	public String getMatchingLinesWithLeadingContext(int option_B,
			String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i,j; int linePtr = -1;
		String matchLine = EMPTY;
		
		for(i = 0; i < size; i++) 
		{
			if(inputLine[i].contains(pattern))
			{
				for(j = i - option_B ; j <= i; j++)
				{
					if(j > linePtr)
						matchLine = matchLine + inputLine[j]+EOL;
				}	
				
				linePtr = i;
			}
		}

		return matchLine;
				
	}

	@Override
	public String getMatchingLinesWithOutputContext(int option_C,
			String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i,j; int linePtr = -1;
		String matchLine = EMPTY;
		
		for(i = 0; i < size; i++) 
		{
			if(inputLine[i].contains(pattern))
			{
				for(j = i - option_C ; j <= i+ option_C; j++)
				{
					if(j > linePtr && j<size)
						matchLine = matchLine + inputLine[j]+EOL;
				}	
				
				linePtr = i + option_C;
				i = j - 1;
			}
		}

		return matchLine;

	}

	@Override
	public String getMatchingLinesOnlyMatchingPart(String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i;
		String matchLine = EMPTY;
		
		for(i = 0; i < size; i++) 
		{
			if(inputLine[i].contains(pattern))
			{
				matchLine = matchLine + pattern + EOL;
			}
		}
				
		return matchLine;
	}

	@Override
	public String getNonMatchingLines(String pattern, String input) {
		
		String []inputLine = input.split(EOL);
		int size = inputLine.length;
		int i;
		String matchLine = EMPTY;
		
		for(i = 0; i < size; i++) 
		{
			if(!inputLine[i].contains(pattern))
			{
				matchLine = matchLine + inputLine[i] + EOL;
			}
		}
				
		return matchLine;
	}

	@Override
	public String getHelp() {
		
		return null;
	}
	
	private int checkForInputType(String[] command2) {
		
		if(isOption(command2[1]))
		{
			if(command2[1].equals("-A")||command2[1].equals("-B")||command2[1].equals("-C"))
			{
				if(command2[4].startsWith(DASH))
				{
					contentStartIndex = 4;
					return STDINPUT;
				}
			
				if(command2[3].startsWith(DASH))
				{
					contentStartIndex = 3;
					return STDINPUT;
				}
			}
		}
		else
		{
			if(command2[2].startsWith(DASH))
			{
				contentStartIndex = 2;
				return STDINPUT;
			}
			else
				return FILEINPUT;
		}
		
		return FILEINPUT;
	}

	private String getStdInputContents() {
		
		String stdContent = mergeContent();
		
		StringBuilder stdBuilder = new StringBuilder(stdContent);
		stdBuilder = stdBuilder.deleteCharAt(0);//remove "-"
		stdContent = stdBuilder.toString();
		
		return stdContent;
	}

	private String mergeContent() {
		
		int i;
		String stdContent = EMPTY;
		for(i = contentStartIndex; i<commandSize; i++)
		{
			stdContent = stdContent + command[i];
			if(i!=commandSize-1)
				stdContent = stdContent + SPACE;
		}
		
		return stdContent;
	}

	private String processContents(String content) {
		
		String output = EMPTY;
		if(command[1].equals("-A")) 
			output = getMatchingLinesWithTrailingContext(Integer.parseInt(command[2]), command[3], content);
		else if(command[1].equals("-B"))
			output = getMatchingLinesWithLeadingContext(Integer.parseInt(command[2]), command[3], content);
		else if(command[1].equals("-C"))
			output = getMatchingLinesWithOutputContext(Integer.parseInt(command[2]), command[3], content);
		else if(command[1].equals("-c"))
		{
			numOfMatch = getCountOfMatchingLines(command[2],content);
			output = Integer.toString(numOfMatch);
		}
		else if(command[1].equals("-o"))
			output = getMatchingLinesOnlyMatchingPart(command[2],content);
		else if(command[1].equals("v"))
			output = getNonMatchingLines(command[2],content);
		
		else
			output = getOnlyMatchingLines(command[1], content);
		
		return output;
	}
	
	
	private String getFileContents (File workdir, String fileName) throws IOException {
		
		String path; 
						
		if(!fileName.contains("\\"))
			//it is a fileName, thus it means the file resides in the same directory as the working directory
			path = workdir.getAbsolutePath() + "\\" + fileName;
		else
			path = fileName;
		
		File file = new File(path);
		
		return readFile(file);
	}
	
	private String readFile(File file) throws IOException {
		
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    
	    try {
	    
	    	StringBuilder fileContents = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            fileContents.append(line);
	            fileContents.append("\n");
	            line = br.readLine();
	        }
	        
	        return fileContents.toString();
	    } finally {
	        br.close();
	    }
	}
	
	/*
	//Helper Methods
	private String[] splitCommand(String command) {
		return command.split(SPACE);
	}
	
	private boolean isCommandValid(String[] command) {
		if(checkCommandLength(command) 
		
		return true;
	}

	private boolean checkCommandLength(String[] command) {
		if(command.length!=4) {
			errorMsg = "Command Length is Invalid";
			return false;
		}
		
		return true;
	}
	
	private boolean checkCommandOption(String commandOption) {
		
	
		
	}
	*/
}