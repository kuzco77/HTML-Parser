package com.facade;

import com.config.Config;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {

    private static DBConnect instance = null;
    private static final BasicDataSource ds = null;
    public static String url;
    public static Connection cnn;

    private static BasicDataSource datasource;

    private DBConnect() {
        super();
    }

    public static Connection getConnection() throws SQLException {
        if (datasource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(Config.DB_DRIVER);
            ds.setUrl(Config.CONNECTION_URL);
            ds.setUsername(Config.DB_USER);
            ds.setPassword(Config.DB_PWD);
            ds.setInitialSize(Config.DB_MIN_CONNECTION);
//            ds.setMaxOpenPreparedStatements(100);
            ds.setValidationQuery("Select 1");
            ds.setMaxTotal(Config.DB_MAX_CONNECTION);
            datasource = ds;
        }
        return datasource.getConnection();
    }

    public static synchronized DBConnect getInstance() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }

    public static boolean Open() {
        try {
            if (cnn == null || cnn.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                url = "jdbc:mysql://" + Config.DB_IP + ":3306/" + Config.DB_NAME + "?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";
                cnn = DriverManager.getConnection(url, Config.DB_USER, Config.DB_PWD);
            }
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void Close() {
        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void Close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Close();
    }

    public static void Close(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {

        }
        Close(ps);
    }
}
