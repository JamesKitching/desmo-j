package desmoj.demo.balticSea_single;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeOperations;
import desmoj.core.simulator.TimeSpan;

public class Ship extends SimProcess {

    // number of route the ship serves
    long route;

    // index of port the ship currently sailed to
    int routeIndex;

    // number of containers for each port (loaded in Hamburg)
    long[] numberOfContainers;

    /** constructor method */
    public Ship(BalticSeaModel owner) {

        super(owner, "ship", true);
        
        // Assign route
        this.route = owner.routeStream.sample();
        
        // Assign containers
        this.numberOfContainers = new long[owner.routes[(int) this.route].length];
        for (int i = 1; i < owner.routes[(int) this.route].length; i++) {
            this.numberOfContainers[i] = owner.containerDemandStream.get(owner.routes[(int)this.route][i]).sample();
        }

    } // end of constructor method

    /** the ship's lifecycle */
    public void lifeCycle() throws SuspendExecution {

        // get model reference
        BalticSeaModel model = (BalticSeaModel) this.getModel();

        // update statistics: another ship enters the model
        model.numberOfShipsInSystem.update(1);

        // record start time
        TimeInstant startTime = this.presentTime();

        // each ship leaves Hamburg
        int harbourOfDeparture = 0;

        do {

            // determine port to sail to and start sailing
            int currentHarbour = model.routes[(int)this.route][this.routeIndex];
            this.hold(new TimeSpan(model.travelTimes.get(harbourOfDeparture).get(currentHarbour).sample()));

            // queue ship on arrival in port
            model.shipQueue.get(currentHarbour).insert(this);

            // check for free crane
            if (!model.idleCraneQueue.get(currentHarbour).isEmpty()) {

                // at least one crane is idle, remove first one
                GantryCrane crane = model.idleCraneQueue.get(currentHarbour).first();
                model.idleCraneQueue.get(currentHarbour).remove(crane);

                // update statistics: utilization of cranes in this port
                model.craneUtil[currentHarbour].update(1
                        - model.idleCraneQueue.get(currentHarbour).length()
                        / (double) model.numberOfCranesInHarbour[currentHarbour]);

                // activate crane
                crane.activateAfter(this);

            }

            // wait until unloading is finished
            this.passivate();

            // update container statistics
            for (int i=0; i < numberOfContainers[routeIndex]; i++) {
                model.containerPerRouteTotal.update("Route " + this.route);                
            }
            model.containersPerRouteDevelopmentOverTime[(int)this.route].update(model.containerPerRouteTotal.getObservationsOfString("Route " + this.route));
            
            // prepare to sail to next port
            harbourOfDeparture = currentHarbour;
            this.routeIndex++;

        } while (this.routeIndex < model.routes[(int)this.route].length);

        // all ports have been visited - update statistics and leave
        model.numberOfShipsInSystem.update(-1);
        TimeSpan residenceTime = TimeOperations.diff(this.presentTime(), startTime);
        model.residenceTime[(int)this.route].update(residenceTime.getTimeAsDouble());
        model.shipsPerRouteTotal.update("Route " + this.route);
        model.shipsPerRouteDevelopmentOverTime[(int)this.route].update(model.shipsPerRouteTotal.getObservationsOfString("Route " + this.route));

    } // end of lifecycle method

} // end of class Ship