package desmoj.extensions.db.dao;

import desmoj.core.dist.Distribution;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DistributionDAO extends NamedMultipleDatasetDAO<Distribution> implements SavableDAO<Distribution>, IdenticalTestableDAO<Distribution>
{
    public DistributionDAO(DBConnection connection, ModelDAO modelDAO)
    {
        super(connection, modelDAO);
    }

    public PreparedStatement getIdStatement(String name) throws SQLException
    {
        String sql = "SELECT id FROM distribution WHERE name = ? AND model_fk = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getSaveStatement(Distribution distribution) throws SQLException
    {
        String sql = "INSERT INTO distribution (name, model_fk) VALUES (?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, distribution.getName());
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getIdenticalStatement(Distribution object) throws SQLException
    {
        String sql = "SELECT name FROM distribution WHERE model_fk = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setInt(1, parentDAO.getID());
        
        return stmnt;
    }

    public boolean getTestResult(ResultSet resultSet, Distribution distribution) throws SQLException
    {
        return resultSet.getString("NAME").equals(distribution.getName());
    }
}
