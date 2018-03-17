package test.suites;


import junit.framework.Test;
import junit.framework.TestSuite;
import test.core.statistic.AccumulateTest;
import test.core.statistic.AggregateTest;
import test.core.statistic.BoolStatisticTest;
import test.core.statistic.CounterTest;
import test.core.statistic.HistoGramTest;
import test.core.statistic.StatisticObjectTest;

/**
 * Test Suite, which runs every Test, related to the package core.statistic
 * 
 * @author Sascha Winde, Clara Bluemm
 *
 */
public class RunStatisticTests {


		public static Test suite()
		{
			TestSuite suite = new TestSuite();
			suite.addTestSuite(AccumulateTest.class);
			suite.addTestSuite(AggregateTest.class);
			suite.addTestSuite(BoolStatisticTest.class);
			suite.addTestSuite(CounterTest.class);
			suite.addTestSuite(HistoGramTest.class);
			suite.addTestSuite(StatisticObjectTest.class);
			return suite;
			
		}
}