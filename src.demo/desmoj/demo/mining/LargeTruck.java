package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.dist.*;
import desmoj.core.simulator.*;

/**
 * LargeTruck is the class for all the large trucks coming to the quarry  
 * to be loaded with stones.
 * A detailed description of this quarry model is described in [Schn98] 
 * pages 171 following.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class LargeTruck extends Truck 
{

//   ******   attributes   ******

 /** 
	* The ValueSupplier calculating the total time a large truck has spent 
	* in the quarry (waiting for service and being loaded).
	*/
	private LargeTruckStaySuppl lTruckStaySupplier;


//   ******   methods   ****** 

/**
 * Constructs a new large truck with the name "L-Truck".
 * @param owner desmoj.Model : The model this large truck belongs to.
 * @param capacity double : The capactiy of this large truck.
 * @param lookingForDigger desmoj.WaitQueue : The queue where the truck  
 * (slave) has to wait for a digger (master) to cooperate with.
 * @param lTArrival desmoj.dist.RealDistExponential : The random 
 * distribution stream for the time span between arrivals of large trucks.
 * @param lTStay claassen_mining.LargeTruckStaySuppl : The ValueSupplier 
 * calculating how long a large truck has been staying in the quarry  
 * (waiting and being loaded)
 */
	public LargeTruck(Model owner,
											double capacity,
											WaitQueue lookingForDigger,
											ContDistExponential lTArrival,
											LargeTruckStaySuppl lTStay) 
	{
		// call the super class' constructor
		super(owner, "L-Truck", capacity, lookingForDigger);
		
		// the ValueSupplier calculating the stay of a large truck in the quarry
		this.lTruckStaySupplier = lTStay;
	}
	/**
	 * The lifeCycle describes all possible activities that a large truck 
	 * follows during its existence in the quarry. 
	 */
	public void lifeCycle() throws SuspendExecution
	{
		// set the arrivalTime of this truck
		arrive();
		
		// create next LargeTruck to ensure the system doesn't run out of 
		// LargeTrucks
		( (QuarryModel)getModel() ).activateNewLargeTruck();
		
		// wait as a slave to be loaded by a master (digger)
		do
		{
			rendezvousWithDigger.waitOnCoop();	// wait for a digger to be loaded
			
			// set the priority higher when the loading was interrupted
			setQueueingPriority(1);
		} 
		while ( getRemainingCapacity() > 0 );
		
		// update the statistics for the large trucks
		// make the ValueSupplier notify all its Observers
		lTruckStaySupplier.notifyStatistics(
												new Double(this.getDelay().getTimeAsDouble()) );
	}
}		// end class LargeTruck
