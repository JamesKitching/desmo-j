package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;

public class ConditionArgumentDAO extends MultipleDatasetDAO<Object> implements SavableDAO<Object>, LoadableDAO<Object>
{
    private int counter = 0;
    
    public ConditionArgumentDAO(DBConnection connection, ConditionDAO conditionDAO)
    {
        super(connection, conditionDAO);
    }

    public PreparedStatement getSaveStatement(Object obj) throws SQLException
    {
        Class cls = obj.getClass();
        String table;
        
        if (cls.equals(String.class) || cls.equals(Boolean.class))
        {
            table = "condition_arg_string";
        }
        else
        {
            table = "condition_arg_numeric";
        }
        
        String sql = "INSERT INTO " + table + " (num, type, value, condition_fk) VALUES (?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setInt(1, counter);
        counter++;
        stmnt.setString(2, cls.getSimpleName());
        
        if (cls.equals(Boolean.class))
            stmnt.setObject(3, ((Boolean)obj).booleanValue() ? "true" : "false");
        else if (cls.equals(Character.class))
            stmnt.setInt(3, (int)((Character)obj).charValue());
        else
            stmnt.setObject(3, obj);
        
        //stmnt.setObject(2, cls.equals(Boolean.class) ? ((Boolean)obj).booleanValue() ? "true" : "false" : obj);
        stmnt.setInt(4, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getLoadStatement() throws SQLException
    {        
        String sql = "SELECT id, num as n, type, a.value as num, b.value as str " + 
            "FROM condition_arg_numeric a FULL JOIN condition_arg_string b " +
            "USING (id, num, type, condition_fk) WHERE condition_fk = ? " +
            "ORDER BY n";
         
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setInt(1, parentDAO.getID());
        
        return stmnt;
    }

    public Object toResult(ResultSet resultSet) throws SQLException
    {
        String type = resultSet.getString("TYPE");
        Object result = null;
        
        if (type.equals("Boolean"))
            result = resultSet.getString("STR").equals("true") ? true : false;
        else if (type.equals("Integer"))
            result = resultSet.getInt("NUM");
        else if (type.equals("Character"))
            result = (char)resultSet.getInt("NUM");
        else if (type.equals("Byte"))
            result = resultSet.getByte("NUM");
        else if (type.equals("Short"))
            result = resultSet.getShort("NUM");
        else if (type.equals("Double"))
            result = resultSet.getDouble("NUM");
        else if (type.equals("Long"))
            result = resultSet.getLong("NUM");
        else if (type.equals("Float"))
            result = resultSet.getFloat("NUM");
        else if (type.equals("String"))
            result = resultSet.getString("STR");
        
        return result;
    }
    
    @Override public void saveAll(Collection<Object> objectCollection)
    {
        counter = 0;
        
        super.saveAll(objectCollection);
    }
}
