package com.b3qTest.dao;

import com.b3qTest.db.JDBCUtils;
import com.b3qTest.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author b3q
 * 实现 user 数据库相关操作接口
 * 包括 添加 查询
 */
public class UserDaoImpl implements UserDao{

    @Override
    public boolean addUser(User user) throws Exception {
        // 定义标识，标记是否添加成功
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
        // 添加 sql 语句，用于添加 user
        String sql = "insert into user(user_name, pwd, name, gender, birthday, email, phone_number, zip ,address)" +
                " value (?,?,?,?,?,?,?,?,?)";

            // 通过 sql 语句，实例化数据库操作对象，操作数据库
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 添加用户名
            pstmt.setString(1,user.getUser_name());

            // 添加密码
            pstmt.setString(2,user.getPwd());

            // 添加姓名
            pstmt.setString(3,user.getName());

            // 添加性别
            pstmt.setString(4,user.getGender());

            // 添加生日
            Date sqlDate = null;
            if(user.getBirthday()!=null){
                sqlDate = new Date(user.getBirthday().getTime());
            }
            System.out.println(sqlDate);
            pstmt.setDate(5, sqlDate);



            // 添加邮箱
            pstmt.setString(6,user.getEmail());

            // 添加电话号码
            pstmt.setLong(7,user.getPhone_number());

            // 添加邮编
            pstmt.setInt(8,user.getZip());

            //添加住址
            pstmt.setString(9,user.getAddress());

            // 判断更新操作是否执行
            if(pstmt.executeUpdate()>0){
                flag = true;
            }

            // 关闭数据库操作
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }

        return flag;
    }

    @Override
    public List<User> findAll(String user_name) throws Exception {
        // 定义 list 返回查询的结果
        List<User> list = new ArrayList<User>();

        try (Connection conn = JDBCUtils.getConnection()) {
            // sql 查询字符串
            String sql = "select user_name, pwd, name, gender, birthday, email, phone_number, zip , address from user";

            PreparedStatement pstmt = null;

            // 如果添加参数则返回当前用户名的数据
            if (user_name!=null&&!"".equals(user_name)){
                sql += " where user_name like ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,"%"+user_name+"%");
            }else {
                pstmt = conn.prepareStatement(sql);
            }

            // 查询结果
            ResultSet rs = pstmt.executeQuery();

            // 逐行添加
            User user =  null;
            while (rs.next()){
                user = new User();
                user.setUser_name(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setBirthday(rs.getDate(5));
                user.setEmail(rs.getString(6));
                user.setPhone_number(rs.getLong(7));
                user.setZip(rs.getInt(8));
                user.setAddress(rs.getString(9));
                list.add(user);
            }

            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }

        return list;
    }

    @Override
    public User findByUserName(String user_name) throws Exception {
        User user = null;

        try (Connection conn = JDBCUtils.getConnection()) {
            // 查询 sql 字符串
            String sql = "select user_name, pwd, name, gender, birthday, email, phone_number, zip ,address from user where user_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user_name);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                user = new User();
                user.setUser_name(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setBirthday(rs.getDate(5));
                user.setEmail(rs.getString(6));
                user.setPhone_number(rs.getLong(7));
                user.setZip(rs.getInt(8));
                user.setAddress(rs.getString(9));
            }
            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }

        // 没有找到则返回 null
        return user;
    }

    @Override
    public List<User> findByPage(int pagesize, int offset) throws Exception {
        // 定义 list 返回查询的结果
        List<User> list = new ArrayList<User>();

        try (Connection conn = JDBCUtils.getConnection()) {
            // sql 查询字符串
            String sql = "select user_name, pwd, name, gender, birthday, email, phone_number, zip , address from user LIMIT ? OFFSET ?";

            offset = (offset-1)*pagesize;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pagesize);
            pstmt.setInt(2, offset);

            // 查询结果
            ResultSet rs = pstmt.executeQuery();

            // 逐行添加
            User user =  null;
            while (rs.next()){
                user = new User();
                user.setUser_name(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setBirthday(rs.getDate(5));
                user.setEmail(rs.getString(6));
                user.setPhone_number(rs.getLong(7));
                user.setZip(rs.getInt(8));
                user.setAddress(rs.getString(9));
                list.add(user);
            }

            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }

        return list;
    }

    @Override
    public int getSQLSize() throws Exception {
        int size = 0;
        try (Connection conn = JDBCUtils.getConnection()) {
            String sql = "select count(*) from user;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            size = rs.getInt(1);

            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }
        return size;
    }

    @Override
    public Boolean deleteByUserName(String username) throws Exception {

        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            String sql = "DELETE from user where user_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            if(pstmt.executeUpdate()>0){
                flag = true;
            }

            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }
        return flag;
    }
}
