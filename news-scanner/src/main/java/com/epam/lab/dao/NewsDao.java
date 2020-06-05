package com.epam.lab.dao;

import com.epam.lab.model.News;

import java.util.List;

public interface NewsDao extends Dao {

    void addNews(List<News> news);
}
