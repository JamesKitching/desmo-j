package desmoj.demo.manufacturing;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.dist.*;
/**
 * This class is part of the "ManufacturingModel".
 * See the description() method of the model class for
 * further documentation of the basis model.
 *
 * This class represents a workstation in the model
 * mentioned above.
 * The workstation consists of one buffer with a buffersize of n and
 * m machines which are able to process m blank parts at the same time.
 *
 * Creation date: 28.06.01
 * @author Bente Matzen
 */
public class WorkstationProcess extends SimProcess {

  /**
   * Keeps a reference to the model this actor is a part of.
   * usefull shortcut to access the model infrastructure
   */
  private ManufacturingModel myModel;

  /**
   * Number of the workstation
   */
  private int workstationID;

  /**
   * The size of the buffer
   */
  private int bufferSize;

  /**
   * Queue of blank parts which can't get placed into the next buffer
   */
  private Queue waitingQueue;

  /**
   * The queue of blank parts in the buffer
   */
  public Queue bufferQueue;

  /**
   * The number of machines in a workstation
   */
  private int machineQuantity;

  /**
   * The queue of idle machines
   */
  public ProcessQueue idleMachines;

  /**
   * Queue of blocked machines
   * machines which are not able to work because they cannot place the
   * blank part into the next buffer
   */
  public ProcessQueue blockedMachines;

  /**
   * Random stream used to draw the processing time for blank parts
   * in the workstation.
   * Describes the time needed by a machine of a workstation to get a
   * blank part processed.
   */
  private ContDistExponential processingTime;

  /**
   * This method constructs a new workstation with limited buffer
   * @param owner desmoj.Model    ---> the associated model
   * @param name java.lang.String ---> of the workstation
   * @param showInTrace boolean   ---> show in trace file or not show in trace
   * @param showInReport boolean  ---> show in report file or not
   * @param size integer          ---> of the buffer in front of the workstation
   * @param quantity integer      ---> of the machines in the workstation
   * @param meanProcTime          ---> mean processing time for a machine in
   *                                   the workstation in minutes
   */
  public WorkstationProcess
  (Model owner, String name, int number, boolean showInTrace,
    boolean showInReport, int size, int quantity, float meanProcTime) {

    super(owner, name, showInTrace);
    myModel = (ManufacturingModel) owner;
    workstationID = number;
    waitingQueue =
      new Queue(owner, "Waiting queue of " + name, showInReport, showInTrace);
    bufferSize = size;
    bufferQueue =
      new Queue(owner, "Buffer queue of " + name, showInReport, showInTrace);
    machineQuantity = quantity;
    idleMachines = new ProcessQueue
      (owner, "idle machines of " + name, showInReport, showInTrace);
    blockedMachines = new ProcessQueue
      (owner, "blocked machines of " + name, showInReport, showInTrace);
    //make instance of the processingdistribution
    processingTime = new ContDistExponential
      (owner, "Processing time for blank part in " + name, meanProcTime, true, true);
    processingTime.setNonNegative(true);
  }

  /**
   * This method returns the ID/number of the workstation
   */
  public int getWorkstationID() {
    return workstationID;
  }

  /**
   * This method returns a sample out of the random stream used to measure
   * time needed to process the blank part.
   */
  public double getProcessingTime() {
    return processingTime.sample();
  }

  /**
   * This method puts a blank part into the buffer and returns false "fuer erfolgreich"
   * if there is no place in the buffer the blank part will be put into
   * the waitingQueue and the method returns true "fuer misslungen"
   */
  public boolean putIntoBuffer(BlankPart part) {
    //if there is place in buffer
    if (bufferQueue.length() < bufferSize) {
      //insert the blank part into the bufferQueue
      bufferQueue.insert(part);
      if (workstationID == 1)
        part.arrivalTime = presentTime().getTimeAsDouble();
      return false;
    }
    else {
      //if there is no place insert it into the waitingQueue
      waitingQueue.insert(part);
      return true;
    }
  }

  /**
   * This lifeCycle() describes what the workstation does when it
   * becomes activated by DESMO-J.
   *
   * It will cycle through a process like this:
   *
   * A) create the machines of this workstation
   * B) insert the machines in the waiting queue of idle machines
   * C1) if there is at least one blank part waiting in the buffer
   *      a) get as many machines as possible or necessary out of the queue
   *         of idle machines
   *      b) activate the machines
   * C2) if there is no blank part waiting in the buffer
   *      a) go to D)
   * D) be passivated until a new blank part reaches the buffer or until
   *    this workstation has processed a blank part
   * E1) if there is at least one blank part waiting for place in the buffer
   *     of this workstation
   *      a) put as many blank parts as possible or necessary into the buffer
   *      b1) if there is at least one blank part put into the buffer and
   *          this isn't workstation1
   *              release and activate the concerned blocked machines
   *      b2) if there is either no blank part put into the buffer or
   *          this is workstation1
   *              return to C)
   * E2) if there is no blank part waiting for place in the buffer
   *      a) return to C)
   * F) return to C)
   *
   */
  public void lifeCycle() throws SuspendExecution {
    MachineProcess newMachine, idleMachine, previousMachine;
    int freeMachines, movingAmount, waitingParts, processingParts, bufferRoom,
      processingAmount;
    BlankPart nextBlankPart;
    boolean moved = false;

    for (int i = 1; i <= machineQuantity; i++) {
      newMachine = new MachineProcess
        (myModel, this, "Machine" + i + " of workstation" + workstationID,
          true, true);
      idleMachines.insert(newMachine);
    }
    //the servicer is always on duty and will never stop working
    while (true) {
      processingParts = bufferQueue.length();
      freeMachines = idleMachines.length();
      if (processingParts != 0) {
        //find out how many blank parts can or want to be processed
        if (freeMachines < processingParts)
          processingAmount = freeMachines;
        else
          processingAmount = processingParts;
        //activate as many machines as can or should process blank parts
        for (int i = 1; i <= processingAmount; i++) {
          idleMachine = (MachineProcess) idleMachines.first();
          idleMachines.remove(idleMachine);
          idleMachine.activate();
        } // end for
      } // end if
      passivate();

      waitingParts = waitingQueue.length();
      bufferRoom = bufferSize - bufferQueue.length();
      //if there are some blank parts in the waitingQueue
      if (waitingParts != 0) {
        //find out how many blank parts can or want to be moved
        if (bufferRoom < waitingParts)
          movingAmount = bufferRoom;
        else
          movingAmount = waitingParts;
        //try to put blank parts out of the waitingQueue
        //into the bufferQueue
        for (int i = 1; i <= movingAmount; i++) {
          nextBlankPart = (BlankPart) waitingQueue.first();
          waitingQueue.remove(nextBlankPart);
          bufferQueue.insert(nextBlankPart);
          if (workstationID == 1)
            nextBlankPart.arrivalTime = presentTime().getTimeAsDouble();
          moved = true;
        } // end for
        //tell the machine of the previous workstation
        //not to be blocked any longer
        if ((moved) & (workstationID != 1)) {
          previousMachine = (MachineProcess)
            myModel.workstations[workstationID-2].blockedMachines.first();
          myModel.workstations[workstationID-2].blockedMachines.
            remove(previousMachine);
          previousMachine.blocked = false;
          moved = false;
          previousMachine.activate();
        } // end if (workstationID != 0)
      } // end if (waitingParts != 0)
    } // end while
  } // end lifecycle()
}
