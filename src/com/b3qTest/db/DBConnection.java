package com.b3qTest.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author b3q
 * 数据库连接
 */

public class DBConnection {
    private  String Driver;
    private  String URL;
    private  String USER;
    private  String PASSWORD;
    private Connection conn = null;
    public DBConnection() throws Exception{
        //读取数据库配置文件
        try{
            Properties pro = new Properties();
            pro.load(new FileInputStream("G:\\work2\\src\\com\\b3qTest\\config.properties"));
            Driver = pro.getProperty("database.driver");
            URL = pro.getProperty("database.url");
            USER = pro.getProperty("database.user");
            PASSWORD = pro.getProperty("database.pwd");

        }catch (Exception e){
            throw new RuntimeException("加载配置文件出错", e);
        }

        try {
        //  利用反射加载数据库驱动
            Class.forName(Driver);
            this.conn = DriverManager.getConnection(URL,USER,PASSWORD);

        }catch (Exception e){
        //   抛出异常
            throw new RuntimeException("连接异常", e);
        }

    }

    //    取得数据库连接
    public Connection getConnection(){
        return this.conn;
    }

    //    关闭数据库
    public void close() throws Exception{
        if(this.conn!=null){
            try{
                this.conn.close();
            }catch (Exception e){
                throw e;
            }

        }
    }
}
