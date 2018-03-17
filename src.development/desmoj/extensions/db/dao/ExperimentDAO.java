package desmoj.extensions.db.dao;


import desmoj.core.simulator.Condition;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ModelCondition;
import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.util.ExperimentData;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class ExperimentDAO extends NamedSingleDatasetDAO<ExperimentData> implements SavableDAO<ExperimentData>, LoadableDAO<ExperimentData>
{
    private Model model;
    
    public ExperimentDAO(DBConnection connection, String name, ModelDAO modelDAO, Model model)
    {
        super(connection, name, modelDAO);
        
        this.model = model;
    }

    public PreparedStatement getIdStatement() throws SQLException
    {
        String sql = "SELECT id FROM experiment WHERE name = ? AND model_fk = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getSaveStatement(ExperimentData experimentData) throws SQLException
    {
        String sql = "INSERT INTO experiment (name, description, simdate, stoptime, stopcond, model_fk) VALUES (?, ?, CURRENT_DATE, ?, ?, ?)";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        //stmnt.setString(2, experiment.description());
        stmnt.setString(2, experimentData.getDescription());
        //stmnt.setNull(3, Types.DOUBLE);
        //stmnt.setDouble(3, experiment.getStopTime().getTimeValue());
        try
        {
            stmnt.setDouble(3, experimentData.getStopTime());
        }
        catch (Exception e)
        {
            stmnt.setNull(3, Types.DOUBLE);
        }
        
        if (experimentData.getStopCondition() != null)
        {
            stmnt.setString(4, experimentData.getStopCondition().getName());
        }
        else
        {
            stmnt.setNull(4, Types.VARCHAR);
        }
        
        stmnt.setInt(5, parentDAO.getID());
        
        return stmnt;
    }

    public PreparedStatement getLoadStatement() throws SQLException
    {
        String sql = "SELECT description, simdate, stoptime, stopcond FROM experiment WHERE name = ? AND model_fk = ?";
        
        PreparedStatement stmnt = connection.createPreparedStatement(sql);
        stmnt.setString(1, name);
        stmnt.setInt(2, parentDAO.getID());
        
        return stmnt;
    }

    public ExperimentData toResult(ResultSet resultSet) throws SQLException
    {
        String desc = resultSet.getString("DESCRIPTION");
        Double sptm = resultSet.getDouble("STOPTIME");
        String cdnm = resultSet.getString("STOPCOND");
        ModelCondition cond = null;

        if (cdnm != null)
        {
            ModelConditionDAO condDAO = new ModelConditionDAO(connection, cdnm, this, model);
            cond = condDAO.load();
        }
        
        if (sptm == null)
            return new ExperimentData(name, desc, cond);
        else
            return new ExperimentData(name, desc, sptm, cond);
    }
    
    @Override public void save(ExperimentData experimentData)
    {
        super.save(experimentData);
        
        ModelCondition cond = experimentData.getStopCondition();
        
        if (cond != null)
        {
            ModelConditionDAO condDAO = new ModelConditionDAO(connection, cond.getName(), this, model);          
            condDAO.save(cond);
        }
    }
}
