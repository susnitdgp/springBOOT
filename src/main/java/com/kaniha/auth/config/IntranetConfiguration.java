package com.kaniha.auth.config;

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
@PropertySource({ "classpath:Intranet-db.properties" })
@EnableJpaRepositories(
	    basePackages = "com.kaniha.auth.repository", 
	    entityManagerFactoryRef = "intranetEntityManager", 
	    transactionManagerRef = "intranetTransactionManager")
public class IntranetConfiguration {
	
	@Autowired
    private Environment env;
	
	 @Bean
	 public LocalContainerEntityManagerFactoryBean intranetEntityManager() {
	        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	        em.setDataSource(intranetDataSource());
	        em.setPackagesToScan(new String[] { "com.kaniha.auth.entity" });
	        
	        em.setPersistenceUnitName("Intranet");
	        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	        em.setJpaVendorAdapter(vendorAdapter);
	        HashMap<String, Object> properties = new HashMap<String, Object>();
	        //properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	        properties.put("intranet.hibernate.dialect", env.getProperty("hibernate.dialect"));
	        em.setJpaPropertyMap(properties);
	 
	        return em;
	    }
	
	
	@Bean
    public DataSource intranetDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("intranet.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("intranet.user.jdbc.url"));
        dataSource.setUsername(env.getProperty("intranet.jdbc.user"));
        dataSource.setPassword(env.getProperty("intranet.jdbc.password"));
        
        return dataSource;
    }

	@Bean
	public PlatformTransactionManager intranetTransactionManager() {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(intranetEntityManager().getObject());
	        return transactionManager;
	}

}
