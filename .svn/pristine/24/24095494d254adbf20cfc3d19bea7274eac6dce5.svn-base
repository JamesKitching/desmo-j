package desmoj.extensions.space2D.space;

/**
 * An Attribute is a pair of name and value. The name defines the attribute
 * (case is ignored). Values may be of any scale level: nominal, ordinal,
 * interval or ratio.
 * 
 * @author Ruth Meyer
 */
public class Attribute {
	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the name of this attribute. */
	private String name;

	/** the value of this attribute. */
	private Object value;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * Constructs a new attribute from the given name and value.
	 * 
	 * @param name
	 *            the name of this attribute
	 * @param value
	 *            the value of this attribute
	 */
	public Attribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * empty constructor (for castor)
	 * 
	 */
	public Attribute() {
		this.name = "";
		this.value = null;
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * sets the attribute value to <code>value</code>. The old value is
	 * overwritten.
	 * 
	 * @param value
	 *            the new value for this attribute
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * returns the current attribute value.
	 * 
	 * @return the current attribute value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * returns the name of this attribute.
	 * 
	 * @return the attribute name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * sets the name of this attribute to <code>name</code>. The old name is
	 * overwritten.
	 * 
	 * @param name
	 *            the new name for this attribute
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * returns <code>true</code> if <code>that</code> is an attribute and
	 * has the same name as this attribute. Note that the values will NOT be
	 * compared.
	 * 
	 * @param that
	 *            the object (attribute) to be compared to this attribute
	 */
	public boolean equals(Object that) {
		if (!(that instanceof Attribute))
			return false;
		Attribute other = (Attribute) that;
		if (!(other.getName().equalsIgnoreCase(this.name)))
			return false;
		return true;
	}
	
// SKR	
// Extended on 22.07.2010 
// Reason: 		Created a further equals method to check name and value of the attribute. 
// Problems:	Return value for method get Value returns an Object. Usual problem may occur.
	/**
	 * returns <code>true</code> if <code>that</code> is an attribute and
	 * has the same name as this attribute. Note that the values are
	 * compared as well.
	 * 
	 * @param that
	 *            the object (attribute) to be compared to this attribute
	 */	
	public boolean hardEquals(Object that) {
		if (!(that instanceof Attribute))
			return false;
		Attribute other = (Attribute) that;
		if (!(other.getName().equalsIgnoreCase(this.name)) 
				&& other.getValue().equals(this.getValue()))
			return false;
		return true;
	}
// SKR
	
	/**
	 * returns a string representation of this attribute.
	 */
	public String toString() {
		return new String(getName() + "{" + getValue() + "}");
	}
}
