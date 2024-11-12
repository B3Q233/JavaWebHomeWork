package com.xxx.servlet;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.News;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class getNewsListToAdmin extends HttpServlet {

    public JSONObject getRetJson(List<News> newsList){
        JSONArray data = new JSONArray();
        JSONObject retJson = new JSONObject();
        for (News news : newsList){
            data.add(JSONObject.from(news));
        }
        retJson.put("data",data);
        retJson.put("msg","ok");
        retJson.put("code",0);
        retJson.put("count",data.size());
        return retJson;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json;utf-8");
            List<News> newsList = DAOFactory.getIEmpNewsDAOInstance().queryAll(new News());
            JSONObject retJson = getRetJson(newsList);
            resp.getWriter().write(String.valueOf(retJson));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
