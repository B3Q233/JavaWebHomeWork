package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.News;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class newsBrief extends HttpServlet {

    public JSONObject getNewsList(List<News> list,int size){
        JSONObject reJson = new JSONObject();
        ArrayList<JSONObject> newsJson = new ArrayList<>();
        for (News news : list){
            newsJson.add(JSONObject.from(news));
        }
        reJson.put("size",size);
        reJson.put("data",newsJson);
        return reJson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String style = req.getParameter("style");
        String column = req.getParameter("column");
        int pagesize = Integer.parseInt(req.getParameter("pagesize"));
        int offset = Integer.parseInt(req.getParameter("offset"));
        JSONObject reJson = null;
        if(style!=null&&style.equals("all")){
            try {
                List<News> newsList = DAOFactory.getIEmpNewsDAOInstance().queryAll(new News());
                reJson = getNewsList(newsList,newsList.size());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                List<News> newsList = DAOFactory.getIEmpNewsDAOInstance().findByColumn(pagesize,offset,column);
                reJson = getNewsList(newsList,DAOFactory.getIEmpNewsDAOInstance().getSizeByColumn(column));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(reJson.toString());
        }
    }
}