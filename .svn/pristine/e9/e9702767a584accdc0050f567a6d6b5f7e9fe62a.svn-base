package desmoj.demo.bungee2D;

import java.awt.Point;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.exception.DelayedInterruptException;
import desmoj.core.exception.InterruptException;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.PositionExt;
import desmoj.extensions.visualization2d.animation.core.simulator.SimProcessAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.EntityTypeAnimation;

public class Rain extends SimProcessAnimation{

	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(Rain.class.getSimpleName());
		entityType.setGenereratedBy(Rain.class.getName());
		entityType.addPossibleState("active", "rain-active");
		entityType.addPossibleState("passiv", "rain-passiv");
	}
	
	private boolean raining;

	public Rain(ModelBungee owner, Point point) {
		super(owner, "Rain", owner.traceIsOn());
		super.createAnimation(entityType.getId(), "passiv", 
				new PositionExt(point, 0.0, true), owner.animationIsOn());
		this.raining	= false;
	}
	
	public boolean isRaining(){
		return this.raining;
	}
	
    /** the rain's lifecycle 
     * @throws SuspendExecution */
    public void lifeCycle() throws SuspendExecution {
    	
    	ModelBungee model = (ModelBungee)this.getModel();
    	
    	// cycle indefinitely through dry and raining weather
        while (true) {
        	
    		// wait for next rain
    		TimeSpan rainStartTime = new TimeSpan(model.dryDurationDist.sample(), TimeUnit.MINUTES);
    		hold(rainStartTime);
    		
    		// update model
    		this.raining = true;
    		this.setState("active");
    		
    		// wait for end of rain
    		TimeSpan rainEndTime = new TimeSpan(model.rainDurationDist.sample(), TimeUnit.MINUTES);
    		hold(rainEndTime);
    		
    		// update model
    		this.raining = false;
    		this.setState("passiv");
    		
    		// resume plattform climbing
    		while (!model.waitForClimbQ.isEmpty()) {
    			
    			Customer customer = model.waitForClimbQ.first();
    			model.waitForClimbQ.remove(customer);
    			customer.activate(new TimeSpan(0));
    		}
    		
    		// resume jumping
    		if (!model.waitForJumpQ.isEmpty() && model.jumpingQ.isEmpty()) {
    			
    			Customer customer = model.waitForJumpQ.first();
    			model.waitForJumpQ.remove(customer);
    			customer.activate(new TimeSpan(0));
    			
    		}
        }
    }    
}