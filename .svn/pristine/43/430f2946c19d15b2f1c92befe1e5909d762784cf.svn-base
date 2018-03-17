package desmoj.extensions.report.jrxmlFiles;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * A simple class to compile all jrxml-output specifications to jasper-files.
 * Needs not be called unless the jrxml-output specifications are changed.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
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
public class CompileJrxmlToJasper {

    /**
     * Invoke to compile all jrxml-output specifications to jasper-files
     * 
     * @param args String[] : unused
     */
    public static void main(String[] args) {

        try {
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/report_layout_pdf.jrxml",   "src.experimentation/desmoj/extensions/report/jrxmlFiles/report_layout_pdf.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/debug_layout_pdf.jrxml",    "src.experimentation/desmoj/extensions/report/jrxmlFiles/debug_layout_pdf.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/error_layout_pdf.jrxml",    "src.experimentation/desmoj/extensions/report/jrxmlFiles/error_layout_pdf.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/trace_layout_pdf.jrxml",    "src.experimentation/desmoj/extensions/report/jrxmlFiles/trace_layout_pdf.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/report_layout_excel.jrxml", "src.experimentation/desmoj/extensions/report/jrxmlFiles/report_layout_excel.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/debug_layout_excel.jrxml",  "src.experimentation/desmoj/extensions/report/jrxmlFiles/debug_layout_excel.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/error_layout_excel.jrxml",  "src.experimentation/desmoj/extensions/report/jrxmlFiles/error_layout_excel.jasper");
            JasperCompileManager.compileReportToFile("src.experimentation/desmoj/extensions/report/jrxmlFiles/trace_layout_excel.jrxml",  "src.experimentation/desmoj/extensions/report/jrxmlFiles/trace_layout_excel.jasper");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
