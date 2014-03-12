package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		try {
			//CUTToolTest gt = new CUTToolTest();
			//gt.before();
			//gt.cOptionWithEmptyFileTest();
			//System.out.println("result: "+result);
			IntegrateTest ctt = new IntegrateTest();
			ctt.componentIntegrateTest2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}
