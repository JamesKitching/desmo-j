package desmoj.extensions.space2D.space;

import javax.swing.ImageIcon;
/**
* The orientation is for situated agents (and mobile agents) to give them
* a heading. 
* We decided to use a (more or less) polar coordinate approach 
* to represent the orientation by an angle and a direction (both represented 
* as double values)
* 
* @author Jens Lindemann, Sven Kruse
* @version Version 1.4 25.06.2010
*/

public abstract class Orientation {
	// -------------------- Attributes --------------------//
	/** Double variable represent the angle */
	protected double _angle;
	
	/** Double variable represent the direction */
	protected double _direction;

	// -------------------- Methods --------------------// 
	/** Access the angle */
	public double getAngle() {
		return _angle;
	}
    /** Change the angle */
	public void setAngle(double angle) {
		_angle = angle;
	}
	
	/** Access the Direction */
	public double getDirection() {
		return _direction;
	}
	
	/** Change the direction */
	public void setDirection(double direction) {
		_direction = direction;
	}
	
	// -------------------- Abstract Methods --------------------//
	/** Get an Image (Icon) to represent the orientation */
	public abstract ImageIcon getImageIconForOrientation(double direction);
	
}