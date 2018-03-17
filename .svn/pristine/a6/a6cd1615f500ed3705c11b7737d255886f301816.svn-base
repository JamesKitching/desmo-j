package desmoj.extensions.space2D.gui;

import desmoj.core.simulator.Experiment;
import desmoj.core.util.SimRunEvent;
import desmoj.demo.balticSea.BalticSeaModel;
import desmoj.extensions.space2D.DisplayableModel;
import desmoj.extensions.space2D.space.Environment;

/**
 * An event emitted by the environment when something changes in the space
 * model.
 * 
 * @author Nick Knaak
 * @version 1.0
 */

public class EnvironmentEvent extends SimRunEvent {

	private static final long serialVersionUID = 1L;

	/** Indicates that an agent has entered */
	public static final int AGENT_ENTERED = 1;

	/** Indicates that an agent has left */
	public static final int AGENT_LEFT = 2;

	/** Indicates that an agent has changed its position */
	public static final int AGENT_MOVED = 3;

	/** Indicates that an attribute changed */
	public static final int ATTRIBUTE_CHANGED = 4;

	public static final int SIGNAL_SENT = 5;

	private int type;

	private Object content;

	/** Creates a new environment event */
	public EnvironmentEvent(Experiment source, int type, Object content) {
		super(source);
		this.type = type;
		this.content = content;
	}

	/** Returns the type of event (i. e. what happened) */
	public int getType() {
		return type;
	}

	/** Returns the event's content (e.g. the agent who has moved) */
	public Object getContent() {
		return content;
	}

	/** Returns the environment object that emitted this event */
	public Environment getEnvironment() {
		return ((DisplayableModel) getModel()).getEnvironment();
	}

}