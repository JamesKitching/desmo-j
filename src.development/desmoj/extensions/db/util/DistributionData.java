package desmoj.extensions.db.util;


public class DistributionData
{
    private String name;
    private long seed;
    
    public DistributionData(String name, long seed)
    {
        this.name = name;
        this.seed = seed;
    }
    
    public String getName()
    {
        return name;
    }
    
    public long getInitialSeed()
    {
        return seed;
    }
    
    public String toString()
    {
        return name + ": " + seed;
    }
}
