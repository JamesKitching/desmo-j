package desmoj.demo.bungee2D;

import java.awt.Point;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import desmoj.core.dist.BoolDist;
import desmoj.core.dist.BoolDistBernoulli;
import desmoj.core.dist.ContDist;
import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.DiscreteDist;
import desmoj.core.dist.DiscreteDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.PatternBasedTimeFormatter;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.CmdGeneration;
import desmoj.extensions.visualization2d.animation.Form;
import desmoj.extensions.visualization2d.animation.FormExt;
import desmoj.extensions.visualization2d.animation.Position;
import desmoj.extensions.visualization2d.animation.core.simulator.ModelAnimation;
import desmoj.extensions.visualization2d.animation.core.simulator.ProcessQueueAnimation;
import desmoj.extensions.visualization2d.animation.core.statistic.AccumulateAnimation;
import desmoj.extensions.visualization2d.animation.core.statistic.CountAnimation;
import desmoj.extensions.visualization2d.animation.core.statistic.TallyAnimation;
import desmoj.extensions.visualization2d.animation.processStation.ProcessStationAnimation;
import desmoj.extensions.visualization2d.animation.transport.TransportRouteAnimation;
import desmoj.extensions.visualization2d.animation.transport.TransportStationAnimation;
import desmoj.extensions.visualization2d.engine.Constants;
import desmoj.extensions.visualization2d.engine.command.Command;

/** A process-oriented model of the bungee jumping facility "extreme jump".
 *
 *  This is the model class. It simulates a bungee jumping tower where groups
 *  of customers arrive to perform bungee jumping. They first have to buy
 *  a ticket from one of the two cashiers working at the ticket booth before they
 *  proceed towards the platform on top of the tower. There a bungee attendant 
 *  attaches the bungee cord to a customer. 
 *  As soon as the jumping area is clear (i.e. no other customer
 *  jumping or being detached from the rope on ground level), the 
 *  customer starts his jump and (once arrived at ground level)
 *  gets detached from the rope.
 *  A customer may or may not queue at the photo booth to buy a photo
 *  of himself automatically shot during the jump. 25% of all customers
 *  want to attempt another jump.
 *
 *  @author Ruth Meyer, Nicolas Knaak
 */
public class ModelBungee extends ModelAnimation{
	
	/** model parameter: number of cashiers */
	int NUM_CASHIERS = 2;
	/** model parameter: number of attendants */
	int NUM_ATTENDANTS = 3;
	/** model parameter: number of photo sellers */
	int NUM_PHOTOSELLERS = 1;

	/** distribution for arrivals of customers */
	ContDist arrivalDist;
	/** distribution for the number of customers arriving together */
	DiscreteDist<Long>  groupDist;
	/** distribution for the time needed to pay for a jump */
	ContDist payingJumpDist;
	/** distribution for the time a customer needs to climb the platform */
	ContDist climbingDist;
	/** distribution for the time needed to attach the cord */
	ContDist prepareDist;
	/** distribution for the jumping time */
	ContDist jumpDist;
	/** distribution for the time needed to detach the cord again */
	ContDist detachDist;
	/** distribution the decision whether or not to buy a photo */
	BoolDist wantsPhotoDist;
	/** distribution for the time needed to select/pay for a photo */
	ContDist payingPhotoDist;
	/** distribution the decision whether or not to jump again */
	BoolDist wantsAnotherJumpDist;
	/** distribution for rain duration */
	ContDist rainDurationDist;
	/** distribution for dry period duration */
	ContDist dryDurationDist;
	
	/** tally for the duration of customers' jumps (including paying on entry and photo selling)*/
	TallyAnimation jumpDuration;
	/** tally for number of jumps per customer */
	TallyAnimation jumpsPerCustomer;
	/** count for customer counting */
	CountAnimation customersProcessed;
	/** accumulates for utilization of staff */
	AccumulateAnimation cashierUtil, attendantUtil, photosellerUtil;
	
	

