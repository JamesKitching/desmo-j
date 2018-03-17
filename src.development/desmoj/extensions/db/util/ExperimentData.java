package desmoj.extensions.db.util;

import desmoj.core.simulator.ModelCondition;
import desmoj.core.simulator.Experiment;

public class ExperimentData
{
    private String name;
    private String description;
    private boolean hasStopTime;
    private Double stopTime;
    private ModelCondition stopCondition;
    
    public ExperimentData(String name, String description, ModelCondition stopCondition)
    {
        this.name = name;
        this.description = description;
        this.stopCondition = stopCondition;
        this.hasStopTime = false;
    }
    
    public ExperimentData(String name, String description, double stopTime, ModelCondition stopCondition)
    {
        this(name, description, stopCondition);
        
        this.hasStopTime = true;
        this.stopTime = stopTime;
    }
    
    public ExperimentData(Experiment exp)
    {
        this.name = exp.getName();
        this.description = exp.getDescription();
        this.hasStopTime = !(exp.getStopTime() == null);
        if (hasStopTime)
            this.stopTime = exp.getStopTime().getTimeAsDouble();
        this.stopCondition = exp.getStopConditions().get(0);
        // %TODO: Only the first stop condition is stored
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public boolean hasStoptime()
    {
        return hasStopTime;        
    }
    
    public double getStopTime() throws Exception
    {
        if (!hasStopTime)
            throw new Exception("no stoptime");
        return stopTime;
    }
    
    public ModelCondition getStopCondition()
    {
        return stopCondition;
    }
}
