/*******************************************************************************
 * Copyright 2009 Christian Seipl
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.christianseipl.utilities.distributions;

import de.christianseipl.utilities.TextUtilities;



/**
 * Real number constant "pseudo"-distribution returns a single constant
 * predefined double value. This "distribution" is most useful for testing
 * purposes. The value to be returned can be specified at construction time.
 *
 * 
 * The constant distribution is regarded as a special case of the uniform
 * distribution, where the bounds are identical to the constant value.
 * 
 * @author Christian Seipl 
 * @author Tim Lechler
 * 
 * @version Interface based on DESMO-J, Ver. 2.1.4b copyright (c) 2008
 */
public final class ConstantDist extends AbstractDistribution<Double> {

	private static final long serialVersionUID = 1L;
	
	public static final class ConstantDistributionBuilder extends AbstractDistBuilder<ConstantDist> {

		private static final long serialVersionUID = 1L;
		private final double value;

		public ConstantDistributionBuilder(double _value) {
			super(false);
			value = _value;
		}

		@Override
		public ConstantDist build() {
			return new ConstantDist(this);
		}

		/**
		 * Eine Veränderung der Varianz bewirkt keine Änderung 
		 * an der Verteilung.
		 * 
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scaleToVariance(double)
		 */
		@Override
		public AbstractDistBuilder<ConstantDist> scaleToVariance(double _variance) {
			return this;
		}

		/**
		 * Eine Verschiebung des Mittelwertes ist identisch mit
		 * der Verschiebung der konstanten Verteilung.
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shiftToMean(double)
		 */
		@Override
		public AbstractDistBuilder<ConstantDist> shiftToMean(double _mean) {
			return new ConstantDistributionBuilder(_mean);
		}
		
	}
	
	public static ConstantDistributionBuilder createConstantDistribution(double _constantValue) {
		return new ConstantDistributionBuilder(_constantValue);
	}

	/**
	 * Stores the status for the constant value to be returned from this
	 * "stochastic"-distribution
	 */
	private final double constValue;


	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.AbstractDistribution#toString()
	 */
	@Override
	public String toString() {
		String result = super.toString();
		result += String.format("constant=%f", constValue) + TextUtilities.LINEBREAK;
		return result;
	}

	/**
	 * @param _constantDistributionBuilder
	 */
	private ConstantDist(ConstantDistributionBuilder _builder) {
		super(_builder);
		this.constValue = _builder.value;
	}

	/**
	 * Returns the constant floating point value associated with this
	 * RealDistConstant distribution
	 * 
	 * @return double : The constant boolean value returned by this
	 *         RealDistConstant distribution
	 */
	public double constantValue() {

		return constValue;

	}

	/**
	 * Returns the next floating point constant sample from this distribution.
	 * For this "pseudo"-distribution it is always is the default value
	 * specified through the constructor or via the <em>setConstant</em>
	 * method.
	 * 
	 * @return double : the constant value
	 */
	@Override
    public Double sample() {
		return constValue; // always return same constant value

	}

	/**
	 * A constant value can always be sampled
	 * 
	 * @return true
	 */
	@Override
	public boolean canSample() {
		return true;
	}



	@Override
	public Double minimum() {
		return constValue;
	}

	@Override
	public Double maximum() {
		return constValue;
	}

	@Override
	public boolean hasUpperBound() {
		return true;
	}

	@Override
	public boolean hasLowerBound() {
		return true;
	}

	@Override
	public double mean() {
		return constValue;
	}

	/**
	 * Die Varianz einer konstanten Verteilung ist Null.
	 * @see de.christianseipl.utilities.distributions.StochasticDist#variance()
	 */
	@Override
	public double variance() {
		return 0;
	}

}
