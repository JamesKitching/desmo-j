package test.core.dist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import desmoj.core.dist.Series;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;

public class SeriesTest {

	private Series<Integer> series;

	// Sample cases

	@Before
	public void startup() {
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
		
		series = new Series<Integer>(model, "test", false, false);
		series.add(1);
		series.add(2);
		series.add(3);
		series.add(4);
		series.add(5);
		series.add(6);
		series.add(7);
		series.add(8);
	}

	@Test
	public void testSeries() {
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		assertEquals(series.sample(), new Integer(4));
		assertEquals(series.getMean(), 2.5, 0.001);
		assertEquals(series.getStandardDegression(), 1.1180, 0.001);
		assertEquals(series.sample(), new Integer(5));
		assertEquals(series.sample(), new Integer(6));
		assertEquals(series.sample(), new Integer(7));
		assertEquals(series.sample(), new Integer(8));
		assertEquals(series.sample(), null);
		series.setRepeating(true);
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		series.setReverse(true);
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(8));
		series.setReverse(false);
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
	}

	@Test
	public void normalUse() {
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		assertEquals(series.sample(), new Integer(4));
		assertEquals(series.sample(), new Integer(5));
		assertEquals(series.sample(), new Integer(6));
		assertEquals(series.sample(), new Integer(7));
		assertEquals(series.sample(), new Integer(8));
		assertNull(series.sample());
	}

	@Test
	public void falseDirection() {
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		series.setReverse(true);
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(1));
		assertNull(series.sample());
		series.setReverse(false);
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		assertEquals(series.sample(), new Integer(4));
		series.setReverse(true);
		assertEquals(series.sample(), new Integer(3));
		series.setReverse(true);
		assertEquals(series.sample(), new Integer(2));
	}

	@Test
	public void testRepeat() {
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		assertEquals(series.sample(), new Integer(4));
		assertEquals(series.sample(), new Integer(5));
		assertEquals(series.sample(), new Integer(6));
		assertEquals(series.sample(), new Integer(7));
		assertEquals(series.sample(), new Integer(8));
		series.setRepeating(true);
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		series.setReverse(true);
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(8));
		series.setReverse(false);
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		series.clearList();
		assertNull(series.sample());
	}

	@Test
	public void testAverage() {
		assertEquals(series.sample(), new Integer(1));
		assertEquals(series.sample(), new Integer(2));
		assertEquals(series.sample(), new Integer(3));
		assertEquals(series.sample(), new Integer(4));
		assertEquals(series.getMean(), 2.5, 0.001);
		assertEquals(series.getStandardDegression(), 1.1180, 0.001);
	}

}
