package desmoj.extensions.space2D.gui;

import java.util.Map;

/**
 * interface indicating that the objects of the implementing class can be
 * inspected by inspectors. Every inspectable must return a name and type for
 * use in the title bar of the inspector window and a list of inspectable
 * attributes to be displayed in the table part of the inspector window.
 * 
 * @dependency desmoj.gui.ProbeMap
 */
public interface Inspectable {

	/**
	 * returns the name of this inspectable object.
	 */
	public String getName();

	/**
	 * returns the type of this inspectable object. This will probably be the
	 * class name (without packages).
	 */
	public String getType();

	/**
	 * returns the inspectable attributes as a Map. The attributes need not be
	 * fields declared directly in the class of the inspectable object. Instead,
	 * they may e.g. be inherited from superclasses or be declared in used
	 * classes.
	 */
	public Map getAccessPoints();
}