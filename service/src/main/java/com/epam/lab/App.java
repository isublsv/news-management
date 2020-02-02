package com.epam.lab;

import com.epam.lab.configuration.DataSourceConfiguration;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.NewsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

public class App 
{
    public static void main( String[] args ) throws ServiceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
        NewsService newsService = (NewsService) context.getBean("newsService");

        NewsDto news = (NewsDto) context.getBean("newsDto");
        news.setTitle("!!!GOOD NEWS!!!");
        news.setShortText("blabla");
        news.setFullText("BLBLBLBLB");
        news.setCreationDate(LocalDateTime.now());
        news.setModificationDate(LocalDateTime.now());

        newsService.create(news);

        System.out.println(newsService.find(3));

        news.setId(1L);
        news.setShortText("FFFFFF");
        newsService.update(news);

        newsService.delete(1);

        System.out.println("The number of all news: " + newsService.countAllNews());
    }
}
