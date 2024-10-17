<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Pteam 后台管理</title>
    <script src="js/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
</head>

<body>

    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo layui-hide-xs layui-bg-black">后台管理</div>
            <!-- 头部区域（可配合layui 已有的水平导航） -->
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item layui-hide layui-show-md-inline-block">
                    <a href="javascript:;">
                        admin
                    </a>
                </li>

            </ul>
        </div>

        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                <ul class="layui-nav layui-nav-tree" lay-filter="test">
                    <li class="layui-nav-item layui-nav-itemed">
                        <a class="" href="javascript:;">menu group 1</a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;" data-url = "footer.jsp">menu 1</a></dd>
                            <dd><a href="javascript:;">menu 2</a></dd>
                            <dd><a href="javascript:;">menu 3</a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">menu group 2</a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;">list 1</a></dd>
                            <dd><a href="javascript:;">list 2</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>

        <div class="layui-body">
            <!-- 内容主体区域 -->
            <div style="padding: 15px;">
                内容主体区域

                <br><br>

                <blockquote class="layui-elem-quote layui-text">
                    <ul>
                        <li>
                            你也可以单独打开管理界面大布局的演示页面：
                            <a class="layui-btn layui-btn-normal" href="layuiAdmin.html" target="_blank">单独打开</a>
                        </li>
                        <li>
                            该页面只是简单的管理系统的界面基础布局示例，并不是一整套界面布局方案，您可以关注基于 layui 的通用型管理系统界面模板解决方案：
                            <a href="/layuiadmin/" target="_blank" class="layui-btn">layuiAdmin</a>
                        </li>
                        <li>
                            layui 之所以赢得如此多人的青睐，更多是在于它“前后界面兼备”的能力。既可编织出绚丽的前台页面，又可满足繁杂的管理系统的界面需求。
                            <br>layui 管理基本布局， 致力于让每一位开发者都能轻松搭建自己的管理系统模板。
                        </li>
                    </ul>
                </blockquote>

                <a href="/doc/element/layout.html#admin" target="_blank" class="layui-btn">查看该布局代码</a>
                <br><br><br>

                <table class="layui-hide" id="test"></table>

                <div class="layui-card layui-panel">

                </div>
                <br><br>
            </div>
        </div>

        <div class="layui-footer">
        </div>
    </div>

    <script type="text/html" id="custom">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-id = "{{d.id}}">删除</a>
    </script>

    <script src="layui/layui.js"></script>
    <script>
        //JS
        layui.use(['element', 'layer', 'util'], function () {
            var element = layui.element
                , layer = layui.layer
                , util = layui.util
                , $ = layui.$;

            //头部事件
            util.event('lay-header-event', {
                //左侧菜单事件
                menuLeft: function (othis) {
                    layer.msg('展开左侧菜单的操作', { icon: 0 });
                }
                , menuRight: function () {
                    layer.open({
                        type: 1
                        , content: '<div style="padding: 15px;">处理右侧面板的操作</div>'
                        , area: ['260px', '100%']
                        , offset: 'rt' //右上角
                        , anim: 5
                        , shadeClose: true
                    });
                }
            });
        });
    </script>
    <script>

        function sendOperation(operation,param){
            let data = {}
            data["operation"] = operation;
            data["param"] = param;
            console.log(data);
            $.ajax({
                type: "post",
                url: '/handle',
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
                    console.log(data);
                }
            });
        }


        layui.use('table', function () {
            let table = layui.table;
            table.render({
                elem: '#test'
                , url: '/getdata'
                , page: true
                , response: {
                        statusCode: 200 // 重新规定成功的状态码为 200，table 组件默认为 0
                    },
                    parseData: function(res){
                        console.log(res);
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.msg, //解析提示文本
                            "count": res.count, //解析数据长度
                            "data": res.data //解析数据列表
                        };
                    }
                , cellMinWidth: 60
                , cols: [[
                    { field: 'id', width: 80, title: 'ID', sort: true }
                    , { field: 'username', width: 120, title: '用户名' }
                    , {field: 'name',title: "姓名"}
                    , { field: 'gender', width: 80, title: '性别', sort: true }
                    , { field: 'birthday', width: 150, title: '生日' }
                    , { field: 'address', title: '住址', width: '10%', minWidth: 100 } //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                    , { field: 'phone', title: '电话', sort: true }
                    , { field: 'email', title: '邮箱',width:200, sort: true }
                    , { field: 'zip', title: '邮编' }
                    ,{
                        title: '操作',
                        templet:'#custom'
                    }
                ]]
            });

            table.on('tool(test)', function(obj){
                console.log("123");
                let layEvent = obj.event; // 获取 lay-event 的值
                if (layEvent === 'del') {
                    console.log("del")
                    // 删除
                    layer.confirm('确定删除？', function (index) {
                        obj.del(); // 删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                        sendOperation("delete",obj.data.username);
                    })
                }
            });
        });
    </script>
    <script>
        layui.use('element',function (){
            let element =   layui.element;

            element.on('nav(test)',function (elem){
                let url = elem.data('url');
                if(url){}
                document.getElementById('contentIframe').src = url;
            })
        })
    </script>
</body>

</html>