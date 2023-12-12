package org.company.project.common.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;

public class JDBCProvider {
    public static final int ORCLPDB1 = 1;
    public static final int XEPDB1 = 2;

    private JDBCProvider () {}
    private static final BasicDataSource BASIC_DATA_SOURCE_ORCL = new BasicDataSource();
    private static final BasicDataSource BASIC_DATA_SOURCE_XEPDB = new BasicDataSource();
    static {
        BASIC_DATA_SOURCE_ORCL.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        BASIC_DATA_SOURCE_ORCL.setUrl("jdbc:oracle:thin:@localhost:1521/orclpdb1");
        BASIC_DATA_SOURCE_ORCL.setUsername("amir");
        BASIC_DATA_SOURCE_ORCL.setPassword("myjava123");
        BASIC_DATA_SOURCE_ORCL.setMaxTotal(20);
        BASIC_DATA_SOURCE_ORCL.setMaxIdle(5);

        BASIC_DATA_SOURCE_XEPDB.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        BASIC_DATA_SOURCE_XEPDB.setUrl("jdbc:oracle:thin:@localhost:1521/xepdb1");
        BASIC_DATA_SOURCE_XEPDB.setUsername("amir");
        BASIC_DATA_SOURCE_XEPDB.setPassword("myjava123");
        BASIC_DATA_SOURCE_XEPDB.setMaxTotal(20);
        BASIC_DATA_SOURCE_XEPDB.setMaxIdle(5);
    }
    public static  Connection getConnection (int dbName) throws Exception{
        Connection connection = null;
        switch (dbName){
            case 1 :
                connection = BASIC_DATA_SOURCE_ORCL.getConnection();
                connection.setAutoCommit(false);
                return connection;
            default:
                connection = BASIC_DATA_SOURCE_XEPDB.getConnection();
                connection.setAutoCommit(false);
                return connection;

        }
    }

}
