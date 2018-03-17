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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Diese Klasse enthält statische Hilfsmethoden, welche auf Collections operieren. Die
 * Collections werden hierbei immer als Ganzes behandelt.
 * 
 * 
 * @author Christian Seipl
 *
 */
public class CollectionsMath {

	/**
	 * Berechnet die Summe aus allen Werten der Collection.
	 * 
	 * Falls die Collection leer ist, wird {@link Double#NaN} zurückgegeben.
	 * 
	 * @param _coll 
	 * @return die Summe
	 */
	public static double sum(Collection<? extends Number> _coll) {
		if (_coll.isEmpty()) return Double.NaN;
		double sum = 0.0d;
		for (Number value : _coll) {
			sum += value.doubleValue();
		}
		return sum;
	}

	/**
	 * Skaliert die Liste mit einem Multiplikator, so daß die Summe dem Zielwert
	 * entspricht und gibt eine neue Liste mit den veränderten Werten zurück.
	 * 
	 * @param _list
	 * @return Eine neue Collection - die Reihenfolge bleibt erhalten.
	 */
	public static Collection<Double> scale(Collection<Double> _list,
			double _target) {
		return CollectionsMath.times(_list, _target / sum(_list));
	}

	/**
	 * Multipliziert jeden Wert der Liste mit dem Faktor und gibt eine neue
	 * Liste mit den veränderten Werten zurück.
	 * 
	 * @param _list
	 * @param _factor
	 * @return
	 */
	public static Collection<Double> times(
			Collection<? extends Number> _list, double _factor) {
		List<Double> result = new ArrayList<Double>(_list.size());
		for (Number _value : _list) {
			result.add(result.size(), _value.doubleValue() * _factor);
		}
		return result;
	}

	/**
	 * Berechnet den Mittelwert der übergebenen Collection.
	 * 
	 * @param _coll
	 * @return Der Mittelwert, bzw. NaN, wenn die Collection leer ist.
	 */
	public static double mean(Collection<? extends Number> _coll) {
		return _coll.size() == 0 ? Double.NaN : sum(_coll) / _coll.size();
	}

}
