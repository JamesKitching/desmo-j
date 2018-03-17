package desmoj.extensions.space2D.space;

/**
 * A position for use with directed graphs (famos.space.Graph). Each graph
 * position refers to a node in such a graph.
 */
public class GraphPosition extends Position {

	// //////////////// Attribute ////////////////////////////////////////

	/** the node this graph positions is associated with. */
	protected Node node;

	// //////////////// Konstruktoren ///////////////////////////////////

	/**
	 * constructs a new graph position for the given graph and the given node.
	 */
	public GraphPosition(Graph graph, Node node) {
		super(graph);
		this.node = node;
	}

	// ///////////////// Methoden //////////////////////////////////////

	/** returns the node this graph position refers to. */
	public Node getNode() {
		return this.node;
	}

	// public void setNode(Node node) {
	// this.node = node;
	// }

	/** returns a string representation of this graph position */
	public String toString() {
		return "GraphPosition " + this.node.toString();
	}

	// /--------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * returns the point in space this position refers to, i.e. the position of
	 * the node.
	 */
	public Point getPoint() {
		return this.node.getRefPoint();
	}

	/**
	 * GraphPosition objects are equal iff they belong to the same graph node
	 */
	public boolean equals(Object o) {
		return (o instanceof GraphPosition)
				&& ((GraphPosition) o).getNode() == this.getNode();
	}

	/** returns this position's node id as hash code. */
	public int hashCode() {
		return this.node.id;
	}

	/**
	 * adds the given attribute area to this position's areas, i.e. the areas to
	 * which the node this position refers to belongs.
	 */
	protected void addAttributeArea(AttributeArea area) {
		this.node.addAttributeArea(area);
	}

	/**
	 * removes the given attribute area from this position's areas, i.e. the
	 * areas to which the node this position refers to belongs.
	 */
	protected void removeAttributeArea(AttributeArea area) {
		this.node.removeAttributeArea(area);
	}

}