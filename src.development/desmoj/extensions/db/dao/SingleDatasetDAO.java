package desmoj.extensions.db.dao;


import desmoj.extensions.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class SingleDatasetDAO<T> extends DAO<T>
{
    public SingleDatasetDAO(DBConnection connection, IDDAO parentDAO)
    {
        super(connection, parentDAO);
    }
    
    public SingleDatasetDAO(DBConnection connection)
    {
        super(connection);
    }
}

