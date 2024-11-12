package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class getUsers extends HttpServlet {

    public JSONObject getData(List<User> users,int size){
        JSONObject retJson = new JSONObject();
        retJson.put("code",200);
        retJson.put("msg","所有用户");
        retJson.put("count",size);
        JSONObject[] item = new JSONObject[users.size()];
        for(int i = 0;i<users.size();i++){
            item[i] = new JSONObject();
            item[i].put("id",i+1);
            item[i].put("username",users.get(i).getUser_name());
            item[i].put("name",users.get(i).getName());
            item[i].put("gender",users.get(i).getGender());
            item[i].put("email",users.get(i).getEmail());
            item[i].put("birthday",users.get(i).getBirthday());
            item[i].put("phone",users.get(i).getPhone_number());
            item[i].put("address",users.get(i).getAddress());
            item[i].put("zip",users.get(i).getZip());
        }
        retJson.put("data",item);
        return  retJson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        int pagesize = Integer.valueOf(req.getParameter("limit"));
        int offset = Integer.valueOf(req.getParameter("page"));
        List<User> users;
        int size = 0;
        try {
           users = DAOFactory.getIEmpUserDAOInstance().findByPage(pagesize,offset);
           size = DAOFactory.getIEmpUserDAOInstance().getSQLSize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JSONObject retJson = getData(users,size);
        resp.getWriter().write(String.valueOf(retJson));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req,resp);
    }
}
