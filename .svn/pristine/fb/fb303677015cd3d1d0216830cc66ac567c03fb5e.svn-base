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

public class PhotoSeller extends  SimProcessAnimation {
	
	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(PhotoSeller.class.getSimpleName());
		entityType.setGenereratedBy(PhotoSeller.class.getName());
		entityType.addPossibleState("active", "fotograf-active");
		entityType.addPossibleState("passiv", "fotograf-passive");
	}

	public PhotoSeller(ModelBungee owner) {
		super(owner, "PhotoSeller", owner.traceIsOn());
		super.createAnimation(entityType.getId(), owner.animationIsOn());
		
	}
    /** the photo seller's lifecycle 
     * @throws SuspendExecution */
    public void lifeCycle() throws SuspendExecution {
    	
    	ModelBungee model = (ModelBungee)this.getModel();
    	
    	// cycle indefinitely through serving and waiting for customers
        while (true) {
        	
    		// If the counter queue is empty, the photo seller becomes idle
    		if (model.waitForPhotoQ.isEmpty()) {
    			
    			model.idlePhotoSellerQ.insert(this);
    			model.updatePhotoSellerUtil();
    			passivate();
    			model.idlePhotoSellerQ.remove(this);
    			
    			model.updatePhotoSellerUtil();
    		
    		// otherwise the photo seller services the next customer, if available
    	 	} else {
    	 		
    	 		if (!model.waitForPhotoQ.isEmpty()) {
    			
	    			// Get the 1st customer from the counter queue
	    			Customer nextCustomer = model.waitForPhotoQ.first();
	
	    			// Remove customer from queue
	    			model.waitForPhotoQ.remove(nextCustomer);
	    			
	    			//neu
	    			Vector<Customer> nextCustomerV = new Vector<Customer>(); 
	    			nextCustomerV.add(nextCustomer);
	    			Vector<PhotoSeller> selbstV = new Vector<PhotoSeller>(); 
	    			selbstV.add(this);
	    			model.sellPhoto.insert(nextCustomerV, selbstV);
	    			//end neu
	
	    			// determine next paying duration
	    			TimeSpan sellingDuration = new TimeSpan(model.payingPhotoDist.sample(), TimeUnit.MINUTES);
	    			
	    			// paying
	    			hold(sellingDuration);
	    			
	    			//neu
	    			EntryAnimation<Customer, PhotoSeller>	e = 
	    			model.sellPhoto.remove(this);
	    			
	    			//end neu
	    			
	    		
	    			
	    			// activate customer
	    			nextCustomer.activate(new TimeSpan(0));
    			
    	 		}
    		}
        }
    }    
}