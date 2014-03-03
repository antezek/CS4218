package sg.edu.nus.comp.cs4218.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.Helper;

public class COMMTool extends ATool implements ICommTool {
	public COMMTool(String args[]) {
		super(null);
		list = new ArrayList();
		listA = new ArrayList();
		listB = new ArrayList();
		listC = new ArrayList();
		if (args.length == 4) {
			stdin += "comm" + dash + args[0] + dash + args[1] + dash + args[2]
					+ dash + args[3];
		} else if (args.length == 3) {
			stdin += "comm" + dash + args[0] + dash + args[1] + dash + args[2];
		} else if (args.length == 2) {
			stdin += "comm" + dash + args[0] + dash + args[1];
		} else {
			setStatusCode(1);
			System.out.println("Error: wrong inputs");
		}
	}

	public COMMTool() {
		super(null);
	}

	File workingDir;
	String stdin = "";
	final String tab = "\t";
	final String newLine = "\n";
	final String dash = " ";
	ArrayList listA, listB, listC, list;

	public ArrayList returnArray(String input) {
		ArrayList list = new ArrayList();
		Scanner sc = new Scanner(input);

		while (sc.hasNext()) {
			list.add(sc.next());
		}
		return list;
	}

	public String compareFiles(String input1, String input2) {
		String result = "";
		String uniA = "";
		String uniB = "";
		String com = "";
		String temp = "";
		String temp1 = "";
		String temp2 = "";
		String temp3 = "";
		String str1 = "";
		String str2 = "";
		int countA = 0;
		int countB = 0;
		int countC = 0;
		int tempCount = 0;
		// Scanner sc1;
		// Scanner sc2;
		ArrayList inputA;
		ArrayList inputB;
		ComObj co;

		inputA = new ArrayList();
		inputB = new ArrayList();
		inputA = returnArray(input1);
		inputB = returnArray(input2);
		// sc1 = new Scanner(input1);
		// sc2 = new Scanner(input2);
		while (countA < inputA.size() || countB < inputB.size()) {
			// String str1 = sc1.next();
			// String str2 = sc2.next();
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

			if (foundA < 0 && foundAC < 0) {
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

			/*
			 * if (!str1.equals(str2)) { // uniA += str1 + tab + dash + tab +
			 * dash + newLine + dash + // tab; // uniB += str2 + tab + dash +
			 * newLine + dash + tab + dash + // tab; listA.add(str1);
			 * listB.add(str2); // list.add(new ComObj(str1, "uniA")); //
			 * list.add(new ComObj(str2, "uniB")); }
			 * 
			 * if (str1.equals(str2)) { // com += str1 + newLine + dash + tab +
			 * dash + tab; listC.add(str1); // list.add(new ComObj(str1,
			 * "com")); }
			 */
			countA++;
			countB++;
		}
		// Reset counter
		countA = 0;
		countB = 0;
		countC = 0;
		// When all 3 cols contain something
		if (!listA.isEmpty() && !listB.isEmpty() && !listC.isEmpty()) {

			while ((countA < listA.size()) || (countB < listB.size())
					|| (countC < listC.size())) {
				// when A slot is empty, fill up slot b and c
				if (countA == listA.size()) {
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
					// when b slot is empty, fill up slot a and c
				} else if (countB == listB.size()) {
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
					// when c slot is empty, fill up slot a and b
				} else if (countC == listC.size()) {
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
					// Fill up as normal
				} else {
					temp1 = listA.get(countA).toString();
					temp2 = listB.get(countB).toString();
					temp3 = listC.get(countC).toString();
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

			}

			// When all 3 cols contain nothing
		} else if (listA.isEmpty() && listB.isEmpty() && listC.isEmpty()) {
			result = "";
			setStatusCode(0);
		} else if (listA.isEmpty() && listB.isEmpty()) {
			// When colA and colB is empty
			// result += dash + tab + dash + tab + com.trim();
			for (int i = 0; i < listC.size(); i++) {
				// result += listC.get(i) + newLine + dash + tab + dash + tab;
				list.add(new ComObj(listC.get(i).toString(), "com"));
			}

			setStatusCode(0);
		} else if (!listA.isEmpty() && !listB.isEmpty() && listC.isEmpty()) {
			// When only colC is empty

			while ((countA < listA.size()) || (countB < listB.size())) {
				if (countA == listA.size()) {
					while (countB < listB.size()) {
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
			/*
			 * result += uniA.trim() + tab + dash + tab + dash + newLine + dash
			 * + tab + uniB.trim() + tab + dash + newLine + dash + tab + dash +
			 * tab + com.trim(); // TODO
			 */
			setStatusCode(0);
		}

		result = formatResult(list);

		return result;
	}

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
				result += tab + dash + tab + dash + newLine + dash + tab;
			}
			if (tempCount == 0 && temp2.equals("com")) {
				result += dash + tab + dash + tab;
			}

			if ((tempCount + 1) < list.size()) {// TODO
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
				}
			}
			result += temp1;
			tempCount++;
		}

		return result;
	}

	public String compareFilesCheckSortStatus(String input1, String input2) {
		String result;
		boolean isSort;

		result = compareFiles(input1, input2);
		isSort = true; // TODO : Wait for Sort func to finish
		if (isSort) { // Test if it's sorted
			setStatusCode(0);
		} else {
			setStatusCode(1);
		}
		return result;
	}

	public String compareFilesDoNotCheckSortStatus(String input1, String input2) {
		String result;

		result = compareFiles(input1, input2);
		setStatusCode(0);
		return result;
	}

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

	public String[] getCmd(String stdin) {
		int count = 0;
		String temp = "";
		String[] tempCmd = new String[5];
		String[] finalCmd = new String[5];
		Scanner scan = new Scanner(stdin);
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
		return finalCmd;
	}

	public String read(InputStream is) {
		Scanner sc = new Scanner(is);
		String temp = "";

		while (sc.hasNext()) {
			temp += sc.next() + " ";
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
		String[] cmd = new String[4];
		String result = "";
		boolean isError = false;

		cmd = getCmd(this.stdin);
		if (!cmd[1].equals("-help") && !cmd[2].equals("-help")) {
			file1 = Helper.isValidFile(workingDir, cmd[3]);
			file2 = Helper.isValidFile(workingDir, cmd[4]);
			try {
				fi2 = new FileInputStream(file2);
			} catch (Exception e) {
				isError = true;
				result = "File 2 doesn't exist!";
			}
			try {
				fi1 = new FileInputStream(file1);
			} catch (Exception e) {
				isError = true;
				result = "File 1 doesn't exist!";
			}
			if (!isError) {
				di1 = new DataInputStream(fi1);
				di2 = new DataInputStream(fi2);
				if (cmd[1].equals("")) { // For "comm file1 file2"
					result = compareFiles(read(di1), read(di2));
				} else if ((cmd[1].equals("-c") & !cmd[2].equals("-d"))
						|| (cmd[1].equals("-d") & !cmd[2].equals("-c"))
						|| cmd[1].equals("-help") || cmd[2].equals("-help")) { // For
																				// "comm -c -help file1 file2"
																				// ||
																				// "comm -d -help file1 file2"
																				// via
																				// versa
																				// but
																				// not
																				// "comm -c -d file1 file2"
					if ((cmd[1].equals("-c") || cmd[2].equals("-c"))
							&& (!cmd[1].equals("-help") & !cmd[2]
									.equals("-help"))) { // When
															// -help
															// args,
															// -help
															// take
															// priority
						result = compareFilesCheckSortStatus(read(di1),
								read(di2));
					} else if ((cmd[1].equals("-d") || cmd[2].equals("-d"))
							&& (!cmd[1].equals("-help") & !cmd[2]
									.equals("-help"))) { // When
						// -help
						// args,
						// -help
						// take
						// priority
						result = compareFilesDoNotCheckSortStatus(read(di1),
								read(di2));
					} else {
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

		return result; // Return error msg
	}
}
