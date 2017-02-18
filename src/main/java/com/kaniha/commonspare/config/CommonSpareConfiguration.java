package com.kaniha.commonspare.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:CommonSpare-db.properties" })
@EnableJpaRepositories(
	    basePackages = "com.kaniha.commonspare.repository", 
	    entityManagerFactoryRef = "commonspareEntityManager", 
	    transactionManagerRef = "commonspareTransactionManager")
public class CommonSpareConfiguration {
	
	@Autowired
    private Environment env;
	
	 @Bean
	 public LocalContainerEntityManagerFactoryBean commonspareEntityManager() {
	        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	        em.setDataSource(commonspareDataSource());
	        em.setPackagesToScan(new String[] { "com.kaniha.commonspare.entity" });
	        
	        em.setPersistenceUnitName("CommonSpare");
	        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	        em.setJpaVendorAdapter(vendorAdapter);
	        HashMap<String, Object> properties = new HashMap<String, Object>();
	        //properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	        properties.put("intranet.hibernate.dialect", env.getProperty("hibernate.dialect"));
	        em.setJpaPropertyMap(properties);
	 
	        return em;
	    }
	
	
	@Bean
    public DataSource commonspareDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("commonspare.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("commonspare.user.jdbc.url"));
        dataSource.setUsername(env.getProperty("commonspare.jdbc.user"));
        dataSource.setPassword(env.getProperty("commonspare.jdbc.password"));
        
        return dataSource;
    }

	@Bean
	public PlatformTransactionManager commonspareTransactionManager() {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(commonspareEntityManager().getObject());
	        return transactionManager;
	}

}
