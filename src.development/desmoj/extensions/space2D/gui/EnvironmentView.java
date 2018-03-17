package desmoj.extensions.space2D.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import desmoj.core.util.SimClockListener;
import desmoj.core.util.SimRunEvent;
import desmoj.extensions.experimentation.ui.GraphicalObserver;
import desmoj.extensions.experimentation.ui.GraphicalObserverContext;
import desmoj.extensions.space2D.space.ComparableAttribute;
import desmoj.extensions.space2D.space.Environment;
import desmoj.extensions.space2D.space.Position;
import desmoj.extensions.space2D.space.Space;

// zeigt die r�umliche Umgebung (=GraphView + AgentViews + ...) auf
// einer JLayeredPane in einer JScrollPane
// am rechten Fensterrand eine "Toolbox"
/**
 * An environment view displays the spatial environment (space, agents, objects
 * and some attributes). It uses a JLayeredPane inside a JScrollPane to handle
 * the space view and the agent / object views.
 */
public class EnvironmentView extends GraphicalObserver implements
		EnvironmentListener, SimClockListener {
	// ///////////// ATTRIBUTE ///////////////////////////////////////
	private static final int ICON_SIZE = 8;

	/** constant defining the default window width. */
	private static final int DEFAULT_WIN_WIDTH = 350;

	/** constant defining the default window height. */
	private static final int DEFAULT_WIN_HEIGHT = 300;

	/**
	 * contant defining which layer of the layered pane displays the background.
	 */
	private static final int BACKGROUND_LAYER = 0;

	/**
	 * contant defining which layer of the layered pane displays the space view.
	 */
	private static final int SPACE_LAYER = 1;

	/**
	 * contant defining which layer of the layered pane displays the object
	 * views.
	 */
	private static final int OBJECT_LAYER = 8;

	/**
	 * contant defining which layer of the layered pane displays the agent
	 * views.
	 */
	private static final int AGENT_LAYER = 10;

	/** reference to the model environment. */
	private Environment env;

	/** the space view. */
	private SpaceView spaceView;

	/** The agent view */
//	private AgentPlane agentView;

	/** the model-view transformer obtained from the space view. */
	private ModelViewTransformer mvTransformer;

	/** the scroll pane for the main part of the window. */
	private JScrollPane scrollPane;

	private int updateCount = 0;

	private int updateIntervall = 3;

// SKR BEGIN - Macro or Micro -Images for agents	
	private boolean macroImages;
// SKR END	
	
	
	/** Inner class for a panel to draw the environment onto */
	class EnvironmentPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		EnvironmentPanel() {
			handleEvents();
		}

		public void paintComponent(Graphics g) {
			if (spaceVisible)
				spaceView.paintComponent(g);
//			if (agentsVisible)
//				agentView.paintComponent(g);
		}

		void handleEvents() {
			super.addMouseListener(new MouseAdapter() {
				// inner class
				public void mouseClicked(MouseEvent event) {
					// System.out.println("Mausklick f�r EnvPanel an
					// Koordinaten " + event.getX() + ", " + event.getY());
					if (spaceVisible) {
						EventListener[] list = spaceView
								.getListeners(MouseListener.class);
						for (int i = 0; i < list.length; i++) {
							((MouseListener) list[0]).mouseClicked(event);
						}
					}
//					if (agentsVisible) {
//						EventListener[] list = agentView
//								.getListeners(MouseListener.class);
//						for (int i = 0; i < list.length; i++) {
//							((MouseListener) list[0]).mouseClicked(event);
//						}
//					}
				}
			});
		}
	};

	/** the environment panel */
	private EnvironmentPanel layeredPane = new EnvironmentPanel();

	/** Flag indicating if space layer is drawn */
	private boolean spaceVisible = true;

	/** Flag indicating if agent layer is drawn */
	private boolean agentsVisible = false;

	/** a plane white background for the main part of the window. */
	private JPanel background;

	/** the tool box at the right side of the window. */
	private Box toolBox;

	/** check box for the space layer. */
	private JCheckBox checkSpace;

	/** check box for the agents layer. */
	private JCheckBox checkAgents;

	/** the GUI */
	private JPanel myGUI = new JPanel();

	// ---- provisorisch
	/** a (provisional) update button ( */
	private JButton updateButton;

	/** the window width in pixel */
	private int winWidth;

	/** the window height in pixel */
	private int winHeight;

	/** true if something in the environment has changed and redraw is necessary */
	private boolean changed;

	/** The display update thread */
	Runnable displayUpdate;
	
	private Image backgroundImage;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////
	/**
	 * constructs a new environment view for the given environment. Using
	 * default values for window dimensions and frame title.
	 * 
	 * @param env
	 *            the model environment represented by this environment view
	 * @param name
	 *            the window title
	 * @param winWidth
	 *            the window width in pixel
	 * @param winHeight
	 *            the window height in pixel
	 * @param context
	 *            the graphical observer context in which this view is embedded
	 */
	public EnvironmentView(Environment env, String name, int winWidth,
			int winHeight, GraphicalObserverContext context) {
		this(env, name, winWidth, winHeight, context, null, null, 0);
	}

	/**
	 * constructs a new environment view for the given environment with the
	 * given window dimensions and the given name.
	 * 
	 * @param env
	 *            the model environment represented by this environment view
	 * @param name
	 *            the window title
	 * @param winWidth
	 *            the window width
	 * @param winHeight
	 *            the window height
	 * @param context
	 *            the graphical observer context in which this view is embedded
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public EnvironmentView(Environment env, String name, int winWidth,
			int winHeight, GraphicalObserverContext context,
			ComparableAttribute attr, Color color, int numClasses) {
		super(name, context);
		// modellbestimmte Komponenten initialisieren
		this.env = env;
		Space space = env.getSpace();
		if (attr != null) {
// SKR - BEGIN - Substracted 150 for better display
			this.spaceView = space.getSpaceView(winWidth - 150, winHeight - 150, attr,
					color, numClasses);
		} else {
// SKR - BEGIN - Substracted 150 for better display			
			this.spaceView = space.getSpaceView(winWidth - 150, winHeight - 150); // ##abziehen:
			// toolbox-breite!
		}
		// Init space view and transformer
		spaceView.setContext(context);
		mvTransformer = spaceView.getTransformer();
		// Init agent view with transformer and size
//		agentView = new AgentPlane(mvTransformer, ICON_SIZE);
// SKR - BEGIN - Substracted 150 for better display
//		agentView.setBounds(0, 0, winWidth - 150, winHeight - 150);
//		agentView.setContext(context);
		// Init layered pane showing environment */
		layeredPane.setPreferredSize(this.spaceView.getPreferredSize());
		layeredPane.setBackground(Color.white);
		// Wrap scroll pane around layered pane.
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(layeredPane);
		// create toolbox
		this.checkSpace = new JCheckBox("Space", true);
		this.checkAgents = new JCheckBox("Agents", true);
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
		checkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(), "Layer"));
		checkPanel.add(this.checkSpace);
