package com.xxx.servlet;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.FirstCategory;
import com.b3qTest.pojo.News;
import com.b3qTest.tool.ComTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class firstCategoryHandle extends HttpServlet {

    public JSONObject getRetJson(List<FirstCategory> firstCategories){
        JSONArray data = new JSONArray();
        JSONObject retJson = new JSONObject();
        for (FirstCategory firstCategorie : firstCategories){
            data.add(JSONObject.from(firstCategorie));
        }
        retJson.put("data",data);
        retJson.put("msg","ok");
        retJson.put("code",0);
        retJson.put("count",data.size());
        return retJson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<FirstCategory> firstCategories = DAOFactory.getIEmpFirstCategoryInstance().queryAll(new FirstCategory());
            JSONObject reJson = getRetJson(firstCategories);
            resp.setContentType("application/json;utf-8");
            resp.getWriter().write(String.valueOf(reJson));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        resp.setContentType("application/json;utf-8");
        if("del".equals(type)){
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                boolean flag = DAOFactory.getIEmpFirstCategoryInstance().delete(new FirstCategory(),"id",id);
                JSONObject retJson;
                if(flag){
                    retJson = ComTool.setRetJson(3, "删除栏目成功");
                }else{
                    retJson = ComTool.setRetJson(4, "删除栏目失败,请检查其子栏目是否删除干净");
                }
                resp.getWriter().write(String.valueOf(retJson));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if ("add".equals(type)) {
            FirstCategory firstCategory = new FirstCategory();
            if(!"".equals(req.getParameter("name"))){
                firstCategory.setName(req.getParameter("name"));
            }
            try {
                boolean flag = DAOFactory.getIEmpFirstCategoryInstance().insert(firstCategory);
                JSONObject retJson;
                if(flag){
                    retJson = ComTool.setRetJson(1, "添加栏目成功");
                }else{
                    retJson = ComTool.setRetJson(0, "栏目名重复");
                }
                resp.getWriter().write(String.valueOf(retJson));
            } catch (Exception e) {
                 throw new RuntimeException(e);
            }
        } else if ("query".equals(type)) {
            try {
                DAOFactory.getIEmpFirstCategoryInstance().queryAll(new FirstCategory());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
