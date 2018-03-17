package desmoj.extensions.db;


import desmoj.core.report.Message;
import desmoj.core.report.Reporter;
import desmoj.core.report.TraceNote;

import desmoj.core.simulator.SimTime;
import desmoj.extensions.db.dao.DBTraceDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * @javadoc DBTraceOutput is used to write the experiment's trace into a database. 
 * It implements a MessageReceiver, receives messages and sends them into a database. 
 *
 * @author Soeren Linke, Tim Janz, Niklas Diehl
 */

public class DBTraceOutput implements desmoj.core.report.MessageReceiver
{

    private DBConnection dbc;
    private DBTraceDAO traceDAO;

    /**
     * Create a new DBTraceOutput class.
     *
     * @param dbconnection
     *          DBConnection : the connection to the database
     * @param traceDAO
     *          DBTraceDAO : the Trace-DAO which handles TraceNotes
     * 
     */
    public DBTraceOutput(DBConnection dbconnection, DBTraceDAO traceDAO)
    {
        super();
        dbc = dbconnection;
        this.traceDAO = traceDAO;
    }

    /**
     * @javadoc This Method receives a message. If this message is a tracenote,
     *          it saves the tracenote's time, entity, event and description
     *          plus the foreign key of the experiment the trace belongs to into the database.
     *
     * @param m
     *          Message: The message to be split and sent to database.
     */
    public void receive(Message m)
    {
        TraceNote tmp;

        if (m == null)
            return; // again nulls
        if (!(m instanceof TraceNote))
            return; // got wrong message
        
        tmp = (TraceNote)m; 
        traceDAO.save(tmp);
    }

    /**
     * @javadoc No need to do anything because no reporters are used for trace.
     *
     * @param r             
     */
    public void receive(Reporter r)
    {
        return;
    }
}

