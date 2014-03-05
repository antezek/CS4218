package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.extended2.IWcTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class WCTool extends ATool implements IWcTool {

	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_NOT_FOUND = "Error: file not found";
	private static final String MESSAGE_FILE_CONTENTS_EMPTY = "File is empty";
	private static final String MESSAGE_COMMAND_EMPTY = "Invalid WC Command: Use wc -help for command format\n";

	private String[] parts;
	
	public WCTool() {
		super(null);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		
		parts = stdin.split(Helper.REGEX_WHITE_SPACE);
		String result = "";
		String m=null,l=null,w=null;
		String readFile1 = "";
		String readFile2 = "";
		boolean optnul = false;
		boolean fileName1Null = true;
		boolean fileName2Null = true;
		if (isHelpCommand(parts))
			return getHelp();

		if (!isInvalidWCCommand(parts)) {
			setStatusCode(1);
			return MESSAGE_COMMAND_EMPTY; 
		}
		if (!isFileNameNull(parts)) {

			for (int i = 1; i < parts.length; i++) {
			
				if(parts[i].equalsIgnoreCase("-l") )l=parts[i].toString();
				if(parts[i].equalsIgnoreCase("-m") )m=parts[i].toString();
				if(parts[i].equalsIgnoreCase("-w") )w=parts[i].toString();					
				
				if(!parts[i].equalsIgnoreCase("-") && !parts[i].equalsIgnoreCase("-m") && !parts[i].equalsIgnoreCase("-w") && !parts[i].equalsIgnoreCase("-l"))
				{
						
						File toRead = Helper.isValidFile(workingDir, parts[i]);
				
						if (toRead != null) {
							if( readFile1 ==""&& fileName1Null==true){
								fileName1Null=false;
							readFile1 += getStringForFile(toRead);
								if(toRead.length()==0)readFile1="";	
								
							}
							else{
								fileName2Null=false;
							readFile2 += getStringForFile(toRead);
							if(toRead.length()==0)readFile2="";
							}
						}
						else{
							setStatusCode(1);
							return MESSAGE_FILE_NOT_FOUND;
						}
										
				}
				
			}
		}
		else {
			setStatusCode(1);
			return MESSAGE_FILE_NAME_NULL;
		}
		if(fileName1Null==true && fileName2Null==true)
		{
			setStatusCode(1);
			return MESSAGE_FILE_NAME_NULL;
		}
		
		if((m==null&&w==null&&l==null))
		{
			optnul= true;
			if(fileName1Null==false){
				result+="\nFile1:";
				result+= " chars= "+ getCharacterCount(readFile1);
				result+= " words= "+ getWordCount(readFile1);
				result+= " lines= "+ getNewLineCount(readFile1);	
			}
			if(fileName2Null==false){
				result+="\nFile2:";
				result+= " chars= "+ getCharacterCount(readFile2);
				result+= " words= "+ getWordCount(readFile2);
				result+= " lines= "+ getNewLineCount(readFile2);				
			}
		}
		
	
			if(fileName1Null==false && optnul == false){
				result+="\nFile1:";
				if(m!=null)
				{
					result+= " chars= "+ getCharacterCount(readFile1);
				}
				if(l!=null)
				{
					result+= " lines= "+ getNewLineCount(readFile1);
				}
				if(w!=null)
				{
					result+= " words= "+ getWordCount(readFile1);
				}
			}
			if(fileName2Null==false && optnul == false)
			{
				result+="\nFile2:";
				if(m!=null)
				{
					result+= " chars= "+ getCharacterCount(readFile2);
				}
				if(l!=null)
				{
					result+= " lines="+ getNewLineCount(readFile2);
				}
				if(w!=null)
				{
					result+= " words= "+ getWordCount(readFile2);
				}
			}
		 
			
		return result.trim();
	}

	@Override
	public int getStatusCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterCount(String input) {
		int count=0;
		
		
	    for (int i = 0; i < input.length(); i++) {
		     
	    		if( Character.isLetter(input.charAt(i)) && input.charAt(i)!=' ' &&  input.charAt(i)!='\n' ){
	    	        count++;
	    	    }
	    }
		return Integer.toString(count);
	}

	@Override
	public String getWordCount(String s) {
		 int wordCount = 0;
		
		    boolean word = false;
		    int endOfLine = s.length() - 1;

		    for (int i = 0; i < s.length(); i++) {
		        // if the char is a letter, word = true.
		        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
		            word = true;
		            // if char isn't a letter and there have been letters before,
		            // counter goes up.
		        } else if (!Character.isLetter(s.charAt(i)) && word) {
		            wordCount++;
		            word = false;
		            // last word of String; if it doesn't end with a non letter, it
		            // wouldn't count without this.
		        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
		            wordCount++;
		        }
		    }
		    
		    return Integer.toString(wordCount);
	}

	@Override
	public String getNewLineCount(String input) {
		   int lineLen,charcount;
		   charcount=Integer.parseInt(getCharacterCount(input));
		   String[] lines = input.split("\r\n|\r|\n");
		   lineLen=(lines.length);
		   if(charcount==0)
		   {
			   lineLen=0;
		   }
		   return  Integer.toString(lineLen);
	}

	@Override
	public String getHelp() {

		String helpMsg = "wc : Prints the number of bytes, words, and lines in given file\n"
				         + " Command Format - wc [OPTIONS] [FILE]\n"
				 + " FILE - Name of the file, when no file is present (denoted by '-') use standard input\n"
				 +" OPTIONS\n"
				 +"-m : Print only the character counts\n"
				 +"-w : Print only the word counts\n"
				 +"-l : Print only the newline counts\n"
				 +"-help : Brief information about supported options";

		return helpMsg;
	}
	
	private static boolean isFileNameNull(String[] fileName) {
		if (fileName.length < 2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getStringForFile(File toRead) {
		String displayString = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(toRead));
			String nextLine;

			while ((nextLine = br.readLine()) != null) {
				displayString += nextLine +"\n";
			}
			br.close();
			
			if (displayString.equalsIgnoreCase("")) {
				displayString = MESSAGE_FILE_CONTENTS_EMPTY;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return displayString.trim();
	}
	private boolean isHelpCommand(String[] command) {
		
		Boolean helpChk = false;
		int length;
        length= command.length;
        for (int i=1;i<length;i++){
			if (command[i].equalsIgnoreCase("-help"))
				    helpChk=true;
		}
        return helpChk;
	}
	private boolean isInvalidWCCommand(String[] command){
		Boolean cmdChk = true;
		int length;
		int optLen;
		length= command.length;
        for (int i=1;i<length;i++){
            optLen=command[i].length();
        	if (optLen==1 && command[i]!="-")
				    cmdChk=false;
        	}
        return cmdChk;
	}

        

}
