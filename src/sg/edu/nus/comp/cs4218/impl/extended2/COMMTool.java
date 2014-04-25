package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.fileutils.Helper;

public class COMMTool extends ATool implements ICommTool {
	public COMMTool(String args[]) {
		super(null);
		list = new ArrayList<ComObj>();
		listA = new ArrayList<String>();
		listB = new ArrayList<String>();
		listC = new ArrayList<String>();
		if (args.length == 4) {
			stdin += "comm" + dash + args[0] + dash + args[1] + dash + args[2]
					+ dash + args[3];
		} else if (args.length == 3) {
			stdin += "comm" + dash + args[0] + dash + args[1] + dash + args[2];
		} else if (args.length == 2) {
			stdin += "comm" + dash + args[0] + dash + args[1];
		} else {
			setStatusCode(1);
		}
	}

	public COMMTool() {
		super(null);
		list = new ArrayList<ComObj>();
		listA = new ArrayList<String>();
		listB = new ArrayList<String>();
		listC = new ArrayList<String>();
	}

	File workingDir;
	String stdin = "";
	final String tab = "\t";
	final String newLine = "\n";
	final String dash = " ";
	String[] cmd;
	ArrayList<String> listA, listB, listC;
	ArrayList<ComObj> list;
	int countA = 0;
	int countB = 0;
	int countC = 0;

	public ArrayList<String> returnArray(String input) {
		ArrayList<String> list = new ArrayList<String>();
		Scanner sc = new Scanner(input);

		sc.useDelimiter("\n");
		while (sc.hasNext()) {
			list.add(sc.next());
		}
		return list;
	}
/*
 * compare when count for A list equals list A
 * 
 */
	public void compareWhenCountAEqualsListA() {
		String temp2 = "";
		String temp3 = "";
		while (countB < listB.size() && countC < listC.size()) {
			temp2 = listB.get(countB).toString();
			temp3 = listC.get(countC).toString();
			if (temp2.compareTo(temp3) <= 0) {
				list.add(new ComObj(temp2, "uniB"));
				countB++;
			} else {
				list.add(new ComObj(temp3, "com"));
				countC++;
			}
		}
		if (countB == listB.size()) {
			while (countC < listC.size()) {
				temp3 = listC.get(countC).toString();
				list.add(new ComObj(temp3, "com"));
				countC++;
			}
		} else if (countC == listC.size()) {
			while (countB < listB.size()) {
				temp2 = listB.get(countB).toString();
				list.add(new ComObj(temp2, "uniB"));
				countB++;
			}
		}
	}
	/*
	 * compare when count for B list equals list B
	 * 
	 */
	public void compareWhenCountBEqualsListB() {
		String temp1 = "";
		String temp3 = "";

		while (countA < listA.size() && countC < listC.size()) {
			temp1 = listA.get(countA).toString();
			temp3 = listC.get(countC).toString();
			if (temp1.compareTo(temp3) <= 0) {
				list.add(new ComObj(temp1, "uniA"));
				countA++;
			} else {
				list.add(new ComObj(temp3, "com"));
				countC++;
			}
		}
		if (countA == listA.size()) {
			while (countC < listC.size()) {
				temp3 = listC.get(countC).toString();
				list.add(new ComObj(temp3, "com"));
				countC++;
			}
		}
		if (countC == listC.size()) {
			while (countA < listA.size()) {
				temp1 = listA.get(countA).toString();
				list.add(new ComObj(temp1, "uniA"));
				countA++;
			}
		}
	}
	/*
	 * compare when count for C list equals list C
	 * 
	 */
	public void compareWhenCountCEqualsListC() {
		String temp1 = "";
		String temp2 = "";
		while (countA < listA.size() && countB < listB.size()) {
			temp1 = listA.get(countA).toString();
			temp2 = listB.get(countB).toString();
			if (temp1.compareTo(temp2) <= 0) {
				list.add(new ComObj(temp1, "uniA"));
				countA++;
			} else {
				list.add(new ComObj(temp2, "uniB"));
				countB++;
			}
		}
		if (countA == listA.size()) {
			while (countB < listB.size()) {
				temp2 = listB.get(countB).toString();
				list.add(new ComObj(temp2, "uniB"));
				countB++;
			}
		}
		if (countB == listB.size()) {
			while (countA < listA.size()) {
				temp1 = listA.get(countA).toString();
				list.add(new ComObj(temp1, "uniA"));
				countA++;
			}
		}
	}
	/*
	 * compare normal flow when all 3 cols has something
	 * 
	 */
	public void compareNorm() {
		String temp1 = listA.get(countA).toString();
		String temp2 = listB.get(countB).toString();
		String temp3 = listC.get(countC).toString();

		if (temp1.compareTo(temp2) <= 0) {
			if (temp1.compareTo(temp3) <= 0) {
				// For A<=B,A<=C
				list.add(new ComObj(temp1, "uniA"));
				if (countA < listA.size()) {
					countA++;
				}
			} else {
				// For A<=B,A>C
				list.add(new ComObj(temp3, "com"));
				if (countC < listC.size()) {
					countC++;
				}
			}
		} else {
			// For A>B,B>C
			if (temp2.compareTo(temp3) > 0) {
				list.add(new ComObj(temp3, "com"));
				if (countC < listC.size()) {
					countC++;
				}
			} else {
				// For A>B,B<C
				list.add(new ComObj(temp2, "uniB"));
				if (countB < listB.size()) {
					countB++;
				}
			}
		}
	}
	/*
	 * compare only when A and B cols not empty
	 * 
	 */
	public void compareOnlyABNotEmpty() {
		// When colA and colB is empty
		// result += dash + tab + dash + tab + com.trim();
		for (int i = 0; i < listC.size(); i++) {
			// result += listC.get(i) + newLine + dash + tab + dash + tab;
			list.add(new ComObj(listC.get(i).toString(), "com"));
		}
		setStatusCode(0);
	}
	
