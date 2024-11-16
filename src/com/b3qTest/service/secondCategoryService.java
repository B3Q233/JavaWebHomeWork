package com.b3qTest.service;

import com.b3qTest.dao.SecondCategoryDao;
import com.b3qTest.dao.SecondCategoryDaoImpl;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.SecondCategory;
import com.b3qTest.pojo.ThirdCategory;

import java.util.List;

public class secondCategoryService implements SecondCategoryDao {
    @Override
    public boolean update(SecondCategory secondCategory, String primaryKey, Object value) throws Exception {
        return this.dao.update(secondCategory,primaryKey,value);
    }

    private SecondCategoryDao dao = null;
    public secondCategoryService() {
        try{
            this.dao = new SecondCategoryDaoImpl();
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public List<SecondCategory> queryAll(SecondCategory secondCategory) throws Exception {
        return this.dao.queryAll(secondCategory);
    }

    @Override
    public boolean delete(SecondCategory secondCategory, String primaryKey, Object id) throws Exception {
        System.out.println((DAOFactory.getIEmpThirdCategoryInstance().findById(new ThirdCategory(),"parentId",id)));
        if (DAOFactory.getIEmpThirdCategoryInstance().findById(new ThirdCategory(),"parentId",id)!=null){
            return false;
        }
        return this.dao.delete(secondCategory,primaryKey,id);
    }

    @Override
    public SecondCategory findById(SecondCategory secondCategory, String key, Object value) throws Exception {
        return this.dao.findById(secondCategory,key,value);
    }

    @Override
    public boolean insert(SecondCategory secondCategory) throws Exception {
        if(findById(secondCategory,"name",secondCategory.getName() )!=null){
            return false;
        }
        return this.dao.insert(secondCategory);
    }
}
