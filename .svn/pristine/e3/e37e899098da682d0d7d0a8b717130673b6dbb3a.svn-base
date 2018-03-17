package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDistBooleanDAO extends SingleDatasetDAO<Boolean> implements SavableDAO<Boolean>
{

    public DBDistBooleanDAO(DBConnection dbConnection, DBDistParaDAO distParaDAO)
    {
        super(dbConnection, distParaDAO);
    }

    public PreparedStatement getSaveStatement(Boolean value) throws SQLException
    {
        String sql = "INSERT INTO OC_DIST_BOOLEAN (VALUE, OC_DIST_PARA_FK) VALUES (?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        if (value)
        {
            stmnt.setString(1, "true");
        }
        else
        {
            stmnt.setString(1, "false");
        }
        stmnt.setInt(2, parentDAO.getID());

        return stmnt;
    }
}
