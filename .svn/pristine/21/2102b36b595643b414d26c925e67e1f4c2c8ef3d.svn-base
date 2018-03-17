package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.IdentityHashMap;


public abstract class NamedMultipleDatasetDAO<T> extends MultipleDatasetDAO<T>
{
    private IdentityHashMap<String, Integer> ids;
    
    public NamedMultipleDatasetDAO(DBConnection connection, NamedSingleDatasetDAO parentDAO)
    {
        super(connection, parentDAO);
        
        ids = new IdentityHashMap<String, Integer>();
    }

    public int getID(String name)
    {
        int result = 0;
        
        if (ids.containsKey(name))
        {
            result = ids.get(name);
        }
        
        if (result == 0)
        {
            try
            {
                PreparedStatement stmnt = getIdStatement(name);
                ResultSet resultSet = stmnt.executeQuery();
                
                if (resultSet.next())
                {
                    result = resultSet.getInt("ID");
                }
                else
                {
                    //TODO: (Exception) not found.
                }
                
                resultSet.close();
                stmnt.close();
            }
            catch (Exception e)
            {
                // TODO: Add catch code
                e.printStackTrace();
            }
        }
        
        ids.put(name, result);
        
        return result;
    }
    
    public boolean existsInDatabase(String name)
    {
        return (getID(name) > 0);
    }
    
    public abstract PreparedStatement getIdStatement(String name) throws SQLException;
}