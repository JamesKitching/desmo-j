package desmoj.extensions.db.visustorage;


import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.ExperimentDAO;
import desmoj.extensions.db.visustorage.dao.AttachDAO;
import desmoj.extensions.db.visustorage.dao.CreateVisibleObjectDAO;
import desmoj.extensions.db.visustorage.dao.MoveDAO;
import desmoj.extensions.db.visustorage.dao.MovementInterruptDAO;
import desmoj.extensions.db.visustorage.dao.RotateDAO;
import desmoj.extensions.db.visustorage.dao.RotationInterruptDAO;
import desmoj.extensions.db.visustorage.dao.SetOrientationDAO;
import desmoj.extensions.db.visustorage.dao.SetPositionDAO;
import desmoj.extensions.db.visustorage.dao.SetVisibleDAO;
import desmoj.extensions.db.visustorage.dao.TrackDAO;
import desmoj.extensions.db.visustorage.dao.VisualDAO;
import desmoj.extensions.db.visustorage.dao.WayPointDAO;
import desmoj.extensions.space3D.SimpleTrack;
import desmoj.extensions.visualEvents.AttachEvent;
import desmoj.extensions.visualEvents.CreateVisibleObjectEvent;
import desmoj.extensions.visualEvents.DetachEvent;
import desmoj.extensions.visualEvents.MoveEvent;
import desmoj.extensions.visualEvents.MovementInterruptEvent;
import desmoj.extensions.visualEvents.RemoveEvent;
import desmoj.extensions.visualEvents.RotateEvent;
import desmoj.extensions.visualEvents.RotationInterruptEvent;
import desmoj.extensions.visualEvents.SetOrientationEvent;
import desmoj.extensions.visualEvents.SetPositionEvent;
import desmoj.extensions.visualEvents.SetVisibleEvent;
import desmoj.extensions.visualEvents.VisualEvent;
import desmoj.extensions.visualEvents.VisualEventListener;
import desmoj.extensions.visualization3d.VisualModule;
import desmoj.extensions.visualization3d.VisualizationClock;
import desmoj.extensions.visualization3d.VisualizationControl;



import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

import oracle.jdbc.OracleResultSet;

import oracle.sql.BLOB;



/**
 * This Module stores the Visual Data for later use in the database.
 * @author Niklas Diehl
 *
 */
public class VisualToDBModule implements VisualModule
{

    //the name of this VisualModule
    private String _name;

    //the clock of the VisualizationControler
    private VisualizationClock _clock;

    //a list to hold the customized listeners
    private ArrayList<VisualEventListener> _listenerList = null;

    //where this module is registered to
    private VisualizationControl _visCon = null;

    //a DBConnection with access data to a database
    private DBConnection _dbConnection;

    //the data-access object of an experiment
    private ExperimentDAO _experimentDAO;

    private int _handleEventCount = 0;

    /**
     * Constructs a VisualToDBModule.
     *
     * @param name the name of this module.
     * @param dbConnection DBConnection with database access data.
     * @param experimentDAO The DAO of the underlying experiment. Used to link the visu data with the experiment data.
     */

    public VisualToDBModule(final String name, DBConnection dbConnection,
                            ExperimentDAO experimentDAO)
    {
        this(name, null, null, null, dbConnection, experimentDAO);
    }


