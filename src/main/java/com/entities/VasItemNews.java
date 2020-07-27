package com.entities;

import com.facade.DBConnect;
import com.logger.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VasItemNews {

    public int id, type, status;
    public String title, logo, shortDesc, detail, url;
    public Date create_date;

    public static void add2SQL(List<VasItemNews> listObjects) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (!DBConnect.getConnection().isClosed()) {
                con = DBConnect.getConnection();
                ps = con.prepareStatement("call vnmedia.insertArticle(?,?,?,?,?,?,?,?);");
                int i = 0;
                for (VasItemNews object : listObjects) {
//                    Logger.log("add line " + i + " to bach");

                    ps.setString(1, object.title);
                    ps.setInt(2, object.type);
                    ps.setString(3, object.logo);
                    ps.setString(4, object.detail);
                    ps.setInt(5, object.status);
                    ps.setTimestamp(6, new Timestamp(object.create_date.getTime()));
                    ps.setString(7, object.shortDesc);
                    ps.setString(8, object.url);
                    ps.addBatch();
                    i++;
                    if ( i == listObjects.size()) {
                        Logger.log("execute batch in add2SQL");
                        Logger.log("=======================================");
                        int[] listResult = ps.executeBatch();
                        Logger.log("Articles have been added: ");
                        for (int j = 0; j < listResult.length; j++) {
                            if (listResult[j] != -1) {
                                Logger.log("- " + listObjects.get(j).title);
                            }
                        }
                        Logger.log("======= END =======");
                    }
                }

            }
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    public static List<VasItemNews> getArticle(int type, int offset, int limit) {
        List<VasItemNews> listNews = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (!DBConnect.getConnection().isClosed()) {
                con = DBConnect.getConnection();
                ps = con.prepareStatement("call vnmedia.get_article(?,?,?);");
                ps.setInt(1, type);
                ps.setInt(2, offset);
                ps.setInt(3, limit);

                rs = ps.executeQuery();
                while (rs.next()) {
                    VasItemNews news = new VasItemNews();
                    news.id = rs.getInt("id");
                    news.title = rs.getString("name");
                    news.type = rs.getInt("type");
                    news.logo = rs.getString("image");
                    news.detail = rs.getString("content");
                    news.status = rs.getInt("status");
                    news.create_date = rs.getDate("create_date");
                    news.shortDesc = rs.getString("summary");
                    news.url = rs.getString("url");

                    listNews.add(news);
                }

                Logger.log("Total of articles obtained: " + listNews.size() + " type " + type);
                Logger.log("======= END =======");

            }
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }

        return listNews;
    }

    public static void updateArticleSQL(List<VasItemNews> listObjects) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (!DBConnect.getConnection().isClosed()) {
                con = DBConnect.getConnection();
                ps = con.prepareStatement("call vnmedia.updateArticle(?,?,?,?,?,?,?,?,?);");
                int i = 0;
                for (VasItemNews object : listObjects) {
//                    Logger.log("add line " + i + " to bach");

                    ps.setInt(1, object.id);
                    ps.setString(2, object.title);
                    ps.setInt(3, object.type);
                    ps.setString(4, object.logo);
                    ps.setString(5, object.detail);
                    ps.setInt(6, object.status);
                    ps.setTimestamp(7, new Timestamp(object.create_date.getTime()));
                    ps.setString(8, object.shortDesc);
                    ps.setString(9, object.url);
                    ps.addBatch();
                    i++;
                    if ( i == listObjects.size()) {
                        Logger.log("execute batch in updateArticleSQL");
                        Logger.log("=======================================");
                        int[] listResult = ps.executeBatch();
                        Logger.log("Articles have been updated: ");
                        for (int j = 0; j < listResult.length; j++) {
                            if (listResult[j] != -1) {
                                Logger.log("- " + listObjects.get(j).title);
                            }
                        }
                        Logger.log("======= END =======");
                    }
                }

            }
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    public static boolean checkItemExist(VasItemNews object) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (!DBConnect.getConnection().isClosed()) {
                con = DBConnect.getConnection();
                ps = con.prepareStatement("select * from vnmedia.article where name = ?");
                ps.setString(1, object.title);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Logger.log("Exist article with name: " + rs.getString("name"));
                    return true;
                }

            }
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
//                    DBConnect.Close();
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }
        return false;
    }

}
