package com.b3qTest.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *  数据库连接池
 * @author b3q
 */
public class JDBCUtils {

    private static DataSource ds;

    static {
        // 一个Properties对象，用于存储配置信息
        Properties properties = new Properties();
        // 获取类加载器，并通过它来获取配置文件的输入流
        InputStream resourceAsStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            // 加载配置文件 druid.properties
            properties.load(resourceAsStream);
            // 使用Druid连接池库来创建一个数据源（DataSource）对象的实例
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // 获取连接池对象
    public static DataSource getDataSource(){
        return ds;
    }

    /**
     * 从连接池中获取一个数据库的连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 关闭数据库的连接
     * @param connection
     * @throws SQLException
     */
    public static void close(Connection connection) throws SQLException {
        if(connection != null){
            connection.close();
        }
    }

}
