package desmoj.extensions.applicationDomains.petriNets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.Reportable;
import desmoj.core.simulator.TimeInstant;
import desmoj.extensions.applicationDomains.petriNets.report.PetriNetReporter;

/**
 * Class modelling a stochastic petri net. It is defined by its Places and
 * Transitions and has its own default TokenType.
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
public class PetriNet extends Reportable {

	Set<Place> places;
	Set<Transition> transitions;
	TokenType defaultToken;

	Map<TransitionMode, TimeInstant> activatedModes;
	TreeMap<Integer, TransitionMode> currentPriorities;

	public PetriNet(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);

		places = new HashSet<Place>();
		transitions = new HashSet<Transition>();
		defaultToken = new DefaultToken();

		activatedModes = new HashMap<TransitionMode, TimeInstant>();
		currentPriorities = new TreeMap<Integer, TransitionMode>();
	}

	/**
	 * Adds a Place to the Net. This is done internally when a new Place gets
	 * instantiated using this PetriNet as parameter. Should not be used
	 * manually.
	 * 
	 * @param p
	 *            The Place to be added to the net.
	 */
	protected void addPlace(Place p) {
		places.add(p);
	}

	/**
	 * Adds a Transition to the Net. This is done internally when a new
	 * Transition gets instantiated using this PetriNet as parameter. Should not
	 * be used manually.
	 * 
	 * @param t
	 *            The Transition to be added to the net.
	 */
	public void addTransition(Transition t) {
		transitions.add(t);
	}

	/**
	 * 
	 * @return A textual representation of all Places of this net, to be used in
	 *         the Report.
	 */
	public String getPlacesString() {
		String result = "";

		for (Place p : places) {
			result = result + p.getName() + ", ";
		}

		if (result.length() != 0) {
			result = result.substring(0, result.length() - 2);
		}

		return result;
	}

	/**
	 * 
	 * @return A textual representation of all Transitions of this net, to be
	 *         used in the Report.
	 */
	public String getTransitionString() {

		String result = "";

		for (Transition t : transitions) {
			result = result + t.getName() + ", ";
		}

		if (result.length() != 0) {
			result = result.substring(0, result.length() - 2);
		}

		return result;
	}

	/**
	 * 
	 * @return The default TokenType of this net which is automatically used
	 *         whenever no TokenType is specified.
	 */
	public TokenType getDefaultTokenType() {
		return defaultToken;
	}

	/**
	 * 
	 * @return A PetriNetReporter to report about this object.
	 */
	public PetriNetReporter createDefaultReporter() {
		return new PetriNetReporter(this);
	}

	/**
	 * Notifies the Net of the activation of a transition mode, for priority
	 * purposes
	 * 
	 * Calling with null as TimeInstant means the mode is not activated anymore
	 */
	protected void changeActivation(TransitionMode mode, TimeInstant time) {

		if (time != null) {
			activatedModes.put(mode, time);
		} else {
			activatedModes.remove(mode);
		}
	}

	/**
	 * Fires all Transition Modes that are to be fired in this very moment, in
	 * order of their (descending) priority.
	 */
	protected void firePrioritized() {
		for (Map.Entry<TransitionMode, TimeInstant> entry : activatedModes
				.entrySet()) {

			if (entry.getValue() != null
					&& entry.getValue().equals(presentTime())) {
				currentPriorities.put(entry.getKey().getPriority(),
						entry.getKey());
			}
		}

		// There might be more checks than needed, which happens whenever a mode
		// gets cancelled by the start of the firing process of another one
		if (!currentPriorities.isEmpty()) {
			TransitionMode nextMode = currentPriorities.lastEntry().getValue();
			nextMode.startFire();

			currentPriorities.clear();
		}

	}

}
