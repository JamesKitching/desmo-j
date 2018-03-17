package desmoj.extensions.space2D;

import desmoj.extensions.dimensions.Length;
import desmoj.extensions.space2D.space.Position;

/**
 * the interface Situated describes objects with a clearly defined position in
 * space.
 */
public interface Situated {

	/**
	 * returns the current position.
	 */
	public abstract Position getCurrentPosition();

	/**
	 * sets the current position to the specified position.
	 * 
	 * @param position
	 *            the new position this situated object is to take
	 */
	public abstract void setCurrentPosition(Position position);

	/**
	 * returns the sensor range.
	 */
	public abstract Length getSensorRange();

}