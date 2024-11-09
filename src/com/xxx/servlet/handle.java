package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.listen.UserSessionListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class handle extends HttpServlet {

    public boolean deleteByUserName(String username) {
        boolean flag = false;
        try {
            flag = DAOFactory.getIEmpUserDAOInstance().deleteByUserName(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  flag;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        String param = req.getParameter("param");
        if(operation!=null&&operation.equals("delete")){
            resp.setContentType("text/plain;utf-8");
            boolean ret = deleteByUserName(param);
            if (ret){
                resp.getWriter().write("true");
            }else{
                resp.getWriter().write("false");
            }
        }else if(operation!=null&&operation.equals("getOnlineUser")){
            resp.setContentType("application/json;utf-8");
            Set<String> getUserName =  UserSessionListener.getUserName();
            int cnt = 0;
            JSONObject nameJson[] =new JSONObject[getUserName.size()];
            for(String element : getUserName){
                nameJson[cnt] = new JSONObject();
                nameJson[cnt++].put("name",element);
            }
            JSONObject retJson = new JSONObject();
            retJson.put("names",nameJson);
            resp.getWriter().write(String.valueOf(retJson));
        }
    }
}
