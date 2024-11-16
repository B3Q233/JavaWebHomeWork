<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>新闻</title>
  <link rel="stylesheet" type="text/css" href="../layui/css/layui.css">
  <link rel="stylesheet" href="/style/login.css">
  <link rel="stylesheet" href="/style/font/icomoon/style.css">
  <link rel="stylesheet" href="/style/newsList.css">
  <script src="../js/jquery-3.5.1.min.js"></script>
  <script src="js/getInform.js"></script>
</head>
<body>
<jsp:include page="admin/top.jsp"></jsp:include>
<div class="content">
  <div class="column" id = "columnList">
  </div>
</div>
<div style="margin-top: 50px"></div>
<jsp:include page="admin/footer.jsp"></jsp:include>
</body>
<script>

  function getColumn(id){
    $.ajax({
      type: "get",
      url: "/thirdCategoryHandle",
      //data:定义数据,以键值对的方式放在大括号里
      dataType: 'json',
      data: {"id":id},
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
        let column = $("#columnList");
        let columns = data["data"];
        columns.sort((a, b) => a.categoryOrder - b.categoryOrder);
        let name = "";
        let id  = 0;
        let f = 0;
        for (let i in columns) {
            let div = $("<div></div>");
            div.addClass("column-list");
            div.attr("id", columns[i]["id"]);

            let span = $("<span></span>");
            if(f===0){
              f = 1
              name = columns[i]["name"];
              id = columns[i]["id"];
            }
            span.text(columns[i]["name"]);

            div.append(span);
            column.append(div);
          }
          $("#" + id + " span").addClass("column-active");
          getNewsList(name, 20, 1,22);
      }
    });
  }

  function getNewsList(column,pagesize,offset,parentId) {
    let data = {}
    data["column"]=column;
    data["pagesize"] = pagesize;
    data["offset"] = offset;
    data["parentId"] = parentId;
    if(column==="全部")data["style"] = "all";
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
    let searchParams = new URLSearchParams(new URL(window.location.href).search);
    let id = searchParams.get('id');
    getColumn(id);
  });


  $(document).on('click', '.column-list', function() {
    let columnId = $(this).attr("id");
    let columnName =  $("#" + columnId + " span").html();
    $(".list-news").remove();
    $(".column-list span").removeClass("column-active");
    $("#" + columnId + " span").addClass("column-active");
    getNewsList(columnName, 20, 1,22);
  });

</script>
</html>
