package com.xxx.servlet;

import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.User;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * @author ASUS
 * 正则表达式 测试
 * ok
 * 验证码生成 测试
 * ok
 */

public class test {

    public static void main(String[] args) throws Exception {
        List<User> users;
        users = DAOFactory.getIEmpDAOInstance().findByPage(10,3);
        for (int i =0 ;i<users.size();i++){
            System.out.println(users.get(i).getUser_name());
            System.out.println(users.get(i).getName());
            System.out.println(users.get(i).getGender());
            System.out.println(users.get(i).getEmail());
            System.out.println(users.get(i).getPhone_number());
            System.out.println(users.get(i).getZip());
        }
        System.out.println();
    }

}
