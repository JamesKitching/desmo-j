package desmoj.demo.mining;

import desmoj.core.simulator.*;
import desmoj.core.statistic.*;

/**
 * Calculates the total time the large truck has spent in the quarry. That is
 * the time the truck was waiting plus the time it took to load him
 * completly.
 * Because it is an observable, it can be observed by StatisticObjects.
 *
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class LargeTruckStaySuppl extends ValueSupplier 
{
	/*
	* The Model or ModelComponent which is providing the values needed to 
	* calculate the total time the truck has spent in the quarry.
	*/
	private QuarryModel dataProvider;

/**
 * Constructs a simple valueSupplier which is calculating the total time the
 * large truck has spent in the quarry.
 * @param provider desmoj.Model : A model reference, to the model which is
 * providing the data this ValueSupplier needs for its calculations.
 */
public LargeTruckStaySuppl(QuarryModel provider) 
{
	// construct a ValueSupplier with the name "large truck stay"
	super( "large truck stay" );	
	
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
	// get the current SimProcess (should be a LargeTruck)
	SimProcess lt = dataProvider.currentSimProcess();
	
	// check if it is a LargeTruck
	if ( lt instanceof LargeTruck )
	{
		// cast SimProcess to LargeTruck
		LargeTruck lTruck = (LargeTruck)lt; 
		
		return ( lTruck.getDelay().getTimeAsDouble() );
	}
	else
	{
		dataProvider.sendWarning ( "Attempt to calculate the delay of a large  " +
				"truck, but the current SimProcess is no LargeTruck. Zero will be " +
				"returned!",
				"LargeTruckStaySuppl: " + this.getName() + " Method: double value(). " ,
				"The delay will always be calculated from the currentSimProcess. " +
				"If the current SimProcess is no LargeTruck no delay can be " +
				"calculated.",
				"Make sure that the current SimProcess is a LargeTruck.");
			
		return 0.0;
	}
}
}		// end class LargeTruckStaySuppl
