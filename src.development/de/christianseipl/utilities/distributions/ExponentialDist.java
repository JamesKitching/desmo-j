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
/**
 * 
 */
package de.christianseipl.utilities.distributions;


/**
 * Exponential-Distribution random numbers.
 * 
 * @author Christian Seipl
 *
 */
public class ExponentialDist extends AbstractDistribution<Double> {

	public static ExponentialDistBuilder createFromMean(double _mean) {
		return new ExponentialDistBuilder(1/_mean);
	}
	
	public static ExponentialDistBuilder createFromVariance(double _variance) {
		return new ExponentialDistBuilder(1/Math.sqrt(_variance));
	}
	
	/**
	 * Parameter der Exponentialverteilung
	 */
	private final double beta;
	
	public static final class ExponentialDistBuilder extends AbstractDistBuilder<ExponentialDist> {

		private double beta;

		/**
		 * @param _useRNG
		 */
		public ExponentialDistBuilder(double _beta) {
			super(true);
			this.beta = _beta;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#build()
		 */
		@Override
		public ExponentialDist build() {
			return new ExponentialDist(this);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scaleToVariance(double)
		 */
		@Override
		public AbstractDistBuilder<ExponentialDist> scaleToVariance(
				double _variance) {
			return createFromVariance(_variance);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shiftToMean(double)
		 */
		@Override
		public AbstractDistBuilder<ExponentialDist> shiftToMean(double _mean) {
			return createFromMean(_mean);
		}
		
	}
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param _builder
	 */
	private ExponentialDist(ExponentialDistBuilder _builder) {
		super(_builder);
		beta = _builder.beta;
		if (beta <= 0d) {
			throw new IllegalArgumentException(String.format("Beta must be greater than 0, but is %f", beta));
		}
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#canSample()
	 */
	@Override
	public boolean canSample() {
		return true;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#hasLowerBound()
	 */
	@Override
	public boolean hasLowerBound() {
		return true;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#hasUpperBound()
	 */
	@Override
	public boolean hasUpperBound() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#maximum()
	 */
	@Override
	public Double maximum() {
		return Double.POSITIVE_INFINITY;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#mean()
	 */
	@Override
	public double mean() {
		return 1/beta;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#minimum()
	 */
	@Override
	public Double minimum() {
		return 0d;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#sample()
	 */
	@Override
	public Double sample() {
		return -beta * Math.log(getRandomGenerator().nextDouble());
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#variance()
	 */
	@Override
	public double variance() {
		return 1/(beta*beta);
	}

}
