package desmoj.extensions.space2D.space;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import desmoj.core.simulator.Units;
//import famos.agent.SituatedAgent;

/**
 * Abstract continuous space. Manages lists of agents and objects indexed with
 * their points of residence.
 */
public abstract class ContinuousSpace extends Space {

	// //////////// Attribute /////////////////////////////////////////////

	/**
	 * the list of agents. (key = Point, value = Vector of SituatedAgent)
	 */
	protected HashMap agents;

	/**
	 * the list of objects. (key = Point, value = Vector of Object)
	 */
	protected HashMap objects;

	/**
	 * the list of attributes. (key = Point, value = Vector of Attribute)
	 */
	protected HashMap attributes;

	// die Einheit: ## hardcodiert auf Meter ##
	protected int unit = Units.M;

	// //////////// Konstruktoren /////////////////////////////////////////

	/**
	 * Default constructor. Constructs an empty continuous space.
	 */
	public ContinuousSpace() {
	}

	// //////////// Methoden //////////////////////////////////////////////

	// ---------------------------------------- Implementation abstrakter
	// Methoden

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
	public Position[] getNeighbours(Position position) {
		return null;
	}
	
	/**
	 * returns the reachable neighbours of the given position as an array of Position
	 * objects.
	 */
	public Position[] getReachableNeighbours(Position position) {
		return null;
	}
	
	// SKR
	
	/**
	 * returns the spatial distribution of the specified attribute as a hashmap
	 * with key = PointPosition and value = Attribute. If the attribute is
	 * nowhere to be found in space the hashmap will be empty.
	 */
	public HashMap getAttributeDistribution(ComparableAttribute attr) {
		HashMap map = new HashMap(this.attributes.size());
		for (Iterator i = this.attributes.keySet().iterator(); i.hasNext();) {
			Point p = (Point) i.next();
			Vector v = (Vector) this.attributes.get(p);
			int index = v.indexOf(attr);
			if (index >= 0) {
				map.put(getPosition(p), (ComparableAttribute) v.get(index));
			}
		}
		return map;
	}

	/**
	 * returns a new PointPosition for the given point.
	 */
	public Position getPosition(Point p) {
		return new PointPosition(this, p);
	}

	/**
	 * returns a list of all objects currently existing in this space.
	 */
	public Object[] getObjects() {
		if (this.objects == null)
			return null;
		Vector allObjects = new Vector();
		for (Iterator i = this.objects.values().iterator(); i.hasNext();) {
			allObjects.addAll((Vector) i.next());
		}
		Object[] list = new Object[allObjects.size()];
		allObjects.copyInto(list);
		return list;
	}

	/**
	 * adds an agent to the list of agents at the point the given position
	 * refers to. This method also sets the agent's current position to
	 * <code>pos</code>.
	 */
//	protected void addAgent(SituatedAgent agent, Position pos) {
//		// Agenten in die Liste der Agenten aufnehmen
//		if (this.agents == null) {
//			this.agents = new HashMap();
//		}
//		add(agent, this.agents, pos.getPoint());
//		// im Agenten die Position setzen
//		agent.setCurrentPosition(pos);
//	}

	/**
	 * removes the agent from this space. Also deletes the agent's current
	 * position, i.e. sets the agent's current position to <code>null</code>.
	 */
//	protected void removeAgent(SituatedAgent agent) {
//		remove(agent, this.agents, agent.getCurrentPosition().getPoint());
//		// im Agenten die Position lschen
//		agent.setCurrentPosition(null);
//	}

	/**
	 * returns the attributes of the given position in space. NOT YET
	 * IMPLEMENTED.
	 */
	protected AttributeList getAttributes(Position pos) {
		/** @todo: implement this famos.space.Space abstract method */
		return null;
	}

	/**
	 * returns the situated agents currently residing at the point in space the
	 * given position refers to.
	 */
//	protected SituatedAgent[] getAgents(Position pos) {
//		return getAgents(pos.getPoint());
//	}

