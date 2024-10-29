package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getSpecificNews extends HttpServlet {

    public JSONObject getRetJson(int id) throws Exception {
        JSONObject retJson = null;
        retJson = JSONObject.from(DAOFactory.getIEmpNewsDAOInstance().findNews(id));
        return retJson;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        JSONObject retJson = null;
        try {
             retJson = getRetJson(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(String.valueOf(retJson));
    }
}
