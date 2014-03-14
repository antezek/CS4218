package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.extended2.COMMToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.CUTToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.PASTEToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.SORTToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.UNIQToolRunnable;
import sg.edu.nus.comp.cs4218.impl.extended2.WCToolRunnable;

/**
 * DELETEToolTest class to test the functionality of DELETETool
 *
 */
public class RunnableTest {
	private File workingDir;
	private String stdin;
	
	@Before
	public void setUp() throws Exception {
		workingDir = new File(System.getProperty("user.dir"));
		stdin = "testing all runnable interface";
	}

	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * Test basic runnable interface
	 */
	@Test
	public void testAllBasicRunnable() {
		CATToolRunnable cat = new CATToolRunnable(workingDir, stdin);
		cat.run();
		
		CDToolRunnable cd = new CDToolRunnable(workingDir, stdin);
		cd.run();
		
		COPYToolRunnable copy = new COPYToolRunnable(workingDir, stdin);
		copy.run();
		
		DELETEToolRunnable del = new DELETEToolRunnable(workingDir, stdin);
		del.run();
		
		ECHOToolRunnable echo = new ECHOToolRunnable(workingDir, stdin);
		echo.run();
		
		GREPToolRunnable grep = new GREPToolRunnable(workingDir, stdin);
		grep.run();
		
		LSToolRunnable ls = new LSToolRunnable(workingDir, stdin);
		ls.run();
		
		MOVEToolRunnable move = new MOVEToolRunnable(workingDir, stdin);
		move.run();
		
		PIPEToolRunnable pipe = new PIPEToolRunnable(workingDir, stdin);
		pipe.run();
		
		PWDToolRunnable pwd = new PWDToolRunnable(workingDir, stdin);
		pwd.run();
	}
	
	/**
	 * Test extended runnable interface
	 */
	@Test
	public void testAllExtendedRunnable() {
		COMMToolRunnable comm = new COMMToolRunnable(workingDir, stdin);
		comm.run();
		
		CUTToolRunnable cut = new CUTToolRunnable(workingDir, stdin);
		cut.run();
		
		PASTEToolRunnable paste = new PASTEToolRunnable(workingDir, stdin);
		paste.run();
		
		SORTToolRunnable sort = new SORTToolRunnable(workingDir, stdin);
		sort.run();
		
		UNIQToolRunnable uniq = new UNIQToolRunnable(workingDir, stdin);
		uniq.run();
		
		WCToolRunnable wc = new WCToolRunnable(workingDir, stdin);
		wc.run();
	}
}
