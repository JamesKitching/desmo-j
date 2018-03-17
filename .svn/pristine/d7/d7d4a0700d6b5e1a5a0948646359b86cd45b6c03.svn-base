package desmoj.extensions.db;

import java.sql.SQLException;
import java.util.List;

import desmoj.core.dist.BoolDistBernoulli;
import desmoj.core.dist.BoolDistConstant;
import desmoj.core.dist.ContDistEmpirical;
import desmoj.core.dist.ContDistErlang;
import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.dist.DiscreteDistConstant;
import desmoj.core.dist.Distribution;
import desmoj.core.dist.IntDistConstant;
import desmoj.core.dist.IntDistEmpirical;
import desmoj.core.dist.IntDistPoisson;
import desmoj.core.dist.IntDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.QueueBased;
import desmoj.core.simulator.Reportable;
import desmoj.core.statistic.Accumulate;
import desmoj.core.statistic.Aggregate;
import desmoj.core.statistic.Count;
import desmoj.core.statistic.Tally;
import desmoj.core.statistic.TimeSeries;
import desmoj.extensions.db.dao.DBAccumulateDAO;
import desmoj.extensions.db.dao.DBCountOrAggDAO;
import desmoj.extensions.db.dao.DBDistBooleanDAO;
import desmoj.extensions.db.dao.DBDistNumberDAO;
import desmoj.extensions.db.dao.DBDistParaDAO;
import desmoj.extensions.db.dao.DBDistributionDAO;
import desmoj.extensions.db.dao.DBQueueDAO;
import desmoj.extensions.db.dao.DBTallyDAO;
import desmoj.extensions.db.dao.ExperimentDAO;
import desmoj.extensions.db.dao.TimeSeriesDAO;
import desmoj.extensions.db.dao.TimeSeriesValueDAO;

/**
 * Use this class to persist all Reportable objects into your database.
 *
 * @author S�ren Linke, Tim Janz, Niklas Diehl
 */
public class DBReportableOutput
{

    List<Reportable> reportables;
    DBConnection dbc;
    ExperimentDAO experimentDAO;
    boolean saveTimeSeries;

    /**
     * Create a new DBReportableOutput class *
     *
     * @param model
     *          Model : the simulation model
     * @param dbconnection
     *          DBConnection : the connection to the database
     * @param experimentDAO
     *          desmoj.extensions.db.dao.ExperimentDAO : the experimentDAO
     */
    public DBReportableOutput(Model model, DBConnection dbconnection, desmoj.extensions.db.dao.ExperimentDAO experimentDAO) throws SQLException
    {
        reportables = model.getReportables();
        dbc = dbconnection;
        this.experimentDAO = experimentDAO;
        this.saveTimeSeries = true;

        start();
    }

    /**
     * Create a new DBReportableOutput class.
     *
     * @param model
     *          Model : the simulation model
     * @param dbconnection
     *          DBConnection : the connection to the database
     * @param experimentDAO
     *          ExperimentDAO : the Experiment-DAO
     * @param saveTimeSeries
     *          boolean : TimeSeries are saved if "true"
     */
    public DBReportableOutput(Model model, DBConnection dbconnection, ExperimentDAO experimentDAO, boolean saveTimeSeries) throws SQLException
    {
        reportables = model.getReportables();
        dbc = dbconnection;
        this.experimentDAO = experimentDAO;
        this.saveTimeSeries = saveTimeSeries;

        start();
    }

