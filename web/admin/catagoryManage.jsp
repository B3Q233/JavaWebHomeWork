<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>二级栏目管理页面</title>
  <link rel="stylesheet" href="../layui/css/layui.css">
  <script src="../js/jquery-3.5.1.min.js"></script>
  <script src="../layui/layui.js"></script>
</head>

<body>
<!-- 二级栏目表格容器 -->
<div class="layui-card">
  <div class="layui-card-header">二级栏目列表</div>
  <div class="layui-card-body">
    <table class="layui-table" id="secondaryCategoryTable">
      <thead>
      <tr>
      <tr>
      </tr>
      </thead>
      <tbody>
      <!-- 这里通过JavaScript动态添加表格行数据 -->
      </tbody>
    </table>
  </div>
</div>

<!-- 添加按钮 -->
<button class="layui-btn layui-btn-normal" id="addButton">添加二级栏目</button>

<!-- 操作按钮模板 -->
<script type="text/html" id="operationBarTemplate">
  <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>
  <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
</script>

<script>
  // 模拟的二级栏目数据（实际中应该从服务器获取）
  const secondaryCategories = [
    { id: 1, name: '二级栏目1', thumbnail: 'https://example.com/thumb1.jpg', parentCategory: '一级栏目1' },
    { id: 2, name: '二级栏目2', thumbnail: 'https://example.com/thumb2.jpg', parentCategory: '一级栏目2' },
    { id: 3, name: '二级栏目3', thumbnail: 'https://example.com/thumb3.jpg', parentCategory: '一级栏目1' }
  ];

  layui.use(['table', 'layer'], function () {
    const table = layui.table;
    const layer = layui.layer;

    // 渲染表格
    table.render({
      elem: '#secondaryCategoryTable',
      cols: [[
        { field: 'id', title: 'ID' },
        { field: 'name', title: '名称' },
        { field: 'thumbnail', title: '缩略图', templet: function (d) {
            return '<img src="' + d.thumbnail + '" width="50" height="50">';
          }
        },
        { field: 'parentCategory', title: '所属父栏目' },
        { title: '操作', toolbar: '#operationBarTemplate' }
      ]],
      data: secondaryCategories
    });

    // 监听工具条事件
    table.on('tool(secondaryCategoryTable)', function (obj) {
      const data = obj.data;
      if (obj.event === 'delete') {
        layer.confirm('确定要删除此二级栏目吗？', function (index) {
          // 这里可以添加删除数据的逻辑，比如发送AJAX请求到服务器
          layer.msg('已删除');
          obj.del();
          layer.close(index);
        });
      } else if (obj.event === 'edit') {
        layer.prompt({
          formType: 2,
          value: data.name
        }, function (value, index) {
          // 这里可以添加编辑数据的逻辑，比如发送AJAX请求到服务器更新数据
          layer.msg('已修改为: ' + value);
          layer.close(index);
        });
      }
    });

    // 添加按钮点击事件
    document.getElementById('addButton').addEventListener('click', function () {
      layer.open({
        type: 1,
        title: '添加二级栏目',
        area: ['400px', '300px'],
        content: '<form class="layui-form"><div class="layui-form-item"><label class="layui-form-label">名称</label><div class="layui-input-block"><input type="text" name="name" lay-verify="required" placeholder="请输入二级栏目名称" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">缩略图</label><div class="layui-input-block"><button type="button" class="layui-btn" id="uploadThumbnail">选择图片</button><input type="hidden" name="thumbnail" lay-verify="required" placeholder="请上传缩略图" autocomplete="off" class="layui-input"><div class="layui-upload-list"><img class="layui-upload-img" id="previewThumbnail" style="width: 100px; height: auto;"></div></div></div><div class="layui-form-item"><label class="layui-form-label">所属父栏目</label><div class="layui-input-block"><input type="text" name="parentCategory" placeholder="请输入所属父栏目" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit lay-filter="addCategory">提交</button></div></div></form>',
        success: function (layero) {
          layui.form.render();
          layui.form.on('submit(addCategory)', function (formData) {
            // 这里可以添加添加数据的逻辑，比如发送AJAX请求到服务器
            layer.msg('添加成功');
            layer.close(layero);
            return false;
          });
        }
      });
    });
  });
</script>
</body>

</html>