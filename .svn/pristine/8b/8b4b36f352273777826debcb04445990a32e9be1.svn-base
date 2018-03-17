package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDistParaDAO extends NamedSingleDatasetDAO<String> implements SavableDAO<String>
{


    public DBDistParaDAO(DBConnection dbConnection, String name, DBDistributionDAO distributionDAO)
    {
        super(dbConnection, name, distributionDAO);
        
    }

    public PreparedStatement getSaveStatement(String valueTable) throws SQLException
    {
        String sql =
            "INSERT INTO OC_DIST_PARA (NAME, VALUETABLE, OC_REPORT_DIST_FK) VALUES (?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setString(2, valueTable);
        stmnt.setInt(3, parentDAO.getID());

        return stmnt;
        }

    public PreparedStatement getIdStatement() throws SQLException
    {
        String sql = "SELECT ID FROM OC_DIST_PARA WHERE NAME = ? AND OC_REPORT_DIST_FK = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }
}


