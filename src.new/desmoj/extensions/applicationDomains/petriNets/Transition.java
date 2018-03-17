package desmoj.extensions.applicationDomains.petriNets;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import desmoj.core.dist.NumericalDist;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Reportable;
import desmoj.extensions.applicationDomains.petriNets.report.TransitionReporter;

/**
 * Represents a Transition of a certain Petri net. It contains an arbitrary
 * amount of Transition modes to fire. All these modes work completely
 * independently from each other and can fire simultaneously.
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
public class Transition extends Reportable {

	private Set<TransitionMode> transitionModes;
	private PetriNet ownerNet;
	private TransitionMode defaultMode;

	/**
	 * Standard constructor. Does not add any Transition Modes just yet.
	 * 
	 * @param owner
	 *            The Model this Transition belongs to
	 * @param ownerNet
	 *            The Petri Net this Transition belongs to
	 * @param name
	 *            Textual identifier for this Transition
	 * @param showInReport
	 *            Whether to produce a Report for this Transition
	 * @param showInTrace
	 *            Whether to show this Transition in the trace log
	 */
	public Transition(Model owner, PetriNet ownerNet, String name,
			boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
		this.ownerNet = ownerNet;
		ownerNet.addTransition(this);
		transitionModes = new HashSet<TransitionMode>();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for a Transition that is supposed to only have one
	 * TransitionMode, thus being uncolored. This will internally create a
	 * default mode with the specified parameters.
	 * 
	 * For more information on the Transitions firing behaviour, refer to the
	 * commentary within the TransitionMode class.
	 * 
	 * @param owner
	 *            The Model this Transition belongs to
	 * @param ownerNet
	 *            The Petri Net this Transition belongs to
	 * @param name
	 *            Textual identifier for this Transition
	 * @param waitDist
	 *            The Distribution to sample the waiting duration of this
	 *            Transition from
	 * @param durationDist
	 *            The Distribution to sample the firing duration of this
	 *            Transition from
	 * @param showInReport
	 *            Whether to produce a Report for this Transition
	 * @param showInTrace
	 *            Whether to show this Transition in the trace log
	 */
	public Transition(Model owner, PetriNet ownerNet, String name,
			NumericalDist<Double> waitDist, NumericalDist<Double> durationDist,
			boolean showInReport, boolean showInTrace) {
		super(owner, name, false, showInTrace);

		this.ownerNet = ownerNet;
		ownerNet.addTransition(this);
		transitionModes = new HashSet<TransitionMode>();

		defaultMode = new TransitionMode(owner, ownerNet, "Default", waitDist,
				durationDist, showInReport, showInTrace);
		addTransitionMode(defaultMode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Adds a fully new TransitionMode to the already existing modes of this
	 * Transition.
	 * 
	 * @param mode
	 *            The TransitionMode to be added
	 */
	public void addTransitionMode(TransitionMode mode) {
		transitionModes.add(mode);
		mode.setTransition(this);
	}

	/**
	 * Adds a new input place to the default TransitionMode of this Transition.
	 * Note that this is only to be used if the Transition is supposed to only
	 * have one mode, and was thus instantiated using the appropriate
	 * constructor. Will not do anything if being used on a Transition that has
	 * no default mode.
	 * 
	 * 
	 * @param p
	 *            The Place to be listed as an input place.
	 * @param amount
	 *            The new amount of default tokens that this Transition is
	 *            supposed to require from the place for firing
	 */
	public void addInputPlace(Place p, int amount) {
		if (defaultMode != null) {
			defaultMode.addInputPlace(p, amount);
		} else {
			sendWarning(
					"Trying to add an input place to a Transition that "
							+ "possibly has multiple modes",
					"Transition: " + getName() + "; Place: " + p.getName(),
					"Method addInputPlace(Place p, int amount) is only to be "
							+ "used on Transitions that only have a single mode and are"
							+ " constructed as such",
					"To create a Transition with only a single mode, use the"
							+ " public Transition(Model owner, PetriNet ownerNet, String"
							+ " name, NumericalDist<Double> waitDist, NumericalDist<Double>"
							+ " durationDist, boolean showInReport, boolean showInTrace) constructor");
		}
	}

	/**
	 * Adds a new input place to the default TransitionMode of this Transition.
	 * Note that this is only to be used if the Transition is supposed to only
	 * have one mode, and was thus instantiated using the appropriate
	 * constructor. Will not do anything if being used on a Transition that has
	 * no default mode.
	 * 
	 * @param p
	 *            The Place to be listed as an input place.
	 * @param amount
	 *            A map of TokenTypes and their respective amounts to be set as
	 *            a requirement for firing the default Transition mode
	 */
	public void addInputPlace(Place p, Map<TokenType, Integer> amounts) {
		if (defaultMode != null) {
			defaultMode.addInputPlace(p, amounts);
		} else {
			sendWarning(
					"Trying to add an input place to a Transition that "
							+ "possibly has multiple modes",
					"Transition: " + getName() + "; Place: " + p.getName(),
					"Method addInputPlace(Place p, int amount) is only to be "
							+ "used on Transitions that only have a single mode and are"
							+ " constructed as such",
					"To create a Transition with only a single mode, use the"
							+ " public Transition(Model owner, PetriNet ownerNet, String"
							+ " name, NumericalDist<Double> waitDist, NumericalDist<Double>"
							+ " durationDist, boolean showInReport, boolean showInTrace) constructor");
		}
	}

	/**
	 * Adds a new output place to the default TransitionMode of this Transition.
	 * Note that this is only to be used if the Transition is supposed to only
	 * have one mode, and was thus instantiated using the appropriate
	 * constructor. Will not do anything if being used on a Transition that has
	 * no default mode.
	 * 
	 * @param p
	 *            The Place to be listed as an output place.
	 * @param amount
	 *            The new amount of default tokens that this Transition is
	 *            supposed to create in the Place when firing
	 */
	public void addOutputPlace(Place p, int amount) {
		if (defaultMode != null) {
			defaultMode.addOutputPlace(p, amount);
		} else {
			sendWarning(
					"Trying to add an output place to a Transition that "
							+ "possibly has multiple modes",
					"Transition: " + getName() + "; Place: " + p.getName(),
					"Method addOutputPlace(Place p, int amount) is only to be "
							+ "used on Transitions that only have a single mode and are"
							+ " constructed as such",
					"To create a Transition with only a single mode, use the"
							+ " public Transition(Model owner, PetriNet ownerNet, String"
							+ " name, NumericalDist<Double> waitDist, NumericalDist<Double>"
							+ " durationDist, boolean showInReport, boolean showInTrace) constructor");
		}
	}

	/**
	 * Adds a new output place to the default TransitionMode of this Transition.
	 * Note that this is only to be used if the Transition is supposed to only
	 * have one mode, and was thus instantiated using the appropriate
	 * constructor. Will not do anything if being used on a Transition that has
	 * no default mode.
	 * 
	 * @param p
	 *            The Place to be listed as an output place.
	 * @param amount
	 *            A map of TokenTypes and their respective amounts to be created
	 *            in the specified Place when firing the default Transition mode
	 */
	public void addOutputPlace(Place p, Map<TokenType, Integer> amounts) {
		if (defaultMode != null) {
			defaultMode.addOutputPlace(p, amounts);
		} else {
			sendWarning(
					"Trying to add an output place to a Transition that "
							+ "possibly has multiple modes",
					"Transition: " + getName() + "; Place: " + p.getName(),
					"Method addOutputPlace(Place p, int amount) is only to be "
							+ "used on Transitions that only have a single mode and are"
							+ " constructed as such",
					"To create a Transition with only a single mode, use the"
							+ " public Transition(Model owner, PetriNet ownerNet, String"
							+ " name, NumericalDist<Double> waitDist, NumericalDist<Double>"
							+ " durationDist, boolean showInReport, boolean showInTrace) constructor");
		}
	}

	/**
	 * Sets the priority value for the default mode of this transition (default
	 * value is 1). It is relevant when the mode tries to start its firing
	 * process exactly the same time as other modes (since they might be a
	 * direct conflict around tokens). The mode with the highest priority value
	 * will always fire first in these cases, possibly disabling others. Note
	 * that this has no impact if the modes do not try to start firing at the
	 * exact same time, in which case the earlier transition will always be the
	 * first one to start firing regardless of priority value.
	 * 
	 * Will not do anything if the Transition does not have a default mode. Call
	 * setPriority() for its TransitionModes then instead.
	 * 
	 * @param priority
	 *            The priority value
	 */
	public void setPriority(int priority) {
		
		if (defaultMode != null) {
			defaultMode.setPriority(priority);
		} else {
			sendWarning(
					"Trying set the priority of a Transition that "
							+ "possibly has multiple modes",
					"Transition: " + getName(),
					"Method setPriority(int priority) of class Transition is only to be "
							+ "used on Transitions that only have a single mode and are"
							+ " constructed as such",
					"Use setPriority(int priority) on the specific TransitionModes instead");
		}
	}

	/**
	 * Returns the Reporter for this Transition.
	 */
	public TransitionReporter createDefaultReporter() {
		return new TransitionReporter(this);
	}

	/**
	 * Resets the counter for observations and cancellations made by this
	 * Transition. Also resets all the statistical values observed by all the
	 * contained Transition modes The point of simulation time this method was
	 * called will be stored and can be retrieved using method
	 * <code>resetAt()</code>.
	 */
	public void reset() {
		super.reset();
		for (TransitionMode mode : transitionModes) {

			mode.reset();
		}

	}

	/**
	 * Returns the amount of times that any TransitionMode of this Transition
	 * has been firing so far.
	 */
	public long getObservations() {
		long result = 0;
		for (TransitionMode mode : transitionModes) {

			result = result + mode.getObservations();
		}
		return result;
	}

	/**
	 * Returns the amount of times that any TransitionMode of this Transition
	 * has been cancelled so far.
	 */
	public long getCancellations() {
		long result = 0;
		for (TransitionMode mode : transitionModes) {

			result = result + mode.getCancellations();
		}
		return result;
	}

	/**
	 * Returns a textual representation of all the Transition modes this
	 * Transition has
	 * 
	 * @return A String containing the names of the Transition modes
	 */
	public String getTransitionModesString() {
		String s = "";
		for (TransitionMode mode : transitionModes) {
			s = s.concat(mode.getName() + "; ");
		}
		return s;
	}

	/**
	 * Returns the PetriNet that this Transition belongs to
	 * 
	 * @return The PetriNet that this Transition is a part of
	 */
	public PetriNet getOwnerNet() {
		return ownerNet;
	}
}