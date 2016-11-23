package test;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Mickey on 2016/11/2.
 */
abstract class RelationalDatabaseOper implements DatabaseOperations{

    public abstract ResultSet getData(String query);

    public ResultSet getData(int a){
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    abstract void batch();
}
