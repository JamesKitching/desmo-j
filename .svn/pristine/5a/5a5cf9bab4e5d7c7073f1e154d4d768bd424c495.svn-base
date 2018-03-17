package desmoj.extensions.space2D.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import desmoj.extensions.experimentation.ui.AccessPointTableModel;
import desmoj.extensions.experimentation.ui.AttributeTableEditor;
import desmoj.extensions.experimentation.ui.GraphicalObserver;
import desmoj.extensions.experimentation.ui.GraphicalObserverContext;

/**
 * A window to display an situated object's properties represented by probes
 * 
 * @author Ruth Meyer, Nicolas Knaak
 * @version 1.0
 */

public class Inspector extends GraphicalObserver {

	// GUI components
	BorderLayout borderLayout1 = new BorderLayout();

	JPanel buttonPanel = new JPanel();

	JButton getValueButton = new JButton();

	JButton setValueButton = new JButton();

	JButton closeButton = new JButton();

	JScrollPane tableScrollPane = new JScrollPane();

	JTable probeMapTable = new JTable();

	JPanel myGUI = new JPanel();

	// data
	private Inspectable iObj;

	/**
	 * constructs a new inspector for the given inspectable object. The
	 * inspector will be displayed on the specified graphical observer context.
	 */
	public Inspector(Inspectable iObj, GraphicalObserverContext c) {
		super(iObj.getType() + ": " + iObj.getName(), c);
		try {
			this.iObj = iObj;
			jbInit();
			initTable();
			register();
			setVisible(true);
			setSize(300, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// //// Methoden ////////////////////////////////////

	/** Returns the inspector's GUI */
	public Component getGUI() {
		return myGUI;
	}

	/** Retrieves probe values from the object */
	protected void getValues() {
		AccessPointTableModel p = (AccessPointTableModel) probeMapTable
				.getModel();
		p.getValues();
	}

	/** Updates probe values of the object */
	protected void setValues() {
		AccessPointTableModel p = (AccessPointTableModel) probeMapTable
				.getModel();
		p.setValues();
	}

	// ------------------------------------------------------------
	// Hilfsmethoden

	private void initTable() {
		probeMapTable
				.setModel(new AccessPointTableModel(iObj.getAccessPoints()));
		probeMapTable
				.setDefaultEditor(Object.class, new AttributeTableEditor());
	}

	private void jbInit() throws Exception {
		myGUI.setLayout(borderLayout1);
		getValueButton.setText("Update");
		getValueButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getValues();
			}
		});
		setValueButton.setText("Apply");
		setValueButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setValues();
			}
		});
		closeButton.setText("Close");
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inspector.this.setVisible(false);
			}
		});
		myGUI.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(getValueButton, null);
		buttonPanel.add(setValueButton, null);
		buttonPanel.add(closeButton, null);
		myGUI.add(tableScrollPane, BorderLayout.CENTER);
		tableScrollPane.getViewport().add(probeMapTable, null);
	}
}