package desmoj.demo.mining;

import java.util.concurrent.TimeUnit;

import desmoj.core.advancedModellingFeatures.*;
import desmoj.core.dist.*;
import desmoj.core.simulator.*;
import desmoj.core.statistic.*;

/**
 * A model of a quarry as described in [Schn98] pages 171 following.
 * The model is implemented following the process oriented paradigm using
 * simprocesses to represent the active entities, diggers and trucks.
 * Simulated time units are hours.
 * @author Soenke Claassen
 * @author based on DESMO-C from Thomas Schniewind, 1998
 */
public class QuarryModel extends Model 
{

//   ******   attributes   ******

	/**
	* The number of small diggers working in the quarry 
	*/
	protected int nOfSmallDiggers;
	
	/**
	* The number of large diggers working in the quarry
	*/
	protected int nOfLargeDiggers;
	
	/**
	* The number of all diggers; for statistic
	*/
	protected int nOfAllDiggers;
	
	/**
	* The mean arrival time of the small trucks
	*/
	protected double meanSmallTArrival;

	/**
	* The mean arrival time of the large trucks
	*/
	protected double meanLargeTArrival;

	/**
	* The load rate of the small diggers
	*/
	protected double smallLoadRate;
	
	/**
	* The load rate of the large diggers
	*/
	protected double largeLoadRate;
	
	/**
	* The loading capacity of the small trucks
	*/
	protected double smallCapacity;
	
	/**
	* The loading capacity of the large trucks
	*/
	protected double largeCapacity;
	
	/**
	* The number of small diggers working right now
	*/
	protected int workingSmallDiggers;
	
	/**
	* The number of large diggers working right now
	*/
	protected int workingLargeDiggers;
	
	/**
	* The number of all working diggers; for statistic
	*/
	protected int allWorkingDiggers;
	

//   ***   random number streams   ***

	/**
	* The random distribution for the time span between arrivals of small 
	* trucks. A RealDistExponential distribution is used with a mean arrival 
	* time of 1/10 per hour.
	* It is set to <code>static</code> since one stream of random numbers
	* is needed for the class, not for individual objects.
	*/
	protected static ContDistExponential sTruckArrivalStream;
								
	/**
	* The random distribution for the time span between arrivals of large 
	* trucks. A RealDistExponential distribution is used with a mean arrival 
	* time of 1/22 per hour.
	* It is set to <code>static</code> since one stream of random numbers
	* is needed for the class, not for individual objects.
	*/
	protected static ContDistExponential lTruckArrivalStream;

	
//   ***   ValueSupplier   ***

	/** 
	* The ValueSupplier calculating the utilization of the small diggers.
	*/
	protected SmallDiggerUtilSuppl smallDiggerUtilSuppl;
	
	/** 
	* The ValueSupplier calculating the utilization of the large diggers.
	*/
	protected LargeDiggerUtilSuppl largeDiggerUtilSuppl;
	
	/** 
	* The ValueSupplier calculating the utilization of all diggers.
	*/
	protected AllDiggerUtilSuppl allDiggerUtilSuppl;
	
	/** 
	* The ValueSupplier calculating the total time a small truck has spent in 
	* the quarry (waiting for service and being loaded).
	*/
	protected SmallTruckStaySuppl smallTruckStaySupplier;
	
	/** 
	* The ValueSupplier calculating the total time a large truck has spent in 
	* the quarry (waiting for service and being loaded).
	*/
	protected LargeTruckStaySuppl largeTruckStaySupplier;


//   ***   Counts   ***

	/** 
	* The number of small diggers having completly loaded a large truck.
	*/
	protected Count smallDCompletedLargeT;


//   ***   Accumulates   ***

	/** 
	* The statistic calculating the utilization of the small diggers.
	*/
	protected Accumulate smallDiggerUtil;
	
	/** 
	* The statistic calculating the utilization of the large diggers.
	*/
	protected Accumulate largeDiggerUtil;

	/** 
	* The statistic calculating the utilization of all diggers.
	*/
	protected Accumulate allDiggerUtil;
	
	
//   ***   Tallies   ***

	/** 
	* The statistic calculating the total time a small truck has spent in 
	* the quarry (waiting for service and being loaded).
	*/
	protected Tally smallTruckStay;
	
