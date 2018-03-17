package desmoj.extensions.space2D.space;

/* �nderungen wg. xml-Schnittstelle:
 (1) neuer Konstruktor, der Array von Node- und EdgeDescription �bernimmt
 (2) die beiden alten Konstruktoren mit famos.space.EdgeDescription entfernt
 */

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import desmoj.core.simulator.Units;
import desmoj.extensions.dimensions.Length;
//import desmoj.extensions.space2D.agent.SituatedAgent;
import desmoj.extensions.space2D.gui.GraphView;
import desmoj.extensions.space2D.gui.SpaceView;
import desmoj.extensions.space2D.xml.EdgeDescription;
import desmoj.extensions.space2D.xml.NodeDescription;

/**
 * A directed graph as a discrete space. Consists of nodes as discrete space
 * elements and directed edges defining the neighbourhood relation. As only
 * nodes are space elements, graph positions are associated with nodes only.
 * Graphs can be imported from XML specifications via famos.xml.Import
 * 
 * @see famos.xml.Import
 * 
 * @author Ruth Meyer
 */
public class Graph extends DiscreteSpace {

	// //////////// Attribute ////////////////////////////////

	/** the list of nodes. */
	protected Node[] nodes;

	/** the list of edges. */
	protected ArrayList edges;

	/** the default length unit is desmoj.Units.M */
	private int unit = Units.M; // ### ACHTUNG: noch hardcodiert

	/**
	 * this graph's distance finder used to compute the distance between two
	 * nodes.
	 */
	private GraphPathDijkstra distanceFinder;

	// ////////////// Konstruktoren ////////////////////////////

	/**
	 * Constructs a new graph out of the node and edge descriptions as specified
	 * by an xml document.
	 * 
	 * @param nd
	 *            the list of node descriptions specifying the nodes of this
	 *            graph
	 * @param ed
	 *            the list of edge descriptions specifying the edges of this
	 *            graph
	 * @param origin
	 *            the position of this graph's coordinate system as an array of
	 *            integer constants (defined in the interface
	 *            AffineTransformable)
	 * @see famos.gui.AffineTransformable
	 */
	public Graph(NodeDescription[] nd, EdgeDescription[] ed, int[] origin) {
		// change node descriptions to real nodes
		this.nodes = new Node[nd.length];
		for (int i = 0; i < nd.length; i++) {
			this.nodes[i] = nd[i].getNode();
		}
		// sort nodes according to node IDs to enable binary search in
		// method getNode(id)
		this.sortNodes();
		// change edge descriptions into real edges and join edges and nodes
		this.edges = new /* Edge[ed.length] */ArrayList(ed.length);
		for (int i = 0; i < ed.length; i++) {
			Node start = getNode(ed[i].getStartNodeID());
			Node end = getNode(ed[i].getEndNodeID());
			Edge e = ed[i].getEdge(start, end);
			this.edges.add(i, e);
			start.addLeavingEdge(e);
			end.addIncomingEdge(e);
		}
		// set origin, bounds and distance finder
		finishInitialize(origin);
	}

	/**
	 * Constructs a new graph from the given nodes, edges and the origin. It is
	 * assumed that the edges are already joined to the nodes!
	 * 
	 * @param nodes
	 *            the nodes
	 * @param edges
	 *            the edges
	 * @param origin
	 *            the position of this graph's coordinate system as an array of
	 *            integer constants (defined in the interface
	 *            AffineTransformable)
	 * @see famos.gui.AffineTransformable
	 */
	public Graph(Node[] nodes, Edge[] edges, int[] origin) {
		// set nodes
		this.nodes = nodes;
		// set edges
		this.edges = new ArrayList(edges.length);
		for (int i = 0; i < edges.length; i++) {
			this.edges.add(i, edges[i]);
		}
		// sort nodes according to node IDs to enable binary search in
		// method getNode(id)
		sortNodes();

		// set origin, bounds and distance finder
		finishInitialize(origin);
	}