	/*
	 * compare only when C col is empty
	 * 
	 */
	public void compareOnlyCEmpty() {
		String temp1 = "";
		String temp2 = "";

		// When only colC is empty
		while ((countA < listA.size()) || (countB < listB.size())) {
			if (countA == listA.size()) {
				while (countB < listB.size()) { // When colA are used up,
												// dump in everything else
												// in colB. vice versa
					temp2 = listB.get(countB).toString();
					list.add(new ComObj(temp2, "uniB"));
					countB++;
				}
			} else if (countB == listB.size()) {
				while (countA < listA.size()) {
					temp1 = listA.get(countA).toString();
					list.add(new ComObj(temp1, "uniA"));
					countA++;
				}
			} else {
				temp1 = listA.get(countA).toString();
				temp2 = listB.get(countB).toString();
				if (temp1.compareTo(temp2) <= 0) {
					list.add(new ComObj(temp1, "uniA"));
					if (countA < listA.size()) {
						countA++;
					}

				} else {
					list.add(new ComObj(temp2, "uniB"));
					if (countB < listB.size()) {
						countB++;
					}
				}
			}
		}
		setStatusCode(0);
	}
	/*
	 * compare only when B col is empty
	 * 
	 */
	public void compareOnlyBEmpty() {
		String temp1 = "";
		String temp3 = "";
		while ((countA < listA.size()) || (countC < listC.size())) {
			if (countA == listA.size()) {
				while (countC < listC.size()) { // When colA are used up,
												// dump in everything else
												// in colC. vice versa
					temp3 = listC.get(countC).toString();
					list.add(new ComObj(temp3, "com"));
					countC++;
				}
			} else if (countC == listC.size()) {
				while (countA < listA.size()) {
					temp1 = listA.get(countA).toString();
					list.add(new ComObj(temp1, "uniA"));
					countA++;
				}
			} else {
				temp1 = listA.get(countA).toString();
				temp3 = listC.get(countC).toString();
				if (temp1.compareTo(temp3) <= 0) {
					list.add(new ComObj(temp1, "uniA"));
					if (countA < listA.size()) {
						countA++;
					}

				} else {
					list.add(new ComObj(temp3, "com"));
					if (countC < listC.size()) {
						countC++;
					}
				}
			}
		}
		setStatusCode(0);
	}
	/*
	 * compare only when A col is empty
	 * 
	 */
	public void compareOnlyAEmpty() {
		String temp2 = "";
		String temp3 = "";
		while ((countB < listB.size()) || (countC < listC.size())) {
			if (countB == listB.size()) {
				while (countC < listC.size()) { // When colB are used up, dump in everything else in colC. vice versa
					temp3 = listC.get(countC).toString();
					list.add(new ComObj(temp3, "com"));
					countC++;
				}
			} else if (countC == listC.size()) {
				while (countB < listB.size()) {
					temp2 = listB.get(countB).toString();
					list.add(new ComObj(temp2, "uniB"));
					countB++;
				}
			} else {
				temp2 = listB.get(countB).toString();
				temp3 = listC.get(countC).toString();
				if (temp2.compareTo(temp3) <= 0) {
					list.add(new ComObj(temp2, "uniB"));
					if (countB < listB.size()) {
						countB++;
					}

				} else {
					list.add(new ComObj(temp3, "com"));
					if (countC < listC.size()) {
						countC++;
					}
				}
			}
		}
		setStatusCode(0);
	}
	/*
	 * compare files
	 * 
	 */
	public String compareFiles(String input1, String input2) {
		String result = "";
		String str1 = "";
		String str2 = "";
		ArrayList<String> inputA = new ArrayList<String>();
		ArrayList<String> inputB = new ArrayList<String>();
		ArrayList<ComObj> tempList;
		tempList = new ArrayList<ComObj>();
		inputA = returnArray(input1);
		inputB = returnArray(input2);
		while (countA < inputA.size() || countB < inputB.size()) {
			if (countA < inputA.size()) {
				str1 = inputA.get(countA).toString();
			}
			if (countB < inputB.size()) {
				str2 = inputB.get(countB).toString();
			}
			int foundA = inputB.indexOf(str1);
			int foundAC = listC.indexOf(str1);
			int foundB = inputA.indexOf(str2);
			int foundBC = listC.indexOf(str2);

			if (foundA < 0 && foundAC < 0) { // If not found, meant enter into non-list C. Else enter into listC
				listA.add(str1);
			} else {
				if (listC.indexOf(str1) < 0) {
					listC.add(str1);
				}
			}
			if (foundB < 0 && foundBC < 0) {
				listB.add(str2);
			} else {
				if (listC.indexOf(str2) < 0) {
					listC.add(str2);
				}
			}
			str1 = "";
			str2 = "";
			countA++;
			countB++;
		}
		countA = 0;	// Reset counter
		countB = 0;
		countC = 0;
		if (!listA.isEmpty() && !listB.isEmpty() && !listC.isEmpty()) {		// When all 3 cols contain something
			while ((countA < listA.size()) || (countB < listB.size()) || (countC < listC.size())) {
				if (countA == listA.size()) {			// when A slot is empty, fill up slot b and c
					compareWhenCountAEqualsListA();
				} else if (countB == listB.size()) {	// when b slot is empty, fill up slot a and c
					compareWhenCountBEqualsListB();
				} else if (countC == listC.size()) {	// when c slot is empty, fill up slot a and b
					compareWhenCountCEqualsListC();
				} else {
					compareNorm();						// Fill up as normal
				}
			}
		} else if (listA.isEmpty() && listB.isEmpty() && listC.isEmpty()) {		// When all 3 cols contain nothing
			result = "";
			setStatusCode(0);
		} else if (listA.isEmpty() && listB.isEmpty()) {
			compareOnlyABNotEmpty();
		} else if (!listA.isEmpty() && !listB.isEmpty() && listC.isEmpty()) {
			compareOnlyCEmpty();
		} else if (!listA.isEmpty() && listB.isEmpty() && !listC.isEmpty()) {
			compareOnlyBEmpty();
		} else if (listA.isEmpty() && !listB.isEmpty() && !listC.isEmpty()) {
			compareOnlyAEmpty();
		}
		tempList = filterList(list);
		result = formatResult(tempList);
		return result;
	}
	/*
	 * Filter the list
	 * 
	 */
	public ArrayList filterList(ArrayList list) {
		ArrayList tempList = new ArrayList();
		String temp1 = "";
		ComObj co = null;

		for (int i = 0; i < list.size(); i++) {
			co = (ComObj) list.get(i);
			temp1 = co.getName();
			if (!temp1.equals("")) {
				tempList.add(co);
			}
			temp1 = "";
		}
		return tempList;
	}
	/*
	 * Format Result for display
	 * 
	 */
	public String formatResult(ArrayList list) {
		String result = "";
		String temp, temp1, temp2;
		int tempCount = 0;
		ComObj co;

		while (tempCount < list.size()) {
			co = (ComObj) list.get(tempCount);
			temp1 = co.getName();
			temp2 = co.getSlot();
			if (tempCount == 0 && temp2.equals("uniB")) {
				// result += tab + dash + tab + dash + newLine + dash + tab;
				result += tab;
			}
			if (tempCount == 0 && temp2.equals("com")) {
				result += dash + tab + dash + tab;
			}

			if ((tempCount + 1) < list.size()) {
				co = (ComObj) list.get(tempCount + 1); // Peek in the next
				temp = co.getSlot();
				if (temp2.equals("uniA") && temp.equals("uniA")) {
					temp1 += tab + dash + tab + dash + newLine;
				} else if (temp2.equals("uniA") && temp.equals("uniB")) {
					temp1 += tab + dash + tab + dash + newLine + dash + tab;
				} else if (temp2.equals("uniB") && temp.equals("uniA")) {
					temp1 += tab + dash + newLine;
				} else if (temp2.equals("uniB") && temp.equals("uniB")) {
					temp1 += tab + dash + newLine + dash + tab;
				} else if (temp2.equals("uniB") && temp.equals("com")) {
					temp1 += tab + dash + newLine + dash + tab + dash + tab;
				} else if (temp2.equals("com") && temp.equals("com")) {
					temp1 += newLine + dash + tab + dash + tab;
				} else if (temp2.equals("uniA") && temp.equals("com")) {
					temp1 += tab + dash + tab + dash + newLine + dash + tab
							+ tab;
				} else if (temp2.equals("com") && temp.equals("uniB")) {
					temp1 += tab + dash + tab + dash + newLine + tab;
				} else if (temp2.equals("com") && temp.equals("uniA")) {
					temp1 += tab + dash + tab + dash + newLine;
				}
			}
			result += temp1;
			tempCount++;
		}

		return result;
	}
	/*
	 * compare files with sort attribute 
	 * 
	 */
	public String compareFilesCheckSortStatus(String input1, String input2) {
		String result = "";
		boolean isFile1Sorted = false;
		boolean isFile2Sorted = false;
		String temp = "";
		boolean isSort;
		SORTTool wctool;

		wctool = new SORTTool();
		temp = wctool.checkIfSorted(input1);
		if (temp.equalsIgnoreCase("sorted")) {
			isFile1Sorted = true;
		}
		temp = wctool.checkIfSorted(input2);
		if (temp.equalsIgnoreCase("sorted")) {
			isFile2Sorted = true;
		}
		if (!isFile1Sorted && !isFile2Sorted) {
			result = "File 1 not sorted!\nFile 2 not sorted!";
		} else if (!isFile1Sorted) {
			result = "File 1 not sorted!";
		} else if (!isFile2Sorted) {
			result = "File 2 not sorted!";
		} else if (isFile1Sorted && isFile2Sorted) {
			result = compareFiles(input1, input2);
		}
		setStatusCode(0);
		return result;
	}
	/*
	 * compare files with do not sort attribute 
	 * 
	 */
	public String compareFilesDoNotCheckSortStatus(String input1, String input2) {
		String result;

		result = compareFiles(input1, input2);
		setStatusCode(0);
		return result;
	}
	/*
	 * get help 
	 * 
	 */
	public String getHelp() {
		String helpOutput;

		helpOutput = " /*\n"
				+ "\n*"
				+ "\n* comm : Compares two sorted files line by line. With no options, produce three-column output."
				+ "\n* 		 Column one contains lines unique to FILE1, column two contains lines unique to FILE2,"
				+ "\n 		 and column three contains lines common to both files."
				+ "\n*"
				+ "\n*	Command Format - comm [OPTIONS] FILE1 FILE2"
				+ "\n*	FILE1 - Name of the file 1"
				+ "\n*	FILE2 - Name of the file 2"
				+ "\n*		-c : check that the input is correctly sorted"
				+ "\n*      -d : do not check that the input is correctly sorted"
				+ "\n*      -help : Brief information about supported options"
				+ "\n*/";

		setStatusCode(0);
		return helpOutput;
	}
	/*
	 * Execute the command input
	 * 
	 */
	public String[] getCmd(String stdin) {
		int count = 0;
		String temp = "";
		String[] tempCmd = new String[5];
		String[] finalCmd = new String[5];
		Scanner scan = null;
		try {
			scan = new Scanner(stdin);
			while (scan.hasNext()) {
				tempCmd[count] = scan.next();
				count++;
			}
			if (count == 3) { // For "comm file1 file2" w/o args
				finalCmd[0] = tempCmd[0];
				finalCmd[1] = "";
				finalCmd[2] = "";
				finalCmd[3] = tempCmd[1];
				finalCmd[4] = tempCmd[2];
			} else if (count == 4) { // for "comm -s -help file1 file2"
				finalCmd[0] = tempCmd[0];
				finalCmd[1] = tempCmd[1];
				finalCmd[2] = "";
				finalCmd[3] = tempCmd[2];
				finalCmd[4] = tempCmd[3];
			} else {
				System.arraycopy(tempCmd, 0, finalCmd, 0, 5);
			}
		} catch (Exception e) {
			setStatusCode(1);
		}
		return finalCmd;
	}

