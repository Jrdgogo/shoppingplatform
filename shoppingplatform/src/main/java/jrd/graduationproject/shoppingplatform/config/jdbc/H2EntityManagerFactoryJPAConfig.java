package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
//@PropertySource("classpath:jdbc-h2.properties")
//@EnableJpaRepositories(entityManagerFactoryRef="h2EntityManagerFactory",transactionManagerRef="h2TransactionManager",basePackages={"jrd.graduationproject.shoppingplatform.dao.jpa.h2"})
public class H2EntityManagerFactoryJPAConfig {

	// @Bean(name = "h2EntityManager")
	public EntityManager entityManager(@Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	// @Bean(name = "h2EntityManagerFactory")
	public EntityManagerFactory h2EntityManagerFactory(@Qualifier("h2DataSource") DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.H2);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setDataSource(dataSource);
		factory.setPackagesToScan("jrd.graduationproject.shoppingplatform.pojo");
		factory.setPersistenceUnitName("h2entityManagerFactory");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.physical_naming_strategy",
				"org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		jpaProperties.put("hibernate.hbm2ddl.auto", "create");
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.format_sql", "true");
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();

		return factory.getObject();
	}
}
