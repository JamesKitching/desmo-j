package desmoj.extensions.db.dao;

import desmoj.core.simulator.Parameter;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;


public class ParameterValueDAO extends MultipleDatasetDAO<Parameter> implements SavableDAO<Parameter>, LoadableDAO<Parameter>
{
    private ParameterDAO parameterDAO;

    public ParameterValueDAO(DBConnection connection, ExperimentDAO experimentDAO, ParameterDAO parameterDAO)
    {
        super(connection, experimentDAO);

        this.parameterDAO = parameterDAO;
    }

    public PreparedStatement getSaveStatement(Parameter parameter) throws SQLException
    {
        PreparedStatement stmnt = null;

        if (parameter.getParameterType() == Parameter.ParameterType.EXPERIMENTPARAMETER)
        {
            String sql =
                "INSERT INTO " + parameterDAO.getValueTableName(parameter.getType()) + " (value, parameter_fk, experiment_fk) VALUES (?, ?, ?)";
            stmnt = connection.createPreparedStatement(sql);
            stmnt.setObject(1, parameter.getValue());
            stmnt.setInt(2, parameterDAO.getID(parameter.getName()));
            stmnt.setInt(3, parentDAO.getID());
        }
        
        return stmnt;
    }

    public PreparedStatement getLoadStatement() throws SQLException
    {
        String sql = 
            "SELECT name, valuetable, int, string, double " +
            "FROM parameter " +
            "RIGHT JOIN (" +
                "SELECT id, experiment_fk, parameter_fk, " +
                "a.value AS int, b.value AS string, c.value AS double " +
                "FROM parameter_int a " +
                "FULL JOIN parameter_string b " +
                "USING (id, experiment_fk, parameter_fk) " +
                "FULL JOIN parameter_double c " +
                "USING (id, experiment_fk, parameter_fk) " +
                "WHERE experiment_fk = ?" +
            ") ON (parameter.id = parameter_fk)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setInt(1, parentDAO.getID());        
        
        return stmnt;
    }

    public Parameter toResult(ResultSet resultSet) throws SQLException
    {
        Class type = getType(resultSet.getString("VALUETABLE").toUpperCase());
        String name = resultSet.getString("Name");
        
        Parameter result = Parameter.createExperimentParameter(type, name);
        
        Object value = null;
        
        if (type.equals(Integer.class))
        {
            value = resultSet.getInt("INT");
        }
        else if (type.equals(String.class))
        {
            value = resultSet.getString("STRING");
        }
        else if (type.equals(Double.class))
        {
            value = resultSet.getDouble("DOUBLE");
        }
        else
        {
            //TODO:(Exception) cant load type.
        }

        result.setValue(value);
        
        return result;
    }

    private Class getType(String valueTableName)
    {
        Class result;
        
        if (valueTableName.equals("PARAMETER_INT"))
        {
            result = Integer.class;
        }
        else if (valueTableName.equals("PARAMETER_STRING"))
        {
            result = String.class;
        }
        else if (valueTableName.equals("PARAMETER_DOUBLE"))
        {
            result = Double.class;
        }
        else
        {
            //TODO:(Exception) cannot load.
            result = null;
        }
        
        return result;
    }
}
