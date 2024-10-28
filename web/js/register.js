sendReqCapthcha();

// 用户名：必须是英文字母或数字，长度3-15
let usernameRegex = /^[A-Za-z0-9]{3,15}$/;

// 密码：必须有英文字母和数字，长度6-15
let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,15}$/;

// 真实姓名：中文，2-10个字符
let realNameRegex = /^[\u4e00-\u9fa5]{2,10}$/;

// 出生日期：格式yyyy-mm-dd，按此日期算出的年龄应大于等于10岁
// 日期格式的正则
let birthdateRegex = /^(19|20)\d\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;

// 电子邮箱
let emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

// 电话号码：国内号码，假设以1开头的11位数字
let phoneRegex = /^1\d{10}$/;

// 地址：长度不能大于100
let addressRegex = /^.{1,100}$/;
;

// 邮编：是6位数字
let postalCodeRegex = /^\d{6}$/;

// 逻辑判断年龄是否大于等于10岁
function isAgeValid(birthdate) {
    let birthDate = new Date(birthdate);
    let today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    let m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    return age >= 10;
}

// 确认密码：值要和密码框的值相同
function validateConfirmPassword(password, confirmPassword) {
    return password === confirmPassword;
}

//使用相关函数
function validateUsername(username) {
    return usernameRegex.test(username);
}

function validatePassword(password) {
    return passwordRegex.test(password);
}

function validateRealName(realName) {
    return realNameRegex.test(realName);
}

function validateBirthdate(birthdate) {
    return birthdateRegex.test(birthdate) && isAgeValid(birthdate);
}

function validateEmail(email) {
    return emailRegex.test(email);
}

function validatePhone(phone) {
    return phoneRegex.test(phone);
}

function validateAddress(address) {
    if (address == '') {
        return false; // 地址不能为空
    }
    return addressRegex.test(address); // 检查长度是否在1到100个字符之间
}

function validatePostalCode(postalCode) {
    return postalCodeRegex.test(postalCode);
}

// 文字闪烁效果
let flag = 0;
let int = '';

function startFlash() {
    var text = document.querySelectorAll(".error_show p");
    console.log(text);
    let tmp = "#b8b6b4";
    let size = "400";
    console.log(tmp);
    for (let i = 0; i < text.length; i++) {
        if (flag === 0) {
            text[i].style.color = "#ffffff";
            text[i].style.fontWeight = "800";
        } else {
            text[i].style.color = tmp;
            text[i].style.fontWeight = size;
        }
        if (i == text.length - 1) {
            flag++;
        }
    }
    if (flag <= 1) {
        int = setTimeout(startFlash, 300);
    } else {
        window.clearInterval(int);
    }

}

