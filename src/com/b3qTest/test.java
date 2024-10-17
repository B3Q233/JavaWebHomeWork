package com.b3qTest;

import com.b3qTest.factory.DAOFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author b3q
 * 用户相关功能 测试用
 * ok
 */

public class test {
    public static Date changeDateFormat(String str) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(str);
        return date;
    }

    public static void main(String[] args) throws Exception {

        Boolean f = DAOFactory.getIEmpDAOInstance().deleteByUserName("b3131232s2");
        System.out.println(f);

        //System.out.println("good luck");

        //test add
        //User user =new User();
        //user.setUser_name("b3qq2");
        //user.setName("mxxy");
        //user.setPhone_number(114514255);
        //user.setEmail("114514@qq.com");
        //System.out.println(changeDateFormat("1145-11-04"));
        //user.setBirthday(changeDateFormat("1145-11-04"));
        //user.setPwd("ddnn114514");
        //user.setGender("男");
        //user.setZip(114514);
        //DAOFactorty.getIEmpDAOInstance().addUser(user);

        //test query
        //User user = DAOFactorty.getIEmpDAOInstance().findByUserName("b3q1");
        //System.out.println(user);

        //List<User> list = DAOFactorty.getIEmpDAOInstance().findAll("");
        //System.out.println(list);
    }

}
