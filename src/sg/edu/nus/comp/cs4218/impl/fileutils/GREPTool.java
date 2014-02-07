package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class GREPTool extends ATool implements IGrepTool {
	
	private final String SPACE = " ";
	private final String EOL = "\n";
	private final String EMPTY = "";
	private final String DASH = "-";
	private final String COLON = ":";
	
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
	private int statusCode;
	
	public GREPTool() {
		super(null);
	}
	/*
	 * Execute: 
	 * Main method to process Grep Command
	 * Return: Error Msg is any or the corresponding Grep output
	 */
	@Override
	public String execute(File workingDir, String stdin) {
		
		int i, j=0;
		Scanner myScan = new Scanner(System.in);
		command = stdin.split(SPACE);
		
		if(isEmptyCommand(command))
		{
			errorMsg = "Invalid Grep Command: Use grep -help for command format\n";
			statusCode = 1;
			return errorMsg;
		}
		
		if(isHelpCommand(command))
			return getHelp();
		
		if(!isInvalidGrepCommand(command))
		{
			errorMsg = "Invalid Grep Command: Use grep -help for command format\n";
			statusCode = 1;
			return errorMsg;
		}
				
		initialiseGrepVariables();
				
		if(inputType==FILEINPUT)
		{
			fileNameStartIndex = getFileNameStartIndex(command);
			fileContent = new String[commandSize - fileNameStartIndex];
			for(i = fileNameStartIndex; i<commandSize; i++) {
				try {
					fileContent[j] = getFileContents(workingDir, command[i]);
				} catch (IOException e) {
					errorMsg = command[i]+COLON+SPACE+"No such files or directory\n";
					statusCode = 2;
					fileContent[j] = errorMsg;
				}
				j++;
			}
		}
		else
		{
			stdContent = getStdInputContents();
		}
		
		if(inputType == FILEINPUT )
		{
			for(i = 0; i<fileContent.length; i++) {
				if(!fileContent[i].contains("No such files or directory"))
					result = result + processContents(fileContent[i]);
				else
					result = result + fileContent[i];
			}
			
			return result;
		}
		else
		{
			/*testing for junit!! 
			*result = processContents(stdContent);
			*return result;
			*/
			while(true)
			{
				result = processContents(stdContent);
				System.out.print(result);
				stdContent = myScan.nextLine();
				if(!stdContent.equals("ctrl-z"))
					result = processContents(stdContent);
				else
					break;
			}
			result = EMPTY;
			return result;
		}
	}

	//Check if command is Empty
	private boolean isEmptyCommand(String[] command2) {
		if(command2.length<2)
			return true;
		
		return false;
	}

	//Check if the Command is a help command
	private boolean isHelpCommand(String[] command2) {
		
		if(command2.length == 2 && command2[1].equals("-help"))
			return true;
		else
			return false;
	}

	//Check if the Grep Command is valid
	private boolean isInvalidGrepCommand(String[] command2) {
		
		if(isOption(command2[1]))
		{
			if(command2.length < 3)
				return false;
			if(!isOptionNumValid(command2))
				return false;
		}
		
		return true;
	}

	//Check if the Option NUM is valid
	private boolean isOptionNumValid(String[] command2) {
		
		
		if(command2[1].equals("-A")||command2[1].equals("-B")||command2[1].equals("-C"))
		{
			if(command2.length < 4)
				return false;
			
			try {
				Integer.parseInt(command2[2]);
			} catch (Exception e)
			{
				return false;
			}
		}
	
		return true;
	}

	//Initialising the Grep Variables
	private void initialiseGrepVariables() {
		
		commandSize = command.length;
		inputType = checkForInputType(command);
		stdContent = EMPTY;
		result = EMPTY;
		statusCode = 0;
	}

	//Get the start index of input files
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

	//Check if the grep command has any option
	private boolean isOption(String command) {
		return command.equals("-A")| command.equals("-B") | command.equals("-C") |
				command.equals("-v")| command.equals("-o") | command.equals("-c");
	}
	
	//Get the Status code of grep
	@Override
	public int getStatusCode() {
		return statusCode;
	}

	//Get the number of matching Lines
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

	//Get the matchingLines
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

	//Get the matchingLines with Trailing context
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

	//Get the matching Lines with Leading Context
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

	//Get the matchingLines with output context
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

	//Get only the matching part
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

	//Get the non matching Lines
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

	//Get the help message
	@Override
	public String getHelp() {
		
		String helpMsg = "Command Format - grep [OPTIONS] PATTERN [FILE] PATTERN\n"+
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
		
		return helpMsg;
	}
	
	//Check if the input is STD input or file input
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
			}
			else
			{	
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

	//Get the contents from STD
	private String getStdInputContents() {
		
		String stdContent = mergeContent();
		
		StringBuilder stdBuilder = new StringBuilder(stdContent);
		stdBuilder = stdBuilder.deleteCharAt(0);//remove "-"
		stdContent = stdBuilder.toString();
		
		return stdContent;
	}

	//merge all the contents from STD as we have split the command for all SPACES
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

	//Using the pattern, options and content, process the relevant output  
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
	
	//Get the contents inside the files
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
	
	//Read from a file
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
	
	
}