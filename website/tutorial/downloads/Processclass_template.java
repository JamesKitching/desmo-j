package <my.package.name>;

// import the DESMO-J stuff
import desmoj.*;
import co.paralleluniverse.fibers.SuspendExecution;

public class <MyComponentType> extends SimProcess {

	// define attributes here (optional)


	/** constructs a process... */
	public MyComponentType(MyModel model) {
		super (model, "<MyComponentTypeName>", true);

	}

	/** describes this process's life cycle */
	public void lifeCycle() throws SuspendExecution {
		// define behaviour here
	}

	// define additional methods here (optional)

} /* end of process class */