package desmoj.extensions.space2D.space;

import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

import desmoj.extensions.dimensions.Length;
//import desmoj.extensions.space2D.agent.SituatedAgent;
import desmoj.extensions.space2D.gui.SpaceView;

public abstract class Grid extends DiscreteSpace {

	// -----------------------------------------------------------------
	// Attribute

	/** the grid as an array of grid cells. */
	protected GridCell[] grid;

	/** the size of the grid, i.e. the number of cells in each dimension */
	protected int[] gridSize;

	/** the dual graph. */
	protected Graph dual;

	// -------------------------------------------------------------
	// Konstruktoren

	/** Standard constructor: constructs a new grid with no cells */
	public Grid() {
		this.grid = new GridCell[0];
		this.gridSize = new int[0];
		this.dual = null;
	}

	// ------------------------------------------------------------------
	// Methoden

	/** returns (a copy of) the grid size. */
	public int[] getGridSize() {
		// Kopie zuržckliefern, damit nderungen keinen Schaden anrichten
		int[] copy = new int[this.gridSize.length];
		for (int i = 0; i < this.gridSize.length; i++) {
			copy[i] = this.gridSize[i];
		}
		return copy;
	}

	/** returns the total number of cells in this grid. */
	public int getNumberOfCells() {
		int size = 1;
		for (int i = 0; i < this.gridSize.length; i++) {
			size *= this.gridSize[i];
		}
		return size;
	}

	/** returns the dual graph. */
	public Graph getDualGraph() {
		return this.dual;
	}

	/** returns the grid cell defined by the given index. */
	public GridCell getCell(int index) {
		if (index >= 0 && index < this.grid.length)
			return this.grid[index];
		return null;
	}

	/** returns the (single value) index of the given grid cell. */
	public int getCellIndex(GridCell cell) {
		return getIndex(cell.getIndex());
	}

	/**
	 * returns the (single value) index of the grid cell specified by the given
	 * n-dimensional index.
	 */
	public int getCellIndex(int[] index) {
		return getIndex(index);
	}

	// --------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * returns the neighbours of the given position according to the
	 * neighbourhood relation as an array of GridPosition objects. This array is
	 * of length 0 if the given position is not a grid position.
	 * 
	 * @param position
	 *            a grid position referring to a grid cell.
	 * @return all direct neighbours of the specified grid cell.
	 */
	public Position[] getNeighbours(Position position) {
		if (!(position instanceof GridPosition))
			return new Position[0];
		// GridPosition in GraphPosition wandeln
		GraphPosition gp = toGraphPosition(position);
		// dann an dualen Graph weiterreichen
		Position[] nb = this.dual.getNeighbours(gp);
		// und wieder zuržckwandeln in GridPosition
		return toGridPosition(nb);
	}

	/**
	 * returns the neighbours of the given position reachable from the specified
	 * cell according to the neighbourhood relation as an array of GridPosition
	 * objects. This array is of length 0 if the given position is not a grid
	 * position.
	 * 
	 * @param position
	 *            a grid position referring to a grid cell.
	 * @return the reachable direct neighbours of the specified grid cell.
	 */
	public Position[] getReachableNeighbours(Position position) {
		if (!(position instanceof GridPosition))
			return new Position[0];
		// GridPosition in GraphPosition wandeln
		GraphPosition gp = toGraphPosition(position);
		// dann an dualen Graph weiterreichen
		Position[] nb = this.dual.getReachableNeighbours(gp);
		// und wieder zuržckwandeln in GridPosition
		return toGridPosition(nb);
	}

	// ### ACHTUNG: diese Methode darf hier nicht die von DiscreteSpace geerbte
	// žberschreiben, wenn RectGrid und HexGrid die gute Performanz behalten
	// sollen, die sie vor Einfžhrung des dualen Graphen hatten
	// --> RectGrid und HexGrid žberschreiben wie gehabt die Methode
	// getNeighbours(),
	// die von getObservableArea() in DiscreteSpace aufgerufen wird

	// public Position[] getObservableArea(Position agentPos, Length range) {
	// System.out.println("Grid.getObservableArea() called for " + agentPos + "
	// with range " + range);
	// if (! (agentPos instanceof GridPosition)) return new Position[0];
	// // GridPosition in GraphPosition wandeln
	// GraphPosition gp = toGraphPosition(agentPos);
	// // dann an dualen Graph weiterreichen
	// Position[] nb = this.dual.getObservableArea(gp, range);
	// // und wieder zuržckwandeln in GridPosition
	// return toGridPosition(nb);
	// }

