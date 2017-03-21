package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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

	private void setProperties(DataSource dataSource) {
		if (dataSource instanceof DruidDataSource) {
			DruidDataSource druidDataSource = (DruidDataSource) dataSource;
			try {
				Properties properties = druidProperties();
				setConnectionProperties(druidDataSource, properties);
			} catch (IOException e) {
				logger.error("数据库连接属性配置失败");
			}
		}
	}

	private void setConnectionProperties(DataSource dataSource, Properties properties) {
		Class<? extends DataSource> cls = dataSource.getClass();
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			if (!name.startsWith("set"))
				continue;
			name = name.substring(3);
			String key = name.substring(0, 1).toLowerCase() + name.substring(1);
			methodMap.put(key, method);
		}
		for (String nameKey : properties.stringPropertyNames()) {
			try {
				Method method = methodMap.get(nameKey);
				Class<?> pcls = method.getParameterTypes()[0];
				method.invoke(dataSource, new Object[] { convertType(pcls, properties.getProperty(nameKey)) });
			} catch (Exception e) {
				logger.error("参数" + nameKey + "配置失败");
				continue;
			}

		}
	}

	private Object convertType(Class<?> pcls, String property) {
		if (pcls.equals(String.class))
			return property;
		else if (pcls.equals(Integer.class) || pcls.equals(int.class))
			return Integer.parseInt(property);
		else if (pcls.equals(Boolean.class) || pcls.equals(boolean.class))
			return Boolean.parseBoolean(property);
		else if (pcls.equals(Long.class) || pcls.equals(long.class))
			return Long.parseLong(property);
		else if (pcls.equals(Byte.class) || pcls.equals(byte.class))
			return Byte.parseByte(property);
		else if (pcls.equals(Short.class) || pcls.equals(short.class))
			return Short.parseShort(property);
		else if (pcls.equals(CharSequence.class) || pcls.equals(char.class))
			return property.charAt(0);
		else if (pcls.equals(Double.class) || pcls.equals(double.class))
			return Double.parseDouble(property);
		else if (pcls.equals(Float.class) || pcls.equals(float.class))
			return Float.parseFloat(property);
		return property;
	}

	private static final String druidPropertiesStartsWith = "druid";
	private Properties druidProperties;

	@Bean(name = "druidProperties")
	public Properties druidProperties() throws IOException {
		if (druidProperties == null) {
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
			druidProperties = new Properties();
			for (String nameKey : properties.stringPropertyNames()) {
				if (nameKey.startsWith(druidPropertiesStartsWith))
					druidProperties.setProperty(nameKey.substring(druidPropertiesStartsWith.length() + 1),
							properties.getProperty(nameKey));
			}
		}
		return druidProperties;
	}
}
