package com.b3qTest.tool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author b3q
 * 数据库操作类
 * 使用反射封装一些数据库公用方法
 */
public class DBTool {

    /**
     * 反射优化数据库插入，传入表对应的JAVABean对象，根据对象动态插入对应的表中
     * @param obj JAVABean 对象
     * @param conn 数据库连接对象
     * @return boolean true表示插入成功,否则表示插入失败
     * @throws SQLException
     */
    public static boolean insert(Object obj, Connection conn) throws SQLException {
        boolean flag = false;
        //获取obj的Class
        Class<?> c = obj.getClass();

        //利用StringBuffer进行插入SQL语句的构造
        //通过反射获取类名映射表名
        StringBuilder  sb1 = new StringBuilder("insert into " + c.getSimpleName().toLowerCase() + "(");
        StringBuilder  sb2 = new StringBuilder(" values(");

        Field[] field = c.getDeclaredFields();

        for (int i = 0; i < field.length; i++) {        //遍历属性构造SQL语句
            if (i != field.length - 1) {
                sb1.append(field[i].getName()).append(",");
                sb2.append("?,");
            } else {
                sb1.append(field[i].getName()).append(")");
                sb2.append("?);");
            }
        }

        // 构造 sql 语句
        String sql = sb1.append(sb2).toString();

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < field.length; i++) {
                field[i].setAccessible(true);        //设置属性的可访问性，可以访问私有属性
                try {                                //通过Field的get(Object)方法获取Object对象的属性值
                    pstmt.setObject(i + 1, field[i].get(obj));    //对预编译的SQL语句中的？进行赋值
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            int cnt = pstmt.executeUpdate();
            flag = cnt > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete data due to reflection error", e);
        }

        return flag;
    }

    /**
     * 反射优化数据删除，传入表对应的JAVABean对象和属性，根据对象动态删除对应的表中数据
     * @param obj JAVABean 对象
     * @param conn 数据库连接对象
     * @param key  对应的属性
     * @return boolean true表示删除成功,否则表示删除失败
     * @throws SQLException
     */
    public static boolean delete(Object obj, Connection conn,String key) throws SQLException{
        boolean flag = false;

        Class<?> clazz = obj.getClass();
        StringBuilder sql = new StringBuilder("DELETE FROM ");

        sql.append(clazz.getSimpleName().toLowerCase()).append(" WHERE ").append(key).append(" = ?");

        // 自动关闭 pstmt
        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            Field field = clazz.getDeclaredField(key);
            field.setAccessible(true);
            pstmt.setObject(1, field.get(obj));
            int cnt = pstmt.executeUpdate();
            flag = cnt > 0;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete data due to reflection error", e);
        }

        return flag;
    }

    /**
     * 反射优化数据查询，传入表对应的JAVABean对象，根据对象动态查询对应的表中所有的数据
     * @param obj  JAVABean 对象
     * @param conn 数据库连接对象
     * @return List<obj> 传入的对象类型的list
     * @throws SQLException
     */
    public static <T> List<T> queryAll(Object obj, Connection conn) throws SQLException{
        List<T> resultList = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ").append(tableName);

        try (PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                T instance = (T) clazz.getDeclaredConstructor().newInstance(); // 创建一个新的实例
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true); // 使得私有字段也可以访问
                    Object value = rs.getObject(field.getName()); // 从结果集中获取值
                    field.set(instance, value); // 设置字段值
                }
                resultList.add(instance);
            }
        } catch (Exception e) {
            throw new SQLException("Error querying data", e);
        }
        return resultList;
    }


    /**
     * 反射优化数据更新，传入表对应的JAVABean对象，根据对象动态更新对应的表中所有的数据
     * @param obj  JAVABean 对象
     * @param conn 数据库连接对象
     * @return List<obj> 传入的对象类型的list
     * @throws SQLException
     */
    public static boolean update(Object obj, Connection conn,String primaryKey) throws SQLException {
        boolean flag = false;
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase(); // 类名和表名相同

        // 构建更新语句
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        StringBuilder whereClause = new StringBuilder(" WHERE id = ?"); // 字段名为 "id"
        PreparedStatement pstmt = null;

        try{
            Field[] fields = clazz.getDeclaredFields();
            Object primaryKeyValue = null;

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);

                // 如果是主键字段，则构建 WHERE 子句
                if (fieldName.equalsIgnoreCase(primaryKey)) {
                    primaryKeyValue = value;
                    continue;
                }

                sql.append(fieldName).append(" = ?");
                if (i < fields.length - 1) {
                    sql.append(", ");
                }
            }

            sql.append(whereClause);
            pstmt = conn.prepareStatement(sql.toString());
            int index = 1;
            for (Field field : fields) {
                if (!field.getName().equalsIgnoreCase(primaryKey)) {
                    field.setAccessible(true);
                    pstmt.setObject(index++, field.get(obj));
                }
            }

            // 设置主键值
            if (primaryKeyValue != null) {
                pstmt.setObject(index, primaryKeyValue);
            } else {
                throw new SQLException("Primary key value not found");
            }
            int affectedRows = pstmt.executeUpdate();
            flag = affectedRows > 0;
        } catch (IllegalAccessException e) {
            throw new SQLException("Error accessing field value", e);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

        return flag;
    }

}
