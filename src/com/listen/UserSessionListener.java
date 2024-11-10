package com.listen;

import com.b3qTest.pojo.SessionUser;
import com.b3qTest.pojo.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionListener implements HttpSessionListener {

    // 使用线程安全的集合来存储在线用户信息
    private static Set<SessionUser> sessionUsers = Collections.synchronizedSet(new HashSet<>());
    private static Map<String, SessionUser> sessionIdToUser = new ConcurrentHashMap<>();
    private static Map<String, HttpSession> sessionIdToSession = new ConcurrentHashMap<>();
    private static Set<String> userName = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sessionIdToSession.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String sessionId = session.getId();

        // 清理会话销毁后的资源
        SessionUser sessionUser = sessionIdToUser.remove(sessionId);
        if (sessionUser != null) {
            sessionUsers.remove(sessionUser);
            userName.remove(sessionUser.getUserName());
        }
        sessionIdToSession.remove(sessionId);
    }

    // 添加用户到在线用户列表
    public static void addUser(String name, String sessionId) {
        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserName(name);
        sessionUser.setSessionId(sessionId);
        sessionUsers.add(sessionUser);
        sessionIdToUser.put(sessionId, sessionUser);
        userName.add(name);
    }

    // 从在线用户列表移除用户
    public static void removeUser(String sessionId) {
        SessionUser sessionUser = sessionIdToUser.remove(sessionId);
        sessionIdToSession.get(sessionId).removeAttribute("user");
        if (sessionUser != null) {
            sessionUsers.remove(sessionUser);
            userName.remove(sessionUser.getUserName());
        }
    }

    // 获取在线用户列表
    public static Set<SessionUser> getOnlineUsers() {
        return new HashSet<>(sessionUsers); // 返回一个新的HashSet以避免并发修改异常
    }

    // 检查用户是否已登录
    public static boolean isUserLoggedIn(String userName) {
        return UserSessionListener.userName.contains(userName);
    }
}
