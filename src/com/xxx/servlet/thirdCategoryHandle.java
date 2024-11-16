package com.xxx.servlet;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.*;
import com.b3qTest.pojo.ThirdCategory;
import com.b3qTest.tool.ComTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class thirdCategoryHandle extends HttpServlet {

    public JSONObject getRetJson(List<ThirdCategory> thirdCategories){
        JSONArray data = new JSONArray();
        JSONObject retJson = new JSONObject();
        for (ThirdCategory  thirdCategory: thirdCategories){
            data.add(JSONObject.from(thirdCategory));
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
            List<ThirdCategory> thirdCategories = DAOFactory.getIEmpThirdCategoryInstance().queryAll(new ThirdCategory());
            int id = 0;
            JSONObject reJson = new JSONObject();
            if(!"".equals(req.getParameter("id"))&&req.getParameter("id")!=null){
                List<ThirdCategory> retCategories = new ArrayList<>();
                id = Integer.parseInt(req.getParameter("id"));
                for (ThirdCategory thirdCategory:thirdCategories){
                    if(id==thirdCategory.getParentId()){
                        retCategories.add(thirdCategory);
                    }
                }
                reJson = getRetJson(retCategories);
            }else{
                reJson = getRetJson(thirdCategories);
            }
            writeResponse(resp, reJson);
        } catch (Exception e) {
            throw new ServletException("Error processing GET request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        resp.setContentType("application/json;utf-8");
        try {
            switch (type) {
                case "del" -> handleDelete(req, resp);
                case "add" -> handleAdd(req, resp);
                case "query" -> handleQuery(req, resp);
                case "update" -> handleUpdate(req,resp);
                default -> writeResponse(resp, ComTool.setRetJson(5, "Invalid operation"));
            }
        } catch (Exception e) {
            throw new ServletException("Error processing POST request", e);
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ThirdCategory thirdCategory = new ThirdCategory();
        String name = req.getParameter("name");
        String secondCategory = req.getParameter("secondCategoryName");
        String secondCategoryId = req.getParameter("secondCategoryId");
        int order = Integer.parseInt(req.getParameter("order"));
        SecondCategory secondCate = DAOFactory.getIEmpSecondCategoryInstance().findById(new SecondCategory(),"name",secondCategory);
        int parentId = secondCate.getId();
        String dir =secondCate.getDir() +"/"+name;
        if (!"".equals(name)) {
            thirdCategory.setName(name);
        }
        if(!"".equals(secondCategory)){
            thirdCategory.setParenName(secondCategory);
        }
        int id = Integer.parseInt(req.getParameter("id"));
        thirdCategory.setParentId(Integer.parseInt(secondCategoryId));
        thirdCategory.setCategoryOrder(order);
        thirdCategory.setDir(dir);
        thirdCategory.setId(id);
        thirdCategory.setParentId(parentId);
        boolean flag = DAOFactory.getIEmpThirdCategoryInstance().update(thirdCategory,"id",id);
        JSONObject retJson = flag ? ComTool.setRetJson(6, "更新栏目成功") : ComTool.setRetJson(7, "栏目名更新错误，请检查网络");
        writeResponse(resp, retJson);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean flag = DAOFactory.getIEmpThirdCategoryInstance().delete(new ThirdCategory(), "id", id);
        JSONObject retJson = flag ? ComTool.setRetJson(3, "删除栏目成功") : ComTool.setRetJson(4, "删除栏目失败，请将其子栏目全部删除后再试");
        writeResponse(resp, retJson);
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ThirdCategory thirdCategory = new ThirdCategory();
        String name = req.getParameter("name");
        String secondCategory = req.getParameter("secondCategoryName");
        String secondCategoryId = req.getParameter("secondCategoryId");
        int order = Integer.parseInt(req.getParameter("order"));
        SecondCategory secondCate = DAOFactory.getIEmpSecondCategoryInstance().findById(new SecondCategory(),"name",secondCategory);
        String dir =secondCate.getDir() +"/"+name;
        if (!"".equals(name)) {
            thirdCategory.setName(name);
        }
        if(!"".equals(secondCategory)){
            thirdCategory.setParenName(secondCategory);
        }
        thirdCategory.setParentId(Integer.parseInt(secondCategoryId));
        thirdCategory.setCategoryOrder(order);
        thirdCategory.setDir(dir);
        boolean flag = DAOFactory.getIEmpThirdCategoryInstance().insert(thirdCategory);
        JSONObject retJson = flag ? ComTool.setRetJson(1, "添加栏目成功") : ComTool.setRetJson(0, "栏目名重复");
        writeResponse(resp, retJson);
    }

    private void handleQuery(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = 0;
        if(!"".equals(req.getParameter("id"))){
            id = Integer.parseInt(req.getParameter("id"));
        }else{
            writeResponse(resp, ComTool.setRetJson(5,"三级栏目查询出错，请检查id"));
        }
        ThirdCategory thirdCategories = DAOFactory.getIEmpThirdCategoryInstance().findById(new ThirdCategory(),"id",id);
        JSONObject json = JSONObject.from(thirdCategories);
        JSONObject reJson = new JSONObject();
        reJson.put("status",4);
        reJson.put("data",json);
        writeResponse(resp, reJson);
    }

    private void writeResponse(HttpServletResponse resp, JSONObject jsonObject) throws IOException {
        resp.setContentType("application/json;utf-8");
        resp.getWriter().write(String.valueOf(jsonObject));
    }

}