//		checkPanel.add(this.checkAgents);
		this.toolBox = Box.createVerticalBox();
		this.toolBox.add(Box.createVerticalStrut(10));
		this.toolBox.add(checkPanel);
		this.toolBox.add(Box.createVerticalGlue());
		// ---- provisorisch -----
		this.updateButton = new JButton("update");
		this.toolBox.add(this.updateButton);
		this.toolBox.add(Box.createVerticalStrut(10));
		// alles in den Frame tun mit BorderLayout
		myGUI.setLayout(new BorderLayout());
		myGUI.add(this.scrollPane, BorderLayout.CENTER);
		myGUI.add(this.toolBox, BorderLayout.EAST);
		// event-handling
		// - f�rs gesamte Fenster
		myGUI.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// Hintergrund der layeredPane an die Fenstergr��e anpassen
				// ehemals background.setBounds...
				layeredPane.setBounds(0, 0, e.getComponent().getWidth(), e
						.getComponent().getHeight());
			}
		});
		// - f�r die Checkboxen
		this.checkAgents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agentsVisible = checkAgents.isSelected();
				myGUI.repaint();
			}
		});
		this.checkSpace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spaceVisible = checkSpace.isSelected();
				myGUI.repaint();
			}
		});
		// - f�r den update-Button --------- provisorisch
		this.updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myGUI.repaint();
			}
		});
		// init display update object
		displayUpdate = new Runnable() {
			public void run() {
				layeredPane.paintImmediately(layeredPane.getX(), layeredPane
						.getY(), layeredPane.getWidth(), layeredPane
						.getHeight());
			}
		};
		// for graphical observer
		register();
		
//SKR - BEGIN - Re-sized for better viewing quality	
		setSize(winWidth - 100, winHeight + 30);
//SKR - END			
		setVisible(true);
	}
	
	/**
	 * constructs a new environment view for the given environment with the
	 * given window dimensions and the given name.
	 * 
	 * @param env
	 *            the model environment represented by this environment view
	 * @param name
	 *            the window title
	 * @param winWidth
	 *            the window width
	 * @param winHeight
	 *            the window height
	 * @param context
	 *            the graphical observer context in which this view is embedded
	 * @param attr
	 *            the comparable attribute to be mapped to a color scale
	 * @param color
	 *            the base color of the color scale
	 * @param numClasses
	 *            the number of classes (elements of the color scale)
	 */
	public EnvironmentView(Environment env, String name, int winWidth,
			int winHeight, GraphicalObserverContext context,
			ComparableAttribute attr, Color color, int numClasses, Image backgroundImage) {
		super(name, context);
		// modellbestimmte Komponenten initialisieren
		this.env = env;
		Space space = env.getSpace();
		if (backgroundImage != null) {
			if (attr != null) {
				// SKR - BEGIN - Substracted 150 for better display
							this.spaceView = space.getSpaceView(winWidth - 150, winHeight - 150, attr,
									color, numClasses, backgroundImage);
						} else {
				// SKR - BEGIN - Substracted 150 for better display			
							this.spaceView = space.getSpaceView(winWidth - 150, winHeight - 150, backgroundImage); // ##abziehen:
							// toolbox-breite!
						}
		}
		else {
			if (attr != null) {
// SKR - BEGIN - Substracted 150 for better display
				this.spaceView = space.getSpaceView(winWidth - 150, winHeight - 150, attr,
						color, numClasses, null);
			} else {
// SKR - BEGIN - Substracted 150 for better display			
				this.spaceView = space.getSpaceView(winWidth - 150, winHeight - 150, null); // ##abziehen:
				// toolbox-breite!
			}			
		}
		// Init space view and transformer
		spaceView.setContext(context);
		mvTransformer = spaceView.getTransformer();
		// Init agent view with transformer and size
//		agentView = new AgentPlane(mvTransformer, ICON_SIZE);
// SKR - BEGIN - Substracted 150 for better display
//		agentView.setBounds(0, 0, winWidth - 150, winHeight - 150);
//		agentView.setContext(context);
		// Init layered pane showing environment */
		layeredPane.setPreferredSize(this.spaceView.getPreferredSize());
		layeredPane.setBackground(Color.white);
		// Wrap scroll pane around layered pane.
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(layeredPane);
		// create toolbox
		this.checkSpace = new JCheckBox("Space", true);
		this.checkAgents = new JCheckBox("Agents", true);
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
		checkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(), "Layer"));
		checkPanel.add(this.checkSpace);
