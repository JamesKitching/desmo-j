package desmoj.extensions.db.dao;


import desmoj.core.simulator.SimTime;
import desmoj.core.simulator.TimeOperations;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.Accumulate;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DBAccumulateDAO extends SingleDatasetDAO<Accumulate> implements SavableDAO<Accumulate>
{
    public DBAccumulateDAO(DBConnection dbConnection,
                           ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }


    public PreparedStatement getSaveStatement(Accumulate a) throws SQLException
    {
        String sql =
            "INSERT INTO OC_REPORT_ACCU (TITLE, RESET, OBS, MEAN, STD_DEV, MIN, MAX, EXPERIMENT_FK) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, a.getName());
        stmnt.setDouble(2, a.resetAt().getTimeAsDouble());
        stmnt.setLong(3, a.getObservations());
        stmnt.setDouble(6, a.getMinimum());
        stmnt.setDouble(7, a.getMaximum());
        stmnt.setInt(8, parentDAO.getID());
        // Mean
        // has no time passed since the last reset?
        if (TimeSpan.isEqual(TimeOperations.diff(a.presentTime(), a.resetAt()), new TimeSpan(0) || a.getObservations() == 0) 
        // OR no observations are made
        {
            stmnt.setNull(4, Types.NULL);
        }
        else // return mean value
        {
            stmnt.setDouble(4, a.getMean());
        }
        // Std.Dev
        // has no time passed since the last reset?
        if (TimeSpan.isEqual(TimeOperations.diff(a.presentTime(), a.resetAt()), new TimeSpan(0)) || a.getObservations() < 2) 
        // OR not enough observations are made
        {
            stmnt.setNull(5, Types.NULL);
        } else // return mean value
        {
            stmnt.setDouble(5, a.getStdDev());
        }

        return stmnt;
    }
}
