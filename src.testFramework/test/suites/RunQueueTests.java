package test.suites;

import test.core.simulator.ProcessQueueModulTest;
import test.core.simulator.QueueListFifoModulTest;
import test.core.simulator.QueueListLifoModulTest;
import test.core.simulator.QueueModulTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * J-Unit TestSuite, which runs every Queue related Test.
 * 
 * @author Sascha Winde, Clara Bluemm
 * 
 * @version 1.0
 *
 */
public class RunQueueTests {

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(ProcessQueueModulTest.class);
		suite.addTestSuite(QueueListLifoModulTest.class);
		suite.addTestSuite(QueueListFifoModulTest.class);
		suite.addTestSuite(QueueModulTest.class);
	
		return suite;
		
	}

}