package desmoj.demo.simple_super_model;

import desmoj.core.simulator.*;
import desmoj.core.statistic.Count;
import desmoj.demo.balticSea_single.BalticSeaModel;
import desmoj.demo.vc_model.VancarrierModel;

/**
 * Just combining two models for testing purposes
 */
public class SuperModel extends Model  {
    
    Count modelCounter;
    
	public SuperModel() {
		super(null, "SuperModel", true, true);
	}

	/**
	 * Describes what the model does
	 */
	public String description() {
		return "2in1";
	}

	public void doInitialSchedules() {
	    
		// nohting to do (that's submodels' business)
	    modelCounter.update();
	    modelCounter.update();
	}
	
	/**
	 * This method is used to initialize all 
	 * DESMO-J infrastructure we use
	 */
	public void init() {

	    this.modelCounter = new Count(this, "Number of models", true, true);
        new BalticSeaModel(this); 
        new VancarrierModel(this, "VC-Model", true, true);
	}
	
	/**
	 * Starts the application.
	 *
	 * In DESMO-J used to 
	 *    - instantiate the experiment
	 *    - instantiate the model
	 *    - connect the model to the experiment
	 *		- steer length of simulation and outputs
	 *		- start the simulation
	 *		- set the ending criteria (normally the time)
	 *		- initiate reporting
	 *		- clean up the experiment
	 *
	 * @param args  : is an array of command-line arguments
	 */
	public static void main(java.lang.String[] args) {

		Experiment experiment = new Experiment("Combined Experiment");
		SuperModel model = new SuperModel();
		model.connectToExperiment(experiment);
		experiment.tracePeriod(new TimeInstant(0), new TimeInstant(100));
		experiment.stop(new TimeInstant(1000));
		experiment.setShowProgressBar(false);

		// start the Experiment with start time 0.0
		experiment.start();

		// --> now the simulation is running until it reaches its ending criteria
		// ...
		// ...
		// <-- after reaching ending criteria, the main thread returns here

		// print the report about the already existing reporters into the report file
		experiment.report();

		// stop all threads still alive and close all output files
		experiment.finish();
	}
}