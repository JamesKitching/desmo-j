package desmoj.demo.mining;

import desmoj.core.simulator.*;
import desmoj.core.statistic.*;
/** 
 * The <code>AllTruckTally</code> class is providing a statistic  
 * analysis about the time all trucks are staying in the quarry. 
 * The mean value and the standard deviation is calculated on basis of 
 * the total number of observations. This special Tally was created to 
 * observe two ValueSuppliers.
 *  
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 * 
 * @version August 1999
 */
public class AllTruckTally extends Tally
{

//   ******   attributes   ******

/**
 * The ValueSupplier providing the values for the large trucks.
 */
 	private ValueSupplier valSupplLT;
 	

//   ******   methods   ****** 
			
/**
 * Constructor for a AllTruckTally object.
 * @param ownerModel Model : The model this AllTruckTally is associated to 
 * @param name java.lang.String : The name of this AllTruckTally object
 * @param valSupST ValueSupplier : The ValueSupplier providing the value 
 * for the small trucks for this AllTruckTally. The given ValueSupplier be 
 * will observed by this AllTruckTally object.
 * @param valSupLT ValueSupplier : The ValueSupplier providing the value 
 * for the large trucks for this AllTruckTally. The given ValueSupplier 
 * will be observed by this AllTruckTally object.
 * @param showInReport boolean : Flag for showing the report about this 
 * AllTruckTally. 
 * @param showInTrace boolean : Flag for showing the trace output of this 
 * AllTruckTally.
 */ 
	public AllTruckTally (Model ownerModel, String name, 
													ValueSupplier valSupST, ValueSupplier valSupLT,	
													boolean showInReport, boolean showInTrace)	
	{
		// call the constructor of Tally
		super(ownerModel, name, valSupST, showInReport, showInTrace);
		
		// valSupLT is no valid ValueSupplier
		if ( valSupLT == null)	 
		{
			sendWarning ( "Attempt to produce a AllTruckTally about a non " +
				"existing ValueSupplier. The command will be ignored!",
				"AllTruckTally: " + this.getName() + " Constructor: " +
						"AllTruckTally (Model ownerModel, String name, ValueSupplier"+
							" valSupST, ValueSupplier valSupLT	boolean " + 
							"showInReport, boolean showInTrace)	",
				"The given ValueSupplier: valSupLT is only a null pointer.",
				"Make sure to pass a valid ValueSupplier when constructing a " +
				"new AllTruckTally object.");
				
			return;		// just return
		}
		
		this.valSupplLT = valSupLT;
		
		// this AllTruckTally will observe the valSupplLT
		valSupplLT.addObserver(this);		
	}
}		// end class AllTruckTally
