<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>新闻</title>
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
    <link rel="stylesheet" href="/style/login.css">
    <link rel="stylesheet" href="/style/font/icomoon/style.css">
    <link rel="stylesheet" href="/style/newsList.css">
    <script src="js/jquery-3.5.1.min.js"></script>
</head>
<body>
<div class="responsive_header">
    <div class="header_conten">
        <div class="header_logo">
            <img src="/image/bg_logo/header_logo.png" alt="">
        </div>
        <div class="nav">
            <ul>
                <li><a href="/html/main_pasage.html">
                    <p>商店</p>
                </a>
                </li>
                <li><a href="/html/community_passage.html">
                    <p>社区</p>
                </a>
                </li>
                <li><a href="#">
                    <p class="active">关于</p>
                </a>
                </li>
                <li><a href="/html/customer_service_passage.html">
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
            <a href="/html/login.html">登录</a>
            &nbsp;|&nbsp;
            <a class="header_language_pulldown" href="#">语言</a>
        </div>
    </div>
</div>
<div class="content">
    <a href="#" class="game-brief">
    <div class="brief-show">
            <div class="text">
                <h3 class="game-title">0721</h3>
                <h1>111111111111111111111111111111</h1>
                <h3 class="time">发布时间： 2024-10-03  作者：b3q</h3>
                <textarea class="content-brief">近期我们关注到超越者们在社区对于温控塔玩法的相关反馈，对此我们梳理了相关问题与各位进行同步和答疑，如您有新版本的其他问题，可以通过反馈平台和客服和我们进行反馈。
1.温控塔的解锁条件温控塔的解锁条件温控塔的解锁条件温控塔的解锁条件
当赛季到达第二阶段【炽尘弥散】后，超越者可通过模因-建造解锁温控塔。
                </textarea>
            </div>
            <div class="img">
                <img src="image/game_pic/video_3.png">
            </div>
    </div>
    </a>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script>

    function getData(limit) {
        let data = {}
        data["limit"] = limit;
        $.ajax({
            type: "post",
            url: "/getNewsList",
            //data:定义数据,以键值对的方式放在大括号里
            data: data,
            dataType: 'text',
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
                let relString="";
                for (let i = 14;i<=14+3;i++){
                    relString+=data[i];
                }
                drawCaptcha(relString);
            }
        });
    }

    function appendText(){
        let newsA = $("<a></a>").attr("href","#").addClass("game-brief");
        let newsDiv = $("<div></div>").addClass("brief-show");
        let newsText = $("<div></div>").addClass("text");
        let gameName = $("<h3></h3>").addClass("game-title").text("游戏名称");
        let gameTitle = $("<h1></h1>").text("游戏标题");
        let gameTime = $("<h3></h3>").addClass("time").text("游戏时间");
        let gameBrief = $("<textarea></textarea>").addClass("content-brief").val("游戏简介");
        let gameImg = $("<div></div>").addClass("img");
        let img = $("<img>").attr("src", "image/game_pic/video_3.png");

        newsText.append(gameName);
        newsText.append(gameTitle);
        newsText.append(gameTime);
        newsText.append(gameBrief);

        gameImg.append(img);

        newsDiv.append(newsText);
        newsDiv.append(gameImg);

        newsA.append(newsDiv);

        $(".content").append(newsA);
    }
    $(document).ready(function(){
        for (let i = 0;i<1;i++){
            appendText();
            getData(10);
        }
    });
</script>
</html>