function sendReqCapthcha() {
    let data = {};
    data["len"] = 4;
    data["type"] = "register";
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
    let is_all_right = true;
    let user_name = document.getElementById('user_name').value;
    let pwd = document.getElementById('pwd').value;
    let veriy_pwd = document.getElementById('veriy_pwd').value;
    let name = document.getElementById('name').value;
    let birthday = document.getElementById('birthday').value;
    let email = document.getElementById('email').value;
    let phone_number = document.getElementById('phone_number').value;
    let address = document.getElementById('address').value;
    let zip = document.getElementById('zip').value;
    let man_checked = document.querySelector('.man');
    let woman_checked = document.querySelector('.woman');
    let captcha = document.querySelector('.register_captcha').value;

    const error_div = document.querySelector(".error_show");
    const error_show = document.querySelector(".error_show p");

    //传输的数据
    let data = {};

    let error = [];

    // 验证过程实现
    if (!validateUsername(user_name)) {
        error.push("用户名格式有误！！")
        data["username"] = "";
        is_all_right = false;
        document.getElementById('user_name').style.outline = '1px solid red';
    } else {
        data["username"] = user_name;
        document.getElementById('user_name').style.outline = '0px solid red';
    }

    if (!validatePassword(pwd)) {
        error.push("密码格式有误！！！")
        data["pwd"] = "";
        is_all_right = false;
        document.getElementById('pwd').style.outline = '1px solid red';
    } else {
        data["pwd"] = pwd;
        document.getElementById('pwd').style.outline = '0px solid red';
    }

    if (!validateConfirmPassword(pwd, veriy_pwd)) {
        error.push("两次填入的密码不同！！！")
        data["veriy_pwd"] = "";
        is_all_right = false;
        document.getElementById('veriy_pwd').style.outline = '1px solid red';
    } else {
        data["veriy_pwd"] = veriy_pwd;
        document.getElementById('veriy_pwd').style.outline = '0px solid red';
    }

    if (!validateRealName(name)) {
        error.push("姓名格式有误！！！")
        data["name"] = "";
        is_all_right = false;
        document.getElementById('name').style.outline = '1px solid red';
    } else {
        data["name"] = name;
        document.getElementById('name').style.outline = '0px solid red';
    }

    if(birthday==""){
        data["birthday"] = "";
        document.getElementById('birthday').style.outline = '0px solid red';
    } else if (!validateBirthdate(birthday)) {
        error.push("出生日期格式错误！！！")
        data["birthday"] = "";
        is_all_right = false;
        document.getElementById('birthday').style.outline = '1px solid red';
    } else {
        data["birthday"] = birthday;
        document.getElementById('birthday').style.outline = '0px solid red';
    }

    if (!validateEmail(email)) {
        error.push("邮箱格式有误！！！")
        data["email"] = "";
        is_all_right = false;
        document.getElementById('email').style.outline = '1px solid red';
    } else {
        data["email"] = email;
        document.getElementById('email').style.outline = '0px solid red';
    }

    if(phone_number==""){
        data["phone_number"] = phone_number;
        document.getElementById('phone_number').style.outline = '0px solid red';
    } else if (!validatePhone(phone_number)) {
        error.push("电话号码格式有误！！！")
        data["phone_number"] = "";
        is_all_right = false;
        document.getElementById('phone_number').style.outline = '1px solid red';
    } else {
        data["phone_number"] = phone_number;
        document.getElementById('phone_number').style.outline = '0px solid red';
    }


    if (address == "" || validateAddress(address)) {
        data["address"] = address;
        document.getElementById('address').style.outline = '0px solid red';
    } else {
        error.push("家庭住址字符过长！！！")
        is_all_right = false;
        document.getElementById('address').style.outline = '1px solid red';
    }

    if (zip == "" || validatePostalCode(zip)) {
        data["zip"] = zip;
        document.getElementById('zip').style.outline = '0px solid red';
    } else {
        error.push("邮编格式错误！！！");
        data["zip"] = "";
        is_all_right = false;
        document.getElementById('address').style.outline = '1px solid red';
    }

    if (!man_checked.checked && !woman_checked.checked) {
        is_all_right = false;
        error.push("性别不能为空！！！")
        man_checked.style.outline = '1px solid red';
        woman_checked.style.outline = '1px solid red';
    } else {
        man_checked.style.outline = '0px solid red';
        woman_checked.style.outline = '0px solid red';
        if (man_checked.checked) {
            data["gender"] = "man";
        } else {
            data["gender"] = "woman";
        }
    }

    data["captcha"] = captcha;
    console.log(data);
    if (is_all_right == true) {
        error_div.style.display = "none";
        $.ajax({
            type: "post",
            url: "/register",
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
                if (data == "1") {
                    alert("用户名重复！！！");
                } else if (data == "2") {
                   alert("验证码错误！！！")
                }else{
                    alert("注册成功");
                    window.location.href = "/login.jsp";
                }
                sendReqCapthcha();
            }
        });
    } else {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
        flag = 0;
        startFlash();
        error_div.style.display = "block";
        error_show.innerHTML = "";
        for (let i in error) {
            error_show.innerHTML += `${+i + 1}. ${error[i]}`;
            if (i < error.length) {
                error_show.innerHTML += "<br/>"
            }
        }
    }
}

