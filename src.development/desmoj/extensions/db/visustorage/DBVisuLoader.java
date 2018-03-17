package desmoj.extensions.db.visustorage;


import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

import desmoj.extensions.db.DBConnection;
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
import desmoj.extensions.db.visustorage.sim.MockupModel;
import desmoj.extensions.space3D.ExtendedLength;
import desmoj.extensions.space3D.Movement;
import desmoj.extensions.space3D.Rotation;
import desmoj.extensions.space3D.SimpleTrack;
import desmoj.extensions.space3D.Track;
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
import desmoj.extensions.visualEvents.VisualEventTransmitter;
import desmoj.extensions.visualization3d.SpatialVis3DModule;
import desmoj.extensions.visualization3d.VisualModule;
import desmoj.extensions.visualization3d.VisualizationControl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;




public class DBVisuLoader
{
    private Model _mockupSource;

    private DBConnection _dbConnection;

    private Date _startTime;

    private Map<Integer, String> _eventMap;

    private List<VisualEvent> _eventList;

    private int _experimentID;
    // time to skip in the beginning (in ms)
    private long _skipTime;

    private Timer _timer;

    private VisualizationControl _visCon;
    //private SpatialVis3DModule _3DModule;
    private VisualModule _3DModule;

    public DBVisuLoader(DBConnection dbCon, int experimentID)
    {
        _dbConnection = dbCon;
        _experimentID = experimentID;
        
        _mockupSource = new MockupModel();
        _visCon = new VisualizationControl();
        _eventList = new ArrayList<VisualEvent>();
        _timer = new Timer();
        
    }

