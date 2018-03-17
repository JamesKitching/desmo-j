package test.core.statistic;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import test.implementation.TestModel;

import desmoj.core.statistic.Count;

/**
 * This class is the test of class count, which is used
 * to simple count something (e.g. some kind of objects)
 * during an experiment.
 * 
 * @see core.statistic.count
 * @author Sascha Winde, Clara Bluemm
 *
 */
public class CounterTest extends TestCase{
    
    Count count;
    TestModel model;

    /**
     * Setting up the testfixture.
     */
    @Before
    public void setUp() throws Exception {
        this.model = new TestModel();
        this.count = new Count(model, "Test Counter", false, false);
    }

    /**
     * Tests the method getValue. It is supposed to return the 
     * actual value.
     */
    public void testValue()
    {
        assertTrue(0 == count.getValue());
        count.update(10);
        assertFalse(0 == count.getValue());
        assertTrue(10 == count.getValue());
    }
    
    /**
     * Tests the method getMaximum, which is supposed
     * to return the highest value of the count object so far.
     */
    public void testMax()
    {
        assertTrue(0 == count.getMaximum());
        count.update(10);
        assertFalse(0 == count.getMaximum());
        assertTrue(10 == count.getMaximum());
        count.update(5);
        assertFalse(10 == count.getMaximum());
        assertTrue(15 == count.getMaximum());
    }
    
    /**
     * Tests the method getMinimum, which is supposed
     * to return the lowest value of the count object so far.
     */
    public void testMin()
    {
        assertTrue(0 == count.getMinimum());
        count.update(10);
        assertFalse(10 == count.getMinimum());
        assertTrue(0 == count.getMinimum());
    }
    
    /**
     * Destroys the testfixture after every test.
     */
    @After
    public void tearDown() throws Exception {
    }

}