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
/*
 * Created on 17.09.2006
 *
 */
package de.christianseipl.utilities.identification;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import de.christianseipl.utilities.identification.AbstractObjectID;

/**
 * @author Christian Seipl
 *
 */
public final class ExampleID extends AbstractObjectID {


	private static final transient WeakHashMap<String, WeakReference<ExampleID>> pool = new WeakHashMap<String, WeakReference<ExampleID>>(100);
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Gibt ein Objekt vom Typ {@link ExampleID} zurück.
	 * 
	 * Dieses Objekt stammt, sofern es schon einmal erzeugt
	 * wurde aus einem Pool und wird nicht neu erzeugt.
	 * 
	 * @param _name
	 * @return
	 */
	public static ExampleID valueOf(String _name) {
		final WeakReference<ExampleID> fromPool = pool.get(_name);
		if (fromPool == null || fromPool.get() == null) {
			final ExampleID toPool = new ExampleID(_name);
			pool.put(_name, new WeakReference<ExampleID>(toPool));
			return toPool;
		}
		return fromPool.get();
	}

	/**
     * @param _name
     */
    private ExampleID(String _name) {
        super(_name);
    }


	public static final ExampleID DEFAULT_EXAMPLE = valueOf("Default example");

	/**
	 * Implementiert das NULL-Object, welches für nicht initialisierte
	 * Werte benutzt wird. Vgl. auch "Refactoring" [Fowler].
	 */
	public static final ExampleID  NULL_EXAMPLE = valueOf("NULL Example");

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		pool.put(getName(), new WeakReference<ExampleID>(this));
	}

}
