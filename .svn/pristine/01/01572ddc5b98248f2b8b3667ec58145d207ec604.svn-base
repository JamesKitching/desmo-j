/**
 * 
 */
package test.core.simulator;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import desmoj.core.simulator.EventList;
import desmoj.core.simulator.EventVectorList;

/**
 * This class represents the implementation of the event-list as 
 * a Vector. It only returns an object of this implementation
 * to its super class. All testing methods are implemented in the
 * super class EventListModulTest.
 * 
 * @author Clara Bluemm
 * 
 * @see EventListModulTest
 */
public class EventVectorListModulTest extends EventListModulTest{

	private EventVectorList list;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		list = new EventVectorList();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Returns the specified implementation of EventList:
	 * EventVectorList. This is used for testing by the super
	 * class EventListModulTest.
	 */
	@Override
	public EventList getList() {
		return this.list;
	}

}
