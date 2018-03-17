package desmoj.demo.constructionVis3D;

import java.util.concurrent.TimeUnit;

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
	private ConstructionModel myModel;

	/**
	 * TruckGenerator constructor comment.
	 * @param arg1 desmoj.Model
	 * @param arg2 java.lang.String
	 * @param arg3 boolean
	 */
	public TruckGenerator(Model owner, String name, boolean showInReport) {
		super(owner, name, showInReport);

		myModel = (ConstructionModel) owner;
	}
	/**
	 * This eventRoutine describes what an object of this class
	 * will do, when it becomes activated by DESMO-J
	 *
	 * The eventRoutine()/lifeCycle() methods are one of the most import
	 * methods within DESMO-J based simulations. This is where the real
	 * action happens.
	 */
	public void eventRoutine() {
		//OK, the truck generator was activated, let's create a new truck
		// Parameters:
		// myModel						= it's part of this model
		// "Truck"						= name of the object
		// "Truck"						= type of the 3D-model
		// true							= yes please, show the truck in trace file
		// 1.0							= the acceleration: 1 m/s^2
		// -1.0							= the deceleration: -1 m/s^2
		// 16.6							= the maximum speed: 16.6 m/s = ca. 60 km/h
		Truck newTruck = new Truck(myModel, "Truck", "Truck",true,
				1.0,-1.0,16.6,null,null,null);

		//now let the newly created truck roll on the parking-lot
		//which means we will activate it after this truck generator has ended
		newTruck.activateAfter(this);

		//because we need another truck arrival we will activate
		//this truck generator again at the next truck arrival time
        this.schedule(new TimeSpan(myModel.getTruckArrivalTime(),TimeUnit.SECONDS));
		
		//from inside to outside...
		//we draw a new arrival time (difference)
		//we make a SimTime object out of it    and
		//we schedule ourselfes at this point of time
		myModel.trucksArrived.update( ++myModel.arrivedTrucks);
	}
}
