package com.xxx.servlet;

import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.News;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteNews extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean f = false;
        if(req.getParameter("id")!=null){
            int id = Integer.parseInt(req.getParameter("id"));
            System.out.println(id);
            try {
                f = DAOFactory.getIEmpNewsDAOInstance().delete(id);
                if(f){
                    resp.setContentType("application/json;utf-8");
                    resp.getWriter().write("1");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
