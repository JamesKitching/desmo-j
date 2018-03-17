package desmoj.demo.queue2D;

import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.exception.DelayedInterruptException;
import desmoj.core.exception.InterruptException;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.core.simulator.ModelAnimation;
import desmoj.extensions.visualization2d.animation.core.simulator.SimProcessAnimation;
 

public class KundeGenerator extends SimProcessAnimation {
	
	/** model reference */
	private ModelKasse 	model;
	
	
	/** constructor */
	public KundeGenerator(ModelAnimation owner) {
		super(owner, "truck generator", true);
		this.model 	= (ModelKasse) owner;
	}// end constructor
	
	
	public void lifeCycle() throws DelayedInterruptException, InterruptException, SuspendExecution {
		while(true){
			Kunde newKunde = new Kunde(model);
			newKunde.activate(new TimeSpan(0));
			hold(new TimeSpan(model.distKundeInterArrivalTime.sample(), TimeUnit.MINUTES));
		}
	}// end lifeCycle
	   
}// class