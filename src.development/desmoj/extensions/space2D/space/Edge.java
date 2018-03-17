package desmoj.extensions.space2D.space;

import java.util.Vector;

import desmoj.extensions.dimensions.Length;

/**
 * This class represents a directed edge in a graph. A directed edge is defined
 * by its start and end node, its id and its length. It may have additional
 * attributes. An edge can be implicitly contained in an attribute area if both
 * its start node and its end node are part of that area.
 * 
 * @todo Edge ist jetzt �ber die Vererbungshierarchie ein DiscreteSpaceElement
 *       und kann daher Agenten, Objekte, Attribute und Attributgebiete haben
 *       Frage: ist das gut so?
 */
public class Edge extends GraphElement {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the start node of this edge. */
	protected Node startNode;

	/** the end node of this edge. */
	protected Node endNode;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new directed edge from start node to end node with the given
	 * id and length.
	 * 
	 * @param id
	 *            the edge's id
	 * @param startNode
	 *            the node the edge starts from
	 * @param endNode
	 *            the node the edge ends in
	 * @param length
	 *            the edge's length
	 */
	public Edge(int id, Node startNode, Node endNode, Length length) {
		this(id, startNode, endNode, length, null);
	}

	/**
	 * constructs a new directed edge from start node to end node with the given
	 * id, length and additional attributes.
	 * 
	 * @param id
	 *            the edge's id
	 * @param startNode
	 *            the node the edge starts from
	 * @param endNode
	 *            the node the edge ends in
	 * @param length
	 *            the edge's length
	 * @param attributes
	 *            the edge's additional attributes
	 */
	public Edge(int id, Node startNode, Node endNode, Length length,
			AttributeList attributes) {
		super(id, attributes);
		this.startNode = startNode;
		this.endNode = endNode;
		// add length to the attribute list
		super.addAttribute(new ComparableAttribute("length", length));
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/** returns the start node. */
	public Node getStartNode() {
		return this.startNode;
	}

	/** returns the end node. */
	public Node getEndNode() {
		return this.endNode;
	}

	/** returns the length of the edge */
	public Length getLength() {
		return (Length) this.attributes.getAttributeValue("length");
	}

	// /// �berschriebene Methoden ///////////////////////////////////////////

	// geerbte Methode getAttributes() �berschreiben, damit auch die Attribute
	// der attributeAreas enthalten sind, in denen Edge implizit enthalten
	// ist, weil sowohl Start- als auch Endknoten drin sind
	// --- kann auch hier das Problem auftreten, dass ein key mit mehreren
	// values vorkommt? Falls ja, wird momentan nur der zuletzt gefundene
	// Wert in die Gesamtliste �bernommen

	/**
	 * returns the attributes of this edge as an attribute list. This list
	 * contains at least the edge's length even if this edge does not have any
	 * additional attributes and is not contained in any attribute areas.
	 */
	public AttributeList getAttributes() {
		AttributeList list = new AttributeList(this.attributes);
		AttributeArea[] startAreas = startNode.getAttributeAreas();
		AttributeArea[] endAreas = endNode.getAttributeAreas();
		// Schnittmenge aus startAreas und endAreas bilden
		Vector myAreas = new Vector();
		for (int i = 0; i < startAreas.length; i++) {
			int j = 0;
			while (j < endAreas.length && startAreas[i] != endAreas[j]) {
				j++;
			}
			if (j < endAreas.length) {
				// gefunden!
				myAreas.add(startAreas[i]);
			}
		}
		// Attribute aus der Schnittmenge einsammeln
		for (int i = myAreas.size() - 1; i >= 0; i--) {
			list.putAttributes(((AttributeArea) myAreas.elementAt(i))
					.getAttributes());
		}
		return list;
	}

	// f�r Testausgaben auf console
	/** returns a string representation of this edge. */
	public String toString() {
		return new String("(" + startNode.toString() + ")" + "---" + id
				+ "--->" + "(" + endNode.toString() + ")");
	}
} /* end of class Edge */