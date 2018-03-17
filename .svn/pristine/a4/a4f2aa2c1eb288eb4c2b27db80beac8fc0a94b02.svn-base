package desmoj.extensions.db.dao;


import desmoj.core.statistic.Tally;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DBTallyDAO extends SingleDatasetDAO<Tally> implements SavableDAO<Tally>
{
    public DBTallyDAO(DBConnection dbConnection, ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }


    public PreparedStatement getSaveStatement(Tally t) throws SQLException
    {
        String sql = "INSERT INTO OC_REPORT_TALLY (TITLE, RESET, OBS, MEAN_TOT, STD_DV_TOT, MIN_TOT, MAX_TOT, MEAN_LST_N, STD_DV_LST_N, MIN_LST_N, MAX_LST_N, N, EXPERIMENT_FK) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, t.getName());
        stmnt.setDouble(2, t.resetAt().getTimeAsDouble());
        stmnt.setLong(3, t.getObservations());
        stmnt.setDouble(6, t.getMinimum());
        stmnt.setDouble(7, t.getMaximum());
        stmnt.setInt(13, parentDAO.getID());
        // no observations made, so Mean can not be calculated
        if (t.getObservations() == 0)
        {
            stmnt.setNull(4, Types.NULL);
        }
        else // return mean value
        {
            stmnt.setDouble(4, t.getMean());
        }
        // not enough observations are made, so Std.Dev can not be calculated
        if (t.getObservations() < 2)
        {
            stmnt.setNull(5, Types.NULL);
        }
        else // return standard deviation
        {
            stmnt.setDouble(5, t.getStdDev());
        }
        if (t instanceof desmoj.core.statistic.TallyRunning)
        {
            desmoj.core.statistic.TallyRunning tr = (desmoj.core.statistic.TallyRunning)t;
            stmnt.setDouble(10, tr.getMinimumLastN());
            stmnt.setDouble(11, tr.getMaximumLastN());
            stmnt.setInt(12, tr.getSampleSizeN());
            if (t.getObservations() == 0)
            {
                stmnt.setNull(8, Types.NULL);
            }
            else
            {
                stmnt.setDouble(8, tr.getMeanLastN());
            }
            // Std.Dev/Last n not enough observations are made, so Std.Dev can not be calculated
            if (t.getObservations() < 2)
            {
                stmnt.setNull(9, Types.NULL);
            }
            else // return standard deviation
            {
                stmnt.setDouble(9, tr.getStdDevLastN());
            }
        }
        else
        {
            stmnt.setNull(10, Types.NULL);
            stmnt.setNull(11, Types.NULL);
            stmnt.setNull(12, Types.NULL);
        }
        
        return stmnt;        
    }
}