	/**
	 * returns a position corresponding to the given point.
	 * 
	 * @param p
	 *            the point to be converted to a position associated with this
	 *            grid
	 */
	public Position getPosition(Point p) {
		// nicht effizient, aber einfach: lineare Suche žber die Zellen, ob p
		// in der Zelle enthalten ist
		boolean inside = false;
		int i = 0;
		while (i < this.grid.length && !inside) {
			inside = this.grid[i++].contains(p);
		}
		if (!inside)
			i = 0; // Default: zelle 0 zuržckliefern
		else
			i--; // gefunden: i um 1 zuržcksetzen, da 1 zu weit
		// System.out.println(p + " innerhalb grid? " + inside);
		return new GridPosition(this, this.grid[i]);
	}

	/**
	 * returns the "center" point of the grid cell the given positions refers
	 * to.
	 */
	protected Point getPoint(GridPosition pos) {
		// Refpoint des dualen Knotens der GridCell zuržckliefern
		return pos.getPoint();
	}

	/**
	 * returns the spatial distribution of the specified attribute as a hashmap
	 * with key = GridPosition and value = Attribute. If the attribute is
	 * nowhere to be found in the grid the hashmap will be empty.
	 */
	public HashMap getAttributeDistribution(ComparableAttribute attr) {
		HashMap map = new HashMap();
		// einmal durchs grid laufen und nach dem Attribut suchen
		for (int i = 0; i < this.grid.length; i++) {
			Object value = this.grid[i].getAttributeValue(attr.getName());
			if (value != null && value instanceof Comparable) {
				map.put(new GridPosition(this, this.grid[i]),
						new ComparableAttribute(attr.getName(),
								(Comparable) value));
			}
		}
		return map;
	}

	/** returns the list of all objects currently situated in this grid. */
	public Object[] getObjects() {
		// einmal alle Zellen durchlaufen und die Objekte einsammeln
		Vector objects = new Vector();
		for (int i = 0; i < grid.length; i++) {
			Object[] temp = grid[i].getObjects();
			for (int k = 0; k < temp.length; k++) {
				objects.add(temp[k]);
			}
		}
		return objects.toArray();
	}

	/**
	 * returns the objects residing at the given position in space.
	 */
	protected Object[] getObjects(Position pos) {
		if (pos instanceof GridPosition) {
			return ((GridPosition) pos).getCell().getObjects();
		}
		return new Object[0];
	}

	/**
	 * adds the given object to the specified position in space.
	 */
	protected void addObject(Object obj, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().addObject(obj);
		}
	}

	/**
	 * removes the given object from the specified position in space.
	 */
	protected void removeObject(Object obj, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().removeObject(obj);
		}
	}

	/**
	 * returns the agents currently residing at the given position in space
	 */
//	protected SituatedAgent[] getAgents(Position pos) {
//		if (pos instanceof GridPosition) {
//			return ((GridPosition) pos).getCell().getAgents();
//		}
//		return new SituatedAgent[0];
//	}

	/**
	 * adds the given agent to the specified position in space.
	 * 
	 * @param agent
	 *            the situated agent to be positioned in this grid
	 * @param pos
	 *            the position of the agent
	 */
//	protected void addAgent(SituatedAgent agent, Position pos) {
//		if (pos instanceof GridPosition) {
//			GridPosition agentPos = (GridPosition) pos;
//			agentPos.getCell().addAgent(agent);
//			agent.setCurrentPosition(agentPos);
//		}
//	}

	/**
	 * removes the given agent to the specified position in space.
	 * 
	 * @param agent
	 *            the situated agent to be removed from this grid
	 */
