package desmoj.demo.bungee2D;

import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.exception.DelayedInterruptException;
import desmoj.core.exception.InterruptException;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
import desmoj.extensions.visualization2d.animation.core.simulator.SimProcessAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.EntityTypeAnimation;
import desmoj.extensions.visualization2d.animation.internalTools.SimulationException;

public class Customer extends SimProcessAnimation {
	
	static EntityTypeAnimation entityType;

	static{
		entityType = new EntityTypeAnimation();
		entityType.setId(Customer.class.getSimpleName());
		entityType.setGenereratedBy(Customer.class.getName());
		entityType.addPossibleState("active", "Client-active");
		entityType.addPossibleState("passiv", "Client-passive");
	}

	public Customer(ModelBungee owner) {
		super(owner, "Customer", owner.traceIsOn());
		super.createAnimation(entityType.getId(), owner.animationIsOn());
		
	}
	/** the customer's lifecycle 
	 * @throws SuspendExecution 
	 * @throws InterruptException 
	 * @throws DelayedInterruptException */
    public void lifeCycle() throws DelayedInterruptException, InterruptException, SuspendExecution {
    	
       	ModelBungee model = (ModelBungee)this.getModel();
		int numberJumpsConducted = 0;
		
		//another jump -->  delete customer from Exit(list), activate, put to route "customerbacktocashierRoute"
		//neu 
		do {
			if(model.wsAusgang.last()==this)
			{
				this.setState("active");
				model.wsAusgang.remove(this);
				
				TimeSpan ruteduration = new TimeSpan(0.2, TimeUnit.MINUTES);
				
				try {
					model.customerbacktocashierRoute.insert(this, ruteduration);
				} catch (SimulationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.hold(ruteduration);

				model.customerbacktocashierRoute.remove(this);
				
				//end neu
			
				
				
			}
			
			// Jump (again)
			TimeInstant currentJumpStartTime = this.presentTime();
			
			
			
			// Pay
			model.waitAtCounterQ.insert(this);
			if (!model.idleCashierQ.isEmpty()) {
			
				model.idleCashierQ.first().activate(new TimeSpan(0));
				model.idleCashierQ.remove(model.idleCashierQ.first());
			}
			this.passivate();
			
			// Wait for end of rain, if necessary
			if (model.rain.isRaining()) {
				model.waitForClimbQ.insert(this);
				this.passivate();
			}
			
			// Climb
			
			//neu auf die Rute stellen
			TimeSpan climbingDuration = new TimeSpan(model.climbingDist.sample(), TimeUnit.MINUTES);
			
			try {
				
				model.customerclimbRoute.insert(this, climbingDuration);
			} catch (SimulationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hold(climbingDuration);

			model.customerclimbRoute.remove(this);
			
			//end neu
			
			this.hold(climbingDuration);
			
			
			
			
			
			// Get prepared for jump
			
			
			
			model.waitAtPlatformQ.insert(this);
			if (!model.idleAttendantQ.isEmpty()) {
				model.idleAttendantQ.first().activate(new TimeSpan(0.0));
				model.idleAttendantQ.remove(model.idleAttendantQ.first());
			}
			this.passivate();
			
			// Wait for end of rain and for free jumping area, if necessary
			model.waitForJumpQ.insert(this);
			if (model.rain.isRaining() || !model.jumpingQ.isEmpty())
				this.passivate();
			model.waitForJumpQ.remove(this);
			
			// Jump (and detach)!
			
			
			
			
			TimeSpan jumpDuration = new TimeSpan(model.jumpDist.sample(), TimeUnit.MINUTES);
			
			//neu auf die Rute stellen
			try {
				
				model.customerjumpingRoute.insert(this, jumpDuration);
			} catch (SimulationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.hold(jumpDuration);

			model.customerjumpingRoute.remove(this);
			
			//end neu
			
			
			model.jumpingQ.insert(this);
			
			
			TimeSpan detachDuration = new TimeSpan(model.detachDist.sample(), TimeUnit.MINUTES);
			
			this.hold(detachDuration);
			model.jumpingQ.remove(this);
			
			
			
			
			
			
			
			// Inform next jumper, if weather permits
			if (!model.rain.isRaining() == false && !model.waitForJumpQ.isEmpty())
				model.waitForJumpQ.first().activate(new TimeSpan(0));
				
			// Buy a photo?
			if (model.wantsPhotoDist.sample() == true) {
				
				model.waitForPhotoQ.insert(this);
				
				if (!model.idlePhotoSellerQ.isEmpty()) {
					model.idlePhotoSellerQ.first().activate(new TimeSpan(0));
					model.idlePhotoSellerQ.remove(model.idlePhotoSellerQ.first());
				}
				
				
				this.passivate();
			
			}
			
			//neu auf die Rute stellen
			try {
				this.setState("passiv");
				model.customerexitRoute.insert(this, jumpDuration);
			} catch (SimulationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.hold(jumpDuration);

			model.customerexitRoute.remove(this);
			
			//end neu
			model.wsAusgang.insert(this);
			numberJumpsConducted++;
			
			// determine overall jump duration
			double start = currentJumpStartTime.getTimeAsDouble(TimeUnit.MINUTES);
			model.jumpDuration.update(this.presentTime().getTimeAsDouble(TimeUnit.MINUTES) - start);
		
		} while (model.wantsAnotherJumpDist.sample() == true);
		
		
		// ...update customer and jumps per customer statistic
		model.customersProcessed.update();
		model.jumpsPerCustomer.update(numberJumpsConducted);

    }

}