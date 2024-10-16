package com.b3qTest.dao;

import com.b3qTest.pojo.User;

import java.util.List;

/**
 * @author b3q
 * user 数据库操作相关接口
 */
public interface UserDao {
    /**
     * 数据库 新增用户
     * @param user 要增加的数据对象;
     * @return 是否增加成功的标记
     * @throw Exception 如果有异常，则直接抛出
     */

    public boolean addUser(User user) throws Exception;

    /**
     * 查询全部的 user 数据
     * @param user_name 用户名;
     * @return 返回全部的查询结果，每一个 user 对象表示表的一行数据
     * @throw Exception 如果有异常，则直接抛出
     */
    public List<User> findAll(String user_name)throws Exception;

    /**
     * 根据 user_name 查询用户
     * @param user_name 用户名;
     * @return 用户的 vo 对象
     * @throw Exception 如果有异常，则直接抛出
     */
    public User findByUserName(String user_name)throws Exception;

}