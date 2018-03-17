package test.suites;

import test.core.simulator.EntityTest;
import test.core.simulator.EventNoteModulTest;
import test.core.simulator.EventTest;
import test.core.simulator.QueueListFifoModulTest;
import test.core.simulator.QueueListLifoModulTest;
import test.core.simulator.SchedulableTest;
import test.core.simulator.SimprocessTest;
import test.core.statistic.AggregateTest;
import test.core.statistic.CounterTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PraesentationsTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(PraesentationsTest.class.getName());
		suite.addTestSuite(EntityTest.class);
		suite.addTestSuite(SchedulableTest.class);
		suite.addTestSuite(SimprocessTest.class);
		suite.addTestSuite(EventNoteModulTest.class);
		suite.addTestSuite(EventTest.class);
		suite.addTestSuite(QueueListLifoModulTest.class);
		suite.addTestSuite(QueueListFifoModulTest.class);
//		suite.addTestSuite(SchedulerInteractionTest.class);
		suite.addTestSuite(CounterTest.class);
		suite.addTestSuite(AggregateTest.class);

		return suite;
	}
}