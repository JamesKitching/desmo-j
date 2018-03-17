package desmoj.demo.balticSea_single;

import java.util.ArrayList;

import desmoj.core.dist.*;
import desmoj.core.simulator.*;
import desmoj.core.statistic.*;

public class BalticSeaModel extends Model {

    /**
     * model parameters, e.g. the ports, routes, number of cranes in each port
     */
    static int NUM_HARBOURS = 9;

    static int NUM_ROUTES = 4;

    String[] nameOfHarbour;

    int[][] routes;
    
    double[] routeCumulativeDistribution;

    int[] numberOfCranesInHarbour;
    
    int[] minContainersPerHarbour;
    
    int[] maxContainersPerHarbour;

    double meanInterArrivalTime;
    
    double[] meanServiceTime;

    double[][] meanTravelTime;


    /** static components: queues, distributions, data collectors */
    // queue of ships waiting to be unloaded (one for each harbour)
    ArrayList<ProcessQueue<Ship>> shipQueue;

    // queue of idle cranes (one in each harbour)
    ArrayList<ProcessQueue<GantryCrane>> idleCraneQueue;
    
    // distribution of container shipped to each port 
    ArrayList<DiscreteDist<Long>> containerDemandStream;
    
    // distribution of container shipped to each port 
    DiscreteDistEmpirical<Integer> routeStream;

    // distribution of inter-arrival times for new ships
    ContDist arrivalStream;

    // distribution of service times in each port
    ArrayList<ContDist> serviceTimeStream;

    // distribution of sailing times between ports
    ArrayList<ArrayList<ContDist>> travelTimes;

    // counter of ships in the model
    Count numberOfShipsInSystem;

    // accumulation of utilization of cranes in each port
    HistogramAccumulate[] craneUtil;

    // tally of average model residence times across ships
    Histogram[] residenceTime;
    
    // total ships per route
    TextHistogram shipsPerRouteTotal;

    // total container per route
    TextHistogram containerPerRouteTotal;
    
    // running ships per route
    TimeSeries[] shipsPerRouteDevelopmentOverTime;
    
    // running containers per route
    TimeSeries[] containersPerRouteDevelopmentOverTime;


    /** constructor method */
    public BalticSeaModel(Model parent) {

        super(parent, "Container Shipment in the Baltic Sea ", true, false);

        // initialise model parameters
        this.nameOfHarbour = new String[] { "Hamburg", "Rostock-Warnemuende", "Gdansk", "Riga",
                "Tallinn", "St.Petersburg", "Helsinki", "Stockholm", "Copenhagen" };
        this.numberOfCranesInHarbour = new int[] { 0,  6, 11,  3,  6,  3,  4,  4,  7 };
        this.minContainersPerHarbour = new int[] { 0, 10, 15, 25, 12, 20, 20, 15,  8 };
        this.maxContainersPerHarbour = new int[] { 0, 30, 55, 35, 20, 50, 35, 30, 15 };
        this.routes = new int[BalticSeaModel.NUM_ROUTES][];

        this.routes[0] = new int[] { 1, 2, 3, 4, 6 };
        this.routes[1] = new int[] { 8, 7, 6, 5, 4 };
        this.routes[2] = new int[] { 2, 7, 8 };
        this.routes[3] = new int[] { 8, 2, 1 };
        
        this.routeCumulativeDistribution = new double[] { 0.3, 0.7, 0.9, 1.0 };
        
        this.meanInterArrivalTime = 1;

        this.meanServiceTime = new double[] { 0, 0.09, 0.125, 0.15, 0.18, 0.225, 0.085, 0.09, 0.075 };

        this.meanTravelTime = new double[][] {
                {  0.0, 14.2, 32.4, 46.8, 51.0, 64.6, 52.8, 39.6, 17.4 },
                { 14.2,  0.0, 20.4, 36.6, 40.8, 54.4, 42.6, 29.4,  7.2 },
                { 32.4, 20.4,  0.0, 24.0, 28.8, 40.8, 30.6, 22.8, 20.4 },
                { 46.8, 36.6, 24.0,  0.0, 14.4, 26.4, 16.2, 14.4, 34.8 },
                { 51.0, 40.8, 28.8, 14.4,  0.0, 14.2,  4.0, 15.0, 39.0 },
                { 64.6, 54.4, 40.8, 26.4, 14.2,  0.0, 12.6, 27.6, 51.6 },
                { 52.8, 42.6, 30.6, 16.2,  4.0, 12.6,  0.0, 16.8, 40.8 },
                { 39.6, 29.4, 22.8, 14.4, 15.0, 27.6, 16.8,  0.0, 27.6 },
                { 17.4,  7.2, 20.4, 34.8, 39.0, 61.6, 40.8, 27.6,  0.0 } };

    } // end of constructor method

