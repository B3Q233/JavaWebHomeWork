package com.b3qTest.service;

import com.b3qTest.dao.FirstCategoryDao;
import com.b3qTest.dao.FirstCategoryDaoImpl;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.FirstCategory;
import com.b3qTest.pojo.SecondCategory;

import java.util.List;

public class FirstCategoryService implements FirstCategoryDao {

    private FirstCategoryDao dao = null;

    @Override
    public boolean update(FirstCategory firstCategory, String primaryKey, Object value) throws Exception {
        return this.dao.update(firstCategory,primaryKey,value);
    }

    public FirstCategoryService() {
        try{
            this.dao = new FirstCategoryDaoImpl();
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public List<FirstCategory> queryAll(FirstCategory firstCategory) throws Exception {
        return this.dao.queryAll(firstCategory);
    }

    @Override
    public boolean delete(FirstCategory firstCategory, String primaryKey, Object id) throws Exception {
        if(DAOFactory.getIEmpSecondCategoryInstance().findById(new SecondCategory(),"parentId",id)!=null){
            return false;
        }
        return this.dao.delete(firstCategory,primaryKey,id);
    }

    @Override
    public FirstCategory findById(FirstCategory firstCategory, String key, Object value) throws Exception {
        return this.dao.findById(firstCategory,key,value);
    }

    @Override
    public boolean insert(FirstCategory firstCategory) throws Exception {
        if(findById(firstCategory,"name",firstCategory.getName() )!=null){
            return false;
        }
        return this.dao.insert(firstCategory);
    }
}
