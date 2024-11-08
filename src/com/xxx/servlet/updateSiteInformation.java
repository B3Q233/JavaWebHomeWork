package com.xxx.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.SiteInfo;
import com.b3qTest.tool.ComTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class updateSiteInformation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
             List<SiteInfo> siteInfoList = DAOFactory.getIEmpSiteInfoDao().queryAll(new SiteInfo());
             if(siteInfoList.size()>0&&siteInfoList!=null){
                 resp.setContentType("application/json;charset=utf-8;");
                 JSONObject siteJson = JSONObject.from(siteInfoList.get(0));
                 resp.getWriter().write(String.valueOf(siteJson));
             }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String siteName = req.getParameter("site_name");
        String siteDescription = req.getParameter("site_description");
        String siteDomain = req.getParameter("site_domain");
        if(!ComTool.checkDomain(siteDomain)){
            resp.setContentType("application/json;charset=utf-8;");
            String retJson = String.valueOf(ComTool.setRetJson(1,"域名格式错误，请重新输入"));
            resp.getWriter().write(retJson);
            return;
        }
        String siteKeywords = req.getParameter("site_keywords");
        String siteImg = req.getParameter("file");
        SiteInfo siteInfo = new SiteInfo();
        siteInfo.setId(1);
        siteInfo.setSiteName(siteName);
        siteInfo.setSiteDomain(siteDomain);
        siteInfo.setSiteDescription(siteDescription);
        siteInfo.setLogoImg(siteImg);
        siteInfo.setSiteKeyWords(siteKeywords);
        try {
            if(DAOFactory.getIEmpSiteInfoDao().update(siteInfo,"id")){
                String retJson = String.valueOf(ComTool.setRetJson(0,"数据更新成功"));
                resp.setContentType("application/json;charset=utf-8;");
                resp.getWriter().write(retJson);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
