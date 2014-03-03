package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import sg.edu.nus.comp.cs4218.extended2.ISortTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class SORTTool extends ATool implements ISortTool {

	private static final String MESSAGE_FILE_NAME_NULL = "Error: file name null";
	private static final String MESSAGE_FILE_NOT_FOUND = "Error: file not found";
	private static final String MESSAGE_FILE_CONTENTS_EMPTY = "File is empty";
	private static final String MESSAGE_COMMAND_EMPTY = "Invalid SORT Command: Use wc -help for command format\n";
	private String[] parts;
	public SORTTool() {
		super(null);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		parts = stdin.split(Helper.REGEX_WHITE_SPACE);
		String result = "";		
		String readFile1 = "";		
		String readFile2 = "";
		String file1Name="";
		String file2Name="";
		
		boolean fileName1Null = true;
		boolean fileName2Null = true;
		boolean fileSortCheck = false;
		if (isHelpCommand(parts))
			return getHelp();

		if (!isInvalidSortCommand(parts)) {
			setStatusCode(1);
			return MESSAGE_COMMAND_EMPTY; 
		}
		
		if (!isFileNameNull(parts)) {

			for (int i = 1; i < parts.length; i++) {
				
				if(parts[i].equalsIgnoreCase("-c") )fileSortCheck=true;
				if(!parts[i].equalsIgnoreCase("-") && !parts[i].equalsIgnoreCase("-c"))
				{
						File toRead = Helper.isValidFile(workingDir, parts[i]);
						if (toRead != null) {
							if( readFile1 ==""){
								fileName1Null=false;
								file1Name=parts[i].toString();
								readFile1 += getStringForFile(toRead);}
							else{
								fileName2Null=false;
								file2Name=parts[i].toString();	
							readFile2 += getStringForFile(toRead);}
						}
						else {
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
		if(fileName1Null==true && fileName2Null==true)return MESSAGE_FILE_NAME_NULL;
		if(fileSortCheck==true)
		{String chkfile1=null;
		String chkfile2=null;
			if(readFile1!=""){
				chkfile1=checkIfSorted(readFile1);
				if(chkfile1=="notsorted"){
				result+="\nFile1: not sorted";
				}
				else{result+="\nFile1: Already sorted"; }
				}
			if(readFile2!=""){
				chkfile2=checkIfSorted(readFile2);
				if(chkfile2=="notsorted"){
					result+="\nFile2: not sorted";
					}
					else{result+="\nFile2: Already sorted"; }
				}
		}
		else{
		String chkfile1=null;
		String chkfile2=null;
		if(readFile1!=""){
			chkfile1=checkIfSorted(readFile1);
			if(chkfile1=="notsorted"){
				String sortresult1=sortFile(readFile1);
				try{
				FileWriter fstream = new FileWriter(file1Name);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(sortresult1);
				out.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			result+="\nFile1:\n"+sortresult1;
			}
			else{result+="\nFile1: Already sorted"; }
			}
		if(readFile2!=""){
			chkfile2=checkIfSorted(readFile2);
			if(chkfile2=="notsorted"){
				String sortresult1=sortFile(readFile2);
				try{
				FileWriter fstream = new FileWriter(file2Name);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(sortresult1);
				out.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			result+="\nFile2:\n"+sortresult1;
				}
				else{result+="\nFile2: Already sorted"; }
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
	public String sortFile(String input) {
		String[] lines = input.split("\r\n|\r|\n");
		String sortedStr= "";
		Arrays.sort(lines);
        for(int i = 0; i < lines.length; i++) {
        	sortedStr+=lines[i].toString()+"\n";
        }
		return sortedStr;
	}

	@Override
	public String checkIfSorted(String input) {
		String[] lines = input.split("\r\n|\r|\n");
		String beforeSort="";
		String afterSort="";
        for(int i = 0; i < lines.length; i++) {
        	beforeSort+=lines[i].toString().trim()+"\n";
        }
		Arrays.sort(lines);
        for(int i = 0; i < lines.length; i++) {
        	afterSort+=lines[i].toString().trim()+"\n";
        }
        if((beforeSort).equalsIgnoreCase(afterSort))
        {	return "sorted";}
        else
        {
        	return "notsorted";
        }
	}

	@Override
	public String getHelp() {
		String helpMsg =  " sort : sort lines of text file\n"
				 
				 +" Command Format - sort [OPTIONS] [FILE]\n"
				 +"	FILE - Name of the file\n"
				 +"	OPTIONS\n"
				 +"		-c : Check whether the given file is already sorted, if it is not all sorted, print a\n"
				 +"           diagnostic containing the first line that is out of order\n"
				 +"	    -help : Brief information about supported options";

		return helpMsg;
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
	private boolean isInvalidSortCommand(String[] command){
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

}
