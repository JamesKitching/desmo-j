package desmoj.extensions.space2D.xml;

import desmoj.extensions.space2D.space.Point;

/**
 * A description of a point in two dimensional space. Consists of the x and y
 * coordinates.
 */
public class PointDescription {

	/** the x coordinate */
	private double x;

	/** the y coordinate */
	private double y;

	// ----- the java-bean conform interface: standard constructor and get/set
	// ----- methods

	/** Default constructor */
	public PointDescription() {
	}

	/** returns the x coordinate */
	public double getX() {
		return this.x;
	}

	/** sets the x coordinate */
	public void setX(double x) {
		this.x = x;
	}

	/** returns the y coordinate */
	public double getY() {
		return this.y;
	}

	/** sets the y coordinate */
	public void setY(double y) {
		this.y = y;
	}

	// ------ additional methods

	/**
	 * Returns the point described by this point description.
	 */
	public Point getPoint() {
		return new Point(this.x, this.y);
	}

	/**
	 * Returns a String representation of this point description.
	 */
	public String toString() {
		return "Point (" + x + ", " + y + ")";
	}

	// ----- additional constructor for marshalling

	/**
	 * Constructs a new point description for the given point to be used in
	 * marshalling (= exporting to xml). As point descriptions describe only
	 * two-dimensional points, only the x and y coordinates are stored.
	 */
	public PointDescription(Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}

}