	/** Default constructor for an empty graph. */
	protected Graph() {
	}

	// ////////////// Methoden ////////////////////////////////

	/**
	 * Returns the node with the given node id. As node ids are unique there can
	 * only be one node with the specified id.
	 * 
	 * @param nodeID
	 *            the node id defining the node
	 * @return the node with <code>nodeID</code> or <code>null</code> if
	 *         this graph does not contain such a node
	 */
	protected Node getNode(int nodeID) {
		// anhand nodeID im Array nodes suchen mit bin�rer Suche
		int left = 0;
		int right = this.nodes.length;
		while (left < right) {
			int middle = (left + right) / 2;
			if (this.nodes[middle].getID() < nodeID) {
				left = middle + 1;
			} else {
				right = middle;
			}
		}
		if (right < this.nodes.length && nodeID == this.nodes[right].getID()) {
			// nodeID gefunden
			return this.nodes[right];
		}
		return null;
	}

	// ---------------------- die von (Discrete)Space geerbten abstrakten
	// Methoden

	/**
	 * Returns the spatial distribution of the specified attribute as a hashmap
	 * with key = GraphPosition and value = Attribute. If the attribute is
	 * nowhere to be found in the graph the hashmap will be empty. This method
	 * is called from the corresponding graph view if an attribute is to be
	 * displayed.
	 * 
	 * @param attr
	 *            the comparable attribute to be visualized
	 * @return the distribution of the specified attribute in space as a hashmap
	 */
	public HashMap getAttributeDistribution(ComparableAttribute attr) {
		HashMap map = new HashMap();
		// einmal durch die Knotenliste laufen und nach dem Attribut suchen
		for (int i = 0; i < this.nodes.length; i++) {
			Object value = this.nodes[i].getAttributeValue(attr.getName());
			if (value != null && value instanceof Comparable) {
				map.put(new GraphPosition(this, this.nodes[i]),
						new ComparableAttribute(attr.getName(),
								(Comparable) value));
			}
		}
		return map;
	}

	/**
	 * Returns a list of all objects currently situated in this graph, i.e. at
	 * the nodes of this graph.
	 * 
	 * @return all objects in space as an array
	 */
	public Object[] getObjects() {
		// einmal alle Knoten durchlaufen und die Objekte einsammeln
		Vector objects = new Vector();
		for (int i = 0; i < this.nodes.length; i++) {
			Object[] temp = this.nodes[i].getObjects();
			for (int j = 0; j < temp.length; j++) {
				objects.add(temp[j]);
			}
		}
		return objects.toArray();
	}

	/**
	 * Constructs a graph view for this graph with the given window dimensions.
	 * 
	 * @param winWidth
	 *            the width of the viewport (window) in pixel
	 * @param winHeight
	 *            the height of the viewport (window) in pixel
	 * @return a visualization (GraphView) for this graph
	 */
	public SpaceView getSpaceView(int winWidth, int winHeight) {
		return new GraphView(this, winWidth, winHeight);
	}

	/**
	 * Constructs a graph view for this graph with the given window dimensions
	 * and the specified attribute distribution.
	 * 
	 * @param winWidth
	 *            the window width in pixel
	 * @param winHeight
	 *            the window height in pixel
	 * @param attr
	 *            the attribute to be colour coded
	 * @param color
	 *            the color to be used
	 * @param numClasses
	 *            the number of attribute value classes (= shades of color)
	 * @return a visualization (GraphView) for this graph
	 */
	public SpaceView getSpaceView(int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses) {
		return new GraphView(this, winWidth, winHeight, attr, color, numClasses);
	}

	/**
	 * Returns a GraphPosition corresponding to the given point.
	 * 
	 * @param p
	 *            the point to be converted to a position associated with this
	 *            graph
	 * @return the GraphPosition associated to the node nearest to the point
	 *         <code>p</code>
	 */
	public Position getPosition(Point p) {
		// mit nearestNode den Knoten bestimmen
		return new GraphPosition(this, nearestNode(p));
	}

