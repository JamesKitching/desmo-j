package desmoj.demo.balticSea_batch;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class ShipGenerator extends SimProcess {

    /** constructor method */
    public ShipGenerator(BalticSeaModel owner) {

        super(owner, "ship generator", true);

    } // end of constructor method

    /** lifecycle method */
    public void lifeCycle() throws SuspendExecution {

        // get model reference
        BalticSeaModel model = (BalticSeaModel) this.getModel();

        while (true) {

            // create and activate a new ship
            new Ship(model).activateAfter(this);

            // wait for the next ship to enter
            this.hold(new TimeSpan(model.arrivalStream.sample()));

        } // end of loop

    } // end of lifecycle

} // end of class ShipGenerator
