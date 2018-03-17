package desmoj.extensions.space2D.gui;

import java.awt.Color;

import javax.swing.ImageIcon;

import desmoj.extensions.space2D.space.Point;

/**
 * interface to indicate that objects of the implementing class can be displayed
 * in an environment view. Every displayable object knows (a) where it is to be
 * displayed --> method getPoint() (b) how it is to be displayed --> methods
 * getColor(), getIcon() (c) how it can be described --> method getToolTipText()
 */
public interface Displayable {

	/**
	 * returns the coordinates of this displayable object's position in model
	 * space. These will be converted to screen space by the displayable
	 * object's view.
	 */
	public Point getPoint();

	/**
	 * returns the colour used to render this displayable object.
	 */
	public Color getColor();

	/**
	 * returns the icon used to represent this displayable object on the screen
	 * or <code>null</code> if no icon is available.
	 */
	public ImageIcon getIcon();

	/**
	 * returns the description used to display in a tool tip for this
	 * displayable object.
	 */
	public String getToolTipText();

}