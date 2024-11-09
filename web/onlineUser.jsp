<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>在线用户列表</title>
    <!-- 引入layui样式 -->
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <!-- 引入layui.js -->
    <script src="layui/layui.js"></script>
    <script src="../js/jquery-3.5.1.min.js"></script>
</head>
<body>
<div class="layui-container">
    <h1 class="layui-title">在线用户列表</h1>
    <table class="layui-table" lay-skin="line">
        <colgroup>
            <col width="150">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>用户名</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="userList">
        <!-- 用户列表将在这里动态插入 -->
        </tbody>
    </table>
</div>
</body>
<script>

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
                console.log(data);
                updateUserList(data["names"]);
            }
     });
    }

    function updateUserList(users) {
        let userListHtml = '';
        users.forEach(function(user) {
            userListHtml += '<tr>' +
                '<td>' + user["name"] + '</td>' +
                '<td>' +"在线" + '</td>' +
                '<td><button class="layui-btn layui-btn-sm">下线</button></td>' +
                '</tr>';
        });
        $('#userList').html(userListHtml);
    }

    getOnlineUser()


</script>
</html>
