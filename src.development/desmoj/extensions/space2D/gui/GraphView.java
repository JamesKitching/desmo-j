package desmoj.extensions.space2D.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;

import desmoj.extensions.space2D.space.ComparableAttribute;
import desmoj.extensions.space2D.space.Edge;
import desmoj.extensions.space2D.space.Graph;
import desmoj.extensions.space2D.space.GraphPosition;
import desmoj.extensions.space2D.space.Node;
import desmoj.extensions.space2D.space.Point;
import desmoj.extensions.space2D.space.Position;

/**
 * a graph view is the graphical representation of a graph, with nodes painted
 * as filled black circles and edges painted as straight lines. As nodes are
 * inspectable the node circles may be clicked with the mouse to produce an
 * inspect dialog.
 */
public class GraphView extends SpaceView {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	private static final long serialVersionUID = 1L;

	/**
	 * the list of graph nodes. Needed for inspection when a node circle is
	 * clicked at.
	 */
	private Node[] nodes;
	
	private Image background;

	/**
	 * for efficiently determining which node was clicked the node points are
	 * additionally stored sorted according to x and y coordinates.
	 * <code>sortedX</code> contains the x coordinates in ascending order
	 * together with a reference to the respective point.
	 */
	private int[][] sortedX; // int ist genau genug

	/**
	 * <code>sortedY</code> contains the y coordinates in ascending order
	 * together with a reference to the respective point.
	 */
	private int[][] sortedY;

	/**
	 * the size (diameter) of the node circles in pixels.
	 */
	private double size;

	/** the node circles as Ellipse2D objects */
	private Ellipse2D[] nodeCircles;

	/** the edge lines as Line2D objects */
	private Line2D[] edgeLines;

	/** the color coded attributes (if any) */
	private Color[] colors;

	/** the default minimal node circle size. */
	private static int MIN_SIZE = 2;

	/** the default maximal node circle size. */
	private static int MAX_SIZE = 8;

	/** the default window size. */
	private static int WIN_SIZE = 300;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a graph view for the given graph. View dimensions and node
	 * sizes are set to default values.
	 * 
	 * @param graph
	 *            the represented graph
	 */
	public GraphView(Graph graph) {
		this(graph, WIN_SIZE, WIN_SIZE, MIN_SIZE, MAX_SIZE, MAX_SIZE);
	}

	/**
	 * constructs a graph view for the given graph with the given view
	 * dimensions. Node sizes are set to default values.
	 * 
	 * @param graph
	 *            the represented graph
	 * @param winWidth
	 *            the window width for this graph view
	 * @param winHeight
	 *            the window height for this graph view
	 */
	public GraphView(Graph graph, int winWidth, int winHeight) {
		this(graph, winWidth, winHeight, MIN_SIZE, MAX_SIZE, MAX_SIZE);
	}

	/**
	 * constructs a graph view for the given graph with the given view
	 * dimensions and node sizes.
	 * 
	 * @param graph
	 *            the represented graph
	 * @param winWidth
	 *            the window width for this graph view
	 * @param winHeight
	 *            the window height for this graph view
	 * @param minNodeSize
	 *            the minimal size (diameter) of the node circles
	 * @param maxNodeSize
	 *            the maximal size (diameter) of the node circles
	 * @param desiredNodeSize
	 *            the desired size of the node circles
	 */
	public GraphView(Graph graph, int winWidth, int winHeight, int minNodeSize,
			int maxNodeSize, int desiredNodeSize) {
		this(graph, winWidth, winHeight, minNodeSize, maxNodeSize,
				desiredNodeSize, null, null, 0);
	}

