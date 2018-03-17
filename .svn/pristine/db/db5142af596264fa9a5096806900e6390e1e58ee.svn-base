package desmoj.extensions.applicationDomains.petriNets;

import java.util.HashMap;
import java.util.Map;

/**
 * Class providing methods for transforming different representations of token
 * multisets.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Peter W&uuml;ppen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
public class TokenMultiSetTools {

	/**
	 * Transforms two arrays that represent a multiset of tokens into a Map that
	 * does the same. The first array is meant to be a list of the contained
	 * token types, while the second array contains their respective amount in
	 * the set. The returned Map then maps the respective values onto each
	 * other.
	 * 
	 * @param type
	 *            An Array of all the types of tokens to be be found in the
	 *            multiset
	 * 
	 * @param amount
	 *            The amount of each respective token type, in the order given
	 *            by the type array.
	 * @return The resulting map
	 */
	public static Map<TokenType, Integer> arrayToMap(TokenType[] type,
			int[] amount) {
		Map<TokenType, Integer> tokenMap = new HashMap<TokenType, Integer>();
		for (int i = 0; i < type.length; i++) {
			tokenMap.put(type[i], amount[i]);
		}
		return tokenMap;
	}

	/**
	 * Multiplies all the amount values of a given input Map by -1 and returns
	 * the result as a new map. Does not change the original input map.
	 * 
	 * @param inputMap
	 *            The original Map representing a token multiset to be negated
	 *            
	 * @return The resulting map
	 */
	public static Map<TokenType, Integer> negativeMap(
			Map<TokenType, Integer> inputMap) {
		Map<TokenType, Integer> result = new HashMap<TokenType, Integer>();
		for (Map.Entry<TokenType, Integer> entry : inputMap.entrySet()) {
			result.put(entry.getKey(), -entry.getValue());
		}
		return result;
	}

}
