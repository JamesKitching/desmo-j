package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.ExperimentDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.space3D.Track;
import desmoj.extensions.visualEvents.MoveEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrackDAO extends IDSingleDatasetDAO<MoveEvent> implements SavableDAO<MoveEvent>
{

    public static final String tableName = "V_TRACK";

    public TrackDAO(DBConnection dbConnection, ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(MoveEvent event) throws SQLException
    {
        String sql =
            "INSERT INTO " + getTableName() + " (TRACKNAME, XSTART, YSTART, ZSTART, " +
            "XDEST, YDEST, ZDEST, EXPERIMENT_FK, ID) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);

        Track track = event.getMovement().getTrack();
        stmnt.setString(1, track.getName());
        stmnt.setDouble(2, track.getStartPositionValue()[0]);
        stmnt.setDouble(3, track.getStartPositionValue()[1]);
        stmnt.setDouble(4, track.getStartPositionValue()[2]);
        stmnt.setDouble(5, track.getDestinationValue()[0]);
        stmnt.setDouble(6, track.getDestinationValue()[1]);
        stmnt.setDouble(7, track.getDestinationValue()[2]);
        stmnt.setDouble(8, parentDAO.getID());
        stmnt.setInt(9, this.getID());

        return stmnt;
    }

}
