<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>新闻</title>
    <link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
    <link rel="stylesheet" href="../js/wangEditor/css/style.css">
    <script src="../layui/layui.js"></script>
    <script src="../js/jquery-3.5.1.min.js"></script>
    <script src="../js/wangEditor/index.js"></script>
    <link rel="stylesheet" href="../style/editor/view.css">
    <link rel="stylesheet" href="../style/editor/normal.css">
</head>
<body>
<style>
    #editor—wrapper {
        border: 1px solid #ccc;
        z-index: 100; /* 按需定义 */
    }

    #toolbar-container {
        border-bottom: 1px solid #ccc;
    }

    #editor-container {
        height: 500px;
    }


</style>
<div style="padding:15px">
    <br><br>
    <h1>文章编辑</h1>
    <br><br>

    <blockquote class="layui-elem-quote layui-text">
        <ul>
            <li>
                设置文章标题
            </li>
            <li>
                选择文章的栏目
            </li>
            <li>
                编写文章的简介
            </li>
            <li>
                上传文章缩略图
            </li>
            <li>
                输入作者的名称
            </li>
            <li>
                编辑文章的内容
            </li>
        </ul>
    </blockquote>

    <br><br><br>

    <table class="layui-hide" id="test"></table>

    <div class="layui-card layui-panel">

    </div>
    <br><br>
</div>
<form class="layui-form">

        <!-- 标题 -->
        <div class="layui-form-item">
            <label class="layui-form-label">标题</label>
            <div class="layui-input-block">
                <input type="text" name="title" id="title" required lay-verify="required" placeholder="请输入标题"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <!-- 作者 -->
        <div class="layui-form-item">
            <label class="layui-form-label">作者</label>
            <div class="layui-input-block">
                <input type="text" name="title" id="author" required lay-verify="required" placeholder="请输入作者名称"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <!-- 简介 -->
        <div class="layui-form-item">
            <label class="layui-form-label">简介</label>
            <div class="layui-input-block">
                <input type="text" name="title" id="brief" required lay-verify="required" placeholder="请输入文章简介"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

    <!-- 一级栏目 -->
    <div class="layui-form-item">
        <label class="layui-form-label">一级栏目</label>
        <div class="layui-input-block">
            <select id="column1" lay-filter="column1" lay-verify="required">
                <option value="">请选择一级栏目</option>
            </select>
        </div>
    </div>
    <!-- 二级栏目 -->
    <div class="layui-form-item">
        <label class="layui-form-label">二级栏目</label>
        <div class="layui-input-block">
            <select id="column2" lay-filter="column2" lay-verify="required">
                <option value="">请选择二级栏目</option>
            </select>
        </div>
    </div>
    <!-- 三级栏目 -->
    <div class="layui-form-item">
        <label class="layui-form-label">三级栏目</label>
        <div class="layui-input-block">
            <select id="column3" lay-filter="column3" lay-verify="required">
                <option value="">请选择三级栏目</option>
            </select>
        </div>
    </div>


        <!-- 缩略图回显 -->
        <div class="layui-form-item">
            <label class="layui-form-label">上传图片</label>
            <div class="layui-input-block" style="width: 132px;">
                <div class="layui-upload-list">
                    <img class="layui-upload-img" id="upload-show-img" style="width: 100%; height: 92px;">
                    <div id="ID-upload-demo-text"></div>
                </div>
                <button type="button" class="layui-btn" id="upload-img-btn">
                    <i class="layui-icon layui-icon-upload"></i> 图片上传
                </button>
            </div>
        </div>

        <!-- 内容 -->
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <!-- 创建编辑器容器 -->
                <div id="editor—wrapper">
                    <div id="toolbar-container"><!-- 工具栏 --></div>
                    <div id="editor-container"><!-- 编辑器 --></div>
                </div>
            </div>
        </div>

        <!-- 提交按钮 -->
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="submit">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
</form>


<script>
    let newsId = 0
    function sendNewsData(data) {
        $.ajax({
            type: "post",
            url: "/admin/addNews",
            //data:定义数据,以键值对的方式放在大括号里
            data: data,
            dataType: 'json',
            //statusCode:状态码，用于定义执行时提示的状态
            statusCode: {
                404: function () {
                    alert("404");
                },
                500: function () {
                    alert("500");
                }
            },
            success: function (data) {
                console.log(data["status"]);
                layui.use('layer', function () {
                    let layer = layui.layer;
                    if (data["status"] === 1) {
                        layer.open({
                            type:1,
                            content:data["data"],
                            btn:["确认"],
                            offset: ['30%', '40%']
                        })
                    } else if (data["status"] === 0) {
                        layer.open({
                            type:1,
                            content:data["data"],
                            btn:["确认"],
                            offset: ['30%', '40%']
                        })
                    } else if(data==3){
                        layer.open({
                            type:1,
                            content:"更新成功",
                            btn:["确认"],
                            offset: ['30%', '40%']
                        })
                    }
                });
            }
        });
    }
</script>

<script>

    let getHtml = ""

    const {createEditor, createToolbar} = window.wangEditor

    const editorConfig = {
        placeholder: '请输入文章内容....',
        MENU_CONF: {
            uploadImage: {
                server: '/uploadImg',
                maxFileSize: 10 * 1024 * 1024,
            }
        },
    }

    const editor = createEditor({
        selector: '#editor-container',
        html: '',
        config: editorConfig,
        mode: 'default', // or 'simple'
    })

    const toolbarConfig = {}

    const toolbar = createToolbar({
        editor,
        selector: '#toolbar-container',
        config: toolbarConfig,
        mode: 'default', // or 'simple'
    })


