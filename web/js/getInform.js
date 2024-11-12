
function reload(){
    location.reload();
}
function getUserName(){
    let data = {}
    data["operation"] = "getUserInform";
    data["param"] = "username";
    $.ajax({
        type: "post",
        url: "/handle",
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
            if(data!=null&&data!==""){
                setUserName(data);
            }else{
                $("#user").text("登录");
                $("#user").attr("href","../login.jsp");
                if($("#leaveAccount").length!==0){
                    $("#leaveAccount").remove(" | "+'<span id = "leaveAccount" onclick=\'leaveAccount()\'>登出</span>');
                }
            }
        }
    });
}

function setUserName(userName){
    $("#user").text(userName);
    $("#user").attr("href","#");
    $("#menu").append(" | "+'<span id = "leaveAccount" onclick=\'leaveAccount()\'>登出</span>')
}

function leaveAccount(){
    let data = {}
    data["operation"] = "leaveAccount";
    data["param"] = "";
    $.ajax({
        type: "post",
        url: "/handle",
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
           if(data=="1"){

           }
        }
    });
    reload();
}

function getLogoImg(){
    let data = {}
    data["operation"] = "getLogo";
    data["param"] = "";
    $.ajax({
        type: "get",
        url: "/getLogo",
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
           if(data["status"]==1){
               $("#logoImg").attr('src',data["data"]);
           }
        }
    });
}

getLogoImg()
getUserName()