    /**
     * Constructs a VisualToDBModule.
     *
     * @param name the name of this module.
     * @param modelPath The file path to the directory with the 3DModels (NOT simulation models) and textures; (null: models and xmls not saved; PATH should be relative for better use on a different PC; directory must end with a '/')
     * @param layoutXMLPath the file path to the layout.xml which is used for simulation (not visualization)
     *  (null: models and xmls not saved; PATH should be relative for better use on a different PC)
     * @param modelXMLPath the file path to the model.xml which is used for visualization
     *  (null: models and xmls not saved; PATH should be relative for better use on a different PC)
     * @param dbConnection DBConnection with database access data.
     * @param experimentDAO The DAO of the underlying experiment. Used to link the visu data with the experiment data.
     */
    public VisualToDBModule(final String name, String modelPath,
                            String layoutXMLPath, String modelXMLPath,
                            DBConnection dbConnection,
                            ExperimentDAO experimentDAO)
    {
        if (experimentDAO == null)
        {
            throw new IllegalArgumentException("The ParentDAO (ExperimentDAO) is null - not allowed!");
        }
        //set the name of this VisualModule
        if (name == null)
        {
            throw new IllegalArgumentException("The name of the VisualToDBModule" +
                                               " must be specified.");
        }
        _name = name;
        //the DBConnection contains the necassary data to connect to the database
        _dbConnection = dbConnection;
        //the DAO to the underlying experiment
        _experimentDAO = experimentDAO;
        //initialize clock
        _clock = VisualizationControl.getClock();

        if ((modelPath != null) && (layoutXMLPath != null) &&
            (modelXMLPath != null))
        {
            File file1 = new File(modelPath);
            File file2 = new File(layoutXMLPath);
            File file3 = new File(modelXMLPath);
            //3DModels, textures, etc.
            String[] models = file1.list();
            String[] xmls =
                new String[] { file2.getParent() + "/", file2.getName(),
                               file3.getParent() + "/", file3.getName() };

            try
            {
                SaveXMLandModels objectSaver = SaveXMLandModels.getInstance();
                objectSaver.setDBConnection(_dbConnection);
                objectSaver.setExperimentDAO(_experimentDAO);
                objectSaver.saveXML(xmls);
                objectSaver.saveModels(modelPath, models);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public String getModuleName()
    {
        return _name;
    }


    public void handleEvent(VisualEvent event)
    {
        /*_handleEventCount++;
        String text = _handleEventCount + " | ToDatabase: " +
                      event.getOccurredTime().getTimeRounded(TimeUnit.MILLISECONDS) + " : " +
                      event.getClass().getSimpleName() + " - " + event.getTargetName();
        System.out.println(text);*/

        if (event instanceof CreateVisibleObjectEvent)
        {
            this.handleCreateSpatialObjectEvent((CreateVisibleObjectEvent)event);
        }
        else if (event instanceof RotateEvent)
        {
            this.handleRotateEvent((RotateEvent)event);
        }
        else if (event instanceof MoveEvent)
        {
            this.handleMoveEvent((MoveEvent)event);
        }
        else if (event instanceof SetOrientationEvent)
        {
            this.handleSetOrientationEvent((SetOrientationEvent)event);
        }
        else if (event instanceof SetPositionEvent)
        {
            this.handleSetPositionEvent((SetPositionEvent)event);
        }
        else if (event instanceof AttachEvent)
        {
            this.handleAttachEvent((AttachEvent)event);
        }
        else if (event instanceof DetachEvent)
        {
            this.handleDetachEvent((DetachEvent)event);
        }
        else if (event instanceof MovementInterruptEvent)
        {
            this.handleMovementInterruptEvent((MovementInterruptEvent)event);
        }
        else if (event instanceof RotationInterruptEvent)
        {
            this.handleRotationInterruptEvent((RotationInterruptEvent)event);
        }
        else if (event instanceof RemoveEvent)
        {
            this.handleRemoveEvent((RemoveEvent)event);
        }
        else if (event instanceof SetVisibleEvent)
        {
            this.handleSetVisibleEvent((SetVisibleEvent)event);
        }
    }

    /**
     * Saves the SetVisibleEvent in to the database.
     * @param event The SetVisibleEvent
     */
    private void handleSetVisibleEvent(SetVisibleEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "SetVisibleEvent");
        visualDAO.save(event);
        SetVisibleDAO setVisibleDAO =
            new SetVisibleDAO(_dbConnection, visualDAO);
        setVisibleDAO.save(event);
    }

    /**
     * Saves the RemoveEvent in to the database.
     * @param event The RemoveEvent
     */
    private void handleRemoveEvent(RemoveEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "RemoveEvent");
        visualDAO.save(event);
    }

    /**
     * Saves the RotationInterruptEvent.
     * @param event The RotationInterruptEvent.
     */
    private void handleRotationInterruptEvent(RotationInterruptEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "RotationInterruptEvent");
        visualDAO.save(event);
        RotationInterruptDAO rotationInterruptDAO =
            new RotationInterruptDAO(_dbConnection, visualDAO);
        rotationInterruptDAO.save(event);
    }

