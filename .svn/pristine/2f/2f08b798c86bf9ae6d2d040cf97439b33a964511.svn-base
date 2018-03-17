package desmoj.extensions.space2D.space;


/**
 * This abstract class models a two-dimensional grid with regular cells. Grid
 * positions are associated with cells. The origin of a 2D grid's coordinate
 * system is located at the upper left corner; analogous to the cell with
 * indices [0][0]. Thus both point coordinates and cell indices grow to the
 * bottom (x) and right (y).
 */
public abstract class RegularGrid2D extends Grid2D {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/**
	 * the size of a grid cell given as a double value in x and y. These values
	 * are interpreted as having length unit "m".
	 */
	protected double[] cellSize = new double[2];

	/** flag indicating if the grid wraps around in x and y dimension */
	protected boolean[] wrap = new boolean[2];

	/**
	 * used to indicate that a neighbouring cell would be outside the grid;
	 * needed only for non-wrapping grids.
	 */
	protected static final int OUTSIDE = Integer.MIN_VALUE;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new RegularGrid2D with the given grid dimensions (size). All
	 * other attributes are set to default values: a cell size of 1.0 x 1.0, no
	 * wrap in either x and y, reference point is at (1.0, 1.0).
	 */
	public RegularGrid2D(int width, int height) {
		this(width, height, 1.0, 1.0, false, false, new Point(1.0, 1.0));
	}

	/**
	 * constructs a new RegularGrid2D with the given grid dimensions (size),
	 * cell size, wrap settings and reference point.
	 */
	public RegularGrid2D(int width, int height, double cellSizeX,
			double cellSizeY, boolean wrapX, boolean wrapY, Point refPoint) {
		// Grid erzeugen
		this.gridSize = new int[] { width, height };
		this.cellSize = new double[] { cellSizeX, cellSizeY };
		this.wrap = new boolean[] { wrapX, wrapY };
		this.grid = new GridCell2D[width * height];
		int[] index2D = new int[2];
		for (int i = 0; i < width; i++) {
			index2D[0] = i;
			for (int j = 0; j < height; j++) {
				index2D[1] = j;
				int index = getIndex(index2D);
				this.grid[index] = new GridCell2D(index2D, constructCellShape(
						index2D, cellSizeX, cellSizeY, refPoint));
			}
		}
		// fehlt noch: dual graph... --> erst in konkreten Klassen?
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/** returns (a copy of) the cell size. */
	public double[] getCellSize() {
		// Kopie zuržckliefern, damit nderungen keinen Schaden anrichten
		double[] copy = new double[this.cellSize.length];
		for (int i = 0; i < this.cellSize.length; i++) {
			copy[i] = this.cellSize[i];
		}
		return copy;
	}

	/** returns the grid cell defined by the given x and y indices. */
	public GridCell getCell(int x, int y) {
		return this.grid[getIndex(new int[] { x, y })];
	}

	/** changes the wrap settings to the given wrap values. */
	public void setWrap(boolean wrapX, boolean wrapY) {
		// ndert sich wirklich was?
		if (wrapX != this.wrap[0]) {
			this.wrap[0] = wrapX;
			// dann dualen Graph anpassen
			if (wrapX)
				addEdges(0);
			else
				removeEdges(0);
		}
		if (wrapY != this.wrap[1]) {
			this.wrap[1] = wrapY;
			// dualen Graph anpassen
			if (wrapY)
				addEdges(1);
			else
				removeEdges(1);
		}
	}

	// ------ zwecks Debugging
	/**
	 * returns the center point of the specified grid cell. Only used during
	 * debugging in HexagonalGridView.
	 */
	public Point getCellCenter(int x, int y) {
		return getCell(x, y).getRefPoint();
	}

	protected abstract Point[] constructCellShape(int[] cellIndex,
			double cellSizeX, double cellSizeY, Point refPoint);

	protected abstract void addEdges(int dimension);

	protected abstract void removeEdges(int dimension);
}