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
package de.christianseipl.utilities.rng;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Interface for uniform pseudo random number generators to be used by the
 * distribution classes to compute their samples. Random generators of this type
 * must return uniform distributed double values. 
 * 
 * The generator must contain an unmodifiable initialization vector (e.g. 
 * a static class variable) as fixpoint for the positioning along the stream.
 * 
 * Every generator must have its own fixed starting point.
 * The coordinates of the starting point are relative to the initialization
 * vector and might coincidine with the start of a substream.
 * 
 * @author Christian Seipl 
 * @author Tim Lechler
 * 
 * @version Interface based on DESMO-J, Ver. 2.1.4b copyright (c) 2008
 */
public interface UniformRandomGenerator extends Serializable {
	
	
	/**
	 * Implementiert ein Null-Objekt.
	 * 
	 * Der Null-Generator implementiert die Aufrufe von {@code #advanceState(int, int, int)}
	 * sowie von {@link #resetToSubstream()}. Ein Aufruf von
	 * {@link #nextDouble()} löst eine Ausnahme aus.
	 * 
	 * @author Christian Seipl
	 *
	 */
	public static final class NullUniformRandomGenerator implements UniformRandomGenerator, Serializable {

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return NullUniformRandomGenerator.class.getSimpleName();
		}

		private static final long serialVersionUID = 1L;
		private BigInteger level1;
		private BigInteger level2;
		private BigInteger level3;


		public double nextDouble() {
			throw new UnsupportedOperationException();
		}

		public void resetToSubstream() {
		}


		@Override
		public void setSubstreamPosition(BigInteger _level1, BigInteger _level2,
				BigInteger _level3) {
			this.level1 = _level1;
			this.level2 = _level2;
			this.level3 = _level3;
		}

		@Override
		public void advanceToStream(BigInteger _level1, BigInteger _level2,
				BigInteger _level3) {
		}

		@Override
		public void advancePosition(BigInteger _by) {
		}

		@Override
		public BigInteger getStreamLevel(int _rank) {
			switch (_rank) {
			case 1:
				return level1;
			case 2:
				return level2;
			case 3:
				return level3;
			default:
				throw new IllegalArgumentException();
			}
		}
		
	}
	
	/**
	 * Returns the next uniform distributed [0,1] double pseudo random number
	 * from the random generator's stream.
	 * 
	 * @return double : The next uniformly distributed [0,1] double value
	 */
	public double nextDouble();

	/**
     * Setzt den Startpunkt des Substreams
     * auf den angegebenen Substream ausgehend vom
     * Initialisierungsvektor. Die aktuelle Position wird hierbei
     * nicht verändert.
	 * 
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void setSubstreamPosition(BigInteger _level1, BigInteger _level2, BigInteger _level3);

	/**
	 * Reset the RNG to the start of the Substream. The starting point of the
	 * substream was fixed during the initialization.
	 */
	public void resetToSubstream();

	/**
	 * Schreitet ausgehend von der aktuellen Position um die angegebenen
	 * Teilströme voran. Hierbei wird nicht die gespeicherte Information
	 * über die Substreams verändert.
	 * 
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void advanceToStream(BigInteger _level1, BigInteger _level2, BigInteger _level3);
    
	
	/**
	 * Schreitet im Strom um {@code _by} voran ausgehend von der aktuellen
	 * Position.
	 * 
	 * @param _by
	 */
	public void advancePosition(BigInteger _by);

	/**
	 * Gibt die Nummer des Substreams mit dem angegebenen Rang zurück.
	 * 
	 * @param _rank
	 * @return
	 * 
	 *  @throws IllegalArgumentException wenn sich der Rang außerhalb des Gültigkeitsbereichs befindet.
	 */
	public BigInteger getStreamLevel(int _rank);

}
