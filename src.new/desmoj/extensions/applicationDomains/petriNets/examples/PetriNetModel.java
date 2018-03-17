package desmoj.extensions.applicationDomains.petriNets.examples;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import desmoj.extensions.applicationDomains.petriNets.PetriNet;
import desmoj.extensions.applicationDomains.petriNets.Place;
import desmoj.extensions.applicationDomains.petriNets.Transition;

/**
 * Model using the capabilities of simulated stochastic petri nets. This is an
 * example of usage for uncolored nets.
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
public class PetriNetModel extends Model {

	public PetriNetModel(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {

		return "Model for demonstration of usage (stochastic petri nets)";
	}

	@Override
	public void doInitialSchedules() {
		// Nothing should have to be scheduled manually, the net will
		// automatically get active and run according to the firing rules once
		// the simulation is started.
	}

	@Override
	public void init() {
		
		// Instantiation of all the parts of the net. More detail on the constructors and methods used can be found in their respective classes.

	    PetriNet _net = new PetriNet(this, "PhilosopherNet", true, true);
		
	    ContDistExponential dist1 = new ContDistExponential(this, "waitdist1", 2, true, true);
	    ContDistExponential dist2 = new ContDistExponential(this, "waitdist1", 3, true, true);
	    ContDistExponential dist3 = new ContDistExponential(this, "durdist1", 3, true, true);

		Place p1 = new Place(this, _net, "p1", 1, true, true);
		Place p2 = new Place(this, _net, "p2", 1, true, true);
		Place p3 = new Place(this, _net, "p3", 1, true, true);
		Place p4 = new Place(this, _net, "p4", 1, true, true);
		Place p5 = new Place(this, _net, "p5", 1, true, true);

		Transition t1 = new Transition(this, _net, "t1", dist1, dist3, true, true);
		Transition t2 = new Transition(this, _net, "t2", dist2, dist3, true, true);
		Transition t3 = new Transition(this, _net, "t3", dist1, dist3, true, true);
		Transition t4 = new Transition(this, _net, "t4", dist2, dist3, true, true);
		Transition t5 = new Transition(this, _net, "t5", dist1, dist3, true, true);

		t1.addInputPlace(p1, 1);
		t1.addInputPlace(p2, 1);
		t1.addOutputPlace(p1, 1);
		t1.addOutputPlace(p2, 1);

		t2.addInputPlace(p3, 1);
		t2.addInputPlace(p2, 1);
		t2.addOutputPlace(p3, 1);
		t2.addOutputPlace(p2, 1);

		t3.addInputPlace(p3, 1);
		t3.addInputPlace(p4, 1);
		t3.addOutputPlace(p3, 1);
		t3.addOutputPlace(p4, 1);

		t4.addInputPlace(p5, 1);
		t4.addInputPlace(p4, 1);
		t4.addOutputPlace(p5, 1);
		t4.addOutputPlace(p4, 1);

		t5.addInputPlace(p5, 1);
		t5.addInputPlace(p1, 1);
		t5.addOutputPlace(p5, 1);
		t5.addOutputPlace(p1, 1);

	}

	public static void main(String[] args) {

		PetriNetModel model = new PetriNetModel(null, "Example Philosopher Model", true,
				true);
		Experiment exp = new Experiment("testexp");

		model.connectToExperiment(exp);

		exp.setShowProgressBar(true); // display a progress bar (or not)
		exp.stop(new TimeInstant(100000, TimeUnit.MINUTES)); // set end of
																// simulation
		exp.tracePeriod(new TimeInstant(0), new TimeInstant(100,
				TimeUnit.MINUTES));
		exp.debugPeriod(new TimeInstant(0), new TimeInstant(50,
				TimeUnit.MINUTES));

		exp.start();
		exp.report();
		exp.finish();
	}

}
