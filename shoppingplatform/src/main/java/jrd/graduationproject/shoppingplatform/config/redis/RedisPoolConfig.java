package jrd.graduationproject.shoppingplatform.config.redis;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import jrd.graduationproject.shoppingplatform.util.PropertiesUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource(value = { "classpath:redis.properties" })
public class RedisPoolConfig {

	@Autowired
	private Environment env;

	@Bean(name = "jedisPool")
	public JedisPool jedisPool(@Qualifier(value = "jedisPoolConfig") JedisPoolConfig poolConfig) {
		JedisPool jedisPool = new JedisPool(poolConfig, env.getProperty("redis_host"),
				env.getProperty("redis_port", Integer.class), env.getProperty("redis_timeout", Integer.class),
				env.getProperty("redis_password"));
		return jedisPool;
	}

	private static final String redisPropertiesStartsWith = "spring.redisProperties";

	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() throws IOException {
		Properties properties = PropertiesUtil.propertiesOfFile("redis.properties", redisPropertiesStartsWith);
		JedisPoolConfig config = new JedisPoolConfig();
		PropertiesUtil.setMethodOfProperties(config, properties);

		return config;

	}

	@Bean(name = "jedisDataSource")
	public JedisDataSource jedisDataSource(@Qualifier(value = "jedisPool") JedisPool jedisPool) throws IOException {
		JedisDataSource dataSource = new JedisDataSource();

		dataSource.setJedisPool(jedisPool);
		return dataSource;

	}

}
