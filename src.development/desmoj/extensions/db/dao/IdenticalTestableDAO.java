package desmoj.extensions.db.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface IdenticalTestableDAO<T>
{
    public PreparedStatement getIdenticalStatement(T object) throws SQLException;
    
    public boolean getTestResult(ResultSet resultSet, T object) throws SQLException;
}