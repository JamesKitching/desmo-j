package desmoj.extensions.db.dao;

import desmoj.core.report.TraceNote;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBTraceDAO extends SingleDatasetDAO<TraceNote> implements SavableDAO<TraceNote>
{
    public DBTraceDAO(DBConnection dbConnection, ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }


    public PreparedStatement getSaveStatement(TraceNote tn) throws SQLException
    {
        String sql = "INSERT INTO OC_TRACE (time, entity, event, description, experiment_fk) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);

        stmnt.setString(1, tn.getTime());
        stmnt.setString(2, tn.getEntity());
        stmnt.setString(3, tn.getEvent());
        stmnt.setString(4, tn.getDescription());
        stmnt.setInt(5, parentDAO.getID());

        return stmnt;
    }


}
