package com.xxx.servlet;

import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



/**
 * @author b3q
 * 接收登录页面post参数 并完成登录验证的servlet
 */
public class login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String captcha = (String) session.getAttribute("login");
        captcha = captcha.toLowerCase();
        String getCaptcha = req.getParameter("captcha");
        if (getCaptcha != null) {
            getCaptcha = getCaptcha.toLowerCase();
        }else {
            getCaptcha="";
        }
        User user;
        try {
            user = DAOFactory.getIEmpUserDAOInstance().findByUserName(req.getParameter("username"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (user == null) {
            resp.setContentType("text/plain");
            resp.getWriter().write("1");
        } else if (!user.getPwd().equals(req.getParameter("pwd"))) {
            resp.setContentType("text/plain");
            resp.getWriter().write("2");
        } else if (!getCaptcha.equals(captcha)) {
            resp.setContentType("text/plain");
            resp.getWriter().write("3");
        } else {
            resp.setContentType("text/plain");
            if(session.getAttribute("user")!=null&&!session.getAttribute("user").equals("")){
                session.removeAttribute("user");
            }
            session.setAttribute("user",user);
            resp.getWriter().write("");
        }
    }

}
