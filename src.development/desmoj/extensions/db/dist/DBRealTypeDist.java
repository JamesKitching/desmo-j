package desmoj.extensions.db.dist;

import desmoj.core.report.Reporter;
import desmoj.core.simulator.Model;

import java.util.Vector;

public class DBRealTypeDist extends DBDataDistribution
{
    protected Vector<Double> values;
    protected int index;

    public DBRealTypeDist(Model owner, String name, boolean showInReport, boolean showInTrace, Vector<Double> values)
    {
        super(owner, name, showInReport, showInTrace);
        this.values = values;
        index = 0;
    }

    public DBRealTypeDist(Model owner, String name, boolean showInReport, boolean showInTrace, Vector<Double> values,
                          int index)
    {
        super(owner, name, showInReport, showInTrace);
        this.values = values;
        this.index = index;
    }

    /**
     * Creates the default reporter for the RealDistNormal distribution.
     *
     * @return Reporter : The reporter for the RealDistNormal distribution
     * @see desmoj.core.report.ContDistNormalReporter
     */
    //TODO: Reporter Klasse schreiben
    //public Reporter createReporter()
    //{
    //  return new DBRealTypeDist(this);
    //}

    public double sample()
    {
        incrementObservations(); // increase the number of Samples given to the client

        if (index > values.size())
        {
            index = 0;
        }
        else
        {
            index++;
        }
        Double newSample = values.get(index);

        return newSample;
    }
}
