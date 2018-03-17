package desmoj.extensions.db.visustorage.sim;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.Units;
import desmoj.extensions.space3D.LayoutLoader;
import desmoj.extensions.space3D.ExtendedLength;
import desmoj.extensions.space3D.SpatialObject;
import desmoj.extensions.space3D.SpatialProcessQueue;
import desmoj.extensions.space3D.Track;

/**
 * A simple LayoutLoader.
 * </br></br>
 * It loads queues for trucks and excavators.
 * 
 * @author Fred Sun
 *
 */
public class SimpleLayoutLoader extends LayoutLoader {

	public SimpleLayoutLoader(Model model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see desmoj.extensions.space3D.LayoutLoader#createSpatialObject(org.w3c.dom.Element)
	 */
	@Override
	protected SpatialObject createSpatialObject(Element spatialObject) {
		//get the name and the type of the SpatialObject
		String type = spatialObject.getAttribute("Type");
			//for creating SpatialQueues
		if(type.equals("TruckQueue")){
			//get show in trace/report values
			boolean showInReport = false;
			boolean showInTrace = false;
			if(spatialObject.hasAttribute("showInReport")&&
					spatialObject.getAttribute("showInReport").equals("true")){
				showInReport = true;
			}
			if(spatialObject.hasAttribute("showInTrace")&&
					spatialObject.getAttribute("showInTrace").equals("true")){
				showInTrace = true;
			}
			
			//get position
			double x = 0;
			double y = 0;
			double z = 0;
			NodeList position = spatialObject.getElementsByTagName("Position");
			for(int i=0;i<position.getLength();i++){
				Element positionElement = (Element)position.item(i);
				x = new Double(positionElement.getAttribute("x"));
				y = new Double(positionElement.getAttribute("y"));
				z = new Double(positionElement.getAttribute("z"));
			}
			
			//create the queue
			SpatialProcessQueue<Truck> queue = new SpatialProcessQueue<Truck>(this._model,spatialObject.getAttribute("Name"),
						type,showInReport,showInTrace,new ExtendedLength(x,Units.M),
						new ExtendedLength(y,Units.M),new ExtendedLength(z,Units.M));
			
			//get the entry points
			NodeList entryPoints = spatialObject.getElementsByTagName("EntryPoint");
			for(int i=0;i<entryPoints.getLength();i++){
				Element entryPoint = (Element)entryPoints.item(i);
				queue.addEntryPoint(entryPoint.getAttribute("name"),
						new ExtendedLength(new Double(entryPoint.getAttribute("x")),Units.M),
						new ExtendedLength(new Double(entryPoint.getAttribute("y")),Units.M),
						new ExtendedLength(new Double(entryPoint.getAttribute("z")),Units.M));
			}
			
			//get the exit points
			NodeList exitPoints = spatialObject.getElementsByTagName("ExitPoint");
			for(int i=0;i<exitPoints.getLength();i++){
				Element exitPoint = (Element)exitPoints.item(i);
				queue.addExitPoint(exitPoint.getAttribute("name"),
						new ExtendedLength(new Double(exitPoint.getAttribute("x")),Units.M),
						new ExtendedLength(new Double(exitPoint.getAttribute("y")),Units.M),
						new ExtendedLength(new Double(exitPoint.getAttribute("z")),Units.M));
			}

			//set the orientation
			NodeList rotations = spatialObject.getElementsByTagName("Rotation");
			for(int k=0;k<rotations.getLength();k++){
				Element rot = (Element)rotations.item(k);
				if(rot.hasAttribute("x")){
					queue.rotX(new Double(rot.getAttribute("x")));
				}else if(rot.hasAttribute("y")){
					queue.rotY(new Double(rot.getAttribute("y")));
				}else if(rot.hasAttribute("z")){
					queue.rotZ(new Double(rot.getAttribute("z")));
				}
			}
			return queue;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see desmoj.extensions.space3D.LayoutLoader#createTrack(org.w3c.dom.Element)
	 */
	@Override
	protected Track createTrack(Element track) {
		return null;
	}

}
