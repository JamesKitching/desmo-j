package desmoj.extensions.space2D.space;

/**
 * An abstract position in a certain space. Each position represents a possible
 * residence for situated objects and agents in that space. As space models
 * differ from each other, so do the specialized position classes referring to
 * them. In discrete space e.g. a position is associated with a discrete space
 * element. The space "knows" how to convert its positions to points and vice
 * versa.
 */
public abstract class Position {
	// //////////// Attribute /////////////////////////////////////////////

	/** reference to the space */
	protected Space space;

	// //////////// Konstruktoren /////////////////////////////////////////

	/** constructs a new position for the given space. */
	public Position(Space space) {
		this.space = space;
	}

	// //////////// Methoden //////////////////////////////////////////////

	/**
	 * returns the space this position refers to.
	 */
	public Space getSpace() {
		return this.space;
	}

	/**
	 * returns the attributes for this position, i.e. the attributes of the
	 * space element or point in space this position is associated to. This
	 * method is needed for the GradientTrace class.
	 */
	protected AttributeList getAttributes() {
		return this.space.getAttributes(this);
	}

	// -------------------------------------------------------- abstrakte
	// Methoden

	/**
	 * returns a point in space this position refers to. Needed for displayable
	 * objects.
	 */
	public abstract Point getPoint();

	/**
	 * returns <code>true</code> if the given object (position) equals this
	 * position.
	 */
	public abstract boolean equals(Object that);

	/**
	 * returns a hash code for this position. This method (along with the
	 * equals() method) is required for allowing positions to be used as key
	 * elements in hash tables and hash maps.
	 */
	public abstract int hashCode();

	/**
	 * adds the given attribute area to the space element this position is
	 * associated with.
	 */
	protected abstract void addAttributeArea(AttributeArea area);

	/**
	 * removes the given attribute area from the space element this position is
	 * associated with.
	 */
	protected abstract void removeAttributeArea(AttributeArea area);
}
