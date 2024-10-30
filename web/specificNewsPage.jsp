<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>新闻详细页</title>
    <link rel="stylesheet" href="/style/login.css">
    <link rel="stylesheet" href="/style/font/icomoon/style.css">
    <script src="js/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="/js/wangEditor/css/style.css">
    <link rel="stylesheet" href="/style/editor/view.css">
    <link rel="stylesheet" href="/style/editor/normal.css">
    <link rel="stylesheet" href="/style/specific_new.css">
</head>

<body style="background-image: none">
<div class="responsive_header">
    <div class="header_conten">
        <div class="header_logo">
            <img src="../image/bg_logo/header_logo.png" alt="">
        </div>
        <div class="nav">
            <ul>
                <li><a href="../html/main_pasage.html">
                    <p>商店</p>
                </a>
                </li>
                <li><a href="../html/community_passage.html">
                    <p>社区</p>
                </a>
                </li>
                <li><a href="../html/about_Pteam_passage.html">
                    <p class="active">关于</p>
                </a>
                </li>
                <li><a href="../html/customer_service_passage.html">
                    <p>客服</p>
                </a>
                </li>
            </ul>
        </div>
        <div class="globle_action_meau">
            <a href="#" class="header_install">
                &nbsp;&nbsp;
                <span class="font_download"></span>
                <span>下载pteam</span>
            </a>
            &nbsp;&nbsp;
            &nbsp;&nbsp;
            <a href="../html/login.html">登录</a>
            &nbsp;|&nbsp;
            <a class="header_language_pulldown" href="#">语言</a>
        </div>
    </div>
</div>
<!-- 内容 -->
<div class="content editor-content-view" style="min-height: 900px">

    <div class="top">
        <span id = "id" style="display: none">${requestScope.id}</span>
        <h1 class="title" id = "title">
        </h1>
        <div class="about-news">
            <span id="author"></span>
            <span id="time"></span>
            <span id="article_column"></span>
        </div>
    </div>
    <div class="article" id = "text">
    </div>

</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script>

    function setArticle(content){
        if (content==null){
            $("#text").html("<h1 style='color:red;text-align: center' >获取内容失败请检查网络连接！！！！<h1>");
            return;
        }
        $("#title").text(content["title"]);
        document.title = content["title"];
        $("#author").text("作者："+content["author"]);
        $("#time").text("发布时间："+content["date"]);
        $("#article_column").text("来源："+content["articleColumn"]+"栏目");
        $("#text").html(content["content"]);
    }

    function sendAjax(){
        let data = {};
        data["id"] = $("#id").html();
        console.log(data);
        $.ajax({
            type: "post",
            url: '/getNews',
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
    sendAjax();
</script>
</html>