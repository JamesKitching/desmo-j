package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.MoveEvent;

import desmoj.extensions.visualEvents.MovementInterruptEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovementInterruptDAO extends IDSingleDatasetDAO<MovementInterruptEvent> implements SavableDAO<MovementInterruptEvent>
{
    
    public static final String tableName = "V_EVENTMOVEMENTINTERRUPT";
    
    public MovementInterruptDAO(DBConnection dbConnection, VisualDAO visualDAO)
    {
        super(dbConnection, visualDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(MovementInterruptEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, XSTOPPED, YSTOPPED, " +
                     "ZSTOPPED, VISUAL_FK) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setDouble(2, event.getStoppedAtPosition()[0]);
        stmnt.setDouble(3, event.getStoppedAtPosition()[1]);
        stmnt.setDouble(4, event.getStoppedAtPosition()[2]);
        stmnt.setInt(5, parentDAO.getID());
        
        return stmnt;
    }
}
