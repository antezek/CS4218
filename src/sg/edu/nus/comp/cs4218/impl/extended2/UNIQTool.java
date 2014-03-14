package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import sg.edu.nus.comp.cs4218.extended2.IUniqTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.fileutils.Helper;

public class UNIQTool extends ATool implements IUniqTool {
	private boolean ignoreCase;
	private boolean help;
	private boolean numFields;
	private ArrayList<String> fileList;
	private ArrayList<String> numberList;
	
	public UNIQTool() {
		super(null);
		ignoreCase = false;
		help = false;
		numFields = false;
		fileList = new ArrayList<String>();
		numberList = new ArrayList<String>();
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String result = "";
		String cmd = Helper.getCommand(stdin);
		stdin = stdin.replace(cmd, "").trim();
		String[] arguments = stdin.split(Helper.REGEX_WHITE_SPACE);
		ignoreCase = false;
		help = false;
		numFields = false;
		fileList = new ArrayList<String>();
		numberList = new ArrayList<String>();

		if (arguments.length != 0) {
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i].equalsIgnoreCase("-i")) {
					ignoreCase = true;
				}
				else if (arguments[i].equalsIgnoreCase("-help")) {
					help = true;
				}
				else if (arguments[i].equalsIgnoreCase("-f")) {
					numFields = true;
					numberList.add(arguments[i+1]);
					i++;
				}
				else {
					fileList.add(arguments[i]);
				}
			}
		}

		if (help) {
			return getHelp();
		}
		else {
			for (String s: fileList) {
				if (Helper.isValidFile(workingDir, s) == null) {
					setStatusCode(-1);
					result += "No such file";
					result += "\n";
					return result.trim();
				}
				else {
					if (numFields) {
						int tempNum;
						for (String num: numberList) {
							// Check for valid argument for -f
							try {
								tempNum = Integer.parseInt(num);
								if (tempNum < 0) {
									result += "Invalid argument for -f";
									result += "\n";
									return result.trim();
								}
							} catch (NumberFormatException e) {
								result += "Invalid argument for -f";
								result += "\n";
								return result.trim();
							}
							// skip num fields execution
							result += getUniqueSkipNum(tempNum, ignoreCase, readFromFile(new File(s)));
						}
					}
					else {
						// normal execution
						result += getUnique(ignoreCase, readFromFile(new File(s)));
					}
				}
			}
		}
		return result.trim();
	}

	@Override
	public String getUnique(boolean checkCase, String input) {
		String result = "";
		String lines[] = input.split("\\r?\\n");

		if (!checkCase) {
			// normal execution
			Set<String> set = new LinkedHashSet<String>();

			for (String s: lines) {
				set.add(s);
			}

			for (String ss: set) {
				result += ss;
				result += "\n";
			}
		}
		else {
			// execution for -i argument
			Set<String> set = new LinkedHashSet<String>();
			Set<String> lowerCaseSet = new LinkedHashSet<String>();

			for (String s: lines) {
				String temp = s;
				if (!lowerCaseSet.contains(s.toLowerCase())) {
					lowerCaseSet.add(s.toLowerCase());
					set.add(temp);
				}
			}

			for (String ss: set) {
				result += ss;
				result += "\n";
			}
		}
		return result;
	}

	@Override
	public String getUniqueSkipNum(int NUM, boolean checkCase, String input) {
		String result = "";
		String lines[] = input.split("\\r?\\n");

		if (!checkCase) {
			// normal execution
			Set<String> set = new LinkedHashSet<String>();

			for (String s: lines) {
				String[] temp = s.split(Helper.REGEX_WHITE_SPACE);
				String tempString = "";
				if (NUM >= temp.length) {
					// do nothing
				}
				else {
					for (int i = NUM; i < temp.length; i++) {
						tempString += temp[i];
						tempString += " ";
					}
					set.add(tempString.trim());
				}
			}

			for (String ss: set) {
				result += ss;
				result += "\n";
			}
		}
		else {
			// execution for -i argument
			Set<String> set = new LinkedHashSet<String>();
			Set<String> lowerCaseSet = new LinkedHashSet<String>();

			for (String s: lines) {
				String[] temp = s.split(Helper.REGEX_WHITE_SPACE);
				String tempString = "";
				if (NUM >= temp.length) {
					// do nothing
				}
				else {
					for (int i = NUM; i < temp.length; i++) {
						tempString += temp[i];
						tempString += " ";
					}
					String temp2 = tempString.trim();
					if (!lowerCaseSet.contains(tempString.toLowerCase())) {
						lowerCaseSet.add(tempString.toLowerCase());
						set.add(temp2);
					}
				}
			}

			for (String ss: set) {
				result += ss;
				result += "\n";
			}
		}
		return result;
	}

	@Override
	public String getHelp() {
		String helpOutput = "uniq : Writes the unique lines in the given input, with repetitions compares only in adjacent input lines.";
		helpOutput += "\nCommand Format - uniq [OPTIONS] [FILE]\nFILE - Name of the file. Alternatively use \"-\" to enter standard input.";
		helpOutput += "\nOPTIONS\n\t-f NUM : Skips NUM fields on each line before checking for uniqueness. Fields are sequences of non-space non-tab characters that are separated from each other by at least one space or tab.";
		helpOutput += "\n\t-i : Ignore differences in case when comparing lines.";
		helpOutput += "\n\t-help : Brief information about supported options";
		return helpOutput;
	}

	public String readFromFile(File inputFile){
		String output = ""; FileReader fr = null;
		try{
			fr = new FileReader(inputFile);
		} catch(FileNotFoundException e){
			e.printStackTrace();
			return "File not found";
		}
		BufferedReader br = new BufferedReader(fr);
		try{
			String line = br.readLine();
			while(line != null){
				if(line.equalsIgnoreCase("\n")||line.equalsIgnoreCase(""))
					output+="\n";
				else
					output += line + "\n";
				line = br.readLine();
			}
		} catch(IOException e){
			e.printStackTrace();
			return "Unable to read file";
		} finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

}
