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

import java.io.Serializable;
import java.math.BigInteger;

import de.christianseipl.utilities.TextUtilities;
import de.christianseipl.utilities.rng.MRG32k3a;
import de.christianseipl.utilities.rng.UniformRandomGenerator;

/**
 * Abstrakte  Referenzimplementierung für alle stochastischen
 * Verteilungen, welche die Schnittstelle {@link StochasticDist} implementieren.
 * 
 * Die Klasse enthält einen Zufallszahlengenerator vom Typ {@link MRG32k3a},
 * der gleichverteilte Zufallszahlen im Intervall 0, 1 zurückliefert.
 * 
 * Der dazugehörige Builder besitzt eine build-Methode, die es erlaubt für den
 * entsprechenden Zufallszahlengenerator einen anderen Initialisierungsvektor
 * zu benutzen.
 * 
 * @author Christian Seipl
 * @param <E>
 */
public abstract class AbstractDistribution<E extends Number> implements Serializable, StochasticDist<E> {


	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#advancePosition(java.math.BigInteger)
	 */
	@Override
	public void advancePosition(BigInteger _by) {
		randomGenerator.advancePosition(_by);
	}

	/**
	 * Implementiert die Berechnung der Standardabweichung indem
	 * die Wurzel aus der Varianz berechnet wird.
	 * @see de.christianseipl.utilities.distributions.StochasticDist#standardDeviation()
	 */
	@Override
	public final double standardDeviation() {
		return Math.sqrt(variance());
	}

	public static abstract class AbstractDistBuilder<F extends StochasticDist<? extends Number>> implements Serializable {
		private static final long serialVersionUID = 1L;
		private long[][] iv = MRG32k3a.getPackageIV();
		private final boolean useRNG;

		/**
		 * Erzeugt einen neues Builder, der auch gleichzeitig festlegt,
		 * ob er einen Zufallszahlengenerator benutzt.
		 *  
		 * @param _useRNG
		 */
		public AbstractDistBuilder(boolean _useRNG) {
			super();
			useRNG = _useRNG;
		}

		/**
		 * Setzt den Intialisierungsvektor für den Zufallszahlengenerator dieser Verteilung
		 * @param _iv
		 * @return
		 */
		private final AbstractDistBuilder<F> setInitialisationVector(long[][] _iv) {
			this.iv = new long[2][3];
			System.arraycopy(_iv[0], 0, iv[0], 0, _iv[0].length);
			System.arraycopy(_iv[1], 0, iv[1], 0, _iv[1].length);
			return this;
		}
		
		/**
		 * Erzeugt eine neue Verteilung vom Type {@code E} und gibt diese zurück.
		 * @return Eine Verteilung vom Typ {@code E}
		 */
		public abstract F build();
		
		
		/**
		 * @param _iv
		 * @return Eine neue Verteilung, dessen Zufallszahlengenerator auf den angegebenen Initialisierungsvektor gesetzt ist. 
		 * 
		 */
		public final F build(long[][] _iv) {
			return setInitialisationVector(_iv).build();
		}
		/**
		 * Skaliert die Verteilung so, daß die Varianz {@code _variance} entspricht.
		 * und der Mittelwert erhalten bleibt.
		 * 
		 * @param _variance
		 * @return Ein Builder, der die neue Verteilung zurückgibt.
		 */
		public abstract AbstractDistBuilder<F> scaleToVariance(double _variance);
		
		
		/**
		 * Verschiebt die Verteilung so, daß der Mittelwert bei {@code _mean}
		 * liegt und die Varianz erhalten bleibt. 
		 * 
		 * @param _mean
		 * @return Ein Builder, der die neue Verteilung zurückgibt.
		 */
		public abstract AbstractDistBuilder<F> shiftToMean(double _mean);
	}


	private static final long serialVersionUID = 1L;


	/**
	 * The underlying uniform pseudo random generator available to every
	 * distribution inheriting from this abstract class. Valid generators have
	 * to implement the <code>desmoj.dist.UniformRandomGenerator</code>
	 * interface. By default the <code>desmoj.dist.DefaultRandomGenerator</code>
	 * is used.
	 * 
	 * @see desmoj.core.dist.UniformRandomGenerator
	 * @see desmoj.core.dist.DefaultRandomGenerator
	 */
	private final UniformRandomGenerator randomGenerator;

	/**
	 * @param _builder
	 */
	public AbstractDistribution(AbstractDistBuilder<? extends StochasticDist<? extends Number>> _builder) {
		if (_builder.useRNG) {
			randomGenerator = new MRG32k3a(_builder.iv, BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO);
		} else {
			randomGenerator = new UniformRandomGenerator.NullUniformRandomGenerator();
		}
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#advanceToStream(int, int, int)
	 */
	@Override
	public void advanceToStream(BigInteger _level1, BigInteger _level2, BigInteger _level3) {
		randomGenerator.advanceToStream(_level1, _level2, _level3);
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#advanceToStream(long, long, long)
	 */
	@Override
	public final void advanceToStream(long _level1, long _level2, long _level3) {
		advanceToStream(BigInteger.valueOf(_level1), BigInteger.valueOf(_level2), BigInteger.valueOf(_level3));
	}

	/**
	 * @return the randomGenerator
	 */
	public final UniformRandomGenerator getRandomGenerator() {
		return randomGenerator;
	}
	
	
	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#getStreamLevel(int)
	 */
	@Override
	public BigInteger getStreamLevel(int _rank) {
		return randomGenerator.getStreamLevel(_rank);
	}

	/**
	 * Must reset the internal state to a define configuration.
	 * Derived classes may reset the RNG or reset a sequence counter. 
	 *
	 *
	 */
	@Override
	public void resetToSubstream() {
		randomGenerator.resetToSubstream();
	}

	@Override
	public void setStreamLevels(BigInteger _level1, BigInteger _level2, BigInteger _level3) {
		randomGenerator.setSubstreamPosition(_level1, _level2, _level3);
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#setStreamLevels(long, long, long)
	 */
	@Override
	public final void setStreamLevels(long _level1, long _level2, long _level3) {
		setStreamLevels(BigInteger.valueOf(_level1), BigInteger.valueOf(_level2), BigInteger.valueOf(_level3));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = this.getClass().getSimpleName() + TextUtilities.LINEBREAK;
		result += String.format("randomGenerator=%s", randomGenerator.toString());
		return result;
	}

	/**
	 * Implementiert die Berechung des Variationskoeffizienten
	 * @see de.christianseipl.utilities.distributions.StochasticDist#variationCoefficient()
	 */
	@Override
	public final double variationCoefficient() {
		return Math.sqrt(variance())/mean();
	}
	
	
}
