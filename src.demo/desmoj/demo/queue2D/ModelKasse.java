package desmoj.demo.queue2D;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.DiscreteDistConstant;
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
import desmoj.extensions.visualization2d.animation.processStation.ProcessStationAnimation;
import desmoj.extensions.visualization2d.engine.Constants;
import desmoj.extensions.visualization2d.engine.command.Parameter;

/**
 * Jede Kasse hat eine eigene Warteschlange
 * @author Christian
 *
 */
public class ModelKasse extends ModelAnimation {

	protected int Anz_Kassierer = 3;
	protected double Mean_Kunde_InterArivalTime = 1.0;
	protected double Mean_Kassierer_ServiceTime = 3.0;

	// DISTRIBUTIONS:
	protected ContDistExponential distKundeInterArrivalTime;
	protected DiscreteDistConstant<Double> 	distKassiererServiceTime;	
	
	// STATISTICS:
	protected CountAnimation countKunde;
	protected AccumulateAnimation wartezeitKunde;
	protected AccumulateAnimation auslastungKassierer;
	
	// WAITING QUEUES:
	protected ProcessQueueAnimation<Kunde>[] queueKassen;
	protected ProcessQueueAnimation<Kassierer> queueIdleKassierer;
	
	// ProcessStation
	protected ProcessStationAnimation<Kunde, Kassierer>[] kassen;

	/** standard constructor */
	public ModelKasse(String modelName, CmdGeneration cmdGen, 
			boolean showInReport, boolean showInTrace, boolean showInAnimation) {
		super(null, modelName,	cmdGen, showInReport, showInTrace, showInAnimation);
		this.setModelProjectName("Desmo-J Tutorial");
		this.setModelProjectURL("http://www.wi-bw.tfh-wildau.de/~cmueller/SimulationAnimation/Documentation/Tutorial/");
		this.setModelProjectIconId("DESMO-J");
		this.setModelAuthor("Chr. Mueller");
		this.setModelDate("10.4.2010");
		this.setModelDescription(this.description());
		this.setModelLicense("\u00A9 Christian Mueller 2010");
		this.addRemark("Kassen mit einzelnen Warteschlangen");
		this.addIcon("Kassierer-active", "client1_active.gif");
		this.addIcon("Kassierer-passive", "client1_idle.gif");
		this.addIcon("Kunde-active", "client2_active.gif");
		this.addIcon("Kunde-passive", "client2_idle.gif");
		this.addIcon("DESMO-J", "desmoJ.gif");
		this.addEntityTypeAnimation(Kunde.entityType);
		this.addEntityTypeAnimation(Kassierer.entityType);
		this.setGeneratedBy(ModelKasse.class.getName());
	}// end constructor
	
	/** initializes model components, required by superclass */
	public void initAnimation() {

		// Distributions
		this.distKundeInterArrivalTime = new ContDistExponential(this, "Kunde InterArrivalTime", this.Mean_Kunde_InterArivalTime, this.reportIsOn(), this.traceIsOn());
		this.distKassiererServiceTime  = new DiscreteDistConstant<Double>(this, "Kassierer Service Time", this.Mean_Kassierer_ServiceTime, this.reportIsOn(), this.traceIsOn());
		
		// Statistics
		this.countKunde = new CountAnimation(this, "bediente Kunden", this.reportIsOn(), this.traceIsOn());
		this.countKunde.createAnimation(new Position(200,-100), new Form(), this.animationIsOn());
		this.wartezeitKunde = new AccumulateAnimation(this, "Wartezeit Kunden", this.reportIsOn(), this.traceIsOn());
		this.wartezeitKunde.createAnimation(new Position(300,-100), new Form(), this.animationIsOn());
		this.auslastungKassierer = new AccumulateAnimation(this, "Auslastung Kassierer", this.reportIsOn(), this.traceIsOn());
		this.auslastungKassierer.createAnimation(new Position(400,-100), new Form(), this.animationIsOn());
		
		// IdleQueue
		this.queueIdleKassierer = new ProcessQueueAnimation<Kassierer>(this, "Idle Kassierer", this.reportIsOn(), this.traceIsOn());
		this.queueIdleKassierer.createAnimation(new Position(000,-100),
				new FormExt(true, 3, Kassierer.entityType.getId()),	this.animationIsOn());
		
		// Kasse mit Warteschlange
		this.queueKassen = new ProcessQueueAnimation[this.Anz_Kassierer];
		this.kassen = new ProcessStationAnimation[this.Anz_Kassierer];
		for(int i=0; i<this.Anz_Kassierer; i++){
			this.queueKassen[i] = new ProcessQueueAnimation<Kunde>(this, "Kasse Queue "+i, this.reportIsOn(), this.traceIsOn());
			this.queueKassen[i].createAnimation(new Position(000,150 * i), 
					new FormExt(true, 3, Kunde.entityType.getId()), this.animationIsOn());

			this.kassen[i] = new ProcessStationAnimation<Kunde, Kassierer>(this, "Kasse "+i,
					new Position(200,150 * i), new FormExt(true, 1, Kunde.entityType.getId()),
					"Kasse Queue "+i, this.animationIsOn());
		}
	}// end init()


