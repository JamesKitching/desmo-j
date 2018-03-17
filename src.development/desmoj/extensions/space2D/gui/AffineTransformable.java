package desmoj.extensions.space2D.gui;

/**
 * This interface indicates that affine transformations can be applied to the
 * implementing class. (This may be necessary for graphical observers.)
 * 
 * @todo besser formulieren
 */
public interface AffineTransformable {

	/**
	 * constant for 2D to define the x-position of the origin (0,0) for an
	 * affine transformable object to be on the left side (of the screen).
	 */
	public final static int LEFT = 1;

	/**
	 * constant for 2D to define the x-position of the origin (0,0) for an
	 * affine transformable object to be on the right side.
	 */
	public final static int RIGHT = -1;

	/**
	 * constant for 2D to define the y-position of the origin (0,0) for an
	 * affine transformable object to be at the top (of the screen).
	 */
	public final static int TOP = 1;

	/**
	 * constant for 2D to define the y-position of the origin (0,0) for an
	 * affine transformable object to be at the bottom (of the screen).
	 */
	public final static int BOTTOM = -1;

	/**
	 * returns the bounds of an affine transformable object as a (minimum)
	 * bounding rectangle. Two points define this rectangle, consisting of the
	 * minimal and the maximal coordinate values, respectively. For each
	 * dimension the returned array lists first the minimal values and then the
	 * maximal values. (min_x, min_y) ------------ (max_x, min_y) | | | | | | | |
	 * (min_x, max_y) ------------ (max_x, max_y)
	 * 
	 * @return the minimal and maximal coordinate values as an array
	 */
	public double[] getBounds();

	/**
	 * returns the position of the origin as an array of int. In the standard 2D
	 * space, two of the above constants should be used, indicating the position
	 * in x and y dimension, respectively. Example: {LEFT, TOP} defines the
	 * origin to be in the upper left corner, meaning x increases to the right,
	 * y increases downwards -- this is the coordinate system used in java awt.
	 * (0,0) -------> x | | | \/ y
	 */
	public int[] getOrigin();
}