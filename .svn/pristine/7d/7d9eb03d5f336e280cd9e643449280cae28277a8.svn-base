package desmoj.demo.balticSeaExcel;

import desmoj.core.simulator.*;
import desmoj.demo.balticSea_single.*;

public class BalticSeaModelExcel extends BalticSeaModel {

    public BalticSeaModelExcel() {
        super(null);
    }

    /** return a model description for statistics reports */
    public String description() {

        return super.description() + " with Excel output";
    }

    /** run the model */
    public static void main(String[] args) {

        // create model and experiment
        Experiment.setEpsilon(java.util.concurrent.TimeUnit.SECONDS);
        Experiment.setReferenceUnit(java.util.concurrent.TimeUnit.HOURS);
        Experiment experiment = new Experiment("BalticSea", ".", null,
        		"desmoj.core.report.ExcelReportOutput",
        		"desmoj.core.report.ExcelTraceOutput",
        		"desmoj.core.report.ExcelErrorOutput",
        		"desmoj.core.report.ExcelDebugOutput");
        BalticSeaModelExcel model = new BalticSeaModelExcel();

        // and link them to each other
        model.connectToExperiment(experiment);

        // perform the rest of the simulation experiment
        experiment.stop(new TimeInstant(365 * 7));
        experiment.tracePeriod(new TimeInstant(0), new TimeInstant(24 * 7));
        experiment.debugPeriod(new TimeInstant(0), new TimeInstant(24 * 7));
        experiment.start();
        experiment.report();
        experiment.finish();

    } // end of main method

} // end of class BalticSeaModel