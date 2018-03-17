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
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.christianseipl.utilities.TextUtilities;


/**
 * Implements a non cyclic sequence of numbers.
 * 
 * The sequence can be sampled only once and is not cyclic. The function
 * {@code canSample} will return true as long as the internal pointer is within
 * the size of the sequence.
 * 
 * @author Christian Seipl
 * 
 */
public final class SequenceDist extends AbstractSequenceDist {

	public static final class SequenceBuilder extends AbstractSequenceDistBuilder<SequenceDist> {

		private static final long serialVersionUID = 1L;
		private final List<Double> sequence = new LinkedList<Double>();

		public SequenceBuilder(Collection<Double> _sequence) {
			super(false, _sequence);
			sequence.addAll(_sequence);
		}
		
		@Override
		public SequenceDist build() {
			return new SequenceDist(this);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scale(double)
		 */
		@Override
		public AbstractDistBuilder<SequenceDist> scaleToVariance(double _variance) {
			SequenceDist dist = build();
			final Collection<Double> oldSequence = dist.sequence();
			if (oldSequence.size() == 1 || oldSequence.size() == 0) {
				return this;
			} 
			double previousMean = dist.mean();
			final double factor = Math.sqrt(_variance/dist.variance());
			List<Double> scaledSequence = new LinkedList<Double>();
			for (Double element : oldSequence) {
				scaledSequence.add(factor * (element - previousMean) + previousMean);
			}
			return new SequenceBuilder(scaledSequence);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shift(double)
		 */
		@Override
		public AbstractDistBuilder<SequenceDist> shiftToMean(double _mean) {
			SequenceDist dist = build();
			double previousMean = dist.mean();
			Collection<Double> shiftedSequence = new LinkedList<Double>();
			for (Double element : dist.sequence()) {
				shiftedSequence.add((element - previousMean) + _mean);
			}
			return new SequenceBuilder(shiftedSequence);
		}
		
	}

	@Override
	public String toString() {
		String result = super.toString();
		result += String.format("index=%d", index) + TextUtilities.LINEBREAK;
		result += String.format("sequence=%s", Arrays.toString(sequence().toArray())) + TextUtilities.LINEBREAK;
		return result;
	}

	private static final long serialVersionUID = 1L;

	public static SequenceBuilder createSingleValueSequence(double _value) {
		List<Double> sequence = new ArrayList<Double>();
		sequence.add(_value);
		return new SequenceBuilder(sequence);
	}

	public static SequenceBuilder createMultipleValuesSequence(List<Double> _sequence) {
		return new SequenceBuilder(_sequence);
	}

	/**
	 * The index of the actual sample in the array
	 */
	private int index = 0;

	/**
	 * @param _sequenceBuilder
	 */
	private SequenceDist(SequenceBuilder _builder) {
		super(_builder);
	}

	@Override
	public Double sample() {
		if (!canSample())
			throw new IllegalStateException(
					"Could not sample when sequence is finished.");
		return sequence.get(index++);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see desmoj.core.dist.RealDist#canSample()
	 */
	@Override
	public boolean canSample() {
		if (sequence == null) {
			return false;
		}
		return index < sequence.size();
	}

}
