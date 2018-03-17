package test.core.statistic;



import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.statistic.ConfidenceCalculator;

public class TallyTest {

    private ConfidenceCalculator tally;

    @Before
    public void setUp() throws Exception {
        Experiment experiment = new Experiment("testcase");
        Model model = new Model(null, null, false, false) {

            @Override
            public void init() {
                // TODO Auto-generated method stub

            }

            @Override
            public void doInitialSchedules() {
                // TODO Auto-generated method stub

            }

            @Override
            public String description() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        model.connectToExperiment(experiment);
        tally = new ConfidenceCalculator(model, "test", false, false);
    }

    @Test
    public void testConfidence() {
        tally.update(1.55);
        for (int i = 0; i < 5; i++) {
            tally.update(1.65);
        }
        for (int i = 0; i < 49; i++) {
            tally.update(1.75);
        }
        for (int i = 0; i < 53; i++) {
            tally.update(1.85);
        }
        for (int i = 0; i < 15; i++) {
            tally.update(1.95);
        }
        tally.update(2.05);
        tally.setConfidenceLevel(0.9);
        assertEquals(tally.getLowerConfidenceInterval(), 1.803, 0.1);
        assertEquals(tally.getUpperConfidenceInterval(), 1.825, 0.1);
    }

    @After
    public void tearDown() throws Exception {
    }
}