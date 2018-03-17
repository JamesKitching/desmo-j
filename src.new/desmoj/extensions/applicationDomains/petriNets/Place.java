package desmoj.extensions.applicationDomains.petriNets;

import java.util.HashMap;
import java.util.Map;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.Reportable;
import desmoj.core.statistic.Accumulate;
import desmoj.core.statistic.StatisticObject;
import desmoj.extensions.applicationDomains.petriNets.report.PlaceReporter;

/**
 * Class modelling a place in a stochastic petri net. A place contains an
 * arbitrary amount of tokens of possibly different types and has a list of
 * transitions it is an input to, for notification purposes. Tokens of any type
 * can be added at any time using the respective addTokens methods.
 * 
 * Multiple tokens of the same type are being represented by a count of a
 * certain type, not by an own object for each one of them. This means that
 * tokens of the same type will always act the same way and never differ from
 * each other.
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
public class Place extends Reportable {

	private Map<TokenType, Integer> tokenMap;
	private Map<TokenType, Accumulate> accumulateMap;
	private PetriNet ownerNet;

	private Map<TransitionMode, Map<TokenType, Integer>> outputTransitions;

	private Accumulate avgAmountAccumulate;
	private int cancellations;

	private boolean splitAvg;
	private TokenType defaultToken;

	/**
	 * Constructor for a Place that is supposed to contain tokens of different
	 * types.
	 * 
	 * @param net
	 *            The PetriNet this Place is supposed to belong to
	 * @param name
	 *            Name of this Place to be shown in Reports
	 * 
	 * @param tokens
	 *            Map with the types of tokens this place is supposed to contain
	 *            when created and their respective amount
	 * @param splitAvg
	 *            Whether the average amount of tokens in this place is to be
	 *            shown per type of token in the report. (false means the report
	 *            will show the average amount of all tokens regardless of type)
	 * @param showinReport
	 *            Whether to list this Place in the report
	 * @param showinTrace
	 *            Whether to show this Place in the trace
	 */
	public Place(Model owner, PetriNet net, String name,
			Map<TokenType, Integer> tokens, boolean splitAvg,
			boolean showinReport, boolean showinTrace) {
		super(owner, name, showinReport, showinTrace);

		tokenMap = tokens;
		accumulateMap = new HashMap<TokenType, Accumulate>();

		this.splitAvg = splitAvg;

		this.ownerNet = net;
		ownerNet.addPlace(this);

		defaultToken = net.getDefaultTokenType();
		outputTransitions = new HashMap<TransitionMode, Map<TokenType, Integer>>();

		Accumulate acc;
		if (splitAvg) {
			for (Map.Entry<TokenType, Integer> entry : tokenMap.entrySet()) {
				acc = new Accumulate(owner, entry.getKey()
						.getTokenDescription(), false, false);
				acc.update(entry.getValue());
				accumulateMap.put(entry.getKey(), acc);
			}
		}

		avgAmountAccumulate = new Accumulate(owner, "avgAmountAccumulate_"
				+ getName(), false, false);

		avgAmountAccumulate.update(getTotalAmount());

		cancellations = 0;
	}

	/**
	 * Constructor for a Place that is supposed to only contain a single type of
	 * token.
	 * 
	 * @param net
	 *            The PetriNet this Place is supposed to belong to
	 * @param name
	 *            Name of this Place to be shown in Reports
	 * 
	 * @param tokenAmount
	 *            How many tokens of default type this Place is supposed to hold
	 *            initially
	 * 
	 * @param showinReport
	 *            Whether to list this Place in the report
	 * @param showinTrace
	 *            Whether to show this Place in the trace
	 */
	public Place(Model owner, PetriNet net, String name, int tokenAmount,
			boolean showinReport, boolean showinTrace) {
		super(owner, name, showinReport, showinTrace);
		this.splitAvg = false;

		this.ownerNet = net;
		ownerNet.addPlace(this);

		defaultToken = net.getDefaultTokenType();
		outputTransitions = new HashMap<TransitionMode, Map<TokenType, Integer>>();

		tokenMap = new HashMap<TokenType, Integer>();
		tokenMap.put(defaultToken, tokenAmount);

		avgAmountAccumulate = new Accumulate(owner, "avgAmountAccumulate_"
				+ getName(), false, showinTrace);

		avgAmountAccumulate.update(getTotalAmount());
		cancellations = 0;
	}

	/**
	 * Method to store a Place's outgoing connections to Transitions.
	 * Automatically called whenever a Transition gains such a link to a Place.
	 * Do not use manually.
	 * 
	 * @param t
	 * @param weight
	 */
	protected void addOutputTransition(TransitionMode t,
			Map<TokenType, Integer> weights) {
		outputTransitions.put(t, weights);
	}

	/**
	 * Method to alter the amount of tokens in a place. Usually called by
	 * adjacent firing transitions, might also be called manually to alter the
	 * nets state as desired.
	 * 
	 * @param amounts
	 *            Map of Tokens to be added. This Map should map the types of
	 *            tokens to be added on their respective amount. The
	 *            TokenMultiSet class provides static methods to generate these
	 *            kind of maps from arrays.
	 * 
	 */
	public void addTokens(Map<TokenType, Integer> amounts) {

		TokenType type;
		int amount;
		int newAmount;
		for (Map.Entry<TokenType, Integer> addedentry : amounts.entrySet()) {
			type = addedentry.getKey();
			amount = addedentry.getValue();

			if (tokenMap.get(type) != null) {
				newAmount = tokenMap.get(type) + amount;
			} else {
				newAmount = amount;
				Accumulate acc = new Accumulate(this.getModel(),
						type.getTokenDescription(), false, false);
				acc.update(newAmount);
				accumulateMap.put(type, acc);
			}

			if (newAmount < 0) {
				sendWarning("Place with <0 tokens of type " + type.getTokenDescription(), "Place: " + getName(), "More tokens of that type than existed have just been taken from the place",
						"Make sure to not manually take more tokens away than there are, you can check their amount in the token map");
			}

			tokenMap.put(type, newAmount);

			// amount of tokens lowered?
			if (amount < 0) {

				for (Map.Entry<TransitionMode, Map<TokenType, Integer>> outputentry : outputTransitions
						.entrySet()) {
					if (outputentry.getValue().get(type) != null
							&& outputentry.getValue().get(type) > newAmount
							&& outputentry.getKey().getActivationState() == TransitionMode.TRANSITION_ACTIVATED) {
						// Transition definitely not activated anymore
						outputentry.getKey().setActivationState(
								TransitionMode.TRANSITION_DEACTIVATED);
						cancellations++;
					}
				}
			}
			// amount of tokens increased??
			if (amount > 0) {

				for (Map.Entry<TransitionMode, Map<TokenType, Integer>> outputentry : outputTransitions
						.entrySet()) {

					if (outputentry.getValue().get(type) != null
							&& outputentry.getValue().get(type) <= newAmount
							&& outputentry.getKey().getActivationState() == TransitionMode.TRANSITION_DEACTIVATED) {
						// Transition might still be restricted by other Places,
						// analysis required before activation

						outputentry.getKey().checkActivation();
					}
				}
			}

			if (splitAvg) {
				accumulateMap.get(type).update(newAmount);
			}
		}
		incrementObservations();

		avgAmountAccumulate.update(getTotalAmount());
	}

	/**
	 * Method to alter the amount of tokens in a place. May be called manually to alter the
	 * nets state as desired.
	 * 
	 * This simplified version of the method will always create tokens of the
	 * default token type for the respective net.
	 * 
	 * @param amount
	 *            Amount of tokens of default type to be added
	 */
	public void addTokens(int amount) {


		Map<TokenType, Integer> defaultMap = new HashMap<TokenType, Integer>();
		
		defaultMap.put(this.ownerNet.getDefaultTokenType(), amount);
		
		addTokens(defaultMap);
	}

	/**
	 * Returns the total amount of tokens on this place, regardless of color.
	 * 
	 * @return The total amount of tokens this place contains
	 */
	public int getTotalAmount() {
		int totalamount = 0;
		for (Map.Entry<TokenType, Integer> entry : tokenMap.entrySet()) {
			totalamount = totalamount + entry.getValue();
		}
		return totalamount;
	}

	/**
	 * Returns the amount of each type of token on this place as a map
	 * 
	 * @return A map assigning each relevant type of token their respective
	 *         amount in this Place.
	 */
	public Map<TokenType, Integer> getTokenMap() {
		return tokenMap;
	}

	/**
	 * Returns the average amount of tokens in this place, from the start of the
	 * experiment until the current simulation time. If splitAvg is set to true
	 * in the constructor of this Place, the average will be shown individually
	 * for each type of token. If it is set to false, the String returned
	 * contains the overall average of tokens in this Place, regardless of type.
	 * 
	 * @return A String showing the average amount of tokens in this place since last reset
	 */
	public String getAverageString() {
		if (splitAvg) {
			String output = "";
			Accumulate acc;
			for (Map.Entry<TokenType, Accumulate> entry : accumulateMap
					.entrySet()) {
				acc = entry.getValue();
				output = output.concat(acc.getName() + ": "
						+ Double.toString(StatisticObject.round(acc.getMean()))
						+ "; ");
			}
			return output;
		} else {
			return Double.toString(StatisticObject.round(avgAmountAccumulate
					.getMean()));
		}
	}

	/**
	 * Returns how often a reduction of tokens in this place has caused a
	 * transition that was previously activated to cancel its waiting process.
	 * 
	 * @return Amount of cancellations to a Transition's waiting process this Place has been causing
	 */
	public int getCancellations() {
		return cancellations;
	}

	/**
	 * Returns the net to which this Place belongs.
	 * 
	 * @return The PetriNet containing this Place
	 */
	public PetriNet getOwnerNet() {
		return ownerNet;
	}

	/**
	 * Creates a Reporter for this Place.
	 * @return The PlaceReporter for this Place
	 */
	public PlaceReporter createDefaultReporter() {
		return new PlaceReporter(this);
	}
}
