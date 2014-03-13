package sg.edu.nus.comp.cs4218.impl.fileutils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sg.edu.nus.comp.cs4218.impl.extended2.UNIQToolTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ HelperTest.class, DELETEToolTest.class,
		MOVEToolTest.class, COMMToolTest.class, UNIQToolTest.class,
		COPYToolTest.class, CUTToolTest.class, PASTEToolTest.class,
		SORTToolTest.class, GREPToolTest.class, LSToolTest.class,
		PWDToolTest.class, ECHOToolTest.class, CATToolTest.class,
		PIPEToolTest.class, WCToolTest.class, CDToolTest.class, ShellTest.class })
public class JUnitTestSuite {

}