	/** schedules the first events and processes onto the framework's internal event list */
	public void doInitialSchedules() {
		Kassierer kassierer = null;
		for (int i = 0; i < this.Anz_Kassierer; i++) {
			kassierer = new Kassierer(this, i);
			kassierer.setState("passive");
			this.queueIdleKassierer.insert(kassierer);
		}// end for i
		KundeGenerator kundeGenerator = new KundeGenerator(this);
		kundeGenerator.activate();
	}// end doInitialSchedules()


	/** returns a description of this model, required by superclass */
	public String description() {
		String out = "Description of tutorial_00";
		return Parameter.replaceSyntaxSign(out);
	}// end description()
	
	public void updateAuslastung(){
		this.auslastungKassierer.update(1.0-((double)this.queueIdleKassierer.length()/(double)this.Anz_Kassierer));
	}


	/** runs the experiment */
	public static void main(String[] args) {
		String modelName = ConfigTool2d.getModelName(ModelKasse.class.getName());
		ConfigTool2d.checkIconPath(modelName);
		String expName = "experiment";
		ConfigTool2d.createExperimentDir(modelName, expName);

	   // Initialisierung der CmdGeneration fuer Animation (neu Hinzugefuegt)
		CmdGeneration cmdGen = new CmdGeneration(
				ConfigTool2d.buildCmdsPath(modelName, expName, Constants.FILE_EXTENSION_CMD), 
				ConfigTool2d.buildCmdsPath(modelName, expName, Constants.FILE_EXTENSION_LOG_0),
				ConfigTool2d.buildIconPathURL(modelName));
		
		ModelKasse model = new ModelKasse(modelName, cmdGen, true, true, true);
		Experiment experiment = new Experiment(modelName, ConfigTool2d.buildOutputPath(modelName, expName));

		TimeInstant simBegin, simEnd;
	    simBegin = new TimeInstant(new GregorianCalendar(2009, Calendar.JUNE, 1, 10, 00));
	    simEnd 	 = new TimeInstant(new GregorianCalendar(2009, Calendar.JUNE, 1, 11, 10));
		cmdGen.setStartStopTime(simBegin, simEnd, TimeZone.getDefault());
		
		model.connectToExperiment(experiment);
		experiment.setSeedGenerator(6789);
		experiment.setShowProgressBar(true);
		Experiment.setTimeFormatter(new PatternBasedTimeFormatter(true));
		System.out.println("Begin: "+simBegin+"    End: "+simEnd);


		// start the experiment
		cmdGen.experimentStart(experiment, 5.0);
		
		// experiment fertig
		model.updateAuslastung();
		cmdGen.close();
		experiment.report();
		experiment.finish();
		
		// Start des AnimationViewers
		ConfigTool2d.createViewer(cmdGen.getCmdFileURL(), cmdGen.getIconPathURL());
	}// end main()
}// end class