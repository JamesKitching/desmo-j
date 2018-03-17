package desmoj.core.report;

import desmoj.core.simulator.Experiment;

/**
 * A table formatter class for writing simulation output to Excel tables. This
 * class implements the Excel formatting functionality of the deprecated class
 * desmoj.report.ExcelFileOuptut
 * 
 * @version DESMO-J, Ver. 2.3.3 copyright (c) 2017
 * @author Nicolas Knaak
 * @author based on ExcelFileOutput by Tim Lechler
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
public class ExcelTableFormatter extends AbstractTableFormatter {

	ExcelWriter excel = new ExcelWriterDefault();
	
	public void close() {

		if (rowOpen)
			closeRow();
		if (tableOpen)
			closeTable();	
		
		excel.newLine();
		excel.writeCell("created using: DESMO-J (http://www.desmoj.de) " + Experiment.getDesmoJVersion() + " at " + new java.util.Date().toString());
		excel.newLine();
		excel.writeCell("DESMO-J is licensed under " + Experiment.getDesmoJLicense(true));
		excel.newLine();
		// just an example
		// excel.insertHistrogramHere(new double[]{3d, 1d, 4d, 5d, 2d}); 
		excel.close();
	}

	/**
	 * Writes the tag to close a row in a table to the file.
	 */
	public void closeRow() {

		if ((rowOpen) && (tableOpen)) {
			rowOpen = false;
			this._currentReporter = null;
		}
	}

	/**
	 * Inserts the tags needed to close a Excel 3.2 table into the file. Also
	 * inserts a paragraph tag to add some space below the table.
	 */
	public void closeTable() {

		if (!tableOpen)
			return; // do nothing if table not open

		if (rowOpen) {
			closeRow(); // correct an open row if necessary
			rowOpen = false;
		}
		
		excel.newLine(2);

		tableOpen = false;

	}

	/**
	 * Inserts the tags needed to close a Excel 3.2 table into the file. Also
	 * inserts a paragraph tag to add some space below the table. But omits the
	 * top tag as used in method <code>closeTable()</code>. This is needed if
	 * one reportable is generating a report that consists of more than one
	 * table (see <code>StockReporter</code> or <code>HistogramReporter</code>)
	 */
	public void closeTableNoTopTag() {

		if (!tableOpen)
			return; // do nothing if table not open, just return

		if (rowOpen) {
			closeRow(); // correct an open row if necessary
			rowOpen = false;
		}

//		out.writeln("</TABLE><P>"); // The table end tag
		// writeln("<FONT SIZE=-1><A HREF=#top>top</A></FONT><P>");

		tableOpen = false;

	}

	/**
	 * Opens a new file with the given fileName for writing a Excel table to. If
	 * no String is given, the default filename "unnamed_DESMOJ_file" is used.
	 * 
	 * @param name
	 *            java.lang.String : The name of the file to be created
	 */
	public void open(String name) {

		excel.createNewSheet(name);

	}

	/**
	 * Writes the Excel 3.2 tags to open a new row in a table to the file. A new
	 * row can only be started, if the table has alerady been opened and the
	 * previous row has been closed.
	 */
	public void openRow() {

		if (tableOpen) {

			if (!rowOpen) {
				excel.newLine();
				rowOpen = true; // keep a note to shut the row on closes
			}

		}

	}

	/**
	 * Inserts the tags needed to build a Excel 3.2 table heading into the file.
	 * The table's heading text is given with the parameter.
	 * 
	 * @param s
	 *            String : The heading for the table
	 */
	public void openTable(String s) {

		if (tableOpen)
			return; // table already opened
		excel.newLine();
		excel.nextCell();
		excel.nextCell();
		excel.nextCell();
		excel.writeCell(s);
		excel.setBold();
		excel.newLine();

		tableOpen = true;
		rowOpen = false;
	}

	/**
	 * Returns the status of the current table row that is written to.
	 * 
	 * @return boolean : Is <code>true</code> if the method
	 *         <code>openRow()</code> has been called last, <code>false</code>
	 *         if the method <code>closeRow()</code> has been called last
	 */
	public boolean rowIsOpen() {

		return rowOpen;

	}

	/**
	 * Returns the status of the current table that is written to.
	 * 
	 * @return boolean : Is <code>true</code> if the method
	 *         <code>openTable()</code> has been called last,
	 *         <code>false</code> if the method <code>closeTable()</code>
	 *         has been called last
	 */
	public boolean tableIsOpen() {

		return tableOpen;

	}

	/**
	 * Creates a new table cell and writes the given String into that cell. Note
	 * that there this is raw Excel code so there must not be any special
	 * language specific characters that might confuse any browser. A new cell
	 * can not be written, if neither a table nor a row have been opened yet.
	 * The method will simply return without action in that case.
	 * 
	 * @param s
	 *            java.lang.String : The text to be printed into a cell
     * @param spanning
     *            int : number of cells to span (ignored)
	 */
	public void writeCell(String s, int spanning) {
		
		if (s == null)
			return;

		if ((rowOpen) && (tableOpen)){
			try {
				Float f = new Float(s);
				excel.writeCell(f);
				excel.nextCell();
			}
			catch (Exception e){
				excel.writeCell(s);
				excel.nextCell();
			}
		}
			

	}

	/**
	 * Creates a newcentered heading row to print a title in. Note that there
	 * this is raw Excel code so the string given must not contain any special
	 * language specific characters that might confuse any browser. been opened
	 * yet. The method will simply return without action in that case. The
	 * number for the Excel heading style must be inside the range [1,6]. If not,
	 * it will be trimmed to the nearest legal heading style number.
	 * 
	 * @param style
	 *            int : The heading style format number for the text to be
	 *            printed in
	 * @param s
	 *            java.lang.String : The text to be printed as heading
	 */
	public void writeHeading(int style, String s) {

		// check parameter
		if (s == null)
			return;


		// check if no table is open, otherwise I can't write centered heading
		if (tableOpen)
			return;

		// now write heading
		excel.writeHeading(s);


	}

	/**
	 * Creates a new table cell and writes the given String into that cell as
	 * heading cells in bold letters and with centered text. Note that there
	 * this is raw Excel code so there must not be any special language specific
	 * characters that might confuse any browser. A new cell can not be written,
	 * if netiher a table nor a row have been opened yet. The method will simply
	 * return without action in that case.
	 * 
	 * @param s
	 *            java.lang.String : The text to be printed into a cell
	 */
	public void writeHeadingCell(String s) {

		if (s == null)
			return;

		if ((rowOpen) && (tableOpen))
			excel.writeCell(s);
			excel.setBold();
			excel.nextCell();


	}

	/**
	 * Writes the Excel tag for inserting a horizontal ruler into the file. Note
	 * that horizontal rulers are not written into table cells, thus this method
	 * simply returns, if a table is still open.
	 */
	public void writeHorizontalRuler() {

		if (!tableOpen) 
			excel.newLine();
//			histogram;
			for(int i=0; i<12;i++) {
				excel.writeCell("----------");
				excel.nextCell();			
			}				
				
			excel.newLine(2);
	}

	/** @return The string <code>"xml"</code> */
	public String getFileFormat() {
		return "xls";
	}

}