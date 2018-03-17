package desmoj.extensions.db.util;

import desmoj.core.report.ErrorMessage;
import desmoj.core.simulator.Condition;
import desmoj.core.simulator.Model;
import desmoj.extensions.db.exception.DESMOJDBException;


import java.lang.reflect.Constructor;

@Deprecated abstract class DBCondition extends Condition
{
    private Object[] arguments = null;
    private boolean argumentsPrimitive = true;
    private boolean constructorExists = true;
    
    public DBCondition(Model owner, String name, boolean showInTrace, Object... args)
    {
        super(owner, name, showInTrace);
        
        System.out.println(this.getClass().getName());

        Class[] paramTypes = new Class[args.length + 3];
        paramTypes[0] = Model.class;
        paramTypes[1] = String.class;
        paramTypes[2] = Boolean.TYPE;
        
        for (int i = 0; i < args.length; i++)
        {
            Class type = getType(args[i]);
            argumentsPrimitive &= (type != null);
            paramTypes[i + 3] = type;
            
            System.out.println(type + ": " + args[i]);
        }

        try
        {
            this.getClass().getConstructor(paramTypes);
            
            arguments = new Object[args.length + 3];
            arguments[0] = owner;
            arguments[1] = name;
            arguments[2] = showInTrace;
            
            for (int i = 0; i < args.length; i++)
            {
                arguments[i + 3] = args[i];
            }
        }
        catch (NoSuchMethodException e)
        {
            constructorExists = false;
            System.out.println("Constructor not found.");
        }
    }    
    
    public Object[] getConstructArguments() throws DESMOJDBException
    {
        if (!argumentsPrimitive)
        {
            throw new DESMOJDBException(new ErrorMessage(this.getModel(), "", "", "", "", null));
        }
        else if (!constructorExists)
        {
            throw new DESMOJDBException(new ErrorMessage(this.getModel(), "", "", "", "", null));
        }
        else
        {
            return arguments;
        }
    }
    
    private Class getType(Object obj)
    {
        Class cls = obj.getClass();
        
        return cls.equals(Boolean.class) ? ((Boolean)obj).TYPE :
            cls.equals(Integer.class) ? ((Integer)obj).TYPE :
            cls.equals(Character.class) ? ((Character)obj).TYPE :
            cls.equals(Byte.class) ? ((Byte)obj).TYPE :
            cls.equals(Short.class) ? ((Short)obj).TYPE :
            cls.equals(Double.class) ? ((Double)obj).TYPE :
            cls.equals(Long.class) ? ((Long)obj).TYPE :
            cls.equals(Float.class) ? ((Float)obj).TYPE :
            cls.equals(String.class) ? String.class :
            null;
    }
}
