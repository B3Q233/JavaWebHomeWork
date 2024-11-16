package com.xxx.servlet;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.FirstCategory;
import com.b3qTest.pojo.News;
import com.b3qTest.pojo.SecondCategory;
import com.b3qTest.pojo.ThirdCategory;
import com.b3qTest.tool.ComTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class secondCategoryHandle extends HttpServlet {

    public JSONObject getRetJson(List<SecondCategory> secondCategory){
        JSONArray data = new JSONArray();
        JSONObject retJson = new JSONObject();
        for (SecondCategory  secondCategories: secondCategory){
            data.add(JSONObject.from(secondCategories));
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
            List<SecondCategory> secondCategories = DAOFactory.getIEmpSecondCategoryInstance().queryAll(new SecondCategory());
            int id = 0;
            JSONObject reJson = new JSONObject();
            if(!"".equals(req.getParameter("id"))&&req.getParameter("id")!=null){
                List<SecondCategory> retCategories = new ArrayList<>();
                id = Integer.parseInt(req.getParameter("id"));
                for (SecondCategory secondCategory:secondCategories){
                    if(id==secondCategory.getParentId()){
                        retCategories.add(secondCategory);
                    }
                }
                reJson = getRetJson(retCategories);
            }else{
                reJson = getRetJson(secondCategories);
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
        SecondCategory secondCategory = new SecondCategory();
        String name = req.getParameter("name");
        String firstCategory = req.getParameter("firstCategory");
        String thumbnail = req.getParameter("file");
        int order = Integer.parseInt(req.getParameter("order"));
        int parentId = Integer.parseInt(req.getParameter("parentId"));
        secondCategory.setParentId(parentId);
        String dir = firstCategory+'/'+name;
        secondCategory.setDir(dir);
        secondCategory.setCategoryOrder(order);
        int id = 0;
        if(!"".equals(req.getParameter("id"))){
            id = Integer.parseInt(req.getParameter("id"));
        }
        if (!"".equals(name)) {
            secondCategory.setName(name);
        }
        if(!"".equals(firstCategory)){
            secondCategory.setParentName(firstCategory);
        }
        secondCategory.setImg(thumbnail);
        secondCategory.setId(id);
        boolean flag = DAOFactory.getIEmpSecondCategoryInstance().update(secondCategory,"id",id);
        JSONObject retJson = flag ? ComTool.setRetJson(6, "更新栏目成功") : ComTool.setRetJson(7, "栏目名更新错误，请检查网络");
        writeResponse(resp, retJson);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean flag = DAOFactory.getIEmpSecondCategoryInstance().delete(new SecondCategory(), "id", id);
        JSONObject retJson = flag ? ComTool.setRetJson(3, "删除栏目成功") : ComTool.setRetJson(4, "删除栏目失败，请检查其子栏目是否删除干净");
        writeResponse(resp, retJson);
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        SecondCategory secondCategory = new SecondCategory();
        String name = req.getParameter("name");
        String firstCategory = req.getParameter("firstCategory");
        String thumbnail = req.getParameter("file");
        String dir = firstCategory+'/'+name;
        int parentId = Integer.parseInt(req.getParameter("parentId"));
        int order = Integer.parseInt(req.getParameter("order"));
        if (!"".equals(name)) {
            secondCategory.setName(name);
        }
        if(!"".equals(firstCategory)){
            secondCategory.setParentName(firstCategory);
        }
        secondCategory.setCategoryOrder(order);
        secondCategory.setDir(dir);
        secondCategory.setImg(thumbnail);
        secondCategory.setParentId(parentId);
        boolean flag = DAOFactory.getIEmpSecondCategoryInstance().insert(secondCategory);
        JSONObject retJson = flag ? ComTool.setRetJson(1, "添加栏目成功") : ComTool.setRetJson(0, "栏目名重复");
        writeResponse(resp, retJson);
    }

    private void handleQuery(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = 0;
        if(!"".equals(req.getParameter("id"))){
            id = Integer.parseInt(req.getParameter("id"));
        }else{
            writeResponse(resp, ComTool.setRetJson(5,"查询出错，请检查id"));
        }
        SecondCategory secondCategories = DAOFactory.getIEmpSecondCategoryInstance().findById(new SecondCategory(),"id",id);
        JSONObject json = JSONObject.from(secondCategories);
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
