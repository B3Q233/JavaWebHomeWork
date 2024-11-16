<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>添加三级栏目</title>
  <link rel="stylesheet" href="../layui/css/layui.css">
  <script src="../js/jquery-3.5.1.min.js"></script>
  <script src="../layui/layui.js"></script>
</head>
<body>

<form id="addCategoryForm" class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">三级栏目名称</label>
        <div class="layui-input-block">
            <input type="text" id="thirdCategoryName" name="thirdCategoryName" required lay-verify="required" placeholder="请输入三级栏目名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属二级栏目</label>
        <div class="layui-input-block">
            <select id="secondCategory" name="secondCategoryId" required lay-verify="required">
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序码</label>
        <div class="layui-input-block">
            <input type="text" id="order" name="thirdCategoryName" required  lay-verify="integer" placeholder="请输入一个大于等于0的整数" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn" lay-submit lay-filter="formSubmit">提交</button>
        </div>
    </div>
</form>

<script>
    let f = 0;
    let categoryId = 0;

    function handle(data,method)    {
        $.ajax({
            type: method,
            url: '/thirdCategoryHandle', // 表单提交的URL
            data: data,
            dataType:'json',
            success: function(data){
                let code = data["status"];
                let msg = data["data"];
                if (code === 1) {
                    // 删除成功，重新加载表格
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
                }else if(code===4){
                    $("#thirdCategoryName").val(data.data.name);
                    $("#secondCategory").val(data.data.parenName);
                    $("#order").val(data.data.categoryOrder);
                }else if(code===5){
                    layer.msg(msg, {icon: 1});
                }else if(code===6){
                    layer.msg(msg, {icon: 1});
                }else if(code===7){
                    layer.msg(msg, {icon: 2});
                }
            },
            error: function(){
                layer.msg('提交失败');
            }
        });
    }

    function getSecondCategory(){
        $.ajax({
            type: 'GET',
            url: '/secondCategoryHandle', // 表单提交的URL
            success: function(data){
                let category = data["data"];
                let select = $("#secondCategory");
                // 清空现有的选项
                select.empty();
                // 遍历分类数据，并为每个分类创建一个选项
                let initOption = $("<option></option>").val("请选择")
                select.append(initOption);
                for (let i in category){
                    let categoryName = category[i].name; // 分类名称
                    // 创建一个新的option元素，并设置其值和文本
                    let option = $("<option></option>");
                    option.val(categoryName).text(categoryName);
                    option.attr("name",category[i].id);
                    // 将option元素添加到select元素中
                    select.append(option);
                }
                let searchParams = new URLSearchParams(new URL(window.location.href).search);
                let id = searchParams.get('id');
                categoryId = id;

                if(id!==""&&id!=null){
                    f = 1;
                    let data = {};
                    data["type"] = "query";
                    data["id"] = id;
                    handle(data,"post")
                }
                layui.form.render();
            },
            error: function(){
                layer.msg('提交失败');
            }
        });
    }

    $(document).ready(function() {
        getSecondCategory()
    });

    layui.use(['form', 'upload'], function(){
        let form = layui.form;

        form.verify({
            integer: function(value){
                if(value !== ''){
                    if(!/^\d+$/.test(value)){
                        return '请输入一个大于等于0的整数';
                    }
                }
                else if(value ==='')return '必选项不能为空';
            }
        });

        layer.config({
            // 设置全局弹出层的默认位置居中
            offset: 'c',
        });
        // 提交表单
        form.on('submit(formSubmit)', function(data){
            let form = {};
            if(f!==1){
                form["type"] = "add";
            }else if(f ===1){
                form["type"] = "update";
                form["id"] = categoryId;
            }
            form["name"] = $("#thirdCategoryName").val();
            form["secondCategoryName"] = $("#secondCategory").val();
            form["secondCategoryId"] =$("#secondCategory option:selected").attr("name")
            form["order"] = $("#order").val();
            handle(form,"post");
            return false; // 阻止表单跳转。
        });
    });
</script>

</body>
</html>
