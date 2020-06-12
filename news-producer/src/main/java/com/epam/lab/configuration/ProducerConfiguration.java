package com.epam.lab.configuration;

import com.epam.lab.controller.FileGeneratorController;
import com.epam.lab.controller.FolderTreeController;
import com.epam.lab.dao.FileGeneratorDao;
import com.epam.lab.dao.FileGeneratorDaoImpl;
import com.epam.lab.dao.FolderTreeDao;
import com.epam.lab.dao.FolderTreeDaoImpl;
import com.epam.lab.service.FileGeneratorService;
import com.epam.lab.service.FileGeneratorServiceImpl;
import com.epam.lab.service.FolderTreeService;
import com.epam.lab.service.FolderTreeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:producer.properties")
public class ProducerConfiguration {
    
    @Bean
    public FolderTreeDao folderTreeDao() {
        return new FolderTreeDaoImpl();
    }
    
    @Bean
    public FolderTreeService folderTreeService() {
        return new FolderTreeServiceImpl(folderTreeDao());
    }
    
    @Bean
    public FolderTreeController folderTreeController() {
        return new FolderTreeController(folderTreeService());
    }
    
    @Bean
    public FileGeneratorDao fileGeneratorDao() {
        return new FileGeneratorDaoImpl();
    }
    
    @Bean
    public FileGeneratorService fileGeneratorService() {
        return new FileGeneratorServiceImpl(fileGeneratorDao());
    }
    
    @Bean
    public FileGeneratorController fileGeneratorController() {
        return new FileGeneratorController(fileGeneratorService());
    }
    
    @Bean
    public ConfigurationManager configurationManager() {
        return new ConfigurationManager();
    }
}
