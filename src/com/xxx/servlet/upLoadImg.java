package com.xxx.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSONObject;

public class upLoadImg extends HttpServlet {

    // 获取图片的 src 等的 json 格式
    public JSONObject reImgContent(String name){

        JSONObject retJson = new JSONObject();
        JSONObject data = new JSONObject();
        retJson.put("errno",0);
        data.put("url","/upLoadImg/"+name);
        retJson.put("data",data);
        return retJson;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            try {

                String uploadPath = getServletContext().getRealPath("");

                // 配置工厂
                DiskFileItemFactory factory = new DiskFileItemFactory();

                File file = new File(uploadPath+"tmp");
                File uploadDirTmp = new File(uploadPath);
                if (!uploadDirTmp.exists()) {
                    uploadDirTmp.mkdir();
                }

                factory.setSizeThreshold(1024 * 1024 * 10);

                // 设置临时文件存储位置
                factory.setRepository(file);

                // 创建文件上传处理器
                ServletFileUpload upload = new ServletFileUpload(factory);

                // 设置单个文件的最大大小
                upload.setFileSizeMax(1024 * 1024 * 10); // 10MB

                // 解析请求
                List<FileItem> formItems = upload.parseRequest(request);

                JSONObject retJson = null;

                File uploadDirUpload = new File(uploadPath+"upLoadImg");
                if (!uploadDirUpload.exists()) {
                    uploadDirUpload.mkdir();
                }

                if (formItems != null && formItems.size() > 0) {
                    // 迭代文件项
                    for (FileItem item : formItems) {
                        // 处理非文件项（表单字段）
                        if (!item.isFormField()) {
                            String fileName = new File(item.getName()).getName();
                            String filePath1 = "G:\\work4\\web\\upLoadImg"+File.separator+fileName;
                            String filePath2 = uploadPath+"upLoadImg"+File.separator+fileName;
                            File storeFile1 = new File(filePath1);
                            File storeFile2 = new File(filePath2);
                            // 在服务器上保存文件
                            item.write(storeFile1);
                            item.write(storeFile2);
                            retJson = reImgContent(fileName);
                        }
                    }
                }
                response.getWriter().write(String.valueOf(retJson));
            } catch (Exception ex) {
                // 处理异常
                throw new ServletException("File upload failed", ex);
            }
        } else {
            // 不是multipart/form-data类型，抛出异常
            throw new ServletException("Unsupported file upload request");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
