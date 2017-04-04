package jrd.graduationproject.shoppingplatform.config.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisDataSource {

	private JedisPool jedisPool;

	public Jedis getJedis() {
		return jedisPool.getResource();

	}

	public void close() {
		if (!jedisPool.isClosed())
			jedisPool.close();
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
