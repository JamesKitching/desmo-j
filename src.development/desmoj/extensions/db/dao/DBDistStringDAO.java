package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDistStringDAO extends SingleDatasetDAO<String> implements SavableDAO<String>
{

    public DBDistStringDAO(DBConnection dbConnection, DBDistParaDAO distParaDAO)
    {
        super(dbConnection, distParaDAO);
    }

    public PreparedStatement getSaveStatement(String value) throws SQLException
    {
        String sql = "INSERT INTO OC_DIST_STRING (VALUE, OC_DIST_PARA_FK) VALUES (?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, value);
        stmnt.setInt(2, parentDAO.getID());

        return stmnt;
    }
}
