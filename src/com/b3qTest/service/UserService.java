package com.b3qTest.service;

import com.b3qTest.dao.UserDao;
import com.b3qTest.dao.UserDaoImpl;
import com.b3qTest.db.DBConnection;
import com.b3qTest.pojo.User;

import java.util.List;

/**
 * @author b3q
 * 应用层操作
 */
public class UserService implements UserDao {

    // 数据库连接对象
    private DBConnection dbcon = null;

    // UserDao 对象 实现应用
    private UserDao dao = null;

    public UserService() {
        try{
            this.dbcon = new DBConnection();
            this.dao = new UserDaoImpl(this.dbcon.getConnection());
        }catch (Exception e){
            throw new RuntimeException("创建链接失败",e);
        }
    }

    @Override
    public boolean addUser(User user) throws Exception {
        boolean flag = false;
        try {
            if(user.getUser_name()!=null&&!"".equals(user.getUser_name())){
                flag = this.dao.addUser(user);
            }
        }catch (Exception e){
            throw e;
        } finally {
            this.dbcon.close();
        }
        return flag;
    }

    @Override
    public List<User> findAll(String user_name) throws Exception {
        List<User> all = null;
        try{
            all = this.dao.findAll(user_name);
        }catch (Exception e){
            throw e;
        }finally {
            this.dbcon.close();
        }
        return all;
    }

    @Override
    public User findByUserName(String user_name) throws Exception {
        User user = null;
        try{
            user = this.dao.findByUserName(user_name);
        }catch (Exception e){
            throw e;

        }finally {
            this.dbcon.close();
        }
        return user;
    }
}
