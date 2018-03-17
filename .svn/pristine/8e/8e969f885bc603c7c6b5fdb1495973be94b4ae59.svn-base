package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.simulator.*;

/**
 * LargeDigger is the class for all the large diggers working in the quarry
 * to load the incoming trucks with stones. Large diggers can load only 
 * large trucks. When a large digger ends his work and a small digger is 
 * working on a large truck the large digger will interrupt the small 
 * digger and take his work over (the big one is much faster).
 * A detailed description of this quarry model is described in [Schn98] 
 * pages 171 following.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class LargeDigger extends Digger 
{

//   ******   attributes   ******

	/**
	* The interrupt code stating that a large digger has completed his work 
	* and is now free to load another large truck
	*/
	private InterruptCode largeDiggerFree;
	
	/**
	* The condition to make sure that this large digger is loading only large
	* trucks.
	*/
	private OnlyLargeTruckCond onlyLargeTruck;
	
	/**
	* The condition to test whether the given truck has been interrupted or
	* not.
	*/
	private OnlyInterruptedTruckCond onlyInterruptedTruck;
	

//   ******   methods   ****** 

/**
 * Constructs a new LargeDigger with the name "L-Digger".
 * @param owner desmoj.Model : The model this digger process belongs to
 * @param name String : The name of this digger.
 * @param lookingForTruck desmoj.WaitQueue : The queue where the digger  
 * (master) has to wait for a truck (slave) to cooperate with.
 * @param loadRate double : The load rate of this truck in tons per hour.
 * @param interruptableDiggers desmoj.ProcessQueue : The Queue where small 
 * diggers are stored when loading a large truck. They will be interrupted
 * when a small truck arrives or a large digger is available again.
 */
	public LargeDigger(Model owner,
											WaitQueue lookingForTruck,
											double loadRate,
											ProcessQueue<SmallDigger> interruptableDiggers,
											InterruptCode lgDigFree) 
	{
		// call the super class' constructor (Digger)
		super(owner, "L-Digger", 
						lookingForTruck, loadRate, interruptableDiggers);
	
		// make a Condition object for large trucks
		onlyLargeTruck = new OnlyLargeTruckCond(owner);
		
		// make a Condition object for interrupted trucks
		onlyInterruptedTruck = new OnlyInterruptedTruckCond(owner);
		
		// get hold of the InterruptCode
		this.largeDiggerFree = lgDigFree;
	}
	/**
	 * The beginCoop describes what has to be done when a large digger  
	 * begins the cooperation with a given truck (that means he is starting  
	 * to load the truck). That is to update the statistics.
	 * @param truck Truck : The Truck that will be loaded by this large 
	 * digger. 
	 */
	public void beginCoop( Truck truck )
	{
		// update statistic for digger utilization
		// increment the working large diggers
		((QuarryModel)getModel()).incrementWorkingLDig();				
	}
	/**
	 * The endCoop describes what has to be done when a large digger ends  
	 * the cooperation with a truck (that means he stops loading the truck).
	 * That is to update the statistics.
	 * @param truck Truck : The Truck that has been loaded by this large
	 * digger. 
	 */
	public void endCoop( Truck truck )
	{
		// update statistic for digger utilization
		// decrement the working large diggers
		((QuarryModel)getModel()).decrementWorkingLDig();				
	}
	/**
	 * The lifeCycle describes all possible activities that a large digger 
	 * follows during his existence in the quarry. 
	 */
	public void lifeCycle() throws SuspendExecution
	{
		// large diggers have a higher priority in the WaitQueue
	    setQueueingPriority(1);
		
		while ( true )		// endless loop 
		{
			// if no small digger is working on a large truck
			if ( interruptableDiggers.isEmpty() )
			{
				// look for a large truck to load
				rendezvousWithTruck.cooperate( loading, onlyLargeTruck );
			}
			else	// a small digger is working on a large truck
			{
				// interrupt the small digger loading the large truck with the 
				// reason that the large digger is free now
				interruptableDiggers.first().interrupt( largeDiggerFree );
				
				// look for a large interrupted truck to load
				rendezvousWithTruck.cooperate( loading, onlyInterruptedTruck );
			}
		}	// end while
	}
}		// end class LargeDigger
