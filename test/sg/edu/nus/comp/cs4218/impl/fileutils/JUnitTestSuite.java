package sg.edu.nus.comp.cs4218.impl.fileutils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sg.edu.nus.comp.cs4218.impl.extended2.COMMToolTest;
import sg.edu.nus.comp.cs4218.impl.extended2.CUTToolTest;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTEToolTest;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTToolTest;
import sg.edu.nus.comp.cs4218.impl.extended2.UNIQToolTest;
import sg.edu.nus.comp.cs4218.impl.extended2.WCToolTest;

@RunWith(Suite.class)
/*
@Suite.SuiteClasses({ IntegrateTest.class, RunnableTest.class,
		UNIQToolTest.class, HelperTest.class, DELETEToolTest.class,
		MOVEToolTest.class, COMMToolTest.class, COPYToolTest.class,
		CUTToolTest.class, PASTEToolTest.class, SORTToolTest.class,
		GREPToolTest.class, LSToolTest.class, PWDToolTest.class,
		ECHOToolTest.class, CATToolTest.class, PIPEToolTest.class,
		WCToolTest.class, CDToolTest.class, ShellTest.class })
*/
@Suite.SuiteClasses({RunnableTest.class,
	UNIQToolTest.class, HelperTest.class, DELETEToolTest.class,
	MOVEToolTest.class, COMMToolTest.class, COPYToolTest.class,
	CUTToolTest.class, PASTEToolTest.class, SORTToolTest.class,
	GREPToolTest.class, LSToolTest.class, PWDToolTest.class,
	ECHOToolTest.class, CATToolTest.class, PIPEToolTest.class,
	WCToolTest.class, CDToolTest.class, ShellTest.class })

public class JUnitTestSuite {

}
