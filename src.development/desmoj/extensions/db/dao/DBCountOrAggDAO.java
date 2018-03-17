package desmoj.extensions.db.dao;


import desmoj.core.simulator.Reportable;

import desmoj.core.statistic.Aggregate;
import desmoj.core.statistic.Count;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCountOrAggDAO extends SingleDatasetDAO<Reportable> implements SavableDAO<Reportable>
{
    public DBCountOrAggDAO(DBConnection dbConnection, ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }


    public PreparedStatement getSaveStatement(Reportable r) throws SQLException
    {
        String sql = "INSERT INTO OC_REPORT_COUNT_AGG (TITLE, TYPE, RESET, OBS, CURRENT_VALUE, MIN, MAX, EXPERIMENT_FK) " 
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, r.getName());
        stmnt.setDouble(3, r.resetAt().getTimeAsDouble());
        stmnt.setLong(4, r.getObservations());
        stmnt.setInt(8, parentDAO.getID());
        if (r instanceof Count)
        {
            Count c = (Count)r;
            stmnt.setString(2, "Count");
            stmnt.setLong(5, c.getValue());
            stmnt.setLong(6, c.getMinimum());
            stmnt.setLong(7, c.getMaximum());
        }
        else
        {
            Aggregate a = (Aggregate)r;
            stmnt.setString(2, "Aggregate");
            stmnt.setDouble(5, a.getValue());
            stmnt.setDouble(6, a.getMinimum());
            stmnt.setDouble(7, a.getMaximum());
        }
 
        return stmnt;        
    }
}