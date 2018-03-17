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


/**
 * Distribution implementing the null pattern.
 * 
 * @author Christian Seipl
 *
 */
public class NullDistDouble extends AbstractDistribution<Double> {

	public static NullDistBuilder createNullDist() {
		return new NullDistBuilder(false);
	}
	
	public static final class NullDistBuilder extends AbstractDistBuilder<NullDistDouble> {

		/**
		 * @param _useRNG
		 */
		public NullDistBuilder(boolean _useRNG) {
			super(_useRNG);
		}

		private static final long serialVersionUID = 1L;

		@Override
		public NullDistDouble build() {
			return new NullDistDouble(this);
		}

		@Override
		public AbstractDistBuilder<NullDistDouble> scaleToVariance(double _variance) {
			return this;
		}

		@Override
		public AbstractDistBuilder<NullDistDouble> shiftToMean(double _mean) {
			return this;
		}
		
	}
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param _nullDistBuilder
	 */
	private NullDistDouble(NullDistBuilder _builder) {
		super(_builder);
	}

	@Override
	public boolean canSample() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLowerBound() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasUpperBound() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double maximum() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double mean() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double minimum() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double sample() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double variance() {
		throw new UnsupportedOperationException();
	}

}
