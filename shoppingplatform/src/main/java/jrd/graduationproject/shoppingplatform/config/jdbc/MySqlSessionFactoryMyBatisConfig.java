package jrd.graduationproject.shoppingplatform.config.jdbc;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@MapperScan(basePackages="jrd.graduationproject.shoppingplatform.dao.mybatis",sqlSessionFactoryRef="sessionFactory")
public class MySqlSessionFactoryMyBatisConfig {

	@Autowired
	private Environment env;
	
	@Bean(name="sessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource")DataSource datasource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources=resolver.getResources(env.getProperty("mybatis.locationPattern"));
        sessionFactory.setMapperLocations(resources);
        return sessionFactory.getObject();
    }
}
