package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CDToolTest.class, DELETEToolTest.class, HelperTest.class,
	LSToolTest.class, MOVEToolTest.class, PWDToolTest.class,
	ShellTest.class, ECHOToolTest.class, CATToolTest.class, COPYToolTest.class })
public class JunitTestSuite {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
