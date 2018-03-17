package desmoj.extensions.experimentation.ui;

import java.util.List;

/**
 * Interface for graphical user interfaces for the Experiment Starter. This is
 * Interface is used by {@link ExperimentStarter ExperimentStarter}
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Gunnar Kiesel
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

public interface ExperimentStarterGUI {

	/**
	 * should reset the gui to start settings
	 * 
	 * @param filename
	 *            String: the name of the configuration file in use
	 * @param runsBatch
	 *            boolean: <code>true</code> if a batch run is to be made,
	 *            <code>false</code> for single experiment runs
	 * @param modelAccessPoints
	 *            AccessPointTableModel: the access points for the model settings
	 * @param expAccessPoints
	 *            AccessPointTableModel: the access points for the experiment settings
	 * @param modelName
	 *            Sting: the name of the model in use
	 */
	void resetGUI(String filename, boolean runsBatch,
			AccessPointTableModel modelAccessPoints, AccessPointTableModel expAccessPoints,
			String modelName);

	/**
	 * Called by ExperimentStarter when the currently active experiment's
	 * SimClock is advanced
	 * 
	 * @param currentTime
	 *            String: the current simulation time
	 * @param timePercent
	 *            double: percentage of the simulation done
	 * @param startTime
	 *            long: the start time of the simulation
	 */
	public void clockAdvanced(String currentTime, double timePercent,
			long startTime);

	/** Called by ExperimentStarter when a new Model shall be loaded */
	public void loadModel();

	/***************************************************************************
	 * should open a new FileDialog to load a File and return the new filename
	 * if no new filename is given currentFilename should be returned
	 * 
	 * @param currentFilename
	 *            String: the filename of the configuration in use
	 **************************************************************************/
	public String loadDialog(String currentFilename);

	/***************************************************************************
	 * should open a new FileDialog to save a File currentFilename should be
	 * preselected if no new filename is given null should be returned
	 * 
	 * @param currentFilename
	 *            String: the filename of the configuration in use
	 **************************************************************************/
	public String saveDialog(String currentFilename);

	/** should set all buttons etc. for a running experiment */
	public void setRunning();

	/**
	 * should set all buttons etc. for a stopped experiment
	 * 
	 * @param currentTime
	 *            String: the current simulation time
	 * @param startTime
	 *            long: the starting time of the experiment run
	 * @param experimentValues
	 *            String: the current values of the experiment parameters
	 * @param outputPath
	 *            String: the path the experiment output is written to
	 * @param appendixes
	 *            List<List<String>>: the file endings (e.g. .html, .txt, .xml) of the four output channels. May be more than one output per channel, thus the list of lists 
	 */
	public void setStopped(String currentTime, long startTime,
			String experimentValues, String outputPath, List<List<String>> appendixes);

	/** should set all buttons etc. for a paused experiment */
	public void setPaused();

}