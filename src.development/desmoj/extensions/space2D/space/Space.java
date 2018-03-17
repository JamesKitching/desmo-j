package desmoj.extensions.space2D.space;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import desmoj.extensions.dimensions.Length;
//import desmoj.extensions.space2D.agent.Group;
//import desmoj.extensions.space2D.agent.SituatedAgent;
import desmoj.extensions.space2D.gui.AffineTransformable;
import desmoj.extensions.space2D.gui.SpaceView;

/**
 * This abstract class models a space as part of the environment in a multi-
 * agent model. Every space can convert a given point (coordinates) to the
 * corresponding position and compute the distance between two given positions.
 * A space maintains the positions of agents, objects and attributes that are
 * "situated" in it. Additionally, it maintains a list of attribute areas. Only
 * the environment has direct access to any of the space's attributes, objects,
 * agents and areas; agents can only indirectly access the space via the
 * environment. The default coordinate system for a space is two dimensional
 * with the origin in the upper left corner. Every space knows how it can be
 * visualized; you can ask the space for a space view.
 */
public abstract class Space implements AffineTransformable {
	// ///////////// Attribute ///////////////////////////////////

	/**
	 * the list of attribute areas added to this space so far.
	 */
	protected Vector areas = new Vector();

	/**
	 * this space's origin, i.e. the position of the origin of the coordinate
	 * system of this space's positions. Default is the upper left corner of a
	 * two-dimensional coordinate system: {AffineTransformable.LEFT,
	 * AffineTransformable.TOP}.
	 */
	protected int[] origin = new int[] { AffineTransformable.LEFT,
			AffineTransformable.TOP };

	/**
	 * this space's bounds as an array of minimum and maximum coordinate values.
	 * For a two-dimensional space, the bounds obviously define a (minimum)
	 * bounding rectangle. For each dimension the array lists first the minimal
	 * values and then the maximal values.
	 */
	protected double[] bounds;

	// ///////////// Methoden /////////////////////////////////////

	// SKR
	// Extended on 14.01.2010 
	// Reason: 		Neighbouring positions are the possible actions for the (moving) 
	//         		agent which belongs to a position
	// Problems:	I do not think that this is trivial to solve for Continous Spaces. This is
	//				open...

	/**
	 * returns the neighbours of the given position as an array of Position
	 * objects.
	 */
	public abstract Position[] getNeighbours(Position position);
	
	/**
	 * returns the reachable neighbours of the given position as an array of Position
	 * objects.
	 */
	public abstract Position[] getReachableNeighbours(Position position);

	// SKR
	
	/**
	 * adds the given attribute area to this space.
	 * 
	 * @param area
	 *            the new attribute area.
	 */
	public void addAttributeArea(AttributeArea area) {
		// f�r jede Position in area die abstrakte Methode addAttributeArea
		// aufrufen
		Position[] pos = area.getPositions();
		for (int i = 0; i < pos.length; i++) {
			addAttributeArea(area, pos[i]);
		}
		// area in der liste der areas merken
		this.areas.add(area);
	}

	/**
	 * removes the specified attribute area from this space.
	 * 
	 * @param area
	 *            the attribute area to be removed
	 */
	public void removeAttributeArea(AttributeArea area) {
		if (this.areas == null)
			return;
		// f�r jede Position in area die abstrakte Methode removeAttributeArea
		// aufrufen
		Position[] pos = area.getPositions();
		for (int i = 0; i < pos.length; i++) {
			removeAttributeArea(area, pos[i]);
		}
		// area aus der liste der areas streichen
		this.areas.remove(area);
	}

	/** returns a list of attribute areas currently defined in this space. */
	public AttributeArea[] getAttributeAreas() {
		// return list of areas
		AttributeArea[] areaList = new AttributeArea[this.areas.size()];
		this.areas.copyInto(areaList);
		return areaList;
	}

	// -------------------------------------------------- wg.
	// AffineTransformable

	/**
	 * returns the bounds of this space. Provides a default implementation of
	 * this method of the interface AffineTransformable.
	 */
	public double[] getBounds() {
		return this.bounds;
	}

	/**
	 * returns the upper left corner as the origin. Provides a default
	 * implementation of this method of the interface AffineTransformable.
	 */
	public int[] getOrigin() {
		if (this.origin == null) {
			this.origin = new int[] { AffineTransformable.LEFT,
					AffineTransformable.TOP };
		}
		return this.origin;
	}

	/**
	 * sets the origin to the new values.
	 * 
	 * @param origin
	 *            the position of this space's coordinate system as an array of
	 *            integer constants (defined in the interface
	 *            AffineTransformable)
	 * @see famos.gui.AffineTransformable
	 */
	public void setOrigin(int[] origin) {
		this.origin = origin;
	}

	// --------------------------------- f�r Aufruf im Konstruktor von
	// Environment

	/**
	 * returns the spatial groups associated with the currently defined
	 * attribute areas of this space. This method will be called by the
	 * constructor of Environment.
	 */
//	Group[] getSpatialGroups() {
//		// alle areas abklappern, ob sie r�umliche Gruppen sind
//		Vector spatialGroups = new Vector();
//		for (Iterator i = this.areas.iterator(); i.hasNext();) {
//			AttributeArea area = (AttributeArea) i.next();
//			if (area.isSpatialGroup()) {
//				spatialGroups.add(area.getGroup());
//			}
//		}
//		Group[] groups = new Group[spatialGroups.size()];
//		if (groups.length > 0)
//			spatialGroups.copyInto(groups);
//		return groups;
//	}

	// ///////////// abstrakte Methoden ///////////////////////////

