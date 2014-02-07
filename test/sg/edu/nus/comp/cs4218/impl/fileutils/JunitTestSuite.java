package sg.edu.nus.comp.cs4218.impl.fileutils;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sg.edu.nus.comp.cs4218.impl.fileutils.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CDToolTest.class, DELETEToolTest.class, HelperTest.class,
		LSToolTest.class, MOVEToolTest.class, PWDToolTest.class,
		ShellTest.class })
public class JunitTestSuite {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