	/**
	 * Returns the reachable neighbours of the given position, i.e. the nodes
	 * reachable via the leaving edges of the node associated with the given
	 * position.
	 * 
	 * @param position
	 *            the position for which the reachable neighbours are to be
	 *            determined. This must be a graph position
	 * @return the directly reachable neighbouring positions as an array of
	 *         GraphPosition objects. This array may be empty, if there are no
	 *         such neighbouring nodes.
	 */
	public Position[] getReachableNeighbours(Position position) {
		// liefert nur die �ber leavingEdges erreichbaren Knoten
		return getNeighbours(position, false);
	}

	/**
	 * Returns all neighbours of the given position, i.e. the start nodes of the
	 * incoming edges and the end nodes of the leaving edges of the node
	 * associated with the given position.
	 * 
	 * @param position
	 *            the position for which the neighbours are to be determined.
	 *            This must be a graph position
	 * @return the directly adjacent positions as an array of GraphPosition
	 *         objects. This array may be empty, if there are no such neighbour
	 *         nodes.
	 */
	public Position[] getNeighbours(Position position) {
		// liefert die �ber leavingEdges UND die �ber incomingEdges
		// "erreichbaren"
		// Knoten
		return getNeighbours(position, true);
	}

	// ----------------------------------------- die protected Methoden von
	// space

	/**
	 * removes the given agent from this graph.
	 * 
	 * @param agent
	 *            the situated agent to be removed from this graph
	 */
//	protected void removeAgent(SituatedAgent agent) {
//		// Position abfragen, in GraphPosition wandeln, Node abfragen und
//		// agent aus Node austragen und agent die Position wegnehmen
//		GraphPosition agentPos = (GraphPosition) agent.getCurrentPosition();
//		agentPos.getNode().removeAgent(agent);
//		agent.setCurrentPosition(null);
//	}

	/**
	 * adds the given agent to this graph.
	 * 
	 * @param agent
	 *            the situated agent to be positioned in this graph
	 * @param pos
	 *            the position of the agent
	 */
//	protected void addAgent(SituatedAgent agent, Position pos) {
//		// pos in GraphPosition wandeln, den Agenten in Node eintragen und
//		// dem Agenten die Position geben
//		GraphPosition agentPos = (GraphPosition) pos;
//		agentPos.getNode().addAgent(agent);
//		agent.setCurrentPosition(agentPos);
//	}

	/**
	 * returns the attributes of the given position.
	 * 
	 * @param pos
	 *            the position for which the attributes are to be retrieved
	 * @return the attributes associated with the given positon
	 */
	protected AttributeList getAttributes(Position pos) {
		return ((GraphPosition) pos).getNode().getAttributes();
	}

	/**
	 * adds the given attribute area to the given position's areas, i.e. the
	 * areas to which the space element the position points to belongs.
	 * 
	 * @param area
	 *            the attribute area to be added to <code>pos</code>
	 * @param pos
	 *            the position "joining" the attribute area
	 */
	protected void addAttributeArea(AttributeArea area, Position pos) {
		((GraphPosition) pos).getNode().addAttributeArea(area);
	}

	/**
	 * removes the given attribute area from the given position's areas, i.e.
	 * the areas to which the space element the position points to belongs.
	 * 
	 * @param area
	 *            the attribute area to be removed from <code>pos</code>
	 * @param pos
	 *            the position "leaving" the attribute area
	 */
	protected void removeAttributeArea(AttributeArea area, Position pos) {
		((GraphPosition) pos).getNode().removeAttributeArea(area);
	}

	/**
	 * returns the objects residing at the given position in space.
	 * 
	 * @param pos
	 *            the position for which the list of objects is to be retrieved
	 * @return the objects at the given position as an array
	 */
	protected Object[] getObjects(Position pos) {
		return ((GraphPosition) pos).getNode().getObjects();
	}

