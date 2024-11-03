<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>站点设置管理</title>
    <!-- 引入layui的CSS文件 -->
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
    <script src="../js/jquery-3.5.1.min.js"></script>
    <!-- 自定义样式（可选） -->
    <style>
        *{
            font-size: 13px;
        }
        .layui-container {
            width: 600px;
            margin: 30px auto;
            padding: 15px;
        }
        .layui-input-block{
            display: flex;
            flex-direction: column;
        }
        .layui-input-block .layui-upload-list{
            width: 200px;
        }
    </style>
</head>
<body>

<div class="layui-container">
    <form class="layui-form" >
        <!-- 站点名称 -->
        <div class="layui-form-item">
            <label class="layui-form-label">站点名称*</label>
            <div class="layui-input-block">
                <input id = "siteName" type="text" name="site_name" required lay-verify="required" placeholder="请输入站点名称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <!-- 站点简介 -->
        <div class="layui-form-item">
            <label class="layui-form-label">简介</label>
            <div class="layui-input-block">
                <textarea id = "siteDescription" name="site_description" placeholder="请输入站点简介" class="layui-textarea" style="resize:none" ></textarea>
            </div>
        </div>
        <!-- 站点域名 -->
        <div class="layui-form-item">
            <label class="layui-form-label">域名(地址)*</label>
            <div class="layui-input-block">
                <input id = "siteDomain" type="text" name="site_domain" required lay-verify="required" placeholder="请输入站点域名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <!-- 站点关键词 -->
        <div class="layui-form-item">
            <label class="layui-form-label">关键词*</label>
            <div class="layui-input-block">
                <input id = "siteKeywords" type="text" name="site_keywords" required lay-verify="required" placeholder="请输入站点关键词，用逗号分隔" autocomplete="off" class="layui-input">
            </div>
        </div>

        <!-- 网站logo -->
       <div class="layui-form-item">
           <label class="layui-form-label">logo设置</label>
           <div class="layui-input-block" style="width: 132px;" >
               <div class="layui-upload-list" >
                   <img class="layui-upload-img" id="upload-show-img" style="width: 100%; height: 92px;">
                   <div id="ID-upload-demo-text"></div>
               </div>
               <button type="button" class="layui-btn" id="upload-img-btn">
                   <i class="layui-icon layui-icon-upload"></i> logo上传
               </button>
           </div>
       </div>
        <!-- 提交按钮 -->
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="submit">立即提交</button>
            </div>
        </div>
   </form>
</div>

<script src="layui/layui.js"></script>
<script>

    function sendAjax(data){
        $.ajax({
            type: "post",
            url: '/updateSiteInform',
            data: data,
            dataType: 'json',
            statusCode: {
                404: function () {
                    alert("404");
                },
                500: function () {
                    alert("500");
                }
            },
            success: function (data) {
                console.log(data);
                setArticle(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("请求失败: " + textStatus + ", 错误信息: " + errorThrown);
            }
        });
    }
   layui.use('form', function(){
       let form = layui.form;
       let upload = layui.upload;
       let layer = layui.layer;
       let element = layui.element;
       let $ = layui.$;

       layer.config({
           // 设置全局弹出层的默认位置居中
           offset: 'c',
       });

       let uploadInst = upload.render({
           elem: '#upload-img-btn',
           url: '/uploadImg',
           size: 1024*5, // 限制文件大小
           before: function (obj) {
               obj.preview(function (index, file, result) {
                   $('#upload-show-img').attr('src', result); // 图片链接（base64）
               });
           },
           done: function (res) {
               $('#ID-upload-img-btn').html(''); // 置空上传失败的状态
           },
           error: function () {
               // 演示失败状态，并实现重传
               let demoText = $('#ID-upload-demo-text');
               demoText.html('<span style="color: #FF5722;top: 52%;left: 50%;">图片上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
               demoText.find('.demo-reload').on('click', function () {
                   uploadInst.upload();
               });
           },
           // 进度条
           progress: function (n, elem, e) {
               element.progress('filter-demo', n + '%');
               if (n == 100) {
                   layer.msg('上传中', {icon: 16});
               }
           }
       });

       form.on('submit(submit)', function(data){
           let formData = data.field; // 获取表单数据
           formData["siteImg"] = $('#upload-show-img').attr("src"); // 添加图片字段
           console.log(formData);
           sendAjax(formData);
           return false; // 阻止表单跳转
       });

   });
</script>
</body>
</html>

