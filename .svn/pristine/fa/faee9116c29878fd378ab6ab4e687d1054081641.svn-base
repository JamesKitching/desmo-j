package desmoj.extensions.space2D.space;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import desmoj.core.simulator.Entity;
import desmoj.extensions.experimentation.reflect.FieldAccessPoint;
//import desmoj.extensions.space2D.agent.Group;
//import desmoj.extensions.space2D.agent.SituatedAgent;
import desmoj.extensions.space2D.gui.Inspectable;

/**
 * A node in a directed graph. Each node is defined by its id and its reference
 * point by which it is located in space.
 * 
 */
public class Node extends GraphElement implements DiscreteSpaceElement,
		Inspectable {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	private GridCell dualCell;

	protected Vector leavingEdges;

	protected Vector incomingEdges;

	protected Point refPoint;

	/**
	 * the list of attribute areas this space element belongs to. This list is
	 * organized as follows: "real" attribute areas are stored in the front
	 * part, the special local areas are stored in the back part.
	 */
	protected Vector attributeAreas; // vorn die "echten", hinten die

	// "locals"

	/**
	 * index of the first local area in the list of attribute areas
	 */
	private int firstLocal = 0;

	/**
	 * the list of objects currently residing at this space element.
	 */
	protected Vector objects;

	/**
	 * the list of agents currently residing at this space element.
	 */
//	protected Vector agents;
	protected Vector entitys;

	// protected AttributeArea locality;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	public Node(int id, Point refPoint) {
		this(id, refPoint, null);
	}

	public Node(int id, Point refPoint, AttributeList attributes) {
		super(id, attributes);
		this.refPoint = refPoint;
		// aus Speicherplatzgr�nden nur sehr kleine Vektoren erzeugen
		this.leavingEdges = new Vector(2, 2);
		this.incomingEdges = new Vector(2, 2);
		this.attributeAreas = new Vector(2, 5);
//		this.agents = new Vector(0, 2);
		this.entitys = new Vector(0, 2);
		this.objects = new Vector(0, 2);
		// this.locality = null;
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/** returns the reference point of this node. */
	public Point getRefPoint() {
		return this.refPoint;
	}

	/** returns (a copy of) this node's list of leaving edges. */
	public Edge[] getLeavingEdges() {
		Edge[] temp = new Edge[this.leavingEdges.size()];
		this.leavingEdges.copyInto(temp);
		return temp;
	}

	/** returns (a copy of) this node's list of incoming edges. */
	public Edge[] getIncomingEdges() {
		Edge[] temp = new Edge[this.incomingEdges.size()];
		this.incomingEdges.copyInto(temp);
		return temp;
	}

	/** returns a new list of all edges starting from or ending at this node. */
	public Edge[] getEdges() {
		Edge[] temp = new Edge[this.leavingEdges.size()
				+ this.incomingEdges.size()];
		this.leavingEdges.copyInto(temp);
		int offset = leavingEdges.size();
		for (int i = offset; i < temp.length; i++) {
			temp[i] = (Edge) this.incomingEdges.elementAt(i - offset);
		}
		return temp;
	}

	// public Node[] getLocality() {
	// if (locality != null) {
	// Position[] localPos = locality.getPositions();
	// Node[] nodes = new Node[localPos.length];
	// for (int i = 0; i < localPos.length; i++) {
	// nodes[i] = ((GraphPosition)localPos[i]).getNode();
	// }
	// return nodes;
	// }
	// return (new Node[] { this });
	// }
	//
	// public void setLocality(AttributeArea locality) {
	// // �berpr�fen, ob dieser Knoten denn auch in locality vorkommt
	// Position[] localPos = locality.getPositions();
	// boolean found = false;
	// int i=0;
	// while (!found && i < localPos.length) {
	// found = this == ((GraphPosition)localPos[i++]).getNode();
	// }
	// if (found) {
	// this.locality = locality;
	// }
	// }

	/**
	 * adds the given edge to the list of leaving edges if the edge's start node
	 * is identical to this node.
	 */
	public void addLeavingEdge(Edge edge) {
		if (!this.leavingEdges.contains(edge) && (edge.getStartNode() == this)) {
			this.leavingEdges.add(edge);
		}
	}

	/** removes the given edge from the list of leaving edges. */
	public void removeLeavingEdge(Edge edge) {
		this.leavingEdges.remove(edge);
	}

	/**
	 * adds the given edge to the list of incoming edges if the edge's end node
	 * is identical to this node.
	 */
	public void addIncomingEdge(Edge edge) {
		if (!this.incomingEdges.contains(edge) && (edge.getEndNode() == this)) {
			this.incomingEdges.add(edge);
		}
	}

	/** removes the given edge from the list of incoming edges. */
	public void removeIncomingEdge(Edge edge) {
		this.incomingEdges.remove(edge);
	}

	/**
	 * adds the attribute area to this space element's list of areas. If the
	 * area comprises a spatial group all currently at this space element
	 * residing agents automatically join this group.
	 */
	public void addAttributeArea(AttributeArea area) {
		if (!this.attributeAreas.contains(area)) {
			if (area instanceof LocalArea) {
				this.attributeAreas.add(area);
			} else {
				this.attributeAreas.add(0, area);
				this.firstLocal++;
			}
//			if (area.isSpatialGroup()) {
//				// alle Agenten in die r�umliche Gruppe aufnehmen
//				for (int i = this.entitys.size() - 1; i >= 0; i--) {
//					Group g = area.getGroup();
//					g.join((Entity) this.entitys.elementAt(i));
//				}
//			}
		}
	}

	/**
	 * removes the attribute area from this space element's list of areas. If
	 * the area comprises a spatial group all currently at this space element
	 * residing agents automatically leave this group.
	 */
	public void removeAttributeArea(AttributeArea area) {
		this.attributeAreas.remove(area);
		if (!(area instanceof LocalArea))
			this.firstLocal--;
		// falls r�umliche Gruppe, Agenten austragen
//		if (area.isSpatialGroup()) {
//			for (int i = this.entitys.size() - 1; i >= 0; i--) {
//				Group g = area.getGroup();
//				g.leave((Entity) this.entitys.elementAt(i));
//			}
//		}
	}

	/**
	 * returns the list of "genuine" attribute areas as an array. The array is
	 * of length 0 if there are no areas containing this discrete space element.
	 */
	public AttributeArea[] getAttributeAreas() {
		// alle "echten" areas zur�ckliefern
		AttributeArea[] temp = new AttributeArea[this.firstLocal];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = (AttributeArea) this.attributeAreas.get(i);
		}
		return temp;
	}

	/**
	 * returns the list of "local" attribute areas as an array. The array is of
	 * length 0 if there are no local areas containing this discrete space
	 * element.
	 */
	public AttributeArea[] getLocalAreas() {
		// alle "locals" zur�ckliefern
		AttributeArea[] temp = new AttributeArea[this.attributeAreas.size()
				- this.firstLocal];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = (AttributeArea) this.attributeAreas.get(this.firstLocal
					+ i);
		}
		return temp;
	}

	/**
	 * adds the agent to this space element's list of agents.
	 */
