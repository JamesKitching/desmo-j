package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.simulator.*;

/**
 * SmallTruck is the class for all small trucks coming to the quarry to be 
 * loaded with stones. Small trucks can only be loaded by small diggers.
 * If no small digger is available and a small digger is loading a large 
 * truck, that small digger will be interrupted to load the small truck
 * first.
 * A detailed description of this quarry model is described in [Schn98] 
 * pages 171 following.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class SmallTruck extends Truck 
{


 /**
	* The queue where the small diggers are stored when they are loading a 
  * large truck.They will be interrupted, because they have to load the 
  * small trucks first.
	*/
	private ProcessQueue<SmallDigger> interruptableDiggers;
	
 /**
	* The Condition to make sure that this small truck is loaded only by 
	* small diggers.
	*/
	private OnlySmallDigCond onlySmallDigger;
	
 /**
	* The interrupt code stating that a small truck has arrived which must  
	* be loaded by a small digger.
	*/
	private InterruptCode smallTruckArrived;
	
 /** 
	* The ValueSupplier calculating the total time a small truck has spent  
	* in the quarry (waiting for service and being loaded).
	*/
	private SmallTruckStaySuppl sTruckStaySupplier;
	

//   ******   methods   ****** 

/**
 * Constructs a new small truck with the name "S-Truck".
 * @param owner desmoj.Model : The model this small truck belongs to.
 * @param capacity double : The capactiy of this small truck.
 * @param lookingForDigger desmoj.WaitQueue : The queue where the truck  
 * (slave) has to wait for a digger (master) to cooperate with.
 * @param interruptableDiggers desmoj.ProcessQueue : The queue where the 
 * small diggers are stored when they are loading a large truck. They will 
 * be interrupted, because they have to load the small trucks first.
 * @param smallTruckArrived desmoj.InterruptCode : The interrupt code 
 * stating that a small truck has arrived (which might interrupt small 
 * diggers loading large trucks).
 * @param sTArrival desmoj.dist.RealDistExponential : The random  
 * distribution stream for the time span between arrivals of small trucks.
 * @param sTStay claassen_mining.SmallTruckStaySuppl : The ValueSupplier 
 * calculating how long a small truck has been staying in the quarry  
 * (waiting and being loaded)
 */
	public SmallTruck(Model owner,
											double capacity,
											WaitQueue lookingForDigger,
											ProcessQueue<SmallDigger> interruptableDiggers,
											InterruptCode smallTruckArrived,
											SmallTruckStaySuppl sTStay ) 
	{
		// call the super class' constructor
		super(owner, "S-Truck", capacity, lookingForDigger);	
		
		// ProcessQueue
		this.interruptableDiggers = interruptableDiggers;
		
		// InterruptCode
		this.smallTruckArrived = smallTruckArrived;
		
		// make a Condition object for small diggers
		onlySmallDigger = new OnlySmallDigCond(owner);
		
		// the ValueSupplier calculating the stay of a small truck in the quarry
		this.sTruckStaySupplier = sTStay;
	}
	/**
	 * The lifeCycle describes all possible activities that a small truck 
	 * follows during its existence in the quarry. 
	 */
	public void lifeCycle() throws SuspendExecution
	{
		// set the arrivalTime of this truck
		arrive();
		
		// create next SmallTruck to ensure the system doesn't run out of 
		// SmallTrucks
		( (QuarryModel)getModel() ).activateNewSmallTruck();
		
		// set the prioirity so high that the small truck will get the small 
		// digger before the large trucks and even the partly loaded 
		// large trucks
		setQueueingPriority(2);
		
		// if no small digger is available right now and a small digger is 
		// loading a large truck, interrupt that one to make him load me!
		if (	rendezvousWithDigger.availMaster( onlySmallDigger ) == null	 && 
					!interruptableDiggers.isEmpty() )
		{
			interruptableDiggers.first().interrupt(smallTruckArrived);
		}
		
		// wait as a slave to be loaded by a master (digger)
		rendezvousWithDigger.waitOnCoop();
		
		// update the statistics for the small trucks
		// make the ValueSupplier notify all its Observers
		sTruckStaySupplier.notifyStatistics(
													new Double(this.getDelay().getTimeAsDouble()) );									
	}
}		// end class SmallTruck
