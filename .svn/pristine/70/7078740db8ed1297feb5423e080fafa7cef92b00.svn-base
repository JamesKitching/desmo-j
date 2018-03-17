package test.core.simulator;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.implementation.TestEntity;
import desmoj.core.simulator.QueueBased;
import desmoj.core.simulator.QueueList;
import desmoj.core.simulator.QueueListStandard;
import desmoj.core.simulator.QueueListLifo;

/**
 * In this class we test the class QueueListLifo. This class 
 * has just one method. Mind the derived methods from the classes
 * below. All these are part of the package desmoj.core.simulator.
 * 
 * @see QueueBased
 * @see QueueList
 * @see QueueListStandard
 * @see QueueListFifo
 * 
 * @author Clara Bluemm, Sascha Winde
 *
 */
public class QueueListLifoModulTest extends QueueListStandardModulTest
{

	public TestEntity enty1;
	public TestEntity enty2;
	public TestEntity enty3;
	public TestEntity enty4;
	public QueueListLifo queue;

	/**
	 * Set-Up for Testing
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		
	}
	
	@Before
	public void setUp() throws Exception{
		this.queue = new QueueListLifo();
		super.setUp();
//		queue.setQueueBased(new Queue(super.model, "HelpBase", false, false));
		this.enty1 = new TestEntity(super.model, "enty1", false);
		this.enty2 = new TestEntity(super.model, "enty2", false);
		this.enty3 = new TestEntity(super.model, "enty3", false);
		this.enty4 = new TestEntity(super.model, "enty4", false);
	}
	
	/**
	 * Tests the method, which inserts entities into the Queue
	 */
	@Test
	public void testInsert()
	{
		enty2.setPriority(2);
		enty3.setPriority(3);
		queue.insert(enty1);
		queue.insert(enty2);
		queue.insert(enty3);
		queue.insert(enty4);
		assertEquals(queue.get(0), enty3);
		assertEquals(queue.get(1), enty2);
		assertEquals(queue.get(2), enty4);
		assertEquals(queue.get(3), enty1);
		//insert an entity twice
		queue.insert(enty4);
//		} catch (Exception e) {
//			String s = e.toString();
//			assertEquals(s.endsWith("method 'contains(Entity e)'."), true);
//		}
		//insert null-entity
		enty1 = null;
//		try {
		queue.insert(enty1);
//		} catch (Exception e) {
//			String t = "hehe";
//			System.out.println(t);
//			assertEquals(t.endsWith("Be sure to only use valid references."),false);
//		}
	}
	
	/**
	 * This method returns the queue to test 
	 * 
	 */
	@Override
	public QueueListStandard getTyp() {
		return queue;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}


}
