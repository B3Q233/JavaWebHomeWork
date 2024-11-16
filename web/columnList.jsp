<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>新闻</title>
    <link rel="stylesheet" type="text/css" href="layui/css/layui.css">
    <link rel="stylesheet" href="/style/login.css">
    <link rel="stylesheet" href="/style/font/icomoon/style.css">
    <link rel="stylesheet" href="/style/columnList.css">
    <script src="js/jquery-3.5.1.min.js"></script>
    <script src="js/getInform.js"></script>
</head>
<body>
<jsp:include page="admin/top.jsp"></jsp:include>
<div class="card-container">
</div>
<div style="margin-top: 50px"></div>
<jsp:include page="admin/footer.jsp"></jsp:include>
</body>
<script>
    function getCategory(){
        $.ajax({
            type: "get",
            url: "/secondCategoryHandle",
            //data:定义数据,以键值对的方式放在大括号里
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
                createShowColumn(data["data"]);
            }
        });
    }
    function createShowColumn(data){
        for (let i in data){
            let cardContainer = $(".card-container");
            let cardDiv = $("<div></div>");
            let cardImg = $("<img>")
            let h3 = $("<h3></h3>").html(data[i]["name"]);
            let toLink = $("<a></a>").attr("href","newsList.jsp?id="+data[i]["id"]);
            let linkBtn = $("<button></button>")
            cardDiv.addClass("column-card");
            cardImg.addClass("column-card-img").attr("src",data[i]["img"]);
            h3.addClass("column-card-title");
            linkBtn.addClass("column-card-button")
            linkBtn.html("点击查看");

            toLink.append(linkBtn);

            cardDiv.append(cardImg);
            cardDiv.append(h3);
            cardDiv.append(toLink);
            cardContainer.append(cardDiv);
        }
    }
    $(document).ready(function (){
        getCategory();
    })
</script>
</html>
