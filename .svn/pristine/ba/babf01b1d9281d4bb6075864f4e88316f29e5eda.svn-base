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
 * Created on 26.07.2006
 *
 */
package de.christianseipl.utilities.rng;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;


/**
 * Dieser Zufallszahlengenerator ist eine Weiterentwicklung des Generators von
 * L'Ecuyer. Diese Implementierung ist um den Faktor 4 langsamer, als die
 * Originalimplementierung, benutzt jedoch ausschließlich Zahlen vom 
 * Typ long. Der Vorteil hierin ist, daß bei der Benutzung auf einem 64bit
 * Rechner dieser Generator schneller ist, und auch keine Rundungsfehler durch
 * die Umwandlung von long-double auftreten können.
 * 
 * Die Klasse wurde erweitert, um mehrere Teilströme zu erzeugen, ohne daß
 * interne Felder verändert werden. Dies ist nötig, um die Ströme von einer
 * zentralen Instanz aus zu verwalten. Dieser Manager kann alle registrierten
 * Ströme zurücksetzen bzw. um ein definiertes Intervall voranschreiten lassen.
 * 
 *  Die Idee hinter dieser Implementierung ist die Nutzung auf parallelen
 *  Rechnern, deren Ergebnisse reproduzierbar bleiben sollen. Jedem
 *  Durchlauf eines Experimentes wird ein Strom zugeordnet. Die Durchführung
 *  des Experimentes kann dann zu jedem Zeitpunkt stattfinden, unabhängig von
 *  den vorher durchgeführten Experimenten. Es ist so z.B. möglich, daß ein 
 *  Rechner Durchlauf Nr. 1, ein anderer Durchlauf Nr.2 ausführt. Die
 *  Ergebnisse sind identisch zu zwei Durchläufen auf einem Rechner.
 * <p>
 * Technische Daten
 * <p>
 * Die Länge des Gesamtstroms ist 2<sup>192</sup> (Rank 0)
 * <p>
 * Der Gesamtstrom ist unterteilt in 2<sup>192 - 127</sup> = 2<sup>65</sup>
 * Teilströme mit einer Länge von 2<sup>127</sup> (Rank 1)
 * <p>
 * Der Ebene 1 Teilstrom ist unterteilt in 2<sup>127 - 76</sup> = 2<sup>51</sup>
 * Teilströme der Länge 2<sup>76</sup> (Rank 2)
 * <p>
 * Der Ebene 2 Teilstrom ist unterteilt in 2<sup>76 - 51</sup> = 2<sup>25</sup>
 * Teilströme der Länge 2<sup>51</sup> (Rank 3)
 * 
 * 
 * @author Christian Seipl
 * 
 */
public final class MRG32k3a implements  Cloneable, Serializable, UniformRandomGenerator {

	/**
	 * @param _matrix Die Matrix, die ausgegeben werden soll.
	 * @param _name Name der Matrix, der mit ausgegeben wird.
	 * @return Eine Zeichenketter mit allen Elementen der übergebenen Matrix
	 */
	private static String formatMatrix(String _name, long[][] _matrix) {
		String result = String.format("%s=[", _name);
		for (int i = 0; i < _matrix.length; i++) {
			result += "[";
			for (int j = 0; j < _matrix[i].length; j++) {
				result += _matrix[i][j];
				if (j < (_matrix[i].length-1)) result += " ";
			}
			result += "]";
		}
		result += "]";
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String message = String.format("Layer 1=%s; Layer 2=%s; Layer 3=%s;", level1.toString(), level2.toString(), level3.toString());
		message += formatMatrix("initializationVector", initializationVector);
		message += formatMatrix("currentPosition", currentPosition);
		return message;
	}

	private static final long serialVersionUID = 1L;

	
	public static final class State implements Serializable {

		private static final long serialVersionUID = 1L;

		private final long[][] currentPosition;
		
		private final long[][] initializationVector;

		private final long[][] startOfSubStream;
		
		private final BigInteger level1;

		private final BigInteger level2;

