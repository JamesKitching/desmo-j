package desmoj.extensions.db;

import java.sql.*;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

/**
 * DBConnection contains the informations needed (like username and password) 
 * to establish a connection with the database. This class also offers methods
 * for creating SQL-statements.
 * 
 * @author Soeren Linke, Tim Janz, Niklas Diehl 
 */

public class DBConnection
{
    private String url;
    private String user;
    private String pass;
    
    private OracleDataSource datasource;
    private Connection connection;
    
    /**
     * Constructor DBConnection.
     * 
     * @param url
     *          String : adress to reach the database
     * @param user
     *          String : username for database log in
     * @param pass
     *          String : password for database log in
     *          
     */
    public DBConnection(String url, String user, String pass)
    {
        super();
        
        this.url = url;
        this.user = user;
        this.pass = pass;
        
        
        try
        {
            init();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    
    }
    
    /**
     * Initializes the DBConnection. Creates a datasource and a related connection.
     * 
     * @throws SQLException
     */
    public void init() throws SQLException
    {
        datasource = new OracleDataSource();
        datasource.setURL(url);
        
        connection = datasource.getConnection(user, pass);        
    }
    
    /**
     * Creates and returns a Statement.
     * 
     * @return Statement
     * 
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException
    {
        return connection.createStatement();
    }
    
    /**
     * Creates and returns a Prepardedstatement.
     * 
     * @return PreparedStatement
     * 
     * @throws SQLException
     */
    public PreparedStatement createPreparedStatement(String sql) throws SQLException
    {
        return connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }
    
    /**
     * Delays the committing of queries. They get committed once the transaction ends. 
     */
    public void startTransaction()
    {
        if (hasConnection())
        {
            try
            {
                connection.setAutoCommit(false);
            }
            catch (SQLException e)
            {
                System.out.println("autocommit off failure");
                //TODO exception
            }
        }
    }
    
    /**
     * End the transaction and commits all queued queries.
     */
    public void endTransaction()
    {
        if (hasConnection())
        {
            try
            {
                connection.commit();
                connection.setAutoCommit(true);
            }
            catch (SQLException e)
            {
                System.out.println("autocommit on failure");
            }
        }
        
    }
    
    /**
     * Terminates the datasource and its connection.
     * 
     * @throws SQLException
     */
    public void finalize() throws SQLException
    {
        if (hasConnection())
        {
            connection.close();
        }
        
        datasource.close();
    }
    
    /**
     * Returns true if there is a connection.
     * 
     * @return boolean
     */
    private boolean hasConnection()
    {
        return connection != null;
    }
}
