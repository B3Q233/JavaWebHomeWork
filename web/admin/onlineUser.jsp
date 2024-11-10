<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>在线用户管理</title>
    <!-- 引入layui样式 -->
    <link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <!-- 引入layui.js -->
    <script src="../layui/layui.js"></script>
    <script src="../js/jquery-3.5.1.min.js"></script>
</head>
<body>
<div style="padding:15px;margin-bottom: -50px">
    <br><br>
    <h1 style="font-size: 2em">在线用户管理</h1>
    <br><br>

    <blockquote class="layui-elem-quote layui-text">
        <ul>
            <li>
                设置已登录人员上下线
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
    <table class="layui-table" lay-skin="line">
        <colgroup>
            <col width="150">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>用户名</th>
            <th>SessionId</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="userList">
        </tbody>
    </table>
</div>
</body>
<script>

    let userList = {}

    function getOnlineUser(){
        let data = {};
        data["operation"] = "getOnlineUser";
        $.ajax({
            type: "post",
            url: "/handle",
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
                userList = data["names"];
                updateUserList(data["names"]);
            }
     });
    }

    function updateUserList(users) {
        let userListHtml = '';
        let cnt = 0
        users.forEach(function(user) {
            userListHtml += '<tr>' +
                '<td>' + user["name"] + '</td>' +
                '<td>' +user["sessionId"] + '</td>' +
                '<td ><button class="layui-btn layui-btn-sm" onclick="deleteSession(id)" id ='+'"'+cnt+'"'+'>下线</button></td>' +
                '</tr>';
            cnt++;
        });
        $('#userList').html(userListHtml);
    }

    function sendDeleteMsg(sessionId) {
        let data = {}
        data["operation"] = "deleteSession";
        data["param"] = sessionId;
        $.ajax({
            type: "post",
            url: "/handle",
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
                console.log("ok");
            }
        });
    }

    function comfirm(sessionId){
        let flag = false;
        layer.msg('确定下线这个用户？', {
            time: 0, // 不自动关闭
            btn: ['确定', '关闭'],
            yes: function(index) {
                layer.close(index); // 关闭消息框
                sendDeleteMsg(sessionId["sessionId"])
                layer.msg("该用户已经下线",{
                    time: 550,
                });
                getOnlineUser();
            },
            btn2: function(index) {
                layer.close(index); // 关闭消息框
            }
        });
        return flag;
    }

    function deleteSession(id){
        console.log(userList[id]);
        comfirm(userList[id])
    }

    $(document).ready(function(){
        getOnlineUser()
    });
</script>
</html>
