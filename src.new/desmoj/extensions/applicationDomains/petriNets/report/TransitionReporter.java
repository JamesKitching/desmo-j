package desmoj.extensions.applicationDomains.petriNets.report;

import desmoj.core.report.Reporter;
import desmoj.core.simulator.Reportable;
import desmoj.extensions.applicationDomains.petriNets.Transition;

/**
 * Reports all information about a Transition object.
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
public class TransitionReporter extends Reporter{

	/**
	 * Creates a newTransitionReporter.
	 * 
	 * @param informationSource
	 *            desmoj.core.simulator.Reportable : The Transition to
	 *            report about
	 */
	public TransitionReporter(Reportable informationSource) {
		super(informationSource);

		numColumns = 7;
		columns = new String[numColumns];
		entries = new String[numColumns];
		groupID = 2100; 

		columns[0] = "Title";
		columns[1] = "Net";
		columns[2] = "Transition Modes";
		columns[3] = "(Re)set";
		columns[4] = "Obs";
		columns[5] = "Cancellations";
		groupHeading = "Petri Net Components: Transitions";
	}

	/**
	 * Returns the array of strings containing all information about the
	 * Transition.
	 * 
	 * @return java.lang.String[] : The array of Strings containing all
	 *         information about the place.
	 */
	public java.lang.String[] getEntries() {

		if (source instanceof Transition) {
			// Title
			entries[0] = source.getName();
			// Net
			entries[1] = ((Transition)source).getOwnerNet().getName();
			// Modes
			entries[2] = ((Transition)source).getTransitionModesString();
			// (Re)set
			entries[3] =  ((Transition)source).resetAt().toString();
			// Obs
			entries[4] = Long.toString(source.getObservations());
			// Cancellations
			entries[5] = Long.toString(((Transition)source).getCancellations());
			

		} else {
			for (int i = 0; i < numColumns; i++) {
				entries[i] = "Invalid source!";
			}
		}

		return entries;

	}
}
