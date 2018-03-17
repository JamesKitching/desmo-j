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
 * Uniform distributed stream of pseudo random numbers of type double. Values
 * produced by this distribution are uniformly distributed in the range
 * specified as parameters of the constructor.
 * 
 * @author Christian Seipl
 * @author Tim Lechler
 * 
 * @version Interface based on DESMO-J, Ver. 2.1.4b copyright (c) 2008
 */
public final class UniformDist extends AbstractDistribution<Double> {

	@Override
	public String toString() {
		String result = super.toString();
		result += String.format("bounds=[%f;%f]", min, max) + TextUtilities.LINEBREAK;
		return result;
	}

	public static final class UniformDistBuilder extends AbstractDistBuilder<UniformDist> {

		private static final long serialVersionUID = 1L;
		private final double min;
		private final double max;

		@Override
		public UniformDist build() {
			return new UniformDist(this);
		}

		public UniformDistBuilder(double _min, double _max) {
			super(true);
			this.min = _min;
			this.max = _max;
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scale(double)
		 */
		@Override
		public AbstractDistBuilder<UniformDist> scaleToVariance(double _variance) {
			return createWithMeanVariance(build().mean(), _variance);
		}

		/**
		 * Verschiebt die Gleichverteilung, so daß der neue Mittelwert
		 * bei {@code _mean} liegt und die Intervallbreite der alten
		 * Breite entspricht.
		 * 
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shiftToMean(double)
		 */
		@Override
		public AbstractDistBuilder<UniformDist> shiftToMean(double _mean) {
			return createWithMeanVariance(_mean, build().variance());
		}
		
	}
	
	private static final long serialVersionUID = 1L;

	/**
	 * Stores the lower border of the distribution range
	 */
	private final double min;

	/**
	 * Stores the upper border of the distribution range
	 */
	private final double max;

	/**
	 * @param unifromDistBuilder
	 */
	private UniformDist(UniformDistBuilder _builder) {
		super(_builder);
		min = _builder.min;
		max = _builder.max;
		if (min > max) {
			throw new IllegalArgumentException(
					String.format("Upper bound %f must be greater or equal than lower bound %f",
									max, min));
		}
	}

	/**
	 * Returns the next floating point sample from this uniform distribution.
	 * The value returned is basically the uniformly distributed pseudo random
	 * number produced by the underlying random generator stretched to match the
	 * range specified by the client via construtor parameters.
	 * 
	 * @return double : The next floating point sample from this uniform
	 *         distribution
	 */
	@Override
	public Double sample() {
		return min + (max - min) * getRandomGenerator().nextDouble();
	}

	/**
	 * A distribution will always deliver samples
	 * 
	 * @return Always true
	 */
	@Override
	public boolean canSample() {
		return true;
	}

	@Override
	public Double minimum() {
		return min;
	}

	@Override
	public Double maximum() {
		return max;
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
		return (min + max) / 2;
	}

	/**
	 * @param _mean
	 * @param _variance
	 * @return
	 */
	public static UniformDistBuilder createWithMeanVariance(double _mean,
			double _variance) {
		final double root = Math.sqrt(3 * _variance);
		final double a = _mean - root;
		final double b = _mean + root;
		return new UniformDistBuilder(a, b);
	}

	public static UniformDistBuilder createWithLowerUpperBound(double _lowerBound,
			double _upperBound) {
		return new UniformDistBuilder(_lowerBound, _upperBound);
	}

	@Override
	public double variance() {
		return 1/12*Math.pow(max - min, 2d);
	}
}
