package desmoj.demo.balticSea_batch;

import java.util.LinkedHashMap;
import java.util.Locale;

import desmoj.core.simulator.*;
import desmoj.core.statistic.*;

public class BatchDataCollector {
    
    private static class BatchResult extends Model {
        int batches;
        public BatchResult(String batchname) {
            super(null, batchname, false, false);
            this.batches = 0;
        }
        public String description() {return "Summary of the results of the batch runs";}
        public void doInitialSchedules() {}
        public void init() {}
        public desmoj.core.report.ModelReporter createDefaultReporter() {
            return new BatchReporter(this);
        }
    }
    
    private static class BatchReporter extends desmoj.core.report.ModelReporter {
        public BatchReporter(Model model) {
            super(model);
            groupHeading = ""; // none
            columns[0] = "Batch Paramerization";
        }
        public String[] getEntries() {
            entries[0] = "Number of runs: " + ((BatchResult)this.getModel()).batches;
            return entries;

        }
    }    
    
    private static class BatchVariable extends desmoj.core.statistic.Tally {
        String type;
        String property;
        int sortpriority;
        public BatchVariable(Model ownerModel, int sortpriority, String type, String name, String property) {
            super(ownerModel, name, true, false);
            this.type=type;
            this.property=property;
            this.sortpriority = sortpriority;
        }
        public desmoj.core.report.Reporter createReporter() {
            return new BatchVariableReporter(this, sortpriority);
        }
    }
    
    private static class BatchVariableReporter extends desmoj.core.report.TallyReporter {
        public static String lastType, lastName;
        public BatchVariableReporter(Reportable informationSource, int sortpriority) {
            super(informationSource);
            numColumns = 9;
            columns = new String[numColumns];
            columns[0] = "Type";
            columns[1] = "Name";
            columns[2] = "Property";
            columns[3] = "Samples";
            columns[4] = "Mean";
            columns[5] = "Std.Dv";
            columns[6] = "Min";
            columns[7] = "Max";
            columns[8] = "Unit";
            groupHeading = "Variables observed";
            groupID = 1610 - sortpriority; 
            entries = new String[numColumns];
        }
        public static void clearCache() {
            lastType = "";
            lastName = "";
        }
        public String[] getEntries() {
            if (source instanceof BatchVariable) {
                BatchVariable bv = (BatchVariable) source;
                String[] superEntries = super.getEntries(); // will update the field "entries"
                entries = new String[numColumns];                
                entries[0] = bv.type;
                if (entries[0].equals(lastType)) {
                    entries[0] = "";                    
                } else {
                    lastType = entries[0];
                }
                entries[1] = bv.getName();
                if (entries[1].equals(lastName)) {
                    entries[1] = "";                    
                } else {
                    lastName = entries[1];
                }                
                entries[2] = bv.property;
                entries[3] = superEntries[2]; // Obs = Batches
                entries[4] = superEntries[3]; // Mean
                entries[5] = superEntries[4]; // Std.Dev.
                entries[6] = superEntries[5]; // Min
                entries[7] = superEntries[6]; // Max
                entries[8] = superEntries[7]; // Unit
            }
            return entries;
        }
    }
    
    LinkedHashMap<String, BatchVariable> reportableId2Value;
    BatchResult dummyModel;
    Experiment dummyExperiment;
    int batches;
    
    public BatchDataCollector(String batchname) {
        this(batchname, Experiment.DEFAULT_REPORT_OUTPUT_TYPE);
    }
    
    public BatchDataCollector(String batchname, String reportOutputType) {
        this.reportableId2Value = new LinkedHashMap<String, BatchVariable>();
        this.dummyModel = new BatchResult(batchname);
        this.dummyExperiment = new Experiment(batchname, ".", reportOutputType,
                null, null, null);
        this.dummyModel.connectToExperiment(this.dummyExperiment);
    }
    
    public void store(int sortpriority, String type, String name, String property, double sample, String unit) {
        if (Double.isNaN(sample)) return;
        String id = type + "!" + name + "!" + property;
        if (reportableId2Value.containsKey(id)) {
            reportableId2Value.get(id).update(sample);
        } else {
            BatchVariable bv = new BatchVariable(dummyModel, sortpriority, type, name, property);
            bv.reset();
            bv.update(sample);
            bv.setUnit(unit);
            reportableId2Value.put(id, bv);
        }
            
    }
        
    public void readModelData(Model m) {
        this.dummyModel.batches++;
        for (Reportable r : m.getReportables()) {
            if (r instanceof Count) {
                Count c = (Count) r;
                this.store(0, "Count", c.getName(), "final value", c.getValue(), c.getUnit());
            }
            if (r instanceof Aggregate) {
                Aggregate a = (Aggregate) r;
                this.store(1, "Aggregate", a.getName(), "final value", a.getValue(), a.getUnit());
            }
            if (r instanceof BoolStatistic) {
                BoolStatistic b = (BoolStatistic) r;
                this.store(2, "BoolStatistic", b.getName(), "true ratio", b.getTrueRatio(), "");
                this.store(2, "BoolStatistic", b.getName(), "obs", b.getObservations(), "");
            }
            if (r instanceof Tally) {
                Tally t = (Tally) r;
                this.store(3, "Tally", t.getName(), "mean", t.getMean(), t.getUnit());
                this.store(3, "Tally", t.getName(), "obs", t.getObservations(), t.getUnit());
            }
            if (r instanceof Accumulate) {
                Accumulate a = (Accumulate) r;
                this.store(4, "Accumulate", a.getName(), "mean", a.getMean(), a.getUnit());
            }
            if (r instanceof ProcessQueue) {
                ProcessQueue<?> q = (ProcessQueue<?>) r;
                this.store(5, "ProcessQueue", q.getName(), "av length", q.averageLength(), null);
                this.store(5, "ProcessQueue", q.getName(), "av wait time", q.averageWaitTime().getTimeAsDouble(), q.getModel().getExperiment().getReferenceUnit().toString().toLowerCase(Locale.ENGLISH));
                this.store(5, "ProcessQueue", q.getName(), "obs", q.getObservations(), null);
                this.store(5, "ProcessQueue", q.getName(), "zeros", 100.0*q.zeroWaits()/q.getObservations(), "%");
                if (q.getQueueLimit() < Integer.MAX_VALUE)
                    this.store(5, "ProcessQueue", q.getName(), "refused", 100.0*q.getRefused()/q.getObservations(), "%");
            }
            if (r instanceof Queue) {
                Queue<?> q = (Queue<?>) r;
                this.store(6, "Queue", q.getName(), "av length", q.averageLength(), null);
                this.store(6, "Queue", q.getName(), "av wait time", q.averageWaitTime().getTimeAsDouble(), q.getModel().getExperiment().getReferenceUnit().toString().toLowerCase(Locale.ENGLISH));
                this.store(6, "Queue", q.getName(), "obs", q.getObservations(), null);
                this.store(6, "Queue", q.getName(), "zeros", 100.0*q.zeroWaits()/q.getObservations(), "%");
                if (q.getQueueLimit() < Integer.MAX_VALUE)
                    this.store(6, "Queue", q.getName(), "refused", 100.0*q.getRefused()/q.getObservations(), "%");
            }
        }
    }
    
    public void createBatchOutput() {
        BatchVariableReporter.clearCache();
        this.dummyExperiment.report();
        this.dummyExperiment.finish();
    }
}
