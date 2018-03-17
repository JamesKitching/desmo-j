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

import de.christianseipl.utilities.TextUtilities;




/**
 * Gamma distributed random numbers.
 * 
 * The distribution is described by the two parameters alpha and beta. 
 * 
 * @author Christian Seipl
 * 
 */
public final class GammaDist extends AbstractDistribution<Double> {

	public static final class GammaDistBuilder extends AbstractDistBuilder<GammaDist> {

		private static final long serialVersionUID = 1L;
		private final double alpha;
		private final double beta;

		public GammaDistBuilder(double _alpha, double _beta) {
			super(true);
			alpha = _alpha;
			beta = _beta;
		}

		@Override
		public GammaDist build() {
			return new GammaDist(this);
		}

		@Override
		public AbstractDistBuilder<GammaDist> scaleToVariance(double _variance) {
			return createFromMeanVariance(build().mean(), _variance);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shift(double)
		 */
		@Override
		public AbstractDistBuilder<GammaDist> shiftToMean(double _mean) {
			return createFromMeanVariance(_mean, build().variance());
		}
		
	}
	
	/**
	 * @return the alpha
	 */
	public double alpha() {
		return alpha;
	}

	/**
	 * @return the beta
	 */
	public double beta() {
		return beta;
	}

	@Override
	public String toString() {
		String result = super.toString();
		result += String.format("alpha=%f; beta=%f", alpha, beta) + TextUtilities.LINEBREAK;
		return result;
	}

	private static final long serialVersionUID = 1L;

	public static GammaDistBuilder createFromAlphaBeta(double _alpha,
			double _beta) {
		return new GammaDistBuilder(_alpha, _beta);
	}

	/**
	 * The parameters of the distribution
	 */
	private final double alpha;
	/**
	 * The parameters of the distribution
	 */
	private final double beta;

	/**
	 * @param _builder
	 */
	private GammaDist(GammaDistBuilder _builder) {
		super(_builder);
		if (_builder.alpha <= 0.0d || _builder.beta <= 0.0d) {
			throw new IllegalArgumentException(String.format(
					"Alpha %f and beta %f must be greater 0.", _builder.alpha,
					_builder.beta));
		}
		this.alpha = _builder.alpha;
		this.beta = _builder.beta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unihh.ilt.sim.layer0.dist.AbstractRealDist#canSample()
	 */
	@Override
	public boolean canSample() {
		return true;
	}

	/**
	 * Returns a sample from a Gamma-Distribution with the parameters alpha and
	 * beta
	 * 
	 * @return double A sample from the Gamma-Distribution
	 */
	@Override
	public Double sample() {
		if (alpha > 1) {
			return beta * gammaAlphaG1(alpha);
		}
		return beta * gammaAlphaLE1(alpha);
	}

	/**
	 * Internal Function to generate a gamma distributed number if alpha is
	 * greater 1. This algorithm was taken from Tadikamalla, P.R. Computer
	 * Generation of Gamma Random Variables - II, Communications of the ACM,
	 * 1978 , 21 , 925-928
	 * 
	 * @param _alpha
	 *            Alpha of the Gamma-Distribution
	 * @return number The sample, taken from the Gamma-Distribution
	 */
	private double gammaAlphaG1(double _alpha) {
		final double A = _alpha - 1;
		final double B = 0.5 * (1 + Math.sqrt(4 * _alpha - 3));
		final double C = A * (1 + B) / B;
		final double D = (B - 1) / (A * B);
		final double S = A / B;
		final double P = 1 / (2 - Math.exp(-S));
		double U2;
		double x;
		double y;
		do {
			final double U1 = getRandomGenerator().nextDouble();
			if (U1 <= P) {
				// Step 7
				x = A - B * Math.log(U1 / P);
				y = x - A;
			} else {
				// Step 4
				double E = -Math.log((1 - U1) / (1 - P));
				do {
					if (E <= S) {
						break;
					}
					E -= A / B;
				} while (true);
				// Step 6
				x = A - B * E;
				y = A - x;
			}
			// Step 8
			U2 = getRandomGenerator().nextDouble();
			// Step 9
		} while (Math.log(U2) > A * Math.log(D * x) - x + y / B + C);
		// Step 2;
		return x;
	}

	/**
	 * Algorithm taken from D.J. Best 1983, Computing, Vol. 30, pp 185--188, A
	 * Note on Gamma Variate Generators with Shape Parameters less than Unity
	 * 
	 * @param _alpha
	 * @return
	 */
	private double gammaAlphaLE1(double _alpha) {
		final double z = 0.07D + 0.75D * Math.pow(1D - _alpha, 0.5D);
		final double b = 1D + Math.exp(-z) * _alpha / z;
		do {
			final double U = getRandomGenerator().nextDouble();
			final double P = b * U;
			if (P <= 1) {
				final double X = z * Math.pow(P, 1 / _alpha);
				final double U2 = getRandomGenerator().nextDouble();
				if (U2 <= (2 - X) / (2 + X))
					return X;
			} else {
				final double X = -Math.log(z * (b - P) / _alpha);
				final double Y = X / z;
				final double U2 = getRandomGenerator().nextDouble();
				if (U2 * (_alpha + Y - _alpha * Y) < 1)
					return X;
				if (U2 <= Math.pow(Y, _alpha - 1))
					return X;
			}
		} while (true);
	}

	@Override
	public Double minimum() {
		return 0d;
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
		return true;
	}

	@Override
	public double mean() {
		return alpha * beta;
	}

	@Override
	public double variance() {
		return alpha * beta * beta;
	}
	
	
	public static GammaDistBuilder createFromMeanVariance(double _mean, double _variance) {
		double beta = _variance / _mean;
		double alpha = _mean / beta;
		return new GammaDistBuilder(alpha, beta);
	}
}
