package com.b3qTest.dao;

import com.b3qTest.pojo.News;

import java.util.List;

/**
 * @author b3q
 * news 数据库操作相关接口
 */
public interface NewsDao {
    /**
     *  新闻插入函数，接受一个News对象实现插入新闻
     * @param news JAVABean 对象
     * @return 一个boolean值，表示插入数据是否成功，true表示成功，否则失败
     * @throws Exception
     */
    public boolean insert(News news)  throws Exception;

    /**
     *  新闻删除函数，接受一个News对象和供删除使用的属性实现删除新闻
     * @param news JAVABean 对象
     * @param key 根据哪一个属性进行删除，必须为主键
     * @return 一个boolean值，表示删除数据是否成功，true表示成功，否则失败
     * @throws Exception
     */
    public boolean delete(News news,String key) throws Exception;

    /**
     *  新闻查询函数，接受一个News对象,查询所有数据
     * @param news JAVABean 对象
     * @return 一个 List<News>，存储对应表的所有信息
     * @throws Exception
     */
    public List<News> queryAll(News news) throws Exception;

    /**
     *  新闻查询函数，根据新闻标题和栏目查询新闻
     * @param column 文章 栏目
     * @param title  文章 标题
     * @return News 对象
     * @throws Exception
     */
    public News findNews(String title,String column) throws  Exception;

    /**.
     *  新闻查询函数，根据新闻id查询新闻
     * @param id 文章 id
     * @return News 对象
     * @throws Exception
     */
    public News findNews(int id) throws Exception;

    /**.
     *  新闻查询函数，根据最低一级栏目，查找所有属于该栏目的新闻
     * @param column 新闻所属栏目
     * @return List<News> news对象列表
     * @throws Exception
     */
    public List<News> findByColumn(String column) throws Exception;

    /**.
     *  新闻查询函数，栏目为column的第x页的所有新闻
     * @param pagesize 分页大小
     * @param offset 第几页
     * @return List<News> news对象列表
     * @throws Exception
     */
    public List<News> findByColumn(int pagesize,int offset,String column) throws Exception;

    /**.
     *  栏目新闻数量查询函数，查询一个栏目中所有新闻的数量，用于分页
     * @param column 新闻所属栏目
     * @return int 一个栏目中所有新闻的数量
     * @throws Exception
     */
    public int getSizeByColumn(String column) throws Exception;
}
