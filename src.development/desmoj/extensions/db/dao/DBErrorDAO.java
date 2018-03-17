package desmoj.extensions.db.dao;

import desmoj.core.report.ErrorMessage;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBErrorDAO extends SingleDatasetDAO<ErrorMessage> implements SavableDAO<ErrorMessage>
{
    public DBErrorDAO(DBConnection dbConnection, ExperimentDAO experimentDAO)
    {
        super(dbConnection, experimentDAO);
    }


    public PreparedStatement getSaveStatement(ErrorMessage errmsg) throws SQLException
    {
        String sql =
            "INSERT INTO OC_ERROR (TIME, LOCATION, REASON, PREVENTION, DESCRIPTION, EXPERIMENT_FK) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement sqlstmnt = connection.createPreparedStatement(sql);
        sqlstmnt.setString(1, errmsg.getTime());
        sqlstmnt.setString(2, errmsg.getLocation());
        sqlstmnt.setString(3, errmsg.getReason());
        sqlstmnt.setString(4, errmsg.getPrevention());
        sqlstmnt.setString(5, errmsg.getDescription());
        sqlstmnt.setInt(6, parentDAO.getID());

        return sqlstmnt;
    }
}
