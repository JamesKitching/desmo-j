package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDistNumberDAO extends SingleDatasetDAO<Number> implements SavableDAO<Number>
{

    public DBDistNumberDAO(DBConnection dbConnection, DBDistParaDAO distParaDAO)
    {
        super(dbConnection, distParaDAO);
    }

    public PreparedStatement getSaveStatement(Number value) throws SQLException
    {
        String sql = "INSERT INTO OC_DIST_NUMBER (VALUE, OC_DIST_PARA_FK) VALUES (?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        if (value instanceof Integer)
        {
            stmnt.setInt(1, value.intValue());
        }
        else if (value instanceof Long)
        {
            stmnt.setLong(1, value.longValue());
        }
        else if (value instanceof Float)
        {
            stmnt.setFloat(1, value.floatValue());
        }
        else if (value instanceof Double)
        {
            stmnt.setDouble(1, value.doubleValue());
        }
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }
}