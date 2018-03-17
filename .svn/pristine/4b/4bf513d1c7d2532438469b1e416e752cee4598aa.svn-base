package desmoj.demo.mining;

import desmoj.core.simulator.*;
import desmoj.core.statistic.*;

/**
 * Calculates the total time the small truck has spent in the quarry. That 
 * is the time the truck was waiting plus the time it took to load him
 * completly.
 * Because it is an observable, it can be observed by StatisticObjects.
 *
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class SmallTruckStaySuppl extends ValueSupplier 
{
 /*
	* The Model or ModelComponent which is providing the values needed to 
	* calculate the total time the truck has spent in the quarry.
	*/
	private QuarryModel dataProvider;


/**
 * Constructs a simple valueSupplier which is calculating the total time 
 * the small truck has spent in the quarry.
 * @param provider desmoj.Model : A model reference, to the model which 
 * is providing the data this ValueSupplier needs for its calculations.
 */
public SmallTruckStaySuppl(QuarryModel provider) 
{
	// construct a ValueSupplier with the name "small truck stay"
	super( "small truck stay" );	
	
	this.dataProvider = provider;
}
/**
 * Returns the total time the truck has spent in the quarry.
 * Is needed only if the <code>StatisticObject</code> is updated automatically
 * or the Pull-Model (of the Observer-Pattern) is used to update the
 * <code>StatisticObject</code>.
 * @return double  : The total time the truck has spent in the quarry.
 */
public double value()
{
	// get the current SimProcess (should be a SmallTruck)
	SimProcess st = dataProvider.currentSimProcess();
	
	// check if it is a SmallTruck
	if ( st instanceof SmallTruck )
	{
		// cast SimProcess to SmallTruck
		SmallTruck sTruck = (SmallTruck)st; 
		
		return ( sTruck.getDelay().getTimeAsDouble() );
	}
	else
	{
		dataProvider.sendWarning ( "Attempt to calculate the delay of a " +
				"small truck, but the current SimProcess is no SmallTruck. "+
				"Zero will be returned!",
				"SmallTruckStaySuppl: " + this.getName() + " Method: double " +
				"value().",
				"The delay will always be calculated from the currentSimProcess. "+
				"If the current SimProcess is no SmallTruck no delay can be " +
				"calculated.",
				"Make sure that the current SimProcess is a SmallTruck.");
			
		return 0.0;
	}
}
}	// end class SmallTruckStaySuppl
