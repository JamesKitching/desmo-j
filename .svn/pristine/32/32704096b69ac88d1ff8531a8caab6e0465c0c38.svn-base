package desmoj.extensions.space2D.xml;

import desmoj.extensions.space2D.space.Attribute;
import desmoj.extensions.space2D.space.AttributeList;

/**
 * the description of a space element with an id and a list of attributes. Super
 * class of node description and edge description. Also used as super class for
 * attribute area description, although attribute areas don't have an id.
 */
public abstract class SpaceElementDescription {

	/** the id */
	protected int id;

	/** the list of attribute descriptions */
	protected AttributeDescription[] attributes;

	/**
	 * for convenience: the list of "real" attributes (converted from/to
	 * attribute descriptions)
	 */
	protected AttributeList attrList;

	// ----- the java-bean conform interface: standard constructor and get/set
	// ----- methods

	/** Default constructor */
	public SpaceElementDescription() {
		this.id = -1;
		this.attributes = new AttributeDescription[0];
		this.attrList = null;
	}

	/** returns the id */
	public int getID() {
		return this.id;
	}

	/** sets the id */
	public void setID(int id) {
		this.id = id;
	}

	/** returns the list of attribute descriptions */
	public AttributeDescription[] getAttributes() {
		return this.attributes;
	}

	/** sets the list of attribute descriptions */
	public void setAttributes(AttributeDescription[] attr) {
		this.attributes = attr;
		this.attrList = new AttributeList();
		for (int i = 0; i < attr.length; i++) {
			this.attrList.putAttribute(attr[i].getAttribute());
		}
	}

	// ------- additional methods

	/**
	 * Returns the attribute list.
	 */
	public AttributeList getAttributeList() {
		return this.attrList;
	}

	/**
	 * sets all fields in this space element description to null / zero values.
	 * And destroys recursively every attribute description.
	 */
	public void destroy() {
		// id wegwerfen
		this.id = 0;
		// AttributeList wegwerfen
		this.attrList = null;
		// AttributeDescription zerst�ren und wegwerfen
		for (int i = 0; i < this.attributes.length; i++) {
			attributes[i].destroy();
			attributes[i] = null;
		}
		this.attributes = null;
	}

	// ------- additional constructor for marshalling

	/**
	 * Constructs a new space element description using the given attribute
	 * list. It constructs a new attribute description for every attribute in
	 * attrList. This constructor is provided for the corresponding constructors
	 * in the sub classes without ids.
	 */
	protected SpaceElementDescription(AttributeList attrList) {
		this.attrList = attrList;
		Attribute[] attr = attrList.getAttributes();
		this.attributes = new AttributeDescription[attr.length];
		for (int i = 0; i < attr.length; i++) {
			this.attributes[i] = new AttributeDescription(attr[i]);
		}
	}

	/**
	 * Constructs a new space element description using the given attribute list
	 * and id. This constructor is provided for the corresponding constructors
	 * in the sub classes with ids.
	 */
	protected SpaceElementDescription(int id, AttributeList attrList) {
		this(attrList);
		this.id = id;
	}
}