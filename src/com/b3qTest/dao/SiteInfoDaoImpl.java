package com.b3qTest.dao;

import com.b3qTest.db.JDBCUtils;
import com.b3qTest.pojo.SiteInfo;
import com.b3qTest.tool.DBTool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteInfoDaoImpl implements SiteInfoDao{
    @Override
    public boolean update(SiteInfo siteInfo,String primaryKey) throws Exception {
       boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.update(siteInfo, conn,primaryKey);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败", e);
        }
        return flag;
    }

    @Override
    public List<SiteInfo> queryAll(SiteInfo siteInfo) throws Exception {
        List<SiteInfo> list = new ArrayList<SiteInfo>();
        try (Connection conn = JDBCUtils.getConnection()) {
            list = DBTool.queryAll(siteInfo, conn);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败", e);
        }
        return list;
    }
}
