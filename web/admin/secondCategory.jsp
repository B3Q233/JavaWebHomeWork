<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>二级栏目管理</title>
  <link rel="stylesheet" href="../layui/css/layui.css">
  <script src="../js/jquery-3.5.1.min.js"></script>
</head>
<style>
  .layui-table-cell{
    height:80px;
    line-height: 30px;
  }
</style>
<body>

<div class="layui-card">
  <div class="layui-card-header">二级栏目列表</div>
  <div class="layui-card-body">
    <table class="layui-hide" id="secondaryCategoryTable" lay-filter="secondaryCategoryTable"></table>
    <!-- 添加按钮 -->
    <button class="layui-btn layui-btn-normal" id="addButton">添加</button>
  </div>
</div>

<script type="text/html" id="barDemoSecondary">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
</script>

<script src="../layui/layui.js"></script>
<script>

  function handle(data, method) {
    $.ajax({
      type: method,
      url: "/secondCategoryHandle",
      data: data,
      dataType: 'json',
      success: function (data) {
        let code = data["status"];
        let msg = data["data"];
        if (code === 1) {
          layer.msg(msg, {icon: 1});
          layui.table.reload("secondaryCategoryTable");
        } else if (code === 0) {
          layer.msg(msg, {icon: 2});
        } else if (code === 2) {
          layer.msg(msg, {icon: 1});
          layui.table.reload("secondaryCategoryTable");
        } else if (code === 3) {
          layer.msg(msg, {icon: 1});
        } else if(code===4){
          layer.msg(msg, {icon: 2});
          layui.table.reload("secondaryCategoryTable");
        }

      }
    });
  }

  layui.use(['table', 'layer','upload'], function () {
    let table = layui.table;
    let layer = layui.layer;

      // 渲染二级栏目表格
    table.render({
      elem: '#secondaryCategoryTable',
      cols: [[
        {field: 'id', title: 'ID', sort: true},
        {field: 'name', title: '名称'},
        {field: 'parentName', title: '所属一级栏目名称'},
        {field: 'categoryOrder', title: '排序号',sort: true},
        {field: 'dir', title: '目录'},
        {field: 'img', title: '缩略图',templet: function(d){
            return '<img src="' + d.img + '" alt="缩略图" width="100%" height="100%"/>';
          }},
        {title: '操作', toolbar: '#barDemoSecondary'}
      ]],
      url: "/secondCategoryHandle",
      method: "get"
    });

    // 监听二级栏目工具条
    table.on('tool(secondaryCategoryTable)', function (obj) {
      let data = obj.data;

      let param = 'id='+data.id;
      if (obj.event === 'del') {
        layer.confirm('确定删除？', function (index) {
          let delData = {};
          delData["type"] = "del";
          delData["id"] = data.id;
          obj.del();
          layer.close(index);
          handle(delData, "post");
        });
      } else if (obj.event === 'edit') {
        // 弹出修改层
        layer.open({
          type: 2,
          title: '修改栏目',
          content: './addCategory.jsp?'+param,
          area: ['600px', '600px'],
          end:function (){
            layui.table.reload("secondaryCategoryTable");
          }
        });
      }
    });

    let addButton = document.getElementById('addButton');
      addButton.onclick = function () {
      layer.open({
        type: 2,
        title: '添加栏目',
        content: './addCategory.jsp',
        area: ['600px', '600px'],
        end:function (){
          layui.table.reload("secondaryCategoryTable");
        }
      });
    };
  });
</script>

</body>
</html>