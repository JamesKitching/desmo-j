package desmoj.extensions.db.dao;

import desmoj.core.simulator.Model;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class ModelDAO extends NamedSingleDatasetDAO<Model> implements SavableDAO<Model>,
                                                                        IdenticalTestableDAO<Model>
{
    public ModelDAO(DBConnection connection, String name, ModelDAO parent)
    {
        super(connection, name, parent);
    }

    public PreparedStatement getIdStatement() throws SQLException
    {
        PreparedStatement stmnt;

        if (hasParentDAO())
        {
            String sql = "SELECT id FROM model WHERE name = ? AND model_fk = ?";

            stmnt = connection.createPreparedStatement(sql);
            stmnt.setString(1, name);
            stmnt.setInt(2, parentDAO.getID());
        }
        else
        {
            String sql = "SELECT id FROM model WHERE name = ?";

            stmnt = connection.createPreparedStatement(sql);
            stmnt.setString(1, name);
        }

        return stmnt;
    }

    public PreparedStatement getSaveStatement(Model model) throws SQLException
    {
        String sql = "INSERT INTO model (name, description, model_fk) VALUES (?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setString(2, model.description());

        if (hasParentDAO())
        {
            stmnt.setInt(3, parentDAO.getID());
        }
        else
        {
            stmnt.setNull(3, Types.INTEGER);
        }

        return stmnt;
    }

    public PreparedStatement getIdenticalStatement(Model model) throws SQLException
    {
        PreparedStatement stmnt;

        if (hasParentDAO())
        {
            String sql = "SELECT name, description FROM model WHERE name = ? AND model_fk = ?";

            stmnt = connection.createPreparedStatement(sql);
            stmnt.setString(1, name);
            stmnt.setInt(2, parentDAO.getID());
        }
        else
        {
            String sql = "SELECT name, description FROM model WHERE name = ?";

            stmnt = connection.createPreparedStatement(sql);
            stmnt.setString(1, name);
        }

        return stmnt;
    }

    public boolean getTestResult(ResultSet resultSet, Model model) throws SQLException
    {
        boolean result = false;

        result = model.getName().equals(resultSet.getString("NAME"));
        result &= model.description().equals(resultSet.getString("DESCRIPTION"));

        return result;
    }
}
