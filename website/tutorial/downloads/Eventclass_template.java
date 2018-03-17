package <my.package.name>;

// import the DESMO-J stuff
import desmoj.*;

public class <MyEventType> extends Event<MyEntity> {

	// define attributes here (optional)

	/** constructs an event... */
	public MyEventType(MyModel model) {
		super (model, "<MyEventTypeName>", true );

	}

	/** describes what happens during this event */
	public void eventRoutine(EntityType who) {
		// define entity/event behaviour here
	}

	// define additional methods here (optional)

} /* end of event class */
