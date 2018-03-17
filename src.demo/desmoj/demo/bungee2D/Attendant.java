package desmoj.demo.bungee2D;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.exception.DelayedInterruptException;
import desmoj.core.exception.InterruptException;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.core.simulator.SimProcessAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.EntityTypeAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.EntryAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.SimulationException;

public class Attendant extends  SimProcessAnimation implements Kunde {
	
	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(Attendant.class.getSimpleName());
		entityType.setGenereratedBy(Attendant.class.getName());
		entityType.addPossibleState("active", "attendant-active");
		entityType.addPossibleState("passiv", "attendant-passiv");
	}

	public Attendant(ModelBungee owner) {
		super(owner, "Attendant", owner.traceIsOn());
		super.createAnimation(entityType.getId(), owner.animationIsOn());
		
	}
    /** the cashier's lifecycle 
     * @throws SuspendExecution 
     * @throws InterruptException 
     * @throws DelayedInterruptException */
    public void lifeCycle() throws DelayedInterruptException, InterruptException, SuspendExecution {
    	
    	ModelBungee model = (ModelBungee)this.getModel();
    	
    	// cycle indefinitely through serving and waiting for customers
        while (true) {
        	
    		// If the platform queue is empty, the attendant becomes idle
    		if (model.waitAtPlatformQ.isEmpty()) {
    			
    			model.idleAttendantQ.insert(this);
    			model.updateAttendantUtil();
    			this.setState("passiv");
    			passivate();
    			this.setState("active");
    			
    			model.idleAttendantQ.remove(this);
    			
    			model.updateAttendantUtil();
    		
    		// otherwise the attendant services the next customer, if available
    	 	} else {
    	 		
    	 		if (!model.waitAtPlatformQ.isEmpty()) {
    			
	    			// Get the 1st customer from the platform queue
	    			Customer nextCustomer = model.waitAtPlatformQ.first();
	
	    			// Remove customer from queue
	    			model.waitAtPlatformQ.remove(nextCustomer);
	    			

					//neu auf die Rute stellen
					TimeSpan ruteduration = new TimeSpan(0.3, TimeUnit.MINUTES);
					
					try {
						
						model.customerpreparejumpRoute.insert(nextCustomer, ruteduration);
					} catch (SimulationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					hold(ruteduration);

					model.customerpreparejumpRoute.remove(nextCustomer);
					
					//end neu
	    			Vector<Customer> nextCustomerV = new Vector<Customer>(); 
	    			nextCustomerV.add(nextCustomer);
	    			Vector<Attendant> selbstV = new Vector<Attendant>(); 
	    			selbstV.add(this);
	    			model.prepareTojump.insert(nextCustomerV, selbstV);
	 
	    			
	    			// determine next preparing duration
	    			TimeSpan preparingDuration = new TimeSpan(model.prepareDist.sample(), TimeUnit.MINUTES);
	    			
	    			// paying
	    			hold(preparingDuration);
	    			
	    			//neu-----
	    			model.prepareTojump.remove(this);
	    			
	    			// activate customer
	    			nextCustomer.activate(new TimeSpan(0));
    			
    	 		}
    		}
        }
    }    
}