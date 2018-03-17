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

import java.io.Serializable;

/**
 * Abstrakte Oberklasse zur eindeutigen Abbildung von Namen auf 
 * Identifikationsnummern.
 * 
 * Die Abbildung wird über eine Hash-Funktion erreicht, so daß eine
 * Kollision nur mit einer geringen Wahrscheinlichkeit passieren kann.
 * Zusätzlich müssen alle Nachkommen bei der Vergabe des Namens
 * die Bezeichnung der Klasse (z.B. de.unihh.ilt.utilities.EntityID)
 * voranstellen. Hiermit wird vermieden, daß zwei gleichnamige
 * Objekte die gleiche ID bekommen (eine Verwechslung wird auch
 * durch die Typsicherheit erreicht).
 * 
 * 
 * @author Christian Seipl
 *
 */
public abstract class AbstractObjectID implements Serializable {
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return getName();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Der zur ID zugehörige Name.
     */
    private final String idName;
    
    private final int hash;
    
    /**
     * Gibt die ID des Objektes zurück.
     * 
     * @return ID
     */
    public final int getIndex() {
        return hash;
    }

    /**
     * @return der Name, aus dem die Zuorndung erzeugt wurde
     */
    public final String getName() {
    	return idName;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return hash;
	}

	/**
	 * @return
	 */
	private int calcHashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (idName == null ? 0 : idName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractObjectID other = (AbstractObjectID) obj;
		// Fastfail - wenn der Hashcode nicht identisch ist, sind auch die 
		// Inhalte nicht identisch. Der Umkehrschluß gilt aber aufgrund der
		// Möglichkeit zur Kollision hier nicht.
		if (hash != other.hash) return false;
		if (idName == null) {
			if (other.idName != null)
				return false;
		} else if (!idName.equals(other.idName))
			return false;
		return true;
	}

	/**
     * @param name
     */
    protected AbstractObjectID(String name) {
        idName = name;
		hash = calcHashCode();
    }

}