</script>

<script>

    let categoryId = 0;

    function getCategory1(param){
        initColum();
        $.ajax({
            url: '/firstCategoryHandle', // 假设这是获取一级栏目的API端点
            type: 'GET',
            dataType: 'json', // 期望返回的数据类型
            success: function(data) {
                // 假设返回的数据是一个数组，每个元素包含id和name属性
                let colum = data.data;
                let select = $('#column1');
                select.find('option:not(:first)').remove();
                for (let i in colum){
                    let categoryName = colum[i].name; // 分类名称
                    // 创建一个新的option元素，并设置其值和文本
                    let option = $("<option></option>");
                    option.val(categoryName).text(categoryName);
                    option.attr("name",colum[i].id);
                    // 将option元素添加到select元素中
                    select.append(option);
                }
                if(param!=null){
                    $("#column1").val(param[0]);
                    getCategory2(param);
                }
                layui.form.render();
            },
            error: function(xhr, status, error) {
                console.error("Error fetching level 1 columns: " + error);
            }
        });
    }

    function getCategory2(param){
        let id = $("#column1 option:selected").attr("name");
        $.ajax({
            type: 'GET',
            url: '/secondCategoryHandle', // 请求二级栏目的URL
            dataType:"json",
            data:{"id":id},
            success: function(data){
                // 假设返回的数据是一个数组，每个元素包含id和name属性
                let colum = data.data;
                let select = $('#column2');
                select.find('option:not(:first)').remove();
                for (let i in colum){
                    let categoryName = colum[i].name; // 分类名称
                    // 创建一个新的option元素，并设置其值和文本
                    let option = $("<option></option>");
                    option.val(categoryName).text(categoryName);
                    option.attr("name",colum[i].id);
                    select.append(option);
                }
                if(param!=null){
                    $("#column2").val(param[1]);
                    getCategory3(param);
                }
                layui.form.render();
            }
        });
    }

    function getCategory3(param){
        let id = $("#column2 option:selected").attr("name");
        $.ajax({
            type: 'get',
            url: '/thirdCategoryHandle', // 请求三级栏目的URL
            dataType:"json",
            data:{"id":id},
            success: function(data){
                let colum = data.data;
                let select = $('#column3');
                select.find('option:not(:first)').remove();
                for (let i in colum){
                    let categoryName = colum[i].name; // 分类名称
                    let option = $("<option></option>");
                    option.val(categoryName).text(categoryName);
                    option.attr("name",colum[i].id);
                    select.append(option);
                }
                if(param!=null){
                    $("#column3").val(param[2]);
                }
                layui.form.render();
            }
        });
    }

    function initColum() {
        let select2 = $('#column2');
        let select3 = $('#column3');
        select2.find('option:not(:first)').remove();
        select3.find('option:not(:first)').remove();
    }

    layui.use('form', function () {
        let form = layui.form;
        let upload = layui.upload;
        let layer = layui.layer;
        let element = layui.element;
        let $ = layui.$;

        form.on('select(column1)', function(){
            getCategory2(null);
        });

        // 监听二级栏目选择
        form.on('select(column2)', function(){
            getCategory3(null);
        });

        // 配置全局的 alert
        layer.config({
            // 设置全局弹出层的默认位置为底部
            offset: 'c',
        });

        let uploadInst = upload.render({
            elem: '#upload-img-btn',
            url: '/uploadImg',
            size: 1024, // 限制文件大小
            before: function (obj) {
                obj.preview(function (index, file, result) {
                    $('#upload-show-img').attr('src', result); // 图片链接（base64）
                });

                element.progress('filter-demo', '0%'); // 进度条复位
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
                    layer.msg('上传完毕', {icon: 16});
                }
            }
        });
        // 监听表单提交
        form.on('submit(submit)', function (data) {
            let dataNews = {};
            dataNews["content"] = editor.getHtml();
            dataNews["title"] = $("#title").val();
            dataNews["author"] = $("#author").val();
            dataNews["brief"] = $("#brief").val();
            dataNews["briefImg"] = $("#upload-show-img").attr('src');
            dataNews["id"] = newsId;
            dataNews["column"] = $("#column3").val();
            dataNews["dir"] = $("#column1").val() + "/" + $("#column2").val() + "/" + $("#column3").val();
            dataNews["categoryId"] = $("#column3 option:selected").attr("name");
            sendNewsData(dataNews);
            return false;
        });
    });

    function setParams(data){
        $("#title").val(data["title"]);
        $("#author").val(data["author"]);
        $("#brief").val(data["brief"]);
        $("#upload-show-img").attr("src",data["briefImg"]);
        let columns = data["articleColumn"].split('/');
        getCategory1(columns);
        editor.setHtml(data["content"]);
        layui.form.render();
    }

    function getNews(id){
        let data = {}
        data["id"] = id;
        $.ajax({
            type: "post",
            url: "/getNews",
            //data:定义数据,以键值对的方式放在大括号里
            data: data,
            dataType: 'json',
            //statusCode:状态码，用于定义执行时提示的状态
            statusCode: {
                404: function () {
                    alert("404");
                },
                500: function () {
                    alert("500");
                }
            },
            success: function (data) {
                setParams(data);
            }
        });
    }

    $(document).ready(function (){
        let paramValue = new URLSearchParams(window.location.search).get('id');
        if(paramValue!==null){
            newsId = paramValue;
            getNews(paramValue);
        }else{
            getCategory1(null);
        }
    })



</script>

</body>
</html>
