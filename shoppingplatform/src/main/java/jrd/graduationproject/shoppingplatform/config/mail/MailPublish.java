package jrd.graduationproject.shoppingplatform.config.mail;

import jrd.graduationproject.shoppingplatform.config.redis.JedisDataSource;
import redis.clients.jedis.Jedis;

public class MailPublish {

	private JedisDataSource jedisDataSource;

	public void publish(String message) {

		Jedis jedis = jedisDataSource.getJedis();
		new Thread(new Runnable() {

			@Override
			public void run() {
				jedis.publish("activeMail", message);
				jedis.close();
			}
		}).start();

	}


	public JedisDataSource getJedisDataSource() {
		return jedisDataSource;
	}

	public void setJedisDataSource(JedisDataSource jedisDataSource) {
		this.jedisDataSource = jedisDataSource;
	}
	
}
