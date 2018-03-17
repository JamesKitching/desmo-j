package desmoj.demo.manufacturing;

import desmoj.core.simulator.*;

/**
 * This class is part of the "ManufacturingModel".
 * See the description() method of the model class for
 * further documentation of the basis model.
 *
 * This class represents a blank part in the model mentioned above.
 *
 * Creation date: 28.06.01
 * @author Bente Matzen
 */
public class BlankPart extends Entity {
  /**
   * The ID of this blank part
   */
  private long id;

  /**
   * The current ID of blank parts
   */
  public static long currentID = 1;

  /**
   * Moment of arrival into the system
   */
  public double arrivalTime;

  /**
   * Moment of leaving the system
   */
  public double leavingTime;

  /**
   * The period of being processed by all workstations
   */
  private double processingTime;

  /**
   * The constructor creates a new blank part
   */
  public BlankPart(Model owner, String name, boolean showInTrace) {
    super(owner, name, showInTrace);
    id = currentID;
    currentID++;
  }

  /**
   * This method returns the value of ID
   */
  public long getID() {
    return id;
  }

  /**
   * This method returns the sojourn time in the system
   */
  public double getSojournTime() {
    return (leavingTime - arrivalTime);
  }

  /**
   * This method returns the waiting time
   */
  public double getWaitingTime() {
    return (getSojournTime() - processingTime);
  }

  /**
   * This method returns the processing time
   */
  public double getProcessingTime() {
    return processingTime;
  }

  /**
   * This method adds a new period of processing to the current cumulated
   * processing time
   */
  public void addProcessingTime(double time) {
    processingTime += time;
  }
}