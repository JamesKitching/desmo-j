package test.suites;

import test.core.simulator.SimClockModulTest;
import test.core.simulator.TimeInstantTest;
import test.core.simulator.TimeOperationsInteractionTest;
import test.core.simulator.TimeSpanTest;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * J-Unit TestSuite, which runs every simulation time related Test.
 * 
 * @author Sascha Winde, Clara Bluemm
 * 
 * @version 1.0
 *
 */
public class RunTimeTests {

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		
		suite.addTestSuite(SimClockModulTest.class);
		suite.addTestSuite(TimeInstantTest.class);
		suite.addTestSuite(TimeSpanTest.class);
		suite.addTestSuite(TimeOperationsInteractionTest.class);

		return suite;
		
	}

}