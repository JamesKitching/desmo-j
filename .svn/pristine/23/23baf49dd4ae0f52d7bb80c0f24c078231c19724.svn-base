package desmoj.extensions.db.dao;


import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.TimeSeriesValuePair;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Vector;


public class TimeSeriesValueDAO extends MultipleDatasetDAO<TimeSeriesValuePair> implements SavableDAO<TimeSeriesValuePair>
{
    public TimeSeriesValueDAO(DBConnection dbConnection)
    {
        super(dbConnection);
    }

    public TimeSeriesValueDAO(DBConnection dbConnection, TimeSeriesDAO timeseriesDAO)
    {
        super(dbConnection, timeseriesDAO);
    }

    public PreparedStatement getSaveStatement(TimeSeriesValuePair valuePair) throws SQLException
    {
        String sql = "INSERT INTO oc_timeseries_value (simtime, value, oc_timeseries_fk) VALUES (?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setDouble(1, valuePair.getSimTime());
        stmnt.setDouble(2, valuePair.getValue());
        stmnt.setInt(3, parentDAO.getID());
        return null;
    }
    
    public void save(Vector simTimeValues, Vector values)
    {
        for(int i = 0; i < simTimeValues.size(); i++)
        {
            super.save(new TimeSeriesValuePair((Double)simTimeValues.get(i), (Double)values.get(i)));
        }

    }
}
