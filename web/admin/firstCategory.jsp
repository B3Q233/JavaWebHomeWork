<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>一级栏目管理</title>
  <link rel="stylesheet" href="../layui/css/layui.css">
  <script src="../js/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="layui-card">
  <div class="layui-card-header">一级栏目列表</div>
  <div class="layui-card-body">
    <table class="layui-hide" id="primaryCategoryTable" lay-filter="primaryCategoryTable"></table>
    <!-- 添加按钮 -->
    <button class="layui-btn layui-btn-normal" id="addButton">添加</button>
  </div>
</div>

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="../layui/layui.js"></script>
<script>

  function handle(data,method){
    $.ajax({
      type: method,
      url: "/firstCategoryHandle",
      data: data,
      dataType: 'json',
      success: function (data) {
        let code = data["status"];
        let msg = data["data"];
        if (code === 1) {
          layer.msg(msg, {icon: 1});
          layui.table.reload("primaryCategoryTable");
        } else if (code === 0) {
          // 删除失败，显示错误信息
          layer.msg( msg, {icon: 2});
        } else if(code === 2){
          layer.msg(msg, {icon: 1});
          layui.table.reload("primaryCategoryTable");
        } else if(code === 3){
          layer.msg(msg, {icon: 1});
          layui.table.reload("primaryCategoryTable");
        } else if(code===4){
          layer.msg(msg,{icon:2});
          layui.table.reload("primaryCategoryTable");
        }
      }
    });
  }

  layui.use(['table', 'layer'], function () {
    let table = layui.table;
    let layer = layui.layer;

    // 渲染表格
    table.render({
      elem: '#primaryCategoryTable',
      cols: [[
        { field: 'id', title: 'ID', sort: true },
        { field: 'name', title: '名称' },
        { title: '操作', toolbar: '#barDemo' }
      ]],
      url: "/firstCategoryHandle",
      method:"get",
    });

    // 监听工具条
    table.on('tool(primaryCategoryTable)', function (obj) {
      let data = obj.data;
      if (obj.event === 'del') {
        layer.confirm('确定删除？', function (index) {
          let data ={};
          data["type"] = "del";
          data["id"] =obj.data.id;
          handle(data,"post");
        });
      } else if (obj.event === 'edit') {
        // 这里可以编写编辑逻辑
      }
    });

    // 获取添加按钮元素并添加点击事件
    let addButton = document.getElementById('addButton');
    // getData函数实现，这里添加了阻止表单提交的逻辑
    addButton.onclick = function () {
      // 弹出层内容
      let content = '<form id="addForm">'+
              '<div class="layui-form-item">'+
              '<label class="layui-form-label">栏目名称</label>'+
              '<div class="layui-input-block">'+
              '<input type="text" name="name" id="dataName" required lay-verify="required" placeholder="请输入栏目名称" autocomplete="off" class="layui-input">'+
              '</div>'+
              '</div>'+
              '<div class="layui-form-item">'+
              '<div class="layui-input-block">'+
              '<button class="layui-btn" type="button" id = "submitBtn">立即提交</button>'+
              '</div>'+
              '</div>'+
              '</form>';
      // 打开弹出层
      layer.open({
        type: 1,
        title: '添加栏目',
        content: content,
        area: ['500px', '200px'],
        success: function(layero, index){
          // 表单渲染
          layui.form.render();
          // 监听提交
          document.getElementById('submitBtn').addEventListener('click', function(event) {
            // 阻止表单提交
            event.preventDefault();
            let data = {}
            data["type"] = "add";
            data["name"] = $("#dataName").val();
            handle(data,"post");
          });
        }
      });
    };


  });
</script>

</body>
</html>
