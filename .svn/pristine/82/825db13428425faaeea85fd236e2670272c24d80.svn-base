package desmoj.extensions.applicationDomains.petriNets.report;

import desmoj.core.report.Reporter;
import desmoj.core.simulator.Reportable;
import desmoj.extensions.applicationDomains.petriNets.PetriNet;

/**
 * Reports all information about a PetriNet object.
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
 *
 */
public class PetriNetReporter extends Reporter {

	/**
	 * Creates a new PetriNetReporter.
	 * 
	 * @param informationSource
	 *            desmoj.core.simulator.Reportable : The PetriNet to
	 *            report about
	 */
	public PetriNetReporter(Reportable informationSource) {

		super(informationSource);

		numColumns = 5;
		columns = new String[numColumns];
		entries = new String[numColumns];
		groupID = 2300;

		columns[0] = "Title";
		columns[1] = "(Re)set";
		columns[2] = "Places";
		columns[3] = "Transitions";
		columns[4] = "Obs. state changes";
		groupHeading = "Petri Nets";
	}

	/**
	 * Returns the array of strings containing all information about the petri
	 * net.
	 * 
	 * @return java.lang.String[] : The array of Strings containing all
	 *         information about the place.
	 */
	public String[] getEntries() {
		if (source instanceof PetriNet) {
			// Title
			entries[0] = source.getName();
			// (Re)set
			entries[1] = source.resetAt().toString();
			// Places
			entries[2] = ((PetriNet) source).getPlacesString();
			// Transitions
			entries[3] = ((PetriNet) source).getTransitionString();
			// Obs state changes
			entries[4] = Long.toString(source.getObservations());

		} else {
			for (int i = 0; i < numColumns; i++) {
				entries[i] = "Invalid source!";
			}
		}

		return entries;
	}

}