//	protected void removeAgent(SituatedAgent agent) {
//		Position pos = agent.getCurrentPosition();
//		if (pos instanceof GridPosition) {
//			((GridPosition) pos).getCell().removeAgent(agent);
//			agent.setCurrentPosition(null);
//		}
//	}

	/** returns the attributes of the given position in space. */
	protected AttributeList getAttributes(Position pos) {
		if (pos instanceof GridPosition) {
			return ((GridPosition) pos).getCell().getAttributes();
		}
		return new AttributeList();
	}

	/**
	 * adds the given attribute to the specified position in space.
	 */
	protected void addAttribute(Attribute attr, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().addAttribute(attr);
		}
	}

	/**
	 * removes the given attribute from the specified position in space.
	 */
	protected void removeAttribute(Attribute attr, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().removeAttribute(attr.getName());
		}
	}

	/**
	 * sets the value of the attribute defined by the given name at the
	 * specified position in space to the given value.
	 */
	protected void setAttributeValue(String aName, Object aValue, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().setAttribute(aName, aValue);
		}
	}

	/**
	 * returns the local areas (observable areas of situated agents) the given
	 * position -- to be more exact: the grid cell the position refers to -- is
	 * part of. The array is of length 0 if there are no such local areas.
	 */
	protected AttributeArea[] getLocalAreas(Position pos) {
		if (pos instanceof GridPosition) {
			return ((GridPosition) pos).getCell().getLocalAreas();
		}
		return new AttributeArea[0];
	}

	/**
	 * adds the given attribute area to the given position's areas, i.e. the
	 * areas to which the grid cell the position points to belongs.
	 */
	protected void addAttributeArea(AttributeArea area, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().addAttributeArea(area);
		}
	}

	/**
	 * removes the given attribute area from the given position's areas, i.e.
	 * the areas to which the grid cell the position points to belongs.
	 */
	protected void removeAttributeArea(AttributeArea area, Position pos) {
		if (pos instanceof GridPosition) {
			((GridPosition) pos).getCell().removeAttributeArea(area);
		}
	}

	/**
	 * returns the distance between position a and b. Depending on the
	 * neighbourhood relation you may get different results by setting the flag
	 * <code>ignoreDirection</code> to <code>true</code>. Returns
	 * <code>null</code> if the given positions are no grid positions or are
	 * not associated with this grid or if the neighbourhood relation prevents
	 * "reaching" b from a.
	 */
	protected Length getDistance(Position a, Position b, boolean ignoreDirection) {
		// funktioniert nur fžr GridPosition mit diesem Grid
		if (a.getSpace() != this || b.getSpace() != this) {
			// falsches Grid --> null
			return null;
		}
		try {
			GridPosition gpa = (GridPosition) a;
			GridPosition gpb = (GridPosition) b;
			if (gpa.equals(gpb)) {
				// gleiche Position --> Lnge ist 0
				return new Length();
			}
			// Distanzberechnung von dual graph aufrufen
			return this.dual.getDistance(toGraphPosition(gpa),
					toGraphPosition(gpb), ignoreDirection);
		} catch (ClassCastException e) {
			return null;
		}
		// if (! (a instanceof GridPosition && b instanceof GridPosition))
		// return new Length();
		// // GridPosition in GraphPosition wandeln
		// GraphPosition ga = toGraphPosition(a);
		// GraphPosition gb = toGraphPosition(b);
		// // dann an dualen Graph weiterreichen
		// return this.dual.getDistance(ga, gb, ignoreDirection);
	}

	// ----------------------------------- nicht implementierte abstrakte
	// Methoden

	// public abstract double[] getBounds();

	public abstract SpaceView getSpaceView(int winWidth, int winHeight);

	public abstract SpaceView getSpaceView(int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses);

	// ---------------------------------------------------------- interne
	// Methoden

	/**
	 * computes the one-dimensional index of the grid cell specified by the
	 * given n-dimensional index.
	 */
	protected int getIndex(int[] cellIndex) {
		int index = 0;
		int mult = 1;
		for (int i = 0; i < cellIndex.length; i++) {
			mult = (i - 1) >= 0 ? mult * gridSize[i - 1] : mult;
			index += mult * cellIndex[i];
		}
		return index;
	}

	/**
	 * computes the n-dimensional index of the grid cell specified by the given
	 * one-dimensional index.
	 */
	protected int[] getIndex(int cellIndex) {
		// anhand gridSize den index in Einzelteile zerlegen
		// Bsp. 2D-Grid hat gridSize[0] = width, gridSize[1] = height und
		// index = x + width * y
		// --> y = index div width, x = index - (div*y)
		int index = cellIndex;
		int div = 1;
		for (int i = 0; i < gridSize.length; i++) {
			div *= gridSize[i];
		}
		int j = gridSize.length;
		int[] indexParts = new int[gridSize.length];
		for (int i = indexParts.length - 1; i > 0; i--) {
			j--;
			if (j >= 0) {
				div /= gridSize[j];
			}
			indexParts[i] = index / div;
			index -= indexParts[i] * div;
		}
		indexParts[0] = index;
		return indexParts;
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	protected GraphPosition toGraphPosition(Position p) {
		// p ist eine GridPosition
		return new GraphPosition(this.dual, ((GridPosition) p).getCell()
				.getDualNode());
	}

	protected GridPosition toGridPosition(Position p) {
		// p ist eine GraphPosition in den dualen Graphen
		return new GridPosition(this, ((GraphPosition) p).getNode()
				.getDualCell());
	}

	private GridPosition[] toGridPosition(Position[] pList) {
		// pList besteht aus GraphPositions
		GridPosition[] list = new GridPosition[pList.length];
		for (int i = 0; i < pList.length; i++) {
			list[i] = toGridPosition(pList[i]);
		}
		return list;
	}

}