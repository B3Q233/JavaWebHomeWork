package com.b3qTest.dao;

import com.b3qTest.pojo.ThirdCategory;
import com.b3qTest.pojo.ThirdCategory;

import java.util.List;

public interface ThirdCategoryDao {
    /**
     *  网站详情查询函数，接受一个 siteInfo 对象,查询所有数据
     * @param thirdCategory JAVABean 对象
     * @return 一个 List<ThirdCategory>，存储对应表的所有信息
     * @throws Exception
     */
    public List<ThirdCategory> queryAll(ThirdCategory thirdCategory) throws Exception;

    /**
     *  一级栏目删除函数，接受一个 ThirdCategory 对象,进行删除
     * @param thirdCategory JAVABean 对象
     * @param primaryKey 主键名
     * @return 一个 List<ThirdCategory>，存储对应表的所有信息
     * @throws Exception
     */
    public boolean delete(ThirdCategory thirdCategory,String primaryKey,Object id) throws Exception;

    /**
     *  一级栏目插入函数，插入数据到对应数据库
     * @param thirdCategory JAVABean 对象
     * @return 一个 布尔值 表示是否插入成功
     * @throws Exception
     */
    public boolean insert(ThirdCategory thirdCategory) throws Exception;

    /**
     * 一级栏目查询函数，根据主属性以及值查询相关对象
     * @param thirdCategory JAVABean 对象，用于获取表名等信息
     * @param key 主属性名称
     * @param value 对应的值
     * @return 一个ThirdCategory 对象
     * @throws Exception 如果查询过程中发生错误
     */
    public ThirdCategory findById(ThirdCategory thirdCategory, String key, Object value) throws Exception;

    /**
     *  二级级栏目更新函数，插入数据到对应数据库
     * @param thirdCategory JAVABean 对象
     * @return 一个 布尔值 表示是否插入成功
     * @throws Exception
     */
    public boolean update(ThirdCategory thirdCategory, String primaryKey, Object value) throws Exception;
}
