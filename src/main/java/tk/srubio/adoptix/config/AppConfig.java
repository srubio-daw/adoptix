package tk.srubio.adoptix.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "tk.srubio.adoptix")
@PropertySources({ @PropertySource(value = "classpath:adoptix.properties") })
@EnableJpaRepositories("tk.srubio.adoptix.model")
@EnableTransactionManagement
public class AppConfig {
	
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driverClassName";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	
	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}
	
	Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
				setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
				setProperty("hibernate.cache.enabled", env.getProperty("hibernate.cache.enabled"));
				setProperty("hibernate.cache.use_second_level_cache",
						env.getProperty("hibernate.cache.use_second_level_cache"));
				setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));
				setProperty("hibernate.connection.aggressive_release",
						env.getProperty("hibernate.connection.aggressive_release"));
				setProperty("hibernate.cache.provider_class", env.getProperty("hibernate.cache.provider_class"));
				setProperty("hibernate.cache.use_minimal_puts", env.getProperty("hibernate.cache.use_minimal_puts"));
				setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
				setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
				setProperty("hibernate.temp.use_jdbc_metadata_defaults",
						env.getProperty("hibernate.temp.use_jdbc_metadata_defaults"));
			}
		};
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setGenerateDdl(true);
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setDataSource(dataSource());
		
		factory.setPackagesToScan(new String[] { "tk.srubio.adoptix.model" });
		factory.setJpaProperties(hibernateProperties());
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
	
}
