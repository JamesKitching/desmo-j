package desmoj.extensions.db.visustorage.dao;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.ExperimentDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.VisualEvent;


import java.util.concurrent.TimeUnit;


public class VisualDAO extends IDSingleDatasetDAO<VisualEvent> implements SavableDAO<VisualEvent>
{
    public static final String tableName = "V_EVENTVISUAL";
    
    String _type;
    
    public VisualDAO(DBConnection dbConnection, ExperimentDAO experimentDAO, String type)
    {
        super(dbConnection, experimentDAO);
        _type = type;
    }

    protected String getTableName()
    {
        return tableName;
    }
    
    public PreparedStatement getSaveStatement(VisualEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, OCCUREDTIME, " +
                     "AFFECTEDSPATIALOBJECTNAME, TYPE, EXPERIMENT_FK) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, this.getID());
        stmnt.setLong(2,event.getOccurredTime().getTimeInEpsilon());
        stmnt.setString(3, event.getTargetName());
        stmnt.setString(4, _type);
        stmnt.setInt(5, parentDAO.getID());
        
        return stmnt;
    }

}
