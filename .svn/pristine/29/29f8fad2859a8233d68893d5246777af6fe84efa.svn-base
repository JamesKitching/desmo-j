package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.AttachEvent;
import desmoj.extensions.visualEvents.VisualEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AttachDAO extends IDSingleDatasetDAO<AttachEvent> implements SavableDAO<AttachEvent>
{
    
    public static final String tableName = "V_EVENTATTACH";
    
    public AttachDAO(DBConnection dbConnection, VisualDAO visualEventDAO)
    {
        super(dbConnection, visualEventDAO);
    }

    public PreparedStatement getSaveStatement(AttachEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, HOSTNAME, VISUAL_FK) " +
                     "VALUES (?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setString(2, event.getHostName());
        stmnt.setInt(3, parentDAO.getID());
        
        return stmnt;
    }

    protected String getTableName()
    {
        return tableName;
    }
    
}
