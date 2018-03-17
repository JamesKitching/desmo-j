package test.core.simulator;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import desmoj.core.simulator.QueueBased;
import desmoj.core.simulator.QueueListFifo;
import desmoj.core.simulator.QueueListStandard;

/**
 * In this class we test the class QueueList. This class 
 * has just two methods but declares many as abstract. These get implementd
 * in QueueListStandard.
 * Mind the derived methods from the classes
 * named below. All these are part of the package desmoj.core.simulator.
 * 
 * @see QueueBased
 * @see QueueListStandard
 * @see QueueListLifo
 * @see QueueListFifo
 * 
 * @author Clara Bluemm, Sascha Winde
 *
 */
public abstract class QueueListModulTest extends QueueBasedModulTest
{

	@BeforeClass
	public void setUpBeforeClass() throws Exception{
//		super.setUpBeforeClass();
	}
	/**
	 * Set-Up for Testing
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Returns the <code>QueueBased</code> object the <code>QueueList</code>
	 * serves as a queue implementation for.
	 */
	@Ignore
	public void getQueueBased(){
		//not necessary, since it returns an object only
	}
	
	/**
	 * Sets the client queue for which the entities are stored. Is needed,
	 * because this can not be done in the no-arg constructor.
	 */
	@Ignore
	public void setQueueBased(){
		//not necessary, since it sets an object only
	}
	
	@After
	public void tearDown() throws Exception{
		
	}
}	
