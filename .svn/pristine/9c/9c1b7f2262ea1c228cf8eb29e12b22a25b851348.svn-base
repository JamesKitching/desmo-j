package desmoj.extensions.applicationDomains.petriNets.report;

import desmoj.core.report.Reporter;
import desmoj.extensions.applicationDomains.petriNets.Place;

/**
 * Reports all information about a Place object.
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
public class PlaceReporter extends Reporter{

	
	/**
	 * Creates a new PlaceReporter.
	 * 
	 * @param informationSource
	 *            desmoj.core.simulator.Reportable : The PetriNet to
	 *            report about
	 */
	public PlaceReporter(
			desmoj.core.simulator.Reportable informationSource) {

		super(informationSource);

		numColumns = 6;
		columns = new String[numColumns];
		entries = new String[numColumns];
		groupID = 2200;

		columns[0] = "Title";
		columns[1] = "Net";
		columns[2] = "(Re)set";
		columns[3] = "Obs";
		columns[4] = "Avg. Amount";
		columns[5] = "Cancellations caused";
		groupHeading = "Petri Net Components: Places";

	}
	

	/**
	 * Returns the array of strings containing all information about the
	 * place.
	 * 
	 * @return java.lang.String[] : The array of Strings containing all
	 *         information about the place.
	 */
	public java.lang.String[] getEntries() {

		if (source instanceof Place) {
			// Title
			entries[0] = source.getName();
			// Net
			entries[1] = ((Place)source).getOwnerNet().getName();
			// (Re)set
			entries[2] = source.resetAt().toString();
			// Obs
			entries[3] = Long.toString(source.getObservations());
			// Avg. Amount
			entries[4] = ((Place)source).getAverageString();
			// Cancellations
			entries[5] = Integer.toString(((Place) source).getCancellations());

		} else {
			for (int i = 0; i < numColumns; i++) {
				entries[i] = "Invalid source!";
			}
		}

		return entries;

	}
}
