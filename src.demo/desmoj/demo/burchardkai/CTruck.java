package desmoj.demo.burchardkai;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import desmoj.extensions.applicationDomains.harbour.*;

// Referenced classes of package BurchardkaiTerminal:
//            BurchardkaiTerminalModel

public class CTruck extends Truck
{
    private BurchardkaiTerminalModel myModel;

    public CTruck(Model owner, String name, long nImportGoods, long nExportGoods, double speed, boolean showInTrace)
    {
        super(owner, name, nImportGoods, nExportGoods, speed, showInTrace);
        myModel = (BurchardkaiTerminalModel)owner;
    }

    public void lifeCycle() throws SuspendExecution
    {
        if(myModel.observersOn)
            myModel.incArrivedTrucks();
        TimeInstant arrivalTime = presentTime();
        drive(((BurchardkaiTerminalModel)getModel()).roadToHO);
        Lane lane = ((BurchardkaiTerminalModel)getModel()).HO.getLane();
        setLane(lane);
        Yard yard = ((BurchardkaiTerminalModel)getModel()).yard;
        if(getNumberOfExportGoods() > 0)
        {
            yard.setYardStrategy(((BurchardkaiTerminalModel)getModel()).ys1);
//          BurchardkaiTerminalModel _tmp = (BurchardkaiTerminalModel)getModel();
            Block block;
            if(BurchardkaiTerminalModel.ExportReeferContainer.sample())
                block = yard.getBlock(yard.getFreeBlocks(yard.getBlocks(0, yard.getCBlocks(2)), 2L));
            else
                block = yard.getBlock(yard.getFreeBlocks(yard.getBlocks(0, yard.getCBlocks(1)), 2L));
            if(block != null)
            {
                ((BurchardkaiTerminalModel)getModel()).tControl.addJob(new Job(getModel(), "Job", 0, lane, block, false));
                yard.reserve(block, 2L);
            } else
            {
                ((BurchardkaiTerminalModel)getModel()).tControl.addJob(new Job(getModel(), "Job", 0, lane, null, false));
            }
        }
        if(getNumberOfImportGoods() > 0)
        {
            yard.setYardStrategy(((BurchardkaiTerminalModel)getModel()).ys2);
//          BurchardkaiTerminalModel _tmp1 = (BurchardkaiTerminalModel)getModel();
            Block block;
            if(BurchardkaiTerminalModel.ImportReeferContainer.sample())
                block = yard.getBlock(yard.getFullBlocks(yard.getBlocks(1, yard.getCBlocks(2)), 2L));
            else
                block = yard.getBlock(yard.getFullBlocks(yard.getBlocks(1, yard.getCBlocks(1)), 2L));
            if(block != null)
            {
                ((BurchardkaiTerminalModel)getModel()).tControl.addJob(new Job(getModel(), "Job", 1, block, lane, false));
                yard.plan(block, 2L);
            } else
            {
                ((BurchardkaiTerminalModel)getModel()).tControl.addJob(new Job(getModel(), "Job", 1, null, lane, false));
            }
        }
        ((BurchardkaiTerminalModel)getModel()).HO.waitOnCoop();
        ((BurchardkaiTerminalModel)getModel()).HO.takeBack(lane);
        drive(((BurchardkaiTerminalModel)getModel()).roadToHO);
//      BurchardkaiTerminalModel _tmp2 = (BurchardkaiTerminalModel)getModel();
        BurchardkaiTerminalModel.turnAroundTime.update(TimeOperations.diff(presentTime(), arrivalTime).getTimeAsDouble());
        if(myModel.observersOn)
            myModel.incFinishedTrucks();
    }
}
