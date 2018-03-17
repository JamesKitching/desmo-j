package desmoj.extensions.applicationDomains.petriNets;

import java.util.HashMap;
import java.util.Map;

import desmoj.core.dist.NumericalDist;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Reportable;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeOperations;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.Tally;
import desmoj.extensions.applicationDomains.petriNets.report.TransitionModeReporter;

/**
 * Represents a Transition mode of a certain Transition. A mode is represented
 * by the following components: A map of all input places onto the respective
 * multiset of tokens required from these places (the ingoing edges of this
 * mode), another map of all output places onto the multiset of tokens to be
 * created when firing this mode places (the outgoing edges of this mode) and
 * two distributions to be sampled from when requiring waiting/firing times
 * respectively.
 * 
 * The way that any TransitionMode works is the following: As soon as the
 * requirements for firing are met, meaning there is enough tokens of the
 * respective types in all of the input places, it will sample a value from the
 * waitDist to determine how long it will wait until actually firing. No tokens
 * will be consumed by this mode during that time. Should any of the
 * requirements not be met anymore at any point of the waiting time, the mode
 * will reset itself and passively wait until there is enough tokens again,
 * starting a new timer. If the required tokens are still existent when the
 * timer runs out, they will be consumed from the input places and the mode will
 * then be in its firing state. Note that if there is multiple modes trying to
 * start their firing at the exact same time, the one with the greatest priority
 * value will fire first, possibly still disabling others. Transitions with the
 * same priority value fire in an undefined, arbitrary order. The duration of
 * the firing state (should it be reached) is determined by sampling from the
 * durationDist. At the end, the mode will create all the necessary tokens in
 * the output places and reset itself again, waiting for another activation
 * (which might even be right away).
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Peter W&uuml;ppen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
public class TransitionMode extends Reportable {

	public static int TRANSITION_DEACTIVATED = 0;
	public static int TRANSITION_ACTIVATED = 1;
	public static int TRANSITION_FIRING = 2;

	public static int TRANSITION_DEFAULTPRIORITY = 1;

	public String name;
	private PetriNet ownerNet;
	private Transition ownerTransition;
	private int priority;

	private NumericalDist<Double> waitDist;
	private NumericalDist<Double> durationDist;
	private Map<Place, Map<TokenType, Integer>> inputPlaces;
	private Map<Place, Map<TokenType, Integer>> outputPlaces;

	private int activationState;

	private FireStartEvent startEvent;
	private FireEndEvent endEvent;

	private TimeInstant lastActivationTime;
	private TimeInstant lastFiringTime;

	// Reported values

	private Tally waitingTimeTally;
	private Tally firingTimeTally;
	private long cancellations;

	/**
	 * Standard constructor, distributions for waiting and firing have to be
	 * given here already.
	 * 
	 * @param owner
	 *            The Model this TransitionMode belongs to
	 * @param ownerNet
	 *            The Petri Net this TransitionMode belongs to
	 * @param name
	 *            Textual identifier for this TransitionMode
	 * @param waitDist
	 *            Distribution to be sampled from for the waiting duration of
	 *            this mode, that is the time between activation and firing.
	 * @param durationDist
	 *            Distribution to be sampled from for the firing duration of
	 *            this mode, that is the time between consuming tokens and
	 *            creating new ones once the mode fires.
	 * @param showInReport
	 *            Whether to produce a Report for this TransitionMode
	 * @param showInTrace
	 *            Whether to show this TransitionMode in the trace log
	 */
	public TransitionMode(Model owner, PetriNet net, String name,
			NumericalDist<Double> waitDist, NumericalDist<Double> durationDist,
			boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);

		this.name = name;
		this.ownerNet = net;
		this.priority = TRANSITION_DEFAULTPRIORITY;

		this.waitDist = waitDist;
		this.durationDist = durationDist;

		activationState = TRANSITION_DEACTIVATED;
		inputPlaces = new HashMap<Place, Map<TokenType, Integer>>();
		outputPlaces = new HashMap<Place, Map<TokenType, Integer>>();

		startEvent = new FireStartEvent(owner, name + "_FireStartEvent", this,
				showInTrace);
		endEvent = new FireEndEvent(owner, name + "_FireEndEvent", this,
				showInTrace);

		setActivationState(TRANSITION_ACTIVATED);

		cancellations = 0;
		waitingTimeTally = new Tally(owner, name + "_waitingTimeTally", false,
				showInTrace);
		firingTimeTally = new Tally(owner, name + "_firingTimeTally", false,
				showInTrace);

	}

	/**
	 * Declares an existing Place as an input place of this TransitionMode and
	 * sets how many default tokens should be consumed from it when firing.
	 * Should the place already exist as an input place, this will reset its
	 * requirements to whatever amount of default tokens is specified, not add
	 * them on top of it. To specify a multiset with different types of tokens
	 * as the requirement, use addInputPlace(Place p, Map<TokenType, Integer>
	 * amounts) instead.
	 * 
	 * 
	 * @param p
	 *            The Place to be listed as an input place.
	 * @param amount
	 *            The new amount of default tokens that this TransitionMode is
	 *            supposed to require from the place for firing
	 */
	public void addInputPlace(Place p, int amount) {
		TokenType type = this.ownerNet.getDefaultTokenType();
		Map<TokenType, Integer> map = new HashMap<TokenType, Integer>();
		map.put(type, amount);
		addInputPlace(p, map);
	}

	/**
	 * Declares an existing Place as an output place of this TransitionMode and
	 * sets how many default tokens should be created in it when firing. Should
	 * the place already exist as an output place, this will reset the current
	 * output multiset to whatever amount of default tokens is specified, not
	 * add them on top of it. To specify a multiset with different types of
	 * tokens to be created, use addOutputPlace(Place p, Map<TokenType, Integer>
	 * amounts) instead.
	 * 
	 * 
	 * @param p
	 *            The Place to be listed as an input place.
	 * @param amount
	 *            The new amount of default tokens that this TransitionMode is
	 *            supposed to require from the place for firing
	 */
	public void addOutputPlace(Place p, int amount) {
		TokenType type = this.ownerNet.getDefaultTokenType();
		Map<TokenType, Integer> map = new HashMap<TokenType, Integer>();
		map.put(type, amount);
		addOutputPlace(p, map);
	}

	/**
	 * Declares an existing Place as an input place for this Transitionmode and
	 * sets a multiset of tokens to be consumed from it when firing. Calling
	 * this multiple times for the same place will replace the old multiset with
	 * the new one, not combine them.
	 * 
	 * @param p
	 *            The Place to be listed as an input place.
	 * @param amounts
	 *            Maps the type of token to the amount of tokens of this type
	 *            that are required to fire the transition
	 */
	public void addInputPlace(Place p, Map<TokenType, Integer> amounts) {

		// Don't allow changing of transitions whose firing process is underway
		if (getActivationState() == TRANSITION_FIRING) {
			sendWarning(
					"Trying to add an input place to a TransitionMode that is currently firing, command ignored",
					"TransitionMode: " + getName() + "; Place: " + p.getName(),
					"Input places cannot be changed while a TransitionMode is in its firing state",
					"Make sure that a TransitionMode is currently deactivated or waiting "
							+ "to fire when you want to change input places while the simulation is running");
			return;
		}

		int amount;
		TokenType type;

		// negative input sizes are not allowed either
		for (Map.Entry<TokenType, Integer> addedentry : amounts.entrySet()) {
			amount = addedentry.getValue();
			if (amount <= 0) {
				sendWarning(
						"Non-positive amount of tokens specified for input relation, command ignored",
						"TransitionMode: " + getName() + "; Place: "
								+ p.getName() + "; TokenType: "
								+ addedentry.getKey().getTokenDescription(),
						"Behaviour for non-positive input relations of transition modes not defined",
						"Make sure to only use amounts greater than zero for any input relation you set");
				return;
			}
		}

		// Input Place change is legit, execute it
		inputPlaces.put(p, amounts);
		p.addOutputTransition(this, amounts);

		// check for state changes of the transition
		boolean deactivation = false;
		boolean checkneeded = true;
		for (Map.Entry<TokenType, Integer> addedentry : amounts.entrySet()) {
			type = addedentry.getKey();
			amount = addedentry.getValue();

			// transition might get deactivated by adding the input place
			if (p.getTokenMap().get(type) == null
					|| amount > p.getTokenMap().get(type)) {
				deactivation = true;
			}

			// transition might be activated if this method is called with a
			// previously entered input place, lowering its amount
			if (p.getTokenMap().get(type) != null
					&& amount > p.getTokenMap().get(type)) {
				checkneeded = false;
			}
		}

		if (getActivationState() == TRANSITION_ACTIVATED && deactivation) {
			setActivationState(TRANSITION_DEACTIVATED);
		}
		if (getActivationState() == TRANSITION_DEACTIVATED && checkneeded) {
			checkActivation();
		}

	}

	/**
	 * Declares an existing Place as an output place for this Transitionmode and
	 * sets a multiset of tokens to be created in it when firing. Calling this
	 * multiple times for the same place will replace the old multiset with the
	 * new one, not combine them.
	 * 
	 * @param p
	 *            The Place to be listed as an output place.
	 * @param amounts
	 *            Maps the type of token to the amount of tokens of this type
	 *            that are to be created when firing the transition
	 */
	public void addOutputPlace(Place p, Map<TokenType, Integer> amounts) {

		// Don't allow changing of transitions whose firing process is underway
		if (getActivationState() == TRANSITION_FIRING) {
			sendWarning(
					"Trying to add an output place to a TransitionMode that is currently firing, command ignored",
					"TransitionMode: " + getName() + "; Place: " + p.getName(),
					"Output places cannot be changed while a TransitionMode is in its firing state",
					"Make sure that a TransitionMode is currently deactivated or waiting "
							+ "to fire when you want to change ouput places while the simulation is running");
			return;
		}

		// negative input sizes are not allowed either
		int amount;
		for (Map.Entry<TokenType, Integer> addedentry : amounts.entrySet()) {
			amount = addedentry.getValue();
			if (amount < 0) {
				sendWarning(
						"Non-positive amount of tokens specified for output relation, command ignored",
						"TransitionMode: " + getName() + "; Place: "
								+ p.getName() + "; TokenType: "
								+ addedentry.getKey().getTokenDescription(),
						"Behaviour for non-positive output relations of transition modes not defined",
						"Make sure to only use amounts greater than zero for any output relation you set");
				return;
			}
		}
		// change legit, execute it
		outputPlaces.put(p, amounts);
	}

	/**
	 * Testing method to determine whether the transition mode is currently
	 * activated. If it is, will call setActivation(true) to start the waiting
	 * time. Gets called automatically whenever a check is needed, should not be
	 * used manually.
	 */
	protected void checkActivation() {

		if ((activationState == TRANSITION_ACTIVATED || activationState == TRANSITION_FIRING)) {
			System.out
					.println("Checking activation of already activated or even firing transition, undesired behaviour");
		} else {

			// Must have sufficient amount of tokens on all input places and for
			// all input types
			for (Map.Entry<Place, Map<TokenType, Integer>> entry : inputPlaces
					.entrySet()) {

				for (Map.Entry<TokenType, Integer> entryinside : entry
						.getValue().entrySet()) {

					if (entry.getKey().getTokenMap().get(entryinside.getKey()) == null
							|| entryinside.getValue() > entry.getKey()
									.getTokenMap().get(entryinside.getKey())) {
						return;
					}
				}

			}
			setActivationState(TRANSITION_ACTIVATED);
		}
		return;
	}

	/**
	 * Method to set the Transition mode to activated and deactivated state
	 * respectively. Called whenever it has to be switched. Switching from
	 * deactivated to activated will schedule firing of the transition after a
	 * random amount of time sampled from the waitDist Distribution (the
	 * numerical value is counted as DESMO-J time-units). Switching from
	 * activated to deactivated will cancel the scheduled firing event.
	 * 
	 * Do not use manually.
	 * 
	 * @param newstate
	 *            The new state of the transition mode
	 */
	protected void setActivationState(int newstate) {
		if (newstate == TRANSITION_ACTIVATED)
			if (activationState == TRANSITION_ACTIVATED
					|| activationState == TRANSITION_FIRING) {
				System.out
						.println("Trying to activate already activated Transition or even firing Transition, should never happen");
			} else {
				activationState = TRANSITION_ACTIVATED;
				lastActivationTime = getModel().presentTime();

				Number waitingTime = waitDist.sample();
				TimeSpan waitingSpan = new TimeSpan(waitingTime.doubleValue());
				
				TimeInstant firingTime = TimeOperations.add(getModel().presentTime(), waitingSpan);

				ownerNet.changeActivation(this, firingTime);
				startEvent.schedule(firingTime);

			}
		else if (newstate == TRANSITION_DEACTIVATED) {

			if (activationState == TRANSITION_DEACTIVATED) {
				System.out
						.println("Trying to deactivate already deactivated Transition, should never happen");
			} else if (activationState == TRANSITION_ACTIVATED) {
				//
				// System.out.println("(time: " + getModel().presentTime()
				// + "): " + name + " cancelled and deactivated");
				cancellations++;
				startEvent.cancel();
				ownerNet.changeActivation(this, null);
				
			} else if (activationState == TRANSITION_FIRING) {
				// System.out.println("(time: " + getModel().presentTime()
				// + "): " + name + " finished and deactivated");
				incrementObservations();
				ownerNet.incrementObservations();
				firingTimeTally.update(getModel().presentTime()
						.getTimeAsDouble() - lastFiringTime.getTimeAsDouble());

			}
			activationState = TRANSITION_DEACTIVATED;

		} else if (newstate == TRANSITION_FIRING) {

			if (activationState == TRANSITION_DEACTIVATED
					|| activationState == TRANSITION_FIRING) {
				System.out
						.println("Trying to start firing of a deactivated or already firing Transition, should never happen");
			} else {
				activationState = TRANSITION_FIRING;
				ownerNet.changeActivation(this, null);
				
				lastFiringTime = getModel().presentTime();

				waitingTimeTally.update(getModel().presentTime()
						.getTimeAsDouble()
						- lastActivationTime.getTimeAsDouble());

				Number firingTime = durationDist.sample();
				
				endEvent.schedule(new TimeSpan(firingTime.doubleValue()));
				// System.out.println("(time: " + getModel().presentTime()
				// + "): " + name
				// + " Firing, scheduling end of firing process in "
				// + firingTime);

			}
		}
	}

	/**
	 * Returns the current transition mode state (deactivated, activated,
	 * firing).
	 * 
	 * @return The current state of the transition mode
	 */
	public int getActivationState() {
		return activationState;
	}

	/**
	 * Called once the waiting time of this Transition mode has passed. Removes
	 * the ingoing tokens, and schedules the end of the firing process, which is
	 * the point in time where new tokens are getting created.
	 */
	public void startFire() {
		if (activationState != TRANSITION_ACTIVATED) {
			System.out
					.println("startFire() called for Transition that is not in state TRANSTION_ACTIVATED, (time: "
							+ getModel().presentTime());
		} else {
			setActivationState(TRANSITION_FIRING);

			for (Map.Entry<Place, Map<TokenType, Integer>> entry : inputPlaces
					.entrySet()) {

				// for (Map.Entry<TokenType, Integer> entryinside : entry
				// .getValue().entrySet()) {
				//
				// if (entry.getKey().getTokenMap().get(entryinside.getKey()) ==
				// null
				// || entryinside.getValue() > entry.getKey()
				// .getTokenMap().get(entryinside.getKey())) {
				// System.out
				// .println("Fatal error, non-activated transition is marked as activated and trying to fire!");
				// return;
				// }
				//
				// }
				entry.getKey().addTokens(
						TokenMultiSetTools.negativeMap(entry.getValue()));
			}

		}
	}

	/**
	 * Ends firing of the transition mode, creating the new tokens at the output
	 * places.
	 */
	public void endFire() {
		if (activationState != TRANSITION_FIRING) {
			System.out
					.println("endFire() called for Transition that is not in state TRANSTION_FIRING, (time: "
							+ getModel().presentTime());
		} else {
			for (Map.Entry<Place, Map<TokenType, Integer>> entry : outputPlaces
					.entrySet()) {

				entry.getKey().addTokens(entry.getValue());
			}

			setActivationState(TRANSITION_DEACTIVATED);
			checkActivation();
		}
	}

	/**
	 * Resets the counter for observations and cancellations made by this
	 * transition mode. Also resets the average waiting and firing times
	 * observed. The point of simulation time this method was called will be
	 * stored and can be retrieved using method <code>resetAt()</code>.
	 */
	public void reset() {

		waitingTimeTally.reset();
		firingTimeTally.reset();
		cancellations = 0;
		super.reset();
	}

	/**
	 * Returns the average time this transition mode has waited for firing after
	 * activation. Does not count instances where the waiting process was
	 * cancelled.
	 * 
	 * @return The average waiting time
	 */
	public double getAvgWaitingTime() {
		return waitingTimeTally.getMean();
	}

	/**
	 * Returns the average time this transition mode has taken for firing.
	 * 
	 * @return The average firing time
	 */
	public double getAvgFiringTime() {
		return firingTimeTally.getMean();
	}

	/**
	 * Returns the amount of cancellations that this transition mode observed. A
	 * cancellation happens whenever the mode is deactivated after activation
	 * before the waiting time passes and it being allowed to fire.
	 * 
	 * @return The average waiting time
	 */
	public long getCancellations() {
		return cancellations;
	}

	/**
	 * Returns the Reporter for this transition mode.
	 * 
	 * @return The Reporter for this transition mode.
	 */
	public TransitionModeReporter createDefaultReporter() {
		return new TransitionModeReporter(this);
	}

	/**
	 * Returns the PetriNet that this Transition mode belongs to
	 * 
	 * @return The PetriNet that this Transition mode is a part of
	 */
	public PetriNet getOwnerNet() {
		return ownerNet;
	}

	/**
	 * Associates this transition mode with a Transition. This will not cause
	 * any different behaviour on the Transition mode's part.
	 * 
	 * @param t
	 *            The transition to associate this mode to.
	 */
	public void setTransition(Transition t) {
		ownerTransition = t;
	}

	/**
	 * Returns the Transition that this object is a mode of.
	 * 
	 * @return The associated Transition
	 */
	public Transition getTransition() {
		return ownerTransition;
	}

	/**
	 * Returns the priority value of this mode regarding direct conflicts with
	 * other modes.
	 * 
	 * @return The priority value
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority value for this mode (default is 1). It is relevant when
	 * the mode tries to start its firing process exactly the same time as other
	 * modes (since they might be a direct conflict around tokens). The mode
	 * with the highest priority value will always fire first in these cases,
	 * possibly disabling others. Note that this has no impact if the modes do
	 * not try to start firing at the exact same time, in which case the earlier
	 * transition will always be the first one to start firing regardless of
	 * priority value.
	 * 
	 * @param priority
	 *            The priority value
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Internal Event to schedule the end of the firing process.
	 * 
	 * @author Peter W&uuml;ppen
	 * 
	 */
	private static class FireEndEvent extends ExternalEvent {

		private TransitionMode trans;

		public FireEndEvent(Model owner, String name,
				TransitionMode transition, boolean showInTrace) {
			super(owner, name, showInTrace);
			trans = transition;
		}

		@Override
		public void eventRoutine() {
			trans.endFire();

		}

	}

	/**
	 * Internal Event to schedule the start of the firing process.
	 * 
	 * @author Peter W&uuml;ppen
	 * 
	 */
	private static class FireStartEvent extends ExternalEvent {

		private TransitionMode trans;

		public FireStartEvent(Model owner, String name,
				TransitionMode transition, boolean showInTrace) {
			super(owner, name, showInTrace);
			trans = transition;
		}

		public void eventRoutine() {

			trans.getOwnerNet().firePrioritized();

		}

	}

}