//	public void addAgent(SituatedAgent agent) {
//		if (!this.agents.contains(agent)) {
//			this.agents.add(agent);
//			// bei allen r�umlichen Gruppen dieses space elements als Mitglied
//			// eintragen
//			for (int i = 0; i < this.attributeAreas.size(); i++) {
//				AttributeArea area = (AttributeArea) attributeAreas
//						.elementAt(i);
//				if (area.isSpatialGroup()) {
//					area.getGroup().join(agent);
//				}
//			}
//		}
//	}

	/**
	 * removes the agent from this space element's list of agents.
	 */
//	public void removeAgent(SituatedAgent agent) {
//		if (this.agents.contains(agent)) {
//			this.agents.remove(agent);
//			// bei allen r�umlichen Gruppen dieses space elements als Mitglied
//			// austragen
//			for (int i = 0; i < this.attributeAreas.size(); i++) {
//				AttributeArea area = (AttributeArea) attributeAreas
//						.elementAt(i);
//				if (area.isSpatialGroup()) {
//					area.getGroup().leave(agent);
//				}
//			}
//		}
//	}

	/**
	 * returns the list of agents as an array. The array is of length 0 if there
	 * are no agents at this discrete space element.
	 */
//	public SituatedAgent[] getAgents() {
//		SituatedAgent[] temp = new SituatedAgent[this.agents.size()];
//		this.agents.copyInto(temp);
//		return temp;
//	}

	/**
	 * returns the list of objects as an array. The array is of length 0 if
	 * there are no objects at this discrete space element.
	 */
	public Object[] getObjects() {
		Object[] temp = new Object[this.objects.size()];
		this.objects.copyInto(temp);
		return temp;
	}

	/**
	 * adds the object to this space element's list of objects.
	 */
	public void addObject(Object object) {
		if (!this.objects.contains(object)) {
			this.objects.add(object);
		}
	}

	/**
	 * removes the object from this space element's list of objects.
	 */
	public void removeObject(Object object) {
		this.objects.remove(object);
	}

	// --------------------------------------------------- �berschriebene
	// Methoden

	/**
	 * returns all attributes attached to this space element, i.e. the list of
	 * attributes plus the attributes of the attributes areas.
	 * 
	 * NOTE: This implementation makes a copy of the internal attribute list so
	 * that the attributes of the areas can be added without changing the
	 * internal attribute list.
	 */
	public AttributeList getAttributes() {
		// Attribute aus attributes und alle Attribute der attributeAreas
		// einsammeln
		// --- kann hier das Problem auftreten, dass ein key mit mehreren
		// values vorkommt? Falls ja, wird momentan nur der zuletzt gefundene
		// Wert in die Gesamtliste �bernommen
		AttributeList list = new AttributeList(this.attributes.getAttributes());
		// alle Attribute aus den Attributgebieten sammeln
		// dabei die locals auslassen
		AttributeList currentList;
		for (int i = this.firstLocal - 1; i >= 0; i--) {
			currentList = ((AttributeArea) attributeAreas.elementAt(i))
					.getAttributes();
			list.putAttributes(currentList);
		}
		return list;
	}

	/**
	 * returns the attribute value for the given attribute name or
	 * <code>null</code> if there is no attribute with the given name attached
	 * to this space element.
	 */
	public Object getAttributeValue(String attributeName) {
		// check own attribute list first
		Object value = this.attributes.getAttributeValue(attributeName);
		// if necessary, check the attribute areas
		if (value == null) {
			int i = 0;
			while (value == null && i < this.attributeAreas.size()) {
				value = ((AttributeArea) this.attributeAreas.get(i++))
						.getAttributeValue(attributeName);
			}
		}
		return value;
	}

	// #####
	GridCell getDualCell() {
		return this.dualCell;
	}

	void setDualCell(GridCell cell) {
		this.dualCell = cell;
	}

	// ///// Interface-Methoden /////////////////////////////////////////////

	/**
	 * returns the name of this inspectable object.
	 */
	public String getName() {
		return new Integer(this.id).toString();
	}

	/**
	 * returns the type of this inspectable object. This is the class name
	 * (without packages).
	 */
	public String getType() {
		return "Node";
	}

	/**
	 * returns the inspectable attributes as a probe map. For a node, these are:
	 * id, reference point, leaving and incoming edges and the lists of agents,
	 * objects, attributes and attribute areas.
	 */
	public Map getAccessPoints() {
		Map attr = new HashMap();
		attr.put("id", new FieldAccessPoint("id", this));
		attr.put("refPoint", new FieldAccessPoint("refPoint", this));
		attr.put("leavingEdges", new FieldAccessPoint("leavingEdges", this));
		attr.put("incomingEdges", new FieldAccessPoint("incomingEdges", this));
//		attr.put("agents", new FieldAccessPoint("agents", this));
		attr.put("objects", new FieldAccessPoint("objects", this));
		attr.put("attributes", new FieldAccessPoint("attributes", this));
		attr
				.put("attributeAreas", new FieldAccessPoint("attributeAreas",
						this));
		return attr;
	}

	// /// �berschriebene Methoden ///////////////////////////////////////////

	// / wg. effizienter Benutzung von Node als key in Hashtables
	// / Methoden von Object �berschreiben
	/**
	 * returns this nodes's id. This method is implemented to facilitate the use
	 * of nodes as key object in hashtables.
	 */
	public int hashCode() {
		return this.id;
	}

	/**
	 * returns <code>true</code> if the given object is identical to this
	 * node, i.e. if <code>this == obj</code> returns <code>true</code>.
	 * This method is implemented to facilitate the use of nodes as key objects
	 * in hashtables.
	 */
	public boolean equals(Object obj) {
		return this == obj;
	}

	// f�r Testausgaben auf console
	/**
	 * returns a string representation of this node. In this implementation
	 * returns only the id converted to String.
	 */
	public String toString() {
		return Integer.toString(id);
	}

}