package test.core.simulator;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import test.implementation.TestModel;

import desmoj.core.report.MessageReceiver;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeFormatter;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.UTCTimeFormatter;

/**
 * This class tests the experiment Class on it's own.
 * Especially the functionality to set values to model relevant instances.
 * 
 * @author Sascha
 *
 */
public class ExperimentTest extends TestCase{
	
	Experiment experiment;
	TestModel model;

	@Before
	public void setUp() throws Exception {
		
		this.model = new TestModel();
		this.experiment = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);
		model.connectToExperiment(experiment);
	}
	
	/**
	 * The Debugmode is tested.
	 * Therefore it is tested off, and tested after being set on.
	 */
	public void testDebug()
	{
		assertFalse(experiment.debugIsOn());
		experiment.debugOn(new TimeInstant(0));
		experiment.start();
		assertTrue(experiment.debugIsOn());
	}
	
	/**
	 * Tests the Delay in milliseconds of the experiment.
	 */
	public void testDelay()
	{
		assertTrue(0 == experiment.getDelayInMillis());
		experiment.setDelayInMillis(10);
		assertTrue(10 == experiment.getDelayInMillis());
	}
	
	
	/**
	 * This Test gets the experiments name and sets it as Description.
	 * Afterwards it is tested, if the given description equals the name.
	 */
	public void testDescription()
	{
		String test = getName();
		experiment.setDescription(getName());
		assertEquals(test, experiment.getDescription());
	}
	
	/**
	 * This Test checks the ExecutionSpeedRate
	 */
	public void testExecutionSpeedRate()
	{
		assertTrue(0 == experiment.getExecutionSpeedRate());
		experiment.setExecutionSpeedRate(10);
		assertTrue(10 == experiment.getExecutionSpeedRate());
	}
	
	/**
	 * This Test checks the start and stop function of the experiment.
	 * Starttime has to be 0 and stoptime is 100.
	 * 
	 * @throws InterruptedException
	 */
	public void testStartStopExperiment() throws InterruptedException
	{
		assertTrue(0 == experiment.STARTED);
		experiment.stop(new TimeInstant(100));
		experiment.start();
		assertTrue(100 == experiment.getStopTime().getTimeAsDouble());
		assertTrue(1 == experiment.STOPPED);
	}
	
	/**
	 * this test checks if the experiment is connected to a model.
	 */
	public void testModel()
	{
		assertEquals(model, experiment.getModel());
		assertTrue(experiment.isConnected());
	}
	
	/**
	 * this test checks if the experiment is connected to a scheduler.
	 */
	public void testScheduler()
	{
		assertNotNull(experiment.getScheduler());
	}

	/**
	 * this test checks if the experiment is connected to a ResourceDB.
	 */
	public void testResourceDB()
	{
		assertNotNull(experiment.getResourceDB());
	}
	
	/**
	 * this test checks if the experiment is connected to a SimClock.
	 */
	public void testSimClock()
	{
		assertNotNull(experiment.getSimClock());
	}
	
	/**
	 * this test checks if the experiment randomizes Events.
	 */
	public void testRandomizingEvents()
	{
		assertFalse(experiment.isRandomizingConcurrentEvents());
		experiment.randomizeConcurrentEvents(true);
		assertTrue(experiment.isRandomizingConcurrentEvents());
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
