package desmoj.extensions.report;

import java.util.ArrayList;
import java.util.HashMap;

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
 * Use this class to create Excel formatted Simulation Report Output.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @date 30.03.2011
 * @author Xiufeng Li, Binhua Han, Rene Wecker
 */
public class ExcelReportOutput implements OutputTypeEndToExport
{
	/** CollectionDataSource */
	private ArrayList<HashMap<String, String>> al;

	/** Create a new ExcelReportOutput class */
	public ExcelReportOutput()
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
		return; // No messages are handled here
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
		String[] titleBuf = r.getColumnTitles();
		String[] entryBuf = r.getEntries();
		String reportHeading = r.getHeading();
		// For Jasper-Reports
		HashMap<String, String> hm = new HashMap<String, String>();
		if (titleBuf[0].equals("Description")
				|| titleBuf[0].equals("Parameter"))
		{
			hm.put("experiment", r.getModel().getExperiment().getDescription());
			hm.put("model", r.getModel().getName());
			for (int i = 0; i < entryBuf.length; i++)
			{
				int index = i % titleBuf.length;
				hm.put(titleBuf[index], entryBuf[i]);
			}
			al.add(hm);
		} else
		{
			if (reportHeading.equals("Accumulates"))
			{
				for (int i = 0; i < titleBuf.length; i++)
				{
					hm.put("a_" + titleBuf[i], entryBuf[i]);
				}
				al.add(hm);
			}

			if (reportHeading.equals("Tallies"))
			{
				for (int i = 0; i < titleBuf.length; i++)
				{
					hm.put("t_" + titleBuf[i], entryBuf[i]);
				}
				al.add(hm);
			}

			if (reportHeading.equals("Counts and Aggregates"))
			{
				for (int i = 0; i < titleBuf.length; i++)
				{
					hm.put("c_" + titleBuf[i], entryBuf[i]);
				}
				al.add(hm);
			}

			if (reportHeading.equals("Queues"))
			{
				for (int i = 0; i < titleBuf.length; i++)
				{
					hm.put("q_" + titleBuf[i], entryBuf[i]);
				}
				al.add(hm);
			}

			if (reportHeading.equals("Distributions"))
			{
				for (int i = 0; i < titleBuf.length; i++)
				{
					hm.put("d_" + titleBuf[i], entryBuf[i]);
				}
				al.add(hm);
			}

		}
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
        String jasperSource = "report_layout_excel.jasper";
        String jasperSourcePath = "desmoj/extensions/report/jrxmlFiles/"; 
        String jasperDestination = System.getProperty("user.dir") + "/" + jasperSource;

		try
		{
            HashMap<String, String> parameter = new HashMap<String, String>();
            JRMapCollectionDataSource ds = new JRMapCollectionDataSource(al);
            System.out.print("Exporting Report Channel to Excel, please wait... ");

            FileUtil.copy(this.getClass().getClassLoader().getResource(jasperSourcePath + jasperSource), jasperDestination);
            JasperReport jasperReport = JasperManager.loadReport(jasperDestination);
            
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameter, ds);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					pathname + "/" + filename + "_report.xls");
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