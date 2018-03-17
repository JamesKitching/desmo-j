package desmoj.extensions.db.dao;


import desmoj.extensions.db.DBConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class MultipleDatasetDAO<T> extends DAO<T>
{
    public MultipleDatasetDAO(DBConnection connection, NamedSingleDatasetDAO parentDAO)
    {
        super(connection, parentDAO);
    }
    
    public MultipleDatasetDAO(DBConnection connection)
    {
        super(connection);
    }
    
    public void saveAll(Collection<T> objectCollection)
    {
        for (T object : objectCollection)
        {
            super.save(object);
        }
    }
    
    public Collection<T> loadAll()
    {
        Collection<T> result = new ArrayList<T>();
        
        do
        {
            result.add(super.load());
        }
        while (loadResultsLeft);
        
        return result;
    }
    
    public boolean identicalToDatabase(List<T> objectList)
    {
        // TODO: check in Database
        if (objectList == null || objectList.isEmpty())
            return true;
        
        int i = 0;
        boolean result = super.identicalToDatabase(objectList.get(i));

        do
        {
            i++;
            
            if (i < objectList.size())
            {
                result &= super.identicalToDatabase(objectList.get(i));
            }
            else
            {
                result = false;
                break;
            }
        }
        while (testResultsLeft);
        
        return result;
    }
}