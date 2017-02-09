package jrd.graduationproject.shoppingplatform.config.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySource(value={"classpath:jdbc-h2.properties","classpath:application.properties"})
public class DataSourceConfig {
	
	@Bean("dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary//默认
	public DataSource MySqlDataSource() {
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}
	
	@Bean(name = "h2DataSource", initMethod = "init", destroyMethod = "close")
	@ConfigurationProperties(prefix = "h2.datasource")
	public DataSource h2DataSource() {
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}

}
