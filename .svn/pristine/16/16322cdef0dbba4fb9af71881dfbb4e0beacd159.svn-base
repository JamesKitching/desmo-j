package desmoj.demo.manufacturing;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.*;

import java.lang.Math;
import java.io.*;
/**
 * This class is the "main" class of the manufacturing_system_1st_p model.
 * It is derived from desmoj.model and consists of all the
 * needed infrastructural issues of a DESMO-J based model.
 * It provides all random streams , queues, descriptions,
 * and simulation steering needed.
 * See the method comments for further information.
 *
 * Creation date: 28.06.01
 * @author Bente Matzen
 */
public class ManufacturingModel extends Model {

  /**
   * The parameters which have to be variables
   */
  private static final int WORKSTAMOUNT = 4;
  //public static Workstation[] WORKSTINSTANZEN;
  private static int[] bufferSize = new int[WORKSTAMOUNT];
  private static int[] machineQuantity = new int[WORKSTAMOUNT];
  private static int[] meanProcTime = new int[WORKSTAMOUNT];

  /**
   * An array of workstations
   */
  public static WorkstationProcess[] workstations;

  /**
   * Random streams used to draw the arrival time of new blank parts
   */
  private ContDistExponential partArrivalTime;

  /**
   * The queue of leaving blank parts
   */
  private Queue<BlankPart> blankPartQueue;

  /**
   * The statistic object that counts the throughput of blank parts
   */
  private Count throughput;

  /**
   * This statistic object collects statistical data about the sojourn time
   * of blank parts in the system
   */
  private Tally sojournTime;

  /**
   * This statistic object collects statistical data about the processing time
   * of blank parts in the system
   */
  private Tally processingTime;

  /**
   * This statistic object collects statistical data about the waiting time
   * of blank parts in the system
   */
  private Tally waitingTime;
  
  Object[] parameters;

  /**
   * 0-Parameter constructor
   */
  public ManufacturingModel() {
    super (null, "The Manufacturing Model", true, true);
  }

  /**
   * Contructor for Simulation Optimization
   */
//  public ManufacturingModel(Model owner, String name, Integer size1,
//    Integer quantity1, Integer size2, Integer quantity2, Integer size3,
//    Integer quantity3, Integer size4, Integer quantity4, Integer mean1,
//    Integer mean2, Integer mean3, Integer mean4) {

  public ManufacturingModel(Model owner, String name,
    Integer size1, Integer quantity1, Integer mean1,
    Integer size2, Integer quantity2, Integer mean2,
    Integer size3, Integer quantity3, Integer mean3,
    Integer size4, Integer quantity4, Integer mean4) {

    this(owner, name, true, true, size1, quantity1, mean1, size2, quantity2,
      mean2, size3, quantity3, mean3, size4, quantity4, mean4);
  }

  /**
   * ManufacturingModel constructor.
   *
   * Creates a new Manufacturing model with calling
   * the superclasses constructor.
   *
   * @param owner desmoj.Model
   * @param modelname java.lang.String
   * @param showInReport boolean
   * @param showInTrace boolean
   */
  public ManufacturingModel(Model owner, String name,
    boolean showInReport, boolean showInTrace, Integer size1, Integer quantity1,
    Integer mean1, Integer size2, Integer quantity2, Integer mean2,
    Integer size3, Integer quantity3, Integer mean3, Integer size4,
    Integer quantity4, Integer mean4) {

    super(owner, name, showInReport, showInTrace);
    bufferSize[0] = size1.intValue();
    bufferSize[1] = size2.intValue();
    bufferSize[2] = size3.intValue();
    bufferSize[3] = size4.intValue();
    machineQuantity[0] = quantity1.intValue();
    machineQuantity[1] = quantity2.intValue();
    machineQuantity[2] = quantity3.intValue();
    machineQuantity[3] = quantity4.intValue();
    meanProcTime[0] = mean1.intValue();
    meanProcTime[1] = mean2.intValue();
    meanProcTime[2] = mean3.intValue();
    meanProcTime[3] = mean4.intValue();
  }

  /**
   * Describes what the model does
   */
  public String description() {
    return "This is the ManufacturingModel description, which means it is " +
      "the first Manufacturing system model in a process oriented version.";
  }

  /**
   * Returns this model's name
   */
  public String getModelName() {
    return getName();
    //return modelName;
  }

  /**
   * Returns the Count-Object "throughput"
   */
  public Count getThroughput() {
    return throughput;
  }

  /**
   * Returns the Tally-Object "sojournTime"
   */
  public Tally getSojournTime() {
    return sojournTime;
  }

  /**
   * Returns the Tally-Object "waitingTime"
   */
  public Tally getWaitingTime() {
    return waitingTime;
  }

  /**
   * Returns the Tally-Object "processingTime"
   */
  public Tally getProcessingTime() {
    return processingTime;
  }
 

