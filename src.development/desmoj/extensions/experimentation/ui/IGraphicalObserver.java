package desmoj.extensions.experimentation.ui;

import java.awt.Component;

/**
 * an Interface for graphical observers in the experiment launcher. 
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Daniel Ferreira dos Santos, Harbir Singh Bharaj
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

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