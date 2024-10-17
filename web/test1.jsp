<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>layui test </title>
</head>

<body>
  <table id="demo" lay-filter="test"></table>
</body>
<script src="layui/layui.js"></script>
<script src="layui/test1.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<script>
  // 初始化 LayUI 的表格组件

  function sendAJax(){
    $.ajax({
      type: "post",
      url: "/getdata",
      //data:定义数据,以键值对的方式放在大括号里
      data: "",
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
       return data
      }
    });
  }

  layui.use('table', function () {
    let table = layui.table;

    data = sendAJax();
    // 执行一个表格实例
    table.render({
      elem: '#demo', // 指定原始表格元素选择器
      data: data, // 数据接口
      page: true,
      cols: [[ // 表头
        {field: 'id', title: 'ID', sort: true},
        {field: 'username', title: '用户名'},
      ]]
    });
  });
</script>

</html>