    /**
     * This method is called only once when the class is created. It runs through
     * all the model's reportables, creates adequate DAOs and uses them to save
     * the reportable's information into a database.
     * List of savable reportables: Accumulate, Aggregate, Count, Distribution,
     * Tally, TimeSeries, QueueBased.
     * 
     * @throws SQLException
     *          A SQLException is thrown if there is a problem with the database
     *          or the connection to the database
     */
    private void start() 
    {
        for (int i = 0; i < reportables.size(); i++)
        {
            Reportable reportable = (Reportable)reportables.get(i);
            if (reportable.reportIsOn())
            {
                if (reportable instanceof QueueBased)
                {
                    DBQueueDAO queueDAO = new DBQueueDAO(dbc, experimentDAO);
                    queueDAO.save((QueueBased)reportable);
                }
                else if (reportable instanceof Accumulate)
                {
                    DBAccumulateDAO accumulateDAO = new DBAccumulateDAO(dbc, experimentDAO);
                    accumulateDAO.save((Accumulate)reportable);
                }
                else if (reportable instanceof Count)
                {
                    DBCountOrAggDAO countOrAggDAO = new DBCountOrAggDAO(dbc, experimentDAO);
                    countOrAggDAO.save(reportable);
                }
                else if (reportable instanceof Aggregate)
                {
                    DBCountOrAggDAO countOrAggDAO = new DBCountOrAggDAO(dbc, experimentDAO);
                    countOrAggDAO.save(reportable);
                }
                else if (reportable instanceof Tally)
                {
                    DBTallyDAO tallyDAO = new DBTallyDAO(dbc, experimentDAO);
                    tallyDAO.save((Tally)reportable);
                }
                else if (reportable instanceof Distribution)
                {
                    saveDistribution((Distribution)reportable);
                }
                else if (reportable instanceof TimeSeries) // && saveTimeSeries)
                {
                    //TODO
                    TimeSeriesDAO timeSeriesDAO = new TimeSeriesDAO(dbc, reportable.getName(), experimentDAO);
                    timeSeriesDAO.save((TimeSeries)reportable);
                    TimeSeriesValueDAO timeSeriesValueDAO = new TimeSeriesValueDAO(dbc, timeSeriesDAO);
                    timeSeriesValueDAO.save(((TimeSeries)reportable).getTimeValues(), ((TimeSeries)reportable).getDataValues());
                }
                else
                {
                    //TODO
                    System.out.println("Unknown Reportable (maybe histogram?); no capture in database");
                }
            }
        }
    }

