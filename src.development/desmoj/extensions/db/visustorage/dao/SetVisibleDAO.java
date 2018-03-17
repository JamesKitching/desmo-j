package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.SetVisibleEvent;

import desmoj.extensions.visualEvents.VisualEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetVisibleDAO extends IDSingleDatasetDAO<SetVisibleEvent> implements SavableDAO<SetVisibleEvent>
{
    
    public static final String tableName = "V_EVENTSETVISIBLE";
    
    public SetVisibleDAO(DBConnection dbConnection, VisualDAO visualDAO)
    {
        super(dbConnection, visualDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(SetVisibleEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, VISIBLEENABLE, VISUAL_FK) " +
                     "VALUES (?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setString(2, event.getVisible() ? "true" : "false");
        stmnt.setInt(3, parentDAO.getID());
        
        return stmnt;
    }
}
