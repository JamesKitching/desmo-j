package desmoj.extensions.db;

import desmoj.core.report.ErrorMessage;
import desmoj.core.report.Message;
import desmoj.core.report.MessageReceiver;
import desmoj.core.report.Reporter;

import desmoj.core.report.TraceNote;
import desmoj.extensions.db.dao.DBErrorDAO;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Use this class to persist Simulation Error Output into your database.
 *
 * @author Soeren Linke, Tim Janz, Niklas Diehl
 */
public class DBErrorOutput implements MessageReceiver
{
    private DBConnection dbc;
    private DBErrorDAO errorDAO;

    /**
     * Create a new DBErrorOutput class.
     *
     * @param dbconnection
     *          DBConnection : the connection to the database
     * @param errorDAO
     *          DBErrorDAO : the Error-DAO which handles ErrorMessages
     * 
     */
    public DBErrorOutput(DBConnection dbconnection, DBErrorDAO errorDAO)
    {
        super();
        dbc = dbconnection;
        this.errorDAO = errorDAO;
    }

    /**
     * Receives an ErrorMessage and persists its contents into database.
     *
     * @param m
     *          Message : the ErrorMessage to be processed and saved.
     */
    public void receive(Message m)
    {
        ErrorMessage errmsg;

        if (m == null)
            return; // again nulls
        if (!(m instanceof ErrorMessage))
            return; // got wrong message

        errmsg = (ErrorMessage)m;
        errorDAO.save(errmsg);
    }

    /**
     * Reporters are not handled by this class so this method simply returns.
     *
     * @param r
     */
    public void receive(Reporter r)
    {
        return;
    }
}
