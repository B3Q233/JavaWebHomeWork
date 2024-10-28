package com.xxx.servlet;

import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.News;
import com.b3qTest.tool.ComTool;

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
        Date date = ComTool.getNowDate();
        News news = new News();
        news.setAuthor(author);
        news.setArticle_column(column);
        news.setContent(content);
        news.setTitle(title);
        news.setDate(date);
        boolean flag = false;
        try {
            flag = DAOFactory.getIEmpNewsDAOInstance().insert(news);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(flag);
    }
}
