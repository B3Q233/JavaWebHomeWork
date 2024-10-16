<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2024/9/23
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Multiplication table</title>
</head>
<body>
<%
    for (int i = 1;i<=9;i++){
        for (int j = 1;j<=i;j++){
            out.println(i+"*"+j+"= "+i*j+"&nbsp&nbsp");
        }
        out.println("<br/>");
    }
%>
</body>
</html>
