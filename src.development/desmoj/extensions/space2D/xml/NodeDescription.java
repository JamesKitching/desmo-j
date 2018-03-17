package desmoj.extensions.space2D.xml;

import desmoj.extensions.space2D.space.Node;

/**
 * A description of a node. Consists of an id, a point description for the
 * reference point, and a list of attributes. Thus a node description doesn't
 * store the topological information which edges are adjunct to the node.
 */
public class NodeDescription extends SpaceElementDescription {

	/** the point description */
	private PointDescription point;

	// ----- the java-bean conform interface: standard constructor and get/set
	// ----- methods

	/** Default constructor */
	public NodeDescription() {
	}

	/** returns the point description. */
	public PointDescription getPoint() {
		return this.point;
	}

	/** sets the point description. */
	public void setPoint(PointDescription point) {
		this.point = point;
	}

	// ------ additional methods

	/**
	 * Returns the node described by this node description. This node will not
	 * have any information about incoming or leaving edges.
	 */
	public Node getNode() {
		return new Node(this.id, this.point.getPoint(), this.attrList);
	}

	/**
	 * returns a String representation of this node description. Lists the node
	 * id, the point description, and the attributes (if any).
	 */
	public String toString() {
		StringBuffer s = new StringBuffer("Node " + id + " at "
				+ point.toString() + ": ");
		if (this.attrList == null) {
			s.append("* no attributes *");
		} else {
			s.append(this.attrList.toString());
		}
		return s.toString();
	}

	/**
	 * sets all fields in this node description to null / zero values. And
	 * destroys recursively every attribute description.
	 */
	public void destroy() {
		// geerbte felder l�schen
		super.destroy();
		// point wegwerfen
		this.point = null;
	}

	// ----- additional constructor for marshalling

	/**
	 * Constructs a new node description for the given node to be used in
	 * marshalling (= exporting to xml). Only the node's id, attributes and
	 * reference point are retained.
	 */
	public NodeDescription(Node node) {
		super(node.getID(), node.getAttributes());
		this.point = new PointDescription(node.getRefPoint());
	}
}