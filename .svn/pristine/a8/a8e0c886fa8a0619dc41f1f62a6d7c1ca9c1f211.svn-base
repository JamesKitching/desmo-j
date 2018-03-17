package desmoj.demo.manufacturing;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.dist.*;

/**
 * This class is part of the "ManufacturingModel".
 * See the description() method of the model class for
 * further documentation of the basis model.
 *
 * This class represents a process source, which produces continous
 * blank part arrivals in order to keep the simulation running.
 * It will produce a new blank part, schedule it now and place itself on the
 * eventlist, when the next blank part is expected.
 *
 * Creation date: 18.06.01
 * @author Bente Matzen
 */
public class BlankPartGenerator extends ExternalEvent {
  /**
   * Keeps a reference to the model this actor is a part of
   */
  private ManufacturingModel myModel;

  /**
   * This method constructs a blank part generator
   * @param owner desmoj.Model     ---> the associated model
   * @param name java.lang.String   ---> of the workstation
   * @param showInReport boolean   ---> show in report file or not
   */
  public BlankPartGenerator(Model owner, String name, boolean showInReport) {
    super(owner, name, showInReport);
    myModel = (ManufacturingModel) owner;
  }

  /**
   * This eventRoutine describes what an object of this class
   * will do, when it becomes activated by DESMO-J
   */
  public void eventRoutine() throws SuspendExecution {

    //create a new blank part
    BlankPart newBlankPart =
      new BlankPart(myModel, "BlankPart", true);
    //put it into the buffer of workstation1
    myModel.workstations[0].putIntoBuffer(newBlankPart);
    //activate workstation1
    myModel.workstations[0].activate();

    //because we need another blank part arrival we will activate
    //this blank part generator again at the next blank part arrival time
    this.schedule(new TimeSpan(myModel.getPartArrivalTime()));
  } //end eventRoutine()
}