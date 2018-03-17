package desmoj.extensions.db.visustorage.sim;


import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.SimTime;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.TimeSeries;

import desmoj.extensions.db.Database;
import desmoj.extensions.db.visustorage.VisualToDBModule;
import desmoj.extensions.space3D.LayoutLoader;
import desmoj.extensions.space3D.SpatialLayoutManager;
import desmoj.extensions.visualization3d.VisualizationControl;


import java.util.concurrent.TimeUnit;



/**
 * This class is the "main" class of the construction simulation
 * model which is derived from the vancarrier model. It will
 * demonstrate the visualization abillity of the 3D-spatial-extension
 * and the visualization extension of DESMO-J.
 *
 * @author Fred Sun
 *
 */
public class StorageModel extends Model
{

    /**
     * A waiting-queue object to represent the waiting excavator.
     *
     * If there is no truck waiting for service the excavavor will return here
     * and wait for the next truck to come.
     * (Note: We don't use a status field here due to statistical reasons.)
     *
     * This way all idle time statistics of the excavator are monitored by the queue.
     */
    protected ProcessQueue<Excavator> idleExcavatorQueue;

    /**
     * Random stream used to draw an arrival time for the next truck.
     * See Vancarrier_1st_p_model.init() method for stream parameters.
     */
    private ContDistUniform truckArrivalTime;

    /**
     * Random stream used to draw a service time for this truck.
     * Describes the time needed by the VC to get and load the container
     * on the truck.
     * See Vancarrier_1st_p_model.init() method for stream parameters.
     */
    private ContDistUniform serviceTime;

    /** Records numbers of arrived */
    protected TimeSeries trucksArrived;

    /** Records numbers of serviced trucks */
    protected TimeSeries trucksServiced;

    /** Records truck wait times */
    protected Histogram waitTimeHistogram;

    /** Number of arrived trucks ---NEW*/
    protected int arrivedTrucks = 0;

    /** Number of finished trucks ---NEW*/
    protected int servicedTrucks = 0;

    private LayoutLoader _loader;


    public StorageModel(Model owner, String modelName, boolean showInReport,
                        boolean showInTrace)
    {
        super(owner, modelName, showInReport, showInTrace);
        trucksArrived =
                new TimeSeries(this, "arrived", new SimTime(0.0), new SimTime(15000),
                               false);
        trucksServiced =
                new TimeSeries(this, "finished", new SimTime(0.0), new SimTime(15000),
                               false);
        waitTimeHistogram =
                new Histogram(this, "Truck Wait Times", 0, 16, 10, true,
                              false);
    }

    /* (non-Javadoc)
	 * @see desmoj.core.simulator.Model#description()
	 */

    @Override
    public String description()
    {
        return "This is an example Model to show the functionality" +
            "of the 3D-visualization extension. The Model is derived" +
            "from the vancarrier example model." + " " +
            "The model simulates a construction site in a city. " +
            "The trucks will arrived and be loaded by excavators " +
            "with sand. If no excavator is idle, the truck will" +
            "be waiting in a queue. If an excavator becomes free " +
            "the a waiting truck will leave the queue and drive to the " +
            "idle excavator. After the loading, the truck will leave " +
            "the construction site.";
    }

    /**
     * Returns a sample out of the random stream used to measure
     * time needed to find the container for this truck in the
     * storage and the time the VC needs to get it to the truck.
     *
     * Creation date: (30.03.00 12:06:04)
     * @return double    a serviceTime sample
     */
    public double getServiceTime()
    {
        return serviceTime.sample();
    }

    /**
     * Returns a sample out of the random stream used to measure
     * the next truck arrival time.
     *
     * Creation date: (30.03.00 12:00:05)
     * @return double       a truckArrivalTime sample
     */
    public double getTruckArrivalTime()
    {
        return truckArrivalTime.sample();
    }

    /* (non-Javadoc)
	 * @see desmoj.core.simulator.Model#doInitialSchedules()
	 */

