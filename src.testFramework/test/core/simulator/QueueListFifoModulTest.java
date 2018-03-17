package test.core.simulator;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.implementation.TestEntity;

import desmoj.core.simulator.QueueListFifo;
import desmoj.core.simulator.QueueListStandard;

/**
 * This Class tests the implementation of QueueListStandard
 * with the Fifo (first in first out) method. It contains only one test but 
 * derives many others from the classes and
 * the test classes of the classes named below.
 * 
 * @author Clara Bluemm, Sascha Winde
 * 
 *@see QueueListStandard
 *@see QueueList
 *@see QueueBased
 *
 */
public class QueueListFifoModulTest extends QueueListStandardModulTest
{

	private QueueListFifo queue;
	private TestEntity enty1;
	private TestEntity enty2;
	private TestEntity enty3;
	private TestEntity enty4;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.queue = new QueueListFifo();
		super.setUp();
		this.enty1 = new TestEntity(super.model, "enty1", false);
		this.enty2 = new TestEntity(super.model, "enty2", false);
		this.enty3 = new TestEntity(super.model, "enty3", false);
		this.enty4 = new TestEntity(super.model, "enty4", false);
	}
	
	/**
	 * Tests the method to insert an entity into
	 * the Queue.
	 */
	@Test
	public void testInsert() {
		enty2.setPriority(2);
		enty3.setPriority(3);
		queue.insert(enty1);
		queue.insert(enty2);
		queue.insert(enty3);
		queue.insert(enty4);
//		System.out.println(queue.get(0));
		assertEquals(queue.get(0), enty3);
		assertEquals(queue.get(1), enty2);
		assertEquals(queue.get(2), enty1);
		assertEquals(queue.get(3), enty4);
		//insert an entity twice
		queue.insert(enty4);
		//insert null-entity
		enty1 = null;
		queue.insert(enty1);
	}

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
