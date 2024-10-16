package com.b3qTest.dao;

import com.b3qTest.pojo.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author b3q
 * 实现 user 数据库相关操作接口
 * 包括 添加 查询
 */
public class UserDaoImpl implements UserDao{


    //数据库操作对象
    private Connection conn = null;

    // 数据库连接对象
    private PreparedStatement pstmt = null;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean addUser(User user) throws Exception {
        // 定义标识，标记是否添加成功
        boolean flag = false;

        // 添加 sql 语句，用于添加 user
        String sql = "insert into user(user_name, pwd, name, gender, birthday, email, phone_number, zip ,address)" +
                " value (?,?,?,?,?,?,?,?,?)";

        // 通过 sql 语句，实例化数据库操作对象，操作数据库
        this.pstmt = this.conn.prepareStatement(sql);

        // 添加用户名
        this.pstmt.setString(1,user.getUser_name());

        // 添加密码
        this.pstmt.setString(2,user.getPwd());

        // 添加姓名
        this.pstmt.setString(3,user.getName());

        // 添加性别
        this.pstmt.setString(4,user.getGender());

        // 添加生日
        Date sqlDate = new Date(user.getBirthday().getTime());
        this.pstmt.setDate(5, sqlDate);

        // 添加邮箱
        this.pstmt.setString(6,user.getEmail());

        // 添加电话号码
        this.pstmt.setLong(7,user.getPhone_number());

        // 添加邮编
        this.pstmt.setInt(8,user.getZip());

        //添加住址
        this.pstmt.setString(9,user.getAddress());

        // 判断更新操作是否执行
        if(this.pstmt.executeUpdate()>0){
            flag = true;
        }

        // 关闭数据库操作
        this.pstmt.close();

        return flag;
    }

    @Override
    public List<User> findAll(String user_name) throws Exception {
        // 定义 list 返回查询的结果
        List<User> list = new ArrayList<User>();

        // sql 查询字符串
        String sql = "select user_name, pwd, name, gender, birthday, email, phone_number, zip , address from user";

        // 如果添加参数则返回当前用户名的数据
        if (user_name!=null&&!"".equals(user_name)){
            sql += " where user_name like ?";
            this.pstmt = this.conn.prepareStatement(sql);
            this.pstmt.setString(1,"%"+user_name+"%");
        }else {
            this.pstmt = this.conn.prepareStatement(sql);
        }

        // 查询结果
        ResultSet rs = this.pstmt.executeQuery();

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
            user.setPhone_number(rs.getInt(7));
            user.setZip(rs.getInt(8));
            user.setAddress(rs.getString(9));
            list.add(user);
        }

        this.pstmt.close();

        return list;
    }


    @Override
    public User findByUserName(String user_name) throws Exception {
        User user = null;

        // 查询 sql 字符串
        String sql = "select user_name, pwd, name, gender, birthday, email, phone_number, zip ,address from user where user_name = ?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setString(1,user_name);

        ResultSet rs = this.pstmt.executeQuery();
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
        this.pstmt.close();

        // 没有找到则返回 null
        return user;
    }
}
