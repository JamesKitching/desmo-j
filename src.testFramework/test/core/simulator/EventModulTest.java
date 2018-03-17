package test.core.simulator;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import test.implementation.TestEventAbstract;
import test.implementation.TestModel;
import desmoj.core.simulator.Experiment;


/**
 * This class tests the methods derived of the abstract
 * class Event. 
 * 
 * @author Sascha Winde, Clara Bluemm
 *
 */
public abstract class EventModulTest extends TestCase{

	TestModel model;
	TestEventAbstract event;
	
	/**
	 * Sets up the test fixture.
	 */
	@Before
	public void setUp() throws Exception {
		this.model = new TestModel();
		Experiment experiment = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);
		model.connectToExperiment(experiment);
		
		this.event = new TestEventAbstract(model, "First Test Event", false);
	}

	
	/**
	 * This test checks the given name.
	 */
	public void testName()
	{
		assertEquals("First Test Event#1", event.getName());
	}
	
	/**
	 * This Test checks the actual RealTimeConstraint
	 */
	public void testRealTimeConstraint()
	{
		assertTrue(10 == event.getRealTimeConstraint());
	}
	
	/**
	 * This Test checks, if an event is not scheduled.
	 */
	public void testIsNotScheduled()
	{
		assertFalse(event.isScheduled());
	}
	
	
	@After
	public void tearDown() throws Exception {
	}

}
