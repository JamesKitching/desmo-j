package test.suites;

import test.core.simulator.ArrivalProcessTest;
import test.core.simulator.ComplexSimProcessTest;
import test.core.simulator.EntityTest;
import test.core.simulator.EventNoteModulTest;
import test.core.simulator.EventTest;
import test.core.simulator.ExperimentTest;
import test.core.simulator.ExternalEventTest;
import test.core.simulator.InterruptCodeTest;
import test.core.simulator.ModelComponentTest;
import test.core.simulator.ModelTest;
import test.core.simulator.ProcessQueueModulTest;
import test.core.simulator.QueueListFifoModulTest;
import test.core.simulator.QueueListLifoModulTest;
import test.core.simulator.QueueModulTest;
import test.core.simulator.SchedulableTest;
import test.core.simulator.SchedulerInteractionTest;
import test.core.simulator.SimClockModulTest;
import test.core.simulator.SimprocessTest;
import test.core.simulator.TimeInstantTest;
import test.core.simulator.TimeOperationsInteractionTest;
import test.core.simulator.TimeSpanTest;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * J-Unit TestSuite, which runs every implemented Test.
 * 
 * @author Sascha Winde, Clara Bluemm
 * 
 * @version 1.0
 *
 */
public class RunCoreSimulatorTests {

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(ArrivalProcessTest.class);
		suite.addTestSuite(ComplexSimProcessTest.class);
		suite.addTestSuite(EntityTest.class);
		suite.addTestSuite(SchedulableTest.class);
		suite.addTestSuite(SimprocessTest.class);
		suite.addTestSuite(EventTest.class);
		suite.addTestSuite(EventNoteModulTest.class);
		suite.addTestSuite(ExperimentTest.class);
		suite.addTestSuite(ExternalEventTest.class);
		suite.addTestSuite(InterruptCodeTest.class);
		suite.addTestSuite(ModelComponentTest.class);
		suite.addTestSuite(ModelTest.class);
		suite.addTestSuite(ProcessQueueModulTest.class);
		suite.addTestSuite(QueueListLifoModulTest.class);
		suite.addTestSuite(QueueListFifoModulTest.class);
		suite.addTestSuite(QueueModulTest.class);
		suite.addTestSuite(SchedulerInteractionTest.class);
		suite.addTestSuite(SimClockModulTest.class);
		suite.addTestSuite(TimeInstantTest.class);
		suite.addTestSuite(TimeSpanTest.class);
		suite.addTestSuite(TimeOperationsInteractionTest.class);

		
		return suite;
		
	}

}