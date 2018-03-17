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

import java.math.BigInteger;

import de.christianseipl.utilities.TextUtilities;



/**
 * Eine allgemeine Beta-Verteilung mit einer oberen und unteren Schranke.
 * 
 * Die Erzeugung der Zufallszahlen erfolgt über die Kombination von zwei
 * Gamma-Verteilungen.
 * 
 * @author Christian Seipl
 * 
 */
public final class GeneralBetaDist extends AbstractDistribution<Double> {

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
		result+= String.format("x=%s", x.toString()) + TextUtilities.LINEBREAK;
		result+= String.format("y=%s", y.toString()) + TextUtilities.LINEBREAK;
		result+= String.format("bounds=[%f;%f]", lowerBound, upperBound) + TextUtilities.LINEBREAK;
		result+= String.format("alpha=%f; beta=%f", alpha, beta) + TextUtilities.LINEBREAK;
		return result;
	}


	public static final class GeneralBetaDistBuilder extends AbstractDistribution.AbstractDistBuilder<GeneralBetaDist> {

		private static final long serialVersionUID = 1L;
		private final double alpha;
		private final double beta;
		private double lowerBound;
		private double upperBound;

		@Override
		public GeneralBetaDist build() {
			return new GeneralBetaDist(this);
		}

		/**
		 * @param _alpha
		 * @param _beta
		 */
		public GeneralBetaDistBuilder(double _alpha, double _beta) {
			super(true);
			this.alpha = _alpha;
			this.beta = _beta;
		}

		/**
		 * @param _lowerBound the lowerBound to set
		 * @return 
		 */
		public GeneralBetaDistBuilder lowerBound(double _lowerBound) {
			this.lowerBound = _lowerBound;
			return this;
		}

		/**
		 * @param _upperBound the upperBound to set
		 * @return 
		 */
		public GeneralBetaDistBuilder upperBound(double _upperBound) {
			this.upperBound = _upperBound;
			return this;
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scale(double)
		 */
		@Override
		public AbstractDistBuilder<GeneralBetaDist> scaleToVariance(double _variance) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shift(double)
		 */
		@Override
		public AbstractDistBuilder<GeneralBetaDist> shiftToMean(double _mean) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}
	
	private static final long serialVersionUID = 1L;

	private final double lowerBound;

	private final double upperBound;

	private final double alpha;

	private final double beta;

	private final GammaDist x;

	private final GammaDist y;

	private final double width;


	/**
	 * @param generalBetaDistBuilder
	 */
	private GeneralBetaDist(GeneralBetaDistBuilder _builder) {
		super(_builder);
		double _alpha = _builder.alpha;
		double _beta = _builder.beta;
		double _lowerBound = _builder.lowerBound;
		double _upperBound = _builder.upperBound;
		if (_alpha <= 0.0 || _beta <= 0.0) {
			throw new IllegalArgumentException(String.format(
					"Alpha %f and beta %f must be greater 0.0", _alpha, _beta));
		}
		if (_upperBound <= _lowerBound) {
			throw new IllegalArgumentException(String.format(
					"Upper bound %f must be greater than the lower bound %f",
					_upperBound, _lowerBound));
		}
		alpha = _alpha;
		beta = _beta;
		lowerBound = _lowerBound;
		upperBound = _upperBound;
		width = _upperBound - _lowerBound;
		// X und Y registrieren sich und bekommen eigene Substreams zugewiesen.
		// Der Generator von der Vaterklasse ist bereits registriert, wird
		// von uns jedoch nur als Ankerpunkt verwendet, da x und y eigene
		// Generatoren besitzen. 
		// Die dritte Ebene der Substreams ist noch frei, so daß wir diese
		// mit eigenen Funktionen hinterlegen können - hier die Anordnung
		// von x und y.
		x = GammaDist.createFromAlphaBeta(alpha, 1).build();
		y = GammaDist.createFromAlphaBeta(beta, 1).build();
		BigInteger level1 = getRandomGenerator().getStreamLevel(1);
		BigInteger level2 = getRandomGenerator().getStreamLevel(2);
		BigInteger level3 = getRandomGenerator().getStreamLevel(3);
		x.setStreamLevels(level1, level2, level3);
		x.resetToSubstream();
		y.setStreamLevels(level1, level2, level3.add(BigInteger.ONE));
		y.resetToSubstream();
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
	 * Samples a beta distributed random variable. The method is described in
	 * Fishman, pp. 375.
	 * 
	 * We extend the standard interval [0..1] to [lb .. ub] with a linear
	 * transformation.
	 * 
	 * @see de.christianseipl.utilities.distributions.StochasticDist#sample()
	 */
	@Override
	public Double sample() {
		double X = x.sample();
		double Y = y.sample();
		return lowerBound + ((upperBound - lowerBound) * X/(X + Y) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unihh.ilt.sim.layer0.dist.AbstractRealDist#minimum()
	 */
	@Override
	public Double minimum() {
		return lowerBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unihh.ilt.sim.layer0.dist.AbstractRealDist#maximum()
	 */
	@Override
	public Double maximum() {
		return upperBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unihh.ilt.sim.layer0.dist.Distribution#reset()
	 */
	@Override
	public void resetToSubstream() {
		super.resetToSubstream();
		x.resetToSubstream();
		y.resetToSubstream();
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
		return (width) * (alpha / (alpha + beta)) + lowerBound;
	}

	public double variance() {
		return Math.pow(width, 2d) * (alpha * beta) / (Math.pow(alpha + beta, 2.0d) * (alpha + beta + 1));
	}
	
	/**
	 * @param _mean
	 * @param _variance
	 * @param _lower
	 * @param _upper
	 * @return
	 */
	public static GeneralBetaDistBuilder createFromMeanVariance(double _gen_mean,
			double _gen_variance, double _lower, double _upper) {
		double d = (_upper - _lower);
		double _mean = (_gen_mean - _lower) / d;
		double _variance = _gen_variance / (d*d);
		
		double mp2 = _mean * _mean;
		double mp3 = mp2 * _mean;
		double alpha = _mean * (_mean - mp2 - _variance) / _variance;
		double beta = (_mean - 2 * mp2 + mp3 - _variance + _mean * _variance)
				/ _variance;

		return new GeneralBetaDistBuilder(alpha, beta).lowerBound(_lower).upperBound(_upper);
	}

	/**
	 * @param _mean
	 * @param _variance
	 * @return
	 */
	public static GeneralBetaDistBuilder createFromMeanVariance(double _mean,
			double _variance) {
		return createFromMeanVariance(_mean, _variance, 0.0d, 1.0d);
	}

	/**
	 * Versetzt den aktuellen Zustand des Zufallszahlengenerators um
	 * die angegebenen Ebenen.
	 * 
	 * Die dritte Ebene wird intern für zwei Verteilungen verwendet.
	 * Diese werden entsprechend den übergebenen Werten angepaßt.
	 *  
	 * @see de.christianseipl.utilities.distributions.AbstractDistribution#advanceToStream(java.math.BigInteger, java.math.BigInteger, java.math.BigInteger)
	 */
	@Override
	public void advanceToStream(BigInteger _level1, BigInteger _level2,
			BigInteger _level3) {
		super.advanceToStream(_level1, _level2, _level3);
		x.advanceToStream(_level1, _level2, _level3);
		y.advanceToStream(_level1, _level2, _level3.add(BigInteger.ONE));
	}


	/**
	 * Setzt die Ebenen des Zufallszahlengenerators.
	 * 
	 * Die dritte Ebene wird intern für zwei Verteilungen verwendet.
	 * Diese werden entsprechend den übergebenen Werten angepaßt.
	 *  
	 * @see de.christianseipl.utilities.distributions.AbstractDistribution#setStreamLevels(java.math.BigInteger, java.math.BigInteger, java.math.BigInteger)
	 */
	@Override
	public void setStreamLevels(BigInteger _level1, BigInteger _level2,
			BigInteger _level3) {
		super.setStreamLevels(_level1, _level2, _level3);
		x.setStreamLevels(_level1, _level2, _level3);
		y.setStreamLevels(_level1, _level2, _level3.add(BigInteger.ONE)); 
	}
}
