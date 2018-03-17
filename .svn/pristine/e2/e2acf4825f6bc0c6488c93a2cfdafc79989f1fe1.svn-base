package desmoj.extensions.space2D.xml;

import desmoj.extensions.dimensions.Length;
import desmoj.extensions.space2D.space.Edge;
import desmoj.extensions.space2D.space.Node;

/**
 * A description of an edge in a graph. Consists of an id, the ids of start node
 * and end node, the length and a list of additional attributes.
 */
public class EdgeDescription extends SpaceElementDescription {

	/** the start node id */
	protected int startNodeID;

	/** the end node id */
	protected int endNodeID;

	/** the length of the edge */
	protected Length length;

	// ----- the java-bean conform interface: standard constructor and get/set
	// ----- methods

	/** Default constructor */
	public EdgeDescription() {
	}

	/** returns the start node id. */
	public int getStartNodeID() {
		return this.startNodeID;
	}

	/** sets the start node id. */
	public void setStartNodeID(int id) {
		this.startNodeID = id;
	}

	/** returns the end node id. */
	public int getEndNodeID() {
		return this.endNodeID;
	}

	/** sets the end node id. */
	public void setEndNodeID(int id) {
		this.endNodeID = id;
	}

	/** returns the length of the edge. */
	public Length getLength() {
		return this.length;
	}

	/** sets the length of the edge. */
	public void setLength(Length length) {
		this.length = length;
	}

	// ------ additional methods

	/**
	 * returns the edge described by this edge description. Needs the start and
	 * end node, because the node ids alone can't be resolved to real node
	 * objects. Returns <code>null</code> if the node ids of the given nodes
	 * don't match the node ids stored in this description.
	 */
	public Edge getEdge(Node start, Node end) {
		if (this.startNodeID == start.getID() && this.endNodeID == end.getID())
			return new Edge(this.id, start, end, this.length, this.attrList);
		return null;
	}

	/**
	 * returns a String representation of this edge description. Lists the edge
	 * id, start and end node id, the length, and the attributes (if any).
	 */
	public String toString() {
		StringBuffer s = new StringBuffer("Edge " + id + " from " + startNodeID
				+ " to " + endNodeID);
		s.append(" (" + length.toString() + "): ");
		if (this.attrList == null) {
			s.append("* no attributes *");
		} else {
			s.append(this.attrList.toString());
		}
		return s.toString();
	}

	/**
	 * sets all fields in this edge description to null / zero values. And
	 * destroys recursively every attribute description.
	 */
	public void destroy() {
		// geerbte felder l�schen
		super.destroy();
		// knoten ids wegwerfen
		this.startNodeID = 0;
		this.endNodeID = 0;
		// L�nge wegwerfen
		this.length = null;
	}

	// ----- additional constructor for marshalling

	/**
	 * Constructs a new edge description for the given edge to be used in
	 * marshalling (= exporting to xml).
	 */
	public EdgeDescription(Edge edge) {
		// L�ngen-Attribut ist extra, deshalb aus der Attribut-Liste entfernen
		// -- dies ist problemlos m�glich, d.h. ohne Seiteneffekt, weil
		// getAttributes() eine Kopie der internen Attribut-Liste zur�ckliefert
		super(edge.getID(), edge.getAttributes().removeAttribute("length"));
		this.startNodeID = edge.getStartNode().getID();
		this.endNodeID = edge.getEndNode().getID();
		this.length = edge.getLength();
	}

}