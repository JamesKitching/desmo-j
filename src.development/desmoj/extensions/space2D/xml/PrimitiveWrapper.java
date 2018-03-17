package desmoj.extensions.space2D.xml;

/**
 * A wrapper class for xml primitive types (String plus the java primitive
 * types). Consists of a (wrapper) object for the primitive value, and - if it
 * is of a java primitive type - the primitive value itself. Keeps the type
 * information (as the java class name). Only one primitive value can be stored
 * in a primitive wrapper at a time.
 */
public class PrimitiveWrapper {

	/**
	 * the wrapper object for the primitive value (or the primitive value itself
	 * if it is of type String
	 */
	Object obj = null;

	/** the primitive value if it is of type boolean */
	boolean bValue;

	/**
	 * the primitive value if it is of type integer (byte, short, int, long in
	 * Java)
	 */
	long iValue;

	/** the primitive value if it is of type double (float, double in Java) */
	double dValue;

	/** the primitive value if it is of type char */
	char cValue;

	/** the type of the primitive value */
	String type = null;

	// ----- the java-bean conform interface: standard constructor and get/set
	// ----- methods

	/** Default constructor */
	public PrimitiveWrapper() {
	}

	/** returns the boolean primitive value */
	public boolean getBoolean() {
		return bValue;
	}

	/** sets the boolean primitive value */
	public void setBoolean(boolean value) {
		this.bValue = value;
		setObject(new Boolean(value));
	}

	/** returns the double primitive value */
	public double getDouble() {
		return dValue;
	}

	/** sets the double primitive value */
	public void setDouble(double value) {
		this.dValue = value;
		setObject(new Double(value));
	}

	/** returns the float primitive value */
	public float getFloat() {
		return (float) dValue;
	}

	/** sets the float primitive value */
	public void setFloat(float value) {
		this.dValue = value;
		setObject(new Float(value));
	}

	/** returns the long primitive value */
	public long getLong() {
		return iValue;
	}

	/** sets the long primitive value */
	public void setLong(long value) {
		this.iValue = value;
		setObject(new Long(value));
	}

	/** returns the int primitive value */
	public int getInteger() {
		return (int) iValue;
	}

	/** sets the int primitive value */
	public void setInteger(int value) {
		this.iValue = value;
		setObject(new Integer(value));
	}

	/** returns the short primitive value */
	public short getShort() {
		return (short) iValue;
	}

	/** sets the short primitive value */
	public void setShort(short value) {
		this.iValue = value;
		setObject(new Short(value));
	}

	/** returns the byte primitive value */
	public byte getByte() {
		return (byte) iValue;
	}

	/** sets the byte primitive value */
	public void setByte(byte value) {
		this.iValue = value;
		setObject(new Byte(value));
	}

	/** returns the char primitive value */
	public char getCharacter() {
		return this.cValue;
	}

	/** sets the char primitive value */
	public void setCharacter(char value) {
		this.cValue = value;
		setObject(new Character(value));
	}

	/** returns the String primitive value */
	public String getString() {
		return (String) this.obj;
	}

	/** sets the String primitive value */
	public void setString(String value) {
		setObject(value);
	}

	/** returns the type of the primitive value */
	public String getType() {
		return this.type;
	}

	/** sets the type of the primitive value */
	public void setType(String type) {
		this.type = type;
	}

	// ------ additional methods

	/**
	 * returns the primitive value as an object. If the value is a String, the
	 * String is returned. If it is a java primitive type an instance of the
	 * respective wrapper class is returned, e.g. a Boolean for type boolean.
	 */
	public Object getObject() {
		return this.obj;
	}

	/**
	 * Returns a String representation for this primitive wrapper.
	 */
	public String toString() {
		return this.obj.toString();
	}

	/**
	 * Returns <code>true</code> if the given object is an instance of one of
	 * the java wrapper classes for primitive types or if the given object is an
	 * instance of String. Otherwise, returns <code>false</code>.
	 */
	public static boolean isPrimitive(Object o) {
		if (o instanceof String || o instanceof Character
				|| o instanceof Boolean || o instanceof Byte
				|| o instanceof Short || o instanceof Integer
				|| o instanceof Long || o instanceof Float
				|| o instanceof Double)
			return true;
		return false;
	}

	/**
	 * sets the (wrapper) object representing the primitive value and stores the
	 * type of the object. This method is only called internally (?).
	 */
	protected void setObject(Object obj) {
		this.obj = obj;
		this.type = obj.getClass().getName();
	}

	// ----- additional constructor for marshalling

	/**
	 * Constructs a new primitive wrapper for the given primitiveObject. As only
	 * xml primitive values are allowed inside primitive wrappers objects of a
	 * different type will be converted to String.
	 */
	public PrimitiveWrapper(Object primitiveObject) {
		// only really primitive objects are allowed
		// all others will be converted to String
		if (primitiveObject instanceof Character) {
			setCharacter(((Character) primitiveObject).charValue());
		} else if (primitiveObject instanceof Boolean) {
			setBoolean(((Boolean) primitiveObject).booleanValue());
		} else if (primitiveObject instanceof Byte) {
			setByte(((Byte) primitiveObject).byteValue());
		} else if (primitiveObject instanceof Short) {
			setShort(((Short) primitiveObject).shortValue());
		} else if (primitiveObject instanceof Integer) {
			setInteger(((Integer) primitiveObject).intValue());
		} else if (primitiveObject instanceof Long) {
			setLong(((Long) primitiveObject).longValue());
		} else if (primitiveObject instanceof Float) {
			setFloat(((Float) primitiveObject).floatValue());
		} else if (primitiveObject instanceof Double) {
			setDouble(((Double) primitiveObject).doubleValue());
		} else {
			setString(primitiveObject.toString());
		}
	}
}