	/**
	 * constructs a graph view for the given graph with the given view
	 * dimensions and displays the specified attribute.
	 * 
	 * @param graph
	 *            the represented graph
	 * @param winWidth
	 *            the window width for this graph view
	 * @param winHeight
	 *            the window height for this graph view
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public GraphView(Graph graph, int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses) {
		this(graph, winWidth, winHeight, MIN_SIZE, MAX_SIZE, MAX_SIZE, attr,
				color, numClasses);
	}
	
	/**
	 * constructs a graph view for the given graph with the given view
	 * dimensions and node sizes and displays the specified attribute.
	 * 
	 * @param graph
	 *            the represented graph
	 * @param winWidth
	 *            the window width for this graph view
	 * @param winHeight
	 *            the window height for this graph view
	 * @param minNodeSize
	 *            the minimal size (diameter) of the node circles
	 * @param maxNodeSize
	 *            the maximal size (diameter) of the node circles
	 * @param desiredNodeSize
	 *            the desired size of the node circles
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public GraphView(Graph graph, int winWidth, int winHeight, int minNodeSize,
			int maxNodeSize, int desiredNodeSize, ComparableAttribute attr,
			Color color, int numClasses) {
		this(graph, winWidth, winHeight, MIN_SIZE, MAX_SIZE, MAX_SIZE, attr,
				color, numClasses, null);
	}
	
	/**
	 * constructs a graph view for the given graph with the given view
	 * dimensions and node sizes and displays the specified attribute.
	 * 
	 * @param graph
	 *            the represented graph
	 * @param winWidth
	 *            the window width for this graph view
	 * @param winHeight
	 *            the window height for this graph view
	 * @param minNodeSize
	 *            the minimal size (diameter) of the node circles
	 * @param maxNodeSize
	 *            the maximal size (diameter) of the node circles
	 * @param desiredNodeSize
	 *            the desired size of the node circles
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public GraphView(Graph graph, int winWidth, int winHeight,  ComparableAttribute attr,
			Color color, int numClasses, Image background) {
		this(graph, winWidth, winHeight, MIN_SIZE, MAX_SIZE, MAX_SIZE, attr,
				color, numClasses, background);
	}
	
	/**
	 * constructs a graph view for the given graph with the given view
	 * dimensions and node sizes and displays the specified attribute.
	 * 
	 * @param graph
	 *            the represented graph
	 * @param winWidth
	 *            the window width for this graph view
	 * @param winHeight
	 *            the window height for this graph view
	 * @param minNodeSize
	 *            the minimal size (diameter) of the node circles
	 * @param maxNodeSize
	 *            the maximal size (diameter) of the node circles
	 * @param desiredNodeSize
	 *            the desired size of the node circles
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 * @param background
	 * 			  background image to draw
	 */
	public GraphView(Graph graph, int winWidth, int winHeight, int minNodeSize,
			int maxNodeSize, int desiredNodeSize, ComparableAttribute attr,
			Color color, int numClasses, Image background) {
		super(graph, winWidth, winHeight, attr, color, numClasses, background);
		if(background != null)
			this.background = background;
		this.nodes = graph.getNodes();
		// Knotenkreis-Gr��e skalieren
		this.size = this.modelViewTransformer.scale(desiredNodeSize,
				minNodeSize, maxNodeSize);
		System.out.println("scaleFactor = "
				+ modelViewTransformer.getScaleFactor());
		System.out.println("scaled node size: " + this.size);
		// Knoten- und Kantenliste transformieren
		transformGraph(graph);
		// Color-Map erzeugen (wenn n�tig)
		if (attr != null) {
			this.colors = new Color[this.nodes.length];
//			this.colors = new Color[2];
			HashMap attrDistribution = this.space
					.getAttributeDistribution(attr);
			for (Iterator i = attrDistribution.keySet().iterator(); i.hasNext();) {
				GraphPosition pos = (GraphPosition) i.next();
				Comparable value = (Comparable) ((ComparableAttribute) attrDistribution
						.get(pos)).getValue();
				this.colors[getNodeIndex(pos.getNode())] = this.colourCode
						.getColour(value);
			}
		}
	}

	// ///////////// METHODEN ////////////////////////////////////////

	// ---------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * paints the graph view, i.e. paints circles for the nodes and lines for
	 * the edges.
	 */
	protected void paintSpace(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		if(background!=null)
			g2D.drawImage(this.background, 0, 0, null);
		
		// die Kantenliste zeichnen
		for (int i = 0; i < edgeLines.length; i++) {
			g2D.setColor(Color.yellow);
			g2D.draw(edgeLines[i]);
		}
		// die Knotenliste zeichnen
		for (int i = 0; i < nodeCircles.length; i++) {
			if (this.colors != null && this.colors[i] != null) {
				g2D.setColor(this.colors[i]);
				g2D.draw(nodeCircles[i]);
				g2D.fill(nodeCircles[i]);
				g2D.setColor(Color.black);
			} else {
				g2D.draw(nodeCircles[i]);
				g2D.fill(nodeCircles[i]);
			}
		}
	}

