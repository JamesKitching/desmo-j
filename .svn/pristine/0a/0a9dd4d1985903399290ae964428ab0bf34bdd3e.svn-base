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
import java.util.Map.Entry;

import org.apache.commons.math.util.MathUtils;

import de.christianseipl.utilities.distributions.StochasticDist;

/**
 * Diese Klasse enthält statische Hilfsmethoden, welche auf Maps operieren. Die
 * Maps werden hierbei immer als Ganzes behandelt, so daß alle mathematische
 * Operationen auf den Werten der Maps durchgeführt werden.
 * 
 * 
 * Alle Methoden, bei denen zwei Maps als Parameter verwendet werden, erzeugen
 * eine {@link IllegalArgumentException}, falls die Maps nicht identische
 * Key-Sets besitzen.
 * 
 * @author Christian Seipl
 * 
 */
public final class MapMath {
	

	
	/**
	 * Definiert eine Schnittstelle, mit der auf einer Map mit Schlüsseln vom
	 * Typ K eine Operation {@link #transform(K, StochasticDist)} durchgeführt wird und
	 * im Ergebnis ein Objekt vom Typ {@code T extends Number} erzeugt wird.
	 * 
	 * @author Christian Seipl
	 *
	 * @param <K> ein beliebiger Objekttyp
	 * @param <T> Ein von {@link Number} abgeleiteter Typ
	 */
	public static interface DistributionTransformer<K, T extends Number> {
		public T transform(K _key, StochasticDist<T> _dist);
	}


	/**
	 * Definiert eine Schnittstelle, mit der auf einer Map mit Schlüsseln vom
	 * Typ K eine Operation {@link #transform(K, Number)} durchgeführt wird und
	 * im Ergebnis ein Objekt vom Typ {@code T extends Number} erzeugt wird.
	 * 
	 * @author Christian Seipl
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 * @param <T> Ein von {@link Number} abgeleiteter Typ
	 */
	public static interface NumberTransformer<K, T extends Number> {
		/**
		 * Führt die Transformation auf Basis vom Schlüssel {@code _key} und der
		 * Zahl {@code _number} durch und erzeugt ein neues Objekt vom Typ
		 * {@code T extends Number}.
		 * 
		 * @param _key
		 *            Schlüssel der Map
		 * @param _number
		 *            Zahl, auf der die Operation durchgeführt wird
		 * @return Eine neue Zahl vom Typ {@code T extends Number}
		 */
		public T transform(K _key, Number _number);
	}
	
