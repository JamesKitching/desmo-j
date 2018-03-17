package <my.package.name>;

// import the DESMO-J stuff
import desmoj.core.dist.*;
import desmoj.core.simulator.*;
import desmoj.core.statistic.*;
import java.util.concurrent.TimeUnit;

public class <MyModel> extends Model {

	// define model components here


	/** constructs a model... */
	public MyModel() {
		super(null, "<MyModelName>", true, true);

	}

	/** initialise static components */
	public void init() {

	}

	/** activate dynamic components */
	public void doInitialSchedules() {

	}

	/** returns a description of this model to be used in the report */
	public String description() {
		return "<Description of my model>";
	}

	// define any additional methods if necessary,
	// e.g. access methods to model components

	/** runs the model */
	public static void main(String[] args) {

		// create model and experiment
		MyModel model = new MyModel();
		Experiment exp = new Experiment("<MyModelExperimentName>", null);
		// and connect them
		model.connectToExperiment(exp);

		// set experiment parameters
		exp.setShowProgressBar(true);
		TimeInstant stopTime = new TimeInstant(1440, TimeUnit.MINUTES);
		exp.tracePeriod(new TimeInstant(0), stopTime);
		exp.stop(stopTime);

		// start experiment
		exp.start();

		// generate report and shut everything off
		exp.report();
		exp.finish();
	}

} /* end of model class */
