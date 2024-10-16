<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>登录</title>
  <link rel="stylesheet" href="../style/login.css">
  <link rel="stylesheet" href="../style/font/icomoon/style.css">
  <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
</head>

<body>
  <!-- 引入js文件实现动态验证 -->
  <script src="/js/login.js"></script>
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
          <span class="font_download"></span>
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

  <!-- 登录 -->
  <div class="main_login">
    <div>
      <h1>登录</h1>
    </div>
    <div class="main_content">
      <div class="left">
        <div class="login_account">用账户名称登录</div>
        <input type="text" style="margin-top: 20px;" id="user_name">
        <div class="login_pwd" style="margin-top: 20px;">密码</div>
        <input type="password" style="margin-top: 20px;" id="pwd">
        <div class="login_pwd" style="margin-top: 20px;">验证码</div>
        <div class="captcha" >
          <input type="text"  placeholder="不分大小写" style="margin-top: 20px;" class="login_captcha" id="captcha" >
          <div class="captcha_canvas" onclick="sendReqCapthcha()"><canvas></canvas></div>
        </div>
        <button style="text-align: center; display: block;" onclick="check()">登录</button>
      </div>
      <div class="right">
        <div class="login_account">或者使用二维码登录</div>
        <div><img src="../image/game_title/login.png" alt=""
            style="width: 80%; height: 80%; padding-top: 10px; padding-bottom: 10px;"></div>
        <div style="color: #fff; ">通过二维码使用 Steam 手机应用登录</div>
      </div>
      <a href="/resister.jsp" style="margin-top: 46px; margin-left: 8px;">没有账户？点击注册</a>
    </div>
  </div>


  <script src="js/createCaptcha.js"></script>
</body>
<jsp:include page="footer.jsp"></jsp:include>
</html>