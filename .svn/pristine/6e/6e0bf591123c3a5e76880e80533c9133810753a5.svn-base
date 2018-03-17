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
package de.christianseipl.utilities.maputils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.christianseipl.utilities.distributions.StochasticDist;


/**
 * Spezielle Map, die eine Menge an Zahlen repräsentiert zusammen 
 * den darauf möglichen Operationen. Die Operationen stimmen
 * mit denen aus der Klasse {@link MapMath} überein. Die Methoden
 * wurden so implementiert, daß die Aufrufe verkettet werden können,
 * um eine Programmierung ähnlich einer Domain-Specific-Language
 * zu erreichen.
 * 
 * Intern wird per Delegation eine {@link HashMap} angesprochen,
 * die mittels {@link Collections#unmodifiableMap(Map)} vor 
 * Änderungen geschützt ist.
 * 
 * 
 * TODO: alle Methoden herausnehmen, die den Inhalt über die Map-Methoden verändern, ggf. eine Unmodifiable Map benutzen, da diese alle Fälle bereits berücksichtigt.
 * @author Christian Seipl
 *
 */
public final class MapMathImpl<E> implements Map<E, Double> {

	private final Map<E, Double> delegation;
	
	private static final long serialVersionUID = 1L;

	public static final <E> MapMathImpl<E> createFromDistribution(Set<? extends E> _keys, StochasticDist<Double> _dist) {
		return new MapMathImpl<E>(MapMath.createWithSamples(_keys, _dist));
	}

	public static final <E> MapMathImpl<E> createFromDistribution(Set<? extends E> _keys, StochasticDist<Double> _dist, MapMath.DistributionTransformer<E, Double> _transformer) {
		return new MapMathImpl<E>(MapMath.createWithSamples(_keys, _dist, _transformer));
	}

	public static final <E> MapMathImpl<E> createUnityMap(Set<? extends E> _keys) {
		return new MapMathImpl<E>(MapMath.createUnityMap(_keys));
	}
	
	public static final <E> MapMathImpl<E> createWithConstantValues(Set<? extends E> _keys, double _value) {
		return new MapMathImpl<E>(MapMath.createWithConstantValues(_keys, _value));
	}

	public static final <E> MapMathImpl<E> createZeroMap(Set<? extends E> _keys) {
		return new MapMathImpl<E>(MapMath.createZeroMap(_keys));
	}

	/**
	 * @see HashMap#HashMap()
	 */
	public MapMathImpl() {
		delegation = Collections.unmodifiableMap(new HashMap<E, Double>());
	}

	/**
	 * @see HashMap#HashMap(int)
	 */
	public MapMathImpl(int _initialCapacity) {
		delegation = Collections.unmodifiableMap(new HashMap<E, Double>(_initialCapacity));
	}


	/**
	 * @see HashMap#HashMap(int, float)
	 */
	public MapMathImpl(int _initialCapacity, float _loadFactor) {
		delegation = Collections.unmodifiableMap(new HashMap<E, Double>(_initialCapacity, _loadFactor));
	}
	
	/**
	 * Copy-Constructor
	 * @param _otherMap
	 */
	public MapMathImpl(Map<? extends E, Double> _otherMap) {
		delegation = Collections.unmodifiableMap(new HashMap<E, Double>(_otherMap));
	}
	
	public final MapMathImpl<E> abs() {
		return new MapMathImpl<E>(MapMath.abs(this));
	}
	
	public final MapMathImpl<E> add(double _value) {
		return new MapMathImpl<E>(MapMath.add(this, _value));
	}

	public final MapMathImpl<E> add(Map<? extends E, Double> _that) {
		return new MapMathImpl<E>(MapMath.add(this, _that));
	}
	
	public final MapMathImpl<E> ceil() {
		return new MapMathImpl<E>(MapMath.ceil(this));
	}

	public final MapMathImpl<E> divide(double _that) {
		return this.times(1d/_that);
	}
	
	public final MapMathImpl<E> divide(Map<? extends E, Double> _that) {
		return new MapMathImpl<E>(MapMath.divide(this, _that));
	}

	public final MapMathImpl<E> floor() {
		return new MapMathImpl<E>(MapMath.floor(this));
	}

	public final MapMathImpl<E> frac() {
		return new MapMathImpl<E>(MapMath.frac(this));
	}
	
	public final double mean() {
		return MapMath.mean(this);
	}
	
	public final MapMathImpl<E> pow(double _exponent) {
		return new MapMathImpl<E>(MapMath.power(this, _exponent));
	}

	public final MapMathImpl<E> pow(Map<? extends E, Double> _that) {
		return new MapMathImpl<E>(MapMath.power(this, _that));
	}
	
	public final MapMathImpl<E> round() {
		return new MapMathImpl<E>(MapMath.round(this));
	}

	public final MapMathImpl<E> round(int _scale) {
		return new MapMathImpl<E>(MapMath.round(this, _scale));
	}
	public final MapMathImpl<E> scale(double _target) {
		return new MapMathImpl<E>(MapMath.scale(this, _target));
	}

	public final MapMathImpl<E> signum() {
		return new MapMathImpl<E>(MapMath.signum(this));
	}
	
	public final MapMathImpl<E> sqrt() {
		return new MapMathImpl<E>(MapMath.sqrt(this));
	}

	public final MapMathImpl<E> subtract(double _value) {
		return new MapMathImpl<E>(MapMath.subtract(this, _value));
	}

	public final MapMathImpl<E> subtract(Map<? extends E, Double> _that) {
		return new MapMathImpl<E>(MapMath.subtract(this, _that));
	}

	public final double sumValues() {
		return MapMath.sumValues(this);
	}

	public final MapMathImpl<E> times(double _factor) {
		return new MapMathImpl<E>(MapMath.times(this, _factor));
	}
	
	public final MapMathImpl<E> times(Map<? extends E, Double> _that) {
		return new MapMathImpl<E>(MapMath.times(this, _that));
	}

	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		delegation.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object _key) {
		return delegation.containsKey(_key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object _value) {
		return delegation.containsValue(_value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<E, Double>> entrySet() {
		return delegation.entrySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public Double get(Object _key) {
		return delegation.get(_key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return delegation.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<E> keySet() {
		return delegation.keySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Double put(E _key, Double _value) {
		return delegation.put(_key, _value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends E, ? extends Double> _m) {
		delegation.putAll(_m);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public Double remove(Object _key) {
		return delegation.remove(_key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return delegation.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<Double> values() {
		return delegation.values();
	}

}
