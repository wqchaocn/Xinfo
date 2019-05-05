package com.xerplus.xinfo.service;

import com.xerplus.xinfo.dao.NewsDAO;
import com.xerplus.xinfo.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLastestNews(int userId) {
        return newsDAO.selectByUserIdAndOffset(userId);
    }

    public List<News> getUserNews(int userId) {
        return newsDAO.selectByUserId(userId);
    }

    public List<News> getLikeNewsSort() {
        return newsDAO.selectByLike();
    }

    public List<News> getCommentNewsSort() {
        return newsDAO.selectByComment();
    }

    public News getNewsById(int id) {
        return newsDAO.selectById(id);
    }

    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }

    public int updateLikeCount(int id, int count) {
        return newsDAO.updateLikeCount(id, count);
    }

    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }
}
