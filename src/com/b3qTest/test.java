package com.b3qTest;

import com.b3qTest.db.JDBCUtils;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.SiteInfo;
import com.b3qTest.tool.ComTool;
import com.b3qTest.tool.DBTool;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author b3q
 * 用户相关功能 测试用
 * ok
 * 新闻相关功能，测试用
 * ok
 * 数据连接池，多线程测试
 * ok
 * sql反射优化代码测试
 * ok
 * 网站详细相关功能，测试用
 * ok
 */

public class test {
    public static Date changeDateFormat(String str) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(str);
        return date;
    }
    class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("start new thread!");
        }
    }

    public static void main(String[] args) throws Exception {
//        System.out.println("good luck");
//
//        test add
//        User user =new User();
//        user.setUser_name("b3qq2");
//        user.setName("mxxy");
//        user.setPhone_number(114514255);
//        user.setEmail("114514@qq.com");
//        System.out.println(changeDateFormat("1145-11-04"));
//        user.setBirthday(changeDateFormat("1145-11-04"));
//        user.setPwd("ddnn114514");
//        user.setGender("男");
//        user.setZip(114514);
//        DAOFactorty.getIEmpDAOInstance().addUser(user);
//
//        test query
//        User user = DAOFactorty.getIEmpDAOInstance().findByUserName("b3q1");
//        System.out.println(user);
//
//        List<User> list = DAOFactorty.getIEmpDAOInstance().findAll("");
//        System.out.println(list);
//
//        News Test
//        News news = new News();
//
//        news.setId(4);
//        DAOFactory.getIEmpNewsDAOInstance().insert(news);
//         System.out.println(DAOFactory.getIEmpNewsDAOInstance().delete(news,"id"));
//        List<News> all = DAOFactory.getIEmpNewsDAOInstance().queryAll(news);
//        System.out.println(all);

//        数据库连接池多线程 测试
//        final AtomicInteger cnt = new AtomicInteger(0);
//        CountDownLatch latch = new CountDownLatch(20); // 创建一个计数器为20的CountDownLatch
//
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            Thread thread = new Thread(() -> {
//                try {
//                    cnt.incrementAndGet();
//                    Connection c = JDBCUtils.getConnection();
//                    if (finalI % 2 == 0) {
//                        JDBCUtils.close(c);
//                    } else {
//                        System.out.println(finalI);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    latch.countDown(); // 每个线程完成时计数器减一
//                }
//            });
//            thread.start();
//        }
//
//        latch.await(); // 等待所有线程完成
//        System.out.println("over");
//        System.out.println("Total connections attempted: " + cnt.get());

//        news 测试
//        System.out.println(ComTool.getNowDate());

//        System.out.println(DAOFactory.getIEmpNewsDAOInstance().findNews(1).getId());
//        System.out.println(DAOFactory.getIEmpNewsDAOInstance().findNews("asdsaasd","国内").getId());
//        System.out.println(DAOFactory.getIEmpNewsDAOInstance().findByColumn("国际"));
//        System.out.println(DAOFactory.getIEmpNewsDAOInstance().getSizeByColumn("国内"));
//        System.out.println(DAOFactory.getIEmpNewsDAOInstance().findByColumn(3,1,"国际"));

//        网站详情功能测试
//        SiteInfo siteInfo = new SiteInfo();
//        siteInfo.setId(1);
//        siteInfo.setSiteDomain("https://1s14514.com");
//        siteInfo.setSiteName("mxxy");
//        siteInfo.setLogoImg("1145113131.png");
//        siteInfo.setSiteKeyWords("rengewdad,1312");
//        siteInfo.setSiteDescription("2313asdada");
//        System.out.println(DBTool.update(siteInfo,JDBCUtils.getConnection(),"id"));
//        System.out.println(DAOFactory.getIEmpSiteInfoDao().update(siteInfo,"id"));
//        System.out.println(DAOFactory.getIEmpSiteInfoDao().queryAll(siteInfo));

    }

}
