package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.space3D.Rotation;
import desmoj.extensions.visualEvents.RotateEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RotateDAO extends IDSingleDatasetDAO<RotateEvent> implements SavableDAO<RotateEvent>
{
    
    public static final String tableName = "V_EVENTROTATE";
    
    public RotateDAO(DBConnection dbConnection, VisualDAO visualDAO)
    {
        super(dbConnection, visualDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(RotateEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, ANGLE, XSTOPPED, " +
                     "YSTOPPED, ZSTOPPED, DURATION, VISUAL_FK) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Rotation rotation = event.getRotation();
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setDouble(2, rotation.getAngle());
        stmnt.setString(3, rotation.getAxis()[0] ? "true" : "false");
        stmnt.setString(4, rotation.getAxis()[1] ? "true" : "false");
        stmnt.setString(5, rotation.getAxis()[2] ? "true" : "false");
        stmnt.setDouble(6, rotation.getDuration());
        stmnt.setInt(7, parentDAO.getID());
        
        return stmnt;
    }
}
