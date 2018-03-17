package desmoj.extensions.experimentation.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

//import com.sun.xml.internal.bind.v2.TODO;

import desmoj.extensions.visualization3d.ExperimentRunnerApplication3D;
import desmoj.extensions.visualization3d.SpatialVis3DGraphicalObserver;
import desmoj.extensions.visualization3d.VisualizationControl;

/**
 * Desktop for graphical observers in the experiment starter (a JDesktopPane).
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Nicolas Knaak
 * @author modified by Gunnar Kiesel, Daniel Ferreira dos Santos, Harbir Singh Bharaj
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
public class ObserverDesktop extends JDesktopPane implements
		GraphicalObserverContext {

	/** Automatic horizontal shift of window position */
	private static final int X_SHIFT = 25;

	/** Automatic vertical shift of window psoition */
	private static final int Y_SHIFT = 25;

	/** Graphical observers indexed by their name */
	private HashMap graphicalObservers = null;

	/** Horizontal position of next observer window to display */
	private int xOffset = 0;

	/** Vertical position of next observer window to display */
	private int yOffset = 0;
	
	static int speeed=0;
	
	public ExperimentRunnerApplication3D speed1 = new ExperimentRunnerApplication3D();
	
	public int _speed = 5;

	/**
	 * Adds a new graphical observer to the desktop
	 * 
	 * @param o
	 *            the new graphical observer
	 */
	public void add(IGraphicalObserver o) {
		if (graphicalObservers == null)
		
		{
			graphicalObservers = new HashMap();
		JInternalFrame frame = new JInternalFrame(o.getName(), true, false,
				true, true);
		
		
		
		graphicalObservers.put(o, frame);
	
		
		// Buttons for the 3D-Visualzation
		
		final JLabel speed = new JLabel("" + _speed);
		JButton increaseSpeed = new JButton("+");
		JButton decreaseSpeed = new JButton("-");
		
		JPanel speedPanelIntern = new JPanel();
		speedPanelIntern.add(increaseSpeed, FlowLayout.LEFT);
		speedPanelIntern.add(speed, FlowLayout.CENTER);
		speedPanelIntern.add(decreaseSpeed, FlowLayout.RIGHT);
		JPanel speedPanel = new JPanel();
		speedPanel.add(speedPanelIntern);
		
		// increase the speed of the visualisation and also shows the new speed
		
		increaseSpeed.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  
			  _speed = _speed + 1;
			  ExperimentRunnerApplication3D.getInstance().getVisualizationControl().setExecutionSpeed(_speed);
			  speed.setText("" + _speed);
		  }

		});
	
		// decreases the speed of the visualisation and also shows the new speed
		decreaseSpeed.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  if (_speed >=1.1){
				 
			  _speed = _speed - 1;
			  }
			  ExperimentRunnerApplication3D.getInstance().getVisualizationControl().setExecutionSpeed(_speed);
			  speed.setText("" + _speed);
			  ExperimentRunnerApplication3D.getInstance().getExperiment().setExecutionSpeedRate(_speed);
			 
		  }

		});
			
		
		// only if the getGui() methods delievers  a 3D-Visualisation, adds the buttons to the window
		
		if (o.getGUI() == SpatialVis3DGraphicalObserver._scrollPane)
		{
			frame.getContentPane().add(speedPanel, BorderLayout.SOUTH);
			frame.getContentPane().add(o.getGUI(),BorderLayout.CENTER);
		}
		else
		{
			frame.getContentPane().add(o.getGUI(),BorderLayout.CENTER);
		}
			
	this.add(frame);
	
		frame.setLocation(xOffset, yOffset);
		xOffset += X_SHIFT;
		yOffset += Y_SHIFT;
		}
		
		
	}
	
	public int getPresentSpeed(){
		return _speed;
	}
	

	/**
	 * Removes graphical observer from the desktop
	 * 
	 * @param o
	 *            the graphical observer to remove
	 */
	public void remove(IGraphicalObserver o) {
		JInternalFrame frame = getFrame(o);
		if (frame != null) {
			frame.setVisible(false);
			this.remove(frame);
			graphicalObservers.remove(o);
		}
	}

	/**
	 * Sets the given graphical observer (in)visible
	 * 
	 * @param o
	 *            a graphical observer registered with teh desktop
	 * @param visible
	 *            visibility flag (true = visible).
	 */
	public void setVisible(IGraphicalObserver o, boolean visible) {
		JInternalFrame frame = getFrame(o);
		if (frame != null)
			frame.setVisible(visible);
	}

	/**
	 * Returns an array of graphical observers registered with the desktop.
	 * 
	 * @return registered observers as an array.
	 */
	public IGraphicalObserver[] getChildren() {
		if (graphicalObservers == null)
			return new IGraphicalObserver[0];
		Object[] o = graphicalObservers.keySet().toArray();
		IGraphicalObserver[] children = new IGraphicalObserver[o.length];
		System.arraycopy(o, 0, children, 0, children.length);
		return children;
	}

	/**
	 * Sets the size of a registered graphical observer's window
	 * 
	 * @param o
	 *            the observer to change size
	 * @param width
	 *            new window width
	 * @param height
	 *            new window height
	 */
	public void setSize(IGraphicalObserver o, int width, int height) {
		JInternalFrame frame = getFrame(o);
		if (frame != null)
			frame.setSize(width, height);
	}

	/**
	 * Sets the position of a registered graphical observer's window.
	 * 
	 * @param o
	 *            the observer to change position
	 * @param xLoc
	 *            new horizontal position
	 * @param yLoc
	 *            new vertical position
	 */
	public void setLocation(IGraphicalObserver o, int xLoc, int yLoc) {
		JInternalFrame frame = getFrame(o);
		if (frame != null)
			frame.setLocation(xLoc, yLoc);
	}

	/** Resets the position offset for the next window to zero. */
	public void resetOffset() {
		xOffset = 0;
		yOffset = 0;
	}

	/**
	 * Updates the given graphical observer's display
	 * 
	 * @param o
	 *            A graphical observer registered with the desktop
	 */
	public void update(IGraphicalObserver o) {
		
	    //JInternalFrame f = (JInternalFrame) graphicalObservers.get(o);
		//if (f != null) {
		//}
	    //
	    // Fragmentary code? Inserted comments since no effect (JG, 11.03.09)
	}

	/**
	 * Returns the internal frame representing the given observer.
	 * 
	 * @param o
	 *            a registered observer.
	 * @return a JInternalFrame
	 */
	private JInternalFrame getFrame(IGraphicalObserver o) {
		if (graphicalObservers == null)
			return null;
		else
			return (JInternalFrame) graphicalObservers.get(o);
	}
}