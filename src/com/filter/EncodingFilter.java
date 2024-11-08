package com.filter;

//import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//编码过滤器
@WebFilter(
        description = "字符编码过滤器",
        filterName = "encodingFilter",
        urlPatterns = {"/*"}
)
public class EncodingFilter implements Filter {

    private String encoding = "UTF-8";
    private String filterName = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream("G:\\work2\\src\\com\\filter\\config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        encoding = pro.getProperty("encoding");
        filterName = filterConfig.getFilterName();
        if(encoding==null||"".equals(encoding)){
            encoding="UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        filterChain.doFilter(req,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