	/**
	 * returns the agents currently residing at the given position in space
	 * 
	 * @param pos
	 *            the position for which the list of agents is to be retrieved
	 * @return the agents at the given position as an array
	 */
//	protected SituatedAgent[] getAgents(Position pos) {
//		return ((GraphPosition) pos).getNode().getAgents();
//	}

	/**
	 * returns the local areas (observable areas of situated agents) the given
	 * position -- to be more exact: the space element this position refers to --
	 * is part of. The array is of length 0 if there are no such local areas.
	 * 
	 * @param pos
	 *            the position for which the list of areas is to be retrieved
	 * @return the local areas the given position is part of as an array
	 */
	protected AttributeArea[] getLocalAreas(Position pos) {
		return ((GraphPosition) pos).getNode().getLocalAreas();
	}

	/**
	 * adds the given object to the specified position in space.
	 * 
	 * @param obj
	 *            the object to be added
	 * @param pos
	 *            the position for the object (has to be a GraphPosition)
	 */
	protected void addObject(Object obj, Position pos) {
		((GraphPosition) pos).getNode().addObject(obj);
	}

	/**
	 * removes the given object from the specified position in space.
	 * 
	 * @param obj
	 *            the object to be removed
	 * @param pos
	 *            the position of the object (has to be a GraphPosition)
	 */
	protected void removeObject(Object obj, Position pos) {
		((GraphPosition) pos).getNode().removeObject(obj);
	}

	/**
	 * adds the given attribute to the specified position in space.
	 * 
	 * @param attr
	 *            the attribute to be added
	 * @param pos
	 *            the position for the attribute
	 */
	protected void addAttribute(Attribute attr, Position pos) {
		((GraphPosition) pos).getNode().addAttribute(attr);
	}

	/**
	 * removes the given attribute from the specified position in space.
	 * 
	 * @param attr
	 *            the attribute to be removed
	 * @param pos
	 *            the position of the attribute
	 */
	protected void removeAttribute(Attribute attr, Position pos) {
		((GraphPosition) pos).getNode().removeAttribute(attr.getName());
	}

	/**
	 * sets the value of the attribute defined by the given name at the
	 * specified position in space to the given value.
	 * 
	 * @param aName
	 *            the name specifying the attribute
	 * @param aValue
	 *            the new attribute value
	 * @param pos
	 *            the position where the attribute belongs
	 */
	protected void setAttributeValue(String aName, Object aValue, Position pos) {
		((GraphPosition) pos).getNode().setAttribute(aName, aValue);
	}

	/**
	 * sets origin, bounds and distance finder. Declared protected to be used in
	 * derived classes which need the default constructor.
	 * 
	 * @param origin
	 *            this graph's origin
	 */
	protected void finishInitialize(int[] origin) {
		// set origin and compute bounds
		this.origin = origin;
		this.bounds = computeBoundingBox();
		// set distance finder
		this.distanceFinder = new GraphPathDijkstra(this);
	}

	// ------------------------------------ f�r Mitbenutzung von
	// GraphPathDijkstra

	/**
	 * Returns the path strategy used as distance finder. This method provides
	 * for an efficient co-use of this path strategy by agents moving in this
	 * graph.
	 * 
	 * @return a reference to this graph's distance finder
	 */
	public GraphPathDijkstra getDistanceFinder() {
		return this.distanceFinder;
	}

	// ---------------------------------------- f�r Visualisierung und
	// Routensuche

	/**
	 * Returns the list of edges of this graph. This method is needed in
	 * visualizing the graph.
	 * 
	 * @return this graph's edges as an array
	 */
	public Edge[] getEdges() {
		Edge[] list = new Edge[this.edges.size()];
		this.edges.toArray(list);
		return list;
	}