	/** 
	* The statistic calculating the total time a large truck has spent in 
	* the quarry (waiting for service and being loaded).
	*/
	protected Tally largeTruckStay;

	/** 
	* The statistic calculating the total time a all trucks have spent in 
	* the quarry (waiting for service and being loaded).
	*/
	protected AllTruckTally allTruckStay;


//   ***   Histograms   ***

	/** 
	* The histogram showing the classification of the total times the small 
	* trucks have spent in the quarry (waiting for service and being loaded).
	*/
	protected Histogram smallTruckHistStay;
	
	/** 
  * The histogram showing the classification of the total times the large 
	* trucks have spent in the quarry (waiting for service and being loaded).
	*/
	protected Histogram largeTruckHistStay;


//   ***   TimeSeries   ***

	/** 
	* The file containing the time series data of the total times the small 
	* trucks have spent in the quarry (waiting for service and being loaded).
	*/
	protected TimeSeries smallTruckStayFile;	


//   ***   InterruptCodes   ***

	/** 
	* The InterruptCode indicating that a small truck has arrived. So the 
	* small diggers which is loading a large truck will be interrupted to 
	* serve the small truck first. 
	*/
	protected InterruptCode smallTruckArrived;
	
	/** 
	* The InterruptCode indicating that a large digger is free now to load the
	* large truck which is loaded by a small digger. The small digger will be 
	* interrupted.
	*/
	protected InterruptCode largeDiggerFree;


