package test.core.simulator;

import desmoj.core.simulator.*;
import junit.framework.TestCase;

/**
 * This Class tests the SimClock on it's own.
 * 
 * @author Sascha Winde, Clara Bluemm
 *
 */
public class SimClockModulTest extends TestCase {

	private SimClock testClock;
	
	public void setUp(){
	       Experiment experiment = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);

	       
		this.testClock = new SimClock("Test Clock");
		
	}
	/**
	 * This Test checks the Simclocks given name.
	 */
	public void testName()
	{
		String name = testClock.getName();
		assertEquals("Test Clock_clock", name);
	}
	
	/**
	 * This Test checks the SimClocks given Time as double.
	 */
	public void testTime()
	{
		TimeInstant i = new TimeInstant(0);
		assertTrue(i.getTimeAsDouble() == testClock.getTime().getTimeAsDouble());
	}
	
	public void tearDown()
	{
		
	}
}
