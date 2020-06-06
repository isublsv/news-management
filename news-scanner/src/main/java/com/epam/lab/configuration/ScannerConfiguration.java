package com.epam.lab.configuration;

import com.epam.lab.dao.FileReaderDao;
import com.epam.lab.dao.NewsDao;
import com.epam.lab.model.FileConsumer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.nio.file.Path;

@Configuration
@ComponentScan("com.epam.lab")
@PropertySource("classpath:scanner.properties")
public class ScannerConfiguration {
    
    @Autowired
    private FileReaderDao fileReaderDao;

    @Autowired
    private NewsDao newsDao;

    @Bean
    @Scope("prototype")
    FileConsumer newFileConsumer(Path path) {
        return new FileConsumer(path, fileReaderDao, newsDao);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
