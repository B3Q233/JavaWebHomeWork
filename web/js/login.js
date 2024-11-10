function sendReqCapthcha() {
    let data = {};
    data["len"] = 4;
    data["type"]="login";
    $.ajax({
        type: "get",
        url: "/createCaptcha",
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
            let relString="";
            for (let i = 14;i<=14+3;i++){
                relString+=data[i];
            }
            drawCaptcha(relString);
        }
    });
}
function check() {
    let data = {};
    let is_all_right = true;
    let user_name = document.getElementById('user_name').value;
    let pwd = document.getElementById('pwd').value;
    let captcha = document.querySelector('.login_captcha').value;

    if (user_name == "") {
        is_all_right = false;
        document.getElementById('user_name').style.outline = '1px solid red';
    } else {
        document.getElementById('user_name').style.outline = '0px solid red';
    }
    if (pwd == "") {
        is_all_right = false;
        document.getElementById('pwd').style.outline = '1px solid red';
    } else {
        document.getElementById('pwd').style.outline = '0px solid red';
    }
    data["username"] = user_name;
    data["pwd"] = pwd;
    data["captcha"] = captcha;

    if (is_all_right == true) {
        $.ajax({
            type: "post",
            url: "/login",
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
                let flag = true;
                if (data == "") {
                    alert("登录成功!!!");
                    reload();
                } else if (data=="1") {
                    alert("用户名不存在!!!");
                } else if(data=="2"){
                    alert("密码错误!!!")
                }else if(data=="3"){
                    alert("验证码错误!!!")
                }else if(data=="4"){
                    alert("您已经登录，请手动退出账号后重试")
                }
                else if(data=="5"){
                    alert("该账户已经登录")
                }
                sendReqCapthcha();
            }
        });
    } else {
        alert("账户或密码不能为空")
    }
}

sendReqCapthcha()