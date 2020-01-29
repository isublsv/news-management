package com.epam.lab;

import com.epam.lab.configuration.DataSourceConfiguration;
import com.epam.lab.model.News;
import com.epam.lab.service.NewsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
        NewsService newsService = (NewsService) context.getBean("newsService");

        News news = (News) context.getBean("news");
        news.setTitle("!!!GOOD NEWS!!!");
        news.setShortText("blabla");
        news.setFullText("BLBLBLBLB");
        news.setCreationDate(LocalDateTime.now());
        news.setModificationDate(LocalDateTime.now());

        newsService.create(news);

        System.out.println(newsService.find(1));

        news.setShortText("FFFFFF");
        newsService.update(news);

        newsService.delete(5);
    }
}
