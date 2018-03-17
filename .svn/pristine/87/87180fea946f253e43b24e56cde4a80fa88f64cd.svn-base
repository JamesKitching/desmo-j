package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.dist.*;
import desmoj.core.simulator.*;

/**
 * The <code>SmallTruckArrivalEvent</code> is an external event which makes
 * the small trucks arrive in the quarry.
 * @author Soenke Claassen
 */
public class SmallTruckArrivalEvent extends ExternalEvent {

 /**
	* The random distribution for the time span between arrivals of small 
	* trucks.
	*/
	private ContDistExponential smallTruckStream;

 /**
	* The queue where the small diggers are stored when they are loading a 
  * large truck.They will be interrupted, because they have to load the 
  * small trucks first.
	*/
	private ProcessQueue<SmallDigger> interruptableDiggers;
	
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

 /**
	* The capacity that has to be loaded for the small truck.
	*/
	private double capacity;

 /**
	* The queue where the truck (slave) has to wait for a digger (master) 
	* to cooperate with.
	*/
	private WaitQueue rendezvousWithDigger;
	
/**
 * Constructs a new SmallTruckArrivalEvent which makes the small trucks
 * arrive in the quarry.
 * @param owner desmoj.Model : The model this SmallTruckArrivalEvent belongs to.
 * @param capacity double : The capactiy of the small trucks.
 * @param lookingForDigger desmoj.WaitQueue : The queue where the small truck  
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
public SmallTruckArrivalEvent(Model owner,
															double capacity,
															WaitQueue lookingForDigger,
															ProcessQueue<SmallDigger> interruptableDiggers,
															InterruptCode smallTruckArrived,
                                                            ContDistExponential sTArrival,
															SmallTruckStaySuppl sTStay )
{
	
	super(owner, "smallTruckArrivalEvent", true);

	// set the values

	// capacity of the small truck
	this.capacity = capacity;

	// get hold of the WaitQueue where to meet the digger
	this.rendezvousWithDigger = lookingForDigger;	
	
	// ProcessQueue
	this.interruptableDiggers = interruptableDiggers;
		
	// InterruptCode
	this.smallTruckArrived = smallTruckArrived;
		
	// arrival stream of the small trucks
	this.smallTruckStream = sTArrival;
		
	// the ValueSupplier calculating the stay of a small truck in the quarry
	this.sTruckStaySupplier = sTStay;

	
}
/**
 * The eventRoutine instantiates a new smallTruck and makes it arrive in the 
 * quarry. 
 */
public void eventRoutine() throws SuspendExecution 
{
	// schedule the next small truck arrival event
	SmallTruckArrivalEvent sTAE = new SmallTruckArrivalEvent (
																			getModel(),
																			capacity,
																			rendezvousWithDigger, 
																			interruptableDiggers,
																			smallTruckArrived,
																			smallTruckStream,
																			sTruckStaySupplier );

	sTAE.schedule(new TimeSpan ( smallTruckStream.sample() ) );
	
	// make an new small truck and get it activated	
	SmallTruck newST = new SmallTruck( getModel(),
																			capacity,
																			rendezvousWithDigger, 
																			interruptableDiggers,
																			smallTruckArrived,
																			sTruckStaySupplier );
																						
	newST.activate( new TimeSpan (0.0) );
}
}
