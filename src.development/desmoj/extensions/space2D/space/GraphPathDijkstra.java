package desmoj.extensions.space2D.space;

import java.util.Hashtable;
import java.util.Vector;

/**
 * A path strategy to find a path in a directed graph according to the Dijkstra
 * algorithm for shortest paths in graphs.
 */
public class GraphPathDijkstra implements PathStrategy {

	// //////////// Attribute //////////////////////////////////////////////

	/** the underlying graph. Is stored here to enhance efficiency. */
	private Graph graph;

	/** the list of nodes of this graph. */
	private Node[] nodelist;

	/** a hashtable of node indices (key = node, value = index into nodelist) */
	private Hashtable indices;

	/**
	 * the rating function used to rate the edges during path finding. Default
	 * is a length function.
	 */
	private RatingFunction rating = new LengthFunction();

	// //////////// Konstruktoren //////////////////////////////////////////

	/** Default constructor */
	public GraphPathDijkstra() {
	}

	/**
	 * Constructs a GraphPathDijkstra path strategy with the given rating
	 * function.
	 * 
	 * @param rf
	 *            the rating function to use in finding paths.
	 */
	public GraphPathDijkstra(RatingFunction rf) {
		this.rating = rf;
	}

	/**
	 * Constructs a GraphPathDijkstra path strategy for the given graph.
	 * 
	 * @param graph
	 *            the graph to use in finding paths.
	 */
	public GraphPathDijkstra(Graph graph) {
		this.graph = graph;
		this.nodelist = graph.getNodes();
		constructHashtable();
	}

	// //////////// Methoden ///////////////////////////////////////////////

	// ------------------------------- wg. Interface PathStrategy

	/**
	 * returns the currently used rating function.
	 */
	public RatingFunction getRatingFunction() {
		return this.rating;
	}

	/**
	 * sets the rating function. Note that any rating function used with
	 * GraphPathDijkstra must be able to compute a rating off the attributes of
	 * an edge in a graph.
	 */
	public void setRatingFunction(RatingFunction ratingFunction) {
		this.rating = ratingFunction;
	}

	/**
	 * returns a path (as a sequence of GraphPosition objects) between <code>
	 *  from</code>
	 * and <code>to</code>. If no special rating function has been set this
	 * path is the shortest path between the two given positions. If
	 * <code>ignoreDirection</code> is <code>true</code> the direction of
	 * the edges is ignored. Returns an empty path if no path can be found in
	 * the underlying graph. Returns <code>null</code> if one of the given
	 * positions is no GraphPosition.
	 * 
	 * @param from
	 *            the start position of the path
	 * @param to
	 *            the end position of the path
	 * @param ignoreDirection
	 *            flag to indicate if the edge directions should be ignored (<code>true</code>)
	 *            when seeking the path.
	 */
	public Path findPath(Position from, Position to, boolean ignoreDirection) {
		// Parameter �berpr�fen
		try {
			GraphPosition fromGP = (GraphPosition) from;
			GraphPosition toGP = (GraphPosition) to;
			Graph newGraph = (Graph) fromGP.getSpace();
			// neuer Graph?
			if (this.graph != newGraph) {
				// dann merken und ben�tigte Hilfskonstruktionen erstellen
				System.out.println("\nHEY, der Graph ist ja NEU!!!\n");
				this.graph = newGraph;
				this.nodelist = newGraph.getNodes();
				constructHashtable();
			}
			return shortestPathDijkstra(fromGP.getNode(), toGP.getNode(),
					ignoreDirection);
		} catch (ClassCastException e) {
			// falsche Parameterwerte --> null zurueckliefern
			return null;
		}
	}

	/**
	 * returns a path (as a sequence of GraphPosition objects) between <code>
	 *  from</code>
	 * and <code>to</code>. If no special rating function has been set this
	 * path is the shortest path between the two given positions. The direction
	 * of the edges will not be ignored. Returns an empty path if no path can be
	 * found in the underlying graph. Returns <code>null</code> if one of the
	 * given positions is no GraphPosition.
	 * 
	 * @param from
	 *            the start position of the path
	 * @param to
	 *            the end position of the path
	 */
	public Path findPath(Position from, Position to) {
		return findPath(from, to, false);
	}

