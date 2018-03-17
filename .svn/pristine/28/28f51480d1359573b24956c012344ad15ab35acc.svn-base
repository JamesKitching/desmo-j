package desmoj.extensions.db.visustorage.dao;


import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.db.dao.SingleDatasetDAO;
import desmoj.extensions.space3D.SimpleTrack;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WayPointDAO extends SingleDatasetDAO<SimpleTrack> implements SavableDAO<SimpleTrack>
{

    public static final String tableName = "V_WAYPOINT";

    //position of the waypoint in the array
    private int _position;
    private int id;

    public WayPointDAO(DBConnection dbConnection, TrackDAO trackDAO, int arrayPosition)
    {
        super(dbConnection, trackDAO);
        _position = arrayPosition;
        id = -1;
    }

    protected String getTableName()
    {
        return "V_WAYPOINT";
    }

    public PreparedStatement getSaveStatement(SimpleTrack track) throws SQLException
    {
        String sql =
            "INSERT INTO " + getTableName() + " (ID, POSITION, X, Y, Z, TRACK_FK)" +
            "VALUES (?, ?, ?, ?, ?, ?)";

        double[] wayPoint = track.getWayPoints().get(_position);
        PreparedStatement stmnt = connection.createPreparedStatement(sql);

        stmnt.setInt(1, getID());
        stmnt.setInt(2, _position);
        stmnt.setDouble(3, wayPoint[0]);
        stmnt.setDouble(4, wayPoint[1]);
        stmnt.setDouble(5, wayPoint[2]);
        stmnt.setInt(6, parentDAO.getID());

        return stmnt;
    }

    /**
     * @return the framework managed id
     */
    public int getID() throws SQLException
    {
        if (id == -1)
        {
            String sql = "SELECT COUNT(*), MAX(ID) FROM " + getTableName();
            PreparedStatement stmnt = connection.createPreparedStatement(sql);
            ResultSet set = stmnt.executeQuery();
            set.next();
            int count = (set.getInt(1) == 0) ? 0 : set.getInt(2);
            id = (count + 1);
            set.close();
            stmnt.close();
        }
        return id;
    }
}
