package desmoj.extensions.space2D.space;


/**
 * A position for use with continuous space. Each point position refers directly
 * to a certain point in that space.
 */
public class PointPosition extends Position {

	// //////////// Attribute /////////////////////////////////////////////

	/** the point in continuous space this position is connected to. */
	protected Point point;

	// //////////// Konstruktoren /////////////////////////////////////////

	/**
	 * constructs a new point position for the given point in the given
	 * continuous space.
	 */
	public PointPosition(ContinuousSpace space, Point point) {
		super(space);
		this.point = point;
	}

	// //////////// Methoden //////////////////////////////////////////////

	/** returns a string representation of this point position. */
	public String toString() {
		return new String("PointPosition " + this.point);
	}

	// ---------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * PointPosition are equal, if they refer to equal points.
	 */
	public boolean equals(Object that) {
		return ((that instanceof PointPosition) && ((PointPosition) that)
				.getPoint().equals(this.point));
	}

	/** returns the hash code of this position's point. */
	public int hashCode() {
		return this.point.hashCode();
	}

	/** returns this position's point. */
	public Point getPoint() {
		return this.point;
	}

	/**
	 * adds an attribute area. NOT YET IMPLEMENTED.
	 */
	protected void addAttributeArea(AttributeArea area) {
		/** @todo: implement this famos.space.Position abstract method */
	}

	/**
	 * removes an attribute area. NOT YET IMPLEMENTED.
	 */
	protected void removeAttributeArea(AttributeArea area) {
		/** @todo: implement this famos.space.Position abstract method */
	}

}