		private final BigInteger level3;
		/**
		 * @param _parent
		 */
		private State(MRG32k3a _parent) {
			currentPosition = cloneMatrix(_parent.currentPosition);
			initializationVector = cloneMatrix(_parent.initializationVector);
			startOfSubStream = cloneMatrix(_parent.startOfSubStream);
			level1 = _parent.level1;
			level2 = _parent.level2;
			level3 = _parent.level3;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(currentPosition);
			result = prime * result + Arrays.hashCode(initializationVector);
			result = prime * result
					+ ((level1 == null) ? 0 : level1.hashCode());
			result = prime * result
					+ ((level2 == null) ? 0 : level2.hashCode());
			result = prime * result
					+ ((level3 == null) ? 0 : level3.hashCode());
			result = prime * result + Arrays.hashCode(startOfSubStream);
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final State other = (State) obj;
			if (! equalsMatrix(currentPosition, other.currentPosition))
				return false;
			if (! equalsMatrix(initializationVector, other.initializationVector))
				return false;
			if (! equalsMatrix(startOfSubStream, other.startOfSubStream))
				return false;
			if (level1 == null) {
				if (other.level1 != null)
					return false;
			} else if (!level1.equals(other.level1))
				return false;
			if (level2 == null) {
				if (other.level2 != null)
					return false;
			} else if (!level2.equals(other.level2))
				return false;
			if (level3 == null) {
				if (other.level3 != null)
					return false;
			} else if (!level3.equals(other.level3))
				return false;
			return true;
		}

		
	}
	
	/**
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        MRG32k3a that;
		try {
			that = (MRG32k3a) super.clone();
			that.initializationVector = cloneMatrix(this.initializationVector);
			that.startOfSubStream = cloneMatrix(this.startOfSubStream.clone());
			that.currentPosition = cloneMatrix(this.currentPosition.clone());
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		return that;
    }

    private static final long[][] packageIV = { { 12345L, 12345L, 12345L },
            { 12345L, 12345L, 12345L } };

    
    /**
     * @return Eine Kopie des Standard-Paket-Initialisierungvektors
     */
    public static final long[][] getPackageIV() {
    	long[][] result = new long[2][3];
    	System.arraycopy(packageIV[0], 0, result[0], 0, packageIV[0].length);
    	System.arraycopy(packageIV[1], 0, result[1], 0, packageIV[1].length);
		return result;
    }
    
    /**
     * Enthält den aktuellen Zustand des Generators 
     * x[0,0] = x_{1, n-1} 
     * x[0,1] = x_{1, n-2} 
     * x[0,2] = x_{1, n-3} 
     * x[1,0] = x_{2, n-1} 
     * x[1,1] = x_{2, n-2}
     * x[1,2] = x_{2, n-3}
     */
    private long[][] currentPosition = new long[2][3];

    /**
     * Enthält die Startposition des Zufallszahlenstroms
     */
    private long[][] initializationVector = new long[2][3];
    
    /**
     * Enthält die Startposition des Substreams innerhalb des Zufallszahlenstroms
     *  
     */
    private long[][] startOfSubStream;

	private BigInteger level1;

	private BigInteger level2;

	private BigInteger level3;

	/**
	 * Länge des Hauptstroms
	 */
	public static final BigInteger LAYER0_LENGTH = BigInteger.valueOf(2)
	.pow(32)
	.subtract(BigInteger.valueOf(209))
	.pow(3)
	.subtract(BigInteger.ONE)
	.multiply(
			BigInteger.valueOf(2)
			.pow(32)
			.subtract(BigInteger.valueOf(22853))
			.pow(3)
			.subtract(BigInteger.ONE)				
	
	).divide(BigInteger.valueOf(2));

	/**
	 * Länge eines Teilstroms der Ebene 1
	 */
	public static final BigInteger LAYER1_LENGTH = BigInteger.valueOf(2).pow(127);

	/**
	 * Länge eines Teilstroms der Ebene 2
	 */
	public static final BigInteger LAYER2_LENGTH = BigInteger.valueOf(2).pow(76);

	/**
	 * Länge eines Teilstroms der Ebene 3
	 */
	public static final BigInteger LAYER3_LENGTH = BigInteger.valueOf(2).pow(25);

    // Damit der Interpreter die Literale als Long erkennt,
    // muß am Ende ein L angegeben werden - auch bei Hexzahlen
    //private static final long[][] a = { { 0L, 1403580L, -810728L },
    //        { 527612L, 0L, -1370589L } };
    private static final long[][] a = { { -810728L, 1403580L, 0L },
        { -1370589L, 0L, 527612L } };

    /**
     * Modulo der beiden gekoppelten Generatoren
     */
    private static final long[] m = { 0x100000000L - 0xD1L, 0x100000000L - 0x5945L };

