package com.b3qTest.dao;

import com.b3qTest.pojo.News;
import com.b3qTest.db.JDBCUtils;
import com.b3qTest.tool.DBTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao{
    @Override
    public boolean insert(News news) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.insert(news,conn);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }
       return flag;
    }

    @Override
    public boolean delete(News news,String key) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.delete(news,conn,key);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }
        return flag;
    }

    @Override
    public List<News> queryAll(News news) throws Exception {
        List<News> list = new ArrayList<News>();
        try (Connection conn = JDBCUtils.getConnection()) {
            list = DBTool.queryAll(news,conn);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败",e);
        }
        return list;
    }

    @Override
    public News findNews(String title, String column) throws Exception {
        News news = null;

        try (Connection conn = JDBCUtils.getConnection()) {
            // 查询 sql 字符串
            String sql = "select id, title, content, date, article_column, author from news where title = ? and article_column = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,title);
            pstmt.setString(2,column);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                news = new News();
                news.setId(rs.getInt(1));
                news.setTitle(rs.getString(2));
                news.setContent(rs.getString(3));
                news.setDate(rs.getDate(4));
                news.setArticle_column(rs.getString(5));
                news.setAuthor(rs.getString(6));
            }
            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("根据title和column查找新闻错误，请检查输入！！！！",e);
        }

        // 没有找到则返回 null
        return news;
    }

    @Override
    public News findNews(int id) throws Exception {
        News news = null;

        try (Connection conn = JDBCUtils.getConnection()) {
            // 查询 sql 字符串
            String sql = "select id, title, content, date, article_column, author from news where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                news = new News();
                news.setId(rs.getInt(1));
                news.setTitle(rs.getString(2));
                news.setContent(rs.getString(3));
                news.setDate(rs.getDate(4));
                news.setArticle_column(rs.getString(5));
                news.setAuthor(rs.getString(6));
            }
            pstmt.close();
        }catch (SQLException e) {
            throw new RuntimeException("根据id查找新闻错误，请检查输入！！！！",e);
        }

        // 没有找到则返回 null
        return news;
    }
}
