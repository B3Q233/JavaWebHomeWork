<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2024/9/30
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>输入您的籍贯</title>
</head>
<%
    String[] provinces = {
            "","安徽省", "北京市", "福建省", "甘肃省",
            "广东省", "广西壮族自治区", "贵州省",
            "海南省", "河北省", "河南省", "黑龙江省",
            "湖北省", "湖南省", "吉林省", "江苏省",
            "江西省", "辽宁省", "内蒙古自治区",
            "宁夏回族自治区", "青海省", "山东省",
            "山西省", "陕西省", "上海市", "四川省",
            "天津市", "西藏自治区", "新疆维吾尔自治区",
            "云南省", "浙江省", "重庆市",
            "香港特别行政区", "澳门特别行政区", "台湾省",
            "提瓦特大陆","夜之国","黑风山"
    };
    pageContext.setAttribute("provinces",provinces);
%>


    <form name="form1" method=post action="jiguan.jsp">

        <span style="margin-left: 50px">请选择您的籍贯:</span><br>
        <br>
        <br>

        <select name="jiguan" style="margin-left: 50px">
            <c:forEach var="address" items="${provinces}">
                <option value="${address}">${address}</option>
            </c:forEach>
        </select>
        <br>
        <br>
        <br>
        <input type="submit" value="提交" style="margin-left: 50px">
    </form>
    <br>
<%
    request.setCharacterEncoding("utf-8");
    String address = request.getParameter("jiguan");
    if(address!=null&&address.equals("北京市")) {
        response.sendRedirect("welcome.jsp");
    }else if (address!=null&&!address.equals("")){
        out.println("<h2 style=\"margin-left: 50px\">您的籍贯是：</h2>");
        pageContext.setAttribute("address",address);
    }
%>
    <br>
    <div style="margin-left: 50px">
        <c:out value="${address}" ></c:out>
    </div>


</html>
