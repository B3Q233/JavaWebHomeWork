package com.xxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author b3q
 * 用于产生验证的servlet 并将验证码存入session 用于其它页面进行验证
 */

public class generateCaptcha extends HttpServlet {

    //字母和数字的字符串，用于生成随机字符
    private final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int LEN = STR.length();

    //根据随机数生成随机字符串
    private int getRandom(int length) {
        int num = (int) (Math.random() * (length));
        return num;
    }

    //生成 0 到 len-1 之间的随机数
    private String getRandomString(int len) {
        String ans = "";
        for (int i = 0; i < len; i++) {
            ans += STR.charAt(getRandom(LEN));
        }
        return ans;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;utf-8");
        int len = Integer.parseInt(req.getParameter("len"));
        String getRString = getRandomString(len);
        //获取session 设置session
        HttpSession session = req.getSession();
        if(req.getParameter("type").equals("login")){
            if(session.getAttribute("login")!=null){
                session.removeAttribute("login");
            }
            session.setAttribute("login",getRString);
        } else if (req.getParameter("type").equals("register")) {
            if(session.getAttribute("register")!=null){
                session.removeAttribute("register");
            }
            System.out.println("验证码："+getRString);
            session.setAttribute("register",getRString);
        }
        String tmp =getRString;
        int pre = getRandom(10);
        getRString=getRandomString(pre)+getRString;
        int suf = 10-pre;
        getRString+=getRandomString(suf);
        getRString = getRString+tmp;
        getRString+=getRandomString(14);
        resp.getWriter().write(getRString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
