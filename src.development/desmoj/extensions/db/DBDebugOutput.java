package desmoj.extensions.db;

import desmoj.core.report.DebugNote;
import desmoj.core.report.ErrorMessage;
import desmoj.core.report.Message;
import desmoj.core.report.MessageReceiver;

import desmoj.core.report.Reporter;
import desmoj.extensions.db.dao.DBDebugDAO;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.logging.Logger;

/**
 * Use this class to persist Simulation Debug Output into your Database.
 *
 * @author Soeren Linke, Tim Janz, Niklas Diehl
 */
public class DBDebugOutput implements MessageReceiver
{
    private DBConnection dbc;
    private DBDebugDAO debugDAO;

    /**
     * Create a new DBDebugOutput class.
     *
     * @param dbconnection
     *          DBConnection : the connection to the database
     * @param debugDAO
     *          DBDebugDAO : the Debug-DAO which handles DebugNotes
     * 
     */
    public DBDebugOutput(DBConnection dbconnection, DBDebugDAO debugDAO)
    {
        super();
        dbc = dbconnection;
        this.debugDAO = debugDAO;
    }

    /**
     * Receives a DebugNote and persists its contents into database.
     *
     * @param m
     *          Message : the DebugNote to be processed and saved
     */
    public void receive(Message m)
    {
        DebugNote note;

        if (m == null)
            return; // again nulls
        if (!(m instanceof DebugNote))
            return; // got wrong message

        note = (DebugNote)m;
        debugDAO.save(note);
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
