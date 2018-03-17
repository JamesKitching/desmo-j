package desmoj.extensions.space2D.space;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.SimTime;
import desmoj.extensions.space2D.Signal;

/**
 * Environment signals are signals representing events occuring in the
 * environment for / with an entity. They require the environment to be
 * activated in order to handle the event. They are stored in the environment's
 * internal schedule.
 * 
 * @author Ruth Meyer
 */
public abstract class EnvironmentSignal extends Signal {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the entity to which the event belongs */
	Entity entity;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new environment event with the given entity and time.
	 * 
	 * @param entity
	 *            the entity
	 * @param time
	 *            the point in simulation time when the event will happen to the
	 *            entity
	 */
	public EnvironmentSignal(SimTime time, Entity entity) {
		super(time, true);
		this.time = time;
		this.entity = entity;
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns the entity
	 */
	Entity getEntity() {
		return this.entity;
	}

}