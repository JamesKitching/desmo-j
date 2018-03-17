package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.SavableDAO;
import desmoj.extensions.visualEvents.CreateVisibleObjectEvent;



import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateVisibleObjectDAO extends IDSingleDatasetDAO<CreateVisibleObjectEvent> implements SavableDAO<CreateVisibleObjectEvent>
{
 
    public static final String tableName = "V_EVENTCREATEVISIBLEOBJECT"; 
 
    public CreateVisibleObjectDAO(DBConnection dbConnection, VisualDAO visualEventDAO)
    {
        super(dbConnection, visualEventDAO);
    }

    protected String getTableName()
    {
        return tableName;
    }

    public PreparedStatement getSaveStatement(CreateVisibleObjectEvent event) throws SQLException
    {
        String sql = "INSERT INTO " + getTableName() + " (ID, VIRTUALMODEL, MOVABLE, VISUAL_FK) " +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        
        stmnt.setInt(1, getID());
        stmnt.setString(2, event.getVisualModel());
        stmnt.setString(3, event.isMovable() ? "true" : "false");
        stmnt.setInt(4, parentDAO.getID());
        
        return stmnt;
    }



}
