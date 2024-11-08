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
            margin: 30px ;
            padding: 10px;
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
<div style="padding:15px;margin-bottom: -50px">
    <br><br>
    <h1 style="font-size: 2em">网站信息设置</h1>
    <br><br>

    <blockquote class="layui-elem-quote layui-text">
        <ul>
            <li>
                设置网站名称，简介，域名，关键词和logo
            </li>
        </ul>
    </blockquote>

    <br><br><br>

    <table class="layui-hide" id="test"></table>

    <div class="layui-card layui-panel">

    </div>
    <br><br>
</div>

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
                <input id = "siteDomain" type="text" name="site_domain" required lay-verify="required" placeholder="请输入站点域名，以http或https开始" autocomplete="off" class="layui-input">
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

    function updateInfo(data){
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
                console.log(data["data"])
                if(data["status"]!=0){
                    layer.open({
                        type: 1,
                        content:`<div style="padding: 16px;text-align: center">域名格式错误，请重新输入</div>`,
                        title: "警告",
                        area:["250px","160px"]
                    })
                }else if(data["status"]==0){
                    layer.open({
                        type: 1,
                        content:`<div style="padding: 16px;text-align: center">数据更新成功</div>`,
                        title: "提示",
                        area:["250px","160px"]
                    })
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

            }
        });
    }
    function getInfo() {
        $.ajax({
            type: "get",
            url: '/updateSiteInform',
            data: "get",
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
              $("#siteName").val(data["siteName"]);
              $("#siteDescription").val(data["siteDescription"]);
              $("#siteDomain").val(data["siteDomain"]);
              $("#siteKeywords").val(data["siteKeyWords"]);
              $("#upload-show-img").attr("src",data["logoImg"]);
            },
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
           size: 500, // 限制文件大小
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
           progress: function (n, elem, e) {
               element.progress('filter-demo', n + '%');
               if (n == 100) {
                   layer.msg('上传中', {icon: 16});
               }
           }
       });

       form.on('submit(submit)', function(data){
           let formData = data.field; // 获取表单数据
           formData["file"] = $('#upload-show-img').attr("src"); // 添加图片字段
           updateInfo(formData);
           return false; // 阻止表单跳转
       });

   });
    $(document).ready(function(){
        getInfo()
    });
</script>
</body>
</html>

