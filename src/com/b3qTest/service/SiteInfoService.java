package com.b3qTest.service;

import com.b3qTest.dao.SiteInfoDao;
import com.b3qTest.dao.SiteInfoDaoImpl;
import com.b3qTest.pojo.SiteInfo;

import java.util.List;


public class SiteInfoService implements SiteInfoDao {

    private SiteInfoDao dao = null;
    public SiteInfoService() {
        try{
            this.dao = new SiteInfoDaoImpl();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public boolean update(SiteInfo siteInfo, String primaryKey) throws Exception {
        boolean flag = false;
        try{
            flag = this.dao.update(siteInfo,primaryKey);
        }catch (Exception e){
            throw e;
        }
        return flag;
    }

    @Override
    public List<SiteInfo> queryAll(SiteInfo siteInfo) throws Exception {
        List<SiteInfo> all = null;
        try{
            all = this.dao.queryAll(siteInfo);
        }catch (Exception e){
            throw e;
        }
        return all;
    }
}
