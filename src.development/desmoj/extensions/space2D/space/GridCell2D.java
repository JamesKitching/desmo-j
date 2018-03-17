package desmoj.extensions.space2D.space;

import java.awt.Polygon;

/**
 * A cell in an 2-dimensional grid. The shape of a GridCell2D is a polygon.
 * Therefore, the vertices (points) are used to create a java.awt.Polygon to
 * assist in points in polygon tests. As java.awt.Polygons store their
 * coordinates as int values and famos.space.Points store their coordinates as
 * double values, these doubles have to be transformed to ints (without loosing
 * too much precision).
 * 
 * @author Ruth Meyer
 */
public class GridCell2D extends GridCell {

	// -----------------------------------------------------------------
	// Attribute

	/** the vertices of a two-dimensional grid cell form a polygon. */
	private Polygon polygon;

	/**
	 * the precision applied in transforming double to int values; e.g. if
	 * precision = 10000, then 5 decimal places are taken into account.
	 */
	private int precision;

	// -------------------------------------------------------------
	// Konstruktoren

	/**
	 * Constructs a new grid cell with the given shape. The dual node must be
	 * set later.
	 * 
	 * @param index
	 *            the cell's index into the containing grid
	 * @param vertices
	 *            the list of points defining the cell's shape.
	 */
	public GridCell2D(int[] index, Point[] vertices) {
		this(index, vertices, null);
	}

	/**
	 * Constructs a new grid cell with the given shape and the given dual node.
	 * 
	 * @param index
	 *            the cell's index into the containing grid
	 * @param vertices
	 *            the list of points defining the cell's shape.
	 * @param dual
	 *            the dual graph node.
	 */
	public GridCell2D(int[] index, Point[] vertices, Node dual) {
		super(index, vertices, dual);
		this.precision = checkPrecision(vertices);
		int[] x = new int[vertices.length];
		int[] y = new int[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			x[i] = toPreciseInt(vertices[i].getX());
			y[i] = toPreciseInt(vertices[i].getY());
		}
		this.polygon = new Polygon(x, y, vertices.length);
	}

	// ---------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * Tests whether the given point is contained in the polygon forming this
	 * cell's shape.
	 * 
	 * @param p
	 *            the point to be testet
	 * @return <code>true</code> if p is inside the polygon, otherwise
	 *         <code>false</code>.
	 */
	public boolean contains(Point p) {
		// System.out.println("cell poly = " + polygon.getBounds());
		// System.out.println("shifted p = (" + toPreciseDouble(p.getX()) + ", "
		// + toPreciseDouble(p.getY()) + ")");
		return this.polygon.contains(toPreciseDouble(p.getX()),
				toPreciseDouble(p.getY()));
	}

	public int getPrecision() {
		return this.precision;
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	private int toPreciseInt(double x) {
		return (int) (x * this.precision);
	}

	private double toPreciseDouble(double x) {
		return (x * this.precision);
	}

	private int checkPrecision(Point[] list) {
		// maximale Praezision bestimmen
		double max = Double.MIN_VALUE;
		for (int i = 0; i < list.length; i++) {
			for (int j = 1; j < 3; j++) {
				double coord = list[i].getCoordinate(j);
				if (max < coord)
					max = coord;
			}
		}
		int precision = (int) (Integer.MAX_VALUE / max);
		// richtige Zehnerpotenz bestimmen
		int exponent = 0;
		while (precision >= 10) {
			precision /= 10;
			exponent++;
		}
		precision = (int) Math.pow(10, exponent);
		return precision;
	}
}