package test.core.simulator;

import junit.framework.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import test.implementation.TestModel;
import desmoj.core.report.Reporter;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.QueueBased;
import desmoj.core.simulator.QueueListStandard;
import desmoj.core.simulator.Scheduler;
import desmoj.core.simulator.SimClock;

/**
 * This TestCase tests desmoj.core.simulator.QueueBased
 * with a ModulTest.
 *
 * @author Clara Bluemm, Sascha Winde
 * @see desmoj.core.simulator.QueueBased
 */
public abstract class QueueBasedModulTest extends TestCase
{
	/**
	 * Implemented in QueueListLifoModulTest or
	 * QueueListFifoModulTest. 
	 * @return QueueListStandard
	 * 			the Queue to test with
	 */
	public abstract QueueListStandard getTyp();

	protected TestModel model;
	protected Experiment experiment;
	protected SimClock clock;
	protected Scheduler scheduler;
	protected QueueBased basedQueue1;

	@BeforeClass
	public void setUpBeforeClass() throws Exception{
		System.out.println("SetUp QueueBased");
	}

	/**
	 * Sets up the Objects for testing.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//experiment has to be created to avoid NullpointExcepions
		this.experiment = new Experiment("Test Experiment", 
				java.util.concurrent.TimeUnit.SECONDS, 
				java.util.concurrent.TimeUnit.HOURS, null);
		//a model has to be created to avoid NullpointerExceptions
		this.model = new TestModel();
		//and both have to be connected to avoid NullPointerException
		model.connectToExperiment(experiment);
		this.clock = experiment.getSimClock();
		this.scheduler = experiment.getScheduler();
		//this is the QueueBased which is used on the Site of
		//the experiment to have a queue
		getTyp().setQueueBased(new Queue(model, "HelpBase", false, false));
		this.basedQueue1 = getTyp().getQueueBased();
	}
	
	/**
	 * This method updates the statistic value every time when
	 * an element is inserted in the queue underneath.
	 */
	@Test
	public void testAddItem() {
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		assertEquals(basedQueue1.length(), 5);
	}

	/**
	 * Tests whether the counting and calculating of the
	 * average length is correct.
	 */
	@Test
	public void testAverageLength() 
	{
		basedQueue1.reset();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		double x = basedQueue1.averageLength();
		assertTrue(-1 == x);
		
	}

	/**
	 * 
	 */
	@Ignore
	public void testAverageWaitTime() {
		// TODO: Wird auch nicht funktionieren, weil man auf die Zeit
		// zugreifen muss

	}

	/**
	 * Updates the statistics when an element is removed.
	 */
	@Test
	public void testDeleteItem() {
		basedQueue1.reset();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		basedQueue1.addItem();
		assertEquals(basedQueue1.length(), 5);
//		basedQueue1.deleteItem(experiment);
//		assertEquals(basedQueue1.length(), 3);
	}

	/**
	 * Returns the maximum possible number of entities in the underlying queue.
	 */
	@Ignore
	public void getQueueLimit() {
		//no need, since it just returns a variable
	}

	/**
	 * Returns the current length of the underlying queue.
	 */
	@Ignore
	public void testLength(){
		//no neeed
	}

	/**
	 * Returns the maximum length of the underlying queue since the last reset.
	 */
	@Ignore
	public void maxLength() {
		//no need
	}

	/**
	 * Returns the point of simulation time with the maximum number of objects
	 * waiting inside the underlying queue. The value is valid for the period
	 * since the last reset.
	 */
	@Ignore
	public void maxLengthAt() {
		//no need
	}

	/**
	 * Returns the maximum duration in simulation time that an object has spent
	 * waiting inside the underlying queue. The value is valid for the period
	 * since the last reset.
	 */
	@Ignore
	public void maxWaitTime() {
		//no need
	}

	/**
	 * Returns the point of simulation time when the object with the maximum
	 * waiting time exited the underlying queue. The value is valid for the
	 * period since the last reset.
	 */
	@Ignore
	public void maxWaitTimeAt() {
		 //no need
	}

	/**
	 * Returns the minimumn length of the underlying queue since the last reset.
	 * 
	 * @return int : The minimum queue length since last reset
	 */
	public void minLength() {
	}

	/**
	 * Returns the point of simulation time with the minimum number of objects
	 * waiting inside the underlying queue. The value is valid for the period
	 * since the last reset.
	 */
	@Ignore
	public void minLengthAt() {
		// no need
	}

	/**
	 * Returns a boolean flag telling if the underlying queue implementation
	 * should issue own warnings or not. The warnings from the queue
	 * implementation (<code>QueueList</code>) are needed for debugging
	 * purposes.
	 */
	public void qImpWarn() {
		//no need
	}

	/**
	 * Resets all statistical counters to their default values. The mininum and
	 * maximum length of the queue are set to the current number of queued
	 * objects.
	 */
	@Test
	public void testReset() {
		//TODO
	}

	/**
	 * Method switches on warnings issued from the underlying queue
	 * implementation if parameter given is <code>true</code>. Warnings are
	 * suppressed if <code>false</code> is given. This method is used for
	 * internal debugging only.
	 */
	public void setQueueImpWarning(boolean warnFlag) {
		// no need: for debugging only
	}

	/**
	 * Returns the standard deviation of the queue's length. Value is weighted
	 * over time.
	 */
	@Test
	public void StdDevLength() {
		//TODO
	}

	/**
	 * Returns the standard deviation of the queue's objects waiting times.
	 */
	@Test
	public void StdDevWaitTime() {
		//TODO
	}

	/**
	 * Updates the parts of the statistics used by both addItem and deleteItem.
	 */
	protected void updateStatistics() {
		//declared as protected
	}

	/**
	 * Returns the number of objects that have passed through the queue without
	 * spending time waiting.
	 */
	public void zeroWaits() {
		//TODO
	}
	
	/**
	 * Tests whether the QueueBased returns and creates the right
	 * reporter.
	 */
	@Test
	public void testCreateReporter()
	{
		Reporter reporter1 = this.basedQueue1.createDefaultReporter();
		Reporter reporter2 = this.basedQueue1.createDefaultReporter();
		assertNotSame(reporter1, reporter2);
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}