	/**
	 * Returns the list of nodes of this graph. This method is needed in
	 * visualizing the graph.
	 * 
	 * @return this graph's nodes
	 */
	public Node[] getNodes() {
		// ACHTUNG: aus Effizienzgr�nden wird hier direkt der Knoten-Array
		// zur�ckgeliefert
		return this.nodes;
	}

	// ------------------------------------------------------------- f�r
	// Debugging

	/**
	 * Returns a string representation of this graph. Lists all nodes and edges.
	 * 
	 * @return a String of the format <code>Directed Graph with nodes:
	 *  \n node_i \n and edges: \n edge_i</code>
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("Directed Graph with nodes:");
		for (int i = 0; i < this.nodes.length; i++) {
			sb.append("\n");
			sb.append(this.nodes[i].toString());
		}
		sb.append("\n and edges:");
		for (int i = 0; i < this.edges.size(); i++) {
			sb.append("\n");
			sb.append(this.edges.get(i).toString());
		}
		return sb.toString();
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	/**
	 * Helper method: determines the nearest node for the given point. (Note:
	 * the current implementation assumes a 2D-graph.)
	 * 
	 * @param point
	 *            the point for which the nearest node is to be found
	 * @return the nearest node
	 */
	private Node nearestNode(Point point) {
		// ## Achtung: nur 2D-Fall!
		int minIndex = -1; // Index des bislang naechsten Knotens
		double minDistance = Double.POSITIVE_INFINITY;
		double distX, distY, dist;

		// einfach, aber BruteForce: alle Knoten durchlaufen
		for (int i = 0; i < nodes.length; i++) {
			// senkrechte Distanz nur in x- bzw. y-Richtung
			distX = Math.abs(nodes[i].getRefPoint().getX() - point.getX());
			distY = Math.abs(nodes[i].getRefPoint().getY() - point.getY());
			// Quadratwurzel nur berechnen, wenn beide der obigen Distanzen
			// kleiner als die bisherige kuerzeste Distanz sind.
			// nicht ganz BruteForce
			if ((distX < minDistance) && (distY < minDistance)) {
				// Distanz per Luftlinie
				dist = Math.sqrt((Math.pow(distX, 2.0))
						+ (Math.pow(distY, 2.0)));
				// wenn kleiner, dann aktualisiere Index und kuerzeste Distanz
				if (dist < minDistance) {
					minDistance = dist;
					minIndex = i;
				}
			}
		}
		// Rueckgabe Knoten
		return nodes[minIndex];
	}

	/**
	 * Helper method: sorts the node list according to the node ids in ascending
	 * order. This allows binary search in the method getNode(id).
	 */
	private void sortNodes() {
		// einmal den node-array sortieren mit Quicksort
		quicksort(0, this.nodes.length - 1);
	}

	/**
	 * Helper method: the quicksort algorithm applied to the nodes array.
	 * 
	 * @param left
	 *            the left index
	 * @param right
	 *            the right index
	 */
	private void quicksort(int left, int right) {
		int i = left;
		int j = right;
		Node x = this.nodes[(left + right) / 2];
		do {
			while (this.nodes[i].getID() < x.getID())
				i++;
			while (x.getID() < this.nodes[j].getID())
				j--;
			if (i <= j) {
				Node temp = this.nodes[i];
				this.nodes[i] = this.nodes[j];
				this.nodes[j] = temp;
				i++;
				j--;
			}
		} while (i <= j);
		if (left < j)
			quicksort(left, j);
		if (i < right)
			quicksort(i, right);
	}

	/**
	 * Helper method: computes the mimimum bounding rectangle of this graph.
	 * Called in the constructor. (Note: this implementation assumes a 2D-Graph)
	 * 
	 * @return the coordinates of the bounding box as an array {x1, y1, x2, y2}
	 */
	private double[] computeBoundingBox() {
		double[] bounds = new double[] { 0, 0, 0, 0 };
		if (nodes.length == 0) {
			return bounds;
		}
		// bounds mit Koordinaten des ersten Punktes initialisieren
		bounds[0] = nodes[0].getRefPoint().getX();
		bounds[1] = nodes[0].getRefPoint().getY();
		bounds[2] = bounds[0];
		bounds[3] = bounds[1];
		// einmal durch die Knotenliste laufen und min und max bestimmen
		for (int i = 0; i < nodes.length; i++) {
			checkBounds(nodes[i].getRefPoint(), bounds);
		}
		return bounds;
	}

