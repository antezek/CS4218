package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.impl.extended2.COMMToolTest;

public class Test {

	public static void main(String[] args) {
		/*
		Path currentRelativePath = Paths.get("");
		String curDir = currentRelativePath.toAbsolutePath().toString();	
		File workingDirectory = new File(curDir);

		String actualOutput; 
		String stdin1 = "sort -c a.txt d.txt";
		SORTTool st = new SORTTool();
		actualOutput = st.execute(workingDirectory, stdin1);
		System.out.println("actualOutput: "+actualOutput);
		*/
		COMMToolTest ctt = new COMMToolTest();
		ctt.overallTest();
	}

}