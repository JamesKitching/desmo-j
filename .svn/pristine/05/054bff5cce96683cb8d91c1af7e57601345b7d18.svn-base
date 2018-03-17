package desmoj.extensions.space2D;

import desmoj.core.simulator.SimTime;

/**
 * Signals denote (internal) events for an agent. They are connected with a
 * certain point in simulation time, i.e. the time they are actually occurring.
 * They may require an activation of the agent for this simulation time in order
 * to enable the agent to react immediately. Signals can be internal -- the
 * agent sends them to itself -- or external -- the environment or another agent
 * sends them. They are used as events in the agent's finite state machine (if
 * there is any).
 */
public abstract class Signal {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the time of this signal. */
	protected SimTime time;

	/** flag to indicate if this signal requires activating the agent. */
	protected boolean activating;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * Constructs a new signal with the given time and activation flag.
	 * Constructor for internal use in famos.agent package only !
	 * 
	 * @param time
	 *            the signals time of occurrence
	 * @param activating
	 *            idicates if the agent is activated when receiving this signal
	 *            (the signals "level of importance")
	 */
	protected Signal(SimTime time, boolean activating) {
		this.time = time;
		this.activating = activating;
	}

	/**
	 * Constructs a new signal with the given activation flag
	 * 
	 * @param activating
	 *            idicates if the agent is activated when receiving this signal
	 *            (the signals "level of importance")
	 */
	public Signal(boolean activating) {
		this(null, activating);
	}

	/**
	 * Constructs a new activating signal
	 */
	public Signal() {
		this(true);
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns the time of this signal.
	 */
	public SimTime getTime() {
		return this.time;
	}

	/**
	 * returns <code>true</code> if this signal requires activating the agent,
	 * <code>false</code> otherwise.
	 */
	public boolean isActivating() {
		return this.activating;
	}

	/**
	 * sets the time of this signal to a new point in simulation time
	 * 
	 * @param time
	 *            the new time
	 */
	void setTime(SimTime time) {
		this.time = time;
	}

	/**
	 * Returns the name of this signal class (by default the class name without
	 * package qualifiers)
	 */
	public String getSignalClass() {
		String classname = getClass().getName();
		int beginOfName = classname.lastIndexOf(".");
		return classname.substring(beginOfName + 1, classname.length());
	}

	public String toString() {
		return "(" + getSignalClass() + ", " + getTime() + ")";
	}
}