    /**
     * This method provides a more sophisticated treatment for distribution-
     * reportables. An adequate DAO gets created to save the reportable's data.
     * The method handles BoolDistBernoulli, BoolDistConstant, IntDistConstant,
     * IntDistEmpirical, IntDistPoisson, IntDistUniform, RealDistConstant,
     * RealDistEmpirical, RealDistErlang, RealDistExponential, RealDistNormal,
     * RealDistUniform.
     * 
     * @param d
     *          The distribution that is to be handled according to it's type
     */
    private void saveDistribution(Distribution d)
    {
            if (d instanceof BoolDistBernoulli)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Bernoulli");
                distributionDAO.save(d);
                DBDistParaDAO distParaDAO = new DBDistParaDAO(dbc, "Probability", distributionDAO);
                distParaDAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumberDAO = new DBDistNumberDAO(dbc, distParaDAO);
                distNumberDAO.save(((BoolDistBernoulli)d).getProbability());
            }
            else if (d instanceof BoolDistConstant)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Bool. Constant");
                distributionDAO.save(d);
                DBDistParaDAO distParaDAO = new DBDistParaDAO(dbc, "Constant Value", distributionDAO);
                distParaDAO.save("OC_DIST_BOOLEAN");
                DBDistBooleanDAO distBooleanDAO = new DBDistBooleanDAO(dbc, distParaDAO);
                distBooleanDAO.save(((BoolDistConstant)d).getConstantValue());
            }
            else if (d instanceof IntDistConstant)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Integer Constant");
                distributionDAO.save(d);
                DBDistParaDAO distParaDAO = new DBDistParaDAO(dbc, "Constant Value", distributionDAO);
                distParaDAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumberDAO = new DBDistNumberDAO(dbc, distParaDAO);
                distNumberDAO.save(((IntDistConstant)d).getConstantValue());
            }
            else if (d instanceof IntDistEmpirical)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Integer Empirical");
                distributionDAO.save(d);
            }
            else if (d instanceof IntDistPoisson)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Poisson");
                distributionDAO.save(d);
                DBDistParaDAO distParaDAO = new DBDistParaDAO(dbc, "Mean Value", distributionDAO);
                distParaDAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumberDAO = new DBDistNumberDAO(dbc, distParaDAO);
                distNumberDAO.save(((IntDistPoisson)d).getMean());
            }
            else if (d instanceof IntDistUniform)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Integer Uniform");
                distributionDAO.save(d);
                DBDistParaDAO distPara1DAO = new DBDistParaDAO(dbc, "Lower Border", distributionDAO);
                distPara1DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber1DAO = new DBDistNumberDAO(dbc, distPara1DAO);
                distNumber1DAO.save(((IntDistUniform)d).getLower());
                DBDistParaDAO distPara2DAO = new DBDistParaDAO(dbc, "Upper Border", distributionDAO);
                distPara2DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber2DAO = new DBDistNumberDAO(dbc, distPara2DAO);
                distNumber2DAO.save(((IntDistUniform)d).getUpper());
            }
            else if (d instanceof DiscreteDistConstant)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Real Constant");
                distributionDAO.save(d);
                DBDistParaDAO distParaDAO = new DBDistParaDAO(dbc, "Constant Value", distributionDAO);
                distParaDAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumberDAO = new DBDistNumberDAO(dbc, distParaDAO);
                distNumberDAO.save(((DiscreteDistConstant)d).getConstantValue());
            }
            else if (d instanceof ContDistEmpirical)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Real Empirical");
                distributionDAO.save(d);
            }
            else if (d instanceof ContDistErlang)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Erlang");
                distributionDAO.save(d);
                DBDistParaDAO distPara1DAO = new DBDistParaDAO(dbc, "Order", distributionDAO);
                distPara1DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber1DAO = new DBDistNumberDAO(dbc, distPara1DAO);
                distNumber1DAO.save(((ContDistErlang)d).getOrder());
                DBDistParaDAO distPara2DAO = new DBDistParaDAO(dbc, "Mean Value", distributionDAO);
                distPara2DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber2DAO = new DBDistNumberDAO(dbc, distPara2DAO);
                distNumber2DAO.save(((ContDistErlang)d).getMean());
            }
            else if (d instanceof ContDistExponential)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Negative Exponential");
                distributionDAO.save(d);
                DBDistParaDAO distParaDAO = new DBDistParaDAO(dbc, "Mean Value", distributionDAO);
                distParaDAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumberDAO = new DBDistNumberDAO(dbc, distParaDAO);
                distNumberDAO.save(((ContDistExponential)d).getMean());
            }
            else if (d instanceof ContDistNormal)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Normal");
                distributionDAO.save(d);
                DBDistParaDAO distPara1DAO = new DBDistParaDAO(dbc, "Standard Deviation", distributionDAO);
                distPara1DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber1DAO = new DBDistNumberDAO(dbc, distPara1DAO);
                distNumber1DAO.save(((ContDistNormal)d).getStdDev());
                DBDistParaDAO distPara2DAO = new DBDistParaDAO(dbc, "Mean Value", distributionDAO);
                distPara2DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber2DAO = new DBDistNumberDAO(dbc, distPara2DAO);
                distNumber2DAO.save(((ContDistNormal)d).getMean());
            }
            else if (d instanceof ContDistUniform)
            {
                DBDistributionDAO distributionDAO = new DBDistributionDAO(dbc, d.getName(), experimentDAO, "Real Uniform");
                distributionDAO.save(d);
                DBDistParaDAO distPara1DAO = new DBDistParaDAO(dbc, "Lower Border", distributionDAO);
                distPara1DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber1DAO = new DBDistNumberDAO(dbc, distPara1DAO);
                distNumber1DAO.save(((ContDistUniform)d).getLower());
                DBDistParaDAO distPara2DAO = new DBDistParaDAO(dbc, "Upper Border", distributionDAO);
                distPara2DAO.save("OC_DIST_NUMBER");
                DBDistNumberDAO distNumber2DAO = new DBDistNumberDAO(dbc, distPara2DAO);
                distNumber2DAO.save(((ContDistUniform)d).getUpper());
            }
    } 

}
