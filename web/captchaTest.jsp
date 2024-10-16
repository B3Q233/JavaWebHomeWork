<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2024/9/17
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Random"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.awt.Color"%>
<%@ page import="java.awt.Font"%>
<%@ page import="java.awt.Graphics"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="javax.imageio.ImageIO"%>
<%
    int width = 60;
    int height = 32;
    //创建一个image对象
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    //创建一个画板
    Graphics g = image.getGraphics();
    //设置背景颜色
    g.setColor(new Color(0xDCDCDC));
    //填充一个矩形
    g.fillRect(0, 0, width, height);
    //画矩形框的边框颜色
    g.setColor(Color.black);
    //绘制一个矩形
    g.drawRect(0, 0, width - 1, height - 1);
    //创建一个随机对象，用于获取产生随机验证码
    Random rdm = new Random();
    String hash1 = Integer.toHexString(rdm.nextInt());
    // 为了使验证码不易于区分，所以这里就产生50个随机的圆点，来进行迷惑
    for (int i = 0; i < 50; i++) {
        int x = rdm.nextInt(width);
        int y = rdm.nextInt(height);
        //绘制椭圆
        g.drawOval(x, y, 0, 0);
    }
    // 截取产生的验证码的前四位（这个根据自己需要进行设置）
    String capstr = hash1.substring(0, 4);
    //将生成的验证码存入session
    session.setAttribute("validateCode", capstr);
    //绘制验证码的颜色
    g.setColor(new Color(0, 100, 0));
    //设置字体
    g.setFont(new Font("Candara", Font.BOLD, 24));
    g.drawString(capstr, 8, 24);
    g.dispose();
    //输出图片
    response.setContentType("image/jpeg");
    out.clear();
    out = pageContext.pushBody();
    OutputStream strm = response.getOutputStream();
    ImageIO.write(image, "jpeg", strm);
    strm.close();
    out.clear();
%>