	/**
	 * Addiert zu jedem Eintrag in der Map den Wert {@code _value} hinzu.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 *            Die Ursprungsmap mit den Werten für den ersten Summanden
	 * @param _value
	 *            der zweite Summand
	 * @return Eine neue Map deren Werte die Summe darstellen.
	 */
	public static <K> Map<K, Double> add(
			Map<? extends K, ? extends Number> _map, final double _value) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return _number.doubleValue() + _value;
			}
		});
	}

	/**
	 * Addiert zwei Maps und liefert eine neue Map mit der paarweisen
	 * Addition der Werte.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 * @param _this Die Ursprungsmap mit den Werten für den ersten Summanden
	 * @param _that Die Ursprungsmap mit den Werten für den zweiten Summanden
	 * @return Eine neue Map mit den neuen Werten
	 */
	public static <K> Map<K, Double> add(
			Map<? extends K, ? extends Number> _this, final Map<? extends K, ? extends Number> _that) {
		return operateOnMap(_this, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return _number.doubleValue() + _that.get(_key).doubleValue();
			}
		});
	}

	/**
	 * Führt die {@link Math#ceil(double)} Operation auf jedem Element der Map
	 * durch
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 *            Die Ursprungsmap
	 * @return Eine neue Map, deren Wert das Ergebnis von
	 *         {@link Math#ceil(double)} darstellen.
	 */
	public static <K> Map<K, Double> ceil(
			Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Math.ceil(_number.doubleValue());
			}

		});
	}

	/**
	 * Überprüft, ob bei beiden Maps die Menge der Keys identisch ist.
	 * TODO: areKeysEqual in allen Methoden einsetzen, an denen zwei Maps bzw. eine andere Map beteiligt ist.
	 * @param _this
	 * @param _that
	 * @throws IllegalArgumentException
	 *             , falls die Sets nicht identisch sind.
	 */
	public static void areKeysEqual(Map<?, ? extends Number> _this,
			Map<?, ? extends Number> _that) {
		if (!_this.keySet().equals(_that.keySet())) {
			throw new IllegalArgumentException("Key sets are not identical");
		}
	}

	/**
	 * Erzeugt eine neue Map aus den übergebenen Schlüsseln {@code _keys}.
	 * Alle Werte werden auf 1 gesetzt.
	 *  
	 * @param <K>
	 * @param _keys
	 * @param _value
	 * @return
	 */
	public static <K> Map<K, Double> createUnityMap(Set<? extends K> _keys) {
		return createWithConstantValues(_keys, 1d);
	}
	
	/**
	 * Erzeugt eine neue Map aus den übergebenen Schlüsseln {@code _keys}
	 * und dem Wert {@code _value}.
	 * @param <K>
	 * @param _keys
	 * @param _value
	 * @return
	 */
	public static <K> Map<K, Double> createWithConstantValues(Set<? extends K> _keys, double _value) {
		Map<K, Double> result = new HashMap<K, Double>();
		for (K k : _keys) {
			result.put(k, _value);
		}
		return result;
	}

	/**
	 * Erzeugt eine Map, deren Schlüssel aus den übergebenen Werten bestehen und
	 * auf die eine Transformationsfunktion angewendet wird, die Proben aus der
	 * übergebenen Verteilung zieht, ohne die Schlüssel zu berücksichtigen.
	 * 
	 * @param <K>  ein beliebiger Objekttyp
	 * @param _keys
	 * @param _dist
	 * @return
	 */
	public static <K> Map<K, Double> createWithSamples(Set<K> _keys,
			StochasticDist<Double> _dist) {
		return createWithSamples(_keys, _dist,
				new DistributionTransformer<K, Double>() {

					@Override
					public Double transform(K _key, StochasticDist<Double> _trans_dist) {
						return _trans_dist.sample();
					}
				});
	}
	
	/**
	 * Erzeugt eine Map, deren Schlüssel aus den übergebenen Werten bestehen und
	 * auf die eine Transformationsfunktion angewendet wird, deren Verteilung
	 * ebenfalls übergeben wird.
	 * 
	 * @param <K>  ein beliebiger Objekttyp
	 * @param <T> Ein von {@link Number} abgeleiteter Typ
	 * @param _keys
	 * @param _dist
	 * @param _transformer
	 * @return
	 */
	public static <K, T extends Number> Map<K, T> createWithSamples(
			Set<? extends K> _keys, StochasticDist<T> _dist,
			DistributionTransformer<K, T> _transformer) {
		Map<K, T> result = new HashMap<K, T>(_keys.size());
		for (K key : _keys) {
			result.put(key, _transformer.transform(key, _dist));
		}
		return result;
	}

	/**
	 * Erzeugt eine neue Map aus den übergebenen Schlüsseln {@code _keys}.
	 * Alle Werte werden auf 0 gesetzt.
	 *  
	 * @param <K>
	 * @param _keys
	 * @param _value
	 * @return
	 */
	public static <K> Map<K, Double> createZeroMap(Set<? extends K> _keys) {
		return createWithConstantValues(_keys, 0d);
	}

	/**
	 * Dividiert jedes Element der Map {@code _this} durch den entsprechenden
	 * Wert der Map {@code _that}. Das Ergebnis wird im Rückgabewert abgelegt.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _this
	 *            Dividend
	 * @param _that
	 *            Divisor
	 * @return Eine neue Map mit den Quotienten.
	 */
	public static <K> Map<K, Double> divide(
			Map<? extends K, ? extends Number> _this,
			final Map<? extends K, ? extends Number> _that) {

		areKeysEqual(_this, _that);

		return operateOnMap(_this, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return _number.doubleValue() / _that.get(_key).doubleValue();
			}

		});
	}

	/**
	 * Erzeugt eine spezielle Map, zu deren Schlüssel der jeweilige Wert {@code
	 * _repetitions} wiederholt in der Collection abgelegt wird.
	 * 
	 * @param <K>
	 * @param <V>
	 * @param _map
	 * @param _repetitions Anzahl an Wiederholungen, muß größer als Null sein.
	 * @return
	 */
	public static <K, V> Map<K, Collection<V>> expandToMultiMap(Map<K, V> _map,
			int _repetitions) {
		if (_repetitions < 0)
			throw new IllegalArgumentException();
		Map<K, Collection<V>> result = new HashMap<K, Collection<V>>();
		for (Entry<K, V> entry : _map.entrySet()) {
			result.put(entry.getKey(), Collections.nCopies(_repetitions, entry
					.getValue()));
		}
		return result;
	}

	/**
	 * Führt die {@link Math#floor(double)} Operation auf jedem Element der Map
	 * durch
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 *            Die Ursprungsmap
	 * @return Eine neue Map, deren Werte das Ergebnis von {@link Math#floor}
	 *         darstellen.
	 */
	public static <K> Map<K, Double> floor(
			Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Math.floor(_number.doubleValue());
			}

		});
	}


	/**
	 * Es wird folgende Operation durchgeführt:
	 * <code>d - Math.floor(d)</code>
	 * 
	 * Der Nachkommateil hat somit einen Wertebereich von [0;+1]. Ist d
	 * negativ, so wird die Differenz zur nächsten kleineren ganzen Zahl
	 * zurückgegeben.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 *            Die Ursprungsmap
	 * @return Eine neue Map mit den Nachkommaanteilen
	 */
	public static <K> Map<K, Double> frac(
			Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return _number.doubleValue() - Math.floor(_number.doubleValue());
			}

		});
	}

	/**
	 * Erzeugt eine neue Map mit den Absolutwerten der Ursprungsmap.
	 * 
	 * @see Math#abs(double)
	 * @param <K> ein beliebiger Objekttyp
	 * @param _map Die Ursprungsmap
	 * @return Eine neue Map mit den Absolutwerten 
	 */
	public static <K> Map<K, Double> abs(
			Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Math.abs(_number.doubleValue());
			}

		});
	}


	/**
	 * Erzeugt eine neue Map mit den Vorzeichen der Ursprungsmap.
	 * 
	 * @see Math#signum(double)
	 * @param <K> ein beliebiger Objekttyp
	 * @param _map Die Ursprungsmap
	 * @return Eine neue Map mit den Absolutwerten 
	 */
	public static <K> Map<K, Double> signum(
			Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Math.signum(_number.doubleValue());
			}

		});
	}
	
	/**
	 * Berechnet den Mittelwert aus den Werten der Map.
	 * 
	 * @param <K>
	 * @param _map
	 * @return {@link CollectionsMath#mean(Collection)}
	 */
	public static double mean(Map<?, Double> _map) {
		return CollectionsMath.mean(_map.values());
	}

	/**
	 * Kehrt das Vorzeichen von jedem Element um.
	 * @param <K>
	 * @param _map
	 * @return
	 */
	public static <K> Map<K, Double> negate(
			Map<? extends K, ? extends Number> _map) {
		return times(_map, -1d);
	}

	/**
	 * Führt für jeden Eintrag in der Map eine Transformation durch und liefert
	 * die neue Map zurück.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param <T>
	 *            Typ des Rückgabeobjektes, welches als Wert in der Map
	 *            gespeichert wird
	 * @param _map
	 *            Die Map, auf der die Operationen durchgeführt werden
	 * @param _transformer
	 *            Der Transformer, der auf der Map angewendet wird.
	 * @return Eine neue Map, die das Ergebnise der Transformation darstellt.
	 */
	public static <K, T extends Number> Map<K, T> operateOnMap(
			Map<? extends K, ? extends Number> _map,
			NumberTransformer<K, T> _transformer) {
		Map<K, T> result = new HashMap<K, T>();
		for (Entry<? extends K, ? extends Number> entry : _map.entrySet()) {
			result.put(entry.getKey(), _transformer.transform(entry.getKey(),
					entry.getValue()));
		}
		return result;
	}
	
	/**
	 * Erhebt jeden Wert der Map mit Hilfe von {@link Math#pow(double, double)} 
	 * zur Potenz. 
	 * 
	 * @param <K>
	 *            ein beliebiger Objekttyp
	 * @param _map Basis
	 * @param _exponent Exponent
	 * @return Eine neue Map mit den zur Potenz erhobenen Werten.
	 */
	public static <K> Map<K, Double> power(
			Map<? extends K, ? extends Number> _map, final double _exponent) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Math.pow(_number.doubleValue(), _exponent);
			}

		});
	}
	

	/**
	 * Erhebt jeden Wert der Map mit Hilfe von {@link Math#pow(double, double)} 
	 * zur Potenz. 
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 * @param _this Basis
	 * @param _that Exponent
	 * @return Eine neue Map mit der Potenz
	 */
	public static <K> Map<K, Double> power(
			Map<? extends K, ? extends Number> _this, 
			final Map<? extends K, ? extends Number> _that) {
		return operateOnMap(_this, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Math.pow(_number.doubleValue(), _that.get(_key).doubleValue());
			}

		});
	}
	
	/**
	 * Führt die {@link Math#round(double)} Operation auf jedem Element der Map
	 * durch
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 *            Die Ursprungsmap
	 * @return Eine neue Map, deren Wert das Ergebnis von
	 *         {@link Math#round(double)} darstellen.
	 */
	public static <K> Map<K, Double> round(
			Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Double.valueOf(Math.round(_number.doubleValue()));
			}

		});
	}

	/**
	 * Führt die {@link MathUtils#round(double, int)} Operation auf jedem Element der Map
	 * durch. Die Anzahl an signifikanten Stellen kann hierbei vorgegeben werden.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 *            Die Ursprungsmap
	 * @param _scale Die Anzahl an signifikanten Stellen.
	 * @return Eine neue Map, deren Wert das Ergebnis von
	 *         {@link Math#round(double)} darstellen.
	 */
	public static <K> Map<K, Double> round(
			Map<? extends K, ? extends Number> _map, final int _scale) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return Double.valueOf(MathUtils.round(_number.doubleValue(), _scale));
			}

		});
	}
	
	/**
	 * Sklaliert alle Werte der Map so, daß die Summe der Werte dem Zielwert
	 * {@code _target} entspricht.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _map
	 * @param _target
	 * @return Eine neue Map mit modifizierten Werten.
	 */
	public static <K> Map<K, Double> scale(
			Map<? extends K, ? extends Number> _map, double _target) {
		return times(_map, _target / sumValues(_map));
	}

	/**
	 * Berechnet die Wurzel für jedes Element in der Map
	 * @param <K> ein beliebiger Objekttyp
	 * @param _map
	 * @return Eine neue Map mit der Wurzel für jeden Eintrag
	 */
	public static <K> Map<K, Double> sqrt(Map<? extends K, ? extends Number> _map) {
		return operateOnMap(_map, new NumberTransformer<K, Double>(){

			@Override
			public Double transform(K _key, Number _number) {
				return Math.sqrt(_number.doubleValue());
			}
			
		}
		);
	}

	/**
	 * Berechnet die Differenz zwischen jedem Wert der Map und {@code _value}
	 * @param <K>
	 * @param _map
	 * @param _value
	 * @return
	 */
	public static <K> Map<K, Double> subtract(
			Map<? extends K, ? extends Number> _map, final double _value) {
		return add(_map, -1 * _value);
	}


	/**
	 * Subtrahiert {@code _that} von {@code _this} neue Map mit der paarweisen
	 * Differenz der Werte.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 * @param _this Die Ursprungsmap mit den Werten für den Minuend
	 * @param _that Die Ursprungsmap mit den Werten für den Subtrahend
	 * @return Eine neue Map mit der paarweisen Differenz
	 */
	public static <K> Map<K, Double> subtract(
			Map<? extends K, ? extends Number> _this, final Map<? extends K, ? extends Number> _that) {
		return add(_this, negate(_that));
	}
	
	/**
	 * Berechnet die Summe der Werte in der Map
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 * @param _map
	 * @return die Summe der Werte
	 */
	public static <K> double sumValues(Map<? extends K, ? extends Number> _map) {
		return CollectionsMath.sum(_map.values());
	}

	/**
	 * Multipliziert alle Werte der Map mit {@code _factor}.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 * @param _map
	 * @param _factor
	 * @return Eine neue Map mit dem Produkt aus der alten Map und {@code
	 *         _factor}.
	 */
	public static <K> Map<K, Double> times(
			Map<? extends K, ? extends Number> _map, final double _factor) {
		return operateOnMap(_map, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return _number.doubleValue() * _factor;
			}

		});
	}

	/**
	 * Multipliziert die Werte zweier Maps paarweise miteinander und gibt eine
	 * neue Map zurück.
	 * 
	 * @param <K> ein beliebiger Objekttyp
	 *            
	 * @param _this
	 *            Erster Faktor
	 * @param _that
	 *            Zweiter Faktor
	 * @return Eine neue Map mit dem paarweisen Produkt der beiden Faktoren.
	 */
	public static <K> Map<K, Double> times(
			Map<? extends K, ? extends Number> _this,
			final Map<? extends K, ? extends Number> _that) {

		areKeysEqual(_this, _that);

		return operateOnMap(_this, new NumberTransformer<K, Double>() {

			@Override
			public Double transform(K _key, Number _number) {
				return _number.doubleValue() * _that.get(_key).doubleValue();
			}

		});
	}

}
