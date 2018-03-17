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

public class Cashier extends  SimProcessAnimation {
	
	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(Cashier.class.getSimpleName());
		entityType.setGenereratedBy(Cashier.class.getName());
		entityType.addPossibleState("active", "cashier-active");
		entityType.addPossibleState("passiv", "cashier-passiv");
	}

	public Cashier(ModelBungee owner) {
		super(owner, "Cashier", owner.traceIsOn());
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
        	
    		// If the counter queue is empty, the cashier becomes idle
    		if (model.waitAtCounterQ.isEmpty()) {
    			
    			model.idleCashierQ.insert(this);
    			model.updateCashierUtil();
    			this.setState("passiv");
    			passivate();
    			this.setState("active");
    			
    			model.idleCashierQ.remove(this);
    			
    			model.updateCashierUtil();
    		
    		// otherwise the cashier services the next customer, if available
    	 	} else {
    	 		
    	 		if (!model.waitAtCounterQ.isEmpty()) {
    			
	    			// Get the 1st customer from the counter queue
	    			Customer nextCustomer = model.waitAtCounterQ.first();
	
	    			// Remove customer from queue
	    			model.waitAtCounterQ.remove(nextCustomer);
	    			
	    			
	    			//neu auf die Rute stellen
	    			TimeSpan ruteduration = new TimeSpan(1.0, TimeUnit.MINUTES);
					
					try {
						
						model.customerpayRoute.insert(nextCustomer, ruteduration );
					} catch (SimulationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					hold(ruteduration);

					model.customerpayRoute.remove(nextCustomer);
					
					//end neu
	    			
	    			
	    			
	    			
	    			//neu-----
	    			Vector<Customer> nextCustomerV = new Vector<Customer>(); 
	    			nextCustomerV.add(nextCustomer);
	    			Vector<Cashier> selbstV = new Vector<Cashier>(); 
	    			selbstV.add(this);
	    			model.paying.insert(nextCustomerV, selbstV);
	    			//end neu
	    			
	    			// determine next paying duration
	    			TimeSpan payingDuration = new TimeSpan(model.payingJumpDist.sample(), TimeUnit.MINUTES);
	    			
	    			// paying
	    			hold(payingDuration);
	    			
	    			//neu-----
	    			EntryAnimation<Customer, Cashier>	e = 
	    			model.paying.remove(this);
	    			
	    			//neu
	    		
	    		
	    			//end neu
	    			
	    			
	    			// activate customer
	    			nextCustomer.activate(new TimeSpan(0));
	    			
	    			
	    			
	    			
	    			
	    			
	    			
    			
    	 		}
    		}
        }
    }    
}