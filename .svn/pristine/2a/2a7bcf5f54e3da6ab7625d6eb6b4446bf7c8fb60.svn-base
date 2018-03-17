package desmoj.extensions.experimentation.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * A simple MySQL Connection window based on the JFrame class.
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
public class MySqlDBConn extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Components in the Connection window */

	/** Label for MySQL username */
	private JLabel jLabel1;

	/** TextField for MySQL username */
	private JTextField jTextField1;

	/** Label for MySQL password */
	private JLabel jLabel2;

	/** TextField for MySQL password */
	private JPasswordField jPasswordField1;

	/** TextField for MySQL url */
	private JTextField jTextField2;

	/** Label for MySQL url */
	private JLabel jLabel3;

	/** Cancel Button */
	private JButton jButton2;

	/** Ok Button */
	private JButton jButton1;

	/** selected MySQL checkbox */
	private JCheckBox _checkbox;

	/** the String for MySQL username */
	private String _username;

	/** the String for MySQL password */
	private String _pw;

	/** the String for MySQL url */
	private String _url;

	/**
	 * Create a new MySqlDBConn class
	 * 
	 * @param checkbox
	 *            the selected checkbox
	 */
	public MySqlDBConn(JCheckBox checkbox)
	{
		_checkbox = checkbox;
	}

	/** Inits the user interface */
	public void mysqlGUI()
	{
		try
		{
			{
				this.setTitle("MySQL Database");
				this.setResizable(false);
				GridBagLayout thisLayout = new GridBagLayout();
				thisLayout.rowWeights = new double[]
				{ 0.0, 0.0, 0.0, 0.0, 0.0 };
				thisLayout.rowHeights = new int[]
				{ 21, 21, 21, -1, 16 };
				thisLayout.columnWeights = new double[]
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
				thisLayout.columnWidths = new int[]
				{ 112, 50, 50, 50, 50, 50 };
				getContentPane().setLayout(thisLayout);
				this.setPreferredSize(new java.awt.Dimension(371, 230));
				this.setVisible(false);
				getContentPane().setBackground(
						new java.awt.Color(192, 192, 192));
				this.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						_checkbox.setSelected(false);
						setVisible(false);
					}
				});

				{
					jLabel1 = new JLabel();
					getContentPane().add(
							jLabel1,
							new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 3), 0, 0));
					jLabel1.setText("     username");
				}
				{
					jTextField1 = new JTextField();
					getContentPane().add(
							jTextField1,
							new GridBagConstraints(1, 0, 4, 1, 0.0, 0.0,
									GridBagConstraints.EAST,
									GridBagConstraints.BOTH, new Insets(6, 0,
											6, 0), 0, 0));
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(
							jLabel2,
							new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 4), 0, 0));
					jLabel2.setText("     password");
				}
				{
					jPasswordField1 = new JPasswordField();
					getContentPane().add(
							jPasswordField1,
							new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 0), 0, 0));
					jPasswordField1.setText(null);
				}
				{
					jButton1 = new JButton();
					getContentPane().add(
							jButton1,
							new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.NONE, new Insets(3, 0,
											0, 0), 0, 0));
					jButton1.setText("Login");

					jButton1.addMouseListener(new MouseAdapter()
					{
						@SuppressWarnings("deprecation")
						@Override
						public void mouseClicked(MouseEvent e)
						{
							if (jTextField1.getText().equals("desmo_j")
									&& jPasswordField1.getText().equals(
											"desmo$2009")
									&& jTextField2
											.getText()
											.equals("jdbc:oracle:thin:@rzdspc9.informatik.uni-hamburg.de:1521:edu"))
							{
								try
								{

									// Connect to the database
									// You must put a database name after the @
									// sign
									// in the connection URL.
									// You can use either the fully specified
									// SQL*net syntax or a short cut
									// syntax as <host>:<port>:<sid>. The
									// example
									// uses the short cut syntax.
									Class.forName("oracle.jdbc.driver.OracleDriver");

									String url = "jdbc:MySql://localhost/mydatabase";
									String username = "desmo_j";
									String password = "desmo$2009";

									Connection conn = DriverManager
											.getConnection(url, username,
													password);

									Statement stmt = conn.createStatement();

									// Select the ENAME column from the EMP
									// table
									ResultSet rs = stmt
											.executeQuery("select * from test1");

									// Iterate through the result and print the
									// employee names
									while (rs.next())
									{

										System.out.println(rs.getString(1));

									}
								} catch (SQLException e1)
								{

									e1.printStackTrace();
								} catch (ClassNotFoundException e1)
								{

									System.out.println("login erfolg");
									setVisible(false);

								}

							} else
							{
								JOptionPane.showMessageDialog(null,
										"login misserfolg");
							}

						}
					});
				}
				{
					// TODO

				}

				{
					jButton2 = new JButton();
					getContentPane().add(
							jButton2,
							new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.NONE, new Insets(3, 0,
											0, 0), 0, 0));
					jButton2.setText("Cancel");
					jButton2.addActionListener(new ActionListener()
					{

						public void actionPerformed(ActionEvent arg0)
						{

							_checkbox.setSelected(false);
							setVisible(false);

						}
					});
				}
				{
					jLabel3 = new JLabel();
					getContentPane().add(
							jLabel3,
							new GridBagConstraints(0, 2, 1, 3, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.NONE, new Insets(0, 0,
											0, 0), 0, 0));
					jLabel3.setText("     url");
				}
				{
					jTextField2 = new JTextField();
					getContentPane().add(
							jTextField2,
							new GridBagConstraints(1, 2, 4, 3, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.HORIZONTAL, new Insets(
											0, 0, 0, 0), 0, 0));
				}
			}
			{
				this.setSize(371, 230);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Get the MySQL username
	 * 
	 * @return String: the MySQL username
	 */
	public String getUsername()
	{
		return _username;
	}

	/**
	 * Set the MySQL username
	 */
	public void setUsername(String username)
	{
		_username = username;
	}

	/**
	 * Get the MySQL password
	 * 
	 * @return String: the MySQL password
	 */
	public String getPassword()
	{
		return _pw;
	}

	/**
	 * Set the MySQL password
	 */
	public void setPassword(String pw)
	{
		_pw = pw;
	}

	/**
	 * Get the MySQL url
	 * 
	 * @return String: the MySQL url
	 */
	public String getURL()
	{
		return _url;
	}

	/**
	 * Set the MySQL url
	 */
	public void setURL(String url)
	{
		_url = url;
	}
}