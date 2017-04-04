package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

import jrd.graduationproject.shoppingplatform.util.PropertiesUtil;

@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
public class DataSourceConfig {

	private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Bean("dataSource")
	@ConfigurationProperties(prefix = "mysql")
	@Primary // 默认
	public DataSource MySqlDataSource() {
		return dataSource(DruidDataSource.class);
	}

	private DataSource dataSource(Class<? extends DataSource> cls) {
		DataSource dataSource = DataSourceBuilder.create().type(cls).build();
		setProperties(dataSource);
		return dataSource;
	}

	// @Bean(name = "h2DataSource", initMethod = "init", destroyMethod =
	// "close")
	// @ConfigurationProperties(prefix = "h2.datasource")
	// public DataSource h2DataSource() {
	// return DataSourceBuilder.create().type(DruidDataSource.class).build();
	// }

	private static final String druidPropertiesStartsWith = "druid";

	private void setProperties(DataSource dataSource) {
		try {
			Properties properties = PropertiesUtil.propertiesOfFile("jdbc.properties", druidPropertiesStartsWith);
			PropertiesUtil.setMethodOfProperties(dataSource, properties);
		} catch (IOException e) {
			logger.error("数据库连接属性配置失败");
		}
	}

}
