package desmoj.extensions.db.visustorage.dao;

import desmoj.extensions.db.DBConnection;
import desmoj.extensions.db.dao.DBCountOrAggDAO;
import desmoj.extensions.db.dao.IDDAO;
import desmoj.extensions.db.dao.NamedSingleDatasetDAO;
import desmoj.extensions.db.dao.SingleDatasetDAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class IDSingleDatasetDAO<T> extends SingleDatasetDAO<T> implements IDDAO
{
    private int id;
    private IDSingleDatasetDAO parent;

    public IDSingleDatasetDAO(DBConnection dbConnection, IDDAO parentDAO)
    {
        super(dbConnection, parentDAO);
        id = -1;
    }

    /**
     * @return the framework managed id
     */
    public int getID() throws SQLException
    {
        if (id == -1)
        {
            String sql = "SELECT COUNT(*), MAX(ID) FROM " + getTableName();
            PreparedStatement stmnt = connection.createPreparedStatement(sql);
            ResultSet set = stmnt.executeQuery();
            set.next();
            int count = (set.getInt(1) == 0) ? 0 : set.getInt(2);
            id = (count + 1);
            set.close();
            stmnt.close();
        }
        return id;
    }

    protected abstract String getTableName();

}
