package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.News;
import com.b3qTest.tool.ComTool;
import com.b3qTest.tool.DBTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class addNews extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        String author = req.getParameter("author");
        String title = req.getParameter("title");
        String column = req.getParameter("column");
        String brief = req.getParameter("brief");
        String briefImg = req.getParameter("briefImg");
        Date date = ComTool.getNowDate();
        News news = new News();
        news.setAuthor(author);
        news.setArticleColumn(column);
        news.setContent(content);
        news.setTitle(title);
        news.setDate(date);
        news.setBriefImg(briefImg);
        news.setBrief(brief);
        if(req.getParameter("id")!=null){
            news.setId(Integer.parseInt(req.getParameter("id")));
            try {
                if(DAOFactory.getIEmpNewsDAOInstance().update(news,"id")){
                    resp.setCharacterEncoding("utf-8");
                    resp.setContentType("application/json;charset=utf-8");
                    resp.getWriter().write("3");
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        boolean flag = false;
        JSONObject reJson = null;
        try {
            flag = DAOFactory.getIEmpNewsDAOInstance().insert(news);
            if (!flag){
                reJson = ComTool.setRetJson(0,"当前栏目文章已经存在，请勿重复提交");
            }else{
                reJson = ComTool.setRetJson(1,"文章提交成功");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(String.valueOf(reJson));
    }
}
