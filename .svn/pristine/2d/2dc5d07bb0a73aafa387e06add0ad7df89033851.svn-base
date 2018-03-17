package desmoj.demo.vc_model;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
/**
 * This class represents a process source, which produces continuous
 * truck arrivals in order to keep the simulation running.
 *
 * It will produce a new truck, schedule it now and 
 * place itself on the event-list, when the next truck is expected.
 *
 * Creation date: (30.03.00 14:15:47)
 * @author: Olaf Neidhardt
 */
public class TruckGenerator extends ExternalEvent {
    
	/**
	* Keeps a reference to the model this actor is a part of 
	* useful shortcut to access the model infrastructure
	*/
	private VancarrierModel myModel;

	/**
	 * TruckGenerator constructor comment.
	 * @param owner desmoj.Model
	 * @param arg2 java.lang.String
	 * @param arg3 boolean
	 */
	public TruckGenerator(Model owner, String name, boolean showInReport) {
		super(owner, name, showInReport);

		myModel = (VancarrierModel) owner;
	}
	
	/**
	 * This eventRoutine describes what an object of this class
	 * will do, when it becomes activated by DESMO-J
	 *
	 * The eventRoutine()/lifeCycle() methods are one of the most import
	 * methods within DESMO-J based simulations. This is where the real
	 * action happens.
	 */
	public void eventRoutine() throws SuspendExecution {

		//The truck generator was activated, let's create a new truck
		Truck newTruck = new Truck(myModel, "Truck", true);

		//now let the newly created truck roll on the parking-lot
		//which means we will activate it after this truck generator has ended
		newTruck.activate();

		//because we need another truck arrival we will activate
		//this truck generator again at the next truck arrival time
		this.schedule(new TimeSpan(myModel.getTruckArrivalTime()));
		
		//from inside to outside...
		//we draw a new arrival time (difference)
		//we make a TimeSpan object out of it and
		//we schedule ourselves at this point of time
		myModel.trucksArrived.update(++myModel.arrivedTrucks);
	}
}
