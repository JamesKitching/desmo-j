package desmoj.demo.queue2D;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.exception.DelayedInterruptException;
import desmoj.core.exception.InterruptException;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.core.simulator.ModelAnimation;
import desmoj.extensions.visualization2d.animation.core.simulator.SimProcessAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.EntityTypeAnimation;
  

public class Kassierer extends SimProcessAnimation {

	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(Kassierer.class.getSimpleName());
		entityType.setGenereratedBy(Kassierer.class.getName());
		entityType.addPossibleState("active", "Kassierer-active");
		entityType.addPossibleState("passive", "Kassierer-passive");
	}

	/** model reference */
	private ModelKasse model;
	
	private int kasseIndex;
	
	
	/** constructor */
	public Kassierer(ModelAnimation owner, int kasseIndex) {
		super(owner, "Kassierer", owner.traceIsOn());
		super.createAnimation(entityType.getId(), owner.animationIsOn());
		model = (ModelKasse) owner;
		this.kasseIndex = kasseIndex;
	}// end constructor
	
	public int getKasseIndex(){
		return this.kasseIndex;
	}
	
	/** The lifeCycle() methods are one of the most import methods
	 * within DESMO-J-based simulations. This is where the real action happens. */ 
	public void lifeCycle() throws DelayedInterruptException, InterruptException, SuspendExecution {
		while (true) {
			
			if (model.queueKassen[kasseIndex].isEmpty()) {   
				model.queueIdleKassierer.insert(this);
				sendTraceNote("idle Kassierer inserts itself into queueIdleKassierer," +
					" queueIdleKassierer length: " + model.queueIdleKassierer.length());
				this.setState("passive");
				model.updateAuslastung();
				passivate();
			 }
			else { 
				// bestimme naechsten Kunden
				Kunde nextKunde = (Kunde) model.queueKassen[kasseIndex].first();
				model.queueKassen[kasseIndex].remove(nextKunde);
				
				// kassiere
				Vector<Kunde> kunde = new Vector<Kunde>(); kunde.add(nextKunde);
				Vector<Kassierer> kassierer = new Vector<Kassierer>(); kassierer.add(this);
				model.kassen[kasseIndex].insert(kunde, kassierer);
				hold(new TimeSpan(model.distKassiererServiceTime.sample(), TimeUnit.MINUTES));
				model.kassen[kasseIndex].remove(this);
				
				// aktiviere Kunde
				nextKunde.activate(new TimeSpan(0));
			}
				
		}//end while
	}//end lifeCycle() 
	 
}// end class