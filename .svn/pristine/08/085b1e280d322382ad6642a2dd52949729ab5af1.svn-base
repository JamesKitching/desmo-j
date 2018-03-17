package desmoj.extensions.space2D.space;

/**
 * A position for use with n-dimensional grids (famos.space.Grid). Each grid
 * position refers to a certain cell in that grid.
 */
public class GridPosition extends Position {

	// //////////// Attribute /////////////////////////////////////////////

	/** the grid cell this positions is connected to. */
	GridCell gridCell;

	/**
	 * A string build from concatenating the cell indices to compute the hash
	 * code for this grid position.
	 */
	private String hashString = null;

	// //////////// Konstruktoren /////////////////////////////////////////

	/**
	 * constructs a new grid position for the given grid and the given grid
	 * cell.
	 */
	public GridPosition(Grid space, GridCell gridCell) {
		super(space);
		this.gridCell = gridCell;
	}

	/**
	 * constructs a new grid position for the given grid and the grid cell
	 * specified by the given index.
	 */
	public GridPosition(Grid space, int[] index) {
		super(space);
		this.gridCell = space.grid[space.getIndex(index)];
	}

	/**
	 * constructs a new grid position for the given (2D-) grid and the grid cell
	 * specified by the x and y indices. This constructor is provided for
	 * convenience as most grids will be 2D.
	 */
	public GridPosition(Grid space, int x, int y) {
		this(space, new int[] { x, y });
	}

	// //////////// Methoden //////////////////////////////////////////////

	/** returns the grid cell this grid position refers to. */
	public GridCell getCell() {
		return this.gridCell;
	}

	/**
	 * returns the x index of the grid cell this position refers to. This method
	 * is provided for conveniently handling regular 2D grids.
	 */
	public int getX() {
		return this.gridCell.getIndex()[0];
	}

	/**
	 * returns the y index of the grid cell this position refers to. This method
	 * is provided for conveniently handling regular 2D grids.
	 */
	public int getY() {
		int[] index = this.gridCell.getIndex();
		if (index.length >= 2)
			return index[1];
		return 0;
	}

	/** returns a string representation of this grid position. */
	public String toString() {
		return new String("GridPosition: " + this.gridCell.getName());
	}

	// /--------------------------------------- Implementation abstrakter
	// Methoden

	/**
	 * returns the point in space this grid position refers to, i.e. the
	 * "center" point of the grid cell (= the reference point of the dual node).
	 */
	public Point getPoint() {
		return this.gridCell.getRefPoint();
	}

	/** GridPosition objects are equal if they belong to the same grid cell */
	public boolean equals(Object o) {
		return (o instanceof GridPosition)
				&& ((GridPosition) o).gridCell == this.gridCell;
	}

	/**
	 * returns the hash code of this grid position's hash string. The hash
	 * string is build of the cell indices by converting the int values to
	 * String objects, trimming all to length 32 and filling leading spaces with
	 * zeros.
	 */
	public int hashCode() {
		if (this.hashString == null) {
			// indices verketten (als String) und dessen hashCode zuržckliefern
			int[] index = this.gridCell.getIndex();
			StringBuffer s = new StringBuffer(32 * index.length);
			for (int i = 0; i < index.length; i++) {
				String sIndex = Integer.toString(index[i]);
				// jeweils auf feste Lnge (32) mit 0 auffžllen
				char[] zeros = new char[32 - sIndex.length()];
				for (int j = 0; j < zeros.length; j++) {
					zeros[j] = '0';
				}
				s.append(zeros);
				s.append(sIndex);
			}
			this.hashString = s.toString();
		}
		return this.hashString.hashCode();
	}

	/**
	 * adds the given attribute area to this position's areas, i.e. the areas to
	 * which the grid cell this position refers to belongs.
	 */
	protected void addAttributeArea(AttributeArea area) {
		this.gridCell.addAttributeArea(area);
	}

	/**
	 * removes the given attribute area from this position's areas, i.e. the
	 * areas to which the grid cell this position refers to belongs.
	 */
	protected void removeAttributeArea(AttributeArea area) {
		this.gridCell.removeAttributeArea(area);
	}

}