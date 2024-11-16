package com.b3qTest.tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
     * @param value  属性对应的值
     * @return boolean true表示删除成功,否则表示删除失败
     * @throws SQLException
     */
    public static boolean delete(Object obj, Connection conn,String key,Object value) throws SQLException{
        boolean flag = false; // 初始化标志位，默认为删除失败

        // 获取对象的类类型
        Class<?> clazz = obj.getClass();
        // 获取对象中指定属性的Field对象
        Field field;
        try {
            field = clazz.getDeclaredField(key);
            // 设置私有属性也可以访问
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new SQLException("Field not found: " + key, e);
        }

        // 构建删除SQL语句
        String sql = "DELETE FROM " + clazz.getSimpleName().toLowerCase() + " WHERE " + key + " = ?";

        // 使用PreparedStatement执行SQL语句
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 获取属性的类型
            Class<?> fieldType = field.getType();
            // 设置SQL语句中的参数值，根据属性类型进行转换
            if (fieldType == int.class || fieldType == Integer.class) {
                pstmt.setInt(1, (Integer) value);
            } else if (fieldType == long.class || fieldType == Long.class) {
                pstmt.setLong(1, (Long) value);
            } else if (fieldType == double.class || fieldType == Double.class) {
                pstmt.setDouble(1, (Double) value);
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                pstmt.setBoolean(1, (Boolean) value);
            } else if (fieldType == String.class) {
                pstmt.setString(1, (String) value);
            } else {
                // 对于其他类型，可以使用 setObject 方法
                pstmt.setObject(1, value);
            }
            // 执行删除操作
            int affectedRows = pstmt.executeUpdate();
            // 如果有行被删除，则设置标志位为true
            flag = affectedRows > 0;
        }

        // 返回删除操作的结果
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
     * @param primaryKey 主键
     * @return 一个布尔值，为真表示更新成功，否则更新失败
     * @throws SQLException
     */
    public static boolean update(Object obj, Connection conn,String primaryKey) throws SQLException {
        boolean flag = false;
        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName().toLowerCase(); // 类名和表名相同

        // 构建更新语句
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        StringBuilder whereClause = new StringBuilder(" WHERE id = ?");
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

    /**
     * 反射优化数据更新，传入表对应的JAVABean对象，根据对象动的主键和对应的主键值更新对应的表中所有的数据
     * @param obj  JAVABean 对象
     * @param conn 数据库连接对象
     * @param primaryKey 主键
     * @param primaryKey 主键值
     * @return 一个布尔值，为真表示更新成功，否则更新失败
     * @throws SQLException
     */
    public static boolean update(Object obj, Connection conn, String primaryKey, Object primaryKeyValue) throws SQLException {
        if (obj == null || conn == null || primaryKey == null || primaryKeyValue == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        Class<?> clazz = obj.getClass();
        String tableName = clazz.getSimpleName();
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true); // 设置私有属性也可以访问
            sql.append(field.getName()).append(" = ?");
            if (i < fields.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE ").append(primaryKey).append(" = ?");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    pstmt.setObject(index++, field.get(obj));
                } catch (IllegalAccessException e) {
                    throw new SQLException("更新时无法访问字段值", e);
                }
            }
            pstmt.setObject(index, primaryKeyValue);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * 反射优化数据查询，传入表对应的JAVABean对象，根据对象主属性以及其值获取对象
     * @param clazz  JAVABean 对象
     * @param conn 数据库连接对象
     * @param key 主属性
     * @param value 对应的值
     * @return JAVABean 对应的JAVABean 对象
     * @throws SQLException
     */

    public static <T> T findByKey(Class<T> clazz, Connection conn, String key, Object value) throws SQLException {
        // 构建SQL查询语句
        String tableName = clazz.getSimpleName().toLowerCase(); // 假设表名与类名相同，且为小写
        String sql = "SELECT * FROM " + tableName + " WHERE " + key + " = ?";

        // 使用PreparedStatement进行查询
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, value); // 设置查询条件
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 创建一个新的JavaBean实例
                    T bean = clazz.getDeclaredConstructor().newInstance();
                    // 获取JavaBean的所有字段
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        // 设置字段可访问
                        field.setAccessible(true);
                        // 设置字段值
                        field.set(bean, rs.getObject(field.getName()));
                    }
                    return bean;
                }
            }
        } catch (Exception e) {
            // 处理反射异常
            throw new RuntimeException("Error occurred during reflection", e);
        }
        return null; // 如果没有找到结果，返回null
    }

    /**
     * 反射优化数据查询，传入表对应的JAVABean对象，根据对象主属性以及其值获取对象列表
     * @param clazz  JAVABean 对象
     * @param conn 数据库连接对象
     * @param key 主属性
     * @param value 对应的值
     * @return list<JAVABean></> 对应的JAVABean 对象
     * @throws SQLException
     */
    public static <T> List<T> getDataList(Class<T> clazz, Connection conn, String key, Object value) throws SQLException {
        List<T> resultList = new ArrayList<>();

        // 获取表名，简单起见将类名首字母小写作为表名
        String tableName = clazz.getSimpleName().toLowerCase();

        // 构建查询语句
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE ").append(key).append(" =?");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            // 设置查询条件参数
            pstmt.setObject(1, value);

            try (ResultSet rs = pstmt.executeQuery()) {
                // 获取JavaBean类的所有字段（属性）
                Field[] fields = clazz.getDeclaredFields();
                while (rs.next()) {
                    // 通过反射创建JavaBean实例
                    T instance = clazz.getDeclaredConstructor().newInstance();
                    for (Field field : fields) {
                        // 设置字段可访问（因为可能是私有字段）
                        field.setAccessible(true);
                        // 获取字段对应的数据库列名（这里简单假设字段名和列名一致，实际可能需映射）
                        String columnName = field.getName();
                        // 根据ResultSet结果集设置JavaBean实例的属性值
                        Object columnValue = rs.getObject(columnName);
                        field.set(instance, columnValue);
                    }
                    resultList.add(instance);
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return resultList;
    }

}
