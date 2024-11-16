<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加栏目</title>
  <link rel="stylesheet" href="../layui/css/layui.css" media="all">
  <script src="../layui/layui.js"></script>
  <script src="../js/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="layui-container">
  <form class="layui-form">
    <div class="layui-form-item">
      <label class="layui-form-label">栏目名称</label>
      <div class="layui-input-block">
        <input id = "name" type="text" name="columnName" required lay-verify="required" placeholder="请输入栏目名称" autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">选择所属一级栏目</label>
      <div class="layui-input-block">
        <select id = "category" name="firstCategory" lay-verify="required">
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
      <label class="layui-form-label">图片设置</label>
      <div class="layui-input-block" style="width: 132px;" >
        <div class="layui-upload-list" >
          <img class="layui-upload-img" id="upload-show-img" style="width: 100%; height: 92px;">
          <div id="ID-upload-demo-text"></div>
        </div>
        <button type="button" class="layui-btn" id="upload-img-btn">
          <i class="layui-icon layui-icon-upload"></i> 图片上传
        </button>
      </div>
    </div>
    <div class="layui-form-item">
      <div class="layui-input-block">
        <button class="layui-btn" lay-submit lay-filter="formSubmit">立即提交</button>
      </div>
    </div>
  </form>
</div>

<script>

  let f = 0;
  let categoryId = 0;

  function handle(data,method){
    $.ajax({
      type: method,
      url: '/secondCategoryHandle', // 表单提交的URL
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
          $("#name").val(data.data.name);
          $("#category").val(data.data.parentName);
          $("#upload-show-img").attr("src",data.data.img);
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

  function getFirstCategory(){
    $.ajax({
      type: 'GET',
      url: '/firstCategoryHandle', // 表单提交的URL
      success: function(data){
        let category = data["data"];
        let select = $("#category");
        // 清空现有的选项
        select.empty();
        let initOption = $("<option></option>").val("请选择")
        select.append(initOption);
        for (let i in category){
          let categoryName = category[i].name;
          let option = $("<option></option>");
          option.val(categoryName).text(categoryName);
          option.attr("name",category[i].id);
          console.log(option)
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
    getFirstCategory();
    f = 0;
  });

  layui.use(['form', 'upload'], function(){
    let form = layui.form;
    let upload = layui.upload;

    layer.config({
      // 设置全局弹出层的默认位置居中
      offset: 'c',
    });

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

    let uploadInst = upload.render({
      elem: '#upload-img-btn',
      url: '/uploadImg',
      size: 500, // 限制文件大小
      before: function (obj) {
        obj.preview(function (index, file, result) {
          $('#upload-show-img').attr('src', result); // 图片链接（base64）
        });
      },
      done: function (res) {
        $('#ID-upload-img-btn').html(''); // 置空上传失败的状态
      },
      error: function () {
        // 演示失败状态，并实现重传
        let demoText = $('#ID-upload-demo-text');
        demoText.html('<span style="color: #FF5722;top: 52%;left: 50%;">图片上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
        demoText.find('.demo-reload').on('click', function () {
          uploadInst.upload();
        });
      },
      progress: function (n, elem, e) {
        if (n == 100) {
          layer.msg('上传中', {icon: 16});
        }
      }
    });

    // 提交表单
    form.on('submit(formSubmit)', function(data){
      data.field["file"] = $("#upload-show-img").attr("src");
      let form = {};
      if(f!==1){
        form["type"] = "add";
      }else if(f ===1){
        form["type"] = "update";
        form["id"] = categoryId;
      }
      form["name"] = data.field["columnName"];
      form["firstCategory"] = data.field["firstCategory"];
      form["file"] = data.field["file"];
      form["order"] = $("#order").val();
      form["parentId"] = $("#category option:selected").attr("name");
      handle(form,"post");
      return false;
    });
  });
</script>

</body>
</html>