	public String read(InputStream is) { // TODO Find another delimiter sub
		Scanner sc = new Scanner(is);

		String temp = "";
		while (sc.hasNext()) {
			temp += sc.nextLine() + "\n";
		}
		return temp;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		File file1 = null;
		File file2 = null;
		FileInputStream fi1 = null;
		FileInputStream fi2 = null;
		DataInputStream di1 = null;
		DataInputStream di2 = null;
		cmd = new String[4];
		String result = "";
		boolean isError = false;

		cmd = getCmd(stdin);
		try {
			if (!cmd[1].equals("-help") && !cmd[2].equals("-help")
					&& !cmd[3].equals("-help") && !cmd[4].equals("-help")) {
				file1 = Helper.isValidFile(workingDir, cmd[3]);
				file2 = Helper.isValidFile(workingDir, cmd[4]);
				try {
					fi2 = new FileInputStream(file2);
				} catch (Exception e) {
					isError = true;
					setStatusCode(1);
					result = "File 2 doesn't exist!";
				}
				try {
					fi1 = new FileInputStream(file1);
				} catch (Exception e) {
					isError = true;
					setStatusCode(1);
					result = "File 1 doesn't exist!";
				}
				if (!isError) {
					di1 = new DataInputStream(fi1);
					di2 = new DataInputStream(fi2);
					if (cmd[1].equals("")) { // For "comm file1 file2"
						result = compareFiles(read(di1), read(di2));
						// For "comm -c -help file1 file2" || "comm -d -help file1 file2" via versa but not "comm -c -d file1 file2"
					} else if ((cmd[1].equals("-c") & !cmd[2].equals("-d"))
							|| (cmd[1].equals("-d") & !cmd[2].equals("-c"))
							|| cmd[1].equals("-help") || cmd[2].equals("-help")) {
						// When -help args, -help take priority
						if ((cmd[1].equals("-c") || cmd[2].equals("-c"))
								&& (!cmd[1].equals("-help") & !cmd[2]
										.equals("-help"))) {
							this.workingDir = workingDir;
							result = compareFilesCheckSortStatus(read(di1),
									read(di2));
							// When -help args, -help take priority
						} else if ((cmd[1].equals("-d") || cmd[2].equals("-d"))
								&& (!cmd[1].equals("-help") & !cmd[2]
										.equals("-help"))) {
							result = compareFilesDoNotCheckSortStatus(
									read(di1), read(di2));
						} else if (cmd[1].equals("-help")
								|| cmd[2].equals("-help")
								|| cmd[3].equals("-help")) {
							result = getHelp();
						}
					} else {
						setStatusCode(1);
						result = "Error: Invalid command";
					}
				}
			} else {
				result = getHelp();
			}
		} catch (Exception e) {
			System.out.println("ERROR: Please enter again!");
		}
		return result; // Return error msg
	}
}
