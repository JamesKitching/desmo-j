package desmoj.demo.burchardkai;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import desmoj.core.util.*;
import desmoj.extensions.experimentation.reflect.MutableFieldAccessPoint;
import desmoj.extensions.experimentation.ui.*;
import desmoj.extensions.experimentation.util.*;;

// Referenced classes of package BurchardkaiTerminal:
//            BurchardkaiTerminalModel

public class BurchardkaiTerminalRunner extends ExperimentRunner
{

    public BurchardkaiTerminalRunner()
    {
        super();
    }

    public BurchardkaiTerminalRunner(BurchardkaiTerminalModel m)
    {
        super(m);
    }

    public SimRunListener[] createSimRunListeners(GraphicalObserverContext c)
    {
        BurchardkaiTerminalModel model = (BurchardkaiTerminalModel)getModel();
        TimeSeriesPlotter tp1 = new TimeSeriesPlotter("Trucks", c, model.trucksArrived, 300, 300, "Arrived");
        tp1.addTimeSeries(model.trucksServiced, "Finished");        
        TimeSeriesPlotter tp2 = new TimeSeriesPlotter("Holding area", c, model.vcidle, 300, 300, "Idle VCs");
        tp2.addTimeSeries(model.hoQueueSLength, "Trucks waiting");
        return (new SimRunListener[] {tp1, tp2});
    }
    
    public Map<String,AccessPoint> createParameters() {
        Map<String,AccessPoint> pm = super.createParameters();
        AccessUtil.setValue(pm, EXP_STOP_TIME, 86400D);
        AccessUtil.setValue(pm, EXP_TRACE_STOP, 3600D);
        AccessUtil.setValue(pm, EXP_REF_UNIT, TimeUnit.MINUTES);
        AccessUtil.setValue(pm, EXP_EPSILON, TimeUnit.MICROSECONDS);
        pm.put("truckArrivalSeed", new MutableFieldAccessPoint("truckArrivalSeed", this));
        pm.put("truckSpeedSeed", new MutableFieldAccessPoint("truckSpeedSeed", this));
        pm.put("truckLoadTimeSeed", new MutableFieldAccessPoint("truckLoadTimeSeed", this));
        return pm;
    }

    
    public static void main(String[] args) throws Exception {
        new ExperimentStarterApplication(BurchardkaiTerminalModel.class, BurchardkaiTerminalRunner.class).setVisible(true);
    }
    
    static int truckArrivalSeed = 23500829;
    static int truckLoadTimeSeed = 23500830;
    static int truckSpeedSeed = 23500831;
}