	/**
	 * sets a new colour in the colour map for the node the given position
	 * refers to. The given value is mapped to the respective colour by the
	 * internal colour code.
	 */
	public void attributeChanged(Position where, Comparable value) {
		// neuen Wert in colormap setzen
		GraphPosition p = (GraphPosition) where;
		this.colors[getNodeIndex(p.getNode())] = this.colourCode
				.getColour(value);
	}

	/**
	 * provides an event handler for mouse clicks on this view. As nodes are
	 * inspectable, opens an inspect dialog if the click occured within a node
	 * circle.
	 */
	protected void handleEvents() {
		super.addMouseListener(new MouseAdapter() {
			// inner class
			Inspector[] nodeInspectors = new Inspector[((Graph) space)
					.getNodes().length];

			// nodes.length nehmen geht hier nicht, weil nodes noch nicht
			// initialisiert ist,
			// wenn handlemouseclickedevent aufgerufen wird im konstruktor von
			// spaceview
			public void mouseClicked(MouseEvent event) {
				// System.out.println("Mausklick f�r Graphview an Koordinaten "
				// + event.getX() + ", " + event.getY());
				// -- bestimmen, ob in einen Knoten geklickt wurde
				int nodeIndex = checkMouseClick(event.getX(), event.getY());
				if (nodeIndex >= 0) {
					// System.out.println("-- geklickt in Knoten " +
					// nodes[nodeIndex]);
					if (nodeInspectors[nodeIndex] == null) {
						// Node-Inspector erzeugen
						nodeInspectors[nodeIndex] = new Inspector(
								nodes[nodeIndex], getContext());
					} else {
						nodeInspectors[nodeIndex].setVisible(true);
					}
				} else {
					// System.out.println("-- ausserhalb Knoten geklickt");
				}
			}
		});
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	/**
	 * transforms the coordinates of graph nodes and edges to view coordinates,
	 * constructs Ellipse2D and Line2D objects and stores them in the fields
	 * nodeCircles and edgeLines, respectively.
	 */
	private void transformGraph(Graph graph) {
		// Knotenliste transformieren
		Point2D[] nodePoints = new Point2D[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			Point p = (Point) nodes[i].getRefPoint();
			nodePoints[i] = this.modelViewTransformer.transform(p.getPoint2D());
		}
		// Kantenliste transformieren
		// dabei aus Effizienzgr�nden nur 1 Kante zw. je 2 Knoten
		// ber�cksichtigen
		Edge[] edges = graph.getEdges();
		HashMap uniqueEdges = new HashMap();
		for (int i = 0; i < edges.length; i++) {
			int startID = edges[i].getStartNode().getID();
			int endID = edges[i].getEndNode().getID();
			String key = (startID < endID) ? new String(startID + "*" + endID)
					: new String(endID + "*" + startID);
			if (!uniqueEdges.containsKey(key)) {
				Point2D[] ep = new Point2D[2];
				ep[0] = this.modelViewTransformer.transform(edges[i]
						.getStartNode().getRefPoint().getPoint2D());
				ep[1] = this.modelViewTransformer.transform(edges[i]
						.getEndNode().getRefPoint().getPoint2D());
				uniqueEdges.put(key, ep);
			}
		}
		Point2D[][] edgePoints = new Point2D[uniqueEdges.size()][2];
		Iterator u = uniqueEdges.values().iterator();
		for (int i = 0; i < edgePoints.length; i++) {
			edgePoints[i] = (Point2D[]) u.next();
		}
		// Line2D-Objekte erzeugen --> wird effizienter sein, als jedesmal neue
		// Objekte in paintSpace()
		this.edgeLines = new Line2D[edgePoints.length];
		for (int i = 0; i < edgeLines.length; i++) {
			edgeLines[i] = new Line2D.Double(edgePoints[i][0].getX(),
					edgePoints[i][0].getY(), edgePoints[i][1].getX(),
					edgePoints[i][1].getY());
		}
		// Ellipse2D-Objekte erzeugen
		this.nodeCircles = new Ellipse2D[nodePoints.length];
		for (int i = 0; i < nodeCircles.length; i++) {
			nodeCircles[i] = new Ellipse2D.Double(nodePoints[i].getX() - size
					/ 2, nodePoints[i].getY() - size / 2, size, size);
		}

		// zus�tzlich: Knoten-Punkte nach x-y sortieren und aufbewahren
		// f�r schnelleren Zugriff bei Mausklicks
		sortPointCoordinates(nodePoints);
	}

	/**
	 * sorts the node points according to their x and y coordinates to achieve
	 * efficient access while checking for mouse clicks.
	 */
	private void sortPointCoordinates(Point2D[] nodePoints) {
		sortedX = new int[nodePoints.length][2];
		sortedY = new int[nodePoints.length][2];
		// einmal durch nodePoints gehen und erstmal nur in sortedX/Y eintragen
		// sortedX enth�lt die x-Koordinaten, sortedY die y-Koordinaten der
		// Punkte, beide jeweils mit einem entsprechenden Index in nodePoints,
		// also
		// einer Referenz auf den zugeh�rigen Punkt (da nodePoints[i] =
		// nodes[i])
		for (int i = 0; i < nodePoints.length; i++) {
			sortedX[i] = new int[] { (int) nodePoints[i].getX(), i };
			sortedY[i] = new int[] { (int) nodePoints[i].getY(), i };
		}
		// dann sortedX und sorted Y sortieren
		sort(sortedX);
		sort(sortedY);
	}

	/**
	 * helper method: sorts the given int array (algorithm: insert with binary
	 * search)
	 */
	private void sort(int[][] sorted) {
		for (int i = 1; i < sorted.length; i++) {
			int left = 0;
			int right = i;
			int[] temp = sorted[i];
			while (left < right) {
				int middle = (left + right) / 2;
				if (sorted[middle][0] <= temp[0]) {
					left = middle + 1;
				} else {
					right = middle;
				}
			}
			for (int j = i; j > right; j--) {
				sorted[j] = sorted[j - 1];
			}
			sorted[right] = temp;
		}
	}

	/**
	 * tests if the mouse click with the given coordinates (x, y) occured inside
	 * one of the node circles. Returns the index of the belonging node point in
	 * the <code>nodes</code> array or -1 if the click happened outside any
	 * node circle.
	 */
	private int checkMouseClick(double x, double y) {
		// bin�re Suche in sortedX/Y
		// Schritt 1: alle Punkte bestimmen, deren x-Koordinate zum Mausklick
		// pa�t
		int xIndex = binarySearch(x, sortedX);
		IntSet xSet = new IntSet();
		if (xIndex >= 0) {
			while (xIndex < sortedX.length && isInside(x, sortedX[xIndex][0])) {
				xSet.add(sortedX[xIndex][1]);
				xIndex++;
			}
			// Schritt 2: wenn solche vorhanden, alle Punkte bestimmen, deren
			// y-Koordinate zum Mausklick pa�t
			IntSet ySet = new IntSet();
			int yIndex = binarySearch(y, sortedY);
			if (yIndex >= 0) {
				while (yIndex < sortedY.length
						&& isInside(y, sortedY[yIndex][0])) {
					ySet.add(sortedY[yIndex][1]);
					yIndex++;
				}
				// Schritt 3: wenn solche vorhanden sind, Schnittmenge bestimmen
				IntSet xy = xSet.intersect(ySet);
				if (xy.getNumberOfElements() >= 1) {
					// wenn "gemeinsamer" Punkt vorhanden = TREFFER!
					return xy.elementAt(0);
				}
			}
		}
		// kein Knotenpunkt getroffen
		return -1;
	}

	/**
	 * returns true if <code>mouseCoord</code> is inside the interval
	 * <code>[pointCoord - size/2, pointCoord + size/2]<code>, i.e. the
	 *  coordinate of the mouse click is inside the node circle belonging
	 *  to <pointCoord> -- at least for the dimension checked (x or y)
	 */
	private boolean isInside(double mouseCoord, int pointCoord) {
		return (mouseCoord >= pointCoord - size / 2 && mouseCoord <= pointCoord
				+ size / 2);
	}

	/**
	 * does a binary search for <code>mouseCoord</code> in the int array of
	 * point coords. Returns the index of the first matching point coordinate or
	 * -1 if <code>mouseCoord</code> doesn't fall within the confines of any
	 * element of <code>pointCoords</code>.
	 */
	private int binarySearch(double mouseCoord, int[][] pointCoords) {
		int left = 0;
		int right = pointCoords.length;
		while (left < right) {
			int middle = (left + right) / 2;
			if (pointCoords[middle][0] < mouseCoord - size / 2) {
				left = middle + 1;
			} else {
				right = middle;
			}
		}
		if (right < pointCoords.length
				&& isInside(mouseCoord, pointCoords[right][0])) {
			return right;
		}
		return -1;
	}

	/** returns the index for the given node in the array nodes. */
	private int getNodeIndex(Node n) {
		// im array nodes nach n suchen --> da unsortiert, geht nur lineare
		// Suche
		int i = 0;
		while (i < this.nodes.length && this.nodes[i] != n)
			i++;
		return i;
	}
	
	/**
	 * sets the background image
	 * @param background
	 */
	public void setBackground(Image background) {
		this.background = background;
	}

	// /------------------------------------------------------------ innere
	// Klasse

	/**
	 * a set of int. The elements are stored in ascending order thus binary
	 * search is possible to test if an int value is contained in this set. Used
	 * to determine which node was clicked in the checkMouseClick() method.
	 */
	private class IntSet {

		// //////////// Attribute /////////////////////////////////////

		private int increment = 20;

		int[] set = new int[increment];

		int right = 0; // zeigt auf den ersten freien Platz --> entspricht also

		// der anzahl der elemente

		int insertIndex = 0;

		// //////////// Konstruktor ////////////////////////////////////

		/** constructs a new empty int set. */
		IntSet() {
		}

		// //////////// Methoden //////////////////////////////////////

		/** returns the number of elements of this set. */
		int getNumberOfElements() {
			return right;
		}

		/** adds all elements of the given set to this set. */
		void add(IntSet wholeSet) {
			for (int i = 0; i < wholeSet.right; i++) {
				add(wholeSet.set[i]);
			}
		}

		/** adds the given int to this set. */
		void add(int i) {
			if (!contains(i)) {
				if (right >= set.length) {
					expand();
				}
				insert(i);
			}
		}

		/**
		 * returns the element at the given index or Integer.MIN_VALUE if there
		 * is no element with the given index.
		 */
		int elementAt(int index) {
			if (index >= 0 && index < right) {
				return set[index];
			} else {
				return Integer.MIN_VALUE;
			}
		}

		/**
		 * returns <code>true</code> if this set contains the given int, or
		 * <code>false</code> otherwise.
		 */
		boolean contains(int i) {
			// bin�re Suche
			int left = 0;
			int right = this.right;
			while (left < right) {
				insertIndex = (left + right) / 2;
				if (set[insertIndex] < i) {
					left = insertIndex + 1;
				} else {
					right = insertIndex;
				}
			}
			insertIndex = right;
			if (right < this.right && set[right] == i) {
				return true;
			}
			return false;
		}

		/**
		 * returns the intersection of this set with <code>otherSet</code>.
		 * The resulting set may be empty.
		 */
		IntSet intersect(IntSet otherSet) {
			IntSet intersection = new IntSet();
			for (int i = 0; i < right; i++) {
				if (otherSet.contains(set[i])) {
					intersection.add(set[i]);
				}
			}
			return intersection;
		}

		/**
		 * returns the join of this set with <code>otherSet</code>.
		 */
		IntSet join(IntSet otherSet) {
			IntSet joined = new IntSet();
			joined.add(this);
			joined.add(otherSet);
			return joined;
		}

		/** returns a String representation of this set. */
		public String toString() {
			StringBuffer me = new StringBuffer("{ ");
			for (int i = 0; i < right; i++) {
				me.append(set[i]);
				me.append(" ");
			}
			me.append("}");
			return me.toString();
		}

		// ----------------------------------------------------------
		// Hilfsmethoden

		/** helper method: inserts the given int into this set. */
		private void insert(int i) {
			// insertIndex enth�lt bereits die Position, wo eingef�gt werden
			// soll,
			// da immer vor insert() die Methode contains() aufgerufen wird
			// und diese den insertIndex bestimmt
			for (int j = right; j > insertIndex; j--) {
				set[j] = set[j - 1];
			}
			set[insertIndex] = i;
			right++;
		}

		/** helper method: expands the "storage area" of this set. */
		private void expand() {
			int[] temp = new int[set.length + increment];
			for (int i = 0; i < set.length; i++) {
				temp[i] = set[i];
			}
			set = temp;
		}
	} /* end of inner class IntSet */

	// ---------------- zum Testen von Backtracking in CompassStrategy mit
	// SCSTest
	// public void setNodeColor(Node[] nodeList, Color color) {
	// if (this.colors == null) {
	// this.colors = new Color[this.nodes.length];
	// }
	// for (int i = 0; i < nodeList.length; i++) {
	// this.colors[getNodeIndex(nodeList[i])] = color;
	// }
	// }

} /* end of class GraphView */