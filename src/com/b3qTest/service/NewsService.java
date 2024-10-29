package com.b3qTest.service;

import com.b3qTest.dao.NewsDao;
import com.b3qTest.dao.NewsDaoImpl;
import com.b3qTest.pojo.News;

import java.util.List;

public class NewsService implements NewsDao {

    // NewsDao 对象 实现应用
    private NewsDao dao = null;
    public NewsService() {
        try{
            this.dao = new NewsDaoImpl();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public boolean insert(News news) throws Exception {
        boolean flag = false;
        try {
            if(findNews(news.getTitle(),news.getArticle_column())==null){
                flag = this.dao.insert(news);
            }
        }catch (Exception e){
            throw e;
        }
        return flag;
    }

    @Override
    public boolean delete(News news,String key) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.delete(news,key);
        }catch (Exception e){
            throw e;
        }
        return flag;
    }

    @Override
    public List<News> queryAll(News news) throws Exception {
        List<News> all = null;
        try{
            all = this.dao.queryAll(news);
        }catch (Exception e){
            throw e;
        }
        return all;
    }

    @Override
    public News findNews(String title, String column) throws Exception {
        News news = null;
        try {
            news = this.dao.findNews(title,column);
        }catch (Exception e){
            throw e;
        }
        return news;
    }

    @Override
    public int getSizeByColumn(String column) throws Exception {
        int size = 0;
        try {
            size = this.dao.getSizeByColumn(column);
        }catch (Exception e){
            throw e;
        }
        return size;
    }

    @Override
    public List<News> findByColumn(String column) throws Exception {
        List<News> all = null;
        try{
            all = this.dao.findByColumn(column);
        }catch (Exception e){
            throw e;
        }
        return all;
    }

    @Override
    public News findNews(int id) throws Exception {
        News news = null;
        try {
            news = this.dao.findNews(id);
        }catch (Exception e){
            throw e;
        }
        return news;
    }

    @Override
    public List<News> findByColumn(int pagesize, int offset, String column) throws Exception {
        List<News> all = null;
        try{
            all = this.dao.findByColumn(pagesize,offset,column);
        }catch (Exception e){
            throw e;
        }
        return all;
    }
}
