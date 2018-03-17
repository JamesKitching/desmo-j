package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.SetOrientationEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetOrientationDAO extends IDSingleDatasetDAO<SetOrientationEvent> implements SavableDAO<SetOrientationEvent>
{
    
    public static final String tableName = "V_EVENTSETORIENTATION";
    
    public SetOrientationDAO(DBConnection dbConnection, VisualDAO visualDAO)
    {
        super(dbConnection, visualDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(SetOrientationEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, XORI, YORI, ZORI, " +
                     "WORI, VISUAL_FK) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setDouble(2, event.getOrientation().getX());
        stmnt.setDouble(3, event.getOrientation().getY());
        stmnt.setDouble(4, event.getOrientation().getZ());
        stmnt.setDouble(5, event.getOrientation().getW());
        stmnt.setInt(6, parentDAO.getID());
        
        return stmnt;
    }
}
