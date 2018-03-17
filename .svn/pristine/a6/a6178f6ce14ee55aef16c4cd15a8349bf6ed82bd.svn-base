package test.core.simulator;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Experiment;
import test.implementation.TestEntity;
import test.implementation.TestModel;
import junit.framework.TestCase;

/**
 * This class tests the methods derived of the abstract
 * class Entity. 
 * 
 * @author Sascha Winde, Clara Bluemm
 *
 */
public  abstract class EntityModulTest extends TestCase {
	
	public TestModel model;
	public TestEntity entity1;
	public TestEntity entity2;
	
	/**
	 * Sets up the test fixture.
	 */
	public void setUp() throws Exception
	{
		this.model = new TestModel();
		Experiment experiment = new Experiment("Test Experiment", java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.HOURS, null);
		model.connectToExperiment(experiment);
		this.entity1 = new TestEntity(model, "First Test Entity", false);
		this.entity2 = new TestEntity(model, "Second Test Entity", false);
	}
	
	/**
	 * A priority can be set to an entity. So it is possible
	 * to make an entity more important than an other.
	 * Depending on this priority the entities are getting
	 * scheduled in the Queue. This method tests whether the
	 * method sets the priority right.
	 */
	public void testPriority()
	{
		assertTrue(0 == entity1.getPriority());
		entity1.setPriority(1);
		assertFalse(0 == entity1.getPriority());
		assertTrue(1 == entity1.getPriority());
		entity1.setPriority(-1);					//negative Priority can be set
		assertFalse(1 == entity1.getPriority());
		assertTrue(-1 == entity1.getPriority());
		assertFalse(Entity.isEqual(entity1, entity2));
		entity1.setPriority(0);
		assertTrue(Entity.isEqual(entity1, entity2));
		assertFalse(Entity.isLarger(entity1, entity2));
		entity1.setPriority(1);
		assertTrue(Entity.isLarger(entity1, entity2));
		assertTrue(Entity.isLargerOrEqual(entity1, entity2));
		entity1.setPriority(0);
		assertTrue(Entity.isLargerOrEqual(entity1, entity2));
		assertFalse(Entity.isNotEqual(entity1, entity2));
		entity1.setPriority(1);
		assertTrue(Entity.isNotEqual(entity1, entity2));
		assertTrue(Entity.isSmaller(entity2, entity1));
		entity1.setPriority(0);
		assertFalse(Entity.isSmaller(entity2, entity1));
		assertTrue(Entity.isSmallerOrEqual(entity2, entity1));
		entity1.setPriority(1);
		assertTrue(Entity.isSmallerOrEqual(entity2, entity1));

	}
	
	/**
	 * To compare different entities, they need to be 
	 * comparable. This is tested in this method.
	 */
	public void testEntityEquals()
	{
		assertFalse(entity1.equals(entity2));
		TestEntity e = entity2;
		assertTrue(e.equals(entity2));
		assertEquals(e, entity2);
		assertSame(e, entity2);
		assertFalse(e == entity1);
	}
	
	
	/**
	 * The method returns true, if it is called upon 
	 * a sim-process.
	 */
	public void testIsSimprocess()
	{
		assertFalse(entity1.isSimProcess());
	}
	
	
	/**
	 * Tests whether the entity can be renamed.
	 */
	public void testName()
	{
		assertEquals("First Test Entity#1", entity1.getName());
		entity1.rename("");
		assertFalse("First Test Entity#1" == entity1.getName());
		entity1.rename("First Test Entity");
		assertEquals("First Test Entity#2", entity1.getName());
	}
	
	public void tearDown() throws Exception
	{
	}

}
