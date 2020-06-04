package com.epam.lab.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.epam.lab")
@PropertySource("classpath:scanner.properties")
public class ScannerConfiguration {
    
    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
