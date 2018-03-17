package desmoj.extensions.space2D.space;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;
import desmoj.demo.balticSea.Ship;
//import desmoj.extensions.space2D.agent.Alert;
//import desmoj.extensions.space2D.agent.ExternalSignal;
//import desmoj.extensions.space2D.agent.Group;
//import desmoj.extensions.space2D.agent.GroupMember;
import desmoj.extensions.space2D.Schedule;
import desmoj.extensions.space2D.gui.EnvironmentEvent;
import desmoj.extensions.space2D.gui.EnvironmentListener;

/**
 * This class represents the environment in a multi-agent model. The environment
 * consists of a space model and a communication infrastructure and holds a list
 * of all agents. It may contain environmental dynamics in the form of entities
 * or processes which change the environment's state. The environment manages
 * internal update events on an internal event list and appropriate environment
 * update events scheduled with the simulation's event list.
 */
public class Environment extends Entity {
	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/**
	 * the group of all agents.
	 */
//	private Group agents = new Group("AGENTS");

	/**
	 * the list of environmental dynamics.
	 * 
	 * @SBGen Collection of desmoj.Entity (dynamics,,,0)
	 */
	private Vector dynamics = new Vector();

	/**
	 * the space model.
	 */
	private Space space;

	/**
	 * the list of observable areas of the situated agents. (key = agent, value =
	 * attribute area)
	 */
	private HashMap locals;

	/**
	 * the list of groups. Group names must be unique because the names are used
	 * as identifiers.
	 * 
	 * @SBGen Collection of famos.agent.Group (groups,,,0)
	 */
	private Hashtable groups = new Hashtable();

	/**
	 * the internal event list used to manage updates.
	 */
	private Schedule internalEvents;

	/** a flag to turn off (on) environmental alerts */
	private boolean noAlerts = false;

	/** A list of environment Listeners */
	private Vector listeners;

	private EnvironmentUpdate myUpdate;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new environment for the given multi-agent model with the
	 * given space. Initializes all internal lists.
	 */
	public Environment(Model owner, Space space) {
		this(owner);
		setSpace(space);
		myUpdate = new EnvironmentUpdate((Model) this.getModel());
	}

