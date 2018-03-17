package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.simulator.*;
import desmoj.core.statistic.*;


/**
 * SmallDigger is the class for all the small diggers working in the quarry
 * to load the incoming trucks with stones. Small diggers can load small 
 * and large trucks but small trucks can only be loaded by small diggers. 
 * Therefore a small digger working on a large truck will be interrupted 
 * when a small truck arrives or when a large digger can take his work over 
 * (the big one is much faster).
 * A detailed description of this quarry model is described in [Schn98] 
 * pages 171 following.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class SmallDigger extends Digger 
{

//   ******   attributes   ******

	/**
	* The number of small diggers having completly loaded a large truck.
	*/
	private Count completlyLoadedLT;
	

//   ******   methods   ****** 

/**
 * Constructs a new SmallDigger with the name "S-Digger".
 * @param owner desmoj.Model : The model this digger process belongs to
 * @param name String : The name of this digger.
 * @param lookingForTruck desmoj.WaitQueue : The queue where the digger  
 * (master) has to wait for a truck (slave) to cooperate with.
 * @param loadRate double : The load rate of this truck in tons per hour.
 * @param interruptableDiggers desmoj.ProcessQueue : The Queue where small 
 * diggers are stored when loading a large truck. They will be interrupted
 * when a small truck arrives or a large digger is available again.
 */
	public SmallDigger(Model owner,
											WaitQueue lookingForTruck,
											double loadRate,
											ProcessQueue<SmallDigger> interruptableDiggers,
                                            Count sDCompletedLT,
											SmallDiggerUtilSuppl sDUtilSupplier) 
	{
		// call the super class' constructor (Digger)
		super(owner, "S-Digger", 
						lookingForTruck, loadRate, interruptableDiggers);
	
		// get hold of the counter for small diggers having completly loaded a 
		// large truck. 
		this.completlyLoadedLT = sDCompletedLT;	
	}
	/**
	 * The beginCoop describes what has to be done when a small digger begins 
	 * the cooperation with a given truck (that means he is starting to load 
	 * the truck). That is to update the statistics and to check whether a 
	 * small digger is loading a large truck and therefore might be 
	 * interruptable.
	 * @param truck Truck : The Truck that will be loaded by this small 
	 * digger. 
	 */
	public void beginCoop( Truck truck )
	{						
		// get the Model, cast it to a QuarryModel and increment the working 
		// small diggers
		( (QuarryModel)getModel() ).incrementWorkingSDig();				
		
		// if this small digger is working on a large truck
		if ( truck instanceof LargeTruck )
		{
			// put him in the queue of interruptable diggers
			interruptableDiggers.insert(this);	
		}
	}
	/**
	 * The endCoop describes what has to be done when a small digger ends  
	 * the cooperation with a truck (that means he stops loading the truck).
	 * That is to update the statistics and if necessary to remove the small 
	 * digger from the queue of interruptable diggers.
	 * @param truck Truck : The Truck that has been loaded by this small 
	 * digger. 
	 */
	public void endCoop( Truck truck ) throws SuspendExecution
	{		
		// get the Model, cast it to a QuarryModel and decrement the working 
		// small diggers
		( (QuarryModel)getModel() ).decrementWorkingSDig();		
		
		// if this small digger was working on a large truck
		if ( truck instanceof LargeTruck )
		{
			// remove him from the queue of interruptable diggers
			interruptableDiggers.remove(this);
			
			// has this small digger loaded the large truck completly
			if ( truck.getRemainingCapacity() <= 0 )
			{
				// update the counter for small diggers having completly loaded a 
				// large truck.  
				completlyLoadedLT.update();
				
			}	// end inner if
		}	// end outer if
	}
	/**
	 * The lifeCycle describes all possible activities that a small digger 
	 * follows during his existence in the quarry.
	 */
	public void lifeCycle() throws SuspendExecution
	{
		while ( true )		// endless loop 
		{
			// trying to load a truck
			rendezvousWithTruck.cooperate(loading);
		}
	}
}		// end class SmallDigger
