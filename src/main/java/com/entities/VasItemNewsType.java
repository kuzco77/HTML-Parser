package com.entities;

import com.facade.DBConnect;
import com.logger.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VasItemNewsType {
    public int id, parent, status, hasChild;
    public String name, url, api_id;

    public static List<VasItemNewsType> getArticleType() {
        List<VasItemNewsType> list = new ArrayList<VasItemNewsType>();
        try {
            if (!DBConnect.getConnection().isClosed()) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBConnect.getConnection();
                    ps = con.prepareStatement("select * from article_type where url is not null AND status = 1 AND parent = 0");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        VasItemNewsType v = new VasItemNewsType();
                        v.id = rs.getInt("id");
                        v.name = rs.getString("name");
                        v.parent = rs.getInt("parent");
                        v.status = rs.getInt("status");
                        v.hasChild = rs.getInt("has_child");
                        v.url = rs.getString("url");
                        list.add(v);
                    }
                } catch (Exception e) {
                    Logger.error(e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }

                    if (con != null) {
                        con.close();
                    }
                }
            }
        } catch (Exception e) {
            Logger.error(e);
        }
//        DBConnect.Close();
        return list;
    }

    public static VasItemNewsType getArticleTypeById(int id) {
        try {
            if (!DBConnect.getConnection().isClosed()) {
                Connection con = DBConnect.getConnection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {

                    ps = DBConnect.getConnection().prepareStatement("select * from article_type where url is not null AND status = 1 AND id = ? limit 1");
                    ps.setInt(1, id);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        VasItemNewsType v = new VasItemNewsType();
                        v.id = rs.getInt("id");
                        v.name = rs.getString("name");
                        v.parent = rs.getInt("parent");
                        v.status = rs.getInt("status");
                        v.hasChild = rs.getInt("has_child");
                        v.url = rs.getString("url");
                        v.api_id = rs.getString("api_id");
                        return v;
                    }
                } catch (Exception e) {
                    Logger.error(e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
            }
        } catch (Exception e) {
            Logger.error(e);
        }
        return null;
    }
}