  /**
   * Returns the model's result types
   */
  public Class[] getResultTypes() {
    Class[] c = new Class[7+WORKSTAMOUNT*3];
    Class resultClass = null;
    try {
      resultClass = Class.forName("java.lang.Long");
    }
    catch (ClassNotFoundException e) {
      System.out.println("ClassNotFoundException: " + e.getMessage());
    }
    c[0] = resultClass;
    try {
      resultClass = Class.forName("java.lang.Double");
    }
    catch (ClassNotFoundException e) {
      System.out.println("ClassNotFoundException: " + e.getMessage());
    }
    for (int i = 1; i <= 7+WORKSTAMOUNT*3-1; i++)
      c[i] = resultClass;
    return c;
  }

  /**
   * This method inserts a blank part into the blankPartQueue
   */
  public void addBlankPartQueue(BlankPart part) {
    blankPartQueue.insert(part);
  }

  /**
   * This method returns a sample out of the random stream used to measure the
   * next blank part arrival time.
   */
  public double getPartArrivalTime() {
    return partArrivalTime.sample();
  }

  /**
   * This method returns the WORKSTAMOUNT
   */
  public int getWorkstamount() {
    return WORKSTAMOUNT;
  }

  /**
   * refresh the statictic object 'sojournTime'
   */
  public void updateSojournTime(double value) {
    sojournTime.update(value);
  }

  /**
   * refresh the statictic object 'processingTime'
   */
  public void updateProcessingTime(double value) {
    processingTime.update(value);
  }

  /**
   * refresh the statictic object 'waitingTime'
   */
  public void updateWaitingTime(double value) {
    waitingTime.update(value);
  }

  /**
   * refresh the statistic object 'throughput'
   */
  public void updateThroughput() {
    throughput.update();
  }

  /**
   * This method is used to initalize all
   * DESMO-J infrastructure we use
   */
  public void init() {
    //initialize();
    //create servicer, here make the array of workstations
    workstations = new WorkstationProcess[WORKSTAMOUNT];
    //for (int i = 1; i <= WORKSTAMOUNT; i++)
    //  workstations[i] = new WorkstationProcess(WORKSTINSTANZEN[i].getOwner(),
    //  WORKSTINSTANZEN[i].getName(), i, WORKSTINSTANZEN[i].getShowInTrace(),
    //  WORKSTINSTANZEN[i].getShowInReport(), WORKSTINSTANZEN[i].getSize(),
    //  WORKSTINSTANZEN[i].getQuantity(), WORKSTINSTANZEN[i].getMeanProcTime());
    for (int i = 0; i < WORKSTAMOUNT; i++)
      workstations[i] = new WorkstationProcess
(this, "Workstation" + (i+1), i+1, true, true, bufferSize[i], machineQuantity[i], meanProcTime[i]);
    blankPartQueue = new Queue(this, "Blank Part Queue", true, true);
    //initalizing the partArrivalTimeStream
    partArrivalTime= new ContDistExponential
    //   (this, "BlankPartArrivalTimeStream", 33.0, true, true);    Original Bente
      (this, "BlankPartArrivalTimeStream", 10.0, true, true);
    //because a inter-arrival time can not be negative, but
    //a sample of an exponential distribution can...
    partArrivalTime.setNonNegative(true);
    //create the statistic objects
    throughput = new Count(this, "Throughput", true, true);
    sojournTime = new Tally(this, "SojournTime", true, true);
    processingTime = new Tally(this, "ProcessingTime", true, true);
    waitingTime = new Tally(this, "WaitingTime", true, true);
  }

  /**
   * This method is used to place all events or processes on the
   * internal event list of the simulator, which are necessary to start
   * the simulation.
   *
   * In this case the workstation is a necessary entry.
   */
  public void doInitialSchedules() {
    //put the workstations on duty with placing it on the eventlist first
    //it will deactivate itself into waiting status
    //for the first blank part right after activation
    for (int i = 0; i < WORKSTAMOUNT; i++)
      workstations[i].activate();
    //create a blank part spring
    BlankPartGenerator firstarrival =
      new BlankPartGenerator(this,"BlankPartArrival",false);
    //place the blank part generator on the eventlist
    //at the firstarrivaltime of a blank part
    firstarrival.schedule(new TimeSpan(getPartArrivalTime()));
  }

