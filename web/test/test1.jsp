<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>新闻</title>
  <link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
  <link rel="stylesheet" href="../js/wangEditor/css/style.css">
  <script src="../layui/layui.js"></script>
  <script src="../js/jquery-3.5.1.min.js"></script>
  <script src="../js/wangEditor/index.js"></script>
  <link rel="stylesheet" href="../style/editor/view.css">
  <link rel="stylesheet" href="../style/editor/normal.css">
</head>
<body>
<style>
  #editor—wrapper {
    border: 1px solid #ccc;
    z-index: 100; /* 按需定义 */
  }
  #toolbar-container { border-bottom: 1px solid #ccc; }
  #editor-container { height: 500px; }
</style>
<div style="padding:15px">
  <br><br>
  <h1>文章编辑</h1>
  <br><br>

  <blockquote class="layui-elem-quote layui-text">
    <ul>
      <li>
        设置文章标题
      </li>
      <li>
        选择文章的栏目
      </li>
      <li>
        输入作者的名称
      </li>
      <li>
        编辑文章的内容
      </li>
    </ul>
  </blockquote>

  <br><br><br>

  <table class="layui-hide" id="test"></table>

  <div class="layui-card layui-panel">

  </div>
  <br><br>
</div>
<form class="layui-form" action="">
  <div class="layui-container" style="margin: 0 0">

      <!-- 标题 -->
      <div class="layui-form-item">
        <label class="layui-form-label">标题</label>
        <div class="layui-input-block">
          <input type="text" name="title" id = "title" required lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
        </div>
      </div>

    <!-- 作者 -->
    <div class="layui-form-item">
      <label class="layui-form-label">作者</label>
      <div class="layui-input-block">
        <input type="text" name="title" id = "author" required lay-verify="required" placeholder="请输入作者名称" autocomplete="off" class="layui-input">
      </div>
    </div>

      <!-- 分类 -->
      <div class="layui-form-item">
        <label class="layui-form-label">栏目</label>
        <div class="layui-input-block">
          <select name="category" lay-verify="required" id ="column">
            <option value=""></option>
            <option value="国内">国内</option>
            <option value="国际">国际</option>
            <option value="娱乐">娱乐</option>
            <option value="体育">体育</option>
            <option value="财经">财经</option>
          </select>
        </div>
      </div>

    <!-- 缩略图 -->
    <button type="button" class="layui-btn" id="ID-upload-img-btn" style="margin-left: 50px">
      <i class="layui-icon layui-icon-upload"></i> 缩略图
    </button>
    <!-- 回显 -->
    <div style="width: 132px;">
      <div class="layui-upload-list">
        <img class="layui-upload-img" id="ID-upload-demo-img" style="width: 100%; height: 92px;">
        <div id="ID-upload-demo-text"></div>
      </div>
      <div class="layui-progress layui-progress-big" lay-showPercent="yes" lay-filter="filter-demo">
        <div class="layui-progress-bar" lay-percent=""></div>
      </div>
    </div>

      <!-- 内容 -->
      <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-block">
          <!-- 创建编辑器容器 -->
          <div id="editor—wrapper">
            <div id="toolbar-container"><!-- 工具栏 --></div>
            <div id="editor-container"><!-- 编辑器 --></div>
          </div>
        </div>
      </div>

      <!-- 提交按钮 -->
      <div class="layui-form-item">
        <div class="layui-input-block">
          <button class="layui-btn" lay-submit lay-filter="submit">立即提交</button>
          <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
      </div>
</form>
  </div>

<script>
  function sendNewsData(data) {
    $.ajax({
      type: "post",
      url: "/addNews",
      //data:定义数据,以键值对的方式放在大括号里
      data: data,
      dataType: 'text',
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
       alert("ok");
      }
    });
  }
</script>

<script>

  let getHtml = ""

  const { createEditor, createToolbar } = window.wangEditor

  const editorConfig = {
    placeholder: '请输入文章内容....',
    MENU_CONF: {
      uploadImage: {
        server: '/uploadImg',
        maxFileSize: 10 * 1024 * 1024,
      }
    },
  }

  const editor = createEditor({
    selector: '#editor-container',
    html: '',
    config: editorConfig,
    mode: 'default', // or 'simple'
  })

  const toolbarConfig = {}

  const toolbar = createToolbar({
    editor,
    selector: '#toolbar-container',
    config: toolbarConfig,
    mode: 'default', // or 'simple'
  })


</script>

<script>

  layui.use('form', function(){
    let form = layui.form;
    let upload = layui.upload;
    let layer = layui.layer;
    let element = layui.element;
    let $ = layui.$;
    let uploadInst = upload.render({
      elem: '#ID-upload-img-btn',
      url: '/uploadImg',
      size: 10240, // 限制文件大小，10mb
      before: function(obj){
        // 预读本地文件示例，不支持ie8
        obj.preview(function(index, file, result){
          $('#ID-upload-img-btn').attr('src', result); // 图片链接（base64）
        });

        element.progress('filter-demo', '0%'); // 进度条复位
        layer.msg('图片上传中', {icon: 16, time: 0});
      },
      done: function(res){
        // 若上传失败
        if(res.code > 0){
          return layer.msg('上传失败');
        }
        // 上传成功的一些操作
        // …
        $('#ID-upload-img-btn').html(''); // 置空上传失败的状态
      },
      error: function(){
        // 演示失败状态，并实现重传
        let  demoText = $('#ID-upload-demo-text');
        demoText.html('<span style="color: #FF5722;">图片上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
        demoText.find('.demo-reload').on('click', function(){
          uploadInst.upload();
        });
      },
      // 进度条
      progress: function(n, elem, e){
        element.progress('filter-demo', n + '%');
        if(n == 100){
          layer.msg('上传完毕', {icon: 1});
        }
      }
    });
    // 监听表单提交
    form.on('submit(submit)', function(data){
      let dataNews = {};
      dataNews["content"] = editor.getHtml();
      dataNews["title"] = $("#title").val();
      dataNews["author"] = $("#author").val();
      dataNews["column"] = $("#column").val();
      console.log(dataNews);
      sendNewsData(dataNews);
      return false;
    });
  });


</script>

</body>
</html>
