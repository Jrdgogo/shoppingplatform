package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
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

import com.github.pagehelper.PageInterceptor;

import jrd.graduationproject.shoppingplatform.util.PropertiesUtil;

@Configuration
@MapperScan(basePackages = "jrd.graduationproject.shoppingplatform.dao.mybatis", sqlSessionFactoryRef = "sessionFactory")
public class MySqlSessionFactoryMyBatisConfig {

	@Autowired
	private Environment env;
	private static final String mybatisPropertiesStartsWith="mybatis.properties";

	@Bean(name = "sessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource datasource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(datasource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources(env.getProperty("mybatis.locationPattern"));
		sessionFactory.setMapperLocations(resources);
		PageInterceptor pageInterceptor=new PageInterceptor();
		Properties properties=PropertiesUtil.propertiesOfFile("jdbc.properties", mybatisPropertiesStartsWith);
		pageInterceptor.setProperties(properties);
		sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
		return sessionFactory.getObject();
	}

}
