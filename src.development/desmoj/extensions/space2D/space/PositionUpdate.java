package desmoj.extensions.space2D.space;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.SimTime;

/**
 * PositionUpdates denote position updates for agents. These updates are
 * processed by the environment: it lets the space remove the agent from his
 * current position and add him to his new position.
 * 
 * @author Ruth Meyer
 */
public class PositionUpdate extends EnvironmentSignal {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/**
	 * the new position of the agent.
	 */
	Position newPosition;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new position update event for the given agent.
	 * 
	 * @param agent
	 *            the situated agent
	 * @param time
	 *            the point in simulation time when the update is due (this is
	 *            usually in the middle of an agent's move)
	 * @param newPosition
	 *            the agent's new position
	 */
	public PositionUpdate(SimTime time, Entity agent,
			Position newPosition) {
		super(time, agent);
		this.newPosition = newPosition;
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns the agent's new position.
	 */
	Position getNewPosition() {
		return this.newPosition;
	}

	/** Returns a string representation */
	public String toString() {
		return "PositionUpdate to " + newPosition + " at " + getTime();
	}

}