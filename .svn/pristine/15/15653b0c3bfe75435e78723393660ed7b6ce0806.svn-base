package desmoj.extensions.db.visustorage;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.ExperimentDAO;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleResultSet;

import oracle.sql.BLOB;


public class LoadXMLandModels
{
    public static final String objectTableName = "V_OBJECT";
    public static final String xmlTableName = "V_XML";

    private DBConnection _dbConnection;
    private int _experimentID;
    private static LoadXMLandModels _instance = null;
    private String _modelXMLPath = null;


    private LoadXMLandModels()
    {
        super();
    }

    public String getModelXMLPath()
    {
        return _modelXMLPath;
    }
    
    public void setDBConnection(DBConnection connection)
    {
        _dbConnection = connection;
    }

    public void setExperimentDAO(int experimentID)
    {
        _experimentID = experimentID;
    }

    public static LoadXMLandModels getInstance() throws SQLException
    {
        if (_instance == null)
            _instance = new LoadXMLandModels();

        return _instance;
    }

    private void load(String path, String name, BLOB blob) throws Exception
    {

        FileOutputStream outputFileOutputStream = null;
        long position;
        byte[] binaryBuffer;
        int bytesRead = 0;
        //int totbytesRead = 0;
        //int totbytesWritten = 0;
        File outputBinaryFile = null;

        {
            File f = new File(path);
            if (!f.exists())
            {
                f.mkdirs();
            }

            outputBinaryFile = new File(path + name);
            outputFileOutputStream = new FileOutputStream(outputBinaryFile);

            int chunkSize = blob.getChunkSize();
            binaryBuffer = new byte[chunkSize];

            for (position = 1; position <= blob.length();
                 position += chunkSize)
            {
                // Loop through while reading a chunk of data from the BLOB
                // column using the getBytes() method. This data will be stored
                // in a temporary buffer that will be written to disk.
                bytesRead = blob.getBytes(position, chunkSize, binaryBuffer);

                // Now write the buffer to disk.
                outputFileOutputStream.write(binaryBuffer, 0, bytesRead);

                //totbytesRead += bytesRead;
                //totbytesWritten += bytesRead;
            }

            outputFileOutputStream.close();
        }
    }

    public void loadXML() throws Exception
    {
        String sql =
            "SELECT PATH, FILENAME, XML, TYPE FROM " + xmlTableName + " WHERE EXPERIMENT_FK = " +
            _experimentID + " FOR UPDATE";
        Statement stmnt;
        ResultSet set;
        BLOB object;
        String name;
        String path;

        stmnt = _dbConnection.createStatement();
        set = stmnt.executeQuery(sql);
        while (set.next())
        {
            path = set.getString("PATH");
            name = set.getString("FILENAME");
            object = ((OracleResultSet)set).getBLOB("XML");
            load(path, name, object);
            if (set.getString("TYPE").equals("Model"))
            {
                _modelXMLPath = path + name;
            }
        }
        
        set.close();
        stmnt.close();
    }


    public void loadModels() throws Exception
    {
        String sql =
            "SELECT PATH, FILENAME, OBJECT FROM " + objectTableName + " WHERE EXPERIMENT_FK = " +
            _experimentID + " FOR UPDATE";
        Statement stmnt;
        ResultSet set;
        BLOB object;
        String name;
        String path;

        stmnt = _dbConnection.createStatement();
        set = stmnt.executeQuery(sql);
        while (set.next())
        {
            path = set.getString("PATH");
            name = set.getString("FILENAME");
            object = ((OracleResultSet)set).getBLOB("OBJECT");
            load(path, name, object);
        }

        set.close();
        stmnt.close();
    }
}
