<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>layui test </title>
</head>

<body>
<table class="layui-hide" id="test"></table>
</body>
<script src="layui/layui.js"></script>
<script src="layui/test1.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<script src = "layui/css/layui.css"></script>
<script>
  // 初始化 LayUI 的表格组件

  import {layEvent} from "./layui/layui";

  function sendOperation(operation){

  }

  layui.use('table', function () {
    let table = layui.table;

      table.render({
        elem: '#test',
        url:'/getdata',
        // page: true,
        response: {
          statusCode: 200 // 重新规定成功的状态码为 200，table 组件默认为 0
        },
        // 将原始数据解析成 table 组件所规定的数据格式
        parseData: function(res){
          console.log(res);
          return {
            "code": res.status, //解析接口状态
            "msg": res.message, //解析提示文本
            "count": res.total, //解析数据长度
            "data": res.data //解析数据列表
          };
        },
        cols: [[
          {field: 'id', title: 'ID'},
          {field: 'username', title: '用户名'}
        ]]
      });
    table.on('tool(test)',function(obj){
      if (layEvent === 'del') {
        console.log("del")
        //删除
        layer.confirm('确定删除？', function (index) {
          obj.del() //删除对应行（tr）的DOM结构，并更新缓存
          layer.close(index)
          //向服务端发送删除指令
        })
      }
    })
    });

</script>

</html>