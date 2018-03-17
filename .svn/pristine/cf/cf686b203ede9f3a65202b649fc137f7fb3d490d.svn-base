package desmoj.extensions.space2D.space;

/**
 * An abstract graph element. This class is the superclass of Node and Edge and
 * provides its subclasses with the functionality common to both -- in this
 * case, having an id.
 */
abstract class GraphElement {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the id of this graph element */
	protected int id;

	/**
	 * the list of attributes for this graph element.
	 */
	protected AttributeList attributes;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/** constructs a graph element with the given id and attributes. */

	GraphElement(int id, AttributeList attributes) {
		this.id = id;
		this.attributes = new AttributeList();
		if (attributes != null) {
			this.attributes.putAttributes(attributes.getAttributes());
		}
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/** returns the id of this graph element. */
	public int getID() {
		return this.id;
	}

	/**
	 * returns all attributes attached to this graph element, i.e. the list of
	 * attributes plus the attributes of the attributes areas.
	 */
	public abstract AttributeList getAttributes();

	/**
	 * returns the attribute value for the given attribute name or
	 * <code>null</code> if there is no attribute with the given name attached
	 * to this space element.
	 */
	public Object getAttributeValue(String attributeName) {
		return this.attributes.getAttributeValue(attributeName);
	}

	/**
	 * adds the attribute to this space element's list of attributes. If an
	 * attribute with the same name is already part of this list, the new
	 * attribute replaces the old one.
	 */
	public void addAttribute(Attribute attribute) {
		this.attributes.putAttribute(attribute);
	}

	/**
	 * removes the attribute with the given name from this space element's list
	 * of attributes.
	 */
	public void removeAttribute(String attributeName) {
		this.attributes.removeAttribute(attributeName);
	}

	/**
	 * sets the attribute of the specified name to the new value. If the list of
	 * this space element's attributes has so far not contained such an
	 * attribute, a new attribute with name/value will be added.
	 */
	public void setAttribute(String name, Object value) {
		Attribute attr;
		if (value instanceof Comparable) {
			attr = new ComparableAttribute(name, (Comparable) value);
		} else {
			attr = new Attribute(name, value);
		}
		this.attributes.putAttribute(attr);
	}

} /* end of class */
