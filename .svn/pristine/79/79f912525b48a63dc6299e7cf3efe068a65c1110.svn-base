package desmoj.demo.mining;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.simulator.*;

/**
 * Loading is representing the cooperation between the diggers and the 
 * trucks. The diggers are the masters which really executes the 
 * cooperation (the loading of the trucks). The trucks are the slaves 
 * waiting for a master to load him with stones.
 *  
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
 
public class Loading extends ProcessCoop
{
			
/**
 * Constructor for a "loading cooperation" between a digger (master) and a
 * truck (slave). Name is "Loading". It is not showing in the trace. 
 * @param owner Model : The model this "loading cooperation" is associated
 * to.
 */
	public Loading ( Model owner )
	{
		super (owner, "Loading", false);		// construct ProcessCoop, don't 
																				// show in trace
	}
/**
 * Here the action of the cooperation carried out by the master (digger) 
 * and the slave (truck) is described.
 * @param master SimProcess : The master process (digger) which really 
 * carries out the cooperation.
 * @param slave SimProcess : The slave process (truck) which is lead 
 * through the cooperation by the master.
 */
	protected void cooperation (SimProcess master, SimProcess slave) throws SuspendExecution
	{
		// variable to hold the master (digger)
		Digger digger;
		 
		// check what type of digger we have got
		if ( master instanceof SmallDigger )
		{
			// typecast to the correct type of digger
			digger = (SmallDigger)master;	 
		}
		else
		{
			if ( master instanceof LargeDigger )
			{
				// typecast to the correct type of digger
				digger = (LargeDigger)master;	 
			}
			else	// master is no digger at all
			{
				sendWarning ( "The given master process for a cooperation is "+
										"not a digger. Command ignored!",
										"ProcessCoop : "+getName()+" Method: void "+
										"cooperation (SimProcess master, SimProcess slave)",
										"The given master is not a digger.",
										"Be sure to pass only valid Diggers as masters for "+
										"this cooperation");
				return;		// no proper master
			}
		}
		
		// get hold of the slave (truck)
		Truck truck;
		 
		// check what type of truck we have got
		if ( slave instanceof SmallTruck )
		{
			// typecast to the correct type of truck
			truck = (SmallTruck)slave;	 
		}
		else
		{
			if ( slave instanceof LargeTruck )
			{
				// typecast to the correct type of digger
				truck = (LargeTruck)slave;	 
			}
			else	// slave is no truck at all
			{
				sendWarning ( "The given slave process for a cooperation is not "+
										"a truck. Command ignored!",
										"ProcessCoop : "+getName()+" Method: void "+
										"cooperation (SimProcess master, SimProcess slave)",
										"The given slave is not a truck.",
										"Be sure to pass only valid Trucks as slaves for " +
										"this cooperation");
			return;		// no proper slave
			}
		}
		
		// check if the master SimProcess (digger) is interrupted already
		if ( master.getInterruptCode() != null )
		{
			sendWarning ( "The given master process for this cooperation has "+
									"an interrupt code set already. The interrupt code " +
									"will be cleared!",
									"ProcessCoop : "+getName()+" Method: void "+
									"cooperation (SimProcess master, SimProcess slave)",
									"The cooperation can not start when the master is "+
									"interrupted already.",
									"Make sure the master is not interrupted before " +
									"starting a cooperation with him.");
			master.clearInterruptCode();	// clear the interrupt code
		}
		
		// start the cooperation for the digger (update statistic and put 
		// digger in queue of interruptable diggers, if necessary)
		digger.beginCoop( truck );
		
		// start the cooperation 
		TimeInstant loadingStart = presentTime();
		
		// hold for the time to load the truck
		hold ( new TimeSpan( truck.getRemainingCapacity() 
													/ digger.getLoadRate() ) );
		
		// is the cooperation interrupted?
		if ( digger.getInterruptCode() == null )
		{
			truck.clearCapacity(); 	// truck is loaded completly
		}
		else		// truck is interrupted and not fully loaded
		{
			// large digger should not be interrupted
			if ( digger instanceof LargeDigger )		
			{
				sendWarning ( "A large digger has been interrupted. But large "+
									"diggers must not be interrupted!",
									"ProcessCoop : "+getName()+" Method: void "+
									"cooperation (SimProcess master, SimProcess slave)",
									"There is no reason why large diggers should be " +
									"interrupted.",
									"Make sure not to interrupt large diggers.");
			}
			
			// how far is the truck loaded?
			truck.load( TimeOperations.diff(presentTime(), loadingStart).getTimeAsDouble()
									* digger.getLoadRate() );
			// give the partialy loaded truck a higher priority
			truck.setQueueingPriority(1);									
		}
		
		// end the cooperation for the digger (update statistic and remove 
		// digger from queue of interruptable diggers, if necessary)
		digger.endCoop( truck );
		
		// clear the interruptCode
		digger.clearInterruptCode();
	}
}	// end class Loading
