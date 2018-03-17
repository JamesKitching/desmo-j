package desmoj.extensions.applicationDomains.petriNets.examples;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import desmoj.extensions.applicationDomains.petriNets.PetriNet;
import desmoj.extensions.applicationDomains.petriNets.Place;
import desmoj.extensions.applicationDomains.petriNets.TokenMultiSetTools;
import desmoj.extensions.applicationDomains.petriNets.TokenType;
import desmoj.extensions.applicationDomains.petriNets.Transition;
import desmoj.extensions.applicationDomains.petriNets.TransitionMode;

/**
 * Model using the capabilities of simulated stochastic petri nets. This is an
 * example of usage for colored nets.
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
public class ColoredNetModel extends Model {
    
    public static class Fork implements TokenType {
        public String getTokenDescription() {
            return "Fork";
        }
    }    

	public ColoredNetModel(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {

		return "Model for testing purposes (colored stochastic petri nets)";
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

	    PetriNet _net = new PetriNet(this, "PhilosopherNet", true, true);
	    ContDistExponential dist1 = new ContDistExponential(this, "waitdist1", 2, true, true);
	    ContDistExponential dist2 = new ContDistExponential(this, "waitdist1", 3, true, true);
	    ContDistExponential dist3 = new ContDistExponential(this, "durdist1", 3, true, true);

		// Declaration of the used types of Tokens. These have to be objects
		// implementing the TokenType interface. Note that there is only one
		// object for each type, no matter how many Tokens of that type may
		// exist. A different object (even of the same class) always declares a
		// new, different type of token.
		
		TokenType type1 = new Fork();
		TokenType type2 = new Fork();
		TokenType type3 = new Fork();
		TokenType type4 = new Fork();
		TokenType type5 = new Fork();
		Map<TokenType, Integer> tokens = TokenMultiSetTools.arrayToMap(
				new TokenType[] { type1, type2, type3, type4, type5 },
				new int[] { 1, 1, 1, 1, 1 });

		Place p1 = new Place(this, _net, "p1", tokens, true, true, true);

		TransitionMode t1 = new TransitionMode(this, _net, "t1", dist1, dist3, true, true);
		TransitionMode t2 = new TransitionMode(this, _net, "t2", dist2, dist3, true, true);
		TransitionMode t3 = new TransitionMode(this, _net, "t3", dist1, dist3, true, true);
		TransitionMode t4 = new TransitionMode(this, _net, "t4", dist2, dist3, true, true);
		TransitionMode t5 = new TransitionMode(this, _net, "t5", dist1, dist3, true, true);

		Transition trans = new Transition(this, _net, "Mahlzeit", true, true);
		trans.addTransitionMode(t1);
		trans.addTransitionMode(t2);
		trans.addTransitionMode(t3);
		trans.addTransitionMode(t4);
		trans.addTransitionMode(t5);

		t1.addInputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type1,
				type2 }, new int[] { 1, 1 }));
		t1.addOutputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type1,
				type2 }, new int[] { 1, 1 }));

		t2.addInputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type2,
				type3 }, new int[] { 1, 1 }));
		t2.addOutputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type2,
				type3 }, new int[] { 1, 1 }));

		t3.addInputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type3,
				type4 }, new int[] { 1, 1 }));
		t3.addOutputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type3,
				type4 }, new int[] { 1, 1 }));

		t4.addInputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type4,
				type5 }, new int[] { 1, 1 }));
		t4.addOutputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type4,
				type5 }, new int[] { 1, 1 }));

		t5.addInputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type5,
				type1 }, new int[] { 1, 1 }));
		t5.addOutputPlace(p1, TokenMultiSetTools.arrayToMap(new TokenType[] { type5,
				type1 }, new int[] { 1, 1 }));

	}

	public static void main(String[] args) {

		ColoredNetModel model = new ColoredNetModel(null, "Example Model",
				true, true);
		Experiment exp = new Experiment("testexp_colored");

		model.connectToExperiment(exp);

		exp.setShowProgressBar(true); // display a progress bar (or not)
		exp.stop(new TimeInstant(100000, TimeUnit.MINUTES)); // set end of
																// simulation at
																// 1500 minutes
		exp.tracePeriod(new TimeInstant(0), new TimeInstant(100,
				TimeUnit.MINUTES));
		exp.debugPeriod(new TimeInstant(0), new TimeInstant(50,
				TimeUnit.MINUTES));

		exp.start();
		exp.report();
		exp.finish();
	}

}
