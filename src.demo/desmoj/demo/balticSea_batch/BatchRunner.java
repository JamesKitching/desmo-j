package desmoj.demo.balticSea_batch;

import desmoj.core.simulator.*;

public class BatchRunner {

    /** run the model */
    public static void main(String[] args) {
        
        Experiment.setEpsilon(java.util.concurrent.TimeUnit.SECONDS);
        Experiment.setReferenceUnit(java.util.concurrent.TimeUnit.HOURS);
        
        // Create output collector
        BatchDataCollector c = new BatchDataCollector("BalticSea");
        
        for (int batch = 0; batch < 3; batch++) {

            // create model and experiment
            Experiment experiment = new Experiment("BalticSea_Batch" + batch);
            BalticSeaModel model = new BalticSeaModel();
            
            // parameterize
            experiment.setSeedGenerator(batch * 37 + 4211); // a magic number :)
            experiment.setShowProgressBar(false);
    
            // link model and experiment to each other
            model.connectToExperiment(experiment);
    
            // perform the the simulation experiment
            experiment.stop(new TimeInstant(365 * 24));
            experiment.start();
            experiment.report();
            experiment.finish();
            
            // feed the output collector
            c.readModelData(model);
        }
        
        // let the output generator write its report
        c.createBatchOutput();

    } // end of main method

} // end of class BalticSeaModel