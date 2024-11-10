package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.SessionUser;
import com.b3qTest.pojo.User;
import com.listen.UserSessionListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
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
        if("delete".equals(operation)){
            resp.setContentType("text/plain;utf-8");
            boolean ret = deleteByUserName(param);
            if (ret){
                resp.getWriter().write("true");
            }else{
                resp.getWriter().write("false");
            }
        }else if("getOnlineUser".equals(operation)){
            resp.setContentType("application/json;utf-8");
            Set<SessionUser> getUser =  UserSessionListener.getOnlineUsers();
            int cnt = 0;
            JSONObject[] nameJson =new JSONObject[getUser.size()];
            for(SessionUser element : getUser){
                nameJson[cnt] = new JSONObject();
                nameJson[cnt].put("sessionId",element.getSessionId());
                nameJson[cnt++].put("name",element.getUserName());
            }
            JSONObject retJson = new JSONObject();
            retJson.put("names",nameJson);
            resp.getWriter().write(String.valueOf(retJson));
        }else if("deleteSession".equals(operation)){
            UserSessionListener.removeUser(param);
        }else if("getUserInform".equals(operation)){
            User user = (User) req.getSession().getAttribute("user");
            if(user!=null){
                resp.getWriter().write(user.getUser_name());
            }
        }else if("leaveAccount".equals(operation)){
            UserSessionListener.removeUser(req.getSession().getId());
        }
    }
}
