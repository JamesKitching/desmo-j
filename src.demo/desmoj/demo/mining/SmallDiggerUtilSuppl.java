package desmoj.demo.mining;

import desmoj.core.statistic.*;


/**
 * Calculates the utilization of the small diggers.
 * Because it is an observable, it can be observed by StatisticObjects.
 *
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class SmallDiggerUtilSuppl extends ValueSupplier 
{
 /*
	* The maximum number of available diggers.
	*/
	private int maxDiggers;
	
 /*
	* The number of working diggers at the moment.
	*/
	private int workingDiggers;
	
 /*
	* The Model or ModelComponent which is providing the values needed to 
	* calculate the utilization.
	*/
	private QuarryModel dataProvider;


/**
 * Constructs a simple valueSupplier which is calculating the utilization 
 * of the small diggers.
 * @param provider desmoj.Model : A model reference, to the model which 
 * is providing the data this ValueSupplier needs for its calculations.
 */
public SmallDiggerUtilSuppl(QuarryModel provider) 
{
	// construct a ValueSupplier with the name "small Digger Util"
	super( "small Digger Util" );	
	
	this.dataProvider = provider;
	
	this.maxDiggers = dataProvider.getSmallDiggers();
	
	this.workingDiggers = dataProvider.getWorkingSmallDiggers();
}
/**
 * Returns the utilization of the small diggers at this moment.
 * Is needed only if the <code>StatisticObject</code> is updated automatically
 * or the Pull-Model (of the Observer-Pattern) is used to update the
 * <code>StatisticObject</code>.
 * @return double  : The utilization of the small diggers. 
 */
public double value()
{
	maxDiggers = dataProvider.getSmallDiggers();
	
	workingDiggers = dataProvider.getWorkingSmallDiggers();	
	
	return ( (workingDiggers * 100) / maxDiggers );
}
}	// end class SmallDiggerUtilSuppl