  /**
   * This method prints all the results of this experiment
   */
  public void printResults() {
    try {
      FileOutputStream foStream = new FileOutputStream("result.txt");
      PrintStream pStream = new PrintStream(foStream);
      pStream.print("\nDurchsatz an Werkstuecken: ");
      pStream.println(throughput.getObservations() + "\n");
      int t = blankPartQueue.length();
      for (int i = 0; i < t; i++) {
        BlankPart part = (BlankPart) blankPartQueue.first();
        blankPartQueue.remove(part);
        pStream.print("Werkstueck_" + part.getID() + " ----- ");
        pStream.print("Verweilzeit: ");
        pStream.print((long) Math.floor(part.getSojournTime()/60) + " h, ");
        pStream.print(Math.round(part.getSojournTime()%60) + " min -- ");
        pStream.print("Bearbeitungszeit: ");
        pStream.print((long) Math.floor(part.getProcessingTime()/60) + " h, ");
        pStream.print(Math.round(part.getProcessingTime()%60) + " min -- ");
        pStream.print("Wartezeit: ");
        pStream.print((long) Math.floor(part.getWaitingTime()/60) + " h, ");
        pStream.println(Math.round(part.getWaitingTime()%60) + " min") ;
      }
      pStream.print("-------------------------------------------------------");
      pStream.println("-----------------------------");
      pStream.print("Durchschnittswerte:              ");
      pStream.print((long) Math.floor(sojournTime.getMean()/60) + " h, ");
      pStream.print(Math.round(sojournTime.getMean()%60) + " min                      ");
      pStream.print((long) Math.floor(processingTime.getMean()/60) + " h, ");
      pStream.print(Math.round(processingTime.getMean()%60) + " min               ");
      pStream.print((long) Math.floor(waitingTime.getMean()/60) + " h, ");
      pStream.println(Math.round(waitingTime.getMean()%60) + " min \n\n");
      pStream.print("Laengste Verweilzeit eines Werkstuecks: ");
      pStream.print((long) Math.floor(sojournTime.getMaximum()/60) + " h, ");
      pStream.print(Math.round(sojournTime.getMaximum()%60) + "\n");
      pStream.print("Laengste Bearbeitungszeit eines Werkstuecks: ");
      pStream.print((long) Math.floor(processingTime.getMaximum()/60) + " h, ");
      pStream.print(Math.round(processingTime.getMaximum()%60) + "\n");
      pStream.print("Laengste Wartezeit eines Werkstuecks: ");
      pStream.print((long) Math.floor(waitingTime.getMaximum()/60) + " h, ");
      pStream.print(Math.round(waitingTime.getMaximum()%60) + "\n\n\n");
      pStream.println("Ergebnisse zu den Workstations");
      pStream.println("------------------------------");
      for (int i = 0; i < WORKSTAMOUNT; i++) {
        pStream.println("\nWorkstation"+workstations[i].getWorkstationID()+": \n");
        pStream.print("Durchschnittliche Anzahl von belegten Pufferplaetzen: ");
        pStream.print(workstations[i].bufferQueue.averageLength());
        pStream.println(" /von " + bufferSize[i]);
        pStream.print("Durchschnittliche Anzahl freier Maschinen: ");
        pStream.print(workstations[i].idleMachines.averageLength());
        pStream.println(" /von " + machineQuantity[i]);
        pStream.print("Durchschnittliche Anzahl blockierter Maschinen: ");
        pStream.print(workstations[i].blockedMachines.averageLength());
        pStream.println(" /von " + machineQuantity[i] + "\n\n");
      }
      try {
        foStream.close();
      }
      catch(IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
    catch(FileNotFoundException e) {
      System.out.println("FileNotFoundException: " + e.getMessage());
    }
  }

  /**
   * Starts the application.
   *
   * @param args  : is an array of command-line arguments
   */
  public static void main(java.lang.String[] args) {
    // make a new experiment
    Experiment msExperiment = new Experiment
      ("TheManufacturingSystemExperiment");
    // make a new model
    // null as first parameter because it is the main model and
    // has no mastermodel
    ManufacturingModel msModel = new ManufacturingModel
      (null, "The Manufacturing system model (p)", true, true,
//      new Integer(3), new Integer(3), new Integer(20),
//      new Integer(3), new Integer(2), new Integer(30),
//      new Integer(1), new Integer(2), new Integer(12),
//      new Integer(2), new Integer(3), new Integer(15));
      new Integer(2), new Integer(10), new Integer(20),
      new Integer(2), new Integer(2), new Integer(30),
      new Integer(3), new Integer(9), new Integer(12),
      new Integer(1), new Integer(10), new Integer(15));

//  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // set the seed for this experiment
    // This is DESMO's Distribution Manager, not DISMO's !!!
    msExperiment.getDistributionManager().setSeed(2);
//  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // connect Experiment and Model
    msModel.connectToExperiment(msExperiment);
    // now set time the trace/debug output may stop
    msExperiment.tracePeriod( new TimeInstant(1440), new TimeInstant(5760) );
    msExperiment.debugPeriod( new TimeInstant(1440), new TimeInstant(5760) );
    // now set the time this simulation should stop at
    // let him work 5760 Minutes
    msExperiment.stop(new TimeInstant(5760));
    // start the Experiment with start time 0.0
    msExperiment.setShowProgressBar(false);
    msExperiment.start();
    // now the simulation is running until it reaches its ending criteria
    // after reaching ending criteria, the main thread returns here
    // print the report about the already existing reporters into the report
    // file
    msExperiment.report();
    // stop all threads still alive and close all output files
    msExperiment.finish();
    msModel.printResults();
  }
}
