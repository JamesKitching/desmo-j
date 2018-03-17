package desmoj.extensions.applicationDomains.petriNets;

/**
 * Acts as a default TokenType in any PetriNet. If tokens are being added to a
 * Place or the the input/output Maps of a Transition without specifying a type,
 * they will have an object of this class as type automatically. Note that it is
 * unique only within a certain PetriNet. Linking multiple nets that use their
 * respective default tokens will result in these types of tokens being treated
 * as different from each other.
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
public class DefaultToken implements TokenType {

	/**
	 * Returns a textual description of the TokenType to be shown in any related
	 * Report.
	 * 
	 */
	public String getTokenDescription() {
		return "default";
	}

}
