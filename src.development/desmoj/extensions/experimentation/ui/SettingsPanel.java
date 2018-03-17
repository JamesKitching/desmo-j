package desmoj.extensions.experimentation.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import desmoj.core.simulator.Experiment;
import desmoj.core.util.AccessPoint;
import desmoj.extensions.experimentation.util.AccessUtil;

/**
 * A GUI panel containing 2 tables for model and experiment parameters.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Nicolas Knaak
 * 
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *         
 * @author  modified by Binhua Han, Xiufeng Li 30.03.2011 * 
 */
public class SettingsPanel extends JPanel {
	/** Layout of main panel */
	GridLayout gridLayout1 = new GridLayout();
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The Output classescollections, the classnmae to create the Output files (debug, report, error,
	 * trace) 
	 */
	private static final String[] traceOutputType =
	{ "desmoj.core.report.HTMLTraceOutput", null, null,
			"desmoj.extensions.report.ExcelTraceOutput",
			"desmoj.extensions.report.PdfTraceOutput",
			"desmoj.extensions.xml.report.XMLTraceOutput" };

	private static final String[] reportOutputType =
	{ "desmoj.core.report.HTMLReportOutput", null, null,
			"desmoj.extensions.report.ExcelReportOutput",
			"desmoj.extensions.report.PdfReportOutput",
			"desmoj.extensions.xml.report.XMLReportOutput" };

	private static final String[] debugOutputType =
	{ "desmoj.core.report.HTMLDebugOutput", null, null,
			"desmoj.extensions.report.ExcelDebugOutput",
			"desmoj.extensions.report.PdfDebugOutput",
			"desmoj.extensions.xml.report.XMLDebugOutput" };

	private static final String[] errorOutputType =
	{ "desmoj.core.report.HTMLErrorOutput", null, null,
			"desmoj.extensions.report.ExcelErrorOutput",
			"desmoj.extensions.report.PdfErrorOutput",
			"desmoj.extensions.xml.report.XMLErrorOutput" };

	/** Panel for model data */
	JPanel modelPanel = new JPanel();
	
	/** Components for outputstype*/
	private JDialog fileDlg;
	private JCheckBox jCheckBox4;
	private JCheckBox jCheckBox2;
	private JPopupMenu jPopupMenu2;
	private JMenuItem jMenuItem1;
	private JPopupMenu jPopupMenu1;
	private JCheckBox jCheckBox24;
	private JCheckBox jCheckBox23;
	private JCheckBox jCheckBox22;
	private JCheckBox jCheckBox21;
	private JCheckBox jCheckBox20;
	private JCheckBox jCheckBox19;
	private JCheckBox jCheckBox18;
	private JCheckBox jCheckBox17;
	private JCheckBox jCheckBox16;
	private JCheckBox jCheckBox15;
	private JCheckBox jCheckBox14;
	private JCheckBox jCheckBox13;
	private JCheckBox jCheckBox12;
	private JCheckBox jCheckBox11;
	private JCheckBox jCheckBox10;
	private JCheckBox jCheckBox9;
	private JCheckBox jCheckBox8;
	private JCheckBox jCheckBox7;
	private JCheckBox jCheckBox6;
	private JCheckBox jCheckBox5;
	private JCheckBox jCheckBox3;
	private JCheckBox jCheckBox1;
	private JLabel jLabel5;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JButton jButton4;
	private JScrollPane jScrollPane1;
	private JTextField jTextField1;
	private JLabel jLabel1;
	private JPanel output;

	/** Panel for experiment data */
	JPanel expPanel = new JPanel();

	/** Layout of model panel */
	BorderLayout borderLayout1 = new BorderLayout();

	/** Layout of experiment panel */
	BorderLayout borderLayout2 = new BorderLayout();

	/** Title of model panel */
	JLabel modelLabel = new JLabel();

	/** Title of experiment panel */
	JLabel expLabel = new JLabel();

	/** Scroll pane for model parameter table */
	JScrollPane modelScrollPane = new JScrollPane();
	private JMenuItem jMenuItem4;
	private JPopupMenu jPopupMenu4;
	private JMenuItem jMenuItem3;
	private JPopupMenu jPopupMenu3;
	private JMenuItem jMenuItem2;

