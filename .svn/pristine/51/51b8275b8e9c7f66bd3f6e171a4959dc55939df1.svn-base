package desmoj.demo.eventListTest;

import desmoj.core.simulator.*;

public class TestModel extends Model 
{


	public TestModel()	// capacity of the large truck
	{		
		super(null, "Test", true, true);

		// 
	}

	/**
	 * Describes the system this model intends to simulate.
	 * @return java.lang.String : The String describing the model
	 */
	public String description() {
		return "Just a test";
	}

	/**
	 * Makes the diggers and starts them at simulation time 0.0 and also 
	 * activates the first small and large truck, to setup the model into a 
	 * defined state to start with.
	 */
	public void doInitialSchedules() 
	{
		 new AnEvent(this, "30A").schedule(new TimeInstant(30));
		 new AnEvent(this, "30B").schedule(new TimeInstant(30));
		 new AnEvent(this,  "10").schedule(new TimeInstant(10));
		 new AnEvent(this,  "20").schedule(new TimeInstant(20));
		 
		 AnEvent high1 = new AnEvent(this, "30_high1"); high1.setSchedulingPriority(10);
		 high1.schedule(new TimeInstant(30));
		 AnEvent high2 = new AnEvent(this, "30_high2"); high2.setSchedulingPriority(10);
		 high2.schedule(new TimeInstant(30));
		 
		 AnEvent high_attach = new AnEvent(this, "30_high_attach"); 
		 high_attach.scheduleAfter(high1);
		 
		 AnEvent med = new AnEvent(this, "30_med"); med.setSchedulingPriority(5);
		 med.schedule(new TimeInstant(30));

		 AnEvent super_high = new AnEvent(this, "30_super_high"); super_high.setSchedulingPriority(50);
		 super_high.schedule(new TimeInstant(30));


	}

	/**
	 * The main method to start an experiment with this model.
	 * @param args java.lang.String[] : The arguments are not used in this model
	 */
	public static void main(String args[]) 
	{
		Experiment exp = new Experiment("test", true);
		//exp.setEventList(EventTreeList.class);
		exp.setEventList(SortedMapEventList.class);

		TestModel mod = new TestModel ();
		mod.connectToExperiment(exp);	
		exp.stop( new TimeInstant(240) );
		exp.start();
		exp.finish();
	}

	@Override
	public void init() {
		// nothing
	}
}