	// ------- Hilfsmethoden
	// -------------------------------------------------------------

	/**
	 * helper method: constructs the internal hashtable for the node indices
	 * used in the path algorithm. Needs to be constructed only once for each
	 * graph.
	 */
	private void constructHashtable() {
		// in Abhaengigkeit von der Anzahl der Knoten die Groesse
		// der Hashtable bestimmen
		if (this.nodelist.length == 0) {
			this.indices = new Hashtable(1); // Hashtable (0) geht nicht
		} else {
			this.indices = new Hashtable(this.nodelist.length);
		}
		// Hashtable aufbauen
		for (int i = 0; i < this.nodelist.length; i++) {
			this.indices.put(this.nodelist[i], new Integer(i));
		}
	}

	/**
	 * internal method: shortest path (or best path if a different rating
	 * function is to be used) from the given start node to the given end node
	 * in the underlying graph according to Dijkstra's famous algorithm.
	 */
	// (vgl. zum Algorithmus Domschke/Drexl I, Seite 61)
	private Path shortestPathDijkstra(Node startNode, Node endNode,
			boolean ignoreDir) {
		// Die Knoten seien wie in nodelist durchnumeriert / indiziert
		int startNodeIndex = ((Integer) this.indices.get(startNode)).intValue();
		int endNodeIndex = ((Integer) this.indices.get(endNode)).intValue();
		// Liste der (akkumulierten) Kantenbewertungen vom Startknoten zu allen
		// anderen anderen Knoten
		double[] weight = new double[this.nodelist.length];
		// Indices der Vorgaenger eines Knotens auf einem kuerzesten Weg
		int[] predecessor = new int[this.nodelist.length];
		// Liste der Indices der markierten Knoten
		Vector markedNodes = new Vector();

		// Initialisierungen
		// Feld fuer Kantenbewertungen vom Startknoten initialisieren
		for (int i = 0; i < this.nodelist.length; i++) {
			weight[i] = Double.POSITIVE_INFINITY;
		}
		// Kantenbewertung von Startknoten zu Startknoten ist 0
		weight[startNodeIndex] = 0.0;
		// Vorgaenger von Startknoten ist Startknoten
		predecessor[startNodeIndex] = startNodeIndex;

		// Zu Beginn ist nur der Startknoten markiert
		markedNodes.addElement(new Integer(startNodeIndex));

		// Iteration: Kuerzeste Wege suchen vom Startknoten zu allen anderen
		// Knoten

		// Hilfsvariablen
		double minWeight, weightToNextNode;
		int markedNodesIndex, nextNodeIndex;
		int currentNodeIndex = startNodeIndex/*-1*/; // #### ok?
		Node currentNode, nextNode;

		// 1. Abbruchkriterium (= einziges Abbruchkriterium nach Dijkstra):
		// leeres Feld markierter Knoten
		// 2. Abbruchkriterium (da nur 1 kuerzester Weg gesucht ist):
		// endNode wird aus der Liste der markierten Knoten entfernt
		// (Die kuerzeste Strecke dorthin steht dann fest und aendert sich
		// im weiteren Verlauf des Verfahrens nicht mehr.)

		while ((!markedNodes.isEmpty()) && (currentNodeIndex != endNodeIndex)) {

			// Schritt 1

			// Den Index desjenigen Knotens aus der Menge der markierten Knoten
			// herausfinden, der die kleinste Kantenbewertung vom Startknoten
			// hat.
			minWeight = Double.POSITIVE_INFINITY;
			currentNodeIndex = -1;
			markedNodesIndex = -1;
			for (int i = 0; i < markedNodes.size(); i++) {
				currentNodeIndex = ((Integer) markedNodes.elementAt(i))
						.intValue();
				if (weight[currentNodeIndex] < minWeight) {
					markedNodesIndex = i;
					minWeight = weight[currentNodeIndex];
				}
			}
			currentNodeIndex = ((Integer) markedNodes
					.elementAt(markedNodesIndex)).intValue();

			// Schritt 2

			// Der ausgesuchte wird AKTUELLER Knoten und ...
			currentNode = this.nodelist[currentNodeIndex];
			// ... aus der Menge der markierten Knoten entfernt
			markedNodes.removeElementAt(markedNodesIndex);

			// Schritt 3

			// fuer alle Nachfolger des aktuellen Knotens
			weightToNextNode = Double.POSITIVE_INFINITY;
			Edge[] currentList = this.nodelist[currentNodeIndex]
					.getLeavingEdges();

			if (ignoreDir) {
				Edge[] inc = this.nodelist[currentNodeIndex].getIncomingEdges();
				Edge[] temp = new Edge[currentList.length + inc.length];
				for (int i = 0; i < currentList.length; i++) {
					temp[i] = currentList[i];
				}
				for (int i = 0; i < inc.length; i++) {
					temp[currentList.length + i] = inc[i];
				}
				currentList = temp;
			}

			for (int i = 0; i < currentList.length; i++) {
				Edge edge = currentList[i];
				nextNode = (Node) edge.getEndNode();

				if (ignoreDir && nextNode == this.nodelist[currentNodeIndex]) {
					nextNode = edge.getStartNode();
				}

				nextNodeIndex = ((Integer) this.indices.get(nextNode))
						.intValue();
				// Kantenbewertung bestimmen
				weightToNextNode = rating.rate(edge.getAttributes());

				// �nderung: bei Kantenbewertung unendlich Kante als nicht
				// vorhanden
				// betrachten und daher nextNode ignorieren
				if (weightToNextNode == Double.POSITIVE_INFINITY) {
					// System.out.println("Kantenbewertung unendlich bei Kante:
					// " + edge);
				} else {

					if (markedNodes.indexOf((Integer) this.indices
							.get(nextNode)) >= 0) {
						// nextNode ist in markedNodes
						// -- Fall III --
						if (weight[currentNodeIndex] + weightToNextNode < weight[nextNodeIndex]) {
							// Weg ueber akt. Knoten ist kuerzer
							weight[nextNodeIndex] = weight[currentNodeIndex]
									+ weightToNextNode;
							predecessor[nextNodeIndex] = currentNodeIndex;
						}
					} else {
						// nextNode ist nicht in markedNodes
						if (weight[nextNodeIndex] == Double.POSITIVE_INFINITY) {
							// nextNode war noch nicht erreicht
							// -- Fall I --
							weight[nextNodeIndex] = weight[currentNodeIndex]
									+ weightToNextNode;
							predecessor[nextNodeIndex] = currentNodeIndex;
							// nextNode in markedNodes aufnehmen
							markedNodes.addElement(new Integer(nextNodeIndex));
						} else {
							// nichts, Knoten schon abgehandelt (schon wieder
							// aus markedNodes 'raus)
							// -- Fall II --
						}
					}

				}
			} /* end for */
		} /* end while */

		if (currentNodeIndex != endNodeIndex) {
			// kein Pfad gefunden --> null zur�ckliefern
			System.out.println("there is no path from " + startNode + " to "
					+ endNode);
			return null;
		}

		// kuerzester Weg ist jetzt gefunden -> Path konstruieren
		// erstmal Knoten "r�ckw�rts" in Vector zwischenspeichern
		Vector resultRoute = new Vector();
		Node n = endNode;
		do {
			// Zielknoten zuerst und am Anfang des Vektors eintragen
			resultRoute.add(0, n);
			// Vorgaenger bestimmen
			n = this.nodelist[predecessor[((Integer) this.indices.get(n))
					.intValue()]];
		} while (n != startNode);
		// jetzt noch startNode eintragen (am Anfang der Route)
		resultRoute.add(0, startNode);

		// Vector von Nodes in Path von GraphPositions wandeln
		Path path = new Path();
		for (int i = 0; i < resultRoute.size(); i++) {
			path.addPosition(new GraphPosition(this.graph, (Node) resultRoute
					.get(i)));
		}

		// voila!
		return path;

	} /* end of shortestPathDijkstra() */

}