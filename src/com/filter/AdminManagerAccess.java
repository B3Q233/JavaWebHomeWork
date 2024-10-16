package com.filter;

import com.b3qTest.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminManagerAccess implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminManagerAccess Filter 启动");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        System.out.println(requestURI);
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute("user");
        if(requestURI.endsWith("/layout-admin.jsp")){
            System.out.println("AdminManagerAccess Filter 开始过滤");
            if(user!=null&&user.getUser_name().equals("admin")){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
