<%@ page import="java.util.Stack" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2024/9/23
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>calculator</title>
</head>
<body>
<form action="calculator.jsp" method="get">
    <input type="text" name="calculate_1" placeholder="请输入第一个数字"> <br><br>
    <span>请选择运算符:</span>
    <select name="operator">
        <option value="add" >+</option>
        <option value="sub">-</option>
        <option value="mul">*</option>
        <option value="div">/</option>
        <option value="qrt">平方根</option>
        <option value="pow" selected>乘方</option>
    </select>
        <br/>
        <br/>
    <input type="text" name="calculate_2" placeholder="请输入第二个数字"> <br><br>
    <input type="submit" value="计算">
        <br/>
        <br/>
</form>
<%!

    public boolean checkIsEmpty(String str) {
        if ("".equals(str) || str == null) {
            return true;
        }
        return false;
    }

    public boolean checkIsAllNumber(String str) {
        if (checkIsEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public Double calculate(double param1, String operator, double param2) {
        System.out.println(operator);
        switch (operator.charAt(0)) {
            case 'a':
                return param1 + param2;
            case 's':
                return param1 - param2;
            case 'm':
                return param1 * param2;
            case 'd':
                if (param2 == 0) {
                    return null;
                }
                return param1 / param2;
            case 'q':
                return Math.sqrt(param1);
            case 'p':
                return Math.pow(param1,param2);
            default:
                return null;
        }
    }
%>
<%
    double calculate_1 = 0;
    double calculate_2 = 0;
    String getCalculate_1 = request.getParameter("calculate_1");
    String getCalculate_2 = request.getParameter("calculate_2");
    String operator = request.getParameter("operator");
    boolean isAllRight = true;
    if (checkIsAllNumber(getCalculate_1)) {
        calculate_1 = Double.valueOf(getCalculate_1);
    } else {
        out.println("第一个参数应该为数字<br/>");
        isAllRight = false;
    }
    if (checkIsAllNumber(getCalculate_2)) {
        calculate_2 = Double.valueOf(getCalculate_2);
    } else if(operator!=null&&!operator.equals("qrt")) {
        out.println("第二个参数应该为数字<br>");
        isAllRight = false;
    }
    System.out.println(operator);
    System.out.println(1);
    if (isAllRight) {
        Double ans = calculate(calculate_1, operator, calculate_2);
        if (ans == null) {
            out.print("被除数不能为0");
        } else {
            out.print("<span>计算结果为：</span>" + calculate(calculate_1, operator, calculate_2));
        }
    }

%>
</body>
</html>
