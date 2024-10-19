<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>新闻</title>
  <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
  <link rel="stylesheet" href="/style/login.css">
  <link rel="stylesheet" href="/style/font/icomoon/style.css">
  <link rel="stylesheet" href="/style/newsList.css">
  <script src="layui/layui.js"></script>
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
        <img src="image/game_pic/capsule_616x353_1.jpg">
      </div>
    </div>
  </a>
</div>
<div id="test1" class="page-set"></div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
  layui.use('laypage', function(){
    let laypage = layui.laypage;
    console.log(123);



    laypage.render({
      elem: 'test1'
      ,count: 5000
      ,theme: '#4c535f'
      ,url: '/getNewsList'
    });
  });
</script>
</body>
</html>
