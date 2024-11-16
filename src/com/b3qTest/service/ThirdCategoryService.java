package com.b3qTest.service;

import com.b3qTest.dao.ThirdCategoryDao;
import com.b3qTest.dao.ThirdCategoryDaoImpl;
import com.b3qTest.pojo.ThirdCategory;

import java.util.List;

public class ThirdCategoryService implements ThirdCategoryDao {
    @Override
    public boolean update(ThirdCategory thirdCategory, String primaryKey, Object value) throws Exception {
        return this.dao.update(thirdCategory,primaryKey,value);
    }

    private ThirdCategoryDao dao = null;
    public ThirdCategoryService() {
        try{
            this.dao = new ThirdCategoryDaoImpl();
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public List<ThirdCategory> queryAll(ThirdCategory thirdCategory) throws Exception {
        return this.dao.queryAll(thirdCategory);
    }

    @Override
    public boolean delete(ThirdCategory thirdCategory, String primaryKey, Object id) throws Exception {
        return this.dao.delete(thirdCategory,primaryKey,id);
    }

    @Override
    public ThirdCategory findById(ThirdCategory thirdCategory, String key, Object value) throws Exception {
        return this.dao.findById(thirdCategory,key,value);
    }

    @Override
    public boolean insert(ThirdCategory thirdCategory) throws Exception {
        if(findById(thirdCategory,"name",thirdCategory.getName() )!=null){
            return false;
        }
        return this.dao.insert(thirdCategory);
    }
}