	/** Scroll pane for experiment settings table */
	JScrollPane expScrollPane = new JScrollPane();

	/** Table for model parameters */
	JTable modelTable = new JTable();

	/** Table for experiment parameters */
	JTable expTable = new JTable();

	/** FileChosser window for outputpath*/
	private JFileChooser jfc;

	/** Pdf Password configuration for every outtype */
	protected SetPdfPassword ppwTrace;
	protected SetPdfPassword ppwReport;
	protected SetPdfPassword ppwError;
	protected SetPdfPassword ppwDebug;

	/**
	 * Die Checkboxes auf der gleichen Zeile werden jeweils in ihres eignes
	 * ArrayList versammelt.
	 */
	private ArrayList<JCheckBox> debugChekboxlist = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> errorChekboxlist = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> traceChekboxlist = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> reportChekboxlist = new ArrayList<JCheckBox>();

	/** Experiment setting "referenceUnit" */
	public final static String EXP_REF_UNIT = "referenceUnit";

	/** Creates a new settings panel. */
	public SettingsPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Inits the user interface */
	private void jbInit() throws Exception{
		this.setLayout(gridLayout1);
		this.setPreferredSize(new java.awt.Dimension(1227, 384));
		expPanel.setLayout(borderLayout1);
		modelPanel.setLayout(borderLayout2);
		modelLabel.setText("Model Parameters");
		expLabel.setText("Experiment Parameters");
		modelScrollPane.setBorder(null);
		expScrollPane.setBorder(null);
		expTable.setBackground(Color.white);
		modelTable.setBackground(Color.white);
		modelPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		expPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(modelPanel, null);
		modelPanel.setPreferredSize(new java.awt.Dimension(845, 423));
		modelPanel.add(modelLabel, BorderLayout.NORTH);
		modelPanel.add(modelScrollPane, BorderLayout.CENTER);
		modelScrollPane.setPreferredSize(new java.awt.Dimension(591, 309));
		modelScrollPane.getViewport().add(modelTable, null);
		this.add(expPanel, null);
		expPanel.setPreferredSize(new java.awt.Dimension(845, 423));
		expPanel.add(expLabel, BorderLayout.NORTH);
		expPanel.add(expScrollPane, BorderLayout.CENTER);
		expScrollPane.setPreferredSize(new java.awt.Dimension(549, 51));
		expScrollPane.setSize(609, 100);
		{
			jScrollPane1 = new JScrollPane();
			expPanel.add(jScrollPane1, BorderLayout.SOUTH);
			jScrollPane1.setPreferredSize(new java.awt.Dimension(679, 262));
			{
				output = new JPanel();
				jScrollPane1.setViewportView(output);
				GridBagLayout outputLayout = new GridBagLayout();
				outputLayout.rowWeights = new double[]
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				outputLayout.rowHeights = new int[]
				{ 21, 21, 21, 21, -1, 16, 21 };
				outputLayout.columnWeights = new double[]
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				outputLayout.columnWidths = new int[]
				{ 135, 71, 50, 50, 50, 50, 50 };
				output.setPreferredSize(new java.awt.Dimension(572, 223));
				output.setLayout(outputLayout);
				{
					jLabel1 = new JLabel();
					output.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0,
							0.0, GridBagConstraints.WEST,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
					jLabel1.setText("  outputPath");
					jLabel1.setBackground(new java.awt.Color(255, 255, 255));
				}
				{
					jTextField1 = new JTextField();
					output.add(jTextField1, new GridBagConstraints(1, 0, 4, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
					jTextField1.setText("./");
					jTextField1.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							// TODO
							// System.out.println("get file");
						}
					});
				}

				{
					jfc = new JFileChooser();
					jfc.setDialogType(JFileChooser.OPEN_DIALOG);
					jfc.setDialogTitle("Open file...");
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

					jfc.addActionListener(new ActionListener()
					{
						private String fname;

						public void actionPerformed(ActionEvent e)
						{
							if (e.getActionCommand().equals(
									JFileChooser.APPROVE_SELECTION))
							{
								fname = jfc.getSelectedFile().getAbsolutePath();
								fileDlg.setVisible(false);
								jTextField1.setText(fname);
							} else if (e.getActionCommand().equals(
									JFileChooser.CANCEL_SELECTION))
							{
								fileDlg.setVisible(false);
							}
						}
					});

					fileDlg = new JDialog();
					fileDlg.getContentPane().add(jfc);
					fileDlg.setTitle("Open file...");
					fileDlg.setModal(true);
					fileDlg.setSize(500, 300);
				}
				{
					jButton4 = new JButton();
					output.add(jButton4, new GridBagConstraints(5, 0, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jButton4.setText("Select");
					jButton4.addMouseListener(new MouseAdapter()
					{
						public void mouseClicked(MouseEvent e)
						{

							fileDlg.setVisible(true);

						}
					});
				}
				{
					jLabel2 = new JLabel();
					output.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0,
							0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jLabel2.setText("debugOutputType");
					jLabel2.setBackground(new java.awt.Color(255, 255, 255));
				}
				{
					jLabel3 = new JLabel();
					output.add(jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0,
							0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jLabel3.setText("errorOutputType");
					jLabel3.setBackground(new java.awt.Color(255, 255, 255));
				}
				{
					jLabel4 = new JLabel();
					output.add(jLabel4, new GridBagConstraints(0, 5, 1, 1, 0.0,
							0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jLabel4.setText("reportOutputType");
					jLabel4.setBackground(new java.awt.Color(255, 255, 255));
				}
				{
					jLabel5 = new JLabel();
					output.add(jLabel5, new GridBagConstraints(0, 6, 1, 1, 0.0,
							0.0, GridBagConstraints.WEST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jLabel5.setText("traceOutputType");
					jLabel5.setBackground(new java.awt.Color(255, 255, 255));
				}
				{
					jCheckBox1 = new JCheckBox("HTML", true);
					output.add(jCheckBox1, new GridBagConstraints(1, 2, 1, 1,
							0.0, 0.0, GridBagConstraints.NORTH,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));

				}
				{
					jCheckBox2 = new JCheckBox("HTML", true);
					output.add(jCheckBox2, new GridBagConstraints(1, 3, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));

				}
				{
					jCheckBox3 = new JCheckBox("HTML", true);
					output.add(jCheckBox3, new GridBagConstraints(1, 5, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));

				}
				{
					jCheckBox4 = new JCheckBox("HTML", true);
					output.add(jCheckBox4, new GridBagConstraints(1, 6, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));

				}
				{
					jCheckBox5 = new JCheckBox();
					output.add(jCheckBox5, new GridBagConstraints(2, 2, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox5.setText("Oracle DB");
					jCheckBox5.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox5.isSelected() == true
									&& jCheckBox6.isSelected() == false
									&& jCheckBox7.isSelected() == false
									&& jCheckBox8.isSelected() == false)
							{
								orclconn(jCheckBox5);
							} else
							{
								// TODO
							}
						}
					});

				}
				{
					jCheckBox6 = new JCheckBox();
					output.add(jCheckBox6, new GridBagConstraints(2, 3, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox6.setText("Oracle DB");
					jCheckBox6.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox6.isSelected() == true
									&& jCheckBox5.isSelected() == false
									&& jCheckBox7.isSelected() == false
									&& jCheckBox8.isSelected() == false)
							{
								orclconn(jCheckBox6);
							} else
							{
								// TODO
							}
						}
					});
				}
				{
					jCheckBox7 = new JCheckBox();
					output.add(jCheckBox7, new GridBagConstraints(2, 5, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox7.setText("Oracle DB");

					jCheckBox7.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox7.isSelected() == true
									&& jCheckBox5.isSelected() == false
									&& jCheckBox6.isSelected() == false
									&& jCheckBox8.isSelected() == false)
							{
								orclconn(jCheckBox7);
							} else
							{
								// TODO
							}
						}
					});
				}
				{
					jCheckBox8 = new JCheckBox();
					output.add(jCheckBox8, new GridBagConstraints(2, 6, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox8.setText("Oracle DB");

					jCheckBox8.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox8.isSelected() == true
									&& jCheckBox5.isSelected() == false
									&& jCheckBox6.isSelected() == false
									&& jCheckBox7.isSelected() == false)
							{
								orclconn(jCheckBox8);
							} else
							{
								// TODO
							}
						}
					});
				}
				{
					jCheckBox9 = new JCheckBox();
					output.add(jCheckBox9, new GridBagConstraints(3, 2, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox9.setText("MySQL DB");
					jCheckBox9.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox9.isSelected() == true
									&& jCheckBox10.isSelected() == false
									&& jCheckBox11.isSelected() == false
									&& jCheckBox12.isSelected() == false)
							{
								mysqlconn(jCheckBox9);
							} else
							{
								// TODO
							}
						}
					});

				}
				{
					jCheckBox10 = new JCheckBox();
					output.add(jCheckBox10, new GridBagConstraints(3, 3, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox10.setText("MySQL DB");
					jCheckBox10.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox10.isSelected() == true
									&& jCheckBox9.isSelected() == false
									&& jCheckBox11.isSelected() == false
									&& jCheckBox12.isSelected() == false)
							{
								mysqlconn(jCheckBox10);
							} else
							{
								// TODO
							}
						}
					});
				}
				{
					jCheckBox11 = new JCheckBox();
					output.add(jCheckBox11, new GridBagConstraints(3, 5, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox11.setText("MySQL DB");
					jCheckBox11.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox11.isSelected() == true
									&& jCheckBox9.isSelected() == false
									&& jCheckBox10.isSelected() == false
									&& jCheckBox12.isSelected() == false)
							{
								mysqlconn(jCheckBox11);
							} else
							{
								// TODO
							}
						}
					});
				}
				{
					jCheckBox12 = new JCheckBox();
					output.add(jCheckBox12, new GridBagConstraints(3, 6, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox12.setText("MySQL DB");

					jCheckBox12.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (jCheckBox12.isSelected() == true
									&& jCheckBox9.isSelected() == false
									&& jCheckBox10.isSelected() == false
									&& jCheckBox11.isSelected() == false)
							{
								mysqlconn(jCheckBox12);
							} else
							{
								// TODO
							}
						}
					});
				}
				{
					jCheckBox13 = new JCheckBox();
					output.add(jCheckBox13, new GridBagConstraints(4, 2, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox13.setText("Excel");
				}
				{
					jCheckBox14 = new JCheckBox();
					output.add(jCheckBox14, new GridBagConstraints(4, 3, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox14.setText("Excel");
				}
				{
					jCheckBox15 = new JCheckBox();
					output.add(jCheckBox15, new GridBagConstraints(4, 5, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox15.setText("Excel");
				}
				{
					jCheckBox16 = new JCheckBox();
					output.add(jCheckBox16, new GridBagConstraints(4, 6, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox16.setText("Excel");
				}
				{
					jCheckBox17 = new JCheckBox();
					output.add(jCheckBox17, new GridBagConstraints(5, 2, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox17.setText("PDF");

					{
						jPopupMenu1 = new JPopupMenu();
						setComponentPopupMenu(jCheckBox17, jPopupMenu1);
						{
							jMenuItem1 = new JMenuItem();
							jPopupMenu1.add(jMenuItem1);
							jMenuItem1.setText("Set Password");

							// PDF Password Einstellung Fenster wird aufgerufen.
							jMenuItem1.addActionListener(new ActionListener()
							{

								private SetPdfPassword ppwDebug;

								public void actionPerformed(ActionEvent e)
								{

									ppwDebug = new SetPdfPassword(jCheckBox17);

									ppwDebug.pdfGUI();
									ppwDebug.setVisible(true);
									jCheckBox17.setSelected(true);

								}
							});
						}
					}
				}
				{
					jCheckBox18 = new JCheckBox();
					output.add(jCheckBox18, new GridBagConstraints(5, 3, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox18.setText("PDF");
					{
						jPopupMenu2 = new JPopupMenu();
						setComponentPopupMenu(jCheckBox18, jPopupMenu2);
						{
							jMenuItem2 = new JMenuItem();
							jPopupMenu2.add(jMenuItem2);
							jMenuItem2.setText("Set Password");

							// PDF Password Einstellung Fenster wird aufgerufen.
							jMenuItem2.addActionListener(new ActionListener()
							{

								public void actionPerformed(ActionEvent e)
								{

									ppwError = new SetPdfPassword(jCheckBox18);
									ppwError.pdfGUI();
									ppwError.setVisible(true);
									jCheckBox18.setSelected(true);

								}
							});
						}
					}
				}
				{
					jCheckBox19 = new JCheckBox();
					output.add(jCheckBox19, new GridBagConstraints(5, 5, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox19.setText("PDF");
					{
						jPopupMenu3 = new JPopupMenu();
						setComponentPopupMenu(jCheckBox19, jPopupMenu3);
						{
							jMenuItem3 = new JMenuItem();
							jPopupMenu3.add(jMenuItem3);
							jMenuItem3.setText("Set Password");

							// PDF Password Einstellung Fenster wird aufgerufen.
							jMenuItem3.addActionListener(new ActionListener()
							{

								public void actionPerformed(ActionEvent e)
								{

									ppwReport = new SetPdfPassword(jCheckBox19);
									ppwReport.pdfGUI();
									ppwReport.setVisible(true);
									jCheckBox19.setSelected(true);

								}
							});
						}
					}
				}
				{
					jCheckBox20 = new JCheckBox();
					output.add(jCheckBox20, new GridBagConstraints(5, 6, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox20.setText("PDF");
					{
						jPopupMenu4 = new JPopupMenu();
						setComponentPopupMenu(jCheckBox20, jPopupMenu4);
						{
							jMenuItem4 = new JMenuItem();
							jPopupMenu4.add(jMenuItem4);
							jMenuItem4.setText("Set Password");

							// PDF Password Einstellung Fenster wird aufgerufen.
							jMenuItem4.addActionListener(new ActionListener()
							{

								public void actionPerformed(ActionEvent e)
								{

									ppwTrace = new SetPdfPassword(jCheckBox20);
									ppwTrace.pdfGUI();
									ppwTrace.setVisible(true);
									jCheckBox20.setSelected(true);

								}
							});
						}
					}
				}
				{
					jCheckBox21 = new JCheckBox();
					output.add(jCheckBox21, new GridBagConstraints(6, 2, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox21.setText("XML");
				}
				{
					jCheckBox22 = new JCheckBox();
					output.add(jCheckBox22, new GridBagConstraints(6, 3, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox22.setText("XML");
				}
				{
					jCheckBox23 = new JCheckBox();
					output.add(jCheckBox23, new GridBagConstraints(6, 5, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox23.setText("XML");
				}
				{
					jCheckBox24 = new JCheckBox();
					output.add(jCheckBox24, new GridBagConstraints(6, 6, 1, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jCheckBox24.setText("XML");
				}

				debugChekboxlist.add(jCheckBox1);
				debugChekboxlist.add(jCheckBox5);
				debugChekboxlist.add(jCheckBox9);
				debugChekboxlist.add(jCheckBox13);
				debugChekboxlist.add(jCheckBox17);
				debugChekboxlist.add(jCheckBox21);

				errorChekboxlist.add(jCheckBox2);
				errorChekboxlist.add(jCheckBox6);
				errorChekboxlist.add(jCheckBox10);
				errorChekboxlist.add(jCheckBox14);
				errorChekboxlist.add(jCheckBox18);
				errorChekboxlist.add(jCheckBox22);

				reportChekboxlist.add(jCheckBox3);
				reportChekboxlist.add(jCheckBox7);
				reportChekboxlist.add(jCheckBox11);
				reportChekboxlist.add(jCheckBox15);
				reportChekboxlist.add(jCheckBox19);
				reportChekboxlist.add(jCheckBox23);

				traceChekboxlist.add(jCheckBox4);
				traceChekboxlist.add(jCheckBox8);
				traceChekboxlist.add(jCheckBox12);
				traceChekboxlist.add(jCheckBox16);
				traceChekboxlist.add(jCheckBox20);
				traceChekboxlist.add(jCheckBox24);
			}
		}

		expScrollPane.getViewport().add(expTable, null);
		modelTable.setDefaultEditor(Object.class, new AttributeTableEditor());
		modelTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		expTable.setDefaultEditor(Object.class, new AttributeTableEditor());
		expTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}

	
	/**
	 * open the external window for MySQL connection
	 * @param checkbox 
	 * 			the selected checkbox
	 */
	public void mysqlconn(JCheckBox checkbox)
	{
		MySqlDBConn msql = new MySqlDBConn(checkbox);
		msql.mysqlGUI();
		msql.setVisible(true);
	}

	/**
	 * open the external window for Oracel connection
	 * @param checkbox 
	 * 			the selected checkbox
	 */
	public void orclconn(JCheckBox checkbox)
	{
		OracleDBConn orl = new OracleDBConn(checkbox);
		orl.oranGUI();
		orl.setVisible(true);
	}

	
	/**
	 * creates and initializes an experiment with the parameters in expSettings.	 *
	 * @param expSettings 
	 * 			Experiment parameter names and values  			
	 * @return experiment with outputtypes, outputpath and modelname
	 */
	public Experiment creatExperiment(Map<String,AccessPoint> expSettings)
	{
		Experiment e = null;
		ArrayList<String> reportOutput = new ArrayList<String>();
		ArrayList<String> debugOutput = new ArrayList<String>();
		ArrayList<String> traceOutput = new ArrayList<String>();
		ArrayList<String> errorOutput = new ArrayList<String>();
		String outputPath = jTextField1.getText();
		String name = AccessUtil.getStringValue("name", expSettings);
		for (JCheckBox cb : debugChekboxlist)
		{
			if (cb.isSelected())
			{
				debugOutput.add(debugOutputType[debugChekboxlist.indexOf(cb)]);
			}
		}

		for (JCheckBox cb : reportChekboxlist)
		{
			if (cb.isSelected())
			{
				reportOutput
						.add(reportOutputType[reportChekboxlist.indexOf(cb)]);
			}
		}

		for (JCheckBox cb : traceChekboxlist)
		{
			if (cb.isSelected())
			{
				traceOutput.add(traceOutputType[traceChekboxlist.indexOf(cb)]);
			}
		}

		for (JCheckBox cb : errorChekboxlist)
		{
			if (cb.isSelected())
			{
				errorOutput.add(errorOutputType[errorChekboxlist.indexOf(cb)]);
			}
		}
//		if (AccessUtil.getBooleanValue("isTimed", expSettings))
//		{
//			if (reportOutput.size() == 0 || traceOutput == null
//					|| errorOutput == null || debugOutput == null)
//			{
//				JOptionPane.showMessageDialog(null,
//						"OutputType must be selected!", "error",
//						JOptionPane.ERROR_MESSAGE);
//			} else
//			{
//				e = new Experiment(name, outputPath, null,
//						TimeUnit.valueOf(AccessUtil.getStringValue(
//								"referenceUnit", expSettings)), null,
//						reportOutput, traceOutput, errorOutput, debugOutput);
//			}
//		} else
//		{
//			if (reportOutput == null || traceOutput.size() == 0
//					|| errorOutput.size() == 0 || debugOutput.size() == 0)
//			{
//				JOptionPane.showMessageDialog(null,
//						"OutputType must be selected!", "error",
//						JOptionPane.ERROR_MESSAGE);
//			} else
//				e = new Experiment(name, outputPath, reportOutput, traceOutput,
//						errorOutput, debugOutput);
//		}
		
		 e = new Experiment(name, outputPath, null,
			        AccessUtil.getTimeUnitValue(EXP_REF_UNIT, expSettings), null, reportOutput,
					traceOutput, errorOutput, debugOutput);

		return e;
	}

	/**
	 * Auto-generated method for setting the popup menu for a component
	 */
	private void setComponentPopupMenu(final java.awt.Component parent,
			final javax.swing.JPopupMenu menu)
	{
		parent.addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mousePressed(java.awt.event.MouseEvent e)
			{
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}

			public void mouseReleased(java.awt.event.MouseEvent e)
			{
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
		});
	}
}