	/** queue for customers waiting to pay for the jump */
	ProcessQueueAnimation<Customer> waitAtCounterQ;
	/** queue for customers waiting to climb the plattform (due to rain) */
	ProcessQueueAnimation<Customer> waitForClimbQ;
	/** queue for customers waiting to be prepared for the jump */
	ProcessQueueAnimation<Customer> waitAtPlatformQ;
	/** queue for prepared jumpers waiting to start jump */
	ProcessQueueAnimation<Customer> waitForJumpQ;
	/** queue to store the customer currently jumping */
	ProcessQueueAnimation<Customer> jumpingQ;
	/** queue for jumpers waiting to buy their photos */
	ProcessQueueAnimation<Customer> waitForPhotoQ;
	/** queue for jumpers exit*/
	ProcessQueueAnimation<Customer> wsAusgang;

	/** queue for idle cashiers */
	ProcessQueueAnimation<Cashier> idleCashierQ;
	/** queue for idle attendants */
	ProcessQueueAnimation<Attendant> idleAttendantQ;
	/** queue for idle photo selling staff */
	ProcessQueueAnimation<PhotoSeller> idlePhotoSellerQ;

	//neu--------
	
	// Processstationen
	ProcessStationAnimation<Customer, Cashier> paying;
	ProcessStationAnimation<Customer, Attendant> prepareTojump;
	ProcessStationAnimation<Customer, PhotoSeller> sellPhoto;
	
	//TransportStation Customer pay
	TransportStationAnimation customerpaySource;
	TransportStationAnimation customerpaySink;
	//TransportStation Customer climb
	TransportStationAnimation customerclimbSource;
	TransportStationAnimation customerclimbSink;
	//TransportStation Customer prepare to jump
	TransportStationAnimation customerpreparejumpSource;
	TransportStationAnimation customerpreparejumpSink;
	//TransportStation Customer jumping
	TransportStationAnimation customerjumpingSource;
	TransportStationAnimation customerjumpingSink;
	//TransportStantion to Exit
	TransportStationAnimation customerexitSource;
	TransportStationAnimation customerexitSink;
	//TransportStation back to Cashier
	TransportStationAnimation customerbacktocashierSource;
	TransportStationAnimation customerbacktocashierSink;

	
	//TransportRoute Attendant
	TransportRouteAnimation<Customer> customerpayRoute;
	TransportRouteAnimation<Customer> customerclimbRoute;
	TransportRouteAnimation<Customer> customerpreparejumpRoute;
	TransportRouteAnimation<Customer> customerjumpingRoute;
	TransportRouteAnimation<Customer> customerexitRoute;
	TransportRouteAnimation<Customer> customerbacktocashierRoute;
	
	
	/** Rain Process */
	Rain rain;
	
	/** standard constructor */
   public ModelBungee(String modelName, CmdGeneration cmdGen,
		boolean showInReport, boolean showInTrace, boolean showInAnimation) {
			super (null, modelName, cmdGen, showInReport, showInTrace, showInAnimation);
			this.setModelProjectName("Desmo-J Demo");
			this.setModelProjectURL("http://www.wi-bw.tfh-wildau.de/~cmueller/SimulationAnimation/Demos/");
			this.setModelProjectIconId("DESMO-J");
			this.setModelAuthor("Johannes G&ouml;bel / Evgheni Steinbach (Animation)");
			this.setModelDate("23.7.2009");
			this.setModelDescription(this.description());
			this.setModelLicense("\u00A9 Johannes G&ouml;bel / Evgheni Steinbach 2009");
			this.addIcon("Client-active", "client1_active.gif");
			this.addIcon("Client-passive", "client1_idle.gif");
			this.addIcon("fotograf-active", "fotograf_active.gif");
			this.addIcon("fotograf-passive", "fotograf_idle.gif");
			this.addIcon("cashier-active", "cash_active.gif");
			this.addIcon("cashier-passiv", "cash_idle.gif");
			this.addIcon("attendant-active", "client2_active.gif");
			this.addIcon("attendant-passiv", "client2_idle.gif");
			this.addIcon("rain-active", "rain.gif");
			this.addIcon("rain-passiv", "sun.gif");
			this.addIcon("DESMO-J", "desmoJ.gif");
			this.addEntityTypeAnimation(Customer.entityType);
			this.addEntityTypeAnimation(Attendant.entityType);
			this.addEntityTypeAnimation(PhotoSeller.entityType);
			this.addEntityTypeAnimation(Cashier.entityType);
			this.addEntityTypeAnimation(Rain.entityType);
			this.setGeneratedBy(ModelBungee.class.getName());
   }
		
		
	public ModelBungee(ModelAnimation supermodell, String modelName, CmdGeneration cmdGen,
		boolean showInReport, boolean showInTrace, boolean showInAnimation) {
			super (supermodell, modelName, cmdGen, showInReport, showInTrace, showInAnimation);
			this.setModelAuthor("Johannes G&ouml;bel / Evgheni Steinbach (Animation)");
			this.setModelDate("erstellt: 23.7.2009");
			this.addRemark(this.description());
			this.addIcon("Client-active", "client1_active.gif");
			this.addIcon("Client-passive", "client1_idle.gif");
			this.addIcon("fotograf-active", "fotograf_active.gif");
			this.addIcon("fotograf-passive", "fotograf_idle.gif");
			this.addIcon("cashier-active", "cash_active.gif");
			this.addIcon("cashier-passiv", "cash_idle.gif");
			this.addIcon("attendant-active", "client2_active.gif");
			this.addIcon("attendant-passiv", "client2_idle.gif");
			this.addIcon("rain-active", "rain.gif");
			this.addIcon("rain-passiv", "sun.gif");
			this.addEntityTypeAnimation(Customer.entityType);
			this.addEntityTypeAnimation(Attendant.entityType);
			this.addEntityTypeAnimation(PhotoSeller.entityType);
			this.addEntityTypeAnimation(Cashier.entityType);
			this.addEntityTypeAnimation(Rain.entityType);
			this.setGeneratedBy(ModelBungee.class.getName());
   }
	
