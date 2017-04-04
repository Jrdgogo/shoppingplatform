package jrd.graduationproject.shoppingplatform.config.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MailSubscribe {

	private SpringMail springMail;

	private Jedis jedis;

	private static Logger logger = LoggerFactory.getLogger(MailSubscribe.class);

	public void listener() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				jedis.subscribe(new JedisPubSub() {

					@Override
					public void onMessage(String channel, String message) {
						new Thread(new Runnable() {

							@Override
							public void run() {

								JSONObject jsonObject = JSONObject.parseObject(message);

								String logmsg = jsonObject.getObject("0", String.class);

								@SuppressWarnings("unchecked")
								Map<String, Object> params = jsonObject.getObject("1", Map.class);

								List<String> receiverUsers = new ArrayList<>();
								int i = 2;
								while (true) {
									String receiverUser = jsonObject.getString("" + i);
									if (receiverUser == null)
										break;
									receiverUsers.add(receiverUser);
									i++;
								}
								if (!springMail.doSend("畅游--任意购 用户激活", "activeion_User.ftl", params,
										receiverUsers.toArray(new String[receiverUsers.size()])))
									logger.error(logmsg + " 邮箱发送失败");

							}
						}).start();

					}

				}, "activeMail");

				jedis.close();

			}
		}).start();

	}

	public SpringMail getSpringMail() {
		return springMail;
	}

	public void setSpringMail(SpringMail springMail) {
		this.springMail = springMail;
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

}
