package desmoj.extensions.db.dao;

import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.QueueBased;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQueueDAO extends SingleDatasetDAO<QueueBased> implements SavableDAO<QueueBased>
{
    public DBQueueDAO(DBConnection dbConnection, ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }


    public PreparedStatement getSaveStatement(QueueBased q) throws SQLException
    {
        String sql = "INSERT INTO OC_REPORT_QUEUE (TITLE, QORDER, RESET, OBS, QLIMIT, QMAX, QNOW, QAVG_, ZEROS, AVG_WAIT, REFUS_, EXPERIMENT_FK) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, q.getName());
        stmnt.setDouble(3, q.resetAt().getTimeAsDouble());
        stmnt.setLong(4, q.getObservations());
        stmnt.setInt(5, q.getQueueLimit());
        stmnt.setInt(6, q.maxLength());
        stmnt.setInt(7, q.length());
        stmnt.setDouble(8, q.averageLength());
        stmnt.setLong(9, q.zeroWaits());
        stmnt.setDouble(10, q.averageWaitTime().getTimeAsDouble());
        stmnt.setInt(12, parentDAO.getID());
        if (q instanceof Queue)
        {
            stmnt.setString(2, ((Queue)q).getQueueStrategy());
            stmnt.setLong(11, ((Queue)q).getRefused());
        }
        else if (q instanceof ProcessQueue)
        {
            stmnt.setString(2, ((ProcessQueue)q).getQueueStrategy());
            stmnt.setLong(11, ((ProcessQueue)q).getRefused());
        }
        
        return stmnt;        
    }
}
