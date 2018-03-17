package desmoj.extensions.db.visustorage.sim;

import desmoj.core.simulator.Model;

public class MockupModel extends Model
{
    
    /*
     * A Modelclass which has works as an alibi source for events.
     */
    public MockupModel()
    {
        super(null, "MockUpModel", false, false);
    }

    public String description()
    {
        return "A MockUpModel which contains no data or information.";
    }

    public void doInitialSchedules()
    {
    }

    public void init()
    {
    }
}
