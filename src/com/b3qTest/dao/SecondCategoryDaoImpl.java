package com.b3qTest.dao;

import com.b3qTest.db.JDBCUtils;
import com.b3qTest.pojo.SecondCategory;
import com.b3qTest.tool.DBTool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecondCategoryDaoImpl implements SecondCategoryDao{
    @Override
    public List<SecondCategory> queryAll(SecondCategory secondCategory) throws Exception {
        List<SecondCategory> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection()) {
            list = DBTool.queryAll(secondCategory, conn);
        } catch (SQLException e) {
            throw new RuntimeException("查询二级目录失败", e);
        }
        return list;
    }

    @Override
    public boolean delete(SecondCategory secondCategory, String primaryKey, Object id) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.delete(secondCategory, conn,primaryKey,id);
        } catch (SQLException e) {
            throw new RuntimeException("删除二级目录失败", e);
        }
        return flag;
    }

    @Override
    public boolean update(SecondCategory secondCategory, String primaryKey, Object value) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.update(secondCategory, conn,primaryKey,value);
        } catch (SQLException e) {
            throw new RuntimeException("更新二级目录失败", e);
        }
        return flag;
    }

    @Override
    public boolean insert(SecondCategory secondCategory) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.insert(secondCategory, conn);
        } catch (SQLException e) {
            throw new RuntimeException("插入二级目录失败", e);
        }
        return flag;
    }

    @Override
    public SecondCategory findById(SecondCategory secondCategory, String key, Object value) throws Exception {
        SecondCategory retSecondCategory;
        try (Connection conn = JDBCUtils.getConnection()) {
            retSecondCategory = DBTool.findByKey(secondCategory.getClass(),conn,key,value);
        } catch (SQLException e) {
            throw new RuntimeException("查询二级目录失败", e);
        }
        return retSecondCategory;
    }
}