    private static final BigInteger[] mBig = {
            BigInteger.valueOf(m[0]),
            BigInteger.valueOf(m[1]) };

    private static final BigInteger Modulo1 = mBig[0];
    
    private static final BigInteger Modulo2 = mBig[1];
    
    
    private static final long A1p0[][] = { 
    	{ 0, 1, 0 }, 
    	{ 0, 0, 1 },  
        { -810728, 1403580, 0 } };

    private static final long A2p0[][] = { 
    	{ 0, 1, 0 }, 
    	{ 0, 0, 1 }, 
        { -1370589, 0, 527612 } };

    
    private static final long A1p127[][] = matTwoPowModM(A1p0, 127, Modulo1); 

    private static final long A2p127[][] = matTwoPowModM(A2p0, 127, Modulo2); 

    private static final long A1p51[][] = matTwoPowModM(A1p0, 51, Modulo1);

    private static final long A2p51[][] = matTwoPowModM(A2p0, 51, Modulo2);

    private static final long A1p76[][] = matTwoPowModM(A1p0, 76, Modulo1);

    private static final long A2p76[][] = matTwoPowModM(A2p0, 76, Modulo2);

    private static final long InvA1p0[][] = { // Inverse of A1p0
    { 184888585L, 0, 1945170933L }, { 1, 0, 0 }, { 0, 1, 0 } };

    private static final long InvA2p0[][] = { // Inverse of A2p0
    { 0, 360363334L, 4225571728L }, { 1, 0, 0 }, { 0, 1, 0 } };

    private static final long InvA1p127[][] = matTwoPowModM(InvA1p0, 127, Modulo1);

    private static final long InvA2p127[][] = matTwoPowModM(InvA2p0, 127, Modulo2);

    private static final long InvA1p51[][] = matTwoPowModM(InvA1p0, 51, Modulo1);

    private static final long InvA2p51[][] = matTwoPowModM(InvA2p0, 51, Modulo2);

    private static final long InvA1p76[][] = matTwoPowModM(InvA1p0, 76, Modulo1);

    private static final long InvA2p76[][] = matTwoPowModM(InvA2p0, 76, Modulo2);

	private static final long[][] one_3x3 = {{1,0,0},{0,1,0}, {0,0,1}};

    /**
     * Initialisiert einen Zufallszahlengenerator, dessen aktuelle Startposition
     * dem Initialisierungsvektor des Paketes entspricht. Der Startpunkt wird
     * dann um die angegebenen Stufen verschoben.
     * 
     * 
     * @param _level3
     *            The first level, p * A<sup>127</sup>
     * @param _level3
     *            The second level, s * A<sup>76</sup>
     * @param _level3
     *            The third level, t * A<sup>51</sup>
     * 
     */
    public MRG32k3a(BigInteger _level1, BigInteger _level2, BigInteger _level3) {
    	this(packageIV, _level1, _level2, _level3);
    }

    public MRG32k3a(long _level1, long _level2, long _level3) {
    	this(BigInteger.valueOf(_level1), BigInteger.valueOf(_level2), BigInteger.valueOf(_level3));
    }
    
    
    public MRG32k3a(long[][] _initializationVector, long _level1, long _level2, long _level3) {
    	this(_initializationVector, BigInteger.valueOf(_level1), BigInteger.valueOf(_level2), BigInteger.valueOf(_level3));
    }

    
    public MRG32k3a(long[][] _initializationVector, BigInteger _level1, BigInteger _level2, BigInteger _level3) {
    	System.arraycopy(_initializationVector[0], 0, initializationVector[0], 0, _initializationVector[0].length);
    	System.arraycopy(_initializationVector[1], 0, initializationVector[1], 0, _initializationVector[1].length);
        setSubstreamPosition(_level1, _level2, _level3);
        resetToSubstream();
    }
    /**
     * Copy-Constructor, der einen neuen Generator erstellt mit 
     * dem übergebenen Zustand.
     * 
     * @param _state
     */
    public MRG32k3a(State _state) {
    	super();
    	this.currentPosition =  cloneMatrix(_state.currentPosition);
    	this.initializationVector = cloneMatrix(_state.initializationVector);
    	this.startOfSubStream = cloneMatrix(_state.startOfSubStream);
    	this.level1 = _state.level1;
    	this.level2 = _state.level2;
    	this.level3 = _state.level3;
    }
    
