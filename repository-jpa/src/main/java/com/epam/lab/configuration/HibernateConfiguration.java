package com.epam.lab.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.lab")
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class HibernateConfiguration {

    private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";
    private static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DB_URL = "db.url";
    private static final String PROPERTY_NAME_DB_USER = "db.username";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    @Autowired
    private Environment environment;

    @Bean
    DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
        dataSourceConfig.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DB_URL));
        dataSourceConfig.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DB_USER));
        dataSourceConfig.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("com.epam.lab.model");

        Properties jpaProperties = new Properties();

        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));

        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        //If the value of this property is true, Hibernate will use pretty print
        //when it writes SQL to the console.
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
                environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));

        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }
}
