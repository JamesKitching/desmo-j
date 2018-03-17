package desmoj.extensions.applicationDomains.petriNets.examples;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.DiscreteDistConstant;
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
public class PriorityModel extends Model {

	private PetriNet _net;

	private Place p1;

	private Transition t1;
	private Transition t2;

	private DiscreteDistConstant<Double> dist1;

	public PriorityModel(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {

		return "Model for demonstration of boundedness by priority (stochastic petri nets)";
	}

	@Override
	public void doInitialSchedules() {
		// Nothing should have to be scheduled manually, the net will
		// automatically get active and run according to the firing rules once
		// the simulation is started.

	}

	@Override
	public void init() {

		// Instantiation of all the parts of the net. More detail on the
		// constructors and methods used can be found in their respective
		// classes.

		_net = new PetriNet(this, "PriorityNet", true, true);

		dist1 = new DiscreteDistConstant<Double>(this, "waitdist1", 1.0, true,
				true);

		p1 = new Place(this, _net, "p5_1", 0, true, true);

		t1 = new Transition(this, _net, "t1", dist1, dist1, true, true);
		t2 = new Transition(this, _net, "t2", dist1, dist1, true, true);

		t1.setPriority(1);
		t2.setPriority(2);

		t1.addOutputPlace(p1, 1);
		
		t2.addInputPlace(p1, 1);


	}

	public static void main(String[] args) {

		PriorityModel model = new PriorityModel(null,
				"Example Priority Model", true, true);
		Experiment exp = new Experiment("testexp1");

		model.connectToExperiment(exp);

		exp.setShowProgressBar(true); // display a progress bar (or not)
		exp.stop(new TimeInstant(100, TimeUnit.MINUTES)); // set end of
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
