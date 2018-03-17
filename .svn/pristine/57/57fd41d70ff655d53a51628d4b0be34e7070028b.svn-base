package test.core.simulator;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import desmoj.core.simulator.Experiment;

import test.implementation.TestExternalEvent;
import test.implementation.TestModel;

/**
 * This Class simply tests if an Event is external or not.
 * Further functionality is tested in the EventModulTest Class.
 * 
 * @author Sascha Winde, Clara Bluemm
 *
 */
public abstract class ExternalEventModulTest extends TestCase{

	TestModel model;
	TestExternalEvent externalEvent;
	@Before
	public void setUp() throws Exception {
		
		this.model = new TestModel();
		Experiment experiment = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);
		model.connectToExperiment(experiment);
		this.externalEvent = new TestExternalEvent(model, "First Test ExternalEvent", false);
	}

	/**
	 * This checks if the event is external or not.
	 */
	public void testIsExternal()
	{
		assertTrue(externalEvent.isExternal());
	}
	/**
	 * This tests checks the given name.
	 */
	public void testName()
	{
		assertEquals("First Test ExternalEvent#1", externalEvent.getName());
	}
	
	
	@After
	public void tearDown() throws Exception {
	}

}
