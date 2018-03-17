package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.MoveEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.concurrent.TimeUnit;

public class MoveDAO extends IDSingleDatasetDAO<MoveEvent> implements SavableDAO<MoveEvent>
{

    public static final String tableName = "V_EVENTMOVE";
    
    private int _trackID;

    public MoveDAO(DBConnection dbConnection, VisualDAO visualEventDAO, int trackID)
    {
        super(dbConnection, visualEventDAO);
        _trackID = trackID;
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(MoveEvent event) throws SQLException
    {
        String sql =
            "INSERT INTO " + getTableName() + " (ID, FOCUSONTRACK, VISUAL_FK, " +
            "ACCDURATION, MAXDURATION, DECDURATION, INITSPEED, MAXSPEED, " +
            "ENDSPEED, XFRONTSIDEVECTOR, YFRONTSIDEVECTOR, ZFRONTSIDEVECTOR, " +
            "TRACK_FK) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);

        stmnt.setInt(1, getID());
        stmnt.setString(2, event.isFocusedOnTrack() ? "true" : "false");
        stmnt.setInt(3, parentDAO.getID());
        stmnt.setLong(4, event.getMovement().getAccDuration().getTimeInEpsilon());
        stmnt.setLong(5, event.getMovement().getAccDuration().getTimeInEpsilon());
        stmnt.setLong(6, event.getMovement().getAccDuration().getTimeInEpsilon());
        stmnt.setDouble(7, event.getMovement().getInitialSpeed());
        stmnt.setDouble(8, event.getMovement().getMaxSpeed());
        stmnt.setDouble(9, event.getMovement().getEndSpeed());
        stmnt.setDouble(10, event.getFrontSideVector().getX());
        stmnt.setDouble(11, event.getFrontSideVector().getY());
        stmnt.setDouble(12, event.getFrontSideVector().getZ());
        stmnt.setInt(13, _trackID);

        return stmnt;
    }


}
