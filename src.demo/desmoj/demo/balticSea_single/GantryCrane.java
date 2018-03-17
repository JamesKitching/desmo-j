package desmoj.demo.balticSea_single;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class GantryCrane extends SimProcess {

    // harbour this crane works in
    private int harbour;

    /** constructor method */
    public GantryCrane(BalticSeaModel owner, int harbour) {

        super(owner, "crane at " + owner.nameOfHarbour[harbour], true);
        this.harbour = harbour;

    }

    /** the crane's lifecycle */
    public void lifeCycle() throws SuspendExecution {

        // get a model reference
        BalticSeaModel model = (BalticSeaModel) this.getModel();

        // cycle indefinitely through unloading and waiting for ships
        while (true) {

            // check if ships are waiting
            if (model.shipQueue.get(this.harbour).isEmpty()) {

                // no ship is waiting insert crane into queue
                model.idleCraneQueue.get(this.harbour).insert(this);

                // update crane utilization in this port
                model.craneUtil[this.harbour].update(1
                        - model.idleCraneQueue.get(this.harbour).length()
                        / (double) model.numberOfCranesInHarbour[this.harbour]);

                // wait for a ship to arrive
                this.passivate();

            } // end if (no ships are waiting)

            else {

                // at least one ship is waiting - remove from its queue
                Ship ship = model.shipQueue.get(this.harbour).first();
                model.shipQueue.get(this.harbour).remove(ship);

                // compute duration for unloading, based on number of
                // containers to unload and quality of service in this port
                double unloadingDuration = model.serviceTimeStream.get(harbour).sample()
                  * ship.numberOfContainers[ship.routeIndex];

                // unload ship
                this.hold(new TimeSpan(unloadingDuration));

                // unloading is finished; activate ship
                ship.activate();

            } // end of else (ships to unload)

        } // end of loop

    } // end of lifecycle

} // end of class Crane
