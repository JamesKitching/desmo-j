package desmoj.demo.mining;

import desmoj.core.statistic.*;

/**
 * Calculates the utilization of all diggers.
 * Because it is an observable, it can be observed by StatisticObjects.
 *
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class AllDiggerUtilSuppl extends ValueSupplier 
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
 * of all diggers.
 * @param provider desmoj.Model : A model reference, to the model which 
 * is providing the data this ValueSupplier needs for its calculations.
 */
public AllDiggerUtilSuppl(QuarryModel provider) 
{
	// construct a ValueSupplier with the name "all Digger Util"
	super( "all Digger Util" );	
	
	this.dataProvider = provider;
	
	this.maxDiggers = dataProvider.getAllDiggers();
	
	this.workingDiggers = dataProvider.getAllWorkingDiggers();	
}
/**
 * Returns the utilization of all diggers at this moment.
 * Is needed only if the <code>StatisticObject</code> is updated automatically
 * or the Pull-Model (of the Observer-Pattern) is used to update the
 * <code>StatisticObject</code>.
 * @return double  : The utilization of all diggers. 
 */
public double value()
{
	maxDiggers = dataProvider.getAllDiggers();
	
	workingDiggers = dataProvider.getAllWorkingDiggers();
	
	return ( (workingDiggers * 100) / maxDiggers );
}
}		// end class AllDiggerUtilSuppl
