package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jrd.graduationproject.shoppingplatform.util.PropertiesUtil;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"jrd.graduationproject.shoppingplatform.dao.jpa" })
@PropertySource(value = { "classpath:jdbc.properties" })
public class MySqlEntityManagerFactoryJPAConfig {

	@Autowired
	private Environment env;

	@Bean(name = "entityManager")
	@Primary
	public EntityManager entityManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	public EntityManagerFactory mysqlEntityManagerFactory(@Qualifier("dataSource") DataSource dataSource,
			@Qualifier("jpaProperties") Properties jpaProperties) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.SQL_SERVER);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setDataSource(dataSource);
		factory.setPackagesToScan(env.getProperty("jpa.packagesToScan"));
		factory.setPersistenceUnitName(env.getProperty("jpa.persistenceUnitName"));

		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean("transactionManager")
	@Primary
	public PlatformTransactionManager sqlServerDataSourceTransactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	private static final String jpaPropertiesStartsWith = "jpa.properties";

	@Bean(name = "jpaProperties")
	public Properties jpaProperties() throws IOException {
		return PropertiesUtil.propertiesOfFile("jdbc.properties", jpaPropertiesStartsWith);
	}

	@Bean(name = "JpaFlush")
	public JpaFlush jpaFlush(@Qualifier("entityManager") EntityManager entityManager) {
		JpaFlush jpaFlush = new JpaFlush();
		jpaFlush.setEntityManager(entityManager);
		return jpaFlush;
	}
}
