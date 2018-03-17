package desmoj.extensions.space2D.space;

import java.util.Vector;

//import desmoj.extensions.space2D.agent.Group;

/**
 * An attribute area is an area of a certain space with an associated list of
 * attributes. The area is defined by specifying a list of positions. An
 * attribute area may function as a spatial group: every agent entering into the
 * area will become a group member. Leaving the area means leaving the group
 * also.
 * 
 * @author Ruth Meyer
 */
public class AttributeArea {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the list of attributes associated with this area. */
	protected AttributeList attributes;

	/** the list of positions defining the spatial extent of this area. */
	protected Vector positions;

	/** the spatial group associated with this area (if any) */
//	protected Group spatialGroup;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new attribute area with the given list of attributes. This
	 * area does not have any positions so far.
	 * 
	 * @param attributes
	 *            the attributes adhering to this area
	 */
	public AttributeArea(AttributeList attributes) {
		constructArea(attributes, null);
	}

	/**
	 * constructs a new attribute area with the given lists of attributes and
	 * positions.
	 * 
	 * @param attributes
	 *            the attributes adhering to this area
	 * @param positions
	 *            the positions defining this area
	 */
	public AttributeArea(AttributeList attributes, Position[] positions) {
		constructArea(attributes, positions);
	}

	/**
	 * constructs a new attribute area with the given list of attributes. The
	 * positions defining the area are converted from the given point list by
	 * the given space. This constructor is provided for convenience when
	 * importing data from an XML file.
	 * 
	 * @param attributes
	 *            the attributes adhering to this area
	 * @param points
	 *            the points specifying the area-defining positions
	 * @param space
	 *            the space this area belongs to
	 */
	public AttributeArea(AttributeList attributes, Point[] points, Space space) {
		// vom space die points in positions wandeln lassen
		Position[] positions = new Position[points.length];
		for (int i = 0; i < points.length; i++) {
			positions[i] = space.getPosition(points[i]);
		}
		constructArea(attributes, positions);
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns (a copy of) this area's attribute list.
	 * 
	 * @return a new attribute list which is a copy of this area's attribute
	 *         list
	 */
	public AttributeList getAttributes() {
		return new AttributeList(this.attributes.getAttributes());
	}

	/**
	 * returns the attribute value for the attribute specified by the given
	 * name. If there is no such attribute in this area's attribute list,
	 * returns <code>null</code>.
	 * 
	 * @param attributeName
	 *            the name of the attribute whose value is to be retrieved
	 * @return the value of the specified attribute
	 */
	public Object getAttributeValue(String attributeName) {
		return this.attributes.getAttributeValue(attributeName);
	}

	/**
	 * replaces this area's attribute list with the given list. In fact, a copy
	 * of the given list is constructed in order to ensure encapsulation of this
	 * area's list of attributes.
	 * 
	 * @param list
	 *            the new attribute list for this area
	 */
	public void setAttributes(AttributeList list) {
		this.attributes = new AttributeList(list);
	}

	/**
	 * returns the positions defining this attribute area as an array.
	 * 
	 * @return an array of Position objects defining this area
	 */
	public Position[] getPositions() {
		Position[] temp = new Position[this.positions.size()];
		this.positions.copyInto(temp);
		return temp;
	}

	/**
	 * returns this area's spatial group or <code>null</code> if this area is
	 * not associated with a group.
	 * 
	 * @return a group or <code>null</code>
	 */
//	public Group getGroup() {
//		return this.spatialGroup;
//	}

	/**
	 * returns <code>true</code> if this attribute area forms a spatial group
	 * 
	 * @return <code>true</code> if this area is a spatial group;
	 *         <code>false</code> otherwise
	 */
//	public boolean isSpatialGroup() {
//		return this.spatialGroup != null;
//	}

	/**
	 * sets this area's spatial group to the given group.
	 * 
	 * @param spatialGroup
	 *            the group associated to this attribute area.
	 */
//	public void setGroup(Group spatialGroup) {
//		this.spatialGroup = spatialGroup;
//	}

	/**
	 * adds the given position to this attribute area.
	 * 
	 * @param pos
	 *            the position to be added
	 */
	public void addPosition(Position pos) {
		if (!this.positions.contains(pos)) {
			this.positions.add(pos);
			// area als AttributeArea zur Position hinzuf�gen
			pos.addAttributeArea(this);
		}
	}

	/***************************************************************************
	 * removes the given position from this attribute area.
	 * 
	 * @param pos
	 *            the position to be removed
	 */
	public void removePosition(Position pos) {
		if (this.positions.contains(pos)) {
			this.positions.remove(pos);
			// area als AttributeArea in der Position l�schen
			pos.removeAttributeArea(this);
		}
	}

	/**
	 * returns <code>true</code> if the given position is part of this area.
	 * 
	 * @param pos
	 *            the position to be testet
	 * @return <code>true</code> if this area contains the given position;
	 *         <code>false</code> otherwise
	 */
	public boolean contains(Position pos) {
		return this.positions.contains(pos);
	}

	/**
	 * returns a string represenation of this attribute area.
	 * 
	 * @return a String consisting of the words "AttributeArea with "
	 *         concatenated to the string representation of the attribute list.
	 */
	public String toString() {
		return new String("AttributeArea with " + this.attributes.toString());
	}

	// ---------------------------------------------------------------------------

	/**
	 * helper method for constructing the new attribute area. The given
	 * attribute list is stored as the internal attribute list of this area. The
	 * position array is converted into the internal position list (vector).
	 * Each position gets this area added to its attribute areas.
	 * 
	 * @param attributes
	 *            the attributes adhering to this area
	 * @param positions
	 *            the positions defining this area
	 */
	private void constructArea(AttributeList attributes, Position[] positions) {
		this.attributes = attributes;
		this.positions = new Vector();
//		this.spatialGroup = null;
		if (positions != null) {
			for (int i = 0; i < positions.length; i++) {
				this.positions.add(positions[i]);
				// diese AttributeArea zur Position hinzuf�gen
				positions[i].addAttributeArea(this);
			}
		}
	}
}