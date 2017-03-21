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
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactory",transactionManagerRef="transactionManager",basePackages={"jrd.graduationproject.shoppingplatform.dao.jpa"})
@PropertySource(value = { "classpath:jdbc.properties" })
public class MySqlEntityManagerFactoryJPAConfig {

	@Autowired
	private Environment env;
	
	  @Bean(name = "entityManager")
	  @Primary
	  public EntityManager entityManager(@Qualifier("entityManagerFactory")EntityManagerFactory entityManagerFactory) {
	      return entityManagerFactory.createEntityManager();
	  }
	
	@Bean(name = "entityManagerFactory")
	@Primary
    public EntityManagerFactory mysqlEntityManagerFactory(@Qualifier("dataSource")DataSource dataSource,@Qualifier("jpaProperties") Properties jpaProperties) {  
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
	private static final String jpaPropertiesStartsWith="jpa.properties";
	@Bean(name = "jpaProperties")
	public Properties jpaProperties() throws IOException {
		Properties properties= new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
		Properties jpaProperties= new Properties();
		for(String nameKey:properties.stringPropertyNames()){
			if(nameKey.startsWith(jpaPropertiesStartsWith))
				jpaProperties.setProperty(nameKey.substring(jpaPropertiesStartsWith.length()+1), properties.getProperty(nameKey));
		}
		return jpaProperties;
	}
}
