package desmoj.extensions.db.dao;

import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class NamedSingleDatasetDAO<T> extends SingleDatasetDAO<T> implements IDDAO
{
    private int id;
    protected String name;
    
    public NamedSingleDatasetDAO(DBConnection connection, String name, NamedSingleDatasetDAO parentDAO)
    {
        super(connection, parentDAO);
        
        this.name = name;
    }
    
    public NamedSingleDatasetDAO(DBConnection connection, String name)
    {
        this(connection, name, null);
    }
    
    public String getName()
    {
        return name;
    }

    public int getID()
    {
        int result = id;
        
        if (result == 0)
        {
            try
            {
                PreparedStatement stmnt = getIdStatement();
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
        
        id = result;
        
        return result;
    }
    
    public boolean existsInDatabase()
    {
        return (getID() > 0);
    }
    
    public abstract PreparedStatement getIdStatement() throws SQLException;
}