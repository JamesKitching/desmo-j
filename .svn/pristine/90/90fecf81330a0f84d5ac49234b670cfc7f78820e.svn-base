package desmoj.extensions.db.dao;


import desmoj.core.simulator.Parameter;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;


public class ParameterDAO extends NamedMultipleDatasetDAO<Parameter> implements SavableDAO<Parameter>, IdenticalTestableDAO<Parameter>
{
    public ParameterDAO(DBConnection connection, ModelDAO modelDAO)
    {
        super(connection, modelDAO);
    }

    public PreparedStatement getSaveStatement(Parameter parameter) throws SQLException
    {
        PreparedStatement stmnt = null;

        if (parameter.getParameterType() == Parameter.ParameterType.EXPERIMENTPARAMETER)
        {
            String sql = "INSERT INTO parameter (name, valuetable, model_fk) VALUES (?, ?, ?)";
            stmnt = connection.createPreparedStatement(sql);
            stmnt.setString(1, parameter.getName());
            stmnt.setString(2, getValueTableName(parameter.getType()));
            stmnt.setInt(3, parentDAO.getID());
        }

        return stmnt;
    }

    public PreparedStatement getIdStatement(String name) throws SQLException
    {
        String sql = "SELECT id FROM parameter WHERE name = ? AND model_fk = ?";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }

    public String getValueTableName(Class type)
    {
        String result = null;

        if (type.equals(Integer.class))
        {
            result = "parameter_int";
        }
        else if (type.equals(String.class))
        {
            result = "parameter_string";
        }
        else if (type.equals(Double.class))
        {
            result = "parameter_double";       
        }
        else
        {
            //TODO: (Exception) can't save type
        }

        return result.toUpperCase();
    }

    public PreparedStatement getIdenticalStatement(Parameter parameter) throws SQLException
    {
        String sql = "SELECT name, valuetable FROM parameter WHERE model_fk = ? ORDER BY name";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setInt(1, parentDAO.getID());
        
        return stmnt;
    }

    public boolean getTestResult(ResultSet resultSet, Parameter parameter) throws SQLException
    {
        boolean result;
        
        result = resultSet.getString("NAME").equals(parameter.getName());
        result &= resultSet.getString("VALUETABLE").toUpperCase().equals(getValueTableName(parameter.getType()));
        
        return result;
    }
}


