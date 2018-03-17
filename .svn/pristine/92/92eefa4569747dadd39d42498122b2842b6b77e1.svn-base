package desmoj.extensions.db.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface LoadableDAO<T>
{
    public PreparedStatement getLoadStatement() throws SQLException;
    
    public T toResult(ResultSet resultSet) throws SQLException;
}