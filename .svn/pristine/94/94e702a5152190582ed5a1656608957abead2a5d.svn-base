package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.simulator.*;

/**
 * Truck is the super class for all the trucks (small and large) coming 
 * to the quarry to be loaded with stones.
 * A detailed description of this quarry model is described in [Schn98] 
 * pages 171 following.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public abstract class Truck extends SimProcess 
{

//   ******   attributes   ******

	/**
	* The queue where the truck (slave) has to wait for a digger (master) 
	* to cooperate with.
	*/
	protected WaitQueue rendezvousWithDigger;
	
	/**
	* The time the truck has arrived at the quarry.
	*/
	private TimeInstant arrivalTime;

	/**
	* The remaining capacity that has to be loaded for this truck.
	*/
	private double remainingCapacity;
	

//   ******   methods   ****** 

/**
 * Constructs a new truck (SimProcess).
 * @param owner desmoj.Model : The model this truck process belongs to
 * @param name String : The name of this truck.
 * @param capacity double : The capactiy of this truck
 * @param lookingForDigger desmoj.WaitQueue : The queue where the truck  
 * (slave) has to wait for a digger (master) to cooperate with.
 */
	public Truck(Model owner,
								String name,
								double capacity,
								WaitQueue lookingForDigger) 
	{
		super(owner, name, true);	// call the super class' constructor
	
		// check and set the capactiy of this truck
		if ( capacity > 0.0 )
		{	this.remainingCapacity	=	capacity;	}
		else
		{	this.remainingCapacity = -capacity;	}
		
		// get hold of the WaitQueue where to meet the digger
		this.rendezvousWithDigger = lookingForDigger;		
	}
	/** 
	 * Sets the arrival time of this truck. That is the time this truck  
	 * has arrived at the quarry.
	 */
	 protected void arrive()
	 {	
	 		this.arrivalTime = presentTime();	
	 }
	/**
	 * Sets the remaining capacity of this truck to zero.
	 */
	public void clearCapacity() 
	{
		this.remainingCapacity = 0;
	}
	/**
	 * Returns the point in simulation time this truck has entered the
	 * quarry.
	 * @return desmoj.SimTime : The time this truck has entered the quarry.
	 */
	public TimeInstant getArrivalTime() 
	{
		return arrivalTime;
	}
	/**
	 * Returns the time this truck has waited since its arrival at the quarry.
	 * @return desmoj.SimTime : The time this truck has waited since its 
	 * arrival at the quarry. 
	 */
	public TimeSpan getDelay() 
	{
		return TimeOperations.diff( presentTime(), arrivalTime );
	}
	/**
	 * Returns the remaining capacity of this truck.
	 * @return double : The remaining capacity of this truck.
	 */
	public double getRemainingCapacity() 
	{
		return remainingCapacity;
	}
	/**
	 * The lifeCycle describes all possible activities that a truck follows
	 * during his existence in the quarry. Has to be implemented in detail 
	 * in the derived subclasses for small and large trucks. 
	 */
	public abstract void lifeCycle() throws SuspendExecution ;
	/**
	 * Reduces the remaining capacity of this truck of the load loaded so far.
	 * @param load double : The load loaded on this truck, reducing the 
	 * remaining capacity.
	 */
	public void load( double load ) 
	{
		this.remainingCapacity -= load;
	}
}		// end class Truck
