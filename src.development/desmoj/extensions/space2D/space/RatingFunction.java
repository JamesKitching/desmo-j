package desmoj.extensions.space2D.space;

/**
 * A rating function computes one double value from a given set of attribute
 * values. It may need no, one, some or all attribute values to compute the
 * rating -- depending on the application specific context. In Famos, rating
 * functions are used in finding paths in a (discrete) space: Each path strategy
 * takes a rating function to choose between a number of possible next positions
 * by rating attributes related to these positions. That are e.g. each
 * position's own attributes or -- in case of a graph -- the attributes of the
 * edge leading to a possible next node.
 * 
 * @see famos.space.PathStrategy
 */
public interface RatingFunction {
	/**
	 * rates the given attributes according to this function's definition, i.e.
	 * computes a double value from the input attribute values. May use no, one,
	 * some or all attribute values to compute the rating.
	 * 
	 * @param attributes
	 *            the attribute list of a position
	 * @return the rating as a double value
	 */
	public abstract double rate(AttributeList attributes);
}