    @Override
    public void doInitialSchedules()
    {
        SpatialLayoutManager layoutManager =
            SpatialLayoutManager.getSpatialLayoutManager();

        //create the servicer, here make excavators
        Excavator excavator1 =
            new Excavator(this, "excavator 1", "Excavator", layoutManager.getSpatialObject("LoadingPoint1"),
                          true);
        Excavator excavator2 =
            new Excavator(this, "excavator 2", "Excavator", layoutManager.getSpatialObject("LoadingPoint2"),
                          true);

        excavator1.sendToLocation(layoutManager.getSpatialObject("ExcavatorPosition1"));
        excavator2.sendToLocation(layoutManager.getSpatialObject("ExcavatorPosition2"));

        //put the excavators on duty with placing it on the event-list first
        //it will deactivate itself into waiting status
        //for the first truck right after activation
        excavator1.activate(new TimeInstant(0l, TimeUnit.SECONDS));
        excavator2.activate(new TimeInstant(0l, TimeUnit.SECONDS));
        //ATTENTION:
        //Don't use SimTime.NOW or SimTime.now() here, because it
        //leads to "strange" results due to a DESMO-J weakness here.

        //create a truck spring
        TruckGenerator firstarrival =
            new TruckGenerator(this, "TruckArrival", false);

        //place the truck generator on the event-list, in order to
        //start producing truck arrivals when the first truck comes
        //
        //Note : superclass of TruckGenerator is ExternalEvent,
        //therefore we must use "schedule" instead of "activate"
        firstarrival.schedule(new TimeSpan(getTruckArrivalTime(),
                                           TimeUnit.SECONDS));

    }

    /* (non-Javadoc)
	 * @see desmoj.core.simulator.Model#init()
	 */

    @Override
    public void init()
    {
        //creates a layout loader
        _loader = new SimpleLayoutLoader(this);

        //initalizing the serviceTimeStream
        //Parameters:
        //this 	     					=  belongs to this model
        //"ServiceTimeStream" = the name of the stream
        //3.0									= minimum time in seconds to deliver a container
        //7.0								= maximum time in seconds to deliver a container
        //true								= show in report?
        //false								= show in trace?
        serviceTime =
                new ContDistUniform(this, "ServiceTimeStream", 3.0, 7.0, true,
                                    false);

        //initalizing the truckArrivalTimeStream
        //Parameters:
        //this 	     					=  belongs to this model
        //"TruckArrivalTimeStream" = the name of the stream
        //3.0									= mean time in seconds between arrival of trucks
        //true								= show in report?
        //false								= show in trace?
        truckArrivalTime =
                new ContDistUniform(this, "TruckArrivalTimeStream", 2.5, 10.0,
                                    true, false);

        //necessary because a inter-arrival time can not be negative, but
        //a sample of an exponential distribution can...
        truckArrivalTime.setNonNegative(true);

        //initalizing the idleExcavatorQueue
        //Parameters:
        //this 	     					= belongs to this model
        //"idle VC Queue"       = the name of the Queue
        //true								= show in report?
        //false								= show in trace?
        idleExcavatorQueue =
                new ProcessQueue<Excavator>(this, "idle excavator Queue", true,
                                            false);
    }


    public static void main(String[] args) throws Exception
    {
        Experiment experiment =
            new Experiment("Construction site experiment.");
        experiment.setDescription("An experiment with Trucks and Excavators. Used to demonstrate the 3D component.");
        StorageModel constructionSiteModel =
            new StorageModel(null, "8-] StorageConstructionModel", true,
                             false);
        // connect Experiment and Model
        constructionSiteModel.connectToExperiment(experiment);

        experiment.stop(new TimeInstant(150, TimeUnit.SECONDS));
        experiment.setShowProgressBar(true);
        //the experiment running speed.
        //1 means that the experiment is running with the real time speed,
        //0 means as-fast-as-possible
        experiment.setExecutionSpeedRate(0);

        Database db = new Database(constructionSiteModel, experiment);
        db.connect("jdbc:oracle:thin:@rzdspc9.informatik.uni-hamburg.de:1521:edu",
                   "desmo_j", "desmo$2009");

        if (!db.isModelIdenticalToModelInDatabase())
            db.saveModel();
        //if you want to storage the visualization-data, the experiment configuration
        //must not already be in the database.
        if (!db.isExpConfigInDatabase())
        {
            db.saveExperimentConfiguration();

            //directories must end with a '/'
            VisualToDBModule storageModule =
                new VisualToDBModule("DB-StorageModule", "../Visu/3DModels/",
                                     "../layout.xml", "../model.xml",
                                     db.getDBConnection(),
                                     db.getExperimentDAO());
            //connects the visualization module to the visualization control
            VisualizationControl visControl = new VisualizationControl();
            visControl.addModule(storageModule);
        }
        else
            System.out.println("visual data not stored - given experiment configuration already in the database");

        //loads the non-movable SpatialObjects and the decoration 3D-models
        constructionSiteModel._loader.loadLayout("../layout.xml");

        // start the Experiment with start time 0.0
        experiment.start();
        // print the report about the already existing reporters into the report file
        experiment.report();
        // stop all threads still alive and close all output files
        experiment.finish();

        System.exit(0);
    }

}

