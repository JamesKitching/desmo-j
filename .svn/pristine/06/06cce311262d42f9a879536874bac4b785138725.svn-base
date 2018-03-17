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
 * Normal (also known as "Gaussian") distributed stream of pseudo random numbers
 * of type double. The algorithm used here is derived from the Java API class
 * <code>java.util.Random</code>.
 * 
 * @author Christian Seipl
 * @author Tim Lechler
 * @see java.util.Random
 * 
 * @version Interface based on DESMO-J, Ver. 2.1.4b copyright (c) 2008
 */
public final class NormalDist extends AbstractDistribution<Double> {

	public static final class NormalDistBuilder extends AbstractDistBuilder<NormalDist> {

		private NormalDistBuilder(double _mean, double _stdDev) {
			super(true);
			mean = _mean;
			stdDev = _stdDev;
		}

		private static final long serialVersionUID = 1L;
		private final double mean;
		private double stdDev;

		@Override
		public NormalDist build() {
			return new NormalDist(this);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scale(double)
		 */
		@Override
		public AbstractDistBuilder<NormalDist> scaleToVariance(double _variance) {
			return createFromMeanVariance(build().mean(), _variance);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shift(double)
		 */
		@Override
		public AbstractDistBuilder<NormalDist> shiftToMean(double _mean) {
			return createFromMeanVariance(_mean, build().variance());
		}
		
	}
	/**
	 * @return the stdDev
	 */
	public double stdDev() {
		return stdDev;
	}

	@Override
	public String toString() {
		String result = super.toString();
		result += String.format("mean=%f; stdDev=%f", mean, stdDev) + TextUtilities.LINEBREAK;
		return result;
	}

	private static final long serialVersionUID = 1L;

	public static NormalDistBuilder createFromMeanVariance(double _mean, double _variance) {
		return new NormalDistBuilder(_mean, Math.sqrt(_variance));
	}
	
	public static NormalDistBuilder createFromMeanStdDev(double _mean, double _stdDev) {
		return new NormalDistBuilder(_mean, _stdDev);
	}
	/**
	 * The mean value for this type of distribution.
	 */
	private final double mean;

	/**
	 * The standard deviation for this type of distribution
	 */
	private final double stdDev;

	/**
	 * Buffer for storing the next gaussian value already calculated. Necessary
	 * for algorithm taken from Java API class <code>java.util.Random</code>.
	 * When computing a Gaussian value two samples of a pseudo random number
	 * stream are taken and calculated to produce two gaussian values, even if
	 * only one is used. So the other value is stored to be delivered next time
	 * a Gaussian value is requested by a client.
	 */
	private double nextGaussian;

	/**
	 * Flag indicates wether there is already a calculated Gaussian value
	 * present in the buffer variable <code>nextGaussian</code>. Necessary
	 * for algorithm taken from Java API class <code>java.util.Random</code>.
	 * When calculating a pseudo random number using the algorithm implemented
	 * here, two Gaussian values are computed at a time, so the other value is
	 * stored to be used next time the client asks for a new value, thus saving
	 * on computation time. If <code>true</code>, there is a next Gaussian
	 * value already calculated, if <code>false</code> a new pair of Gaussian
	 * values has to be generated.
	 */
	private boolean haveNextGaussian;

	/**
	 * @param _normalDistBuilder
	 */
	private NormalDist(NormalDistBuilder _builder) {
		super(_builder);
		mean = _builder.mean;
		stdDev = _builder.stdDev;
		if (stdDev <= 0) {
			throw new IllegalArgumentException(String.format(
					"Standarddeviation %f must be greater than 0.", stdDev));
		}
	}

	/**
	 * Returns the next normal (also known as "Gaussian") distributed sample
	 * from this distribution. The value depends upon the seed, the number of
	 * values taken from the stream by using this method before and the mean and
	 * standard deviation values specified for this distribution. Computation
	 * same as RealDistGaussian plus cast to <code>long</code>. The basic
	 * algorithm has been taken from the Java API
	 * <code>java.util.Random.nextGaussian()</code> with the feature of
	 * antithetic random numbers added.
	 * 
	 * @return double : The next normal (also known as "Gaussian") distributed
	 *         sample from this distribution.
	 */
	@Override
	public Double sample() {

		double newSample; // aux variable

		if (haveNextGaussian) { // a Gaussian already calculated?
			haveNextGaussian = false; 
			newSample = nextGaussian * stdDev + mean; // the prefab
		} else {
			double v1, v2, s; // interim variables needed for calculation
			do { // loop with normal random number generation
				v1 = 2 * getRandomGenerator().nextDouble() - 1; // between
				// -1
				// and 1
				v2 = 2 * getRandomGenerator().nextDouble() - 1; // between
				// -1
				// and 1
				s = v1 * v1 + v2 * v2;
			} while (s >= 1);
			double multiplier = Math.sqrt(-2 * Math.log(s) / s);
			nextGaussian = v2 * multiplier; 
			haveNextGaussian = true; 
			newSample = v1 * multiplier * stdDev + mean; // the Gaussian
		}
		return newSample;

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
		return Double.NEGATIVE_INFINITY;
	}

	@Override
	public Double maximum() {
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public boolean hasUpperBound() {
		return false;
	}

	@Override
	public boolean hasLowerBound() {
		return false;
	}

	@Override
	public double mean() {
		return mean;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#variance()
	 */
	@Override
	public double variance() {
		return Math.pow(stdDev, 2d);
	}
}
