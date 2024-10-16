<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>创建您的账号</title>
    <link rel="stylesheet" href="style/regigter.css">
    <link rel="stylesheet" href="style/font/icomoon/style.css">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <style>
        .gender{
            position: relative;
        }
        .gender span{
            margin-right: 10px;
            margin-left: 50px;
            color: #393c44;
            line-height: 10px;
        }

        .gender .man{
            top: 33px;
            left: 85px;
            position: absolute;
            width: 20px;
            height: 20px;
        }

        .gender .woman{
            top: 33px;
            left: 162px;
            position: absolute;
            width: 20px;
            height: 20px;
        }
        .gender input:focus{
            box-shadow: none;
        }
        .error_show{
            width: 379px;
            background-color: black;
            margin: 0 auto;
            margin-top: 50px;
            display: none;
            border: 1px solid red;
            padding-left: 10px;
            padding-top: 5px ;
            padding-bottom: 5px;
            padding-right: 10px;
        }
        .error_show p{
            word-wrap:break-word;
            width: 100%;
            color: #ffffff;
            font-size: 15px;
        }

    </style>
</head>

<body>
<!-- 引入js文件 -->
<script src="/js/register.js"></script>
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
                <span class="font_download"></span>
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

<!-- 注册页面 -->
<div class="error_show">
    <p>123</p>
</div>
<div class="register">
    <h1>创建您的账号</h1>
    <div class="main_content">
        <div>
            <h4>用户名</h4>
            <input type="text" placeholder="3-15英文字母或数字" id="user_name" name="uer_name">
        </div>
        <div>
            <h4>密码</h4>
            <input type="password" placeholder="长度6-15必须含有数字与字母" id="pwd" name="pwd">
        </div>
        <div>
            <h4>确认密码</h4>
            <input type="password" placeholder="确认密码" id="veriy_pwd" name="veriy_pwd">
        </div>
        <div>
            <h4>真实姓名</h4>
            <input type="text" placeholder="2-10 中文字符" id="name" name="name">
        </div>
        <div class = "gender">
            <h4>性别</h4>
                <span style="color: #595c62">男</span>
                <input type="radio"  class="man" id="gender_man" name="gender" value="man">
                <span style="color: #595c62">女</span>
                <input type="radio"  class="woman" id="gender_woman" name="gender" value="woman">
        </div>
        <div>
            <h4>出生日期</h4>
            <input type="text" placeholder="格式yyyy-mm-dd 年龄应该大于等于10(选填)" id="birthday" name="birthday">
        </div>
        <div>
            <h4>电子邮箱</h4>
            <input type="text" placeholder="xxxxx..@..." id="email" name="email">
        </div>
        <div>
            <h4>电话号码</h4>
            <input type="text" placeholder="只能为11位数字（选填）" id="phone_number" name="phone_number">
        </div>
        <div>
            <h4>用地址户名</h4>
            <input type="text" placeholder="长度不能大于100 (选填)" id="address" name="address">
        </div>
        <div>
            <h4>邮编</h4>
            <input type="text" placeholder="6位数字 (选填)" id="zip" name="zip">
        </div>
        <h4>验证码</h4>
        <div class="captcha" >
            <input type="text" placeholder="从左往右，不分大小写" class="register_captcha" id="captcha" >
            <div class="captcha_canvas" onclick="sendReqCapthcha()">
                <canvas></canvas>
            </div>
        </div>

        <input type="submit" style="text-align: center; display: block;  cursor: pointer;border: 1px solid;
        border-radius: 3px;margin-top: 30px;
        margin-left: 15%;
        margin-bottom: 10px;
        width: 25%;
        height: 35px;
        color: #fff;
        border: 0;
        background: linear-gradient(to right, rgb(6, 191, 255), rgb(45, 115, 255));
        font-size: 20px;" onclick="check ()"></input>
        <a href="/login.jsp" style="margin-top: -30px;">
            <h4 style="color: #fff;">已有账户？点击登录 </h4>
        </a>
    </div>
</div>

<!-- 页脚 -->
<jsp:include page="footer.jsp"></jsp:include>
<script src="js/createCaptcha.js"></script>
</body>

</html>