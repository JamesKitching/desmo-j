package desmoj.demo.mining;

import desmoj.core.simulator.*;

/**
 * This condition should check if the given entity (SimProcess) is a 
 * large truck which has been interrupted during its loading.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class OnlyInterruptedTruckCond extends Condition 
{

/**
 * Constructs a new conditon with the name "interrupted Truck" and showing 
 * in the trace.
 * @param owner desmoj.Model : The model this interrupted truck condition 
 * belongs to.
 */
	public OnlyInterruptedTruckCond( Model owner ) 
	{
		// call the super class' constructor (Condition)
		super(owner, "interrupted Truck", true);
	}
/**
 * Returns false and a warning because one should use the check (Entity e)
 * method to test whether the given entity is an interrupted truck or not.
 * @return boolean : <code>false</code>
 */
public boolean check ()
{
	sendWarning ( "Attempt to check whether a given entity is an " +
				"interrupted truck or not without any reference to that entity. "+
				"False will be returned!",
				"OnlyInterruptedTruckCond: "+this.getName()+" Method: boolean " +
				"check(). ",
				"There is no entity given which could be checked.",
				"Make sure to use the other check (Entity e)-method and to pass "+
				"a suitable entity.");
	
	return false;
}
/**
 * Returns a boolean showing whether the given entity is an interrupted 
 * truck or not.
 * @return boolean : Is <code>true</code>, if the entity given is an 
 * interrupted truck, <code>false</code> otherwise. 
 * @param e Entity : The entity to test whether it is an interrupted  
 * truck or not.
 */
public boolean check (Entity e)
{
	if ( e == null )
	{
		sendWarning ( "Attempt to check whether a given entity is an " +
				"interrupted truck or not with a null reference to that entity. "+
				"False will be returned!",
				"OnlyInterruptedTruckCond: " + this.getName() + " Method: "+
				"boolean check(Entity e).",
				"There is only a null pointer given which could " +
				" not be checked.",
				"Make sure to pass a suitable entity instead of a null pointer.");
	
	return false;
	}
	
	// the only entities having a piority == 1 are the interrupted trucks
	return e.getQueueingPriority() == 1;
}
}		// end class OnlyInterruptedTruckCond
