package desmoj.extensions.db.visustorage;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.ExperimentDAO;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleResultSet;

import oracle.sql.BLOB;

/**
 * Saves the XML with the information about the 3DModels and the 3DModels
 * themselves in the database. Necessary if the visualization
 * is to be started from another computer.
 */
public class SaveXMLandModels
{

    public static final String objectTableName = "V_OBJECT";
    public static final String xmlTableName = "V_XML";

    private DBConnection _dbConnection;
    private ExperimentDAO _experimentDAO;
    private int _objectID = -1;
    private int _xmlID = -1;
    private static SaveXMLandModels _instance = null;
    private boolean _isInitialized = false;

    private SaveXMLandModels()
    {
        super();
    }

    public void setDBConnection(DBConnection connection)
    {
        _dbConnection = connection;
    }

    public void setExperimentDAO(ExperimentDAO dao)
    {
        _experimentDAO = dao;
    }

    public static SaveXMLandModels getInstance() throws SQLException
    {
        if (_instance == null)
            _instance = new SaveXMLandModels();

        return _instance;
    }

    public void initIDs() throws SQLException
    {
        String sql = "SELECT COUNT(*), MAX(ID) FROM " + objectTableName;
        PreparedStatement stmnt = _dbConnection.createPreparedStatement(sql);
        ResultSet set = stmnt.executeQuery();
        set.next();
        _objectID = (set.getInt(1) == 0) ? 0 : set.getInt(2);
        set.close();
        stmnt.close();

        sql = "SELECT COUNT(*), MAX(ID) FROM " + xmlTableName;
        stmnt = _dbConnection.createPreparedStatement(sql);
        set = stmnt.executeQuery();
        set.next();
        _xmlID = (set.getInt(1) == 0) ? 0 : set.getInt(2);
        set.close();
        stmnt.close();

        _isInitialized = true;
    }

    private void checkArgs(String path, String[] objects) throws Exception
    {
        if (_dbConnection == null)
            throw new Exception("A DBConnection has to be set before saving; object and XML saving canceled");

        if (path == null)
            throw new IllegalArgumentException("Invalid path; object and XML saving canceled");

        if (objects == null)
            throw new IllegalArgumentException("Invalid objects; object and XML saving canceled");

        if (!_isInitialized)
            initIDs();
    }

    private void save(File inputBinaryFile, String sql1, String sql2,
                      String columnName) throws Exception
    {
        FileInputStream inputFileInputStream = null;
        Statement stmt = null;
        ResultSet set = null;
        BLOB object = null;
        int chunkSize;
        byte[] binaryBuffer;
        long position;
        int bytesRead = 0;
        int bytesWritten = 0;


        _dbConnection.startTransaction();
        stmt = _dbConnection.createStatement();

        inputFileInputStream = new FileInputStream(inputBinaryFile);

        stmt.executeUpdate(sql1);
        set = stmt.executeQuery(sql2);
        set.next();
        object = ((OracleResultSet)set).getBLOB(columnName);

        chunkSize = object.getChunkSize();
        binaryBuffer = new byte[chunkSize];

        position = 1;
        while ((bytesRead = inputFileInputStream.read(binaryBuffer)) != -1)
        {
            bytesWritten = object.putBytes(position, binaryBuffer, bytesRead);
            position += bytesRead;
        }

        inputFileInputStream.close();
        _dbConnection.endTransaction();
        set.close();
        stmt.close();
    }


    public void saveModels(String path, String[] files) throws Exception
    {
        checkArgs(path, files);

        for (String name : files)
        {
            _objectID++;
            if (name != null)
            {
                File inputBinaryFile = new File(path + name);

                String sql1 =
                    "INSERT INTO " + objectTableName + " (ID, FILENAME, PATH, OBJECT, EXPERIMENT_FK) " +
                    "VALUES(" + _objectID + ", '" + inputBinaryFile.getName() +
                    "', '" + path + "', EMPTY_BLOB(), " +
                    _experimentDAO.getID() + ")";

                String sql2 =
                    "SELECT OBJECT FROM " + objectTableName + " WHERE ID = " +
                    _objectID + " FOR UPDATE";

                save(inputBinaryFile, sql1, sql2, "OBJECT");
            }
        }
    }

    public void saveXML(String[] xmlPaths) throws Exception
    {
        checkArgs(xmlPaths[0], xmlPaths);


        for (int i = 0; i < 3; i = i + 2)
        {
            _xmlID++;

            if (xmlPaths[i + 1] != null)
            {
                File inputBinaryFile =
                    new File(xmlPaths[i] + xmlPaths[i + 1]);

                String sql1 =
                    "INSERT INTO " + xmlTableName + " (ID, FILENAME, PATH, XML, TYPE, EXPERIMENT_FK) " +
                    "VALUES(" + _xmlID + ", '" + inputBinaryFile.getName() +
                    "', '" + xmlPaths[i] + "', EMPTY_BLOB(), '" +
                    (i == 0 ? "Layout', " : "Model', ") + _experimentDAO.getID() +
                    ")";

                String sql2 =
                    "SELECT XML FROM " + xmlTableName + " WHERE ID = " +
                    _xmlID + " FOR UPDATE";

                save(inputBinaryFile, sql1, sql2, "XML");
            }
        }
    }
}

