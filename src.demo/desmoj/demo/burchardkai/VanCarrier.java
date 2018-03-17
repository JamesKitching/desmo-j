package desmoj.demo.burchardkai;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import desmoj.core.advancedModellingFeatures.WaitQueue;
import desmoj.extensions.applicationDomains.harbour.*;

// Referenced classes of package BurchardkaiTerminal:
//            BurchardkaiTerminalModel

public class VanCarrier extends InternalTransporter
{
    private ModelComponent location;
    private WaitQueue HO;
    private double d_matrix[][];
    private double speedYard;
    private BurchardkaiTerminalModel myModel;
    
    public VanCarrier(Model owner, String name, int capac, double speedLoad, double speedEmpty, 
            double speedYard, T_Control t, HoldingArea HO, double matrix[][], boolean showInTrace)
    {
        super(owner, name, capac, speedLoad, speedEmpty, t, showInTrace);
        location = null;
        myModel = (BurchardkaiTerminalModel)owner;
        this.HO = HO;
        d_matrix = matrix;
        this.speedYard = speedYard;
    }

    public void importJobCycle(Job j) throws SuspendExecution
    {
        Block b = (Block)j.getOrigin();
        Lane lane = (Lane)j.getDestination();
        if(b != null)
        {
            Integer index = new Integer(b.getName().substring(6));
            if(location instanceof HoldingArea)
            {
                driveEmpty(d_matrix[d_matrix.length - 1][index.intValue()]);
            } else
            {
                Block l = (Block)location;
                Integer i = new Integer(l.getName().substring(6));
                setSpeedEmpty(speedYard);
                driveEmpty(d_matrix[i.intValue()][index.intValue()]);
            }
            ((BurchardkaiTerminalModel)getModel()).yard.retrieve(b, 2L);
            pickUp(new TimeSpan(getChangeConPlaceTime(b)));
            setSpeedLoad(getSpeedLoad());
            driveLoad(d_matrix[index.intValue()][d_matrix.length - 1]);
            ((BurchardkaiTerminalModel)getModel()).condMyTruck.setLane(lane);
            HO.cooperate(((BurchardkaiTerminalModel)getModel()).loading, ((BurchardkaiTerminalModel)getModel()).condMyTruck);
            location = HO;
        } else
        if(location instanceof HoldingArea)
        {
            ((BurchardkaiTerminalModel)getModel()).yard.retrieveFromOverflow(2L);
            HO.cooperate(((BurchardkaiTerminalModel)getModel()).loading, ((BurchardkaiTerminalModel)getModel()).condMyTruck);
        } else
        {
            Block l = (Block)location;
            Integer i = new Integer(l.getName().substring(6));
            driveEmpty(d_matrix[i.intValue()][d_matrix.length - 1]);
            ((BurchardkaiTerminalModel)getModel()).yard.retrieveFromOverflow(2L);
            ((BurchardkaiTerminalModel)getModel()).condMyTruck.setLane(lane);
            HO.cooperate(((BurchardkaiTerminalModel)getModel()).loading, ((BurchardkaiTerminalModel)getModel()).condMyTruck);
            setLocation(HO);
        }
        if(myModel.observersOn)
            myModel.updateHOQueue(myModel.tControl.getTS().getTransporter().length(), HO.sLength());
    }

    public void exportJobCycle(Job j) throws SuspendExecution
    {
        Lane lane = (Lane)j.getOrigin();
        Block b = (Block)j.getDestination();
        if(b != null)
        {
            if(location instanceof Block)
            {
                Block l = (Block)location;
                Integer i = new Integer(l.getName().substring(6));
                int index = i.intValue();
                driveEmpty(d_matrix[index][d_matrix.length - 1]);
            }
            ((BurchardkaiTerminalModel)getModel()).condMyTruck.setLane(lane);
            HO.cooperate(((BurchardkaiTerminalModel)getModel()).unloading, ((BurchardkaiTerminalModel)getModel()).condMyTruck);
            Integer bIndex = new Integer(b.getName().substring(6));
            driveLoad(d_matrix[d_matrix.length - 1][bIndex.intValue()]);
            pickDown(new TimeSpan(getChangeConPlaceTime(b)));
            ((BurchardkaiTerminalModel)getModel()).yard.store(b, 2L);
            location = b;
        } else
        {
            if(location instanceof Block)
            {
                Block l = (Block)location;
                Integer i = new Integer(l.getName().substring(6));
                int index = i.intValue();
                driveEmpty(d_matrix[index][d_matrix.length - 1]);
            }
            ((BurchardkaiTerminalModel)getModel()).condMyTruck.setLane(lane);
            HO.cooperate(((BurchardkaiTerminalModel)getModel()).unloading, ((BurchardkaiTerminalModel)getModel()).condMyTruck);
            ((BurchardkaiTerminalModel)getModel()).yard.storeInOverflow(2L);
            setLocation(HO);
        }
        if(myModel.observersOn)
            myModel.updateHOQueue(myModel.tControl.getTS().getTransporter().length(), HO.sLength());
    }

    private double getChangeConPlaceTime(Block b)
    {
        double time = 0.0D;
        if(b.OccupRate() > 0.0D && b.OccupRate() <= 0.20000000000000001D)
        {
//          BurchardkaiTerminalModel _tmp = (BurchardkaiTerminalModel)getModel();
            time = BurchardkaiTerminalModel.loadTime1.sample();
        } else
        if(b.OccupRate() > 0.20000000000000001D && b.OccupRate() <= 0.40000000000000002D)
        {
//          BurchardkaiTerminalModel _tmp1 = (BurchardkaiTerminalModel)getModel();
            time = BurchardkaiTerminalModel.loadTime2.sample();
        } else
        if(b.OccupRate() > 0.40000000000000002D && b.OccupRate() <= 0.80000000000000004D)
        {
//          BurchardkaiTerminalModel _tmp2 = (BurchardkaiTerminalModel)getModel();
            time = BurchardkaiTerminalModel.loadTime4.sample();
        } else
        if(b.OccupRate() > 0.80000000000000004D && b.OccupRate() <= 1.0D)
        {
//          BurchardkaiTerminalModel _tmp3 = (BurchardkaiTerminalModel)getModel();
            time = BurchardkaiTerminalModel.loadTime5.sample();
        }
        return time;
    }

    public void setLocation(ModelComponent l)
    {
        location = l;
    }

    public ModelComponent getLocation()
    {
        return location;
    }
}
