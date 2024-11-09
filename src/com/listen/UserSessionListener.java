package com.listen;

import com.b3qTest.pojo.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;

public class UserSessionListener implements HttpSessionListener {
    //用于存储在线用户的集合
    private static Set<User> users = new HashSet<>();
    private static Set<String> userName = new HashSet<>();
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // 当创建新的会话时，此方法被调用
        HttpSession session = se.getSession();
        userName.add("b3q");
        userName.add("b3q1");
        userName.add("b3q2");
        userName.add("b3q3");
        System.out.println("会话创建: " + session.getId());
        // 可以在这里添加自定义逻辑，例如记录日志、增加在线用户计数等
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 当会话被销毁时，此方法被调用
        HttpSession session = se.getSession();
        System.out.println("会话销毁: " + session.getId());
        // 可以在这里添加自定义逻辑，例如减少在线用户计数、清理资源等
    }

    // 添加用户到在线用户列表
    public static void addUser(User user) {
        userName.add(user.getUser_name());
        users.add(user);
    }

    // 从在线用户列表移除用户
    public static void removeUser(User user) {
        users.remove(user);
        userName.remove(user.getUser_name());
    }

    // 获取在线用户列表
    public static Set<User> getOnlineUsers() {
        return users;
    }

    public static Set<String> getUserName(){return userName;}

    public static boolean isExitedUser(User user){
        return userName.contains(user.getUser_name());
    }
}
