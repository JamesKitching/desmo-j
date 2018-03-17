package desmoj.extensions.space2D.space;

/**
 * A path strategy is an algorithm to compute a (directed) path from one
 * position to another position in a certain space. According to the "strategy"
 * design pattern...
 * 
 */
public interface PathStrategy {
	/**
	 * Finds a path between the start position <code>from</code> and the end
	 * position <code>to</code> in a (discrete) space. A path is a sequence of
	 * positions where the n-th position is directly reachable from position n-1
	 * and position n+1 is in turn directly reachable from position n. When
	 * there are several possible next positions to take from a given position,
	 * a rating function may be applied to find the best next position. The
	 * definition of "best" position may depend on the application specific
	 * context. In some cases, the GREATEST rating value will denote the best
	 * choice, in other cases -- as in finding the shortest path in a directed
	 * graph -- the SMALLEST rating value (here: edge length) denotes the best
	 * choice. Every path strategy should clearly specify how the "best next
	 * position" is determined.
	 * 
	 * @param from
	 *            the position to start from
	 * @param to
	 *            the position to find a path to
	 * @param ratingFunction
	 *            the rating function used to decide between more than one
	 *            possible next positions
	 * @return a path between <code>from</code> and <code>to</code> or
	 *         <code>null</code> if no path exists in the space the given
	 *         positions point to
	 */
	public abstract Path findPath(Position from, Position to);

	/**
	 * returns the rating function currently used to decide between more than
	 * one possible next positions when finding a path.
	 */
	public abstract RatingFunction getRatingFunction();

	/**
	 * sets the rating function used to decide between more than one possible
	 * next positions when finding a path.
	 */
	public abstract void setRatingFunction(RatingFunction ratingFunction);
}