    /**
     * Copy Constructor - der erstellte Zufallszahlengenerator befindet
     * sich in exakt dem selben Zustand wie {@code _other}.
     * 
     * @param _other
     */
    public MRG32k3a(MRG32k3a _other) {
    	this(_other.getState());
    }
    
    /**
     * Setzt die aktuelle Position auf den Anfang des 
     * mit gespeicherten Substreams zurück
     */
    public void resetToSubstream() {
    	for (int i = 0; i < 2; i++) {
            System.arraycopy(startOfSubStream[i], 0, currentPosition[i], 0,
                    startOfSubStream[i].length);
    	}
    }

    
    /**
     * Setzt sowohl die aktuelle Position, als auch den Beginn des
     * Substreams zurück auf den Initialisierungsvektor.
     */
    public void resetToInitializationVector() {
    	for (int i = 0; i < 2; i++) {
            System.arraycopy(initializationVector[i], 0, currentPosition[i], 0,
                    initializationVector[i].length);
            System.arraycopy(initializationVector[i], 0, startOfSubStream[i], 0,
                    initializationVector[i].length);
    	}
    }
    
    
    /**
     * Gibt eine Kopie vom aktuellen Zustand zurück. 
     * 
     * @return Ein Objekt vom Typ {@link MRG32k3a.State}
     */
    public State getState() {
    	return new State(this);
    }
    
    /* (non-Javadoc)
     * @see de.christianseipl.utilities.rng.UniformRandomGenerator#advancePosition(java.math.BigInteger)
     */
    @Override
    public void advancePosition(BigInteger _by) {
    	long[][] p0Pow0;
    	long[][] p1Pow0;
    	if (_by.compareTo(BigInteger.ZERO) >= 0) {
    		p0Pow0 = matPowModM(A1p0, _by, Modulo1);
    		p1Pow0 = matPowModM(A2p0, _by, Modulo2);
    		
    	} else {
            p0Pow0 = matPowModM(InvA1p0, _by.negate(), Modulo1);
            p1Pow0 = matPowModM(InvA2p0, _by.negate(), Modulo2);
    	}
    	currentPosition[0] = matVecModM(p0Pow0, currentPosition[0], Modulo1);
    	currentPosition[1] = matVecModM(p1Pow0, currentPosition[1], Modulo2);
    }

