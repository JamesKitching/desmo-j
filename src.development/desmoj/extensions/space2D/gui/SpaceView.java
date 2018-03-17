package desmoj.extensions.space2D.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import desmoj.extensions.experimentation.ui.GraphicalObserverContext;
import desmoj.extensions.space2D.space.ComparableAttribute;
import desmoj.extensions.space2D.space.Position;
import desmoj.extensions.space2D.space.Space;

/**
 * a space view is the graphical representation of a (model) space. It paints
 * the space on a JPanel. It constructs a model-view transformer out of
 * information about the model space (origin, bounds) and the screen "space"
 * (window width and height). This transformer will be used by all other view
 * elements of the environment view (e.g. agent views) -- thus the space
 * dictates how to convert between model and view coordinate system.
 * 
 * @author Ruth Meyer
 */
public abstract class SpaceView extends JBufferedPanel {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the represented model space. */
	protected Space space;

	/** the model-view transformer. */
	protected ModelViewTransformer modelViewTransformer;

	/** this view's width */
	private int width;

	/** this view's height */
	private int height;

	/** the view margin left blank. */
	private static int margin = 20;

	/** the graphical observer context in which this view is embedded */
	private GraphicalObserverContext context;

	/** the comparable attribute to be displayed on this space view */
	protected ComparableAttribute attr;

	/** the colour code applied to the attribute's values */
	protected ColourCoding colourCode;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a space view for the given space. Instantiates the model-view
	 * transformer to be used in the parent environment view.
	 * 
	 * @param space
	 *            the model space to be displayed by this space view
	 * @param winWidth
	 *            the window width for this space view
	 * @param winHeight
	 *            the window height for this space view
	 */
	public SpaceView(Space space, int winWidth, int winHeight) {
		this(space, winWidth, winHeight, null, null, 0);
	}

	/**
	 * constructs a space view for the given space. Instantiates the model-view
	 * transformer to be used in the parent environment view.
	 * 
	 * @param space
	 *            the model space to be displayed by this space view
	 * @param winWidth
	 *            the window width for this space view
	 * @param winHeight
	 *            the window height for this space view
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public SpaceView(Space space, int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses) {
		this(space, winWidth, winHeight, null, null, 0, null);
	}
	
	/**
	 * constructs a space view for the given space. Instantiates the model-view
	 * transformer to be used in the parent environment view.
	 * 
	 * @param space
	 *            the model space to be displayed by this space view
	 * @param winWidth
	 *            the window width for this space view
	 * @param winHeight
	 *            the window height for this space view
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public SpaceView(Space space, int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses, Image backgroundImage) {
		super(true);
		this.space = space;
		this.modelViewTransformer = constructTransformer(space, winWidth - 110,
				winHeight - 95);
		// Eigenschaften des SpaceView einstellen
		this.setBackground(Color.white);
		computeViewDimensions();
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setBounds(0, 0, this.width, this.height); // n�tig f�r Darstellung
		// in layeredPane!
		// Attribut-Darstellung initialisieren (wenn gew�nscht)
		
		
		this.attr = attr;
		if (attr != null) {
			if (color == null)
				color = Color.orange; // ggf. Default-Werte benutzen
			if (numClasses <= 0)
				numClasses = 3;
			HashMap attrDistribution = space.getAttributeDistribution(attr);
			this.colourCode = new ColourCoding(color, numClasses,
					attrDistribution);
		}
		// event handling
		handleEvents();
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns the model-view transformer.
	 */
	public ModelViewTransformer getTransformer() {
		return this.modelViewTransformer;
	}

	/** paints the space */
	protected void paintMe(Graphics g) {
		paintSpace(g);
	}

	/** sets the graphical observer context */
	public void setContext(GraphicalObserverContext c) {
		context = c;
	}

	/** returns the graphical observer context */
	public GraphicalObserverContext getContext() {
		return context;
	}

	// -------------------------------------------------------- abstrakte
	// Methoden

	/**
	 * paints the space view. This method must be implemented by any derived
	 * class. It is called from paintComponent().
	 * 
	 * @param graphics
	 *            the graphics object used to do the painting
	 */
	protected abstract void paintSpace(Graphics graphics);

	/**
	 * provides an event handler e.g. for mouse clicks on this view. This method
	 * must be implemented by any derived class -- usually by calling
	 * super.addMouseListener( new MouseAdapter () {....} ); and implementing an
	 * appropriate mouse adapter class. This method is called from the
	 * constructor.
	 */
	protected abstract void handleEvents();

	/**
	 * @todo k�nnte auch als leere Methode vorgegeben werden, dann m�ssen views,
	 *       die nicht auf maus klicks reagieren, das nicht implementieren
	 *       entspricht dann immer noch dem Design Pattern Schablonenmethode
	 *       (Template Method), ist dann eine sog. Einschubmethode
	 */

	/**
	 * sets a new colour in the colour map for the node the given position
	 * refers to. The given value is mapped to the respective colour by the
	 * internal colour code. This method must be implemented by any derived
	 * class.
	 */
	public abstract void attributeChanged(Position where, Comparable value);

	// -------------------------------------------------------------
	// Hilfsmethoden

	/**
	 * constructs the model-view transformer. Computes the scale factor from the
	 * space bounds and the window width and height. Computes the affine
	 * transformations from the space origin, the scale factor and the view
	 * margin.
	 */
	protected ModelViewTransformer constructTransformer(Space space, int width,
			int height) {
		// Skalierungsfaktor bestimmen anhand bounds und Fenstergr��e
		double scaleFactor, scaleX, scaleY;
		double[] bounds = this.space.getBounds();
		scaleX = width / (bounds[2] - bounds[0]);
		scaleY = height / (bounds[3] - bounds[1]);
		// scaleFactor = Math.min(scaleX, scaleY); // den kleineren von beiden
		// nehmen
		scaleFactor = Math.max(scaleX, scaleY); // den gr��eren von beiden
		// nehmen
		// System.out.println("scale factors: " + scaleX + " " + scaleY + " -> "
		// + scaleFactor);

		return new ModelViewTransformer(scaleFactor, bounds, space.getOrigin(),
				new double[] { scaleFactor, scaleFactor }, new int[] { margin,
						margin });
	}

	/**
	 * computes this view's width and height out of the space's bounds, the
	 * scale factor and the view margin.
	 */
	protected void computeViewDimensions() {
		double[] bounds = this.space.getBounds();
		double scaleFactor = this.modelViewTransformer.getScaleFactor();
		this.width = (int) ((bounds[2] - bounds[0]) * scaleFactor) + 2 * margin;
		this.height = (int) ((bounds[3] - bounds[1]) * scaleFactor) + 2
				* margin;
	}

} /* end of class SpaceView */
