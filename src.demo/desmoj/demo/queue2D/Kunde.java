package desmoj.demo.queue2D;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeOperations;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.core.simulator.ModelAnimation;
import desmoj.extensions.visualization2d.animation.core.simulator.SimProcessAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.EntityTypeAnimation;
  

public class Kunde extends SimProcessAnimation {

	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(Kunde.class.getSimpleName());
		entityType.setGenereratedBy(Kunde.class.getName());
		entityType.addPossibleState("active", "Kunde-active");
		entityType.addPossibleState("passive", "Kunde-passive");
	}

	/** model reference */
	private ModelKasse model;
	
	private int kasseIndex;
	private TimeInstant angestellt;
	private TimeSpan wartezeit;
	
	private int min;  // Laenge der kuerzesten Schlange
	private int minIndex; // Index der kuerzesten Schlange
	
	
	/** constructor */
	public Kunde(ModelAnimation owner) {
		super(owner, "Kunde", owner.traceIsOn());
		super.createAnimation(entityType.getId(), owner.animationIsOn());
		model = (ModelKasse) owner;
	}// end constructor
	
	private void bestKuerzesteSchlange(){
		min = Integer.MAX_VALUE;
		minIndex = -1;
		for(int i=0; i<model.Anz_Kassierer; i++){
			int l = model.queueKassen[i].length();
			if(l < min){
				min = l;
				minIndex = i;
			}
			//System.out.println("i: "+i+"  l: "+l+"  min: "+min+"  minIndex: "+minIndex);
		}
	}
	
	/** The lifeCycle() methods are one of the most import methods
	 * within DESMO-J-based simulations. This is where the real action happens. 
	 * @throws SuspendExecution */
	public void lifeCycle() throws SuspendExecution {
		if(! model.queueIdleKassierer.isEmpty()){
			// ein Kassierer ist frei; neue Kasse mit diesem Kassier aufmachen
			Kassierer kassierer = model.queueIdleKassierer.first();
			model.queueIdleKassierer.remove(kassierer);
			kasseIndex = kassierer.getKasseIndex();
			model.queueKassen[kasseIndex].insert(this);
			angestellt = model.presentTime();
			model.updateAuslastung();
			kassierer.activate(new TimeSpan(0));
			kassierer.setState("active");
		}else{
			// kein Kassierer ist frei
			// bestimme kuerzeste Schlange
			this.bestKuerzesteSchlange();
			// an kuerzester Schlange anstellen
			if(minIndex > -1){
				kasseIndex = minIndex;
				model.queueKassen[kasseIndex].insert(this);
				angestellt = model.presentTime();
			}else{
				System.out.println("Fehler in Kunde: Es gibt keine kuerzeste Schlange");
			}
		}
		// Kunde paqssiv setzen
		this.setState("passive");
		this.passivate();
		
		// Kunde hat Kasse passiert
		this.setState("active");
		model.countKunde.update();
		wartezeit = TimeOperations.diff(model.presentTime(), angestellt);
		model.wartezeitKunde.update(wartezeit.getTimeAsDouble());
		this.disposeAnimation();
	}//end lifeCycle() 
	 
}// end class