	/** initialize distributions, queues, servers and data collectors */
   public void initAnimation() {
	   
	   // initialize distributions
	   this.arrivalDist = new ContDistExponential(this, "arrivals", 10.0, this.reportIsOn(), this.traceIsOn());
	   this.groupDist = new DiscreteDistUniform(this, "group size", 1, 5, this.reportIsOn(), this.traceIsOn());
	   this.payingJumpDist = new ContDistNormal(this, "paying jump", 3, 1, this.reportIsOn(), this.traceIsOn()); 
	   this.payingJumpDist.setNonNegative(true); 
	   this.climbingDist = new ContDistNormal(this, "climbing", 10, 2.5, this.reportIsOn(), this.traceIsOn()); 
	   this.climbingDist.setNonNegative(true);
	   this.prepareDist = new ContDistNormal(this, "prepare", 6, 2, this.reportIsOn(), this.traceIsOn()); 
	   this.prepareDist.setNonNegative(true);
	   this.jumpDist = new ContDistExponential(this, "jump", 1, this.reportIsOn(), this.traceIsOn());
	   this.detachDist = new ContDistNormal(this, "detach", 1, 0.5, this.reportIsOn(), this.traceIsOn()); 
	   this.detachDist.setNonNegative(true);
	   this.wantsPhotoDist = new BoolDistBernoulli(this, "wants photo", 0.15, this.reportIsOn(), this.traceIsOn());
	   this.payingPhotoDist = new ContDistNormal(this, "paying photo", 7.0, 2.0, this.reportIsOn(), this.traceIsOn()); 
	   this.payingJumpDist.setNonNegative(true);
	   this.wantsAnotherJumpDist = new BoolDistBernoulli(this, "wants another jump", 0.25, this.reportIsOn(), this.traceIsOn());
	   this.rainDurationDist = new ContDistNormal(this, "raining duration", 12, 5, this.reportIsOn(), this.traceIsOn()); 
	   this.rainDurationDist.setNonNegative(true);
	   this.dryDurationDist = new ContDistExponential(this, "dry duration", 45, this.reportIsOn(), this.traceIsOn());
	   
	   
	   // initialize queues
	   this.waitAtCounterQ=   new ProcessQueueAnimation<Customer>(this, "waitAtCounterQ", this.reportIsOn(), this.traceIsOn());
	   this.waitAtCounterQ.createAnimation(new Position(200,400),
			   new FormExt(true, 3, Customer.entityType.getId()),
			   this.animationIsOn());

	   this.waitForClimbQ =   new ProcessQueueAnimation<Customer>(this, "waitForClimbQ (due to rain)", this.reportIsOn(), this.traceIsOn());
	   this.waitForClimbQ.createAnimation(new Position(560,400),
			   new FormExt(true, 3, Customer.entityType.getId()),
			   this.animationIsOn());
	   
	   this.waitAtPlatformQ = new ProcessQueueAnimation<Customer>(this, "waitAtPlatformQ", this.reportIsOn(), this.traceIsOn());
	   this.waitAtPlatformQ.createAnimation(new Position(300,200),
			   new FormExt(true, 3, Customer.entityType.getId()),
			   this.animationIsOn());

	   this.waitForJumpQ =    new ProcessQueueAnimation<Customer> (this, "waitForJump", this.reportIsOn(), this.traceIsOn());
	   this.waitForJumpQ.createAnimation(new Position(800,200),
			   new FormExt(true, 3, Customer.entityType.getId()),
			   this.animationIsOn());

	   this.jumpingQ = 	      new ProcessQueueAnimation<Customer>(this, "Gelandet!", this.reportIsOn(), this.traceIsOn());
	   this.jumpingQ.createAnimation(new Position(800,400),
			   new FormExt(true, 3, Customer.entityType.getId()),
			   this.animationIsOn());

	   this.waitForPhotoQ =   new ProcessQueueAnimation <Customer> (this, "waitForPhotoQ", this.reportIsOn(), this.traceIsOn());
	   this.waitForPhotoQ.createAnimation(new Position(800,500),
			   new FormExt(true, 3, Customer.entityType.getId()),
			   this.animationIsOn());

	   this.idleCashierQ =    new ProcessQueueAnimation<Cashier>(this, "idleCashierQ", this.reportIsOn(), this.traceIsOn());
	   this.idleCashierQ.createAnimation(new Position(200,500),
			   new FormExt(true, 3, Cashier.entityType.getId()),
			   this.animationIsOn());

	   this.idleAttendantQ =  new ProcessQueueAnimation<Attendant>(this, "idleAttendantQ", this.reportIsOn(), this.traceIsOn());
	   this.idleAttendantQ.createAnimation(new Position(200,600),
			   new FormExt(true, 3,  Attendant.entityType.getId()),
			   this.animationIsOn());

	   this.idlePhotoSellerQ =new ProcessQueueAnimation<PhotoSeller>(this, "idlePhotoSellerQ", this.reportIsOn(), this.traceIsOn());
	   this.idlePhotoSellerQ.createAnimation(new Position(200,700),
			   new FormExt(true, 3, PhotoSeller.entityType.getId()),
			   this.animationIsOn());

	   this.wsAusgang =       new ProcessQueueAnimation<Customer>(this, "Exit", this.reportIsOn(), this.traceIsOn());
	   this.wsAusgang.createAnimation(new Position(800,700),
			   new FormExt(false, 3, Customer.entityType.getId()),
			   this.animationIsOn());
	 
	  //initialize process
	   this.paying				= new ProcessStationAnimation<Customer, Cashier>(
			   this, "Paying", new Position(400,400),
			   new FormExt(true, NUM_CASHIERS, Cashier.entityType.getId()),
			   this.waitAtCounterQ, this.animationIsOn());
	   
	   this.prepareTojump		= new ProcessStationAnimation<Customer, Attendant>(
			   this, "PreparingToJump", new Position(600,200),
			   new FormExt(true, NUM_ATTENDANTS, Attendant.entityType.getId(), 70, 0),
			   this.waitAtPlatformQ, this.animationIsOn());
	   
	   this.sellPhoto		= new ProcessStationAnimation<Customer, PhotoSeller>(
			   this, "Selling Photo", new Position(950, 500),
			   new FormExt(true, NUM_PHOTOSELLERS, PhotoSeller.entityType.getId(), 20, 0),
			   this.waitForPhotoQ, this.animationIsOn());
	   
	// Transport-Stationen und Routen
		//Customer pay
		this.customerpaySource			= new TransportStationAnimation(this, "CustomerpaySource", 	new Position(200,400), this.animationIsOn());
		this.customerpaySink			= new TransportStationAnimation(this, "CustomerpaySink", new Position(400,400), this.animationIsOn());
		Position[] zp = new Position[2]; zp[0] = new Position(200,400); zp[1]= new Position(400, 400);
		this.customerpayRoute			= new TransportRouteAnimation<Customer>(this, "CustomerpayRoute", 1.0, this.customerpaySource, this.customerpaySink, zp, this.animationIsOn());
		
		//Customer  climbing
		this.customerclimbSource		= new TransportStationAnimation(this, "CustclimbSource", new Position(560,400), this.animationIsOn());
		this.customerclimbSink			= new TransportStationAnimation(this, "CustclimbSink", 	new Position(300,200), this.animationIsOn());
		Position[] zr = new Position[2]; zr[0] = new Position(560,400); zr[1]= new Position(300, 200);
		this.customerclimbRoute			= new TransportRouteAnimation<Customer>(this, "CustomerclimbRoute", 1.0, this.customerclimbSource, this.customerclimbSink, zr, this.animationIsOn());
		//Customer  prepare
		this.customerpreparejumpSource	= new TransportStationAnimation(this, "CustpreparejumpSource", new Position(300,200), this.animationIsOn());
		this.customerpreparejumpSink	= new TransportStationAnimation(this, "CustpreparejumpSink", new Position(600,200), this.animationIsOn());
		Position[] zf = new Position[2]; zf[0] = new Position(300,200); zf[1]= new Position(600, 200);
		this.customerpreparejumpRoute	= new TransportRouteAnimation<Customer>(this, "CustomerpreparejumpRoute", 1.0, this.customerpreparejumpSource, this.customerpreparejumpSink, zf, this.animationIsOn());
		//Customer  jumping
		this.customerjumpingSource		= new TransportStationAnimation(this, "CustomerjumpingSource", new Position(800,200), this.animationIsOn());
		this.customerjumpingSink		= new TransportStationAnimation(this, "CustomerjumpingSink", new Position(800,400), this.animationIsOn());
		Position[] zm = new Position[2]; zm[0] = new Position(800,200); zm[1]= new Position(800, 400);
		this.customerjumpingRoute 		= new TransportRouteAnimation<Customer>(this, "CustomerjumpingRoute", 1.0, this.customerjumpingSource, this.customerjumpingSink, zm, this.animationIsOn());
		//Customer Exit
		this.customerexitSource			= new TransportStationAnimation(this, "CustomerexitSource", new Position(800,400), this.animationIsOn());
		this.customerexitSink			= new TransportStationAnimation(this, "CustomerexitSink", new Position(800,700), this.animationIsOn());
		Position[] zll = new Position[2]; zll[0] = new Position(800,400); zll[1]= new Position(800, 400);
		this.customerexitRoute 			= new TransportRouteAnimation<Customer>(this, "CustomerexitRoute", 1.0, this.customerexitSource, this.customerexitSink, zll, this.animationIsOn());
		//Customer back to Cashier(another jump)
		this.customerbacktocashierSource= new TransportStationAnimation(this, "CustomerbacktocashierSource", new Position(800,700), this.animationIsOn());
		this.customerbacktocashierSink			= new TransportStationAnimation(this, "CustomerbacktocashierSink", new Position(200,400), this.animationIsOn());
		Position[] zlk = new Position[2]; zlk[0] = new Position(800,700); zlk[1]= new Position(800,700);
		this.customerbacktocashierRoute	= new TransportRouteAnimation<Customer>(this, "CustomerbacktocashierRoute", 1.0, this.customerbacktocashierSource, this.customerbacktocashierSink, zlk, this.animationIsOn());
		
		
	   // initialize data collectors
	   this.jumpDuration 		= new TallyAnimation(this, "jump duration", this.reportIsOn(), this.traceIsOn());
	   this.jumpDuration.createAnimation(new Position(950,700), new Form(), this.animationIsOn());
	   this.jumpsPerCustomer 	= new TallyAnimation(this, "jumps per customer", this.reportIsOn(), this.traceIsOn());
	   this.jumpsPerCustomer.createAnimation(new Position(350,700), new Form(), this.animationIsOn());
	   this.customersProcessed 	= new CountAnimation(this, "customers served", this.reportIsOn(), this.traceIsOn());
	   this.customersProcessed.createAnimation(new Position(500,700), new Form(), this.animationIsOn());
	   this.cashierUtil 		= new AccumulateAnimation(this, "cashier utilization", this.reportIsOn(), this.traceIsOn());
	   this.cashierUtil.createAnimation(new Position(80,500), new Form(), this.animationIsOn());
	   this.attendantUtil 		= new AccumulateAnimation(this, "attendant utilization", this.reportIsOn(), this.traceIsOn());
	   this.attendantUtil.createAnimation(new Position(80,600), new Form(), this.animationIsOn());
	   this.photosellerUtil 	= new AccumulateAnimation(this, "photoseller util.", this.reportIsOn(), this.traceIsOn());
	   this.photosellerUtil.createAnimation(new Position(80,700), new Form(), this.animationIsOn());
       
		/** create Rain Process */
		this.rain = new Rain(this, new Point(320,290));

   }
   

