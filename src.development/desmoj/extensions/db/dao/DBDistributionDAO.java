package desmoj.extensions.db.dao;

import desmoj.core.dist.Distribution;
import desmoj.extensions.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDistributionDAO extends NamedSingleDatasetDAO<Distribution> implements SavableDAO<Distribution>
{

    String distributionType;

    public DBDistributionDAO(DBConnection dbConnection, String name, ExperimentDAO experimentDAO, String distributionType)
    {
        super(dbConnection, name, experimentDAO);
        this.distributionType = distributionType;
    }

    public PreparedStatement getSaveStatement(Distribution d) throws SQLException
    {
        String sql =
            "INSERT INTO OC_REPORT_DIST (TITLE, RESET, OBS, TYPE, SEED, EXPERIMENT_FK) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, d.getName());
        stmnt.setDouble(2, d.resetAt().getTimeAsDouble());
        stmnt.setLong(3, d.getObservations());
        stmnt.setString(4, distributionType);
        stmnt.setLong(5, d.getInitialSeed());
        stmnt.setInt(6, parentDAO.getID());

        return stmnt;
    }

    public PreparedStatement getIdStatement() throws SQLException
    {
        String sql = "SELECT ID FROM OC_REPORT_DIST WHERE TITLE = ? AND EXPERIMENT_FK = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());

        return stmnt;        
    }
}