	//   ***   WaitQueue   ***

/**
 * The WaitQueue where the trucks are waiting to be loaded by the diggers.
 */
 protected WaitQueue diggerMeetsTruck;
  
 
//   ***   ProcessQueue   ***

/**
 * The ProcessQueue where the interruptable small diggers are stored. That 
 * are the ones which are loading a large truck.
 */
 protected ProcessQueue<SmallDigger> interruptableDiggers;	
	
	
//   ******   methods   ****** 
	
/**
 * Constructs the QuarryModel with the given number of large and small
 * diggers and trucks and the arrivaltimes, the loadrates and the capacities.
 * @param owner desmoj.Model : A model reference, if this is a submodel or
 * <code>null</code> otherwise
 * @param name java.lang.String : The name of the model 
 * @param nOfSmallDiggers int : The number of small diggers working in the 
 * quarry.
 * @param nOfLargeDiggers int : The number of  large diggers working in the 
 * quarry.
 * @param meanSTArrival double : The time span between arrivals of small 
 * trucks.
 * @param meanLTArrival double : The time span between arrivals of large 
 * trucks.
 * @param smallDigLoadRate double : The load rate of the small diggers.
 * @param largeDigLoadRate double : The load rate of the large diggers.
 * @param smallTruckCapac double : The capacity of the small trucks.
 * @param largeTruckCapac double : The capacity of the large trucks.
 */
public QuarryModel(Model owner,	// null if main model
								 String name,						// the name of the model
								 int nOfSmallDiggers,		// the number of small diggers
								 int nOfLargeDiggers,		// the number of large diggers
								 double meanSTArrival	,	// mean arrival time of small trucks
								 double meanLTArrival	,	// mean arrival time of large trucks
								 double smallDigLoadRate,	// load rate of the small diggers
								 double largeDigLoadRate,	// load rate of the large diggers
								 double smallTruckCapac	,	// capacity of the small truck
								 double largeTruckCapac	)	// capacity of the large truck
{		
	super(owner, name, true, true);

	// check given parameters and set the number of small and large diggers
	if ( nOfSmallDiggers > 0 ) this.nOfSmallDiggers = nOfSmallDiggers;
	else this.nOfSmallDiggers = 1;
	if ( nOfLargeDiggers > 0 ) this.nOfLargeDiggers = nOfLargeDiggers;
	else this.nOfLargeDiggers = 2;
	// set the number of all diggers
	this.nOfAllDiggers = nOfSmallDiggers + nOfLargeDiggers;	
	
	// check given parameters and set the mean arrival time for the trucks
	if ( meanSTArrival > 0.0 ) this.meanSmallTArrival = meanSTArrival;
	else this.meanSmallTArrival = 1.0/10.0;	// h
	if ( meanLTArrival > 0.0 ) this.meanLargeTArrival = meanLTArrival;
	else this.meanLargeTArrival = 1.0/22.0;	// h
	
	// check given parameters and set the load rates for the diggers
	if ( smallDigLoadRate > 0.0 ) this.smallLoadRate = smallDigLoadRate;
	else this.smallLoadRate = 60.0;	// t/h
	if ( largeDigLoadRate > 0.0 ) this.largeLoadRate = largeDigLoadRate;
	else this.largeLoadRate = 240.0;	// t/h
	
	// check given parameters and set the capacities of the s and l trucks
	if ( smallTruckCapac > 0.0 ) this.smallCapacity = smallTruckCapac;
	else this.smallCapacity = 5.0;		// t
	if ( largeTruckCapac > 0.0 ) this.largeCapacity = largeTruckCapac;
	else this.largeCapacity = 20.0;	// t
	
	// no one is working at the beginning
	this.workingSmallDiggers = this.workingLargeDiggers =
	this.allWorkingDiggers = 0;
}
/**
 * Activates a new large truck at the point of simulation time given by the
 * lTruckArrivalStream. 
 */
	public void activateNewLargeTruck()
	{
		LargeTruck newLargeTruck = new LargeTruck ( this, largeCapacity,
																								diggerMeetsTruck,
																								lTruckArrivalStream,
																								largeTruckStaySupplier );
																								
		// schedule newLargeTruck to be activated according to the 
		// lTruckArrivalStream of the large trucks
		// Note that method 'activate' needs a SimTime object, which is
		// created "on-the-fly" with the value returned from lTruckArrivalStream
		newLargeTruck.activate(new TimeSpan (lTruckArrivalStream.sample()));
	}
/**
 * Activates a new small truck at the point of simulation time given by the
 * sTruckArrivalStream. 
 */
	public void activateNewSmallTruck()
	{
		
	/*
		SmallTruck newSmallTruck = new SmallTruck ( this, smallCapacity,
																								diggerMeetsTruck, 
																								interruptableDiggers,
																								smallTruckArrived,
																								smallTruckStaySupplier );
		
		// schedule newSmallTruck to be activated according to the 
		// sTruckArrivalStream of the small trucks
		// Note that method 'activate' needs a SimTime object, which is
		// created "on-the-fly" with the value returned from sTruckArrivalStream																						
		newSmallTruck.activate( new SimTime( sTruckArrivalStream.sample() ) );
 */
	
	}
/**
 * Decrements the number of large diggers working at the moment by one and 
 * updates the statistic 
 */
	public void decrementWorkingLDig()
	{
		// decrement the working small diggers by one
		workingLargeDiggers -= 1;
		
		if ( workingLargeDiggers < 0 )
		{
			sendWarning ( "Attempt to release more large diggers than there are "+
				"in the quarry. The command will be ignored!",
				"QuarryModel: " + this.getName() + " Method: void " +
				"decrementWorkingLDig(). " ,
				"There can not be more diggers having finished working than there "+
				"are in the quarry in total.",
				"Make sure not to free more diggers than there are in the quarry.");
			
			// set the number of working large diggers right 
			workingLargeDiggers = 0;		
			return;		// and return
		}
		
		allWorkingDiggers = workingSmallDiggers + workingLargeDiggers;
		
		// make the ValueSuppliers notify all their Observers
		largeDiggerUtilSuppl.notifyStatistics( new Double( (workingLargeDiggers 
																													* 100) 
																											 / nOfLargeDiggers ) );
		
		allDiggerUtilSuppl.notifyStatistics( new Double( (allWorkingDiggers 
																												* 100) 
																										 / nOfAllDiggers ) );
	}
/**
 * Decrements the number of small diggers working at the moment by one and 
 * updates the statistic. 
 */
	public void decrementWorkingSDig()
	{
		// decrement the working small diggers by one
		workingSmallDiggers -= 1;
		
		if ( workingSmallDiggers < 0 )
		{
			sendWarning ( "Attempt to release more small diggers than there are "+
				"in the harbour. The command will be ignored!",
				"HarbourModel: " + this.getName() + " Method: void " +
				"decrementWorkingSDig(). " ,
				"There can not be more diggers having finished working than there "+
				"are in the quarry in total.",
				"Make sure not to free more diggers than there are in the quarry.");
			
			// set the number of working small diggers right 
			workingSmallDiggers = 0;		
			return;		// and return
		}
		
		allWorkingDiggers = workingSmallDiggers + workingLargeDiggers;

		// make the ValueSuppliers notify all their Observers
		smallDiggerUtilSuppl.notifyStatistics( new Double( (workingSmallDiggers 
																													* 100) 
																											 / nOfSmallDiggers ) );
		
		allDiggerUtilSuppl.notifyStatistics( new Double( (allWorkingDiggers 
																												* 100) 
																										 / nOfAllDiggers ) );
	}
/**
 * Describes the system this model intends to simulate.
 * @return java.lang.String : The String describing the model
 */
public String description() {
	
	return "The QuarryModel is a process oriented model of a quarry where  "+
					"diggers are working to load trucks with stones. There are two  "+
					"types of diggers (large and small) and two types of trucks "+
					"(large and small). The small diggers are the only ones to load " +
					"the small trucks with the high quality stones. But they can also "+
					"load the large trucks with large stones. The large diggers can " +
					"only load the large trucks. So a small digger loading a large " +
					"truck will be interrupted, if a small truck arrives or a large " +
					"digger is available again. Simulation time units are hours. " +
					"Total simulation time are 240 hours. <br>" +
					"A more detailed description of this "+
					"model (in german) can be found in [Schn98] pages 171 following."+
					"<br> Capacity of the trucks: <br>" +
					"   large truck: 20 t <br>" +
					"   small truck:  5 t <br>" +
					" Load rate of the diggers: <br>" +
					"   large digger: 240 t/h <br>" +
					"   small digger:  60 t/h <br>" ;
}
/**
 * Makes the diggers and starts them at simulation time 0.0 and also 
 * activates the first small and large truck, to setup the model into a 
 * defined state to start with.
 */
public void doInitialSchedules() 
{
	// make the SmallDiggers and get them started
	for ( int i = 1; i <= nOfSmallDiggers; i++ )
	{
		SmallDigger sd = new SmallDigger ( this, diggerMeetsTruck, 
																				smallLoadRate, 
																				interruptableDiggers, 
																				smallDCompletedLargeT,
																				smallDiggerUtilSuppl );
		sd.activate();
		System.out.println("SmallDigger No." + i + " started.");
	}
	
	// make the LargeDiggers and get them started
	for ( int i = 1; i <= nOfLargeDiggers; i++ )
	{
		LargeDigger ld = new LargeDigger ( this, diggerMeetsTruck, 
																				largeLoadRate, 
																				interruptableDiggers, 
																				largeDiggerFree );
		ld.activate();
		System.out.println("LargeDigger No." + i + " started.");
	}
	
	// make the first small truck and schedule him
	// activateNewSmallTruck();

	// start the source (ExternalEvent) for the small trucks
	SmallTruckArrivalEvent smallTrucksGo = new SmallTruckArrivalEvent( this,
																								smallCapacity,
																								diggerMeetsTruck,
																								interruptableDiggers,
																								smallTruckArrived,
																								sTruckArrivalStream,
																								smallTruckStaySupplier );
	
	smallTrucksGo.schedule(new TimeSpan( sTruckArrivalStream.sample() ) );
																								
	// make the first large truck and schedule him
	activateNewLargeTruck();
}
/**
 * Returns the number of all diggers in the quarry.
 * @return int : The number of all diggers in the quarry. 
 */
	public int getAllDiggers()
	{
		return nOfAllDiggers;
	}
/**
 * Returns the number of all diggers working at the moment.
 * @return int : The number of all diggers working at the moment. 
 */
	public int getAllWorkingDiggers()
	{
		return allWorkingDiggers;
	}
/**
 * Returns the number of large diggers in the quarry.
 * @return int : The number of large diggers in the quarry. 
 */
	public int getLargeDiggers()
	{
		return nOfLargeDiggers;
	}
/**
 * Returns the number of small diggers in the quarry.
 * @return int : The number of small diggers in the quarry. 
 */
	public int getSmallDiggers()
	{
		return nOfSmallDiggers;
	}
/**
 * Returns the number of large diggers working at the moment.
 * @return int : The number of large diggers working at the moment. 
 */
	public int getWorkingLargeDiggers()
	{
		return workingLargeDiggers;
	}
/**
 * Returns the number of small diggers working at the moment.
 * @return int : The number of small diggers working at the moment. 
 */
	public int getWorkingSmallDiggers()
	{
		return workingSmallDiggers;
	}
/**
 * Increments the number of large diggers working at the moment by one and 
 * updates the statistic. 
 */
	public void incrementWorkingLDig()
	{
		// increment the working large diggers by one
		workingLargeDiggers += 1;
		
		if ( workingLargeDiggers > nOfLargeDiggers )
		{
			sendWarning ( "Attempt to use more large diggers than there are in "+
				"the quarry. The command will be ignored!",
				"QuarryModel: " + this.getName() + " Method: void " +
				"incrementWorkingLDig(). " ,
				"There can not be more diggers working than there are in the " +
				"quarry.",
				"Make sure not to put more diggers to work than there are in the "+
				"quarry.");
			
			// set the number of working large diggers right 
			workingLargeDiggers = nOfLargeDiggers;		
			return;		// and return
		}
		
		allWorkingDiggers = workingSmallDiggers + workingLargeDiggers;
		
		// make the ValueSuppliers notify all their Observers
		largeDiggerUtilSuppl.notifyStatistics( new Double( (workingLargeDiggers 
																													* 100) 
																											 / nOfLargeDiggers ) );
		
		allDiggerUtilSuppl.notifyStatistics( new Double( (allWorkingDiggers 
																												* 100) 
																										 / nOfAllDiggers ) );
	}
/**
 * Increments the number of small diggers working at the moment by one and 
 * updates the statistic. 
 */
	public void incrementWorkingSDig()
	{
		// increment the working small diggers by one
		workingSmallDiggers += 1;
		
		if ( workingSmallDiggers > nOfSmallDiggers )
		{
			sendWarning ( "Attempt to use more small diggers than there are in  "+
				"the harbour. The command will be ignored!",
				"HarbourModel: " + this.getName() + " Method: void " +
				"incrementWorkingSDig(). " ,
				"There can not be more diggers working than there are in the " +
				"harbour.",
				"Make sure not to put more diggers to work than there are in the "+
				"harbour.");
			
			// set the number of working small diggers right 
			workingSmallDiggers = nOfSmallDiggers;		
			return;		// and return
		}
		
		allWorkingDiggers = workingSmallDiggers + workingLargeDiggers;
		
		// make the ValueSuppliers notify all their Observers
		smallDiggerUtilSuppl.notifyStatistics( new Double( (workingSmallDiggers 
																													* 100) 
																											 / nOfSmallDiggers ) );
		
		allDiggerUtilSuppl.notifyStatistics( new Double( (allWorkingDiggers 
																												* 100) 
																										 / nOfAllDiggers ) );
	}
/**
 * Inits the model's random number streams, the WaitQueue, the ProcessQueue,
 * the interrupt codes, the ValueSuppliers and the statistic objects (Count, 
 * Tally, Accumulate, Histogram anf TimeSeries) that are used by this
 * process-oriented implementation of the model.
 */
@SuppressWarnings("deprecation")
public void init() 
{
	// init the random streams 
	sTruckArrivalStream = new ContDistExponential ( this, 
																									"smallTruck arrival",
																									meanSmallTArrival, 
																									true, true);
																									
	lTruckArrivalStream = new ContDistExponential ( this, 
																									"largeTruck arrival",
																									meanLargeTArrival,
																									true, true );
	// init the WaitQueue
	diggerMeetsTruck = new WaitQueue ( this, "DiggerLoadsTruck WQ",
	 																	true, true );
																			
	// init the ProcessQueue for the small diggers loading large trucks
	interruptableDiggers = new ProcessQueue<SmallDigger> ( this, "smallD loads LargeT",
																						true, true );
	// init the interrupt codes
	smallTruckArrived = new InterruptCode ( "S-Truck arrived" );
	largeDiggerFree  = new InterruptCode ( "L-Digger free" );
	
	// init the ValueSuppliers
		// the digger utilization
	smallDiggerUtilSuppl = new SmallDiggerUtilSuppl ( this );
	largeDiggerUtilSuppl = new LargeDiggerUtilSuppl ( this );
	allDiggerUtilSuppl = new AllDiggerUtilSuppl ( this );
	
		// the truck stay
	smallTruckStaySupplier = new SmallTruckStaySuppl ( this );
	largeTruckStaySupplier = new LargeTruckStaySuppl ( this );
	
	// init the Count statistic
	smallDCompletedLargeT = new Count ( this, "smallD compl.largeT",
																			true, true );
																			
	// init the Accumulate statistics and make them observe the right 
	// ValueSuppliers
	smallDiggerUtil = new Accumulate ( this, "S-Digger Util", 
																			smallDiggerUtilSuppl, false,
																			true, true );
	largeDiggerUtil = new Accumulate ( this, "L-Digger Util", 
																			largeDiggerUtilSuppl, false,
																			true, true );
	allDiggerUtil = new Accumulate ( this, "all Digger Util", 
																			allDiggerUtilSuppl, false,
																			true, true );
																			
	// init the Tally statistics and make them observe the right 
	// ValueSuppliers
	smallTruckStay = new Tally ( this, "S-Truck Stay", 
																	smallTruckStaySupplier, true, true );
	largeTruckStay = new Tally ( this, "L-Truck Stay", 
																	largeTruckStaySupplier, true, true );
	allTruckStay = new AllTruckTally ( this, "all Truck Stay", 
																			smallTruckStaySupplier,
																			largeTruckStaySupplier, true, true );

	// init the Histogram statistics and make them observe the right 
	// ValueSuppliers
	smallTruckHistStay = new Histogram ( this, "S-Truck Stay Hist.", 
																	smallTruckStaySupplier, 
																	0.08, 1.68, 8, true, true );
	largeTruckHistStay = new Histogram ( this, "L-Truck Stay Hist.", 
																	largeTruckStaySupplier,
																	0.08, 1.68, 8, true, true );
	
	// make the start and end SimTime objects we need for the TimeSeries
    TimeInstant start = new TimeInstant(0.0);
    TimeInstant end = new TimeInstant(10.0);
	
	// init the TimeSeries statistics and make it observe the right 
	// ValueSupplier
	smallTruckStayFile = new TimeSeries ( this, "S-Truck Stay File",
																				"STruckStayFile",  
																				smallTruckStaySupplier, 
																				start, end,
																				false,		// no automatic recording 
																				true, true );
}
/**
 * The main method to start an experiment with this model.
 * @param args java.lang.String[] : The arguments are not used in this model
 */
public static void main(String args[]) 
{
	System.out.println("main started.");
	
    Experiment quarryExperiment = new Experiment("mining", true);

	QuarryModel quarryModel = new QuarryModel (null,	// null if main model
								 									"claassen_mining Model",	// the name of the model
								 							1,		// the number of small diggers
								 							2,		// the number of large diggers
								 							1/10	,	// mean arrival time of small trucks
								 							1/22	,	// mean arrival time of large trucks
								 							60,		// load rate of the small diggers
								 							240,	// load rate of the large diggers
								 							5	,			// capacity of the small truck
								 							20	);	// capacity of the large truck

	// connect Experiment and Model
	quarryModel.connectToExperiment(quarryExperiment);	

	// now set the time this simulation should stop at 240 hours
	quarryExperiment.stop( new TimeInstant(240) );
		
	// now set the trace output to stop after 0.4 hours
	// otherwise a 10 MB html page will be created which crashes Netscape :-)
	quarryExperiment.tracePeriod( new TimeInstant(0.0), new TimeInstant(0.37) );

	// start the Experiment with start time 0.0
	quarryExperiment.start();

	// --> now the simulation is running until simclock is set to 240 or more

	// <-- after reaching 240 hours, the main thread returns here

	// print the report about the already existing reporters into the report 
	// file
	quarryExperiment.report();

	// stop all threads still alive and close all output files
	quarryExperiment.finish();
}
}		// end class QuarryModel