	/**
	 * Helper method: checks a point to see if its coordinates require to
	 * compute a new minimum bounding rectangle and corrects the bounds values
	 * accordingly. Called from addNode() and computeBoundingBox().
	 * 
	 * @param position
	 *            the point to be checked
	 * @param bounds
	 *            the currently computed coordinates of the bounding box
	 */
	private void checkBounds(Point position, double[] bounds) {
		double currentValue = position.getX();
		if (currentValue < bounds[0]) {
			bounds[0] = currentValue;
		} else if (currentValue > bounds[2]) {
			bounds[2] = currentValue;
		}
		currentValue = position.getY();
		if (currentValue < bounds[1]) {
			bounds[1] = currentValue;
		} else if (currentValue > bounds[3]) {
			bounds[3] = currentValue;
		}
	}

	/**
	 * Helper method: determines the edge connecting node a to node b (if there
	 * is any).
	 * 
	 * @param a
	 *            the start node
	 * @param b
	 *            the end node
	 * @return the directed edge a-->b or <code>null</code> if there is no
	 *         such edge in this graph
	 */
	protected Edge getEdge(Node a, Node b) {
		Edge e = null;
		Edge[] leaving = a.getLeavingEdges();
		int i = 0;
		while (i < leaving.length && leaving[i].getEndNode() != b) {
			i++;
		}
		if (i < leaving.length) {
			e = leaving[i];
		}
		return e;
	}

	/**
	 * Internal method: removes the specified edge from the graph. The edge
	 * lists of start and end node are updated accordingly.
	 * 
	 * @param edge
	 *            the edge to be removed
	 */
	protected void removeEdge(Edge edge) {
		if (edge == null)
			return;
		// aus Knoten austragen
		edge.getStartNode().removeLeavingEdge(edge);
		edge.getEndNode().removeIncomingEdge(edge);
		// aus Kantenliste l�schen
		this.edges.remove(edge);
	}

	/**
	 * Internal method: adds the specified edge to the graph. The edge lists of
	 * start and end node are updated accordingly.
	 * 
	 * @param edge
	 *            the edge to be added
	 */
	protected void addEdge(Edge edge) {
		if (edge == null)
			return;
		// bei Knoten eintragen
		edge.getStartNode().addLeavingEdge(edge);
		edge.getEndNode().addIncomingEdge(edge);
		// in Kantenliste eintragen
		this.edges.add(edge);
	}

	/**
	 * Internal method: returns the number of edges in this graph.
	 * 
	 * @return the size of the edge list
	 */
	protected int getNumberOfEdges() {
		return this.edges.size();
	}

