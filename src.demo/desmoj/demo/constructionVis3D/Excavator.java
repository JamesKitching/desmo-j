package desmoj.demo.constructionVis3D;

import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.exception.DelayedInterruptException;
import desmoj.core.exception.InterruptException;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.space3D.MovableSpatialSimProcess;
import desmoj.extensions.space3D.SpatialLayoutManager;
import desmoj.extensions.space3D.SpatialObject;
import desmoj.extensions.space3D.SpatialProcessQueue;

/**
 * This class is part of the "ConstructionModel".
 * See the description() method of the model class for
 * further documentation of the basis model.
 *
 * This class represents the excavator in the above 
 * mentioned model.
 * The excavator is on service and waits for a truck to come
 * requesting service. He will work and load the truck.
 * If there is another truck waiting he services it.
 * If not he waits for the next truck to arrive.
 * 
 * @author: Olaf Neidhardt
 * @author Fred Sun
 */
public class Excavator extends MovableSpatialSimProcess {
	
	private SpatialLayoutManager _layoutManager;
	
	private ConstructionModel _conModel;
	
	/**
	 * The position where this excavator loads the trucks
	 */
	private SpatialObject _loadingPoint;

	/**
	 * Constructs an excavator.
	 * 
	 * @param owner The model which contains this process.
	 * @param name The name of this process.
	 * @param type The 3D-model's type of this process. 
	 * @param showInTrace Flag for showing entity in trace-files.
	 * Set it to true if entity should show up in trace. Set to false
	 * in entity should not be shown in trace.
	 */
	public Excavator(Model owner, String name, String type,
			SpatialObject loadingPoint ,boolean showInTrace) {
		super(owner, name, type, showInTrace);
		_conModel = (ConstructionModel)owner;
		_loadingPoint = loadingPoint;
		_layoutManager = SpatialLayoutManager.getSpatialLayoutManager();
		rotY(Math.PI);
	}
	
	/**
	 * Animate the working.
	 * @param workingDuration
	 * @throws SuspendExecution 
	 */
	private void work(double workingDuration) throws SuspendExecution{
		double quarterDuration = workingDuration/4;
		//0.52 radian = ca. 30 degree
		//get the sand
		this.rotY(0.52, new TimeSpan(quarterDuration,TimeUnit.SECONDS));
		//put it on the truck
		this.rotY(-1.04, new TimeSpan(quarterDuration*2,TimeUnit.SECONDS));
		//reset position
		this.rotY(0.52, new TimeSpan(quarterDuration,TimeUnit.SECONDS));
	}

	/**
	 * This lifeCycle() describes what the excavator does when it
	 * becomes activated by DESMO-J,
	 * 
	 * It will cycle through a process like this:
	 * Check if there is a customer waiting.
	 * If there is someone waiting 
	 *   a) remove customer from queue
	 *   b) wait until customer is moved to the loading location
	 *   c) service customer
	 *   d) return to top
	 * If there is no one waiting
	 *   a) wait until you get activated again
	 *	 b) then return to top
	 *   
	 * The eventRoutine()/lifeCycle() methods are one of the most import
	 * methods within DESMO-J based simulations. This is where the real
	 * action happens.
	 * @throws SuspendExecution 
	 * @throws InterruptException 
	 * @throws DelayedInterruptException 
	 * 
	 */
	public void lifeCycle() throws DelayedInterruptException, InterruptException, SuspendExecution {
		
		SpatialProcessQueue<Truck> truckQueue = (SpatialProcessQueue<Truck>)_layoutManager.getSpatialObject("TruckQueue");

		//the servicer is always on duty and will never stop working
		while (true) {
			//check if there is someone waiting
			if (truckQueue.isEmpty()) { // NO,there is no one waiting

				// insert yourself into the idle excavator queue
				_conModel.idleExcavatorQueue.insert(this);

				// and wait for things to happen
				passivate();
			} else { //YES,there is a customer (truck) waiting
				//get the next truck to service station
				Truck nextTruck = truckQueue.first();
				//again first() does not mean it is removed yet, so...
				truckQueue.remove(nextTruck);
				// myModel.queueData.update(myModel.truckQueue.length());
				nextTruck.endWait();
				
				//check whether the truck knows who's serving it
				if(nextTruck._servingExcavator==null){
					//if not, tell it it's me
					nextTruck._servingExcavator = this;
				}
				
				//call the truck to come the loading area
				nextTruck.activate(new TimeSpan(0l,TimeUnit.SECONDS));
				
				//wait for truck's arrival
				passivate();

				//now service it
				work(_conModel.getServiceTime());
				//from inside to outside...
				//...draw a new period of service time
				//...make a SimTime object out of it
				//...and hold for this amount of time

				//now the truck has loaded and can leave
				nextTruck.activate(new TimeSpan(0l,TimeUnit.SECONDS));
				//the excavator can return to top and check for a new customer
			}
		}
	}

	/**
	 * Gets the loading position of this excavator.
	 * @return The position where this excavator loads the trucks
	 */
	public SpatialObject getLoadingPoint() {
		return _loadingPoint;
	}

}
