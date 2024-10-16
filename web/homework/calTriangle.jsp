<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2024/10/03
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>计算三角形面积</title>
</head>
<body>
<!-- 表单用于输入三角形的三条边 -->
<form action="calTriangle.jsp" method=post >
    第一条边：<br>
    <input type="text" name="side_a" > <br>
    第二条边：<br>
    <input type="text" name="side_b" > <br>
    第三条边：<br>
    <input type="text" name="side_c" > <br>
    <input type="submit">
</form>
<%!
    // 检查字符串是否为有效的数字
    public int is_all_numbers (String str){
        if(str==null||str.equals("")){
            return 1;// 空字符串
        }
        int pos = -1;
        int cnt = 0;
        for(int i = 0;i<str.length();i++){
            if(str.charAt(i)<'0'||str.charAt(i)>'9'){
                if(str.charAt(i)=='.'){
                    cnt++;
                    pos = i;
                }else{
                    return 2;// 包含非法字符
                }
            }
        }
        if(cnt>1||pos==0||pos==str.length()-1){
            return 2;// 格式错误
        }
        return 0;// 有效数字
    }
    // 检查是否可以构成三角形
    public boolean is_Triangle(double e1,double e2,double e3){
        if(e1<=0||e2<=0||e3<=0){
            return false;// 边长必须大于0
        }
        // 检查三角形不等式
        if(e1+e2>e3&&e1+e3>e2&&e2+e3>e1){
            return true;
        }
        return false;
    }
%>
<%
    // 设置请求编码
    request.setCharacterEncoding("utf-8");

    // 获取表单参数
    String size_a = request.getParameter("side_a");
    String size_b = request.getParameter("side_b");
    String size_c = request.getParameter("side_c");

    // 初始化边长变量
    double e_a = 0;
    double e_b = 0;
    double e_c = 0;
    boolean f = false;// 标记是否有错误输入

    // 初始化错误信息
    String error_input = "";

    // 检查所有的边是否为有效数字
    if(is_all_numbers(size_a)==0){
        e_a =  Double.parseDouble(size_a);
    }else {
        f = true;
        error_input+="第一个输入边长的格式有误，应该是一个实数\n <br>";
    }

    if(is_all_numbers(size_b)==0){
        e_b =  Double.parseDouble(size_b);
    }else {
        f = true;
        error_input+="第二个输入边长的格式有误，应该是一个实数\n <br>";
    }

    if(is_all_numbers(size_c)==0){
        e_c =  Double.parseDouble(size_b);
    }else {
        f = true;
        error_input+="第三个输入边长的格式有误，应该是一个实数\n <br>";
    }
    if(f){
        // 如果有错误输入，设置错误信息
        pageContext.setAttribute("error_input",error_input);
    }else{
        if(is_Triangle(e_a,e_b,e_c)){
            // 如果输入的边长可以构成三角形，计算面积
            double ans = (e_a+e_b+e_c)/2;
            System.out.println(ans);
            ans = Math.sqrt(ans*(ans-e_a)*(ans-e_b)*(ans-e_c));
            System.out.println(ans);
            pageContext.setAttribute("ans",ans);
        }else{
            // 如果不能构成三角形，设置错误信息
            pageContext.setAttribute("error_size","输入的三条边构成不了三角形，请检查输入的边长");
        }

    }
%>

<%--输出结果或者错误信息--%>
<c:if test="${error_input!=null}">
    <c:out value = "${error_input}" escapeXml="false"></c:out> <br>
</c:if>

<c:if test="${error_size!=null}">
    <c:out value = "${error_size}" escapeXml="false"></c:out> <br>
</c:if>

<c:if test="${ans!=null}">
    <c:out value = "您输入的边构成的三角形面积为：${ans}" escapeXml="false"></c:out> <br>
</c:if>


</body>
</html>