    /** initialise static model components */
    public void init() {

        // queues for ships and idle cranes in each port
        this.shipQueue = new ArrayList<ProcessQueue<Ship>>();
        this.idleCraneQueue = new ArrayList<ProcessQueue<GantryCrane>>();
        for (int i = 0; i < BalticSeaModel.NUM_HARBOURS; i++) {
            this.shipQueue.add(new ProcessQueue<Ship>(this, "ships waiting in "
                    + this.nameOfHarbour[i], true, true));
            this.idleCraneQueue.add(new ProcessQueue<GantryCrane>(this, "idle cranes in "
                    + this.nameOfHarbour[i], true, true));
        }
        
        // distribution of inter-arrival times for new ships
        this.arrivalStream = new ContDistExponential(this, "Ship interarrival", this.meanInterArrivalTime, true, false);
        
        // distribution to sample route index from
        this.routeStream = new DiscreteDistEmpirical<Integer>(this, "route choice", true, false);
        for (int i = 0; i < BalticSeaModel.NUM_ROUTES; i++) {
            this.routeStream.addEntry(i, this.routeCumulativeDistribution[i]);
        }
        
        // distribution of f container shipped to each port 
        this.containerDemandStream = new ArrayList<DiscreteDist<Long>>();
        this.containerDemandStream.add(new DiscreteDistConstant<Long>(this, "Demand Hamburg dummy", 0L, false, false));
        for (int i = 1; i < BalticSeaModel.NUM_HARBOURS; i++) {
            this.containerDemandStream.add(new DiscreteDistUniform(this,
                    "containers for port " + this.nameOfHarbour[i],
                    this.minContainersPerHarbour[i], this.maxContainersPerHarbour[i], true, false));
        }
        
        // distribution of service times (one for each harbour except Hamburg)
        this.serviceTimeStream = new ArrayList<ContDist>();
        this.serviceTimeStream.add(new ContDistConstant(this, "Service Hamburg dummy", 0, false, false));
        for (int i = 1; i < BalticSeaModel.NUM_HARBOURS; i++) {
            this.serviceTimeStream.add(new ContDistExponential(this,
                    "service time for one container in " + this.nameOfHarbour[i],
                    this.meanServiceTime[i], true, false));
        }

        // distributions for sampling travel times between ports
        this.travelTimes = new ArrayList<ArrayList<ContDist>>();
        for (int i = 0; i < BalticSeaModel.NUM_HARBOURS; i++) {
            ArrayList<ContDist> current = new ArrayList<ContDist>();
            for (int j = 0; j < BalticSeaModel.NUM_HARBOURS; j++) {
                current.add(new ContDistExponential(this, "travel times from "
                        + this.nameOfHarbour[i] + " to " + this.nameOfHarbour[j],
                        this.meanTravelTime[i][j], true, false));
            }
            this.travelTimes.add(current);
        }

        // counter for number of ships in the model
        this.numberOfShipsInSystem = new Count(this, "number of ships in system", true, false, true);

        // accumulates for the utilization of cranes in each harbour except
        // Hamburg
        this.craneUtil = new HistogramAccumulate[BalticSeaModel.NUM_HARBOURS];
        for (int i = 1; i < BalticSeaModel.NUM_HARBOURS; i++) {
            this.craneUtil[i] = new HistogramAccumulate(this, "crane utilization in "
                    + this.nameOfHarbour[i], new double[] { 0, 0.2, 0.4, 0.6, 0.8, 1}, true, false);
        } // end of loop

        // tallies for residence times across ships (one for each route)
        this.residenceTime = new Histogram[BalticSeaModel.NUM_ROUTES];
        for (int i = 0; i < BalticSeaModel.NUM_ROUTES; i++) {
            this.residenceTime[i] = new Histogram(this, "residence time for route " + i, new double[] { 0, 25, 50, 75, 100, 150, 200, 300, 400} ,true, false);
        } // end of loop
        
        // route development (one for each route)
        this.shipsPerRouteTotal = new TextHistogram(this, "Ships per Route", true, false);
        this.containerPerRouteTotal = new TextHistogram(this, "Container per Route", true, false);
        this.shipsPerRouteDevelopmentOverTime = new TimeSeries[BalticSeaModel.NUM_ROUTES];
        this.containersPerRouteDevelopmentOverTime = new TimeSeries[BalticSeaModel.NUM_ROUTES];
        for (int i = 0; i < BalticSeaModel.NUM_ROUTES; i++) {
            this.shipsPerRouteDevelopmentOverTime[i] = new TimeSeries(this, "Ships on route " + i, new TimeInstant(0), new TimeInstant(400), true, false);
            this.shipsPerRouteDevelopmentOverTime[i].update(0.0);
            this.containersPerRouteDevelopmentOverTime[i] = new TimeSeries(this, "Container on route " + i, new TimeInstant(0), new TimeInstant(400), true, false);
            this.containersPerRouteDevelopmentOverTime[i].update(0.0);
            this.containersPerRouteDevelopmentOverTime[i].setGroup("Container");
        } // end of loop

    } // end of init method

    /** activates dynamic model components */
    public void doInitialSchedules() {

        // create and activate ship generator
        new ShipGenerator(this).activate();

        // create and activate cranes for each port
        for (int i = 1; i < BalticSeaModel.NUM_HARBOURS; i++) {
            for (int j = 0; j < this.numberOfCranesInHarbour[i]; j++) {
                new GantryCrane(this, i).activate();
            } // end of inner loop
        } // end of outer loop

    } // end of doInitialSchedules method

    /** return a model description for statistics reports */
    public String description() {

        return "Baltic sea container shipment model";
    }

    /** run the model */
    public static void main(String[] args) {

        // create model and experiment
        Experiment.setEpsilon(java.util.concurrent.TimeUnit.MINUTES);
        Experiment.setReferenceUnit(java.util.concurrent.TimeUnit.HOURS); 
        Experiment experiment = new Experiment("BalticSea");
        BalticSeaModel model = new BalticSeaModel(null);

        // and link them to each other
        model.connectToExperiment(experiment);

        // perform the rest of the simulation experiment
        experiment.stop(new TimeInstant(365 * 7));
        experiment.tracePeriod(new TimeInstant(0), new TimeInstant(24 * 7));
        experiment.debugPeriod(new TimeInstant(0), new TimeInstant(24 * 7));
        experiment.start();
        experiment.report();
        experiment.finish();

    } // end of main method

} // end of class BalticSeaModel