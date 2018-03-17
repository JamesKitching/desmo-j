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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.christianseipl.utilities.TextUtilities;


/**
 * Implements a cyclic sequence of real numbers.
 * 
 * E.g. the distribution is initialized with 4,1,7,5. The first sample will be
 * 4, the second sample is 1, ... the fifth sample is 4 again.
 * 
 * @author Christian Seipl
 * 
 */
public final class CyclicSequenceDist extends AbstractSequenceDist {

	private static final long serialVersionUID = 1L;

	public static final class CyclicSequenceBuilder extends AbstractSequenceDistBuilder<CyclicSequenceDist> {

		private static final long serialVersionUID = 1L;
		private final List<Double> sequence = new LinkedList<Double>();

		public CyclicSequenceBuilder(Collection<Double> _sequence) {
			super(false, _sequence);
			sequence.addAll(_sequence);
		}
		
		@Override
		public CyclicSequenceDist build() {
			return new CyclicSequenceDist(this);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scale(double)
		 */
		@Override
		public AbstractDistBuilder<CyclicSequenceDist> scaleToVariance(double _variance) {
			CyclicSequenceDist dist = build();
			final Collection<Double> oldSequence = dist.sequence();
			if (oldSequence.size() == 1 || oldSequence.size() == 0) {
				return this;
			} 
			double previousMean = dist.mean();
			final double factor = Math.sqrt(_variance/dist.variance());
			Collection<Double> scaledSequence = new LinkedList<Double>();
			for (Double element : oldSequence) {
				scaledSequence.add(factor * (element - previousMean) + previousMean);
			}
			return new CyclicSequenceBuilder(scaledSequence);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shift(double)
		 */
		@Override
		public AbstractDistBuilder<CyclicSequenceDist> shiftToMean(double _mean) {
			CyclicSequenceDist dist = build();
			double previousMean = dist.mean();
			Collection<Double> shiftedSequence = new LinkedList<Double>();
			for (Double element : dist.sequence()) {
				shiftedSequence.add((element - previousMean) + _mean);
			}
			return new CyclicSequenceBuilder(shiftedSequence);
		}

	}
	
	public static CyclicSequenceBuilder createCyclicSequence(Collection<Double> _sequence) {
		return new CyclicSequenceBuilder(_sequence);
	}

	/**
	 * The index of the actual sample in the array
	 */
	private int index = 0;

	/**
	 * @param _cyclicSequenceBuilder
	 */
	private CyclicSequenceDist(CyclicSequenceBuilder _builder) {
		super(_builder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see desmoj.core.dist.RealDist#sample()
	 */
	@Override
	public Double sample() {
		double ret = sequence.get(index++);
		index %= sequence.size();
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see desmoj.core.dist.RealDist#canSample()
	 */
	@Override
	public boolean canSample() {
		return index < sequence.size();
	}

	/**
	 * Reset the cyclic sequence to the beginning of the sequence.
	 * 
	 */
	@Override
	public void resetToSubstream() {
		super.resetToSubstream();
		index = 0;
	}


	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.AbstractDistribution#toString()
	 */
	@Override
	public String toString() {
		String result = super.toString();
		result += String.format("index=%d", index) + TextUtilities.LINEBREAK;
		result += String.format("sequence=%s", sequence) + TextUtilities.LINEBREAK;
		return result;
	}

}
