package com.epam.lab.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.module.jdk8.Jdk8Module;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.epam.lab")
public class ServiceConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        //Localdate mapper
        mapper.registerModule(new Jsr310Module());
        mapper.registerModule(new Jdk8Module());

        mapper.getConfiguration()
              //Tokens are matched in strict order
              .setMatchingStrategy(MatchingStrategies.STANDARD)
              //unable field matching
              .setFieldMatchingEnabled(true)
              //null skipping
              .setSkipNullEnabled(true)
              //access to private fields
              .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
