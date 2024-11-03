package com.b3qTest.dao;


import com.b3qTest.pojo.SiteInfo;

import java.util.List;

/**
 * @author b3q
 * siteInfo 数据库操作相关接口
 */
public interface SiteInfoDao {


    /**
     *  网站详情查询函数，接受一个 siteInfo 对象,查询所有数据
     * @param siteInfo JAVABean 对象
     * @return 一个 List<SiteInfo>，存储对应表的所有信息
     * @throws Exception
     */
    public List<SiteInfo> queryAll(SiteInfo siteInfo) throws Exception;

    /**
     *  网站详情更新函数，接受一个 siteInfo 对象,更新所有数据
     * @param siteInfo JAVABean 对象
     * @param primaryKey 主键名
     * @return 一个 List<SiteInfo>，存储对应表的所有信息
     * @throws Exception
     */
    public boolean update(SiteInfo siteInfo,String primaryKey) throws Exception;


}