	/** schedule the initial events
	 */
   public void doInitialSchedules() {
	   
		// create cashiers 
		for (int i=0; i<this.NUM_CASHIERS; i++) {
			new Cashier(this).activate();
		}
		
		// create bungee attendants
		for (int i=0; i<this.NUM_ATTENDANTS; i++) {
			new Attendant(this).activate();
		}
		
		// create photo sellers
		for (int i=0; i<this.NUM_PHOTOSELLERS; i++) {
			new PhotoSeller(this).activate();
		}
	   
	    // create CustomerGenerator
		new CustomerGenerator(this).activate();
	    
	    // activate rain process
	    this.rain.activate();

   }
   
   public void updateCashierUtil() {
	   
	    this.cashierUtil.update(1 - this.idleCashierQ.length()/(double)this.NUM_CASHIERS);
	    
   }
   
   public void updateAttendantUtil() {
	   
	    this.attendantUtil.update(1 - this.idleAttendantQ.length()/(double)this.NUM_ATTENDANTS);
   }
   
   public void updatePhotoSellerUtil() {
	   
	    this.photosellerUtil.update(1 - this.idlePhotoSellerQ.length()/(double)this.NUM_PHOTOSELLERS);
   }
   

   
   /** returns a description of this model to be used in the report */
   public String description() {
	   
        return "A process-oriented model of bungee jumping including rain.";
   }

