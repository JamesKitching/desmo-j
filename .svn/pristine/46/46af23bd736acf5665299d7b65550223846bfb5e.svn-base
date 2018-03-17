package desmoj.extensions.space2D.space;

import java.awt.Color;

import desmoj.extensions.space2D.gui.Grid2DView;
import desmoj.extensions.space2D.gui.SpaceView;
//import flame.famosExtension.FlatCardinalOrientation;

public abstract class Grid2D extends Grid {

	// -------------------------------------------------------------
	// Konstruktoren

	/** Standard constructor: constructs a new Grid2D with no cells */
	public Grid2D() {
	}

	// ------------------------------------------------------------------
	// Methoden

	// fžr die Visualisierung
	public Point[][] getCellShapes() {
		Point[][] shapes = new Point[this.grid.length][];
		for (int i = 0; i < this.grid.length; i++) {
			shapes[i] = this.grid[i].getVertices();
		}
		return shapes;
	}

	// ---------------------------------------- Implementation abstrakter
	// Methoden

	public SpaceView getSpaceView(int winWidth, int winHeight) {
		return new Grid2DView(this, winWidth, winHeight);
	}

	public SpaceView getSpaceView(int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses) {
		return new Grid2DView(this, winWidth, winHeight, attr, color,
				numClasses);
	}

	// JL
	// Extended on 03.08.2010
	public int getCode(GridPosition gpos) {
		return 0;
	}
	
	public int getCode(GridPosition gpos, int orientation) {
		return 0;
	}
	// JL
	
}