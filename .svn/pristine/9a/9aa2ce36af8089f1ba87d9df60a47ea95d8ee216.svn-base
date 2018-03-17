package desmoj.extensions.db.dao;

import desmoj.core.dist.Distribution;
import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.util.DistributionData;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DistributionInitialSeedDAO extends MultipleDatasetDAO<DistributionData> implements SavableDAO<DistributionData>, LoadableDAO<DistributionData>
{
    private DistributionDAO distributionDAO;
    
    public DistributionInitialSeedDAO(DBConnection connection, ExperimentDAO experimentDAO, DistributionDAO distributionDAO)
    {
        super(connection, experimentDAO);
        
        this.distributionDAO = distributionDAO;
    }
    
    public PreparedStatement getSaveStatement(DistributionData distribution) throws SQLException
    {
        String sql = "INSERT INTO initial_seed (seed, distribution_fk, experiment_fk) VALUES (?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setLong(1, distribution.getInitialSeed());
        stmnt.setInt(2, distributionDAO.getID(distribution.getName()));
        stmnt.setInt(3, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getLoadStatement() throws SQLException
    {
        String sql = "SELECT d.name, i.seed FROM distribution d, initial_seed i WHERE d.id = i.distribution_fk AND i.experiment_fk = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setInt(1, parentDAO.getID());
        
        return stmnt;
    }

    public DistributionData toResult(ResultSet resultSet) throws SQLException
    {
        String name = resultSet.getString("NAME");
        long seed = resultSet.getLong("SEED");
        
        DistributionData result = new DistributionData(name, seed);
        return result;
    }
}

