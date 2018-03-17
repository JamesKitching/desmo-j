package desmoj.demo.burchardkai;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;

// Referenced classes of package BurchardkaiTerminal:
//            BurchardkaiTerminalModel, CTruck

public class TruckArrival extends ExternalEvent
{
    private ContDistExponential TruckArrivalStream;
    private BoolDistBernoulli exportCon;

    public TruckArrival(Model owner, String name, ContDistExponential TruckArrivalStream, BoolDistBernoulli exportCon, boolean showInTrace)
    {
        super(owner, name, showInTrace);
        this.TruckArrivalStream = TruckArrivalStream;
        this.exportCon = exportCon;
    }

    public void eventRoutine()
    {
        boolean expCont = exportCon.sample();
//      BurchardkaiTerminalModel _tmp = (BurchardkaiTerminalModel)getModel();
        double speed = BurchardkaiTerminalModel.truckSpeed.sample();
        CTruck truck;
        if(expCont)
            truck = new CTruck(getModel(), "Truck", 0L, 1L, speed, true);
        else
            truck = new CTruck(getModel(), "Truck", 1L, 0L, speed, true);
        truck.activate();
        TruckArrival tA = new TruckArrival(getModel(), "TruckArrivalEvent", TruckArrivalStream, exportCon, traceIsOn());
        tA.schedule(new TimeSpan(TruckArrivalStream.sample()));
    }
}