    public void load()
    {
        try
        {
            loadEvents();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void loadEvents() throws SQLException
    {
        String sql =
            "SELECT ID, TYPE FROM " + VisualDAO.tableName + " WHERE EXPERIMENT_FK = " +
            _experimentID;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        _eventMap = new HashMap<Integer, String>();

        if (set.next())
        {
            do
            {
                _eventMap.put(set.getInt(1), set.getString(2));
            }
            while (set.next());
        }
        else
        {
            System.out.println("no experiment configuration found for the given ID - exiting");
            System.exit(0);
        }

        set.close();
        stmnt.close();

        for (Map.Entry<Integer, String> entry : _eventMap.entrySet())
        {
            int id = entry.getKey();
            String eventType = entry.getValue();
            if (eventType.equals("AttachEvent"))
            {
                loadAttach(id);
            }
            else if (eventType.equals("CreateVisibleObjectEvent"))
            {
                loadCreateVisibleObject(id);
            }
            else if (eventType.equals("DetachEvent"))
            {
                loadDetach(id);
            }
            else if (eventType.equals("MoveEvent"))
            {
                loadMove(id);
            }
            else if (eventType.equals("MovementInterruptEvent"))
            {
                loadMovementInterrupt(id);
            }
            else if (eventType.equals("RemoveEvent"))
            {
                loadRemove(id);
            }
            else if (eventType.equals("RotateEvent"))
            {
                loadRotate(id);
            }
            else if (eventType.equals("RotationInterruptEvent"))
            {
                loadRotationInterrupt(id);
            }
            else if (eventType.equals("SetOrientationEvent"))
            {
                loadSetOrientation(id);
            }
            else if (eventType.equals("SetPositionEvent"))
            {
                loadSetPosition(id);
            }
            else if (eventType.equals("SetVisibleEvent"))
            {
                loadSetVisible(id);
            }
            else
            {
                System.out.println("Corrupted Data - event type unknown");
            }
        }
    }


    private void loadAttach(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + AttachDAO.tableName +
            ".HOSTNAME FROM " + VisualDAO.tableName + ", " +
            AttachDAO.tableName + " WHERE " + VisualDAO.tableName + ".ID = " +
            AttachDAO.tableName + ".VISUAL_FK AND " + VisualDAO.tableName +
            ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        AttachEvent event =
            new AttachEvent(_mockupSource, set.getString(2), set.getString(3),
                            occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadCreateVisibleObject(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + CreateVisibleObjectDAO.tableName +
            ".VIRTUALMODEL, " + CreateVisibleObjectDAO.tableName +
            ".MOVABLE " + "FROM " + VisualDAO.tableName + ", " +
            CreateVisibleObjectDAO.tableName + " WHERE " +
            VisualDAO.tableName + ".ID = " + CreateVisibleObjectDAO.tableName +
            ".VISUAL_FK AND " + VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        long timeToHappen = set.getLong(1);
        TimeInstant occuredTime =
            new TimeInstant(timeToHappen, TimeUnit.MICROSECONDS);

        CreateVisibleObjectEvent event =
            new CreateVisibleObjectEvent(_mockupSource, set.getString(2),
                                         set.getString(3),
                                         set.getString(4).equals("true"),
                                         occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadDetach(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME FROM " + VisualDAO.tableName +
            " WHERE " + VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        DetachEvent event =
            new DetachEvent(_mockupSource, set.getString(2), occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadMove(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + MoveDAO.tableName +
            ".FOCUSONTRACK, " + MoveDAO.tableName + ".ACCDURATION, " +
            MoveDAO.tableName + ".MAXDURATION, " + MoveDAO.tableName +
            ".DECDURATION, " + MoveDAO.tableName + ".INITSPEED, " +
            MoveDAO.tableName + ".MAXSPEED, " + MoveDAO.tableName +
            ".ENDSPEED, " + MoveDAO.tableName + ".XFRONTSIDEVECTOR, " +
            MoveDAO.tableName + ".YFRONTSIDEVECTOR, " + MoveDAO.tableName +
            ".ZFRONTSIDEVECTOR, " + TrackDAO.tableName + ".TRACKNAME, " +
            TrackDAO.tableName + ".XSTART, " + TrackDAO.tableName +
            ".YSTART, " + TrackDAO.tableName + ".ZSTART, " +
            TrackDAO.tableName + ".XDEST, " + TrackDAO.tableName + ".YDEST, " +
            TrackDAO.tableName + ".ZDEST FROM " + VisualDAO.tableName + ", " +
            MoveDAO.tableName + ", " + TrackDAO.tableName + " WHERE " +
            VisualDAO.tableName + ".ID = " + MoveDAO.tableName +
            ".VISUAL_FK AND " + VisualDAO.tableName + ".ID = " + id + " AND " +
            MoveDAO.tableName + ".TRACK_FK = " + TrackDAO.tableName +
            ".ID AND " + TrackDAO.tableName + ".EXPERIMENT_FK = " +
            _experimentID;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();

        set.next();
        String trackName = set.getString("TRACKNAME");

        String sql2 =
            "SELECT " + WayPointDAO.tableName + ".X, " + WayPointDAO.tableName +
            ".Y, " + WayPointDAO.tableName + ".Z " + "FROM " +
            TrackDAO.tableName + ", " + WayPointDAO.tableName + " WHERE " +
            TrackDAO.tableName + ".ID = " + WayPointDAO.tableName +
            ".TRACK_FK AND " + TrackDAO.tableName + ".TRACKNAME = '" +
            trackName + "' ORDER BY " + WayPointDAO.tableName + ".POSITION";

        PreparedStatement stmnt2 = _dbConnection.createPreparedStatement(sql2);
        ResultSet set2 = stmnt2.executeQuery();

        ArrayList<double[]> wayPoints = new ArrayList<double[]>();

        while (set2.next())
        {
            double[] wayPoint = new double[3];
            wayPoint[0] = set2.getDouble("X");
            wayPoint[1] = set2.getDouble("Y");
            wayPoint[2] = set2.getDouble("Z");
            wayPoints.add(wayPoint);
        }

        ExtendedLength[] start = new ExtendedLength[3];
        ExtendedLength[] destination = new ExtendedLength[3];

        start[0] = new ExtendedLength(set.getDouble("XSTART"), 6);
        start[1] = new ExtendedLength(set.getDouble("YSTART"), 6);
        start[2] = new ExtendedLength(set.getDouble("ZSTART"), 6);
        destination[0] = new ExtendedLength(set.getDouble("XDEST"), 6);
        destination[1] = new ExtendedLength(set.getDouble("YDEST"), 6);
        destination[2] = new ExtendedLength(set.getDouble("ZDEST"), 6);

        Track track =
            new SimpleTrack(_mockupSource, trackName, start, destination,
                            wayPoints, false);

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        Vector3d frontsidevector =
            new Vector3d(set.getDouble("XFRONTSIDEVECTOR"),
                         set.getDouble("YFRONTSIDEVECTOR"),
                         set.getDouble("ZFRONTSIDEVECTOR"));
        Movement movement =
            new Movement(track, new TimeSpan(set.getLong("ACCDURATION"),
                                             TimeUnit.MICROSECONDS),
                         new TimeSpan(set.getLong("MAXDURATION"),
                                      TimeUnit.MICROSECONDS),
                         new TimeSpan(set.getLong("DECDURATION"),
                                      TimeUnit.MICROSECONDS),
                         set.getDouble("INITSPEED"), set.getDouble("MAXSPEED"),
                         set.getDouble("ENDSPEED"));

        MoveEvent event =
            new MoveEvent(_mockupSource, set.getString("AFFECTEDSPATIALOBJECTNAME"),
                          movement,
                          set.getString("FOCUSONTRACK").equals("true"),
                          frontsidevector, occuredTime);
        _eventList.add(event);

        set.close();
        set2.close();
        stmnt.close();
        stmnt2.close();
    }

    private void loadMovementInterrupt(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + MovementInterruptDAO.tableName +
            ".X, " + MovementInterruptDAO.tableName + ".Y, " +
            MovementInterruptDAO.tableName + ".Z, " +
            MovementInterruptDAO.tableName + ".W " + "FROM " +
            VisualDAO.tableName + ", " + MovementInterruptDAO.tableName +
            " WHERE " + VisualDAO.tableName + ".ID = " +
            MovementInterruptDAO.tableName + ".VISUAL_FK AND " +
            VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        double[] stoppedAt =
            new double[] { set.getDouble(3), set.getDouble(4), set.getDouble(5),
                           set.getDouble(6) };

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        MovementInterruptEvent event =
            new MovementInterruptEvent(_mockupSource, set.getString(2),
                                       stoppedAt, occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadRemove(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME FROM " + VisualDAO.tableName +
            " WHERE " + VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        RemoveEvent event =
            new RemoveEvent(_mockupSource, set.getString(2), occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadRotate(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + RotateDAO.tableName + ".ANGLE, " +
            RotateDAO.tableName + ".XSTOPPED, " + RotateDAO.tableName +
            ".YSTOPPED, " + RotateDAO.tableName + ".ZSTOPPED, " +
            RotateDAO.tableName + ".DURATION FROM " + VisualDAO.tableName +
            ", " + RotateDAO.tableName + " WHERE " + VisualDAO.tableName +
            ".ID = " + RotateDAO.tableName + ".VISUAL_FK AND " +
            VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        boolean[] axis =
            new boolean[] { set.getString(4).equals("true"), set.getString(5).equals("true"),
                            set.getString(6).equals("true") };
        Rotation rotation =
            new Rotation(axis, set.getDouble(3), set.getDouble(7));

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        RotateEvent event =
            new RotateEvent(_mockupSource, set.getString(2), rotation,
                            occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadRotationInterrupt(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + RotationInterruptDAO.tableName +
            ".X, " + RotationInterruptDAO.tableName + ".Y, " +
            RotationInterruptDAO.tableName + ".Z, " +
            RotationInterruptDAO.tableName + ".W FROM " + VisualDAO.tableName +
            ", " + RotationInterruptDAO.tableName + " WHERE " +
            VisualDAO.tableName + ".ID = " + RotationInterruptDAO.tableName +
            ".VISUAL_FK AND " + VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        Quat4d stoppedAt =
            new Quat4d(set.getDouble(3), set.getDouble(4), set.getDouble(5),
                       set.getDouble(6));

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        RotationInterruptEvent event =
            new RotationInterruptEvent((_mockupSource), set.getString(2),
                                       stoppedAt, occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadSetOrientation(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + SetOrientationDAO.tableName +
            ".XORI, " + SetOrientationDAO.tableName + ".YORI, " +
            SetOrientationDAO.tableName + ".ZORI, " +
            SetOrientationDAO.tableName + ".WORI FROM " + VisualDAO.tableName +
            ", " + SetOrientationDAO.tableName + " WHERE " +
            VisualDAO.tableName + ".ID = " + SetOrientationDAO.tableName +
            ".VISUAL_FK AND " + VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        Quat4d stoppedAt =
            new Quat4d(set.getDouble(3), set.getDouble(4), set.getDouble(5),
                       set.getDouble(6));

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        SetOrientationEvent event =
            new SetOrientationEvent(_mockupSource, set.getString(2), stoppedAt,
                                    occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadSetPosition(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + SetPositionDAO.tableName +
            ".X, " + SetPositionDAO.tableName + ".Y, " +
            SetPositionDAO.tableName + ".Z FROM " + VisualDAO.tableName +
            ", " + SetPositionDAO.tableName + " WHERE " + VisualDAO.tableName +
            ".ID = " + SetPositionDAO.tableName + ".VISUAL_FK AND " +
            VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        SetPositionEvent event =
            new SetPositionEvent(_mockupSource, set.getString(2),
                                 set.getDouble(3), set.getDouble(4),
                                 set.getDouble(5), occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }

    private void loadSetVisible(int id) throws SQLException
    {
        String sql =
            "SELECT " + VisualDAO.tableName + ".OCCUREDTIME, " + VisualDAO.tableName +
            ".AFFECTEDSPATIALOBJECTNAME, " + SetVisibleDAO.tableName +
            ".VISIBLEENABLE FROM " + VisualDAO.tableName + ", " +
            SetVisibleDAO.tableName + " WHERE " + VisualDAO.tableName +
            ".ID = " + SetVisibleDAO.tableName + ".VISUAL_FK AND " +
            VisualDAO.tableName + ".ID = " + id;

        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();

        TimeInstant occuredTime =
            new TimeInstant(set.getLong(1), TimeUnit.MICROSECONDS);

        SetVisibleEvent event =
            new SetVisibleEvent(_mockupSource, set.getString(2),
                                set.getString(3).equals("true"), occuredTime);
        _eventList.add(event);
        set.close();
        stmnt.close();
    }
    
    //the modelXML and Models are loaded, path to the XML is returned
    public String loadXMLandModels()
    {
        String modelXMLPath = null;
        try
        {
            LoadXMLandModels objectLoader = LoadXMLandModels.getInstance();
            objectLoader.setDBConnection(_dbConnection);
            objectLoader.setExperimentDAO(_experimentID);
            objectLoader.loadXML();
            objectLoader.loadModels();
            modelXMLPath = objectLoader.getModelXMLPath();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return modelXMLPath;
    }

    public void start(long skipTime, SpatialVis3DModule module)
    {
        _skipTime = skipTime;
        start(module);
    }
    
    public void start(long skipTime)
    {
        _skipTime = skipTime;
        start();
    }
    public void start()
    {
        String modelXML;
        VisualModule module;
        
        modelXML = this.loadXMLandModels();
        module = new SpatialVis3DModule("Visualization based on stored Simulation Data",
                                   modelXML);
        start(module);
    }
    
    
    public void start(VisualModule module)
    {
        _3DModule = module;

        _visCon.addModule(_3DModule);
        
        _startTime = new Date();
        Long occuredTime;
        for (VisualEvent event : _eventList)
        {
            if (event instanceof CreateVisibleObjectEvent)
            {
                occuredTime =
                        (_startTime.getTime() + event.getOccurredTime().getTimeRounded(TimeUnit.MILLISECONDS) +
                         990L);
            }
            else if (event instanceof SetPositionEvent)
            {
                occuredTime =
                        (_startTime.getTime() + event.getOccurredTime().getTimeRounded(TimeUnit.MILLISECONDS) +
                         995L);
            }
            else
                occuredTime =
                        (_startTime.getTime() + event.getOccurredTime().getTimeRounded(TimeUnit.MILLISECONDS) +
                         1000L);

            //amount of time to overleap
            occuredTime -= _skipTime;
            _timer.schedule(new EventTask(event,
                                          VisualEventTransmitter.getVisualEventTransmitter()),
                            (new Date(occuredTime)));
        }
/*
        if (_skipTime > 0L)
        {
            occuredTime = (_startTime.getTime() - _skipTime);
            _timer.schedule(new FrameTask(module.getFrame(), false),
                            new Date(occuredTime));

            occuredTime = (_startTime.getTime());
            _timer.schedule(new FrameTask(module.getFrame(), true),
                            new Date(occuredTime));
        }
*/
    }
    
    public void setExperimentID(int id)
    {
        _experimentID = id;
    }
    
    public void setSkipTime(long time)
    {
        _skipTime = time;
    }

    public static void main(String[] args)
    {
        DBConnection dbCon =
            new DBConnection("jdbc:oracle:thin:@rzdspc9.informatik.uni-hamburg.de:1521:edu",
                             "desmo_j", "desmo$2009");
        
        DBVisuLoader dbVisuLoader =
            new DBVisuLoader(dbCon, 681);
        
        dbVisuLoader.load();
        dbVisuLoader.start(10000L);
    }
}