	/**
	 * constructs a new environment for the given multi-agent model and
	 * initializes all internal lists. This environment does not have a space
	 * model. You may add a space using the setSpace() method later.
	 */
	public Environment(Model owner) {
		super(owner, /* owner.getName()+ */"Environment", true);
		this.dynamics = new Vector();
		this.groups = new Hashtable();
		this.locals = new HashMap();
		this.internalEvents = new Schedule(this);
		myUpdate = new EnvironmentUpdate((Model) this.getModel());
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * sets the no-alerts flag to <code>noAlerts</code>. <code>true</code>
	 * turns environmental alerts off, <code>false</code> turns them on
	 * (default).
	 */
	public void setNoAlerts(boolean noAlerts) {
		this.noAlerts = noAlerts;
	}

	/**
	 * returns the space model.
	 */
	public Space getSpace() {
		return this.space;
	}

	/**
	 * sets the space model for this environment to the given space. This method
	 * is provided to allow for a two step process in creating a spatial
	 * environment: you may call the "space-less" constructor first, do anything
	 * necessary to create your space and add this space afterwards to the
	 * environment. Note that you can't change an existing space model, i.e. if
	 * the environment already has a space the new space is ignored.
	 */
	public void setSpace(Space space) {
		if (this.space != null || space == null)
			return;
		this.space = space;
		// ask space for existing spatial groups and add them to the group list
//		Group[] spatialGroups = this.space.getSpatialGroups();
//		for (int i = 0; i < spatialGroups.length; i++) {
//			this.groups.put(spatialGroups[i].getName(), spatialGroups[i]);
//		}
	}

	/**
	 * returns the group of all agents in the environment.
	 */
//	public Group getAgents() {
//		return agents;
//	}

	/**
	 * returns a list of all attribute areas.
	 */
	public AttributeArea[] getAttributeAreas() {
		return this.space.getAttributeAreas();
	}

	/**
	 * adds the given attribute area to the space.
	 */
	public void addAttributeArea(AttributeArea area) {
		this.space.addAttributeArea(area);
//		if (area.isSpatialGroup()) {
//			Group g = area.getGroup();
//			this.groups.put(g.getName(), g);
//		}
	}

	/**
	 * removes the given attribute area from the space.
	 */
	public void removeAttributeArea(AttributeArea area) {
		this.space.removeAttributeArea(area);
//		if (area.isSpatialGroup()) {
////			deleteGroup(area.getGroup().getName());
//		}
	}

//	/**
//	 * returns the local state in the sensing area of the given agent.
//	 */
//	public EnvironmentState getLocalState(SituatedAgent agent) {
//		// // vom space den Wahrnehmungsbereich bestimmen lassen
//		// Position[] observable =
//		// this.space.getObservableArea(agent.getCurrentPosition(),
//		// agent.getSensorRange());
//		// die Positionen des Wahrnehmungsbereichs abfragen
//		Position[] observable = ((AttributeArea) this.locals.get(agent))
//				.getPositions();
//		if (observable == null)
//			return null;
//		// environment state aufbauen
//		EnvironmentState state = new EnvironmentState(observable);
//		for (int i = 0; i < observable.length; i++) {
//			Agent[] agents = space.getAgents(observable[i]);
//			if (agents != null) {
//				for (int j = 0; j < agents.length; j++) {
//					/**
//					 * @todo agent selber nicht hinzuf�gen? (oder doch, weil er
//					 *       ja auch zum Zustand geh�rt?)
//					 */
//					state.addAgent(agents[j], observable[i]);
//				}
//			}
//			Object[] objects = space.getObjects(observable[i]);
//			if (objects != null) {
//				for (int j = 0; j < objects.length; j++) {
//					state.addObject(objects[j], observable[i]);
//				}
//			}
//			Attribute[] attr = space.getAttributes(observable[i])
//					.getAttributes();
//			if (attr != null) {
//				for (int j = 0; j < attr.length; j++) {
//					state.addAttribute(attr[j], observable[i]);
//				}
//			}
//
//		}
//		return state;
//	}

//	/**
//	 * returns the group with the specified name or <code>null</code> if no
//	 * such group exists in this environment.
//	 */
//	public Group getGroup(String name) {
//		if (this.groups.containsKey(name)) {
//			return (Group) this.groups.get(name);
//		} else {
//			return null;
//		}
//	}
//
//	public Agent getAgent(String name) {
//		return (Agent) this.agents.getMember(name);
//	}
//
//	/**
//	 * returns the local group (the spatial group belonging to the observable
//	 * area) of the specified situated agent.
//	 */
//	public Group getLocalGroup(SituatedAgent agent) {
//		return getGroup(new String("local-" + agent.getName()));
//	}

	/**
	 * returns the list of groups the specified group member is a (direct)
	 * member of.
	 */
//	public Group[] getGroups(GroupMember member) {
//		// globale Gruppenliste durchgehen und testen, ob member dort
//		// direktes mitglied ist
//		Enumeration allGroups = this.groups.elements();
//		Vector memberGroups = new Vector();
//		while (allGroups.hasMoreElements()) {
//			Group group = (Group) allGroups.nextElement();
//			if (group.hasMember(member)) {
//				memberGroups.add(group);
//			}
//		}
//		Group[] groupArray = new Group[memberGroups.size()];
//		memberGroups.copyInto(groupArray);
//		return groupArray;
//	}

	/**
	 * returns the list of all groups defined in this environment as an array.
	 */
//	public Group[] getGroups() {
//		Enumeration allGroups = this.groups.elements();
//		Group[] groupList = new Group[this.groups.size()];
//		for (int i = 0; i < groupList.length; i++) {
//			groupList[i] = (Group) allGroups.nextElement();
//		}
//		return groupList;
//	}

	/**
	 * returns a list of all spatial groups defined in this environment as an
	 * array.
	 */
//	public Group[] getSpatialGroups() {
//		return this.space.getSpatialGroups();
//	}

	/**
	 * adds a new group to the list of groups. If there is already a group with
	 * the same name (remember: group names must be unique), the new group will
	 * not be added. This failure is indicated by a return value of
	 * <code>false</code>.
	 * 
	 * @param group
	 *            the group to be added to this environment
	 * @return <code>true</code> if the given group could be added, <code>
	 *    false</code>
	 *         otherwise
	 */
//	public boolean addGroup(Group group) {
//		// check the name
//		if (this.groups.containsKey(group.getName())) {
//			return false;
//		}
//		this.groups.put(group.getName(), group);
//		return true;
//	}

	/**
	 * deletes the specified group.
	 * 
	 * @param name
	 *            the name of the group to be deleted
	 * @todo mitglieder gel�schter gruppe als mitglieder deren Obergruppen???
	 */
//	public void deleteGroup(String name) {
//		Group group = getGroup(name);
//		if (group == null)
//			return;
//		// aus der globalen Gruppenliste entfernen
//		this.groups.remove(name);
//		// alle member austragen
//		Collection members = group.getMembers();
//		for (Iterator i = members.iterator(); i.hasNext();) {
//			group.leave((GroupMember) i.next());
//		}
//		// aus allen super gruppen austragen
//		Group[] superGroups = getGroups(group);
//		for (int i = 0; i < superGroups.length; i++) {
//			superGroups[i].leave(group);
//		}
//		// sollen daf�r jetzt die member direkt Mitglied in den super gruppen
//		// werden???
//		// ---> Nein, damit hierarchische Gruppen direkten Zugriff auf
//		// indirekte Mitglieder bieten, sollen sie zuvor "geplaettet" werden.
//	}

	/**
	 * lets the specified group member leave the old group and join the new
	 * group. If something goes wrong a warning is sent to console and error
	 * file.
	 */
//	public void switchGroup(GroupMember member, String oldGroup, String newGroup) {
//		leaveGroup(oldGroup, member);
//		joinGroup(newGroup, member);
//	}
//
//	/**
//	 * Creates a new group without roles in this environment. If the group's
//	 * name is not unique a warning is sent to console and error file.
//	 */
//	public Group createGroup(String name) {
//		return createGroup(name, false);
//	}

	/**
	 * lets the given agent enter this environment. As no point in space is
	 * specified for the agent it will not be positioned in space. Unless agent
	 * is situated AND already provided with a position - in this case it will
	 * be positioned in space correctly.
	 */
//	public void enter(Agent agent) {
//		// perhaps it's a situated agent who already knows his position?
//		try {
//			SituatedAgent sit = (SituatedAgent) agent;
//			Position pos = sit.getCurrentPosition();
//			if (pos != null) {
//				enter(sit, pos);
//				return;
//			}
//		} catch (ClassCastException e) {
//		}
//
//		// -- Its just another simple agent...
//		// Only join if not already member
//		if (!agents.hasMember(agent)) {
//			agents.join(agent);
//		} else {
//			sendWarning("Agent " + agent + " cannot enter environment.",
//					"Environment.enter(Agent)",
//					"The agent has already entered the environment.",
//					"Make sure that every agent enters the environment only once.");
//			System.out.println("Agent " + agent + " cannot enter environment.");
//		}
//	}

	/**
	 * Creates a new groups in this environment. The name of the group has to be
	 * unique, otherwise nothing happens.
	 * 
	 * @param name
	 *            the new group's name
	 * @param hasRoles
	 *            true if a group with roles is to be created
	 */
//	public Group createGroup(String name, boolean hasRoles) {
//		// Check unique name
//		if (groups.containsKey(name)) {
//			// If not unique send warning to console and error file
//			sendWarning("Cannot create group named " + name,
//					"Environment.createGroup",
//					"A group with this name already exists in the environment",
//					"Choose a unique group name.");
//			System.out.println("** Error: A group named " + name
//					+ "already exists in this environment !");
//		}
//
//		// Create the requested group and add it to the mapping of groups
//		Group g;
//		if (hasRoles)
//			g = new RoleGroup(name);
//		else
//			g = new Group(name);
//		groups.put(g.getName(), g);
//		return g;
//	}

	/**
	 * lets the given situated agent enter this environment and position itself
	 * at the specified position in space.
	 */
//	public void enter(SituatedAgent agent, Position pos) {
//		// Only join if not already member
//		if (!agents.hasMember(agent)) {
//			agents.join(agent);
//		} else {
//			sendWarning("Agent " + agent + " cannot enter environment.",
//					"Environment.enter(Agent)",
//					"The agent has already entered the environment.",
//					"Make sure that every agent enters the environment only once.");
//			System.out.println("Agent " + agent + " cannot enter environment.");
//			return;
//		}
//
//		this.space.addAgent(agent, pos);
//		// -- Notify listeners
//		notifyListeners(EnvironmentEvent.AGENT_ENTERED, agent);
//
//		// Wahrnehmungsbereich bestimmen und daraus neue AttributeArea machen
//		// mit lokaler Gruppe
//		Position[] observable = this.space.getObservableArea(agent
//				.getCurrentPosition(), agent.getSensorRange());
//		AttributeArea obsArea = new LocalArea(agent, observable);
//		// Group local = new Group("local-" + agent.getName());
//		// for (int i = 0; i < observable.length; i++) {
//		// Agent[] agents = space.getAgents(observable[i]);
//		// if (agents != null) {
//		// for (int j = 0; j < agents.length; j++) {
//		// local.join(agents[j]);
//		// }
//		// }
//		// }
//		// obsArea.setGroup(local);
//		Group local = obsArea.getGroup();
//		this.groups
//				.put(/* "local-" + agent.getName() */local.getName(), local);
//		this.locals.put(agent, obsArea);
//
//		// System.out.println("local state for agent " + agent.getName() + ":
//		// \n" +
//		// getLocalState(agent).toString());
//		// System.out.println("localGroup = " + local.toString() + "\n");
//
//	}

	/**
	 * lets the given situated agent enter this environment and position itself
	 * at the specified point in space.
	 */
//	public void enter(SituatedAgent agent, Point point) {
//		Position pos = this.space.getPosition(point);
//		enter(agent, pos);
//		// System.out.println(this.currentTime() + ": " + agent.getName() + "
//		// enters env at position " + pos);
//	}

	/**
	 * removes the given agent from this environment. If the agent is situated
	 * it will also be removed from space.
	 */
//	public void exit(Agent agent) {
//
//		if (!agents.hasMember(agent)) {
//			sendWarning("Agent " + agent + " cannot leave environment.",
//					"Environment.leave(Agent)",
//					"The agent has never entered the environment.",
//					"Make sure that every agent enters the environment "
//							+ "before leaving it.");
//			System.out.println("Agent " + agent + " cannot leave environment.");
//			return;
//		}
//
//		agents.leave(agent);
//
//		// und aus allen gruppen austragen
//		Enumeration allGroups = this.groups.elements();
//		while (allGroups.hasMoreElements()) {
//			Group g = (Group) allGroups.nextElement();
//			if (g.hasMember(agent))
//				g.leave(agent);
//		}
//
//		// und wenn agent situated ist und space existiert:
//		if (this.space != null && agent instanceof SituatedAgent) {
//			// aus dem space entfernen
//			this.space.removeAgent((SituatedAgent) agent);
//			// seinen lokalen Wahrnehmungsbereich l�schen
//			AttributeArea local = (AttributeArea) this.locals.remove(agent);
//			Position[] localPos = local.getPositions();
//			for (int i = 0; i < localPos.length; i++) {
//				space.removeAttributeArea(local, localPos[i]);
//			}
//			// einschliesslich der r�umlichen Gruppe
//			deleteGroup(local.getGroup().getName());
//		}
//		// alle Ereignisse f�r agent aus der internen Ereignis-Liste entfernen
//		cancelEvents(agent);
//
//		// -- Notify listeners
//		if (agent instanceof SituatedAgent)
//			notifyListeners(EnvironmentEvent.AGENT_LEFT, agent);
//
//	}

	/**
	 * sends the given external signal to the signal's receiver.
	 */
//	public void send(ExternalSignal signal) {
//		// signal an receiver weiterleiten
//		signal.getReceiver().receive(signal);
//		notifyListeners(EnvironmentEvent.SIGNAL_SENT, signal);
//	}

	/**
	 * adds the given object to the specified position. This method may be used
	 * by agents to change the environment.
	 */
	public void addObject(Object obj, Position p) {
		// Objekt hinzuf�gen lassen durch space
		this.space.addObject(obj, p);
		System.out.println("env: object added");
		// Alerts senden an alle Agenten, in deren Wahrnehmungsbereich p liegt
//		alert(gatherMembers(p, null));
	}

	/**
	 * removes the specified object from the specified position. This method may
	 * be used by agents to change the environment.
	 */
	public void removeObject(Object obj, Position p) {
		// Objekt entfernen lassen durch space
		this.space.removeObject(obj, p);
		// Alerts senden an alle Agenten, in deren Wahrnehmungsbereich p liegt
//		alert(gatherMembers(p, null));
	}

	/**
	 * adds the given attribute to the specified position. This method may be
	 * used by agents to change the environment.
	 */
	public void addAttribute(Attribute attr, Position p) {
		// Attribut hinzuf�gen lassen durch space
		this.space.addAttribute(attr, p);
		// Alerts senden an alle Agenten, in deren Wahrnehmungsbereich p liegt
//		alert(gatherMembers(p, null));
	}

	/**
	 * removes the given attribute from the specified position. This method may
	 * be used by agents to change the environment.
	 */
	public void removeAttribute(Attribute attr, Position p) {
		// Attribut entfernen lassen durch space
		this.space.removeAttribute(attr, p);
		// Alerts senden an alle Agenten, in deren Wahrnehmungsbereich p liegt
//		alert(gatherMembers(p, null));
	}

	/**
	 * changes the value of the specified attribute at the specified position to
	 * the given value. This method may be used by agents to change the
	 * environment.
	 */
	public void setAttributeValue(String aName, Object aValue, Position p) {
		this.space.setAttributeValue(aName, aValue, p);
		// Alerts senden an alle Agenten, in deren Wahrnehmungsbereich p liegt
//		alert(gatherMembers(p, null));

		// -- Notify listeners
		notifyListeners(EnvironmentEvent.ATTRIBUTE_CHANGED, new Object[] { p,
				aValue });
	}

	/** Adds a listener */
	public void addEnvironmentListener(EnvironmentListener l) {
		if (listeners == null)
			listeners = new Vector();
		if (!listeners.contains(l))
			listeners.add(l);
	}

	/** Removes a listener */
	public void removeEnvironmentListener(EnvironmentListener l) {
		listeners.remove(l);
	}

	/**
	 * Makes the given member join the group with the given name. If the group
	 * to be joined does not exist, the member (agent or subgroup) is not
	 * registered with the environment or already member of the group, a warning
	 * is sent to console and error file.
	 */
//	public void joinGroup(String groupName, GroupMember member) {
//		Group g = getGroup(groupName);
//		if (g == null)
//			return;
//		if (checkMember(member)) {
//			if (!g.hasMember(member)) {
//				g.join(member);
//			} else {
//				sendWarning(
//						member + " cannot join group " + groupName + ".",
//						"Environment.leave",
//						"The given GroupMember is already member of this group",
//						"A group member can be member of a group only once ");
//				System.out.println("** Error: " + member + " cannot join "
//						+ groupName);
//			}
//		}
//	}

	/**
	 * Makes the given member leave the group with the given name. If the group
	 * to be joined does not exist, the member (agent or subgroup) is not
	 * registered with the environment or not member of the group, a warning is
	 * sent to console and error file.
	 */
//	public void leaveGroup(String groupName, GroupMember member) {
//		Group g = getGroup(groupName);
//		if (g == null)
//			return;
//		if (checkMember(member)) {
//			if (!g.hasMember(member)) {
//				sendWarning(member + " cannot leave group " + groupName + ".",
//						"Environment.leave",
//						"The given member is not a member of the group",
//						"A group member must be member of a group before it can leave "
//								+ " the group.");
//				System.out.println("** Error: " + member + " cannot leave "
//						+ groupName);
//			} else
//				g.leave(member);
//		}
//	}

	// ---------------------------------------------------------- interne
	// Methoden

	/**
	 * provides a position update for the given agent. At the time of the
	 * position update event the agent will have covered half the distance
	 * between his current position and the specified position so the space will
	 * be updated accordingly.
	 * 
	 * @param event
	 *            the position update event
	 */
	void updatePosition(PositionUpdate event) {
		// System.out.println(" * updatePosition() called from " +
		// event.getEntity().getName() + " for t = " + event.getTime());
		if (!this.internalEvents.contains(event)) {
			// event an der zeitlich richtigen Stelle einsortieren
			boolean frontChanged = this.internalEvents.insert(event);
			// bedeutet ggf. eine Ver�nderung der Aktivierung
			if (frontChanged)
				reactivate();
		}
	}

	/**
	 * Insert a position reached event into schedule to notify the agent about
	 * reaching a new position.
	 * 
	 * @param event
	 *            the position reached event
	 */
	void notifyAgent(PositionNotification event) {
		// System.out.println(" * notifyAgent() called from " +
		// event.getEntity().getName() + " for t = " + event.getTime());
		if (!this.internalEvents.contains(event)) {
			// event an der zeitlich richtigen Stelle einsortieren
			boolean frontChanged = this.internalEvents.insert(event);
			// bedeutet ggf. eine Ver�nderung der Aktivierung
			if (frontChanged)
				reactivate();
		}
	}

	/**
	 * cancels the given event on the internal event list.
	 */
	void cancel(EnvironmentSignal event) {
		if (this.internalEvents.contains(event)) {
			boolean frontChanged = this.internalEvents.remove(event);
			// bedeutet ggf. eine ver�nderung der Aktivierung
			if (frontChanged)
				reactivate();
		}
	}

	/**
	 * executes all pending updates for the current point in simulation time.
	 * This method is called from the event routine of the corresponding
	 * environment update event.
	 */
	void update() {
		// System.out.println(currentTime() + ": environment update");
		// alle event notes f�r diesen Zeitpunkt abarbeiten
		EnvironmentSignal ev = (EnvironmentSignal) this.internalEvents
				.nextSignal();
		while (ev != null) {

			// je nach typ entsprechend handeln
			if (ev instanceof PositionUpdate) {

//				SituatedAgent agent = (SituatedAgent) ev.getEntity();
				// System.out.println("-- update position for " +
				// agent.getName());
				Position newPos = ((PositionUpdate) ev).getNewPosition();
//				if (this.agents.hasMember(agent))
//					moveAgent(agent, newPos);
//				else
//					System.out.println("** Environment: Error - Agent " + agent
//							+ " to move has left environment!");
			}

			// On PositionNotification send agent a position reached event
			/* @todo geht's auch eleganter ?? */
			else if (ev instanceof PositionNotification) {

//				SituatedAgent agent = (SituatedAgent) ev.getEntity();
				// System.out.println("-- position notification for " +
				// agent.getName());
//				if (this.agents.hasMember(agent)) {
//					agent.receive(new PositionReached(agent, currentTime()));
//				} else {
//					System.out.println("** Environment: Error - Agent " + agent
//							+ " to notify has left environment!");
//				}
			}

			// n�chste Note
			ev = (EnvironmentSignal) this.internalEvents.nextSignal();
		}

		// neues environment update event f�r den n�chsten Zeitpunkt
		reactivate();
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	/**
	 * cancels all pending events for the given agent (due to the agent leaving
	 * the environment).
	 */
	public void cancelEvents(Entity e) {
		// zu entfernende events einsammeln
		Vector rm = new Vector();
		for (Iterator i = this.internalEvents.allSignals(); i.hasNext();) {
			EnvironmentSignal event = (EnvironmentSignal) i.next();
			if (event.getEntity() == e) {
				rm.add(event);
			}
		}
		// System.out.println(" * cancelEvents() called for " + e.getName() + "
		// at " + e.currentTime() + " - " + rm.size() + " events to cancel" );
		// zu entfernende events l�schen (darf nicht gleichzeitig beim iterieren
		// passieren!)
		boolean frontChanged = false;
		for (int i = 0; i < rm.size(); i++) {
			frontChanged |= this.internalEvents.remove((EnvironmentSignal) rm
					.get(i));
		}
		// ggf. Aktivierung des environment verschieben
		if (frontChanged)
			reactivate();
	}

	/**
	 * changes the agent's position to newPos.
	 */
//	public void moveAgent(SituatedAgent agent, Position newPos) {
//		Position oldPos = agent.getCurrentPosition();
//		// System.out.println(" * " + agent.getName() + " will change position
//		// from " + oldPos + " to " + newPos);
//		// in space agent aus alter Position austragen und in newPos eintragen
//		this.space.removeAgent(agent);
//		// dabei zwischendurch die lokalen Gruppen bestimmen, an die ein Alert
//		// geschickt werden muss; dies passiert am besten NACH austragen des
//		// Agenten aus der alten Position (dann ist er aus allen r�umlichen
//		// Gruppen
//		// dieser alten Position ausgetragen!) und VOR eintragen des Agenten in
//		// die
//		// neue Position (dann ist er noch nicht in den neuen r�umlichen Gruppen
//		// drin)
//		// --> agent selbst bekommt kein alert!
//		Group toBeAlerted = gatherMembers(oldPos, newPos);
//		this.space.addAgent(agent, newPos); // hier wird auch in agent die neue
//		// Pos gesetzt!
//		// ausserdem wird die Zugeh�rigkeit von agent zu den r�umlichen Gruppen
//		// vom space aktualisiert (jedenfalls in discrete space)
//
//		// jetzt noch den lokalen Wahrnehmungsbereich des Agenten aktualisieren
//		// und damit auch seine lokale r�umliche Gruppe
//		updateLocalArea(agent);
//
//		// und ein Alert an alle Agenten senden, in deren Wahrnehmungsbereich
//		// sich
//		// durch die Bewegung von agent eine �nderung ergeben hat
//		alert(toBeAlerted);
//
//		// notifyListeners(EnvironmentEvent.AGENT_MOVED, agent);
//		// -- lieber erst bei PositionNotification in update()!
//		notifyListeners(EnvironmentEvent.AGENT_MOVED, agent);
//	}


//	private Group[] determineGroups(Position a, Position b) {
//		Vector groups = new Vector();
//		AttributeArea[] areas = space.getLocalAreas(a);
//		for (int i = 0; i < areas.length; i++) {
//			groups.add(areas[i].getGroup());
//		}
//		if (b != null) {
//			areas = space.getLocalAreas(b);
//			for (int i = 0; i < areas.length; i++) {
//				Group g = areas[i].getGroup();
//				if (!groups.contains(g)) {
//					groups.add(g);
//				}
//			}
//		}
//		Group[] temp = new Group[groups.size()];
//		groups.copyInto(temp);
//		return temp;
//	}

//	private Group gatherMembers(Position a, Position b) {
//		// r�umliche Gruppen an a und b bestimmen
//		Group[] groups = determineGroups(a, b);
//		// jedes Mitglied aus groups soll nur einmal auftauchen in neuer Gruppe
//		// am einfachsten: neue Gruppe bilden, alle Mitglieder in groups dort
//		// hinein (join eliminiert doppelte!)
//		Group temp = new Group("temporary_group");
//		for (int i = 0; i < groups.length; i++) {
//			Collection members = groups[i].getMembers();
//			for (Iterator j = members.iterator(); j.hasNext();) {
//				temp.join((GroupMember) j.next());
//			}
//		}
//		return temp;
//	}

//	private void alert(Group group) {
//		if (this.getModel().getExperiment() == null)
//			return;
//		// an alle Mitglieder in group ein Alert senden
//		if (!this.noAlerts && !group.isEmpty()) {
//			// System.out.println(currentTime() + ": env alerts " +
//			// group.toString());
//			send(new Alert(group));
//		}
//	}

//	private void updateLocalArea(SituatedAgent agent) {
//		// aktuellen Wahrnehmungsbereich bestimmen
//		Vector newpos = arrayToVector(this.space.getObservableArea(agent
//				.getCurrentPosition(), agent.getSensorRange()));
//		// und mit vorigem Bereich vergleichen
//		AttributeArea local = (AttributeArea) this.locals.get(agent);
//		Vector oldpos = arrayToVector(local.getPositions());
//		for (int i = 0; i < newpos.size(); i++) {
//			int j = oldpos.indexOf(newpos.get(i));
//			if (j >= 0) {
//				// in beiden enthalten --> streichen
//				newpos.remove(i);
//				oldpos.remove(j);
//			}
//		}
//		// newpos enth�lt jetzt die neu aufzunehmenden, oldpos die zu
//		// entfernenden
//		// Positionen f�r local
//		for (int i = 0; i < oldpos.size(); i++) {
//			local.removePosition((Position) oldpos.get(i));
//			// dies entfernt automatisch die an der Position sitzenden agenten
//			// aus der spatial Group!
//			// (jedenfalls in discrete space)
//		}
//		for (int i = 0; i < newpos.size(); i++) {
//			local.addPosition((Position) newpos.get(i));
//			// die f�gt automatisch die an der position sitzenden agenten zur
//			// spatial group hinzu!
//		}
//	}

	private Vector arrayToVector(Object[] a) {
		Vector v = new Vector(a.length);
		for (int i = 0; i < a.length; i++) {
			v.add(a[i]);
		}
		return v;
	}

	/**
	 * reactivates the environment, i.e. schedules an environment update event
	 * on the simulation's event list.
	 */
	private void reactivate() {
		// next activation time from internal event list
		SimTime actTime = this.internalEvents.nextActivationTime();
		if (actTime == null) {
			// internal event list is empty
			// --> ausstehende Aktivierung l�schen (kann eintreten, wenn letzter
			// Eintrag auf interner event list gecancelt wurde)
			if (myUpdate.isScheduled()) { // this
				// skipTraceNote();
				myUpdate.cancel(); // this
			}
			return;
		}
		SimTime deltaT = SimTime.diff(actTime, currentTime());
		// myUpdate -> this
//		if (myUpdate.isScheduled()
//				&& !SimTime.isEqual(actTime, myUpdate.scheduledAt())) {
//			// vorhandene Aktivierung verschieben
//			// skipTraceNote();
//			myUpdate.reSchedule(deltaT); // this
//		} else if (!myUpdate.isScheduled()) {
			// neue Aktivierung erzeugen
			// skipTraceNote();
			myUpdate.schedule(this, deltaT);
			// this.schedule(new EnvironmentUpdate((MultiAgentModel)getModel()),
			// deltaT);
//		}
	}

	/**
	 * notifies the environment listeners of a change.
	 */
	private void notifyListeners(int eventType, Object eventContent) {
		if (listeners != null) {
			// Modifiziert v. Nick (20.11.): Nur 1 Event erzeugen und an alle
			// schicken
			EnvironmentEvent e = new EnvironmentEvent(this.getModel()
					.getExperiment(), eventType, eventContent);
			for (Iterator i = listeners.iterator(); i.hasNext();)
				((EnvironmentListener) i.next()).environmentChanged(e);
		}
	}

	/**
	 * returns true if the given member (agent or subgroup) is registered with
	 * this environment. If not so a warning is sent to console and error file.
	 */
//	private boolean checkMember(GroupMember m) {
//		// Try to find member where it belongs
//		if (m instanceof Agent) {
//			if (agents.hasMember(m))
//				return true;
//		} else if (m instanceof Group) {
//			if (groups.containsValue(m))
//				return true;
//		}
//		// If not found send warning
//		sendWarning(
//				"GroupMember " + m + " is not registered with this"
//						+ " Envirionment",
//				"Environment.checkMember(GroupMember)",
//				"Group member has not entered this environment yet.",
//				"Make sure an agent enters the environment before joining any "
//						+ "groups. Make sure a Group is created using the createGroup "
//						+ "method before joining any supergroups.");
//		System.out.println("** Error: GroupMember " + m + " is not registered "
//				+ "with environment.");
//		return false;
//	}

	// ----------------------------------------------------------- nested
	// classes

	/**
	 * An environment update event just does what its name suggests: it tells
	 * the environment to do an update at the scheduled event time.
	 */
	private static class EnvironmentUpdate extends Event {

		/**
		 * constructs a new environment update event within the given model.
		 */
		public EnvironmentUpdate(Model model) {
			super(model, "EnvironmentUpdate", true);
		}

		/**
		 * the event routine for environment update events just calls the
		 * environment's (entity <code>who</code>) <code>update()</code>
		 * method.
		 * 
		 * @param who
		 *            the entity (environment) to whom this event belongs. If
		 *            the entity is not an environment nothing happens.
		 */
		public void eventRoutine(Entity who) {
			try {
				((Environment) who).update();
			} catch (ClassCastException e) {
				// who is not an environment
			}
		}
	} /* end of class EnvironmentUpdate */
} /* end of class Environment */
