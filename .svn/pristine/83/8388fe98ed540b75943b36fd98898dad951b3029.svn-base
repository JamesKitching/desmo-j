package desmoj.core.report;

import java.io.File; 
import java.io.IOException;

import jxl.*; 
import jxl.write.*; 
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

/**
 * This class implements ExcelWriter with JExcelAPI.
 * 
 * @author Roman
 *
 */
public class ExcelWriterDefault implements ExcelWriter {

	private WritableWorkbook file;
	private WritableSheet sheet;
	private int row = 0;
	private int column = 0;
	
	/* (non-Javadoc)
	 * @see ExcelWriter#createNewSheet(java.lang.String)
	 */
	public void createNewSheet(String pName) {
		try {
			file = Workbook.createWorkbook(new File(pName));
		} catch (IOException e) {
			// TODO: Error-Handling?
			e.printStackTrace();
		}
		sheet = file.createSheet("First Sheet", 0);
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#goToCell(int, int)
	 */
	public void goToCell(int pColumn, int pRow) {
		this.row = pColumn;
		this.column = pRow;
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#nextCell()
	 */
	public void nextCell() {
		this.row++;
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#newLine(int)
	 */
	public void newLine() {
		this.column++;
		this.row = 0;
	}
	
	/* (non-Javadoc)
	 * @see ExcelWriter#newLine(int)
	 */
	public void newLine(int pI) {
		this.column += pI;
		this.row = 0;
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeHeading(java.lang.String, int)
	 */
	public void writeHeading(String pName, int pColumn) {
		this.writeCell(pName, pColumn, 1);
		this.setBold(pColumn, 1);
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeHeading(java.lang.String)
	 */
	public void writeHeading(String pName) {
		this.writeHeading(pName, this.column);
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeCell(java.lang.String, int, int)
	 */
	public void writeCell(String pContent, int pColumn, int pRow) {
		Label label = new Label(pRow, pColumn, pContent); 
		try {
			sheet.addCell(label);
		} catch (RowsExceededException e) {
			// TODO Error-Handling
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Error-Handling
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeCell(int, int, int)
	 */
	public void writeCell(int pContent, int pColumn, int pRow) {
		Number number = new Number(pColumn, pRow, pContent); 
		try {
			sheet.addCell(number);
		} catch (RowsExceededException e) {
			// TODO Error-Handling
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Error-Handling
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeCell(float, int, int)
	 */
	public void writeCell(float pContent, int pColumn, int pRow) {
		Number number = new Number(pColumn, pRow, pContent); 
		try {
			sheet.addCell(number);
		} catch (RowsExceededException e) {
			// TODO Error-Handling
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Error-Handling
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeCell(java.lang.String)
	 */
	public void writeCell(String pContent) {
		this.writeCell(pContent, this.column, this.row);
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeCell(int)
	 */
	public void writeCell(int pContent) {
		this.writeCell(pContent, this.row, this.column);
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#writeCell(float)
	 */
	public void writeCell(float pContent) {
		this.writeCell(pContent, this.row, this.column);
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#setBold(int, int)
	 */
	public void setBold(int pRow, int pColumn) {
		WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		
		Cell old = sheet.getCell(pColumn, pRow);
		
		if (old.getType() == CellType.LABEL) {
			Label label = new Label(pColumn, pRow, ((LabelCell) old).getContents(), cellFormat);
			try {
				sheet.addCell(label);
			} catch (RowsExceededException e) {
				// TODO Error-Handling?
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Error-Handling?
				e.printStackTrace();
			}
		} 
		else if (old.getType() == CellType.NUMBER) {
			Number label = new Number(pColumn, pRow, ((NumberCell) old).getValue(), cellFormat);
			try {
				sheet.addCell(label);
			} catch (RowsExceededException e) {
				// TODO Error-Handling?
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Error-Handling?
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ExcelWriter#setBold()
	 */
	public void setBold() {
		this.setBold(this.column, this.row);
	}
	
	/* (non-Javadoc)
	 * @see ExcelWriter#close()
	 */
	public void close() {
		try {
			file.write();
			file.close();
		} catch (IOException e) {
			// TODO Error-Handling?
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Error-Handling?
			e.printStackTrace();
		}
	}
}