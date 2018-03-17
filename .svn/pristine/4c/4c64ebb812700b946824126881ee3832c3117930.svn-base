package desmoj.core.report;
/**
 * This interface is used to encapsulate the usage of JExcelAPI
 * 
 * @author Roman Michel
 * @date 27.12.2011
 *
 */
public interface ExcelWriter {
	/**
	 * This method creates a new file (and a new sheet to work with)
	 * 
	 * @param Name
	 */
	public void createNewSheet(String Name);
	
	/**
	 * Set the pointer to a new cell
	 * 
	 * @param pColumn The new column
	 * @param pRow The new row
	 */
	public void goToCell(int pColumn, int pRow);
	
	/**
	 * Moves the pointer one cell forward
	 */
	public void nextCell();
	
	/**
	 * Moves the pointer to the beginning of the next row
	 */
	public void newLine();
	
	/**
	 * Moves the pointer to the beginning of a new line
	 * 
	 * @param pI How many lines should be left blank
	 */
	public void newLine(int pI);
	
	/**
	 * Writes a heading (uses the whole line)
	 * 
	 * @param pName The text
	 * @param pColumn The column to use
	 */
	public void writeHeading(String pName, int pColumn);
	
	/**
	 * Writes a heading to the current line (jumps to a new line)
	 * 
	 * @param pName The text
	 */
	public void writeHeading(String pName);
	
	/**
	 * Writes a string to a specific cell
	 * 
	 * @param pContent The text
	 * @param pColumn The column
	 * @param pRow The Row
	 */
	public void writeCell(String pContent, int pColumn, int pRow);
	
	/**
	 * Writes an integer to a specific cell
	 * 
	 * @param pContent The integer
	 * @param pColumn The column
	 * @param pRow The row
	 */
	public void writeCell(int pContent, int pColumn, int pRow);
	
	/**
	 * Writes a float to a specific cell
	 * 
	 * @param pContent The float
	 * @param pColumn The column
	 * @param pRow The row
	 */
	public void writeCell(float pContent, int pColumn, int pRow);
	
	/**
	 * Writes a string to the current cell
	 * 
	 * @param pContent The String
	 */
	public void writeCell(String pContent);
	
	/**
	 * Writes an integer to the current cell
	 * 
	 * @param pContent The integer
	 */
	public void writeCell(int pContent);
	
	/**
	 * Writes a float to the current cell
	 * 
	 * @param pContent The float
	 */
	public void writeCell(float pContent);
	
	/**
	 * Sets a specific cell to a bold font
	 * 
	 * @param pColumn The column
	 * @param pRow The row
	 */
	public void setBold(int pColumn, int pRow);
	
	/**
	 * Sets the current cell to a bold font 
	 */
	public void setBold();
		
	/**
	 * Generates diagrams, writes the file to the disk and closes it
	 */
	public void close();
	
}
