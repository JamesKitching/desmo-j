package desmoj.demo.bungee2D;

import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;


public class CustomerGenerator extends SimProcess {
	
	
   /** constructor method */
   public CustomerGenerator(ModelBungee owner) {

		super(owner, "Customer Generator", owner.traceIsOn());
		
		

   }	// end of constructor method

   /** lifecycle method 
 * @throws SuspendExecution */
   public void lifeCycle() throws SuspendExecution {

      // get model reference
	  ModelBungee model = (ModelBungee)this.getModel();
		
      while (true) {
    	  
	  		// create a customer group
	  		long numberOfCustomers = (Long)model.groupDist.sample();
	  		for (int i=0; i<numberOfCustomers; i++) {
	  			
	  			// create each single customer
	  			new Customer(model).activate(new TimeSpan(0));
	  		}
	  		
			// wait for the next ship to enter
			hold(new TimeSpan(model.arrivalDist.sample(), TimeUnit.MINUTES));

      }  // end of loop
      
   }  // end of lifecycle
   
}  // end of class CustomerGenerator
