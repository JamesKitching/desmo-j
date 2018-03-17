package test.core.simulator;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import test.implementation.TestModel;
import test.implementation.TestSubModel;
import desmoj.core.simulator.Experiment;

/**
 * this abstract class tests the Model Class on it's own.
 * 
 * @author Sascha Winde, Clara Bluemm
 *
 */
public abstract class ModelModulTest extends TestCase{

	TestModel model;
	TestSubModel subModel;
	Experiment experiment;
	Experiment experiment2;
	
	@Before
	public void setUp() throws Exception {
		
		this.model = new TestModel();
		this.experiment = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);
		model.connectToExperiment(experiment);
		
		this.subModel = new TestSubModel(model);
		this.experiment2 = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);
		subModel.connectToExperiment(experiment2);
	}

	/**
	 * This test checks if the model it connected to an experiment.
	 */
	public void testExperiment()
	{
		assertTrue(experiment == model.getExperiment());
		assertTrue(model.isConnected());
	}
	
	/**
	 * This test checks the model hierarchie to be correct.
	 */
	public void testModelHierarchie()
	{
		assertTrue(model.hasSubModels());
		assertTrue(model.isMainModel());
		assertFalse(model.isSubModel());
		assertTrue(subModel.isSubModel());
		assertFalse(subModel.isMainModel());
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
