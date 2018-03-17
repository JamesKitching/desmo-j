package test.core.statistic;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import test.implementation.TestModel;
import test.implementation.TestStatisticObject;

/**
 * Tests the class StatisticObject. It is the superclass
 * of the other statistical classes, which collect data.
 * Since it is declared as abstract this test class is
 * abstract as well. It is implemented by StatisticObjectTest
 * 
 * @see core.statistic.StatisticObject
 * @author Sascha Winde, Clara Bluemm
 *
 */
public abstract class StatisticObjectModulTest extends TestCase{
    
    TestModel model;
    TestStatisticObject stats;

    /**
     * Sets up the testfixture before every test.
     */
    @Before
    public void setUp() throws Exception {
        
        this.model = new TestModel();
        this.stats = new TestStatisticObject(model, "First Test Statistic Object", false, true);
        
    }

    /**
     * Tests whether the trace can be switched on.
     */
    public void testTrace()
    {
        assertTrue(stats.traceIsOn());
        stats.traceOff();
        assertFalse(stats.traceIsOn());
    }
    
    /**
     * Destroys the testfixture after every test.
     */
    @After
    public void tearDown() throws Exception {
    }

}
