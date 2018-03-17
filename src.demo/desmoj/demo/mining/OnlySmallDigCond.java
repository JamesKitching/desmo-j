package desmoj.demo.mining;

import desmoj.core.simulator.*;


/**
 * This condition should check if the given entity (SimProcess) is a small 
 * digger (small trucks can only be loaded by small diggers).
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class OnlySmallDigCond extends Condition 
{

/**
 * Constructs a new conditon with the name "small digger" and showing 
 * in the trace.
 * @param owner desmoj.Model : The model this small truck belongs to.
 */
	public OnlySmallDigCond( Model owner ) 
	{
		// call the super class' constructor
		super(owner, "small digger", true);
	}
/**
 * Returns false and a warning because one should use the check (Entity e)  
 * method to test whether the given entity is a small digger or not.
 * @return boolean : <code>false</code>
 */
public boolean check ()
{
	sendWarning ( "Attempt to check whether a given entity is a small " +
				"digger or not without any reference to that entity. False will "+
				"be returned!",
				"OnlySmallDigCond: "+this.getName()+" Method: boolean check().",
				"There is no entity given which could be checked.",
				"Make sure to use the other check (Entity e)-method and to pass "+
				"a suitable entity.");
	
	return false;
}
/**
 * Returns a boolean showing whether the given entity is a small digger 
 * or not.
 * @return boolean : Is <code>true</code>, if the entity given is a small 
 * digger, <code>false</code> otherwise. 
 * @param e Entity : The entity to test whether it is a small digger 
 * or not.
 */
public boolean check (Entity e)
{
	if ( e instanceof SmallDigger )
	{	return true;		}
	else
	{	return false;	}
}
}		// end class OnlySmallDigCond
