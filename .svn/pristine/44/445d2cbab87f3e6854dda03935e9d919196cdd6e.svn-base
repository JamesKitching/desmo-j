package desmoj.extensions.db.visustorage.sim;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeOperations;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.space3D.ExtendedLength;
import desmoj.extensions.space3D.MovableSpatialSimProcess;
import desmoj.extensions.space3D.SpatialLayoutManager;
import desmoj.extensions.space3D.SpatialObject;
import desmoj.extensions.space3D.SpatialProcessQueue;
/**
 * This class is part of the "ConstructionModel".
 * See the description() method of the model class for
 * further documentation of the basis model.
 *
 * This class represents the truck in the above mentioned
 * model.
 * A truck arrives at the construction site, and requests
 * loading of sand.  If possible he gets serviced
 * by the excavator immediatly. If not he waits in a queue
 * for his turn.
 * After service he leaves the system.
 *
 * @author: Olaf Neidhardt
 * @author: Fred Sun
 */
public class Truck extends MovableSpatialSimProcess {
	
	/**
	 * The layout manager which contains the the layout data.
	 */
	private SpatialLayoutManager _layoutManager;
	
	/**
	 * The ConstructionModel
	 */
	private StorageModel _conModel;
	
	/**
	 * the excavator which serves this truck
	 */
	protected Excavator _servingExcavator = null;

	private TimeInstant startWait;
	
	private TimeInstant endWait;
	
	/**
	 * Constructs a truck object.
	 * 
	 * @param owner The model which contains this process.
	 * @param name The name of this process.
	 * @param type The associated 3D-model type.
	 * @param showInTrace Flag for showing entity in trace-files.
	 * Set it to true if entity should show up in trace. Set to false
	 * in entity should not be shown in trace.
	 * @param acc The acceleration of the truck in meter/second^2.
	 * @param dec The deceleration of the truck in meter/second^2.
	 * (negative double value)
	 * @param mSpeed The maximum speed of this truck in meter/second.
	 * @param startPositionX The x-coordinate of the start position.
	 * @param startPositionY The y-coordinate of the start position.
	 * @param startPositionZ The z-coordinate of the start position.
	 */
	public Truck(StorageModel owner, String name, String type,
			boolean showInTrace, double acc, double dec,
			double mSpeed, ExtendedLength startPositionX, ExtendedLength startPositionY,
			ExtendedLength startPositionZ){
		super(owner,name,type,showInTrace,acc,dec,mSpeed);
//		this.setPosition(startPositionX, startPositionY, startPositionZ);
		_conModel = owner;
		_layoutManager = SpatialLayoutManager.getSpatialLayoutManager();
		this.sendToLocation(_layoutManager.getSpatialObject("TruckSpring"));
	}
	
	/**
	 * This lifeCycle() describes what the truck does when it
	 * becomes activated by DESMO-J.
	 * 
	 * It will drive to the construction site and check if the 
	 * excavator is available.
	 * If it will activate the excavator to get serviced and
	 * transfer the control to the excavator.
	 * Otherwise it inserts itself into the queue of waiting trucks
	 * and passivates. (it parks and waits)
	 * It leaves the system after service.
	 *
	 * The eventRoutine()/lifeCycle() methods are one of the most import
	 * methods within DESMO-J based simulations. This is where the real
	 * action happens.
	 */
	public void lifeCycle() throws co.paralleluniverse.fibers.SuspendExecution {
		
		SpatialProcessQueue<Truck> truckQueue = (SpatialProcessQueue<Truck>)_layoutManager.getSpatialObject("TruckQueue");
		
		this.move(truckQueue, "QueueEntry", true);
		// Truck enters queue
		truckQueue.insert(this);
		startWait = this.presentTime();
		sendTraceNote("TruckQueuelength: " + truckQueue.length());

		// is the excavator available ?
		if (!_conModel.idleExcavatorQueue.isEmpty()) { // it is available

			//get the first (and only) excavator from the idle excavator queue
			_servingExcavator = _conModel.idleExcavatorQueue.first();
			//to get it does not mean it is removed yet (?!), so do it now:
			_conModel.idleExcavatorQueue.remove(_servingExcavator);

			//place the excavator on the event-list right after me,
			//to ensure that I will be the next customer to get serviced
			_servingExcavator.activateAfter(this);

			//wait until the excavator allows me to come
			passivate();
			//drive to the excavator
			this.moveToLoadingPoint(_servingExcavator.getLoadingPoint());
			//let the excavator working
			_servingExcavator.activate(new TimeSpan(0l,TimeUnit.SECONDS));
			
			//waiting for the loading
			passivate();
			
			//drive away after loading
			move(_layoutManager.getSpatialObject("TruckDrain"), null, true);
			
			//remove the counterpart of this object at the visualization module
			removeVisible();

		} else { // it is NOT available

			// thus I do nothing and wait for service
			passivate();
			
			//drive to the excavator
			this.moveToLoadingPoint(_servingExcavator.getLoadingPoint());
			
			//let the excavator working
			_servingExcavator.activate(new TimeSpan(0l,TimeUnit.SECONDS));
			
			//waiting for the loading
			passivate();
			
			//drive away after loading
			move(_layoutManager.getSpatialObject("TruckDrain"), null, true);
			
			//remove the counterpart of this object at the visualization module
			removeVisible();
			
		}

		// Ok, I am back online again, which means I was serviced
		// by the VC. I can leave the systems now.
		// Luckily I don't have to do anything more than sending
		// a message to the trace file, because the
		// JAVA VM garbagge collector will get the job done.
		// Bye!
		sendTraceNote("Truck was serviced and leaves system.");
		_conModel.trucksServiced.update(++_conModel.servicedTrucks);
		_conModel.waitTimeHistogram.update(getWaitTime());
	}
	
	/**
	 * Moves the truck to the specific loading point.
	 * 
	 * @param loadingPoint Where the truck will be loaded.
	 */
	public void moveToLoadingPoint(SpatialObject loadingPoint){
		move(loadingPoint, null, true);
	}
	
	public void endWait() {
		endWait = this.presentTime();
	}
	
	public double getWaitTime() {
		if (startWait != null && endWait != null)
			return TimeOperations.diff(endWait, startWait).getTimeAsDouble(TimeUnit.SECONDS);
		else
			return Double.NaN;
	}

}