    /**
     * Saves the MovementInterruptEvent.
     * @param event The MovementInterruptEvent.
     */
    private void handleMovementInterruptEvent(MovementInterruptEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "MovementInterruptEvent");
        visualDAO.save(event);
        MovementInterruptDAO movementInterruptDAO =
            new MovementInterruptDAO(_dbConnection, visualDAO);
        movementInterruptDAO.save(event);
    }

    /**
     * Saves the DetachEvent.
     * @param event The DetachEvent.
     */
    private void handleDetachEvent(DetachEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "DetachEvent");
        visualDAO.save(event);
    }

    /**
     * Saves the AttachEvent.
     * @param event The AttachEvent
     */
    private void handleAttachEvent(AttachEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "AttachEvent");
        visualDAO.save(event);
        AttachDAO attachDAO = new AttachDAO(_dbConnection, visualDAO);
        attachDAO.save(event);
    }


    /**
     * Saves the SetPositionEvent.
     * @param event The event for setting new position.
     */
    private void handleSetPositionEvent(SetPositionEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "SetPositionEvent");
        visualDAO.save(event);
        SetPositionDAO setPositionDAO =
            new SetPositionDAO(_dbConnection, visualDAO);
        setPositionDAO.save(event);
    }

    /**
     * Saves the SetOrientationEvent.
     * @param event The event to set the orientation
     */
    private void handleSetOrientationEvent(SetOrientationEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "SetOrientationEvent");
        visualDAO.save(event);
        SetOrientationDAO setOrientationDAO =
            new SetOrientationDAO(_dbConnection, visualDAO);
        setOrientationDAO.save(event);
    }

    /**
     * Saves the MoveEvent.
     * @param event The move event.
     */
    private void handleMoveEvent(MoveEvent event)
    {
        SimpleTrack track = (SimpleTrack)event.getMovement().getTrack();
        String sql =
            "SELECT ID FROM V_TRACK WHERE TRACKNAME = '" + track.getName() +
            "' AND EXPERIMENT_FK = " + _experimentDAO.getID();
        PreparedStatement stmnt;
        int trackID = -1;

        try
        {
            stmnt = _dbConnection.createPreparedStatement(sql);
            ResultSet set = stmnt.executeQuery();
            //if: track in database yet
            //then: get id of the track
            //else: save track and it's waypoints
            if (set.next())
            {
                trackID = set.getInt(1);
            }
            else
            {
                TrackDAO trackDAO =
                    new TrackDAO(_dbConnection, _experimentDAO);
                trackDAO.save(event);
                //if (track.hasWayPoints())
                //{
                    for (int i = 0; i < track.getWayPoints().size(); i++)
                    {
                        WayPointDAO wayPointDAO =
                            new WayPointDAO(_dbConnection, trackDAO, i);
                        wayPointDAO.save(track);
                    }
                //}
                trackID = trackDAO.getID();
            }
            set.close();
            stmnt.close();
        }
        catch (SQLException sqle)
        {
            //TODO exception handling
            sqle.printStackTrace();
        }

        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "MoveEvent");
        visualDAO.save(event);
        if (trackID > -1)
        {
            MoveDAO moveDAO = new MoveDAO(_dbConnection, visualDAO, trackID);
            moveDAO.save(event);
        }
        else
        {
            System.out.println("VisualToDBModule: Track not found - data in " +
                               MoveDAO.tableName + " corrupt");
        }
    }

    /**
     * Saves the RotateEvent.
     * @param event The RotateEvent.
     */
    private void handleRotateEvent(RotateEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "RotateEvent");
        visualDAO.save(event);
        RotateDAO rotateDAO = new RotateDAO(_dbConnection, visualDAO);
        rotateDAO.save(event);
    }

    /**
     * Saves the CreateVisibleObjectEvent.
     *
     * @param event The CreateVisibleObjectEvent which contains the details
     * about the new object to be created.
     */
    private void handleCreateSpatialObjectEvent(CreateVisibleObjectEvent event)
    {
        VisualDAO visualDAO =
            new VisualDAO(_dbConnection, _experimentDAO, "CreateVisibleObjectEvent");
        visualDAO.save(event);
        CreateVisibleObjectDAO createVisibleObjectDAO =
            new CreateVisibleObjectDAO(_dbConnection, visualDAO);
        createVisibleObjectDAO.save(event);
    }

    public void setVisualizationControl(VisualizationControl visCon)
    {
        _visCon = visCon;
    }

    /**
     * Registers a customized VisualEventListener to this module.
     * @param listener The listener to be registered.
     */
    public void registerNewVisualEventListener(VisualEventListener listener)
    {
        if (_listenerList == null)
        {
            _listenerList = new ArrayList<VisualEventListener>();
        }
        _listenerList.add(listener);
    }

    /* (non-Javadoc)
	 * @see desmoj.extensions.visualization3d.VisualModule#removeVisualizationControl()
	 */
    public void removeVisualizationControl()
    {
        _visCon = null;
    }

}
