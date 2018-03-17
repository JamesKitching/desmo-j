package desmoj.extensions.space2D.space;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * An implementation for the orientation where we consider the four cardinals
 * (and their combinations) and set an angle to zero.
 * 
 * @author Jens Lindemann, Sven Kruse
 * @version Version 1.2 16.07.2010
 */

public class FlatCardinalOrientation extends Orientation {
	// -------------------- Attributes --------------------//
	/** the direction constant "north" */
	public static final int N = 1;

	/** the direction constant "east" */
	public static final int E = 2;

	/** the direction constant "south" */
	public static final int S = 4;

	/** the direction constant "west" */
	public static final int W = 8;

	/** the direction constant "northeast" */
	public static final int NE = 16;

	/** the direction constant "southeast" */
	public static final int SE = 32;

	/** the direction constant "southwest" */
	public static final int SW = 64;

	/** the direction constant "northwest" */
	public static final int NW = 128;
	
	private static Hashtable<Integer, FlatCardinalOrientation>  ors = new Hashtable<Integer, FlatCardinalOrientation>();

	/** The images (icons) for the different orientations */
	private ImageIcon orientedNorth;
	private ImageIcon orientedSouth;
	private ImageIcon orientedEast;
	private ImageIcon orientedWest;

	private ImageIcon orientedNorthEast;
	private ImageIcon orientedSouthEast;
	private ImageIcon orientedNorthWest;
	private ImageIcon orientedSouthWest;
	
	// -------------------- Constructors --------------------//	
	/**
	 * Building a flat cardinal direction. Angle is set to zero and cannot be changed.
	 */	
	public FlatCardinalOrientation(double direction) {
		_angle = 0;
		_direction = direction;
		
		try {
			orientedNorth = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedNorth.jpg")));
			orientedSouth = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedSouth.jpg")));
			orientedEast = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedEast.jpg")));
			orientedWest = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedWest.jpg")));
			orientedNorthEast = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedNorthEast.jpg")));
			orientedNorthWest = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedNorthWest.jpg")));
			orientedSouthEast = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedSouthEast.jpg")));
			orientedSouthWest = new ImageIcon(ImageIO.read(new File("C:/Users/Kruse/Desktop/workspace/data/OrientedSouthWest.jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Default Constructor: heading north and with a flat angle of zero
	 */
	public FlatCardinalOrientation() {
		this(new Double(FlatCardinalOrientation.N));
	}
	
	// -------------------- Methods --------------------//	
	/**
	 *  Returning the direction directly as Integer value
	 */
	public int getIntDirection() {
		return (int)_direction;
	}
	
	/**
	 * TODO: Comment properly
	 */
	public int hashCode() {
		return getIntDirection();
	}

	/**
	 * TODO: Comment properly
	 */
	public boolean equals(Object o) {
		if(o instanceof FlatCardinalOrientation) {
			return (this.getIntDirection() == ((FlatCardinalOrientation)o).getIntDirection());
		} else return false;
	}
	
	/**
	 * Returns a FlatCardinalOrientation for the given direction.
	 * @param direction coded direction
	 * @return FlatCardinalOrientation for the given direction
	 */
	public static FlatCardinalOrientation getFlatCardinalOrientation(int direction) {
		Integer directionInt = new Integer(direction);
		if(!ors.containsKey(directionInt)) {
			FlatCardinalOrientation or = new FlatCardinalOrientation(directionInt.doubleValue());
			ors.put(directionInt, or);
			System.out.println("Creating FlatCardinalOrientation: " + direction);
		}
		
		return ors.get(directionInt);
	}

	// -------------------- Implemented Methods from Interface Orientation --------------------//
	/**
	 * Overriding the setAngle method because this is a flat orientation,
	 * therefore the values remains zero.
	 */
	public void setAngle(double angle) {
		// Nada.
	}
	
	/**
	 * Accessing the images according to a direction
	 */
	public ImageIcon getImageIconForOrientation(double direction) {
		if (((Double) direction).intValue() == N) {
			return orientedNorth;
		} else if (((Double) direction).intValue() == S) {
			return orientedSouth;
		} else if (((Double) direction).intValue() == W) {
			return orientedWest;
		} else if (((Double) direction).intValue() == E) {
			return orientedEast;
		} else if (((Double) direction).intValue() == NE) {
			return orientedNorthEast;
		} else if (((Double) direction).intValue() == NW) {
			return orientedNorthWest;
		} else if (((Double) direction).intValue() == SE) {
			return orientedSouthEast;
		} else if (((Double) direction).intValue() == SW) {
			return orientedSouthWest;
		} else {
			return null;
		}
	}
}