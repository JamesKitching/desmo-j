package desmoj.extensions.space2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.SimTime;

/**
 * A schedule stores signals to be processed by the entity sorted in time
 * ascending order. Every agent owns one schedule. Also, the environment owns a
 * schedule.
 */
public class Schedule {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/**
	 * the queue of signals.
	 */
	private List queue;

	// die signals mžssen zeitlich aufsteigend sortiert sein

	/** the entity owning this schedule. */
	private Entity owner;

	/** Signals with this time are inserted at the front of the schedule */
	public static SimTime IMMEDIATE = new SimTime(0.0);

	/** Comparator for identity of timely ordered sigals */
	public static Comparator signalIdentityComparator = new Comparator() {

		public int compare(Object o1, Object o2) {

			if (o1.equals(o2))
				return 0;
			else {
				SimTime t1 = ((Signal) o1).getTime();
				SimTime t2 = ((Signal) o2).getTime();

				if (t1 == IMMEDIATE && t2 != IMMEDIATE)
					return -1;
				else if (t2 == IMMEDIATE && t1 != IMMEDIATE)
					return 1;

				if (SimTime.isLarger(t1, t2))
					return 1;
				else
					return -1;
			}
		}

		public boolean equals(Object o) {
			return (this == o);
		}
	};

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new schedule for the given entity.
	 */
	public Schedule(Entity owner) {
		this.owner = owner;
		this.queue = new ArrayList(5);
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * tests if the queue is empty. Returns <code>true</code> if there are no
	 * signals waiting in the queue, returns <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/** Returns true if the schedule contains the given signal */
	public boolean contains(Signal s) {
		int i = Collections.binarySearch(queue, s, signalIdentityComparator);
		return (i > -1);
	}

	/**
	 * Returns an iterator of all signals on the schedule ordered by their
	 * schedule time.
	 */
	public Iterator allSignals() {
		return queue.iterator();
	}

	/**
	 * Returns the time of the first signal in the queue whose activating flag
	 * is set to true. Returns null if the queue is empty or contains only
	 * signals that do not activate the agent. If the first signal activating
	 * the agent has time IMMEDIATE the model's current time is returned.
	 */
	public SimTime nextActivationTime() {
		for (Iterator i = queue.iterator(); i.hasNext();) {
			Signal s = (Signal) i.next();
			if (s.isActivating()) {
				if (s.getTime() == IMMEDIATE)
					return owner.currentTime();
				else
					return s.getTime();
			}
		}
		return null;
	}

	/**
	 * returns the next signal in the queue if its time is not later than the
	 * current sim time. The signal is removed from the queue!
	 */
	public Signal nextSignal() {
		// Return null if queue is empty
		if (isEmpty()) {
			return null;
		}
		// Return first signal for current time
		SimTime currentTime = owner.currentTime();
		Signal next = (Signal) queue.get(0);

		// check time
//		if (SimTime.isLarger(currentTime, next.getTime())
//				|| !owner.getModel().getExperiment().isDistinguishable(
//						currentTime, next.getTime())) {
//			// if time is correct remove and return signal
//			queue.remove(0);
//			return next;
//		}
		return null;
	}

	/** Removes the given signal if the schedule contains it. */
	public boolean remove(Signal s) {
		int i = Collections.binarySearch(queue, s, signalIdentityComparator);
		if (i > -1)
			queue.remove(i);
		return (i == 0); // returns true, if s was first element
	}

	/**
	 * inserts a signal in the queue sorted in time ascending order.
	 * 
	 * @param signal
	 *            the signal to be inserted
	 */
	public boolean insert(Signal signal) {

		int i = -1;

		if (isEmpty()) {
			this.queue.add(signal);
			i = 0;
		} else {
			i = Collections.binarySearch(queue, signal,
					signalIdentityComparator);
			if (i < 0) {
				i = -(i + 1); // Index to insert signal at
				queue.add(i, signal);
			}
		}
		return (i == 0); // returns true if s was inserted as first element
	}

	/**
	 * returns a string representation of this schedule.
	 */
	public String toString() {
		return this.queue.toString();
	}
}