package desmoj.demo.manufacturing;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 * This class is part of the "ManufacturingModel".
 * See the description() method of the model class for
 * further documentation of the basis model.
 *
 * This class represents a machine of a workstation in the model
 * mentioned above.
 * The machine waits for blank parts to process.
 * After processing the machine discharges the parts.
 *
 * Creation date: 28.06.01
 * @author Bente Matzen
 */
public class MachineProcess extends SimProcess {

  /**
   * Keeps a reference to the model this machine is a part of.
   */
  private ManufacturingModel myModel;

  /**
   * Keeps a reference to the workstation this machine is a part of.
   */
  private WorkstationProcess myWorkstation;

  /**
   * The attribut blocked or not
   */
  public boolean blocked;

  /**
   * This method constructs a new machine
   * @param owner desmoj.Model    ---> the associated model
   * @param subowner WorkstationProcess
   *                              ---> the associated workstation
   * @param name java.lang.String ---> of the machine
   * @param showInTrace boolean   ---> show in trace file or not show in trace
   * @param showInReport boolean  ---> show in report file or not
   */
  public MachineProcess
  (Model owner, WorkstationProcess subowner, String name, boolean showInTrace,
    boolean showInReport) {

    super(owner, name, showInTrace);
    myModel = (ManufacturingModel) owner;
    myWorkstation = (WorkstationProcess) subowner;
    blocked = false;
  }

  /**
   * This lifeCycle() describes what the workstation does when it
   * becomes activated by DESMO-J.
   *
   * It will cycle through a process like this:
   *
   * A1) if there is at least one blank part waiting in the buffer
   *      a1) if this machine isn't blocked
   *            - get a blank part out of the buffer
   *            - activate the workstation this machine is part of
   *            - wait while processing the blank part
   *            -1) if this machine doesn't belong to the last workstation
   *                  - try to put the blank part into the buffer of
   *                    the next workstation
   *                  - activate next workstation
   *            -2) if this machine belongs to the last workstation
   *                  - put the blank part into the accumulation bin
   *      a2) if this machine is blocked
   *            - insert machine into queue of blocked machines
   *            - wait while being blocked
   * A2) if there is no blank part waiting in the buffer
   *      a) insert machine into waiting queue of idle machines
   *      b) wait until a blank part reaches the buffer
   * B) return to A)
   *
   */
  public void lifeCycle() throws SuspendExecution {
    BlankPart nextBlankPart;
    int currentNumber;
    WorkstationProcess nextWorkstation;

    while(true) {
        if (!blocked) {
          if (myWorkstation.bufferQueue.length() != 0) {
            nextBlankPart = (BlankPart) myWorkstation.bufferQueue.first();
            myWorkstation.bufferQueue.remove(nextBlankPart);
            //activate current Workstation
            myWorkstation.activate();
            //processing the blank part...
            double procTime = myWorkstation.getProcessingTime();
            nextBlankPart.addProcessingTime(procTime);
            hold(new TimeSpan(procTime));
            currentNumber = myWorkstation.getWorkstationID() - 1;
            //if the workstation this machine belongs to isn't the last one
            if (currentNumber < (myModel.getWorkstamount() - 1)) {
              //try to put the blank part into the next bufferQueue
              nextWorkstation = myModel.workstations[currentNumber + 1];
              blocked = nextWorkstation.putIntoBuffer(nextBlankPart);
              //activate next workstation to process the blank part
              myModel.workstations[currentNumber + 1].activate();
            } //end if (currentNumber < (myModel.getWorkstamount() - 1))
            else {
            sendTraceNote
              ("The " + nextBlankPart + " was serviced and leaves system.");
            //set the moment when this blank part is leaving the system
            nextBlankPart.leavingTime = presentTime().getTimeAsDouble();
            //the blank part has to inform the statistic object 'sojournTime'
            //about its sojournTime
            myModel.updateSojournTime(nextBlankPart.getSojournTime());
            //inform the statistic object 'processingTime' about processingTime
            myModel.updateProcessingTime(nextBlankPart.getProcessingTime());
            //inform the statistic object 'waitingTime' about waitingTime
            myModel.updateWaitingTime(nextBlankPart.getWaitingTime());
            //insert the processed blank part into the BlankPartQueue
            myModel.addBlankPartQueue(nextBlankPart);
            //increase the count object
            myModel.updateThroughput();
          } //end else

      }
      else {
        myWorkstation.idleMachines.insert(this);
        passivate();
      }
              } //end if (!blocked)
              else {
          myWorkstation.blockedMachines.insert(this);
          passivate();
        }
    }// end while
  }
}