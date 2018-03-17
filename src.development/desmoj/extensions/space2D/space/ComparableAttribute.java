package desmoj.extensions.space2D.space;

/**
 * A comparable attribute is an attribute with (at least) a natural order
 * defined for its value domain. So a comparable attribute has ordinal, interval
 * or ratio level values. Any object used as such a value must implement the
 * interface <code>java.lang.Comparable</code>.
 */
public class ComparableAttribute extends Attribute implements Comparable {
	// ///////////// ATTRIBUTE ///////////////////////////////////////

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * Constructs a new attribute from the given name and value.
	 * 
	 * @param name
	 *            the name of this attribute
	 * @param value
	 *            the value of this attribute
	 */
	public ComparableAttribute(String name, Comparable value) {
		super(name, value);
	}

	/**
	 * Empty constructor (for castor)
	 */
	public ComparableAttribute() {
		super();
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * sets the attribute value to <code>value</code>. The old value is
	 * overwritten. If <code>value</code> is NOT comparable the old value
	 * remains unchanged.
	 * 
	 * @param value
	 *            the new value for this attribute
	 */
	public void setValue(Object value) {
		if (!(value instanceof Comparable))
			return;
		super.setValue(value);
	}

	/**
	 * compares this attribute to <code>that</code>. ComparableAttributes are
	 * comparable only if (a) their names are equal (ignoring case) and (b)
	 * their values are comparable.
	 * 
	 * @param that
	 *            the attribute to be compared with this attribute
	 * @return <0 if this attribute's value is lower than <code>that</code>'s
	 *         value 0 if both values are equal >0 if this attribute's value is
	 *         greater than <code>that</code>'s value (or if
	 *         <code>that == null</code>)
	 * @throws ClassCastException
	 *             if <code>that</code> is not a comparable attribute or if
	 *             <code>that</code>'s value is not comparable to this
	 *             attribute's value
	 */
	public int compareTo(Object that) {
		if (that == null) {
			return 1; // null ist immer kleiner als ein beliebiges Attribut
		}
		ComparableAttribute other = (ComparableAttribute) that; // throws
		// ClassCastException
		// if that's not
		// an attribute
		if (getName().equalsIgnoreCase(other.getName())) {
			// same attribute --> compare values
			return ((Comparable) getValue()).compareTo(other.getValue()); // throws
			// ClassCastException
			// if
			// that's
			// value
			// not
			// comparable
			// to
			// this.value
		}
		throw new ClassCastException("Not the same comparable attribute");
	}

}
