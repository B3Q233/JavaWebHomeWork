<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>三级栏目管理</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <script src="../js/jquery-3.5.1.min.js"></script>
    <script src="../layui/layui.js"></script>
</head>
<style>
    .layui-table-cell{
        height:80px;
        line-height: 30px;
    }
</style>
<body>

<div class="layui-card">
    <div class="layui-card-header">三级栏目列表</div>
    <div class="layui-card-body">
        <table class="layui-hide" id="tertiaryCategoryTable" lay-filter="tertiaryCategoryTable"></table>
        <!-- 添加按钮 -->
        <button class="layui-btn layui-btn-normal" id="addButton">添加</button>
    </div>
</div>

<script type="text/html" id="barDemoTertiary">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
</script>

<script>

    function handle(data, method) {
        $.ajax({
            type: method,
            url: "/thirdCategoryHandle",
            data: data,
            dataType: 'json',
            success: function (data) {
                let code = data["status"];
                let msg = data["data"];
                if (code === 1) {
                    // 操作成功，重新加载表格
                    layer.msg(msg, {icon: 1});
                    layui.table.reload("tertiaryCategoryTable");
                } else if (code === 0) {
                    // 操作失败，显示错误信息
                    layer.msg(msg, {icon: 2});
                } else if (code === 2) {
                    layer.msg(msg, {icon: 1});
                    layui.table.reload("tertiaryCategoryTable");
                } else if (code === 3) {
                    layer.msg(msg, {icon: 1});
                }
            }
        });
    }

    layui.use(['table', 'layer'], function () {
        let table = layui.table;
        let layer = layui.layer;


        // 渲染三级栏目表格
        table.render({
            elem: '#tertiaryCategoryTable',
            cols: [[
                {field: 'id', title: 'ID', sort: true},
                {field: 'name', title: '名称'},
                {field: 'parenName', title: '所属二级栏目名称'},
                {field: 'categoryOrder', title: '排序号',sort: true},
                {field: 'dir', title: '所属目录'},
                {title: '操作', toolbar: '#barDemoTertiary'}
            ]],
            url: "/thirdCategoryHandle",
            method: "get"
        });

        // 监听三级栏目工具条
        table.on('tool(tertiaryCategoryTable)', function (obj) {
            let data = obj.data;

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
                    content: './addThirdCategory.jsp?id=' + data.id,
                    area: ['600px', '600px'],
                    end: function () {
                        layui.table.reload("tertiaryCategoryTable");
                    }
                });
            }
        });

        // 添加按钮点击事件
        let addButton = document.getElementById('addButton');
        addButton.onclick = function () {
            layer.open({
                type: 2,
                title: '添加栏目',
                content: './addThirdCategory.jsp',
                area: ['600px', '600px'],
                end: function () {
                    layui.table.reload("tertiaryCategoryTable");
                }
            });
        };
    });
</script>

</body>
</html>
