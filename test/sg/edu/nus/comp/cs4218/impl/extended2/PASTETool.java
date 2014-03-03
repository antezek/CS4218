package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.extended2.IPasteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class PASTETool extends ATool implements IPasteTool {

	private final String COMMA = ",";
	private final String EOL = "\n";
	private final String EMPTY = "";
	private final String DASH = "-";
	private final String TAB= "\t";
	
	private final String ERROR1 = "Invalid Option";
	private final String ERROR2 = "Invalid Command";
	private final String ERROR3 = "File not found";
	private final String ERROR4 = "Invalid Range Specify";
	
	private final int INPUTPRESENT = 1;
	private final int INPUTABSENT = -1;
	
	private String[] arg;
	private String delimiter;
	
	private int sOptions;
	
	private Vector<String> fileInputList;
	private int inputStartIndex;
	private int fileInput;
	private int stdInput;
	private int statusCode;
	private String output;
	private String stditem;
	
	public PASTETool(String[] arguments) {
		super(arguments);
		arg = arguments;
		sOptions = INPUTABSENT;
		output = EMPTY;
		fileInputList = new Vector<String>();
		delimiter = TAB;
		inputStartIndex = -1;
		fileInput = INPUTABSENT;
		stdInput = INPUTABSENT;
		statusCode = 0;
		
	}
	
	@Override
	public String execute(File workingDir, String stdin) {
		
		stditem = stdin;
		checkFirstInput();
		checkForMoreThanOneOptions();
		
		if(output.equals(getHelp()))
			return output;
		
		checkForInputType(workingDir);
		
		if(output.equals(EMPTY))
			processInput();
		
		return output;
	}
	
	private void checkFirstInput() {
		
		if(arg[0].equals("-s")) {
			
			sOptions = INPUTPRESENT;
			
		} else if(arg[0].equals("-d")) {
		
			delimiter = arg[1];
			inputStartIndex = 2;
			
		} else if(arg[0].equals("-help")) {
			processHelp();
			
		} else {
			inputStartIndex = 0;
			return;
		}
	}
	
	private void processInput() {
		
		if(sOptions == INPUTPRESENT) {
			processSOptions();
			return;
		} else {
			processDOptions();
		}
		
	}
	
	private void processDOptions() {
		
		if(stdInput == INPUTPRESENT) {
			String [] lines = stditem.split(EOL);
			output = output + pasteUseDelimiter(delimiter,lines);
			output = output + EOL;
		}
		int lineCounter = 0, lnEOL = 0;
		boolean AllEOL = false;
		Vector<String> PLList = new Vector<String>();
		
		while(!AllEOL) {
			
			lnEOL = 0;
			String parallelLines = EMPTY;
			for(int i = 0 ; i<fileInputList.size(); i++)
			{
				String [] split = fileInputList.get(i).split(EOL);
				if(lineCounter >= split.length) {
					parallelLines = parallelLines + EMPTY + EOL;
					lnEOL++;
				}
				else
					parallelLines = parallelLines + split[lineCounter] + EOL;
			}
			
			lineCounter ++;
			PLList.add(parallelLines);
			
			if(lnEOL == fileInputList.size())
				AllEOL = true;
		}
		
		for(int i = 0; i<PLList.size();i++)
		{
			String [] Pln = PLList.get(i).split(EOL);
			output = output + pasteUseDelimiter(delimiter, Pln);
			output = output + EOL;
		}
	}
	
	@Override
	public String pasteUseDelimiter(String delim, String[] input) {
		
		String result = EMPTY;
		int delimCounter = 0;
		for(int i = 0; i<input.length; i++) {
			
			result = result + input[i];
			if(i!=input.length-1)
				result = result + delim.charAt(delimCounter);
			
			delimCounter ++;
			if(delimCounter>=delim.length())
				delimCounter = 0;
			
		}
		
		return result;
	}

	private void processSOptions() {
		
		if(stdInput == INPUTPRESENT) {
			String [] lines = stditem.split(EOL);
			output = output + pasteSerial(lines);
			output = output + EOL;
		}
				
		for(int i = 0; i<fileInputList.size(); i++) {
			String [] lines = fileInputList.get(i).split(EOL);
			output = output + pasteSerial(lines);
			output = output + EOL;
		}
	}
	
	@Override
	public String pasteSerial(String[] input) {
		
		String result = EMPTY;
		
		for(int i = 0; i<input.length; i++)
		{
			result = result + input[i];
			if(i!=input.length-1)
				result = result + TAB;
		}
		
		return result;
	}

	private void checkForInputType(File workdir) {
		
		if(arg.length <= inputStartIndex){
			output = ERROR2;
			return;
		}
		
		for(int i = inputStartIndex; i<arg.length; i++)
		{
			if(arg[i].startsWith(DASH))
				stdInput = INPUTPRESENT;
			else
			{
				try {
					String input = getFileContents(workdir, arg[i]);
					fileInput = INPUTPRESENT;
					fileInputList.add(input);
				} catch(Exception e) {
					output = ERROR3;
				}
			}
		}
		
	}
	
	private String getFileContents(File workdir, String fileName)
			throws IOException {

		String path;

		if (!fileName.contains("\\"))
			// it is a fileName, thus it means the file resides in the same
			// directory as the working directory
			path = workdir.getAbsolutePath() + "\\" + fileName;
		else
			path = fileName;

		File file = new File(path);

		return readFile(file);
	}

	// Read from a file
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

	private void processHelp() {
		
		output = getHelp();
	}
	
	private void checkForMoreThanOneOptions() {
		
		if(inputStartIndex!=-1)
			return;
		
		if(delimiter.equals(TAB))
		{
			int i = 1;
			for(i = 1; i<arg.length; i++) {
				if(arg[i].equals("-d")) {
					delimiter = arg[i+1];
					i = i+1;
				}
				else if(arg[i].equals("-help"))
					processHelp();
				else if(arg[i].equals("-s"))
					sOptions = INPUTPRESENT;
				else
					break;
			}
			
			inputStartIndex = i -1;
			
		}
	
	}

	@Override
	public String getHelp() {
		String helpString = "paste : writes to standard output lines "
				+ "\n* of sequentially corresponding lines of each given file,"
				+ "\n* separated by a TAB character"
				+ "\n* Command Format - paste [OPTIONS] [FILE]"
				+ "\n* FILE - Name of the file, when no file is present (denoted by \"-\") "
				+ "\n* use standard input OPTIONS"
				+ "\n -s : paste one file at a time instead of in parallel"
				+ "\n -d DELIM: Use characters from the DELIM instead of TAB character"
				+ "\n -help : Brief information about supported options";
		
		return helpString;
	}



}
