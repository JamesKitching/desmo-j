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
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections15.MultiMap;

/**
 * @author Christian Seipl
 *
 */
public class MapPrinting {

	public static <K,V> void printOneDimensionalMap(String _header, Map<K,V> _map) {
		System.out.println(_header);
		for (Entry<K,V> entry : _map.entrySet()) {
			System.out.println(String.format("%20s\t%20s", entry.getKey().toString(), entry.getValue().toString()));
		}
		System.out.println();
	}
	
	public static <K,L,V> void printTwoDimensionalMap(String _header, Map<K,Map<L,V>> _map) {
		System.out.println(_header);
		for (Entry<K,Map<L,V>> first : _map.entrySet()) {
			System.out.println(first.getKey().toString());
			for (Entry<L,V> second : first.getValue().entrySet()) {
				System.out.println(String.format("%20s\t%20s\t%20s",
						first.getKey().toString(), 
						second.getKey().toString(),
						second.getValue().toString()));
			}
			System.out.println();
		}
		System.out.println();
	}
 
	public static <K,L,M,V> void printThreeDimensionalMap(String _header, Map<K,Map<L,Map<M,V>>> _map) {
		System.out.println(_header);
		for (Entry<K,Map<L,Map<M,V>>> first : _map.entrySet()) {
			System.out.println(first.getKey().toString());
			for (Entry<L,Map<M,V>> second : first.getValue().entrySet()) {
				System.out.println(String.format("%20s\t%20s", first.getKey().toString(), second.getKey().toString()));
				for (Entry<M,V> third : second.getValue().entrySet()) {
					System.out.println(String.format("%20s\t%20s\t%20s\t%20s",
							first.getKey().toString(), 
							second.getKey().toString(),
							third.getKey().toString(),
							third.getValue().toString()));
					
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static <K> void printSet(String _header, Set<K> _set) {
		Object[] elements = _set.toArray();
		System.out.println(_header);
		System.out.printf("[ ");
		for (int i = 0; i < elements.length - 1; i++) {
			System.out.printf("%s, ", elements[i].toString());
		}
		if (elements.length > 0) {
			System.out.printf("%s ", elements[elements.length - 1].toString());
		}
		System.out.printf("]");
		System.out.println();
		System.out.println();
	}
	
	public static <K,V> void printMultiMap(String _header, MultiMap<K, V> _map) {
		printMapCollection(_header, _map.map());
	}
	
	public static <K,V> void printMapCollection(String _header, Map<K, ? extends Collection<V>> _map) {
		System.out.println(_header);
		for (Entry<K, ? extends Collection<V>> entry : _map.entrySet()) {
			System.out.printf("%20s\t", entry.getKey().toString());
			Object[] elements = entry.getValue().toArray();
			System.out.printf("[ ");
			for (int i = 0; i < elements.length - 1; i++) {
				System.out.printf("%s, ", elements[i].toString());
			}
			if (elements.length > 0) {
				System.out.printf("%s ", elements[elements.length - 1].toString());
			}
			System.out.printf("]");
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
}
