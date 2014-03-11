package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.extended2.ICutTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class CUTTool extends ATool implements ICutTool {

	private final String COMMA = ",";
	private final String EOL = "\n";
	private final String EMPTY = "";
	private final String DASH = "-";

	private final String ERROR1 = "Invalid Option";
	private final String ERROR2 = "Invalid Command";
	private final String ERROR3 = "File not found";
	private final String ERROR4 = "Invalid Range Specify";

	private final int INPUTPRESENT = 1;
	private final int INPUTABSENT = -1;

	private String[] arg;
	private Vector<String> cList;
	private String fList;
	private String delimiter;

	private Vector<String> fileInputList;
	private int inputStartIndex;
	private int fileInput;
	private int stdInput;
	private int statusCode;
	private String input;
	private String output;
	private String stditem;

	public CUTTool() {
		super(null);
		fList = EMPTY;
		output = EMPTY;
		fileInputList = new Vector<String>();
		cList = new Vector<String>();
		delimiter = EMPTY;
		inputStartIndex = 0;
		fileInput = INPUTABSENT;
		stdInput = INPUTABSENT;
		statusCode = 0;
	}

	public String[] getCmd(String stdin) {
		String[] tempCmd;
		int finalCount = 0;
		int count = 0;
		Scanner counter = new Scanner(stdin);
		Scanner scan = new Scanner(stdin);

		while (counter.hasNext()) {
			counter.next();
			finalCount++;
		}
		tempCmd = new String[finalCount];
		while (scan.hasNext()) {
			tempCmd[count] = scan.next();
			count++;
		}
		return tempCmd;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String[] temp;
		int count = 0;
		boolean isRepeat = false;

		if (stdin.indexOf("   ") > 0) {
			isRepeat = true;
		}
		temp = getCmd(stdin);
		if (temp.length > 3) {
			if (temp[temp.length - 2].equals("-")) {
				if (isRepeat) {
					// To cater for empty space
					arg = new String[temp.length - 1];
				} else {
					arg = new String[temp.length - 2];
				}

			} else {
				arg = new String[temp.length - 1];
			}
		} else {
			arg = new String[temp.length - 1];
		}
		for (int i = 1; i < temp.length; i++) {
			if (temp[i].equals("-") && i == (temp.length - 2)) {
				arg[count] = temp[i];
				stditem = temp[temp.length - 1];
				break;
			} else {
				if (isRepeat && count == 1) {
					arg[count] = " ";
					i--;
				} else {
					arg[count] = temp[i];
				}
			}
			count++;
		}
		output = EMPTY;
		if (!checkFirstInput())
			output = ERROR1;
		if (arg[0].equals("-help"))
			return output;

		checkForMoreThanOneOptions();
		checkForInputType(workingDir);

		if (output.equals(EMPTY))
			processInput();

		if (output.equals(ERROR3))
			statusCode = -1;

		if (output.equals(ERROR1) || output.equals(ERROR2)
				|| output.equals(ERROR4))
			return EMPTY;
		else
			return output;
	}

	private boolean checkFirstInput() {

		if (arg[0].equals("-c")) {

			cList.add(arg[1]);

		} else if (arg[0].equals("-d")) {

			delimiter = arg[1];

		} else if (arg[0].equals("-f")) {

			fList = arg[1];

		} else if (arg[0].equals("-help")) {
			processHelp();

		} else
			return false;

		inputStartIndex = 2;
		return true;
	}

	private void processHelp() {

		output = getHelp();
	}

	private void checkForMoreThanOneOptions() {

		for (int i = 2; i < arg.length; i = i + 2) {
			if (arg[i].equals("-c")) {

				cList.add(arg[i + 1]);

			} else if (arg[i].equals("-d")) {

				delimiter = arg[i + 1];

			} else if (arg[i].equals("-f")) {

				fList = arg[i + 1];

			} else
				break;

			inputStartIndex = inputStartIndex + 2;
		}

	}

	private void checkForInputType(File workdir) {

		if (arg.length <= inputStartIndex) {
			output = ERROR2;
			return;
		}

		for (int i = inputStartIndex; i < arg.length; i++) {
			if (arg[i].startsWith(DASH))
				stdInput = INPUTPRESENT;
			else {
				try {
					input = getFileContents(workdir, arg[i]);
					fileInput = INPUTPRESENT;
					fileInputList.add(input);
				} catch (Exception e) {
					output = ERROR3;
				}
			}
		}
	}

	private String getFileContents(File workdir, String fileName)
			throws IOException {

		String path;

		if (!fileName.contains("/"))
			// it is a fileName, thus it means the file resides in the same
			// directory as the working directory
			path = workdir.getAbsolutePath() + "/" + fileName;
		else
			path = fileName;

		File file = new File(path);

		return readFile(file);
	}

	// Read from a file
	private String readFile(File file) throws IOException {
		String results = "";
		BufferedReader br = new BufferedReader(new FileReader(file));

		try {

			StringBuilder fileContents = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				results += line;
				results += "\n";
				//fileContents.append(line);
				//fileContents.append("\n"); // CHANGED. BUGS!!
				line = br.readLine();
			}
			if (results.substring(results.length() - 1, results.length())
					.equals("\n")) {
				results = results.substring(0, results.length() - 1);
			}
			fileContents.append(results);
			return fileContents.toString();
		} finally {
			br.close();
		}
	}

	private void processInput() {

		processCOptions();
		if (!fList.isEmpty())
			processDOptions();

	}

	private void processDOptions() {

		if (delimiter.equals(EMPTY))
			return;

		else {
			if (stdInput == INPUTPRESENT) {

				output = output
						+ cutSpecifiedCharactersUseDelimiter(fList, delimiter,
								stditem);
			}

			int fileCounter = 0;
			if (fileInput == INPUTPRESENT) {
				while (fileCounter < fileInputList.size()) {

					output = output
							+ cutSpecifiedCharactersUseDelimiter(fList,
									delimiter, fileInputList.get(fileCounter));
					fileCounter++;
				}

			}
		}

	}

	private void processCOptions() {

		if (cList.isEmpty())
			return;

		else {

			for (int i = 0; i < cList.size(); i++) {

				if (stdInput == INPUTPRESENT) {

					output = output
							+ cutSpecfiedCharacters(cList.get(i), stditem);
				}
			}

			int fileCounter = 0;
			if (fileInput == INPUTPRESENT) {

				while (fileCounter < fileInputList.size()) {

					for (int j = 0; j < cList.size(); j++) {

						output = output
								+ cutSpecfiedCharacters(cList.get(j),
										fileInputList.get(fileCounter));
					}

					fileCounter++;
				}
			}

		}

	}

	@Override
	public int getStatusCode() {

		return statusCode;
	}

	@Override
	public String cutSpecfiedCharacters(String list, String input) {

		String results = EMPTY;
		String[] splitList = list.split(COMMA);
		String[] splitRange;
		String[] lines = input.split(EOL);
		int start, end;

		if (input.equals(EMPTY))
			return EMPTY;

		try {
			for (int h = 0; h < lines.length; h++) {
				for (int i = 0; i < splitList.length; i++) {
					if (splitList[i].contains(DASH)) {

						splitRange = splitList[i].split(DASH);
						if (splitRange.length < 2) {

							start = Integer.parseInt(splitRange[0]);
							end = lines[h].length();

						} else {
							if (splitList[i].startsWith(DASH)) {
								start = 1;
								end = Integer.parseInt(splitRange[1]);
							} else {
								start = Integer.parseInt(splitRange[0]);
								end = Integer.parseInt(splitRange[1]);
							}
						}

						if (end < start) {
							results = ERROR4;
							return results;
						}
						if (end > input.length())
							end = input.length();

						for (int j = start; j <= end; j++) {

							if (lines[i].charAt(j - 1) != '\n')
								results = results + lines[h].charAt(j - 1);
						}

					} else {

						int character = Integer.parseInt(splitList[i]);
						results = results + lines[h].charAt(character - 1);
					}
				}

				results = results + EOL; // CHANGED!!!
			}

		} catch (Exception e) {
			results = ERROR4;
		}
		// Remove trailing \n
		if (results.substring(results.length() - 1, results.length()).equals(
				"\n")) {
			results = results.substring(0, results.length() - 1);
		}
		return results;
	}

	@Override
	public String cutSpecifiedCharactersUseDelimiter(String list, String delim,
			String input) {

		String[] lines = input.split(EOL);
		String results = EMPTY;
		String[] splitList = list.split(COMMA);
		String[] splitRange;
		String[] splitByDelim;
		int start, end;

		if (input.equals(EMPTY))
			return EMPTY;

		try {
			for (int k = 0; k < lines.length; k++) {
				splitByDelim = lines[k].split(delim);

				for (int i = 0; i < splitList.length; i++) {
					if (splitList[i].contains(DASH)) {
						splitRange = splitList[i].split(DASH);
						if (splitRange.length < 2) {

							start = Integer.parseInt(splitRange[0]);
							end = lines[k].length() - 1;

						} else {
							if (splitList[i].startsWith(DASH)) {
								start = 1;
								end = Integer.parseInt(splitRange[1]);
							} else {
								start = Integer.parseInt(splitRange[0]);
								end = Integer.parseInt(splitRange[1]);
							}
						}

						if (end < start) {
							results = ERROR4;
							return results;
						}
						if (end >= splitByDelim.length)
							end = splitByDelim.length;

						for (int j = start; j <= end; j++) {
							results = results + splitByDelim[j - 1];
							if (j != end)
								results = results + delim;
						}

					} else {

						int character = Integer.parseInt(splitList[i]);
						if (splitByDelim.length == 1)
							results = results + lines[k];
						else {
							if (character == 0)
								results = results + lines[k];
							else
								results = results + splitByDelim[character - 1];
						}
					}

					results = results + EOL;
				}
			}
		} catch (Exception e) {
			results = "Invalid Range specify";
		}
		return results;
	}

	@Override
	public String getHelp() {

		String helpString = "usage: cut [OPTIONS] [FILE]"
				+ "\n"
				+ "FILE : Name of the file, when no file is present"
				+ "\n"
				+ "OPTIONS : -c LIST : Use LIST as the list of characters to cut out. Items within "
				+ "the list may be separated by commas, "
				+ "and ranges of characters can be separated with dashes. "
				+ "For example, list 1-5,10,12,18-30 specifies characters "
				+ "1 through 5, 10,12 and 18 through 30"
				+ "\n"
				+ "-d DELIM: Use DELIM as the field-separator character instead of"
				+ "the TAB character" + "\n"
				+ "-help : Brief information about supported options";

		return helpString;
	}

}