    public void advancePosition(long _by) {
    	advancePosition(BigInteger.valueOf(_by));
    }
    	/**
     * Berechnet {@code A * B mod m}
     * 
     * Die Methode arbeitet auch wenn A=B ist.
     * 
     * @param A
     * @param B
     * @param m
     * @return
     */
    public static long[][] matMatMultModM(long[][] A, long[][] B,
            BigInteger modul) {
        long[][] result = cloneMatrix(A);
        long[][] colB = transposeMatrix(B);
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                result[i][j] = vecVecMultModM(A[i], colB[j], modul);
            }
        }
        return result;
    }

    /**
     * Berechnet mittels eines rekursiven Aufrufs (A^{_exp}) mod modul
     * @param A
     * @param _exp
     * @param modul
     * @return
     */
    public static long[][] matPowModM(long[][] A, BigInteger _exp, BigInteger modul) {
        long[][] result = cloneMatrix(A);
        if (_exp.equals(BigInteger.ONE)) // hier stand vorher nocht c==0
            return result;
        if (_exp.equals(BigInteger.ZERO))
			return cloneMatrix(one_3x3);
        if (_exp.testBit(0) == false) {
            result = matPowModM(result, _exp.shiftRight(1), modul);
            result = matMatMultModM(result, result, modul);
        } else {
            result = matPowModM(result, _exp.subtract(BigInteger.ONE), modul);
            result = matMatMultModM(A, result, modul);
        }
        return result;
    }
    
   	/**
     * Führt einen Sprung in Strom {@code from} aus und gibt die Koordinaten der
     * neuen Position zurück. Die Sprungweite darf auch negativ sein, dies wird
     * durch Verwendung der Inversen berücksichtigt.
     * 
     * @param _level1
     *            first level
     * @param _level2
     *            second level
     * @author Christian Seipl
     * @return
     */
    private long[][] calcHierarchicalStep(BigInteger _level1, BigInteger _level2, BigInteger _level3, long[][] from) {
        long[][] result = new long[2][3];

        long[][] p0Pow127;
        long[][] p1Pow127;

        if (_level1.compareTo(BigInteger.ZERO) >= 0) {
            p0Pow127 = matPowModM(A1p127, _level1, Modulo1);
            p1Pow127 = matPowModM(A2p127, _level1, Modulo2);
        } else {
            p0Pow127 = matPowModM(InvA1p127, _level1.negate(), Modulo1);
            p1Pow127 = matPowModM(InvA2p127, _level1.negate(), Modulo2);
        }

        long[][] p0Pow76;
        long[][] p1Pow76;

        if (_level2.compareTo(BigInteger.ZERO) >= 0) {
            p0Pow76 = matPowModM(A1p76, _level2, Modulo1);
            p1Pow76 = matPowModM(A2p76, _level2, Modulo2);
        } else {
            p0Pow76 = matPowModM(InvA1p76, _level2.negate(), Modulo1);
            p1Pow76 = matPowModM(InvA2p76, _level2.negate(), Modulo2);
        }

        long[][] p0Pow51;
        long[][] p1Pow51;

        if (_level3.compareTo(BigInteger.ZERO) >= 0) {
            p0Pow51 = matPowModM(A1p51, _level3, Modulo1);
            p1Pow51 = matPowModM(A2p51, _level3, Modulo2);
        } else {
            p0Pow51 = matPowModM(InvA1p51, _level3.negate(), Modulo1);
            p1Pow51 = matPowModM(InvA2p51, _level3.negate(), Modulo2);
        }

        result[0] = matVecModM(p0Pow127, from[0], Modulo1);
        result[0] = matVecModM(p0Pow76, result[0], Modulo1);
        result[0] = matVecModM(p0Pow51, result[0], Modulo1);

        result[1] = matVecModM(p1Pow127, from[1], Modulo2);
        result[1] = matVecModM(p1Pow76, result[1], Modulo2);
        result[1] = matVecModM(p1Pow51, result[1], Modulo2);

        return result;
    }

    /**
     * Berechnet A^(2^exp) mod modul, indem 
     * A exp mal mit sich selber multipliziert wird
     * 
     * @param A
     * @param exponent
     * @param modul
     * @return
     */
    public static long[][] matTwoPowModM(long[][] A, long exponent,
            BigInteger modul) {
        long[][] result = cloneMatrix(A);
        for (int i = 0; i < exponent; i++) {
            result = matMatMultModM(result, result, modul);
        }
        return result;
    }


    /**
     * Führt eine Iteration des CombinedMRG aus. Die neuen x-Werte sind im Array
     * eingetragen als x[0][0] und x[1][0]
     */
    private void advanceMRNG() {
        for (int j = 0; j <= 1; j++) {
            long product = 0;
            for (int i = 0; i < 3; i++) {
                product += a[j][i] * currentPosition[j][i];
            }
            product %= m[j];
            if (product < 0) {
                product += m[j];
            }
            // Verschiebung in die andere Richtung
            System.arraycopy(currentPosition[j], 1, currentPosition[j], 0, 2);
            currentPosition[j][2] = product;
        }
    }

    
    
    /**
     * Erzeugt eine gleichverteilte Zufallszahl im Bereich 0 bis 1 mit einer
     * Auflösung von 1/(2^32-209)
     * 
     * @return
     */
    private double uniformRandom() {
        advanceMRNG();
        long z = (currentPosition[0][2] - currentPosition[1][2])
                % (0x100000000L - 209);
        if (z < 0) {
            z += (0x100000000L - 209);
        }
        return ((double) z / (0x100000000L - 208));
    }

    /**
     * Erzeugt eine exakte Kopie des zweidimensionalen Feldes,
     * indem alle Felder kopiert werden und die zweite Dimension
     * unabhängig von der ersten Dimension ist.
     * 
     * @param T
     * @return
     */
    private static long[][] cloneMatrix(long[][] T) {
    	long[][] C = new long[T.length][];
    	for (int i = 0; i < C.length; i++) {
    		C[i] = new long[T[i].length];
    		System.arraycopy(T[i], 0, C[i], 0, T[i].length);
    	}
    	return C;
    }

    
    private static boolean equalsMatrix(long[][] _lhs, long[][] _rhs) {
    	if (_lhs.length != _rhs.length) return false;
    	for (int i = 0; i < _lhs.length; i++) {
			if (_lhs.length != _rhs.length) return false;
			if (! Arrays.equals(_lhs[i], _rhs[i])) return false;
		}
    	return true;
    }
    
    /**
     * Berechnet (A * s) mod modul, indem jede Zeile der Matrix
     * mit dem Vector s mit {@link #vecVecMultModM(long[], long[], BigInteger)}
     * verarbeitet wird.
     * 
     * @param A
     * @param s
     * @param modul
     * @return
     */
    private static long[] matVecModM(long[][] A, long[] s, BigInteger modul) {
        long[] result = new long[3];
        for (int i = 0; i < 3; i++) {
            result[i] = vecVecMultModM(A[i], s, modul);
        }
        return result;
    }

    /**
     * Berchnet {@code (r * s) mod m}
     * 
     * @param modul
     * @param l
     * @return
     */
    private static BigInteger multModM(long r, long s, BigInteger modul) {
        return BigInteger.valueOf(r).multiply(BigInteger.valueOf(s)).mod(modul);
    }

    /**
     * Gibt die transponierte der Matrix {@code A} als neues Objekt zurück.
     * 
     * Es wird angenommen, daß die Matrix quadratisch ist.
     * 
     * @param A
     * @return
     */
    private static long[][] transposeMatrix(long[][] A) {
        long[][] result = cloneMatrix(A);
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                result[i][j] = A[j][i];
            }
        }
        return result;
    }

    /**
     * Berechnet {@code v * w mod m}.
     * 
     * Diese Methode arbeitet auch wenn v=w ist.
     * 
     * @param v
     * @param w
     * @return
     */
    private static long vecVecMultModM(long[] v, long[] w, BigInteger modul) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < v.length; i++) {
            result = multModM(v[i], w[i], modul).add(result).mod(modul);
        }
        return result.longValue();
    }

	/**
	 * @see de.christianseipl.utilities.rng.UniformRandomGenerator#setSubstreamPosition(java.math.BigInteger, java.math.BigInteger, java.math.BigInteger)
	 */
	@Override
	public void setSubstreamPosition(BigInteger _level1, BigInteger _level2, BigInteger _level3) {
		this.level1 = _level1;
		this.level2 = _level2;
		this.level3 = _level3;
		startOfSubStream = calcHierarchicalStep(_level1, _level2, _level3, initializationVector);
	}


	/**
	 * @see de.unihh.ilt.sim.layer0.dist.UniformRandomGenerator#nextDouble()
	 */
	public double nextDouble() {
		return uniformRandom();
	}


	/**
	 * Ruft {@link #advanceToStream(BigInteger, BigInteger, BigInteger)} mit den
	 * umgewandelten Parametern.
	 * 
	 * @param _level1
	 * @param _level2
	 * @param _level3
	 */
	public void advanceToStream(long _level1, long _level2, long _level3) {
		advanceToStream(BigInteger.valueOf(_level1), BigInteger.valueOf(_level2), BigInteger.valueOf(_level3));
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.rng.UniformRandomGenerator#advanceToStream(java.math.BigInteger, java.math.BigInteger, java.math.BigInteger)
	 */
	@Override
	public void advanceToStream(BigInteger _level1, BigInteger _level2, BigInteger _level3) {
		long[][] newPosition = calcHierarchicalStep(_level1, _level2, _level3, currentPosition);
       System.arraycopy(newPosition[0], 0, currentPosition[0], 0, newPosition[0].length);
       System.arraycopy(newPosition[1], 0, currentPosition[1], 0, newPosition[1].length);
	}

	/* (non-Javadoc)
	 * @see de.christianseipl.utilities.rng.UniformRandomGenerator#setSubstreamPosition(int, int, int)
	 */
	public void setSubstreamPosition(long _level1, long _level2, long _level3) {
		setSubstreamPosition(BigInteger.valueOf(_level1), BigInteger.valueOf(_level2), BigInteger.valueOf(_level3));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.christianseipl.utilities.rng.UniformRandomGenerator#getStreamLevel(int)
	 */
	@Override
	public BigInteger getStreamLevel(int _rank) {
		switch (_rank) {
		case 3:
			return level3;
		case 2:
			return level2;
		case 1:
			return level1;
		default:
			throw new IllegalArgumentException(String.format(
					"Rank must be between 1 an 3, but is %s", _rank));
		}
	}

}
