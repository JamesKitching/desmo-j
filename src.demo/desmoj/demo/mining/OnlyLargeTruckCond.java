package desmoj.demo.mining;

import desmoj.core.simulator.*;

/**
 * This condition should check if the given entity (SimProcess) is a large
 * truck (large trucks can only be loaded by large diggers).
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class OnlyLargeTruckCond extends Condition 
{

/**
 * Constructs a new conditon with the name "large Truck" and showing 
 * in the trace.
 * @param owner desmoj.Model : The model this large truck condition 
 * belongs to.
 */
	public OnlyLargeTruckCond( Model owner ) 
	{
		// call the super class' constructor (Condition)
		super(owner, "large Truck", true);
	}
/**
 * Returns false and a warning because one should use the check (Entity e) 
 * method to test whether the given entity is a large truck or not.
 * @return boolean : <code>false</code>
 */
public boolean check ()
{
	sendWarning ( "Attempt to check whether a given entity is a large " +
				"truck or not without any reference to that entity. False will " +
				"be returned!",
				"OnlyLargeTruckCond: "+this.getName()+" Method: boolean check().",
				"There is no entity given which could be checked.",
				"Make sure to use the other check (Entity e)-method and to pass "+
				"a suitable entity.");
	
	return false;
}
/**
 * Returns a boolean showing whether the given entity is a large truck 
 * or not.
 * @return boolean : Is <code>true</code>, if the entity given is a large 
 * truck, <code>false</code> otherwise. 
 * @param e Entity : The entity to test whether it is a large truck 
 * or not.
 */
public boolean check (Entity e)
{
	if ( e instanceof LargeTruck )
	{	return true;		}
	else
	{	return false;	}
}
}		// end class OnlyLargeTruckCond
