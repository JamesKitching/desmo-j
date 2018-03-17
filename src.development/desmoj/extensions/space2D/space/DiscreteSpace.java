package desmoj.extensions.space2D.space;

import java.util.Iterator;
import java.util.Vector;

import desmoj.extensions.dimensions.Length;

/**
 * This abstract class models a discrete space. A discrete space is a space
 * consisting of discrete space elements with a characteristic neighbourhood
 * relation. This relation may be directed, i.e. that two space elements can be
 * neighbours but you can reach only one element from the other and not vice
 * versa. An example is a directed graph, where edges form the neighbourhood
 * relation for the nodes. An edge between node A and node B means A is a
 * neighbour of B and B is a neighbour of A. But if the edge is directed from A
 * to B you can't reach A from B via that edge. Positions are associated with
 * discrete space elements. Discrete space adds to the functionality of space:
 * you can get the (reachable) neighbours for a given position.
 * 
 */
public abstract class DiscreteSpace extends Space {
	// ///////////// Attribute ///////////////////////////////////

	// ///////////// Methoden ////////////////////////////////////

	// -------------------------------------------------------- abstrakte
	// Methoden

	/**
	 * returns the neighbours of the given position as an array of Position
	 * objects.
	 */
	public abstract Position[] getNeighbours(Position position);

	/**
	 * returns the reachable neighbours of the given position as an array of
	 * Position objects. The result of this method may differ from the result of
	 * the getNeighbours() method if the neighbourhood relation of this discrete
	 * space is directed.
	 */
	public abstract Position[] getReachableNeighbours(Position position);

	/**
	 * returns the distance between position a and b. In a directed discrete
	 * space this may differ from the distance between b and a. So by setting
	 * the flag <code>ignoreDirection</code> to <code>true</code> you may
	 * influence the way this distance is computed; you will always get the
	 * smallest possible distance.
	 */
	protected abstract Length getDistance(Position a, Position b,
			boolean ignoreDirection);

	// ---------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * returns the length of the shortest path between position a and b or
	 * <code>null</code> if there is no such path in this space.
	 */
	public Length getDistance(Position a, Position b) {
		return getDistance(a, b, false);
	}

	/**
	 * returns the area observable for an agent with the given position and
	 * sensor range. An area is defined by a list of positions. Returns
	 * <code>null</code> if the space associated with the given position is
	 * not discrete.
	 * 
	 * @param agentPos
	 *            the agent's position; forms the center of the observable area
	 * @param range
	 *            the agent's sensor range
	 */
	public Position[] getObservableArea(Position agentPos, Length range) {
		// System.out.println("DiscreteSpace.getObservableArea() called for " +
		// agentPos + " with range " + range);
		// Geht nur f�r DiscreteSpace
		if (!(agentPos.getSpace() instanceof DiscreteSpace))
			return null;

		// observable area aufbauen
		Vector adjacent = new Vector();
		Vector temp = new Vector();
		Vector n = new Vector();
		adjacent.add(agentPos);
		addNeighbours(n, agentPos, adjacent);
		temp = findAdjacents(n, agentPos, range);
		while (temp != null && temp.size() > 0) {
			// print(n, "neig");
			// print(temp, "temp");
			// alle gerade bestimmten wahrnehmbaren Positionen aus temp in
			// adjacent �bertragen
			adjacent.addAll(temp);
			// print(adjacent, "adja");
			// f�r alle Positionen in temp die Nachbarn bestimmen und in n
			// eintragen
			// (dabei die doppelten auslassen und die bereits in vorigen
			// Schritten bestimmten)
			n.removeAllElements();
			for (Iterator i = temp.iterator(); i.hasNext();) {
				addNeighbours(n, (Position) i.next(), adjacent);
			}
			// aus allen Nachbarn die wahrnehmbaren bestimmen
			temp = findAdjacents(n, agentPos, range);
		}
		// nun stehen in adjacent alle wahrnehmbaren Positionen
		Position[] observable = new Position[adjacent.size()];
		adjacent.copyInto(observable);
		return observable;
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	/**
	 * adds the neighbours of the given position to the given vector n (if they
	 * are not already contained in it or in the previously found positions).
	 * This helper method is called from getObservableArea().
	 */
	private void addNeighbours(Vector n, Position pos, Vector previous) {
		Position[] neighbours = getNeighbours(pos);
		for (int i = 0; i < neighbours.length; i++) {
			if (!n.contains(neighbours[i]) && !previous.contains(neighbours[i])) {
				n.add(neighbours[i]);
			}
		}
	}

	/**
	 * selects the positions in vector v lying in the observable area (defined
	 * by pos as the centre and range as the radius of a circle). The
	 * getDistance() method used to determine the distance between a position in
	 * v and the circle center is defined in the specialized discrete space
	 * classes (Graph, RectangularGrid, HexagonalGrid). This helper method is
	 * called from getObservableArea().
	 */
	private Vector findAdjacents(Vector v, Position pos, Length range) {
		Vector adj = new Vector();
		for (Iterator i = v.iterator(); i.hasNext();) {
			Position p = (Position) i.next();
			if (getDistance(pos, p, true).compareTo(range) <= 0) {
				// p ist innerhalb range
				adj.add(p);
			}
		}
		return adj;
	}

	// --------------------------------------------------------------------------

	/**
	 * helper method for debugging purposes: prints the contents of the vector v
	 * preceded by the given name.
	 */
	private void print(Vector v, String name) {
		System.out.print(name + "(" + v.size() + "): ");
		for (Iterator i = v.iterator(); i.hasNext();) {
			System.out.print(i.next() + " ");
		}
		System.out.println("");
	}
	
	public void test() {
	}

}
