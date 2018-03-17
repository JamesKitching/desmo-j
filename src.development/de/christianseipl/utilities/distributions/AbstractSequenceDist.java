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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Christian Seipl
 *
 */
public abstract class AbstractSequenceDist extends AbstractDistribution<Double> {

	private static final long serialVersionUID = 1L;


	public static abstract class AbstractSequenceDistBuilder<E extends AbstractSequenceDist> extends AbstractDistBuilder<E> {

		private Collection<Double> sequence;

		/**
		 * @param _useRNG
		 */
		public AbstractSequenceDistBuilder(boolean _useRNG, Collection<Double> _sequence) {
			super(_useRNG);
			this.sequence = Collections.unmodifiableCollection(new ArrayList<Double>(_sequence));
		}

		private static final long serialVersionUID = 1L;
	}
	/**
	 * After initialization, the list is fixed and made unmodifiable 
	 */
	protected final List<Double> sequence;

	/**
	 * @return the sequence
	 */
	public Collection<Double> sequence() {
		return Collections.unmodifiableCollection(sequence);
	}

	protected AbstractSequenceDist(AbstractSequenceDistBuilder<? extends AbstractSequenceDist> _builder) {
		super(_builder);
		this.sequence = Collections.unmodifiableList(new ArrayList<Double>(_builder.sequence));
	}
	
	@Override
	public Double minimum() {
		return sequence.isEmpty() ? Double.NaN : Collections.min(sequence);
	}

	@Override
	public Double maximum() {
		return sequence.isEmpty() ? Double.NaN : Collections.max(sequence);
	}

	@Override
	public boolean hasUpperBound() {
		return sequence.isEmpty() ? false : true;
	}

	@Override
	public boolean hasLowerBound() {
		return sequence.isEmpty() ? false : true;
	}

	@Override
	public double mean() {
		double sum = 0.0;
		for (double item : sequence) {
			sum += item;
		}
		return sequence.isEmpty() ? Double.NaN : sum / sequence.size();
	}

	/**
	 * @see de.christianseipl.utilities.distributions.StochasticDist#variance()
	 */
	@Override
	public double variance() {
		double mean = mean();
		double sum = 0d;
		for (Double element : sequence) {
			sum += Math.pow(element - mean, 2d);
		}
		return sequence.isEmpty() ? Double.NaN : sum/sequence.size();
	}

}