	/**
	 * returns the objects currently residing at the point in space the given
	 * position refers to.
	 */
	protected Object[] getObjects(Position pos) {
		return getObjects(pos.getPoint());
	}

	/**
	 * adds an attribute area to the given position. NOT YET IMPLEMENTED.
	 */
	protected void addAttributeArea(AttributeArea area, Position pos) {
		/** @todo: implement this famos.space.Space abstract method */
	}

	/**
	 * removes an attribute area from the given position. NOT YET IMPLEMENTED.
	 */
	protected void removeAttributeArea(AttributeArea area, Position pos) {
		/** @todo: implement this famos.space.Space abstract method */
	}

	/**
	 * returns the local areas the given position's point is part of. NOT YET
	 * IMPLEMENTED.
	 */
	protected AttributeArea[] getLocalAreas(Position pos) {
		/** @todo: implement this famos.space.Space abstract method */
		return new AttributeArea[] {};
	}

	/**
	 * adds an object to the point in space the given position refers to.
	 */
	protected void addObject(Object obj, Position pos) {
		// Objekt in die Liste der Objekte aufnehmen
		if (this.objects == null) {
			this.objects = new HashMap();
		}
		add(obj, this.objects, pos.getPoint());
	}

	/**
	 * removes the given object from the specified position in space.
	 */
	protected void removeObject(Object obj, Position pos) {
		remove(obj, this.objects, pos.getPoint());
	}

	/**
	 * adds the given attribute to the specified position in space.
	 */
	protected void addAttribute(Attribute attr, Position pos) {
		// Attribut in die Liste der Attribute aufnehmen
		if (this.attributes == null) {
			this.attributes = new HashMap();
		}
		add(attr, this.attributes, pos.getPoint());
	}

	/**
	 * removes the given attribute from the specified position in space.
	 */
	protected void removeAttribute(Attribute attr, Position pos) {
		remove(attr, this.attributes, pos.getPoint());
	}

	/**
	 * sets the value of the attribute defined by the given name at the
	 * specified position in space to the given value. NOT YET IMPLEMENTED
	 */
	protected void setAttributeValue(String aName, Object aValue, Position pos) {
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	/** helper method */
	private Object[] getObjects(Point p) {
		if (this.objects == null)
			return null;
		if (!this.objects.containsKey(p))
			return null;
		Vector temp = (Vector) this.objects.get(p);
		Object[] list = new Object[temp.size()];
		temp.copyInto(list);
		return list;
	}

	/** helper method */
//	private SituatedAgent[] getAgents(Point p) {
//		if (this.agents == null)
//			return null;
//		if (!this.agents.containsKey(p))
//			return null;
//		Vector temp = (Vector) this.agents.get(p);
//		SituatedAgent[] list = new SituatedAgent[temp.size()];
//		temp.copyInto(list);
//		return list;
//	}

	/** helper method */
	private void add(Object what, HashMap where, Point p) {
		Vector list;
		if (where.containsKey(p)) {
			list = (Vector) where.get(p);
			if (!list.contains(what))
				list.add(what);
		} else {
			list = new Vector();
			list.add(what);
		}
		where.put(p, list);
	}

	/** helper method */
	private void remove(Object what, HashMap where, Point p) {
		if (where == null)
			return;
		if (where.containsKey(p)) {
			Vector list = (Vector) where.get(p);
			list.remove(what);
			where.put(p, list); // ntig? ###
		}
	}

	// ---------------- Hilfsmethoden fžr abgeleitete Klassen

	/** helper method for sub-classes */
	protected Point[] getPoints() {
		// alle Points aus den Hashmaps einsammeln
		Vector p = new Vector();
		for (Iterator i = this.agents.keySet().iterator(); i.hasNext();) {
			p.add((Point) i.next());
		}
		for (Iterator i = this.objects.keySet().iterator(); i.hasNext();) {
			Point pI = (Point) i.next();
			if (!p.contains(pI))
				p.add(pI);
		}
		// Attribute gibts noch nicht...
		Point[] temp = new Point[p.size()];
		p.copyInto(temp);
		return temp;
	}

}