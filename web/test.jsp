<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2024/9/17
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery-3.5.1.min.js"></script>
    <title></title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        div {
            width: 64px;
            height: 32px;
        }

        div canvas {
            width: 100%;
            height: 100%;
        }

        canvas:hover {
            cursor: pointer;
        }

    </style>
</head>
<body>
<div onclick="sendReqCaptcha ()">
    <canvas id="canvas"></canvas>
</div>
<script>
    function sendReqCaptcha() { let data = {}; data["len"] = 6; data["type"] = "login"; console.log(data); $.ajax({ type: "get", url: "/createCaptcha", data: data, dataType: 'text', statusCode: { 404: function () { alert("404"); }, 500: function () { alert("500"); } }, success: function (data) { drawCaptcha(data); } }); }
</script>
<script src="js/createCaptcha.js"></script>
</body>
</html>
