package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.simulator.*;

/**
 * Digger is the super class for all the diggers (small and large) working 
 * in the quarry to load the incoming trucks with stones.
 * A detailed description of this quarry model is described in [Schn98] 
 * pages 171 following.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public abstract class Digger extends SimProcess 
{

//   ******   attributes   ******

	/**
	* The queue where the Digger (master) has to wait for a truck (slave) 
	* to cooperate with.
	*/
	protected WaitQueue rendezvousWithTruck;
	
	/**
	* The queue where the small diggers are stored when loading a large truck.
	* They can be interrupted when a small truck arrives or a large digger is
	* free.
	*/
	protected ProcessQueue<SmallDigger> interruptableDiggers;
	
	/**
	* The cooperation of the master and the slave. When the digger (master) is 
	* loading the truck (slave).
	*/
	protected Loading loading;

	/**
	* The load rate of this digger (how many tons per hour he can load on a 
	* truck).
	*/
	private double loadRate;
	

//   ******   methods   ****** 

/**
 * Constructs a new Digger (SimProcess).
 * @param owner desmoj.Model : The model this digger process belongs to
 * @param name String : The name of this digger.
 * @param lookingForTruck desmoj.WaitQueue : The queue where the digger  
 * (master) has to wait for a truck (slave) to cooperate with.
 * @param loadRate double : The load rate of this truck in tons per hour.
 * @param interruptableDiggers desmoj.ProcessQueue : The Queue where small 
 * diggers are stored when loading a large truck. They will be interrupted
 * when a small truck arrives or a large digger is available again.
 */
	public Digger(Model owner,
								String name,
								WaitQueue lookingForTruck,
								double loadRate,
								ProcessQueue<SmallDigger> interruptableDiggers) 
	{
		// call the super class' constructor
		super(owner, name, true);	// true = showing in the trace
	
		// get hold of the waitQueue where to meet the truck
		this.rendezvousWithTruck = lookingForTruck;	
		
		// make an object of the "loading" cooperation
		loading = new Loading( owner );
		
		// get hold of the queue where the interruptable diggers can be stored
		this.interruptableDiggers = interruptableDiggers;
		
		// set the load rate for this digger
		this.loadRate = loadRate;	
	}
	/**
	 * The beginCoop describes what has to be done when a digger begins the 
	 * cooperation with a given truck (that means he is starting to load the 
	 * truck). That is to update the statistics and to check whether a small 
	 * digger is loading a large truck and therefore might be interruptable.
	 * Has to be implemented in detail in the derived subclasses for small
	 * and large diggers.
	 * @param truck Truck : The Truck that will be loaded by this digger. 
	 */
	public abstract void beginCoop( Truck truck );
	/**
	 * The endCoop describes what has to be done when a digger ends the 
	 * cooperation with a truck (that means he stops loading the truck).
	 * That is to update the statistics and if necessary to remove the small 
	 * digger from the queue of interruptable diggers.
	 * Has to be implemented in detail in the derived subclasses for small
	 * and large diggers.
	 * @param truck Truck : The Truck that has been loaded by this digger. 
	 */
	public abstract void endCoop( Truck truck ) throws SuspendExecution;
	/**
	 * Returns the load rate of this digger.
	 * @return double : The load rate of this digger.
	 */
	public double getLoadRate() 
	{
			return loadRate;
	}
	/**
	 * The lifeCycle describes all possible activities that a digger follows
	 * during his existence in the quarry. Has to be implemented in detail in
	 * the derived subclasses for small and large diggers. 
	 */
	public abstract void lifeCycle() throws SuspendExecution;
}		// end class Digger
