package desmoj.extensions.applicationDomains.petriNets.report;

import desmoj.core.report.Reporter;
import desmoj.core.simulator.Reportable;
import desmoj.core.statistic.StatisticObject;
import desmoj.extensions.applicationDomains.petriNets.TransitionMode;

/**
 * Reports all information about a TransitionMode object.
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
public class TransitionModeReporter extends Reporter{

	/**
	 * Creates a newTransitionModeReporter.
	 * 
	 * @param informationSource
	 *            desmoj.core.simulator.Reportable : The TransitionMode to
	 *            report about
	 */
	public TransitionModeReporter(Reportable informationSource) {
		super(informationSource);

		numColumns = 8;
		columns = new String[numColumns];
		entries = new String[numColumns];
		groupID = 2000; 

		columns[0] = "Title";
		columns[1] = "Transition";
		columns[2] = "Net";
		columns[3] = "(Re)set";
		columns[4] = "Obs";
		columns[5] = "Cancellations";
		columns[6] = "Avg. Waiting Time";
		columns[7] = "Avg. Firing Time";
		groupHeading = "Petri Net Components: Transition Modes";
	}

	/**
	 * Returns the array of strings containing all information about the
	 * TransitionMode.
	 * 
	 * @return java.lang.String[] : The array of Strings containing all
	 *         information about the place.
	 */
	public java.lang.String[] getEntries() {

		if (source instanceof TransitionMode) {
			// Title
			entries[0] = source.getName();
			// Transition this Mode belongs to
			if (((TransitionMode)source).getTransition() != null)
			{
			entries[1] = ((TransitionMode)source).getTransition().getName();
			}
			else
			{
			entries[1] = "No Transition assigned";
			}
			// Net
			entries[2] = ((TransitionMode)source).getOwnerNet().getName();
			// (Re)set
			entries[3] = source.resetAt().toString();
			// Obs
			entries[4] = Long.toString(source.getObservations());
			// Cancellations
			entries[5] = Long.toString(((TransitionMode)source).getCancellations());
			// Avg. Waiting Time
			entries[6] = Double.toString(StatisticObject.round(((TransitionMode)source).getAvgWaitingTime()));
			// Avg. Firing Time
			entries[7] = Double.toString(StatisticObject.round(((TransitionMode)source).getAvgFiringTime()));

		} else {
			for (int i = 0; i < numColumns; i++) {
				entries[i] = "Invalid source!";
			}
		}

		return entries;

	}
}
