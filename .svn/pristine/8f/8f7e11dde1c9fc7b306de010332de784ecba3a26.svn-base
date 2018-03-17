package desmoj.extensions.space2D.space;

import java.awt.geom.Point2D;

/**
 * This class represents a point in n-dimensional space. Each point keeps a list
 * of its coordinates as an array of double values. For convenience, the first
 * three elements of this list are named x, y and z, respectively. Each point
 * also has a java.awt.geom.Point2D associated with it. This is needed for the
 * visualization of the famos space models. Points may be used as key elements
 * in hashtables or hashmaps, therefore they implement hashCode() and equals().
 */
public class Point {

	// //////////////// Attribute //////////////////////////////////

	/** The default coordinate value is 0.0 */
	public static final double DEFAULT_COORD = 0.0;

	/** The list of coordinates */
	protected double[] coords;

	/**
	 * The associated java.awt.geom.Point2D for visualization. As this is a
	 * two-dimensional point, this n-dimensional point has to be mapped to 2D
	 * space. Currently, this is done by regarding only the x and y coordinates
	 * of this point. If this point has less than two dimensions, there is no
	 * awt point associated with it.
	 */
	private Point2D awtPoint;

	/**
	 * A string build from concatenating the coordinates to compute the hash
	 * code for this point.
	 */
	private String hashString = null;

	// //////////////// Konstruktoren //////////////////////////////

	/**
	 * constructs a new point with the given coordinates.
	 */
	public Point(double[] coords) {
		this.coords = coords;
		if (coords == null) {
			this.coords = new double[0];
		}
		if (this.coords.length >= 2) {
			awtPoint = new Point2D.Double(this.coords[0], this.coords[1]);
		} else {
			awtPoint = null;
		}
	}

	/** constructs a new two-dimensional point with the given coordinates. */
	public Point(double x, double y) {
		this(new double[] { x, y });
	}

	/** constructs a new three-dimensional point with the given coordinates. */
	public Point(double x, double y, double z) {
		this(new double[] { x, y, z });
	}

	/** constructs a new two-dimensional point from the given awt point. */
	public Point(Point2D point) {
		this.awtPoint = point;
		this.coords = new double[] { point.getX(), point.getY() };
	}

	// /////////////// Methoden ////////////////////////////////////

	/**
	 * returns the coordinate value of the specified dimension or the default
	 * coordinate value, if the dimension is invalid.
	 * 
	 * @param dimension
	 *            the dimension the coordinate value is wanted for; to be valid
	 *            a dimension must be >= 1 and <= length of the coordinate list.
	 */
	public double getCoordinate(int dimension) {
		if (dimension > 0 && dimension <= this.coords.length) {
			return this.coords[dimension - 1];
		}
		return DEFAULT_COORD;
	}

	/** returns the x coordinate, i.e. the coordinate value for dimension 1. */
	public double getX() {
		return getCoordinate(1);
	}

	/** returns the y coordinate, i.e. the coordinate value for dimension 2. */
	public double getY() {
		return getCoordinate(2);
	}

	/** returns the z coordinate, i.e. the coordinate value for dimension 3. */
	public double getZ() {
		return getCoordinate(3);
	}

	/**
	 * returns the dimension of this point, i.e. the length of the coordinate
	 * list.
	 */
	public int getDimension() {
		return this.coords.length;
	}

	/**
	 * returns <code>true</code> if this point is a true two-dimensional
	 * point, i.e. if the coordinate list consists of exactly two values.
	 */
	public boolean is2D() {
		return (this.coords.length == 2);
	}

	/**
	 * returns <code>true</code> if this point is a true three-dimensional
	 * point, i.e. if the coordinate list consists of exactly three values.
	 */
	public boolean is3D() {
		return (this.coords.length == 3);
	}

	/**
	 * returns <code>true</code> if this point is a true zero-dimensional
	 * point, i.e. if the coordinate list has no values at all.
	 */
	public boolean isNull() {
		return isNull(this.coords.length);
	}

	/**
	 * returns <code>true</code> if this point is the origin in the specified
	 * dimensional space, i.e. if every coordinate value up to the specified
	 * dimension is 0.0
	 */
	public boolean isNull(int dimension) {
		boolean result = true;
		if (dimension > this.coords.length)
			dimension = this.coords.length;
		for (int i = 0; i < dimension; i++) {
			result &= this.coords[i] == 0.0;
		}
		return result;
	}

	/** returns the associated visualization point (java.awt.geom.Point2D). */
	public Point2D getPoint2D() {
		return this.awtPoint;
	}

	/**
	 * returns <code>true</code> if the given object is identical to this
	 * point (<code>this == that</code> or if the given object is a point (or
	 * java.awt.geom.Point2D) with identical coordinate values in every mutual
	 * dimension.
	 */
	public boolean equals(Object that) {
		// gleiches Objekt?
		if (this == that) {
			return true;
		}
		// nicht gleiches Objekt
		boolean isEqual = true;
		try {
			Point thatPoint = (Point) that;
			// Koordinaten vergleichen
			int mutualDimension = Math.min(this.coords.length, thatPoint
					.getDimension());
			for (int i = 0; i < mutualDimension; i++) {
				isEqual = isEqual
						&& (this.coords[i] == thatPoint.getCoordinate(i + 1));
			}
			return isEqual;
		} catch (ClassCastException e) {
			// that ist kein Point, aber vielleicht ein awt-Point?
			try {
				Point2D thatPoint = (Point2D) that;
				// Koordinaten vergleichen
				if (this.coords.length >= 2) {
					return (getX() == thatPoint.getX())
							&& (getY() == thatPoint.getY());
				} else {
					return false;
				}
			} catch (ClassCastException ex) {
				// that ist auch kein awt-Point
				return false;
			}
		}
	}

	/**
	 * returns the hash code of this point's hash string. The hash string is
	 * build of the x and y coordinates by converting the double values to
	 * String objects, trimming both to length 32 and filling leading spaces
	 * with zeros. Example: the point (23.5, 156.7) has the hash string
	 * 000000000000000000000000000023.5000000000000000000000000000156.7,
	 * resulting in the hash code -945223821.
	 */
	public int hashCode() {
		// ACHTUNG: ber�cksichtigt erstmal nur x und y!
		if (this.hashString == null) {
			// x und y verketten (als String) und dessen hashCode zur�ckliefern
			String sx = Double.toString(getX());
			String sy = Double.toString(getY());
			// jeweils auf feste L�nge (32) mit 0 auff�llen
			StringBuffer s = new StringBuffer(64);
			if (sx.length() > 32)
				sx = sx.substring(0, 31);
			char[] zeros = new char[32 - sx.length()];
			for (int i = 0; i < zeros.length; i++) {
				zeros[i] = '0';
			}
			s.append(zeros);
			s.append(sx);
			if (sy.length() > 32)
				sy = sy.substring(0, 31);
			zeros = new char[32 - sy.length()];
			for (int i = 0; i < zeros.length; i++) {
				zeros[i] = '0';
			}
			s.append(zeros);
			s.append(sy);
			this.hashString = s.toString();
		}
		return this.hashString.hashCode();
	}

	/** returns a string representation of this point. */
	public String toString() {
		StringBuffer text = new StringBuffer("(");
		for (int i = 0; i < this.coords.length; i++) {
			text.append(this.coords[i]);
			text.append(", ");
		}
		text.delete(text.length() - 2, text.length());
		text.append(")");
		return text.toString();
	}

} /* end of class Point */