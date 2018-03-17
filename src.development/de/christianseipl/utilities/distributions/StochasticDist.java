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

import java.math.BigInteger;





/**
 * Interface for all distributions returning samples. Use this
 * class instead of a specific distribution if the special distribution function
 * is supposed to be specified in subclasses or changed dynamically. Extend the
 * abstract class to define all your special floating point distributions.
 * 
 * The class is extended to provide different methods for manipulation of
 * the RNG-substreams ({@link UniformRandomGenerator}.
 * 
 * 
 * @author Tim Lechler
 * @author Christian Seipl
 * 
 * @version Interface based on DESMO-J, Ver. 2.1.4b copyright (c) 2008
 */
public interface StochasticDist<E extends Number> {
	
	/**
	 * FIXME: getStreamLevel durch eine Methode ersetzen, welch das Setzen der Ströme auf die
	 * Ströme einer anderen Verteilung erlaubt.
	 * 
	 * Gibt die Nummer des Substreams mit dem angegebenen Rang zurück.
	 * 
	 * @param _rank
	 * @return
	 * 
	 *  @throws IllegalArgumentException wenn sich der Rang außerhalb des Gültigkeitsbereichs befindet.
	 */
	public BigInteger getStreamLevel(int _rank);

	/**
	 * Setzt die aktuelle Position und den Startpunkt des Substreams
     * auf den angegebenen Substream ausgehend vom Initialisierungsvektor.
     * 
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void setStreamLevels(BigInteger _level1, BigInteger _level2, BigInteger _level3);
	/**
	 * @see #setStreamLevels(BigInteger, BigInteger, BigInteger)
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void setStreamLevels(long _level1, long _level2, long _level3);

	/**
	 * Schreitet ausgehend von der aktuellen Position um die angegebenen
	 * Teilströme voran.
     * 
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void advanceToStream(BigInteger _level1, BigInteger _level2, BigInteger _level3);
	/**
	 * @see #advanceToStream(BigInteger, BigInteger, BigInteger)
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void advanceToStream(long _level1, long _level2, long _level3);

	
	/**
	 * Schreitet im Strom um {@code _by} voran ausgehend von der aktuellen
	 * Position.
	 * 
	 * @param _by
	 */
	public void advancePosition(BigInteger _by);

	/**
	 * Abstract method should return true, if there are more samples and false
	 * if the end of the sequence is reached.
	 * 
	 * @return boolean: If the next sample can be made
	 */
	public boolean canSample();

	/**
	 * Abstract method should return the specific sample as a floating point
	 * value when implemented in subclasses.
	 * 
	 * @return double : The floating point sample to be drawn from this
	 *         distribution
	 */
	public E sample();

	/**
	 * Returns the maximum of the distribution, if it exists.
	 * If the distribution is unbounded, the function must
	 * return +inf.
	 * @return
	 */
	public E maximum();

	
	/**
	 * @return True, if the distribution has an upper bound
	 */
	public boolean hasUpperBound();
	/**
	 * Returns the minimum of the distribution, if it exists.
	 * If the distribution is unbounded, the function must
	 * return -inf.
	 * @return
	 */
	public E minimum();
	
	/**
	 * @return True, if the distribution has a lower bound
	 */
	public boolean hasLowerBound();
	
	
	
	/**
	 * Returns the mean value of the distribution
	 * 
	 * @return 
	 */
	public double mean();

	/**
	 * Returns the variance of the distribution.
	 * 
	 * If a distribution does not have a variance, zero is returned.
	 * @return
	 */
	public double variance();
	

	/**
	 * Returns the standard-deviation of the distribution.
	 * 
	 * The standard-deviation is equal to the square-root of the variance.
	 * @return
	 */
	public double standardDeviation();
	
	/**
	 * Returns the variation-coefficient.
	 * 
	 * The coefficient is the quotiont of the standard-deviation and the mean
	 * of the distribution.
	 * @return
	 */
	public double variationCoefficient();
	
	/**
	 * Setzt die aktuelle Position des Stroms auf den gespeicherten Teilstrom zurück,
	 * der mit Hilfe von {@link #setStreamLevels(int, int, int)} gesetzt wurde. 
	 */
	public void resetToSubstream();
}
