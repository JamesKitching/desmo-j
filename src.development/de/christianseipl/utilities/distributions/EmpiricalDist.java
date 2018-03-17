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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import de.christianseipl.utilities.TextUtilities;

/**
 * An empirical distribution samples from a given discrete list of class value -
 * probability pairs.
 * 
 * There is no restriction on the values of the distribution, they might be
 * positive of negative. The sum of the probabilities must not be 1.0 - during
 * initialization, the probabilites are normalized.
 * 
 * @author Christian Seipl
 * 
 */
public final class EmpiricalDist extends AbstractDistribution<Double> {

	public static final class EmpiricalDistBuilder extends AbstractDistBuilder<EmpiricalDist> {

		private static final long serialVersionUID = 1L;
		private final Map<Double, Double> density = new HashMap<Double, Double>();

		public EmpiricalDistBuilder(Map<Double, Double> _density) {
			super(true);
			density.putAll(_density);
		}
		
		@Override
		public EmpiricalDist build() {
			return new EmpiricalDist(this);
		}

		/**
		 * FIXME: Fehler in der Skalierung korrigieren
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#scaleToVariance(double)
		 */
		@Override
		public AbstractDistBuilder<EmpiricalDist> scaleToVariance(double _variance) {
			EmpiricalDist dist = build();
			TreeMap<Double, Double> map = new TreeMap<Double, Double>(dist.getDensity());
			
			List<Double> sequence = new ArrayList<Double>(
					new SequenceDist.SequenceBuilder(map.keySet())
					.scaleToVariance(_variance)
					.build()
					.sequence()
					);
			Map<Double, Double> result = new HashMap<Double, Double>();
			int i = 0;
			for (Entry<Double, Double> element : map.entrySet()) {
				result.put(sequence.get(i++), element.getValue());
			}
			
			return new EmpiricalDistBuilder(result);
		}

		/* (non-Javadoc)
		 * @see de.christianseipl.utilities.distributions.AbstractDistribution.AbstractDistBuilder#shift(double)
		 */
		@Override
		public AbstractDistBuilder<EmpiricalDist> shiftToMean(double _mean) {
			EmpiricalDist dist = build();
			double previousMean = dist.mean();
			Map<Double, Double> previousDensity = dist.getDensity();
			Map<Double, Double> newDensity = new HashMap<Double, Double>();
			for (Entry<Double, Double> element : previousDensity.entrySet()) {
				newDensity.put((element.getKey() - previousMean) + _mean, element.getValue());
			}
			return new EmpiricalDistBuilder(newDensity);
		}
		
	}
	
	@Override
	public String toString() {
		String result = super.toString() + TextUtilities.LINEBREAK;
		result += String.format("upper Bounds=%s", Arrays.toString(upperBounds)) + TextUtilities.LINEBREAK;
		result += String.format("values=%s", Arrays.toString(values)) + TextUtilities.LINEBREAK;
		return result;
	}

	private static final long serialVersionUID = 1L;

	public static EmpiricalDistBuilder createEmpiricalDistribution(
			Map<Double, Double> _density) {
		return new EmpiricalDistBuilder(_density);
	}

	/**
	 * With the given density function, the object calculates a distribution
	 * function. The value of the class is stored here.
	 * 
	 */
	private final double[] values;

	/**
	 * This array contains the upper bounds of the discrete intervals of the
	 * distribution function. The range of the upper bounds starts at p(x)> 0
	 * and ends at p(x)=1.0
	 */
	private final double[] upperBounds;

	/**
	 * Internal field with the density of the distribution. The key of the map
	 * is the class, the value is the weight of the class.
	 */
	private final SortedMap<Double, Double> density;

	/**
	 * @param _empiricalDistBuilder
	 */
	private EmpiricalDist(EmpiricalDistBuilder _builder) {
		super(_builder);
		Map<Double, Double> _density = _builder.density;
		double total = 0;
		for (Entry<Double, Double> x : _density.entrySet()) {
			total += x.getValue(); // calculate the sum of all f(x)
		}
		// store the norm density 
		density = new TreeMap<Double, Double>();
		for (Entry<Double, Double> element : _density.entrySet()) {
			density.put(element.getKey(), element.getValue() / total);
		}
		
		double currentBound = 0d;
		
		int nrOfClasses = this.density.keySet().size();
		values = new double[nrOfClasses];
		upperBounds = new double[nrOfClasses];
		int i = 0;
		for (Entry<Double, Double> x : this.density.entrySet()) {
			currentBound += x.getValue(); // sum up the f(x)
			values[i] = x.getKey();
			upperBounds[i] = currentBound;
			i++;
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unihh.ilt.sim.layer0.dist.AbstractRealDist#sample()
	 */
	@Override
	public Double sample() {
		double rng = getRandomGenerator().nextDouble();
		for (int i = 0; i < values.length; i++) {
			if (rng < upperBounds[i])
				return values[i];
		}
		return 0d;
	}

	/**
	 * Returns the density function
	 * 
	 * @return A reference to the density function.
	 */
	public Map<Double, Double> getDensity() {
		return Collections.unmodifiableMap(density);
	}

	/**
	 * @return A copy of the distribution function. The key of the map is x, the
	 *         density is in the value of the map.
	 */
	public Map<Double, Double> getDistribution() {
		Map<Double, Double> dist = new HashMap<Double, Double>();
		for (int i = 0; i < values.length; i++) {
			dist.put(values[i], upperBounds[i]);
		}
		return dist;
	}

	@Override
	public Double minimum() {
		return Collections.min(density.keySet());
	}

	@Override
	public Double maximum() {
		return Collections.min(density.keySet());
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
		double result = 0.0;
		for (Entry<Double, Double> element : density.entrySet()) {
			result += element.getKey() * element.getValue();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.distributions.StochasticDist#variance()
	 */
	@Override
	public double variance() {
		double sum = 0d;
		double mean = mean();			
		for (Entry<Double, Double> element : density.entrySet()) {
			sum += Math.pow((element.getKey() - mean), 2d) * element.getValue();
		}
		return sum;
	}
}
