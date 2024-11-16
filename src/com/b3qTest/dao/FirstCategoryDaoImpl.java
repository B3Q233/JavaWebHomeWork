package com.b3qTest.dao;

import com.b3qTest.db.JDBCUtils;
import com.b3qTest.pojo.FirstCategory;
import com.b3qTest.tool.DBTool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FirstCategoryDaoImpl implements FirstCategoryDao{
    @Override
    public List<FirstCategory> queryAll(FirstCategory firstCategory) throws Exception {
        List<FirstCategory> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection()) {
            list = DBTool.queryAll(firstCategory, conn);
        } catch (SQLException e) {
            throw new RuntimeException("查询一级目录失败", e);
        }
        return list;
    }

    @Override
    public boolean delete(FirstCategory firstCategory, String primaryKey, Object id) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.delete(firstCategory, conn,primaryKey,id);
        } catch (SQLException e) {
            throw new RuntimeException("删除一级目录失败", e);
        }
        return flag;
    }

    @Override
    public boolean insert(FirstCategory firstCategory) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.insert(firstCategory, conn);
        } catch (SQLException e) {
            throw new RuntimeException("插入一级目录失败", e);
        }
        return flag;
    }

    @Override
    public boolean update(FirstCategory firstCategory,String primaryKey,Object value) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.update(firstCategory, conn,primaryKey,value);
        } catch (SQLException e) {
            throw new RuntimeException("插入一级目录失败", e);
        }
        return flag;
    }

    @Override
    public FirstCategory findById(FirstCategory firstCategory, String key, Object value) throws Exception {
        FirstCategory retFirstCategory;
        try (Connection conn = JDBCUtils.getConnection()) {
            retFirstCategory = DBTool.findByKey(firstCategory.getClass(),conn,key,value);
        } catch (SQLException e) {
            throw new RuntimeException("插入一级目录失败", e);
        }
        return retFirstCategory;
    }
}
