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

public class NewsDaoImpl implements NewsDao {
    @Override
    public boolean insert(News news) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.insert(news, conn);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败", e);
        }
        return flag;
    }

    @Override
    public boolean delete(News news, String key) throws Exception {
        boolean flag = false;
        try (Connection conn = JDBCUtils.getConnection()) {
            flag = DBTool.delete(news, conn, key);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败", e);
        }
        return flag;
    }

    @Override
    public List<News> queryAll(News news) throws Exception {
        List<News> list = new ArrayList<News>();
        try (Connection conn = JDBCUtils.getConnection()) {
            list = DBTool.queryAll(news, conn);
        } catch (SQLException e) {
            throw new RuntimeException("创建链接失败", e);
        }
        return list;
    }

    @Override
    public News findNews(String title, String column) throws Exception {
        News news = null;

        try (Connection conn = JDBCUtils.getConnection()) {
            // 查询 sql 字符串
            String sql = "select id, title, content, date, articleColumn, author,brief,briefImg from news where title = ? and articleColumn = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, column);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    news = new News();
                    news.setId(rs.getInt(1));
                    news.setTitle(rs.getString(2));
                    news.setContent(rs.getString(3));
                    news.setDate(rs.getDate(4));
                    news.setArticleColumn(rs.getString(5));
                    news.setAuthor(rs.getString(6));
                    news.setBrief(rs.getString(7));
                    news.setBriefImg(rs.getString(8));
                }
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("根据title和column查找新闻错误，请检查输入！！！！", e);
            }

            // 没有找到则返回 null
            return news;
        }
    }

    @Override
    public News findNews(int id) throws Exception {
        News news = null;

        try (Connection conn = JDBCUtils.getConnection()) {
            // 查询 sql 字符串
            String sql = "select id, title, content, date, articleColumn, author,brief,briefImg from news where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    news = new News();
                    news.setId(rs.getInt(1));
                    news.setTitle(rs.getString(2));
                    news.setContent(rs.getString(3));
                    news.setDate(rs.getDate(4));
                    news.setArticleColumn(rs.getString(5));
                    news.setAuthor(rs.getString(6));
                    news.setBrief(rs.getString(7));
                    news.setBriefImg(rs.getString(8));
                }
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("根据id查找新闻错误，请检查输入！！！！", e);
            }

            // 没有找到则返回 null
            return news;
        }
    }

    @Override
    public List<News> findByColumn(String column) throws Exception {
        List<News> list = new ArrayList<News>();
        try (Connection conn = JDBCUtils.getConnection()) {
            String sql = "select id, title, content, date, articleColumn, author,brief,briefImg from news where articleColumn = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, column);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    News news = new News();
                    news.setId(rs.getInt(1));
                    news.setTitle(rs.getString(2));
                    news.setContent(rs.getString(3));
                    news.setDate(rs.getDate(4));
                    news.setArticleColumn(rs.getString(5));
                    news.setAuthor(rs.getString(6));
                    news.setBrief(rs.getString(7));
                    news.setBriefImg(rs.getString(8));
                    list.add(news);
                }
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("根据栏目查找新闻失败，请检查输入栏目", e);
            }
            return list;
        }
    }

    @Override
    public int getSizeByColumn(String column) throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM news WHERE articleColumn = ?";

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数，防止SQL注入
            pstmt.setString(1, column);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); // 获取计数结果
                }
            }
        } catch (SQLException e) {
            // 可以根据需要记录日志或者进行其他异常处理
            throw new Exception("查询栏目新闻数量失败", e);
        }

        return count;
    }

    @Override
    public List<News> findByColumn(int pagesize, int offset, String column) throws Exception {
        List<News> newsList = new ArrayList<>();
        offset = (offset-1)*pagesize;
        String sql = "SELECT  id, title, date, articleColumn, author,brief,briefImg  FROM news WHERE articleColumn = ? LIMIT ? OFFSET ?";

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, column);
            pstmt.setInt(2, pagesize);
            pstmt.setInt(3, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    News news = new News();
                    news.setId(rs.getInt(1));
                    news.setTitle(rs.getString(2));
                    news.setDate(rs.getDate(3));
                    news.setArticleColumn(rs.getString(4));
                    news.setAuthor(rs.getString(5));
                    news.setBrief(rs.getString(6));
                    news.setBriefImg(rs.getString(7));
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            // 这里可以添加日志记录，或者根据需要进行其他异常处理
            throw new RuntimeException("查询新闻失败，请检查输入参数", e);
        }

        return newsList;
    }
}
