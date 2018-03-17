package desmoj.extensions.experimentation.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * A simple Passwordsetting window based on the JFrame class.
 * 
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @date 30.03.2011
 * @author Binhua Han, Xiufeng Li
 */
public class SetPdfPassword extends JFrame
{

	private static final long serialVersionUID = 1L;

	/** Components for outputstype */
	private JPasswordField jPasswordField1;

	/** Ok Butten */
	private JButton jButton1;

	/** Cancel Button */
	private JButton jButton2;

	/** the selected checkbox */
	private JCheckBox _checkbox;

	/** the password for pdf */
	public static String _pw;

	/**
	 * Create a new SetPdfPassword class
	 * 
	 * @param checkbox
	 *            the selected checkbox
	 */
	public SetPdfPassword(JCheckBox checkbox)
	{
		_checkbox = checkbox;
	}

	{
		// Set Look & Feel
		try
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/** Inits the user interface */
	public void pdfGUI()
	{
		try
		{
			{

				this.setTitle("set pdf password");
				this.setResizable(false);
				this.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						setPdfPassword(null);
						setVisible(false);
					}
				});
				GridBagLayout thisLayout = new GridBagLayout();
				thisLayout.rowWeights = new double[]
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
						0.0, 0.0 };
				thisLayout.rowHeights = new int[]
				{ 16, 21, 21, 21, 21, -1, 16, 21, 21, 21, 16, 21, 21, 21 };
				thisLayout.columnWeights = new double[]
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				thisLayout.columnWidths = new int[]
				{ 112, 50, 50, 50, 50, 50, 50 };
				getContentPane().setLayout(thisLayout);
				this.setPreferredSize(new java.awt.Dimension(276, 136));
				this.setVisible(false);
				getContentPane().setBackground(
						new java.awt.Color(255, 255, 255));
				{
					jPasswordField1 = new JPasswordField();
					getContentPane().add(
							jPasswordField1,
							new GridBagConstraints(1, 6, 4, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.HORIZONTAL, new Insets(
											0, 0, 0, 0), 0, 0));
					jPasswordField1.setText(null);

				}
				{
					jButton1 = new JButton();
					getContentPane().add(
							jButton1,
							new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.NONE, new Insets(0, 0,
											0, 0), 0, 0));
					jButton1.setText("   OK  ");

					jButton1.addActionListener(new ActionListener()
					{

						public void actionPerformed(ActionEvent e)
						{
							// Set the Password for pdf
							if (_checkbox.isSelected() == true)
							{
								if (jPasswordField1.getText().trim().equals("")
										|| jPasswordField1.getText().trim() == null)
								{
									JOptionPane.showMessageDialog(null,
											"Password can not be empty!",
											"error", JOptionPane.ERROR_MESSAGE);
								} else
								{
									_pw = jPasswordField1.getText().toString();
									JOptionPane.showMessageDialog(null,
											"Password has been set");
									setVisible(false);
								}
							} else
							{
								JOptionPane.showMessageDialog(null,
										"OutputType has not been selected",
										"error", JOptionPane.ERROR_MESSAGE);
							}
						}
					});

				}
				{
					jButton2 = new JButton();
					getContentPane().add(
							jButton2,
							new GridBagConstraints(4, 8, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.NONE, new Insets(0, 0,
											0, 0), 0, 0));
					jButton2.setText("Cancel");

					jButton2.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							setPdfPassword(null);
							setVisible(false);
						}
					});

				}

			}
			{
				this.setSize(276, 136);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Get the PDF password
	 * 
	 * @return String: the PDF password
	 */
	public String getPdfPassword()
	{
		return _pw;
	}

	/**
	 * Set the PDF password
	 * @param pw
	 * 			String: the PDF password
	 */
	public void setPdfPassword(String pw)
	{
		_pw = pw;
	}

}
