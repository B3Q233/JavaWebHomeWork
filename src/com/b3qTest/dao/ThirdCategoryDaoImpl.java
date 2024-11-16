package com.b3qTest.dao;

import com.b3qTest.db.JDBCUtils;
import com.b3qTest.pojo.ThirdCategory;
import com.b3qTest.pojo.ThirdCategory;
import com.b3qTest.tool.DBTool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThirdCategoryDaoImpl implements ThirdCategoryDao{
    @Override
    public List<ThirdCategory> queryAll(ThirdCategory thirdCategory) throws Exception {
        List<ThirdCategory> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection()) {
            list = DBTool.queryAll(thirdCategory, conn);
        } catch (SQLException e) {
            throw new RuntimeException("查询三级目录失败", e);
        }
        return list;
    }

    @Override
    public boolean delete(ThirdCategory thirdCategory, String primaryKey, Object id) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.delete(thirdCategory, conn,primaryKey,id);
        } catch (SQLException e) {
            throw new RuntimeException("删除三级目录失败", e);
        }
        return flag;
    }

    @Override
    public boolean update(ThirdCategory thirdCategory, String primaryKey, Object value) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.update(thirdCategory, conn,primaryKey,value);
        } catch (SQLException e) {
            throw new RuntimeException("更新三级目录失败", e);
        }
        return flag;
    }

    @Override
    public boolean insert(ThirdCategory thirdCategory) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.insert(thirdCategory, conn);
        } catch (SQLException e) {
            throw new RuntimeException("插入三级目录失败", e);
        }
        return flag;
    }

    @Override
    public ThirdCategory findById(ThirdCategory thirdCategory, String key, Object value) throws Exception {
        ThirdCategory retThirdCategory;
        try (Connection conn = JDBCUtils.getConnection()) {
            retThirdCategory = DBTool.findByKey(thirdCategory.getClass(),conn,key,value);
        } catch (SQLException e) {
            throw new RuntimeException("查找三级目录失败", e);
        }
        return retThirdCategory;
    }
}
