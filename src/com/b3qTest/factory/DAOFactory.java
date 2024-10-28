package com.b3qTest.factory;

import com.b3qTest.dao.NewsDao;
import com.b3qTest.dao.UserDao;
import com.b3qTest.pojo.News;
import com.b3qTest.service.NewsService;
import com.b3qTest.service.UserService;

/**
 * @author b3q
 * 使用工厂模式 使得客户端与对象创造逻辑分离
 * 模块化代码 易于维护
 */
public class DAOFactory {
    public static UserDao getIEmpUserDAOInstance() throws Exception{
        return new UserService();
    }

    public static NewsDao getIEmpNewsDAOInstance() throws Exception{
        return new NewsService();
    }
}