//		checkPanel.add(this.checkAgents);
		this.toolBox = Box.createVerticalBox();
		this.toolBox.add(Box.createVerticalStrut(10));
		this.toolBox.add(checkPanel);
		this.toolBox.add(Box.createVerticalGlue());
		// ---- provisorisch -----
		this.updateButton = new JButton("update");
		this.toolBox.add(this.updateButton);
		this.toolBox.add(Box.createVerticalStrut(10));
		// alles in den Frame tun mit BorderLayout
		myGUI.setLayout(new BorderLayout());
		myGUI.add(this.scrollPane, BorderLayout.CENTER);
		myGUI.add(this.toolBox, BorderLayout.EAST);
		// event-handling
		// - f�rs gesamte Fenster
		myGUI.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// Hintergrund der layeredPane an die Fenstergr��e anpassen
				// ehemals background.setBounds...
				layeredPane.setBounds(0, 0, e.getComponent().getWidth(), e
						.getComponent().getHeight());
			}
		});
		// - f�r die Checkboxen
		this.checkAgents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agentsVisible = checkAgents.isSelected();
				myGUI.repaint();
			}
		});
		this.checkSpace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spaceVisible = checkSpace.isSelected();
				myGUI.repaint();
			}
		});
		// - f�r den update-Button --------- provisorisch
		this.updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myGUI.repaint();
			}
		});
		// init display update object
		displayUpdate = new Runnable() {
			public void run() {
				layeredPane.paintImmediately(layeredPane.getX(), layeredPane
						.getY(), layeredPane.getWidth(), layeredPane
						.getHeight());
			}
		};
		// for graphical observer
		register();
		
//SKR - BEGIN - Re-sized for better viewing quality	
		setSize(winWidth - 100, winHeight + 30);
//SKR - END			
		setVisible(true);
	}

	// ///////////// METHODEN ////////////////////////////////////////
	/** Repaints layers of the environment view */
	public void updateView() {
		if (changed) {
			setChanged(false);
			// This update strategy was inspired by the RePast toolkit
			SwingUtilities.invokeLater(displayUpdate);
		}
	}

	// --- Interface EnvironmentListener
	/** On change of the observed environment */
	public void environmentChanged(EnvironmentEvent e) {
		// Observe my environment only !!
		if (e.getEnvironment() != env)
			return;
		int eventType = e.getType();
		Object eventContent = e.getContent();
		switch (eventType) {
//		case EnvironmentEvent.AGENT_ENTERED:
//			agentView.addAgent((SituatedAgent) eventContent);
//			setChanged(true);
//			break;
//		case EnvironmentEvent.AGENT_LEFT:
//			agentView.removeAgent((SituatedAgent) eventContent);
//			setChanged(true);
//			break;
//		case EnvironmentEvent.AGENT_MOVED:
//			agentView.moveAgent((SituatedAgent) eventContent);
//			setChanged(true);
//			break;
		case EnvironmentEvent.ATTRIBUTE_CHANGED:
			// tell space view where attribute changed
			setChanged(true);
			Object[] content = (Object[]) eventContent;
			spaceView.attributeChanged((Position) content[0],
					(Comparable) content[1]);
			spaceView.requestRepaint();
			break;
		default:
// SKR: Switched off warnings.
			System.out.println("** Unknown type of Environment event!");
// SKR			
		}
	}

	/** returns the gui */
	public Component getGUI() {
		return myGUI;
	}

	public void setUpdateIntervall(int i) {
		updateIntervall = i;
	}

	public int getUpdateIntervall() {
		return updateIntervall;
	}

	private synchronized void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void clockAdvanced(SimRunEvent e) {
		if (updateCount == 0)
			updateView();
		updateCount = (updateCount + 1) % updateIntervall;
	}
}