	/** runs the model for 12 simulated hours. */
   public static void main(String[] args) {
	    String modelName = ConfigTool2d.getModelName(ModelBungee.class.getName());
	    ConfigTool2d.checkIconPath(modelName);
	    String expName = "experiment";
	    ConfigTool2d.createExperimentDir(modelName, expName);
	   
		// Initialisierung der CmdGeneration fuer Animation (neu Hinzugefuegt)
		CmdGeneration cmdGen = new CmdGeneration(
		ConfigTool2d.buildCmdsPath(modelName, expName, Constants.FILE_EXTENSION_CMD), 
		ConfigTool2d.buildCmdsPath(modelName, expName, Constants.FILE_EXTENSION_LOG_0), 
		ConfigTool2d.buildIconPathURL(modelName));
	   
	   
	   
		// create model and experiment and connect both
		ModelBungee model = new ModelBungee(modelName, cmdGen, true, false, true);
		Experiment exp = new Experiment(modelName, ConfigTool2d.buildOutputPath(modelName, expName));

		TimeInstant simBegin, simEnd;
	    simBegin = new TimeInstant(new GregorianCalendar(2009, Calendar.JUNE, 1, 10, 00));
	    simEnd 	 = new TimeInstant(new GregorianCalendar(2009, Calendar.JUNE, 2, 10, 00));
		cmdGen.setStartStopTime(simBegin, simEnd, TimeZone.getDefault());

		model.connectToExperiment(exp);
		
		// set experiment parameters
		exp.setShowProgressBar(true);
		Experiment.setTimeFormatter(new PatternBasedTimeFormatter(true));
		System.out.println("Begin: "+simBegin+"    End: "+simEnd);

		// start the experiment
		cmdGen.experimentStart(exp, 5.0);
		
		// Beende CmdGeneration (neu Hinzugefuegt)
		cmdGen.close();
		
		// after experiment has terminated, draw report and finish everything off nicely
		exp.report();
		exp.finish();
		
		
		
		// Start des AnimationViewers
		ConfigTool2d.createViewer(cmdGen.getCmdFileURL(), cmdGen.getIconPathURL());
   }
   
   
   
   
}