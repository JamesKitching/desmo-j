package desmoj.extensions.space2D.space;

//import desmoj.extensions.space2D.agent.SituatedAgent;

/**
 * An abstract element of discrete space. Each element maintains lists of the
 * objects and agents currently situated at it. Also, it maintains a list of its
 * attributes and the attribute areas it is part of.
 */
public interface DiscreteSpaceElement {

	// //////////// Attribute /////////////////////////////////////

	// //////////// Konstruktor ////////////////////////////////////

	// /** Default constructor. Initializes all internal lists.
	// */
	// public DiscreteSpaceElement() {
	// this.attributes = new AttributeList();
	// // aus Speicherplatzgr�nden nur sehr kleine Vektoren erzeugen
	// this.attributeAreas = new Vector(2,5);
	// this.agents = new Vector(0,2);
	// this.objects = new Vector(0,2);
	// }

	// //////////// Methoden ////////////////////////////////////////

	/**
	 * returns the list of agents as an array. The array is of length 0 if there
	 * are no agents at this discrete space element.
	 */
//	public abstract SituatedAgent[] getAgents();

	/**
	 * returns the list of objects as an array. The array is of length 0 if
	 * there are no objects at this discrete space element.
	 */
	public abstract Object[] getObjects();

	/**
	 * returns the list of "genuine" attribute areas as an array. The array is
	 * of length 0 if there are no areas containing this discrete space element.
	 */
	public abstract AttributeArea[] getAttributeAreas();

	/**
	 * returns the list of "local" attribute areas as an array. The array is of
	 * length 0 if there are no local areas containing this discrete space
	 * element.
	 */
	public abstract AttributeArea[] getLocalAreas();

	/**
	 * returns all attributes attached to this space element, i.e. the list of
	 * attributes plus the attributes of the attribute areas.
	 */
	public abstract AttributeList getAttributes();

	/**
	 * returns the attribute value for the given attribute name or
	 * <code>null</code> if there is no attribute with the given name attached
	 * to this space element.
	 */
	public abstract Object getAttributeValue(String attributeName);

	/**
	 * adds the agent to this space element's list of agents.
	 */
//	public abstract void addAgent(SituatedAgent agent);

	/**
	 * removes the agent from this space element's list of agents.
	 */
//	public abstract void removeAgent(SituatedAgent agent);

	/**
	 * adds the object to this space element's list of objects.
	 */
	public abstract void addObject(Object object);

	/**
	 * removes the object from this space element's list of objects.
	 */
	public abstract void removeObject(Object object);

	/**
	 * adds the attribute area to this space element's list of areas. If the
	 * area comprises a spatial group all currently at this space element
	 * residing agents automatically join this group.
	 */
	public abstract void addAttributeArea(AttributeArea area);

	/**
	 * removes the attribute area from this space element's list of areas. If
	 * the area comprises a spatial group all currently at this space element
	 * residing agents automatically leave this group.
	 */
	public abstract void removeAttributeArea(AttributeArea area);

	/**
	 * adds the attribute to this space element's list of attributes. If an
	 * attribute with the same name is already part of this list, the new
	 * attribute replaces the old one.
	 */
	public abstract void addAttribute(Attribute attribute);

	/**
	 * removes the attribute with the given name from this space element's list
	 * of attributes.
	 */
	public abstract void removeAttribute(String attributeName);

	/**
	 * sets the attribute of the specified name to the new value. If the list of
	 * this space element's attributes has so far not contained such an
	 * attribute, a new attribute with name/value will be added.
	 */
	public abstract void setAttribute(String name, Object value);

	public abstract Point getRefPoint();
}