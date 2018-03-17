package desmoj.extensions.db.visustorage.dao;


import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.RotationInterruptEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RotationInterruptDAO extends IDSingleDatasetDAO<RotationInterruptEvent> implements SavableDAO<RotationInterruptEvent>
{
    
    public static final String tableName = "V_EVENTROTATIONINTERRUPT";
    
    public RotationInterruptDAO(DBConnection dbConnection, VisualDAO visualDAO)
    {
        super(dbConnection, visualDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(RotationInterruptEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, X, Y, Z, W, VISUAL_FK) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setDouble(2, event.getStoppedAtOrientation().getX());
        stmnt.setDouble(3, event.getStoppedAtOrientation().getY());
        stmnt.setDouble(4, event.getStoppedAtOrientation().getZ());
        stmnt.setDouble(5, event.getStoppedAtOrientation().getW());
        stmnt.setInt(6, parentDAO.getID());
        
        return stmnt;
    }
}
