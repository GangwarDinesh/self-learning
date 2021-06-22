package com.demo.app.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = "com.demo.app.repository.product", 
  entityManagerFactoryRef = "productEntityManager", 
  transactionManagerRef = "productTransactionManager")
public class PersistenceProductAutoConfiguration {
   
    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource productDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "productEntityManager")
    public LocalContainerEntityManagerFactoryBean productEntityManager(@Qualifier("productDataSource") DataSource dataSource, EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
    	Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.show-sql", "true");
    	return entityManagerFactoryBuilder.dataSource(dataSource).packages("com.demo.app.model.product").persistenceUnit("productdb").properties(properties).build();
    }
   
    @Bean("productTransactionManager")
    public PlatformTransactionManager productTransactionManager(@Qualifier("productEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
   
}