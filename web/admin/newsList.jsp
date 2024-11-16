<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>新闻列表</title>
  <link rel="stylesheet" href="../layui/css/layui.css" media="all">
</head>
<style>
  .layui-table-cell{
    height:60px;
    line-height: 30px;
  }
</style>
<body>

<div class="layui-container">
  <table class="layui-hide" id="newsList" lay-filter="newsList"></table>
</div>

<script type="text/html" id="toolbar">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="browse">浏览</a>
</script>

<script src="../layui/layui.js"></script>
<script src="../js/jquery-3.5.1.min.js"></script>
<script>

  layui.use(['table'], function(){
    let table = layui.table;

    // 表格渲染
    table.render({
      method:'post',
      elem: '#newsList',
      url: '/admin/getNewsForAdmin', // 数据接口
      parseData: function(res){
        return {
          "code": res.code, //解析接口状态
          "msg": res.msg, //解析提示文本
          "count": res.count, //解析数据长度
          "data": res.data //解析数据列表
        };
      },
      cellMinWidth: 60,
      cols: [[
        {field: 'id', title: '新闻ID', sort: true},
        {field: 'title', title: '新闻标题'},
        {field: 'author', title: '作者'},
        {field: 'date', title: '添加时间'},
        {field: 'briefImg', title: '新闻缩略图', templet: function(d){
            return '<img src="' + d.briefImg + '" alt="缩略图" width="100%" height="100%"/>';
          },class :'img-height'},
        {field: 'articleColumn', title: '新闻栏目'},
        {field: 'dir', title: '新闻目录'},
        {fixed: 'right', title:'操作', toolbar: '#toolbar',width: 200}
      ]],
    });

    // 监听工具条
    table.on('tool(newsList)', function(obj){
      let data = obj.data;
      if(obj.event === 'del'){
        deleteNews(data.id)
      } else if(obj.event === 'edit'){
        window.open('/admin/newsEdit.jsp?id=' + data.id);
      }else if(obj.event ==='browse'){
        window.open('/getNews?id=' + data.id, '_blank');
      }
    });
  });

  function deleteNews(id) {
    layui.use('layer', function () {
      let layer = layui.layer;
      layer.confirm(' 确定要删除这条新闻吗？', {icon: 3, title: '提示',shade: 0}, function (index) {
        // 确认删除，发送请求到服务器
        $.ajax({
          type: "post",
          url: "/admin/deleteNews",
          data: { id: id },
          dataType: 'json',
          success: function (data) {
            console.log(data)
            if (data === 1) {
              // 删除成功，重新加载表格
              layer.msg('新闻删除成功', {icon: 1});
              window.location.reload();
            } else {
              // 删除失败，显示错误信息
              layer.msg('新闻删除失败: ' + data["data"], {icon: 2});
            }
          }
        });
        layer.close(index);
      });
    });
  }


</script>

</body>
</html>
