package desmoj.extensions.experimentation.ui;

import java.util.Map;

import desmoj.core.simulator.SimTime;
import desmoj.demo.balticSea.BalticSeaModel;
import desmoj.extensions.experimentation.util.AccessUtil;
import desmoj.extensions.experimentation.util.ExperimentRunner;


/**
 * A demo experiment runner
 * 
 * @author Xiufeng Li
 * @date 30.03.2011
 * @version DESMO-J, Ver. 2.3beta copyright (c) 2017
 */
public class Runner extends ExperimentRunner {
	
	public Runner() {
		super();
	}
	
	public Map createParameters() {
		Map pm = super.createParameters();
		AccessUtil.setValue(pm, "stopTime", new SimTime(20.0));
		AccessUtil.setValue(pm, "traceStopTime", new SimTime(20.0));
//		AccessUtil.setValue(pm, "traceOutputType","desmoj.core.report.ExcelTraceOutput");
//		AccessUtil.setValue(pm, "traceOutputType","desmoj.core.report.PdfTraceOutput");
//		AccessUtil.setValue(pm, "reportOutputType","desmoj.core.report.ExcelReportOutput");
		return pm;
	}
	
	/** Runs the GUI. */
	public static void main(String[] args) throws Exception {
		new ExperimentStarterApplicationModifier(BalticSeaModel.class, Runner.class).setVisible(true);
	}
}