	/**
	 * Returns the length of the shortest path from a to b or <code>null</code>
	 * if there is no path in this graph. If
	 * <code>ignoreDirection == false</code> a directed path from a to b is
	 * sought (default, called in getDistance(a,b)). Otherwise the direction of
	 * the edges is ignored. Thus the shortest path may contain edges pointing
	 * in the other direction (used in ObservableArea()).
	 * 
	 * @param a
	 *            the start position (must be a GraphPosition object)
	 * @param b
	 *            the destination (must be a GraphPosition object)
	 * @param ignoreDirection
	 *            flag indicating if the direction of the edges is taken into
	 *            account when computing the shortest path
	 * @return the distance between <code>a</code> and <code>b</code> as the
	 *         length of the shortest path from <code>a</code> to
	 *         <code>b</code>. The distance is 0 if both positions point to
	 *         the same node. Returns <code>
	 *  null</code> if the position are
	 *         not graph positions.
	 */
	protected Length getDistance(Position a, Position b, boolean ignoreDirection) {
		// funktioniert nur f�r GraphPosition
		Length distance = null;
		// identische Position? dann ist Distance = 0.0
		if (a.equals(b))
			return new Length();
		try {
			// berechnet echte Kanten-Entfernung
			Node na = ((GraphPosition) a).getNode();
			Node nb = ((GraphPosition) b).getNode();
			Edge e = getEdge(na, nb);
			if (ignoreDirection && e == null) {
				e = getEdge(nb, na);
			}
			if (e != null) {
				// direkte Kante --> deren L�nge zur�ckgeben
				// System.out.println("distance " + na.getID() + ">" +
				// nb.getID() + " = " + e.getLength());
				return e.getLength();
			}
			// mit GraphPathDijkstra k�rzesten Weg suchen zwischen a und b
			Path p = distanceFinder.findPath(a, b, ignoreDirection);
			// L�nge von p bestimmen
			if (p != null) {
				// System.out.println("path = " + p);
				distance = new Length();
				for (int i = 1; i < p.size(); i++) {
					nb = ((GraphPosition) p.getPosition(i)).getNode();
					e = getEdge(na, nb);
					if (ignoreDirection && e == null) {
						e = getEdge(nb, na);
					}
					distance = distance.add(e.getLength());
					na = nb;
				}
			}
			// System.out.println("distance " + a + "-->" + b + " = " +
			// distance);
		} catch (ClassCastException e) { // falls Position keine
			// GraphPosition
		}
		return distance;
	}

	/**
	 * Helper method: returns the direct neighbours of the node the given
	 * position refers to. These are the end nodes of the leaving edges and - if
	 * <code>ignoreDirection == true</code> - the start nodes of the incoming
	 * edges.
	 * 
	 * @param position
	 *            the position for which the neighbours are sought. This must be
	 *            a GraphPosition object.
	 * @param ignoreDirection
	 *            flag indicating if the direction of edges shall be taken into
	 *            account
	 * @return an array of GraphPosition objects referring to the adjacent
	 *         nodes. This array is empty if there are no neighbours or if the
	 *         given position is not a GraphPosition object
	 */
	private Position[] getNeighbours(Position position, boolean ignoreDirection) {
		// funktioniert nur f�r graphPosition
		Position[] neighbours = new GraphPosition[0];
		try {
			Edge[] edges = ((GraphPosition) position).getNode()
					.getLeavingEdges();
			ArrayList temp = new ArrayList(edges.length);
			for (int i = 0; i < edges.length; i++) {
				temp.add(i, new GraphPosition(this, edges[i].getEndNode()));
			}
			if (ignoreDirection) {
				// auch incomingEdges ber�cksichtigen
				Edge[] inc = ((GraphPosition) position).getNode()
						.getIncomingEdges();
				temp.ensureCapacity(edges.length + inc.length);
				for (int i = 0; i < inc.length; i++) {
					GraphPosition pos = new GraphPosition(this, inc[i]
							.getStartNode());
					if (!temp.contains(pos)) {
						// nur hinzuf�gen, wenn nicht schon enthalten
						temp.add(pos);
					}
				}
			}
			neighbours = new GraphPosition[temp.size()];
			temp.toArray(neighbours);
		} catch (ClassCastException e) {
			// falls Position keine GraphPosition
			System.out
					.println("ClassCastException: position is not a GraphPosition");
		}
		// System.out.println("... found " + neighbours.length + " neighbours");
		return neighbours;
	}

	@Override
	public SpaceView getSpaceView(int winWidth, int winHeight,
			Image backgroundImage) {
		return new GraphView(this, winWidth, winHeight);
	}

	@Override
	public SpaceView getSpaceView(int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses,
			Image backgroundImage) {
		return new GraphView(this, winWidth, winHeight, attr, color, numClasses, backgroundImage);
	}

} /* end of class Graph */