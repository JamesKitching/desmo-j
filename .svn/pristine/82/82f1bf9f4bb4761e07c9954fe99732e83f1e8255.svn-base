package desmoj.extensions.space2D.space;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import desmoj.extensions.dimensions.Length;

/**
 * An generic path in space as a sequence of positions.
 * 
 * @author Ruth Meyer
 */
public class Path {

	// ////////// Attribute //////////////////////////////////////////////

	/** the list of positions forming the path. */
	protected Vector elements = new Vector();

	// //////////// Konstruktoren //////////////////////////////////////////

	/** Default constructor. */
	public Path() {
	}

	/**
	 * Copy constructor.
	 * 
	 * @param p
	 *            the path to be copied
	 */
	public Path(Path p) {
		if (p != null) {
			for (int i = 0; i < p.size(); i++) {
				addPosition(p.getPosition(i));
			}
		}
	}

	// //////////// Methoden //////////////////////////////////////////////

	/**
	 * Adds a new position to the end of this path.
	 * 
	 * @param p
	 *            the position to be added
	 */
	public void addPosition(Position p) {
		this.elements.add(p);
	}

	/**
	 * Inserts the given position at the specified place in the path.
	 * 
	 * @param p
	 *            the position to be inserted
	 * @param i
	 *            index where to insert
	 */
	public void insertPosition(Position p, int i) {
		this.elements.insertElementAt(p, i);
	}

	/**
	 * Returns the path as an array of position objects.
	 * 
	 * @return this path as an array
	 */
	public Position[] getPath() {
		Position[] currentPath = new Position[this.elements.size()];
		this.elements.copyInto(currentPath);
		return currentPath;
	}

	/**
	 * Returns the number of positions in this path.
	 * 
	 * @return this path's size
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * Removes the i-th element of this path, i.e. the position with index i. If
	 * the given index is invalid (i < 0 or i > size()-1) the path remains
	 * unchanged.
	 * 
	 * @param i
	 *            the index of the position to be removed from this path
	 */
	public void removePosition(int i) {
		try {
			this.elements.remove(i);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	/**
	 * Removes the given position from the path. If this position is contained
	 * more than once in this path, only the first occurrence of the position
	 * will be removed.
	 * 
	 * @param p
	 *            the position to be removed from this path
	 */
	public void removePosition(Position p) {
		this.elements.remove(p);
	}

	/**
	 * Checks if the given position is part of this path.
	 * 
	 * @param p
	 *            the position to be testet
	 * @return <code>true</code> if the path contains the given position;
	 *         otherwise <code>false</code>.
	 */
	public boolean containsPosition(Position p) {
		return this.elements.contains(p);
	}

	/**
	 * Checks if the given position is part of the "sub-path" starting at the
	 * given index.
	 * 
	 * @param p
	 *            the position to be testet
	 * @param i
	 *            the index to start the sub-path
	 * @return <code>true</code> if the path contains the given position at a
	 *         place with index >= i. Otherwise returns <code>false</code>.
	 */
	public boolean containsPosition(Position p, int i) {
		int index = this.elements.indexOf(p, i);
		return (index >= i);
	}

	/**
	 * Returns the index of the first occurrence of the given position in this
	 * path. If the position is not contained in this path, returns -1.
	 * 
	 * @param p
	 *            the position to be testet
	 * @return that position's index in this path or -1 if this path does not
	 *         contain <code>p</code>
	 */
	public int indexOf(Position p) {
		return this.elements.indexOf(p);
	}

	/**
	 * Returns the position with the specified index. Returns <code>null</code>
	 * if the index is invalid (i < 0 or i >= size()).
	 * 
	 * @param i
	 *            the index of the position
	 * @return the position at the specified index or <code>null</code> if the
	 *         index is invalid
	 */
	public Position getPosition(int i) {
		Position pos = null;
		try {
			pos = (Position) this.elements.get(i);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return pos;
	}

	/**
	 * Returns the total length of this path, i.e. the sum of the distances
	 * between every pair of successive positions.
	 * 
	 * @return this path's length
	 */
	public Length getLength() {
		return this.getLength(size() - 1);
	}

	/**
	 * Returns the length of this path from the start position (index = 0) to
	 * the position with the given index.
	 * 
	 * @param index
	 *            the index to end the sub-path
	 * @return the length of the sub-path ending at the position with the given
	 *         index
	 */
	public Length getLength(int index) {
		if (index < 0 || index >= size()) {
			// empty path or invalid index
			return new Length();
		}
		Length length = new Length();
		Position p = getPosition(0);
		Space space = p.getSpace();
		for (int i = 1; i <= index; i++) {
			Position next = getPosition(i);
			length = length.add(space.getDistance(p, next));
			p = next;
		}
		return length;
	}

	/**
	 * Returns a new path consisting of the specified sub path.
	 * 
	 * @param start
	 *            index of the start position
	 * @param end
	 *            index of the end position
	 * @return the sub path between start and end position (included) as a new
	 *         path object
	 */
	public Path subPath(int start, int end) {
		List subList = this.elements.subList(start, end);
		Path subPath = new Path();
		for (Iterator i = subList.iterator(); i.hasNext();) {
			subPath.addPosition((Position) i.next());
		}
		return subPath;
	}

	/**
	 * Returns a string representation of the path.
	 * 
	 * @return a String consisting of this path's size and a list of its
	 *         elements
	 */
	public String toString() {
		return "Path of length " + elements.size() + ": " + elements;
	}

} /* end of class Path */