	/**
	 * returns a position corresponding to the given point.
	 * 
	 * @param p
	 *            the point to be converted to a position associated with this
	 *            space
	 */
	public abstract Position getPosition(Point p);

	/**
	 * returns the distance between position a and position b in this space. The
	 * default unit of a space is "m" (desmoj.Units.M)
	 */
	public abstract Length getDistance(Position a, Position b);

	// ------------------------------------------ f�r den Zugriff aus
	// Environment
	// (Agents k�nnen nur �ber das Environment auf den Space zugreifen)

	/**
	 * adds the given agent to this space at the given position.
	 * 
	 * @param agent
	 *            the situated agent to be positioned in this space
	 * @param pos
	 *            the position of the agent
	 */
//	protected abstract void addAgent(SituatedAgent agent, Position pos);

	/**
	 * removes the given agent from this space.
	 * 
	 * @param agent
	 *            the situated agent to be removed from this space
	 */
//	protected abstract void removeAgent(SituatedAgent agent);

	/**
	 * returns the area observable for an agent with the given position and
	 * sensor range. An area is defined by a list of positions.
	 * 
	 * @param agentPos
	 *            the agent's position; forms the center of the observable area
	 * @param range
	 *            the agent's sensor range
	 */
	protected abstract Position[] getObservableArea(Position agentPos,
			Length range);

	/**
	 * returns the attributes of the given position.
	 */
	protected abstract AttributeList getAttributes(Position pos);

	/**
	 * adds the given attribute area to the given position's areas, i.e. the
	 * areas to which the space element the position points to belongs.
	 */
	protected abstract void addAttributeArea(AttributeArea area, Position pos);

	/**
	 * removes the given attribute area from the given position's areas, i.e.
	 * the areas to which the space element the position points to belongs.
	 */
	protected abstract void removeAttributeArea(AttributeArea area, Position pos);

	/**
	 * returns the objects residing at the given position in space
	 */
	protected abstract Object[] getObjects(Position pos);

	/**
	 * returns the agents currently residing at the given position in space
	 */
//	protected abstract SituatedAgent[] getAgents(Position pos);

	/**
	 * returns the local areas (observable areas of situated agents) the given
	 * position -- to be more exact: the space element this position refers to --
	 * is part of. The array is of length 0 if there are no such local areas.
	 */
	protected abstract AttributeArea[] getLocalAreas(Position pos);

	/**
	 * adds the given object to the specified position in space.
	 */
	protected abstract void addObject(Object obj, Position pos);

	/**
	 * removes the given object from the specified position in space.
	 */
	protected abstract void removeObject(Object obj, Position pos);

	/**
	 * adds the given attribute to the specified position in space.
	 */
	protected abstract void addAttribute(Attribute attr, Position pos);

	/**
	 * removes the given attribute from the specified position in space.
	 */
	protected abstract void removeAttribute(Attribute attr, Position pos);

	/**
	 * sets the value of the attribute defined by the given name at the
	 * specified position in space to the given value.
	 */
	protected abstract void setAttributeValue(String aName, Object aValue,
			Position pos);

	// -------------------------------------------------- f�r die Visualisierung

	/**
	 * returns the spatial distribution of the specified attribute as a hashmap
	 * with key = Position and value = Attribute. If the attribute is nowhere to
	 * be found in space the hashmap will be empty. This method is called from
	 * the corresponding space view if an attribute is to be displayed.
	 */
	public abstract HashMap getAttributeDistribution(ComparableAttribute attr);

	/**
	 * returns a list of all objects currently situated in this space. This
	 * method is called from the corresponding space view to determine which
	 * objects are to be displayed.
	 */
	public abstract Object[] getObjects();

	/**
	 * constructs a space view for this space with the given window dimensions.
	 */
	public abstract SpaceView getSpaceView(int winWidth, int winHeight);
	
	/**
	 * constructs a space view for this space with the given window dimensions.
	 */
	public abstract SpaceView getSpaceView(int winWidth, int winHeight, Image backgroundImage);

	/*
	 * Frage: ist getSpaceView() gut so ? Ist ok, auch in mvc sind verbindungen
	 * zw. m und v zul�ssig Ist ok, da es Design Pattern "Factory Method"
	 * entspricht Vorteil: einfachste L�sung in envview, jeder space liefert
	 * seinen eigenen spaceview Nachteil: feste verkn�pfung space mit view (pro
	 * space genau 1 view -- vielleicht nicht so schlimm), aber �berhaupt
	 * verdrahtung space mit gui sonst aber nur(?) �ber reflection m�glich und
	 * namenskonvention (view heisst genau wie space nur eben hinten view)
	 */

	/**
	 * constructs a space view for this space with the given window dimensions
	 * and the specified attribute distribution.
	 * 
	 * @param winWidth
	 *            the window width in pixel
	 * @param winHeigth
	 *            the window height in pixel
	 * @param attr
	 *            the attribute to be colour coded
	 * @param color
	 *            the color to be used
	 * @param numClasses
	 *            the number of attribute value classes (= shades of color)
	 */
	public abstract SpaceView getSpaceView(int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses);
	
	/**
	 * constructs a space view for this space with the given window dimensions
	 * and the specified attribute distribution.
	 * 
	 * @param winWidth
	 *            the window width in pixel
	 * @param winHeigth
	 *            the window height in pixel
	 * @param attr
	 *            the attribute to be colour coded
	 * @param color
	 *            the color to be used
	 * @param numClasses
	 *            the number of attribute value classes (= shades of color)
	 */
	public abstract SpaceView getSpaceView(int winWidth, int winHeight,
			ComparableAttribute attr, Color color, int numClasses, Image backgroundImage);

} /* end of class Space */
