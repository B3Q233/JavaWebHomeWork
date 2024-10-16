<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 设置请求的字符编码为UTF-8，确保正确读取表单提交的汉字信息 -->
<%
    request.setCharacterEncoding("utf-8");
%>
<html>
<head>
    <title>饮料选择</title>
</head>
<body>
<!-- 在服务器端定义一个字符串数组，存储饮料名称 -->
<%
    String[] drinks = {
            "克扣科勒", "劳大", "农夫三拳", "生活", "秘药G", "0721", "绿茶G"
    };
    // 将饮料数组存储到pageContext中，以便在JSP页面中使用
    pageContext.setAttribute("drinks", drinks);
%>
<!-- 创建一个表单，用于提交用户选择的饮料 -->
<form action="yingliao.jsp" method="post">
    <!-- 使用JSTL的forEach标签遍历drinks数组，并生成复选框 -->
    <c:forEach var="drink" items="${drinks}">
        <!-- 每个复选框的值为对应的饮料名称 -->
        <input type="checkbox" name="selectedDrinks" value="${drink}">
        <!-- 显示饮料名称 -->
        ${drink} <br>
    </c:forEach>
    <!-- 提交按钮 -->
    <input type="submit" value="提交">
</form>
<!-- 当表单提交后，处理选中的饮料 -->
<%
    // 获取表单中所有选中的复选框的值
    String[] selectedDrinks = request.getParameterValues("selectedDrinks");
    // 如果有选中的饮料
    if (selectedDrinks != null) {
        // 输出用户选择的饮料
        out.println("您选择的饮料为：<br>");
        for (String drink : selectedDrinks) {
            // 同时在服务器控制台打印选择的饮料
            System.out.println(drink);
            // 在网页上显示用户选择的饮料
            out.println(drink+"<br>");
        }
    }
%>
</body>
</html>
