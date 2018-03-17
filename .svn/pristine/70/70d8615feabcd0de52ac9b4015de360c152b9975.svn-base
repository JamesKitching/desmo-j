package desmoj.extensions.report;

import java.util.ArrayList;
import java.util.HashMap;

import desmoj.core.report.ErrorMessage;
import desmoj.core.report.Message;
import desmoj.core.report.OutputTypeEndToExport;
import desmoj.core.report.Reporter;
import desmoj.extensions.experimentation.util.FileUtil;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 * Use this class to create Excel formatted Simulation Error Output.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @date 30.03.2011
 * @author Xiufeng Li, Binhua Han, Rene Wecker
 */
public class ExcelErrorOutput implements OutputTypeEndToExport
{
	/** CollectionDataSource */
	private ArrayList<HashMap<String, String>> al;

	/** Create a new ExcelTraceOutput class */
	public ExcelErrorOutput()
	{
		al = new ArrayList<HashMap<String, String>>();
	}

	/**
	 * Implement this method to define the behaviour of the messagereceiver when
	 * messages are sent to it.
	 * 
	 * @param m
	 *            Message : The message sent to the messagereceiver
	 */
	public void receive(Message m)
	{

		// check parameters
		if (m == null)
			return; // again nulls
		if (!(m instanceof ErrorMessage))
			return; // got wrong message
		ErrorMessage tmp = (ErrorMessage) m; // cast and buffer for easier
												// access

		// For Jasper-Reports

		HashMap<String, String> hm = new HashMap<String, String>();

		hm.put("model", tmp.getModelName());
		hm.put("time", tmp.getTime());
		hm.put("error_information", tmp.getDescription());
		hm.put("location", tmp.getLocation());
		hm.put("reason", tmp.getReason());
		hm.put("prevention", tmp.getPrevention());
		al.add(hm);
	}

	/**
	 * Implement this method to define the behaviour of the messagereceiver when
	 * reporters are sent to it.
	 * 
	 * @param r
	 *            Reporter : The reporter sent to the messagereceiver
	 */
	public void receive(Reporter r)
	{
		return; // No reporters are handled here
	}

	/**
	 * opens a new file for writting the output
	 * 
	 * @param pathname
	 *            String: path to write in
	 * @param name
	 *            String: name of the file
	 */
	public void open(String pathname, String name)
	{
	}

	/**
	 * Closes the file
	 */
	public void close()
	{
	}

	/***************************************************************************
	 * deliever the file appendix used for the specific format (e.g. html, txt)
	 **************************************************************************/
	public String getAppendix()
	{
		return ".xls";
	}

	/**
	 * Export a new few file for the writting output.
	 * 
	 * @param pathname
	 *            String: path to write in
	 * @param filename
	 *            String: name of the file
	 */
	public void export(String pathname, String filename)
	{
        String jasperSource = "error_layout_excel.jasper";
        String jasperSourcePath = "desmoj/extensions/report/jrxmlFiles/"; 
        String jasperDestination = System.getProperty("user.dir") + "/" + jasperSource;
	    
		try
		{
            HashMap<String, String> parameter = new HashMap<String, String>();
            JRMapCollectionDataSource ds = new JRMapCollectionDataSource(al);
            System.out.print("Exporting Error Channel to Excel, please wait... ");
                
            FileUtil.copy(this.getClass().getClassLoader().getResource(jasperSourcePath + jasperSource), jasperDestination);
            JasperReport  jasperReport = JasperManager.loadReport(jasperDestination);
            
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameter, ds);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					pathname + "/" + filename + "_error.xls");
			exporter.exportReport();
			FileUtil.deleteFile(jasperDestination);
			System.out.println("Finished!");

        } catch (Exception e)
        {
            System.out.println("Failure! (Try extracting the contents of the jar file, if not done already.)");
            System.out.println("Error stack trace: ");
            e.printStackTrace();
            FileUtil.deleteFile(jasperDestination);
        }
	}
}