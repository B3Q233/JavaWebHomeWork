package com.b3qTest.factory;

import com.b3qTest.dao.*;
import com.b3qTest.pojo.SecondCategory;
import com.b3qTest.service.*;

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

    public static SiteInfoDao getIEmpSiteInfoInstance() throws Exception{
        return new SiteInfoService();
    }

    public static FirstCategoryDao getIEmpFirstCategoryInstance() throws Exception{
        return new FirstCategoryService();
    }

    public static SecondCategoryDao getIEmpSecondCategoryInstance() throws Exception{
        return new secondCategoryService();
    }

    public static ThirdCategoryDao getIEmpThirdCategoryInstance() throws Exception{
        return new ThirdCategoryService();
    }
}
