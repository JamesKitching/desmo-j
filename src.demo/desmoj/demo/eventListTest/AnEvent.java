package desmoj.demo.eventListTest;

import desmoj.core.simulator.*;

public class AnEvent extends ExternalEvent {

	String message;

	public AnEvent(Model owner, String message)
	{

		super(owner, "AnEvent", true);
		this.message = message;
	}
	/**
	 * The eventRoutine instantiates a new smallTruck and makes it arrive in the 
	 * quarry. 
	 */
	public void eventRoutine() throws co.paralleluniverse.fibers.SuspendExecution 
	{
		System.out.println("At " + this.presentTime() + " " + message + " w prio " + this.getSchedulingPriority());	
	}
}
