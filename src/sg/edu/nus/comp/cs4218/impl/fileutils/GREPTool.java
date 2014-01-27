package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;

public class GREPTool implements IGrepTool {
	
	private final String SPACE = " ";
	private final String EOL = "\n";
	private final String EMPTY = "";
	
	private String[] command;
	private String fileContent;
	private int numOfMatch;
	private String output;
	private String errorMsg;

	
	@Override
	public String execute(File workingDir, String stdin) {
		
		command = stdin.split(SPACE);
				
		//check for invalid command
		//Skip for now
		
		try {
						
			fileContent = getFileContents(workingDir, command[command.length - 1]);
		
		} catch (IOException e) {
			
			errorMsg = "Invalid File Name or Path";
			return errorMsg;
		}
		
		if(command[1].equals("-A")) 
			output = getMatchingLinesWithTrailingContext(Integer.parseInt(command[2]), command[3], fileContent);
		else if(command[1].equals("-B"))
			output = getMatchingLinesWithLeadingContext(Integer.parseInt(command[2]), command[3], fileContent);
		else if(command[1].equals("-C"))
		{
			//not implemented yet	
		}
		else if(command[1].equals("-c"))
		{
			numOfMatch = getCountOfMatchingLines(command[2],fileContent);
			output = Integer.toString(numOfMatch);
		}
		else if(command[1].equals("-o"))
			output = getMatchingLinesOnlyMatchingPart(command[2],fileContent);
		else if(command[1].equals("v"))
			output = getNonMatchingLines(command[2],fileContent);
		
		else
			output = getOnlyMatchingLines(command[1], fileContent);
		
		return output;
	}
	
	@Override
	public int getStatusCode() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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