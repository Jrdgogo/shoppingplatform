package jrd.graduationproject.shoppingplatform.config.jdbc;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
//指定持久类dao包名，实体管理器
@MapperScan(basePackages="jrd.graduationproject.shoppingplatform.dao.mybatis",sqlSessionFactoryRef="mysqlSessionFactory")
public class MySqlSessionFactoryMyBatisConfig {

	@Bean(name="mysqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource")DataSource datasource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        //设置mapper xml映射文件名
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources=resolver.getResources("jrd/graduationproject/shoppingplatform/mapper/*.xml");
        sessionFactory.setMapperLocations(resources);
        return sessionFactory.getObject();
    }
}
