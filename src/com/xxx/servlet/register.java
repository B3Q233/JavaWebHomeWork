package com.xxx.servlet;

import com.b3qTest.factory.DAOFactory;
import com.b3qTest.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author b3q
 * 接收注册页面post参数 并完成验证的servlet
 */
public class register extends HttpServlet {

    //判断输入是否为空
    public boolean checkIsNull(String str){
        return str!=null&&!"".equals(str);
    }

    //判断用户名
    public boolean checkUserName(String username){
        Boolean flag = false;
        String regex = "^[A-Za-z0-9]{3,15}$";
        if(checkIsNull(username)){
            return username.matches(regex);
        }
        return flag;
    }

    //判断密码
    public boolean checkpwd(String pwd){
        Boolean flag = false;
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,15}$";
        if(checkIsNull(pwd)){
            return pwd.matches(regex);
        }
        return flag;
    }

    //判断姓名
    public boolean checkName(String name){
        Boolean flag = false;
        String regex = "^[\\u4e00-\\u9fa5]{2,10}$";
        if(checkIsNull(name)){
            return name.matches(regex);
        }
        return flag;
    }

    //判断年龄
    public boolean checkAge(String birthday) throws Exception{
        Boolean flag = false;
        String regex = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if(checkIsNull(birthday)&&!birthday.matches(regex)){
            return false;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    //判断确认密码
    public boolean checkVeridyPwd(String veryname,String pwd){
        Boolean flag = false;
        if(checkIsNull(veryname)&& veryname.equals(pwd)){
            return true;
        }
        return flag;
    }

    //判断邮箱
    public boolean checkEmail(String email){
        Boolean flag = false;
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if(checkIsNull(email)){
            return email.matches(regex);
        }
        return flag;
    }

    //判断电话号码
    public boolean checkPhoneNumber(String phonenumber){
        Boolean flag = false;
        String regex = "^1\\d{10}$";
        if(checkIsNull(phonenumber)){
            return phonenumber.matches(regex);
        }
        return flag;
    }

    //判断邮编
    public boolean checkZip(String zip){
        Boolean flag = false;
        String regex = "^\\d{6}$";
        if(checkIsNull(zip)){
            return zip.matches(regex);
        }
        return flag;
    }
    public Date changeDateFormat(String str) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(str);
        return date;
    }
    public long changeToLong(String str){
        long res = 0;
        for (int i =0;i<str.length();i++){
            res = res*10 + str.charAt(i)-'0';
        }
        return res;
    }

    public int changeToInt(String str){
        int res = 0;
        for (int i =0;i<str.length();i++){
            res = res*10 + str.charAt(i)-'0';
        }
        return res;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain");
        String username = req.getParameter("username");

        HttpSession session = req.getSession();
        String captcha = (String) session.getAttribute("register");
        captcha = captcha.toLowerCase();
        String getCaptcha = req.getParameter("captcha");
        if (getCaptcha != null) {
            getCaptcha = getCaptcha.toLowerCase();
        }else {
            getCaptcha="";
        }

        User user= new User();
        user.setUser_name(username);
        try {
            if(DAOFactory.getIEmpDAOInstance().findByUserName(user.getUser_name())!=null){
                resp.getWriter().write("1");
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException("获取用户错误",e);
        }
        if (!getCaptcha.equals(captcha)) {
            resp.setContentType("text/plain");
            resp.getWriter().write("2");
            return;
        }
        user.setName(req.getParameter("nameS"));
        if("man".equals(req.getParameter("gender"))){
            user.setGender("男");
        }else {
            user.setGender("女");
        }
        try {
            user.setBirthday(changeDateFormat(req.getParameter("birthday")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        user.setEmail(req.getParameter("email"));
        user.setPhone_number(changeToLong(req.getParameter("phone_number")));
        user.setPwd(req.getParameter("pwd"));
        user.setAddress(req.getParameter("address"));
        user.setZip(changeToInt(req.getParameter("zip")));

        try {
            DAOFactory.getIEmpDAOInstance().addUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
