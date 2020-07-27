package com.config;

import com.entities.VasItemNewsType;
import com.facade.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Config {

    public static String BASE_VNMEDIA_URL = "http://vnmedia.vn";
    public static String BASE_IMAGE_VNMEDIA_URL = BASE_VNMEDIA_URL + "/file";
    /*VNMedia specific format*/
    public static String VNMEDIA_DATE_FORMAT = "HH:mm, dd/MM/yyyy";
    public static String VNMEDIA_API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    /*file config*/
    public static String IMAGE_STORE_PATH = "/Users/chunamanh/Downloads/logo";
//    public static String IMAGE_STORE_PATH = "/u01/website/phetit.vn/public/images";

    /*app config*/
    public static String LOG_PATH = "/Users/chunamanh/Downloads/logs";
//    public static String LOG_PATH = "/u01/crawl/VNMedia/logs";

    /*mysql config*/
    public static String DB_IP = "125.212.226.145";
    public static String DB_PORT = "3306";
    public static String DB_NAME = "vnmedia";
    public static String DB_USER = "bigsms";
    public static String DB_PWD = "ghdc@123";
    public static int DB_MAX_CONNECTION = 100;
    public static int DB_MIN_CONNECTION = 2;
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String CONNECTION_URL = "jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;



}
