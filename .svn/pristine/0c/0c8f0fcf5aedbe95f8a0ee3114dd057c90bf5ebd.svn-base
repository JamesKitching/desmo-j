package desmoj.extensions.space2D.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;

import desmoj.extensions.space2D.space.ComparableAttribute;
import desmoj.extensions.space2D.space.Grid2D;
import desmoj.extensions.space2D.space.GridPosition;
import desmoj.extensions.space2D.space.Point;
import desmoj.extensions.space2D.space.Position;

public class Grid2DView extends SpaceView {

	private static final long serialVersionUID = 1L;

	/** the list of polygons building the grid. */
	protected Polygon[] pGrid;

	/** the color coded attributes (if any) */
	protected Color[] colors;

	// // zwecks debugging
	// private Point2D[] sites;

	public Grid2DView(Grid2D grid, int winWidth, int winHeight) {
		this(grid, winWidth, winHeight, null, null, 0);
	}

	public Grid2DView(Grid2D grid, int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses) {
		super(grid, winWidth, winHeight, attr, color, numClasses);
		Point[][] cellShapes = grid.getCellShapes();
		this.pGrid = new Polygon[cellShapes.length];
		for (int i = 0; i < this.pGrid.length; i++) {
			this.pGrid[i] = makePolygon(cellShapes[i]);
		}
		// Color-Map erzeugen (wenn ntig)
		if (attr != null) {
			this.colors = new Color[cellShapes.length];
			HashMap attrDistribution = this.space
					.getAttributeDistribution(attr);
			for (Iterator i = attrDistribution.keySet().iterator(); i.hasNext();) {
				GridPosition pos = (GridPosition) i.next();
				Comparable value = (Comparable) ((ComparableAttribute) attrDistribution
						.get(pos)).getValue();
				int index = grid.getCellIndex(pos.getCell());
				this.colors[index] = this.colourCode.getColour(value);
			}
		}
		// // zwecks debugging: sites zeichnen
		// this.sites = transformSites(grid.getSites());
	}

	protected void handleEvents() {
		super.addMouseListener(new MouseAdapter() {
			/** * inner class ** */
			// one inspector for each cell
			Inspector[] cellInspectors = new Inspector[((Grid2D) space)
					.getNumberOfCells()];

			// event handling for mouse clicks
			public void mouseClicked(MouseEvent event) {
				// System.out.println("Mausklick fžr Gridview an Koordinaten " +
				// event.getX() + ", " + event.getY());
				// -- bestimmen, ob in eine Zelle geklickt wurde
				int cellIndex = checkMouseClick(event.getX(), event.getY());
				if (cellIndex >= 0) {
					if (cellInspectors[cellIndex] == null) {
						// Cell-Inspector erzeugen
						cellInspectors[cellIndex] = new Inspector(
								((Grid2D) space).getCell(cellIndex),
								getContext());
					} else {
						cellInspectors[cellIndex].setVisible(true);
					}
				} else {
					System.out.println("-- ausserhalb Grid geklickt");
				}
			}
		} /* end of inner class */);
	}

	public void attributeChanged(Position where, Comparable value) {
		// neuen Wert in colormap setzen
		GridPosition p = (GridPosition) where;
		Grid2D grid = (Grid2D) this.space;
		this.colors[grid.getCellIndex(p.getCell())] = this.colourCode
				.getColour(value);
	}

	protected void paintSpace(Graphics graphics) {
		Graphics2D g2D = (Graphics2D) graphics;
		for (int i = 0; i < this.pGrid.length; i++) {
			if (colors != null && colors[i] != null) {
				g2D.setColor(colors[i]);
				g2D.fill(pGrid[i]);
				g2D.setColor(Color.black);
			}
			g2D.draw(this.pGrid[i]);
		}
		// // zwecks debugging
		// for (int i = 0; i < this.sites.length; i++) {
		// g2D.drawOval((int)(sites[i].getX()-2), (int)(sites[i].getY()-2), 4,
		// 4);
		// }
	}

	private Polygon makePolygon(Point[] points) {
		// Points nach Point2D transformieren
		Point2D[] vertices = new Point2D[points.length];
		for (int i = 0; i < points.length; i++) {
			vertices[i] = this.modelViewTransformer.transform(points[i]
					.getPoint2D());
		}
		// int Koordinaten machen
		int[] xCoords = new int[points.length];
		int[] yCoords = new int[points.length];
		for (int i = 0; i < points.length; i++) {
			xCoords[i] = (int) vertices[i].getX();
			yCoords[i] = (int) vertices[i].getY();
		}
		return new Polygon(xCoords, yCoords, points.length);
	}

	private Point2D[] transformSites(Point[] points) {
		// Points nach Point2D transformieren
		Point2D[] sites = new Point2D[points.length];
		for (int i = 0; i < points.length; i++) {
			sites[i] = this.modelViewTransformer.transform(points[i]
					.getPoint2D());
		}
		return sites;
	}

	/**
	 * tests if the mouse click with the given coordinates (x, y) occured inside
	 * one of the cell polygons. Returns the index of the belonging grid cell or
	 * -1 if the click happened outside any cell polygon.
	 */
	private int checkMouseClick(double x, double y) {
		// nicht besonders effizient, funktioniert aber erstmal...
		int index = -1;
		boolean inside = false;
		for (int i = 0; i < this.pGrid.length && !inside; i++) {
			inside = this.pGrid[i].contains(x, y);
			if (inside) {
				index = i;
			}
		}
		return index;
	}
}