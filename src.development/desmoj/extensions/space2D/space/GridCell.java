package desmoj.extensions.space2D.space;

import java.util.HashMap;
import java.util.Map;

import desmoj.extensions.experimentation.reflect.FieldAccessPoint;
//import desmoj.extensions.space2D.agent.SituatedAgent;
import desmoj.extensions.space2D.gui.Inspectable;

/** An abstract cell in an n-dimensional grid. */
public abstract class GridCell implements DiscreteSpaceElement, Inspectable {
	/** this cell's index into the containing grid */
	protected int[] index;

	/** the points defining this cell's shape */
	protected Point[] vertices;

	/** the dual graph node of this cell */
	protected Node dual;

	/**
	 * constructs a new grid cell with the given shape. The dual node must be
	 * set later.
	 * 
	 * @param vertices
	 *            the list of points defining the cell's shape.
	 */
	public GridCell(int[] index, Point[] vertices) {
		this(index, vertices, null);
	}

	/**
	 * constructs a new grid cell with the given shape and the given dual node.
	 * 
	 * @param vertices
	 *            the list of points defining the cell's shape.
	 * @param node
	 *            the dual graph node.
	 */
	public GridCell(int[] index, Point[] vertices, Node dual) {
		// Kopie des Arrays anlegen, damit Ÿnderungen auþerhalb nichts ausmachen
		this.index = new int[index.length];
		for (int i = 0; i < index.length; i++) {
			this.index[i] = index[i];
		}
		// ebenfalls Kopie anlegen
		this.vertices = new Point[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			this.vertices[i] = vertices[i];
		}
		this.dual = dual;
	}

	/** returns the dual graph node. */
	public Node getDualNode() {
		return this.dual;
	}

	/** sets the dual graph node to the given node. */
	public void setDualNode(Node dual) {
		this.dual = dual;
	}

	/** returns the shape-defining vertices. */
	public Point[] getVertices() {
		return this.vertices;
	}

	/** returns this cell's index defining its position in the grid. */
	public int[] getIndex() {
		return this.index;
	}

	// -------------------------------------------------------- abstrakte
	// Methoden

	public abstract boolean contains(Point p);

	// --------------------------------------------------------
	// Interface-Methoden

	// -------------------------------------------------- (1)
	// DiscreteSpaceElement

	/**
	 * returns the list of agents as an array. The array is of length 0 if there
	 * are no agents at this grid cell.
	 */
//	public SituatedAgent[] getAgents() {
//		return dual.getAgents();
//	}

	/**
	 * returns the list of objects as an array. The array is of length 0 if
	 * there are no objects at this grid cell.
	 */
	public Object[] getObjects() {
		return dual.getObjects();
	}

	/**
	 * returns the list of "genuine" attribute areas as an array. The array is
	 * of length 0 if there are no areas containing this grid cell.
	 */
	public AttributeArea[] getAttributeAreas() {
		return dual.getAttributeAreas();
	}

	/**
	 * returns the list of "local" attribute areas as an array. The array is of
	 * length 0 if there are no local areas containing this grid cell.
	 */
	public AttributeArea[] getLocalAreas() {
		return dual.getLocalAreas();
	}

	/**
	 * returns all attributes attached to this grid cell, i.e. the list of
	 * attributes plus the attributes of the attributes areas.
	 * 
	 * NOTE: This implementation makes a copy of the internal attribute list so
	 * that the attributes of the areas can be added without changing the
	 * internal attribute list.
	 */
	public AttributeList getAttributes() {
		return dual.getAttributes();
	}

	/**
	 * returns the attribute value for the given attribute name or
	 * <code>null</code> if there is no attribute with the given name attached
	 * to this grid cell.
	 */
	public Object getAttributeValue(String attributeName) {
		return dual.getAttributeValue(attributeName);
	}

	/**
	 * adds the agent to this grid cell's list of agents.
	 */
//	public void addAgent(SituatedAgent agent) {
//		dual.addAgent(agent);
//	}

	/**
	 * removes the agent from this grid cell's list of agents.
	 */
//	public void removeAgent(SituatedAgent agent) {
//		dual.removeAgent(agent);
//	}

	/**
	 * adds the object to this grid cell's list of objects.
	 */
	public void addObject(Object object) {
		dual.addObject(object);
	}

	/**
	 * removes the object from this grid cell's list of objects.
	 */
	public void removeObject(Object object) {
		dual.removeObject(object);
	}

	/**
	 * adds the attribute area to this grid cell's list of areas. If the area
	 * comprises a spatial group all currently at this space element residing
	 * agents automatically join this group.
	 */
	public void addAttributeArea(AttributeArea area) {
		dual.addAttributeArea(area);
	}

	/**
	 * removes the attribute area from this grid cell's list of areas. If the
	 * area comprises a spatial group all currently at this space element
	 * residing agents automatically leave this group.
	 */
	public void removeAttributeArea(AttributeArea area) {
		dual.removeAttributeArea(area);
	}

	/**
	 * adds the attribute to this grid cell's list of attributes. If an
	 * attribute with the same name is already part of this list, the new
	 * attribute replaces the old one.
	 */
	public void addAttribute(Attribute attribute) {
		dual.addAttribute(attribute);
	}

	/**
	 * removes the attribute with the given name from this grid cell's list of
	 * attributes.
	 */
	public void removeAttribute(String attributeName) {
		dual.removeAttribute(attributeName);
	}

	/**
	 * sets the attribute of the specified name to the new value. If the list of
	 * this grid cell's attributes has so far not contained such an attribute, a
	 * new attribute with name/value will be added.
	 */
	public void setAttribute(String name, Object value) {
		dual.setAttribute(name, value);
	}

	/**
	 * returns a reference point. In this case: returns the reference point of
	 * this cell's dual node.
	 */
	public Point getRefPoint() {
		return dual.getRefPoint();
	}

	// ----------------------------------------------------------- (2)
	// Inspectable

	/**
	 * returns the name of this inspectable object.
	 */
	public String getName() {
		StringBuffer name = new StringBuffer("[");
		for (int i = 0; i < this.index.length; i++) {
			name.append(this.index[i]);
			name.append(",");
		}
		name.setCharAt(name.length() - 1, ']');
		return name.toString();
	}

	/**
	 * returns the type of this inspectable object. This will probably be the
	 * class name (without packages).
	 */
	public String getType() {
		return "GridCell";
	}

	/**
	 * returns the inspectable attributes as a probe map. The attributes need
	 * not be declared directly in the class of the inspectable object. Instead,
	 * they may be inherited from superclasses or be declared in used classes.
	 */
	public Map getAccessPoints() {
		Map map = new HashMap();
		map.put("agents", new FieldAccessPoint("agents", dual));
		map.put("objects", new FieldAccessPoint("objects", dual));
		map.put("attributes", new FieldAccessPoint("attributes", dual));
		map.put("attributeAreas", new FieldAccessPoint("attributeAreas", dual));
		return map;
	}

}