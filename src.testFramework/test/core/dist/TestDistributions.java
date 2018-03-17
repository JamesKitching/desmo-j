package test.core.dist;

import org.junit.Before;
import org.junit.Test;

import desmoj.core.dist.ContDistAggregate;
import desmoj.core.dist.ContDistCustom;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistTriangular;
import desmoj.core.dist.Function;
import desmoj.core.dist.Operator;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
/**
 * Temporary Class for testing purposes, will be deleted later on
 * @author 8wueppen
 *
 */
public class TestDistributions {

	private ContDistTriangular dist1;
	private ContDistNormal dist2;
	private Operator productOp;
	private ContDistAggregate distCombined;
	private Function customFunction;
	private ContDistCustom customDist;
	
	

	@Before
	public void startup() {
		Experiment experiment = new Experiment("testcase");
		Model model = new Model(null, null, false, false) {

			@Override
			public void init() {
				// TODO Auto-generated method stub

			}

			@Override
			public void doInitialSchedules() {
				// TODO Auto-generated method stub

			}

			@Override
			public String description() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		model.connectToExperiment(experiment);
		
		dist1  = new ContDistTriangular(model, "TriDist", 2, 5, 4, true, false);
		dist2 = new ContDistNormal(model, "NormalDist", 3, 1, true, false);
		
		productOp = new Operator() {
			
			public double result(double operand1, double operand2) {
				
				return operand1 * operand2;
			}
			
			public String getDescription() {
				return "Product";
			}
		};
		
		distCombined = new ContDistAggregate(model, "AggregateDist", dist1, dist2, productOp, true, false);
		
	
		//distCombined.sample()...
		
		
		
		customFunction = new Function() {
			
			public double value(double x) {
				
				return x*x;
			}
			
			public String getDescription() {
				
				return "quadratic";
			}
		};
		customDist = new ContDistCustom(model, "bla", customFunction, 0, 1, true, false);
		
		
	}

	@Test
	public void normalUse() {
		
		
		
		for(int i = 0; i < 10; i++)
		{ 
		
		System.out.println(customDist.sample());
		}
	}
}
