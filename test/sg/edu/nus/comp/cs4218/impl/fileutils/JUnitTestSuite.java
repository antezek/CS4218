package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ COMMToolTest.class, CUTToolTest.class,
		PASTEToolTest.class, SORTToolTest.class, HelperTest.class,
		GREPToolTest.class, CDToolTest.class, DELETEToolTest.class,
		LSToolTest.class, MOVEToolTest.class, PWDToolTest.class,
		ECHOToolTest.class, CATToolTest.class, COPYToolTest.class,
		PIPEToolTest.class, WCToolTest.class, ShellTest.class })
public class JUnitTestSuite {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
