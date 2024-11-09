<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>新闻</title>
  <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
  <link rel="stylesheet" href="/style/login.css">
  <link rel="stylesheet" href="/style/font/icomoon/style.css">
  <link rel="stylesheet" href="/style/newsList.css">
  <script src="js/jquery-3.5.1.min.js"></script>
</head>
<body>
<div class="responsive_header">
  <div class="header_conten">
    <div class="header_logo">
      <img src="/image/bg_logo/header_logo.png" alt="">
    </div>
    <div class="nav">
      <ul>
        <li><a href="/html/main_pasage.html">
          <p>商店</p>
        </a>
        </li>
        <li><a href="/html/community_passage.html">
          <p>社区</p>
        </a>
        </li>
        <li><a href="#">
          <p class="active">关于</p>
        </a>
        </li>
        <li><a href="/html/customer_service_passage.html">
          <p>客服</p>
        </a>
        </li>
      </ul>
    </div>
    <div class="globle_action_meau">
      <a href="#" class="header_install">
        &nbsp;&nbsp;
        <span class="font_download"></span>
        <span>下载pteam</span>
      </a>
      &nbsp;&nbsp;
      &nbsp;&nbsp;
      <a href="/html/login.html">登录</a>
      &nbsp;|&nbsp;
      <a class="header_language_pulldown" href="#">语言</a>
    </div>
  </div>
</div>
<div class="content">
  <div class="column" >
    <div class="column-list " id="全部"><span class="column-active">全部</span></div>
    <div class="column-list" id="国际"><span >国际</span></div>
    <div class="column-list" id="国内"><span >国内</span></div>
    <div class="column-list" id="娱乐"><span >娱乐</span></div>
    <div class="column-list" id="政治"><span >政治</span></div>
  </div>
</div>
<div style="margin-top: 50px"></div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script>

  function getNewsList(column,pagesize,offset) {
    let data = {}
    data["column"]=column;
    data["pagesize"] = pagesize;
    data["offset"] = offset;
    if(column=="全部")data["style"] = "all";
    else data["style"] = column;
    $.ajax({
      type: "post",
      url: "/getNewsList",
      //data:定义数据,以键值对的方式放在大括号里
      data: data,
      dataType: 'json',
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
        appendText(data);
      }
    });
  }

  function appendText(data){
    for (let i = 0;i<data["data"].length;i++){
      let tmp = data["data"][i];
      let newsA = $("<a></a>").attr("href","/getNews?id="+tmp["id"]).addClass("news-brief");
      let newsDiv = $("<div></div>").addClass("brief-show");
      let newsText = $("<div></div>").addClass("text");
      let newsTitle = $("<h1></h1>").text(tmp["title"]);
      let newsTime = $("<h3></h3>").addClass("time").text("发布时间："+tmp["date"]+" 作者："+tmp["author"]+" 来源："+tmp["articleColumn"]);
      let newsBrief = $("<textarea></textarea>").addClass("content-brief").val(tmp["brief"]);
      let newsImg = $("<div></div>").addClass("img");
      let img = $("<img>").attr("src", tmp["briefImg"]);

      newsText.append(newsTitle);
      newsText.append(newsTime);
      newsText.append(newsBrief);

      newsImg.append(img);

      newsDiv.append(newsText);
      newsDiv.append(newsImg);

      newsA.append(newsDiv);

      let div = $("<div></div>").append(newsA).addClass("list-news")

      $(".content").append(div);
    }
  }

  $(document).ready(function(){
    $(".column-list").addClass("active")
    getNewsList("国内",20,1)
  });

  $(".column-list").click(function (){
    let columnId = $(this).attr("id");
    $(".list-news").remove()
    $(".column-list span").removeClass("column-active")
    $("#"+columnId+" span").addClass("column-active")
    getNewsList(columnId,20,1);
  })

</script>
</html>
