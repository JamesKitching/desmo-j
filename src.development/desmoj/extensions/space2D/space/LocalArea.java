package desmoj.extensions.space2D.space;

//import desmoj.extensions.space2D.agent.Agent;
import desmoj.core.simulator.Entity;
//import desmoj.extensions.space2D.agent.Group;
//import desmoj.extensions.space2D.agent.SituatedAgent;

/**
 * A local area is an attribute area representing the observable area of an
 * agent. Local areas have only one special attribute (name = "local", value =
 * agent) and they are always spatial groups named after the appropriate agent.
 * They are maintained by the environment and are not intended for use outside
 * this framework.
 */
class LocalArea extends AttributeArea {

	/**
	 * the name of the special "locality" attribute. Also used as a prefix in
	 * the name of the associated spatial group.
	 */
	private static final String LOCAL = "local";

	/**
	 * Constructor for use by the environment. Constructs a new local area for
	 * the given agent and the specified positions. This includes creating a
	 * spatial group.
	 */
	LocalArea(Entity entity, Position[] positions) {
		super(
				new AttributeList(
						new Attribute[] { new Attribute(LOCAL, entity) }),
				positions);
//		this.spatialGroup = new Group(LOCAL + "-" + entity.getName());
		if (positions != null && positions.length > 0) {
			Space space = positions[0].getSpace();
			for (int i = 0; i < positions.length; i++) {
//				Agent[] agents = space.getAgents(positions[i]);
				Object[] objects = space.getObjects(positions[i]);
//				if (objects != null) {
//					for (int j = 0; j < objects.length; j++) {
//						this.spatialGroup.join(objects[j]);
//					}
//				}
			}
		}
	}

	/**
	 * returns a string representation of this local area.
	 * 
	 * @return a String consisting of the words "LocalArea for " concatenated to
	 *         the string representation of the agent.
	 */
	public String toString() {
		return new String("LocalArea for "
				+ this.attributes.getAttributeValue(LOCAL));
	}
}