package desmoj.extensions.visualization2d.animation.transport;

import java.awt.Point;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.ModelComponent;
import desmoj.core.simulator.TimeInstant;
import desmoj.extensions.visualization2d.animation.CmdGeneration;
import desmoj.extensions.visualization2d.animation.Position;
import desmoj.extensions.visualization2d.animation.core.simulator.ModelAnimation;
import desmoj.extensions.visualization2d.engine.command.Command;
import desmoj.extensions.visualization2d.engine.command.CommandException;

/**
 * Creates a station for Animation. A station is source and sink of a route.
 *  
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author christian.mueller@th-wildau.de
 *         For information about subproject: desmoj.extensions.visualization2d
 *         please have a look at: 
 *         http://www.th-wildau.de/cmueller/Desmo-J/Visualization2d/ 
 * 
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *
 */
public class TransportStationAnimation extends ModelComponent{

	private Model			model	= null;
	private String			name	= null;
	private CmdGeneration  	cmdGen 	= null;
	private boolean showInAnimation	= true;
	private Position		position= null;
	private String 			id;
	
	/**
	 * creates a Station for animation
	 * @param owner
	 * @param name				Name and ID of Station
	 * @param pos				position
	 * @param showInAnimation
	 */
	public TransportStationAnimation(ModelAnimation owner, String name, Position pos, boolean showInAnimation){
		super (owner, name);
		this.model				= owner;
		this.name				= name;
		this.cmdGen				= owner.getCmdGen();
		this.showInAnimation	= showInAnimation;
		this.id					= this.cmdGen.createInternId(name);
		TimeInstant	simTime 	= this.model.presentTime();
		boolean	init			= this.cmdGen.isInitPhase();
		this.position			= pos;
		Command c;
		Point p					= pos.getPoint();

		if(this.showInAnimation){
			try {
				if(init)	c = Command.getCommandInit("createStation", this.cmdGen.getAnimationTime(simTime));
				else 		c = Command.getCommandTime("createStation", this.cmdGen.getAnimationTime(simTime));
				c.addParameter("StationId", this.id);
				c.addParameter("Name", this.name);
				c.addParameter("Point", pos.getView()+"|"+p.x+"|"+p.y);
				c.setRemark(this.getGeneratedBy(TransportStationAnimation.class.getSimpleName()));
				cmdGen.checkAndLog(c);
				cmdGen.write(c);
			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private String getGeneratedBy(String name){
		String out = "generated by "+name+" and called by ";
		if(this.currentSimProcess() != null)
			out += this.currentSimProcess().getName();
		else
			out += this.currentModel().getName();
		return out;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	public String getInternId(){
		return this.id;
	}

}
