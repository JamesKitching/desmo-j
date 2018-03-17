package desmoj.extensions.experimentation.ui;

import java.awt.Component;

public interface IGraphicalObserver {

	/**
	 * Returns the context this observer is displayed in
	 * 
	 * @return The context (e.g. a JDesktop component).
	 */
	public abstract GraphicalObserverContext getContext();

	/**
	 * Should return the graphical observer's main GUI component
	 * 
	 * @return an AWT or Swing component.
	 */
	public abstract Component getGUI();

	/** Registers the observer with the context */
	public abstract void register();

	/** Deregisters the observer from the context */
	public abstract void deregister();

	/** Sets the observer visible withing the context */
	public abstract void setVisible(boolean visible);

	/**
	 * Sets the observer's main window's size.
	 * 
	 * @param width
	 *            window width
	 * @param height
	 *            window height
	 */
	public abstract void setSize(int width, int height);

	/**
	 * Sets the position of the observer's main window's upper left edge.
	 * 
	 * @param x
	 *            horizontal position
	 * @param y
	 *            vertical position
	 */
	public abstract void setLocation(int x, int y);

	/**
	 * Returns the observer's name
	 * 
	 * @return name
	 */
	public abstract String getName();

	/**
	 * Requests an update of the observer's display from the context.
	 */
	public abstract void update();

}