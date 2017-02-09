package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
//指定持久类dao包名，事物管理器，实体管理器工厂
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactory",transactionManagerRef="transactionManager",basePackages={"jrd.graduationproject.shoppingplatform.dao.jpa.mysql"})
public class MySqlEntityManagerFactoryJPAConfig {

	  @Bean(name = "entityManager")
	  @Primary
	  public EntityManager entityManager(@Qualifier("entityManagerFactory")EntityManagerFactory entityManagerFactory) {
	      return entityManagerFactory.createEntityManager();
	  }
	
	@Bean(name = "entityManagerFactory")
	@Primary
    public EntityManagerFactory mysqlEntityManagerFactory(@Qualifier("dataSource")DataSource dataSource) {  
		    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		    vendorAdapter.setDatabase(Database.MYSQL);
	        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	        factory.setJpaVendorAdapter(vendorAdapter);
	        factory.setDataSource(dataSource);
	        //jpa实体管理model包名
	        factory.setPackagesToScan("jrd.graduationproject.shoppingplatform.pojo");
	        //持久化单元名称
	        factory.setPersistenceUnitName("entityManagerFactory");
	        
	        Properties jpaProperties = new Properties();
	        //jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");//该命名策略已不支持
		            //hibernate.physical_naming_strategy  物理命名策略，用于转换“逻辑名称”(隐式或显式)的表或列成一个物理名称。
									// org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
									//org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
									//对于PhysicalNamingStrategyStandardImpl有DefaultNamingStrategy的效果；对于SpringPhysicalNamingStrategy  有ImprovedNamingStrategy的效果
	                //hibernate.implicit_naming_strategy  隐式命名策略，使用此属性当我们使用的表或列没有明确指定一个使用的名称 。
	        jpaProperties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
	        //方言
	        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	        //数据库ddl语句自动检查数据库结构
	        jpaProperties.put("hibernate.hbm2ddl.auto", "create");//validate | update | create | create-drop
	        //显示并格式化sql语句
	        jpaProperties.put("hibernate.show_sql", "true");
	        jpaProperties.put("hibernate.format_sql", "true");
	        
	        factory.setJpaProperties(jpaProperties);
	        factory.afterPropertiesSet();
	        
	        return factory.getObject();
    }
}
