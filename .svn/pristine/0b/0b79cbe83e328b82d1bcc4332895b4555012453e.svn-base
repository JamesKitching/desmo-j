package desmoj.extensions.db.dao;

import desmoj.core.statistic.TimeSeries;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeSeriesDAO extends NamedSingleDatasetDAO<TimeSeries> implements SavableDAO<TimeSeries>
{
    public TimeSeriesDAO(DBConnection dbConnection, String name)
    {
        super(dbConnection, name);
    }

    public TimeSeriesDAO(DBConnection dbConnection, String name, ExperimentDAO experimentDAO)
    {
        super(dbConnection, name, experimentDAO);
    }

    public PreparedStatement getIdStatement() throws SQLException
    {
        String sql = "SELECT id FORM oc_timeseries WHERE name = ? AND experiment_fk = ?";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getSaveStatement(TimeSeries timeSeries) throws SQLException
    {
        String sql = "INSERT INTO oc_timeseries (name, experiment_fk) VALUES (?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, timeSeries.getName());
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }
}
