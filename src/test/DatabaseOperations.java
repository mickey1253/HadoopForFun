package test;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Mickey on 2016/11/2.
 */
interface DatabaseOperations {

    ResultSet getData(String query);

    default void exute(String proName){
        System.out.print("");
    }

    Connection getConnection();

}
